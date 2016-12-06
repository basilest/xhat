/*
 * Created on Dec 18, 2011
 *
 * 
 * @author  Basile Stefano
 */
package common;

import java.io.*;

import C_dataClass.MsgHdr;
import treetable.TreeNodeInfo;

           


import sbs.*;
/**
 * @author   Basile Stefano
 *
 *      +-----------------------+ --------
 *      |                       |       T
 *      |                       |       O
 *      |                       |       T
 *      |      Data   Size (Sd) |        
 *      |                       |       S  (T)
 *      +-----------------------+       I
 *      |      Select Size (Ss) |       Z
 *      |                       |       E
 *      +-----------------------+---------
 *      
 *      
 *      T = Sd + Ss
 *      Ss = ((Sd - 1) / 8) + 1   
 *
 *      N.B. the trunc on int operation cannot give back Sd from T as (8T - 7 ) / 9
 *           but it is known from item.getIntVar(sbs_generic.V_INT_CTYPESIZE)
 *           thus Ss = T - Sd
 * 
 */

public class Old__BinaryFileManager {
    
    final static int    NUM_BYTE_ROW   = 15; // when reached wrap line  
    final static int    NUM_BYTE_BLOCK = 8;  // when reached write a new block (check/write/get)-var
    
    
    
    //##########################
    private RandomAccessFile    BinaryDumpFile;
    private String              BinaryDumpFileName;
    private byte[]              SelectDataArray;

    Environ environ;
    //##########################
    public Old__BinaryFileManager  (Environ environ)
    {
        this.environ = environ;
        BinaryDumpFileName = "BinaryStructDump.bin";
    }
    //##########################
    private String FullFileName(String FileName)    {
            return (environ.getTempFullFileName(FileName));
    }
    //##########################
    private void CloseBinaryDump()    {
        try {
          if (BinaryDumpFile != null) { 
              BinaryDumpFile.close();
              BinaryDumpFile = null;
          }
        } catch (IOException e) {System.out.println("Unable Close Dump File");}
    }
    //##########################
    private void InitMsgHdr (sbs_item item, MsgHdr hdr) {
        TreeNodeInfo         ninfo;
        
        if (item.getType() == sbs_generic.MESSAGE) {
            
            if (hdr.source_sys != null) {
            ninfo = (TreeNodeInfo)hdr.source_sys.getUserObject();
            setValue (ninfo.getSize(), ninfo.getOffset(), item.getTask1().getProcessor());
            }
            if (hdr.dest_sys   != null){
            ninfo = (TreeNodeInfo) hdr.dest_sys.getUserObject();
            setValue (ninfo.getSize(), ninfo.getOffset(), item.getTask2().getProcessor());
            }
            if (hdr.source_mbx != null){
            ninfo = (TreeNodeInfo) hdr.source_mbx.getUserObject();
            setValue (ninfo.getSize(), ninfo.getOffset(), item.getTask1().getMailbox());
            }
            if (hdr.dest_mbx   != null){
            ninfo = (TreeNodeInfo) hdr.dest_mbx.getUserObject();
            setValue (ninfo.getSize(), ninfo.getOffset(), item.getTask2().getMailbox());
            }
        }
   }                
    //##########################
    public  void InitBinaryDump(sbs_item item, MsgHdr hdr) {
            
            environ.getBinaryFileManager();
        
            int  b;
            int  DataSize   = item.getIntVar(sbs_generic.V_INT_CTYPESIZE);
            int  SelectSize = (DataSize-1)/8 + 1;
            BufferedOutputStream dumpBuff;
            
            try {
                CloseBinaryDump();  // close from previous usage 
                
                if (item.isValidFile() == false) {
                
                dumpBuff = new BufferedOutputStream 
                              (new FileOutputStream (FullFileName(BinaryDumpFileName)));
                
                for (b = 0; b < DataSize; b++) 
//                dumpBuff.write(0xFF);   //   re-size Dump file to 'Size' bytes all 0xFF
                     dumpBuff.write(0);   //   re-size Dump file to 'Size' bytes all 0

                SelectDataArray = new byte [SelectSize];
                for (b = 0; b < SelectDataArray.length; b++) { 
                     SelectDataArray[b] = 0;  //   set any bit to 0
                }
                
                dumpBuff.close(); // close just created Dump File
                                            
                BinaryDumpFile =  // re-open in random access mode
                               new RandomAccessFile(FullFileName(BinaryDumpFileName), "rw");
                
                InitMsgHdr(item, hdr);
                }
                else
                    LoadDumpFile (item.getStringVar(sbs_generic.V_STR_DATAFILE), DataSize);
                
            } catch (IOException e) {System.out.println("Unable to Init Dump File, Size:" + DataSize) ;}
    }
    

    //##########################
     private   void CopyFile (String FileNameOri, String FileNameNew) {
         
         File inputFile  = new File(FullFileName(FileNameOri));
         File outputFile = new File(FullFileName(FileNameNew));
         try {
             FileReader in   = new FileReader(inputFile);
             FileWriter out  = new FileWriter(outputFile);
             int c;

             while ((c = in.read()) != -1)
                         out.write(c);
 
             in.close();
             out.close();
        } catch (Exception e) {System.out.println("Unable to Copy " + FileNameOri + " to " + FileNameNew) ;}
     }
    //##########################
     public  void CopyItemFiles (sbs_item itemOri, sbs_item itemNew) {
         CopyFile (itemOri.getStringVar(sbs_generic.V_STR_DATAFILE), 
                   itemNew.getStringVar(sbs_generic.V_STR_DATAFILE));      
         CopyFile (itemOri.getStringVar(sbs_generic.V_STR_DESCFILE), 
                   itemNew.getStringVar(sbs_generic.V_STR_DESCFILE));      
     }    
    //##########################
    private  void LoadDumpFile (String FileNameIn, int DataSize) {
    
        int  b;
        int  c;
        int  fileLen;
        FileInputStream  in;
        File             f;

        try {
            f              = new File (FullFileName(FileNameIn));
            in             = new FileInputStream (f);
            BinaryDumpFile = new RandomAccessFile(FullFileName(BinaryDumpFileName), "rw");
            
            fileLen  = (int) f.length();
            
            for (b = 0; b < DataSize; b++) { 
                BinaryDumpFile.write(in.read());
            }
            
            SelectDataArray = new byte [fileLen - DataSize];
            for (b = 0; (c = in.read()) != -1; b++)  
                 SelectDataArray[b] = (byte) c;

        } catch (IOException e) {System.out.println("Unable to Load File from " + FileNameIn +
                                                    " (Data Size:" + DataSize + ")");}
    }
    //##########################
    public  void SaveDumpFile (String FileNameOut) {
    
        int b;
        try {
            FileOutputStream out = new FileOutputStream(FullFileName(FileNameOut));
            
            BinaryDumpFile.seek(0);
            while ((b = BinaryDumpFile.read()) != -1)
                        out.write(b);
            
            for (b = 0; b < SelectDataArray.length; b++)  
                 out.write(SelectDataArray[b]);
            
            out.close();
        } catch (Exception e) {System.out.println("Unable to Copy Dump File to " + FileNameOut);}
        
        CloseBinaryDump();  
    }
    //#########    SET VALUE  ###########
     public void setValue (int Size,
                           int Offset, 
                           int val) {
         int b;
         int WriteByte;
         try {
             BinaryDumpFile.seek((long)(Offset));
             for (b=0; b < Size; b++) {
                  val >>= (b * 8);
                  WriteByte = val & 0xFF;
                  BinaryDumpFile.write(WriteByte);
                  SelectDataArray[(Offset + b)/8] |= (1 << ((Offset + b)%8)) ;
             }
        } catch (IOException e) {System.out.println("Unable to Write Struct Dump");}
     }
     //#########    GET VALUE  ###########
      public String getValue (int Size,
                              int Offset) {
          int val = 0;
          int b;
          int ReadByte;
          boolean select = false;
          try {
              BinaryDumpFile.seek((long)(Offset));
              for (b=0; b < Size; b++) {
                   ReadByte = BinaryDumpFile.read();
                   ReadByte <<= (b * 8);
                   val |= ReadByte;
                   if (select == false &&
                      (SelectDataArray[(Offset + b)/8] & (1 << ((Offset + b)%8)))  != 0)
                       select = true;
              }
         } catch (IOException e) {System.out.println("Unable to Read Struct Dump");}
          return (select  ? String.valueOf(val) : "*" );
      }
    //##########################
    private void flush (sbs_item         item, 
                        int              item_Type,
                        RandomAccessFile in,
                        FileWriter       out, 
                        int              Offset, 
                        int              num_byte) throws IOException{
        
        int b;
        int c;
        
        if (num_byte != 0) {
            String str = (item_Type == sbs_generic.READ_VAR) ?  "checkvalue\n" : "setvalue\n";
            out.write( str + item. getStringVar(sbs_generic.V_STR_CTYPENAME) +
                             "\n" + Offset + "\n" + num_byte);
                        
            in.seek(Offset);
            for (b = 0; b < num_byte; b++) { 
                 c = in.read();
                 out.write((((b+1) % NUM_BYTE_ROW == 0) ? "\n" : " ") + 
                            ((c<16) ? "0" : "") + Integer.toHexString(c));
        }
      }
    }
    
    //##########################
    public   void export2Lis (sbs_item item, FileWriter out) {
    
        int    b;
        int    c;
        int    Offset;
        int    num_byte;
        int    fileLen;
        int    DataSize   = item.getIntVar(sbs_generic.V_INT_CTYPESIZE);
        int    SelectSize;
        String FileNameIn = FullFileName(item.getStringVar(sbs_generic.V_STR_DATAFILE));
        RandomAccessFile in;

        try {
            in         = new RandomAccessFile( FileNameIn, "r");
            fileLen    = (int) in.length();
            SelectSize = (DataSize-1)/8 + 1;
            
            in.seek(DataSize);
            SelectDataArray = new byte [fileLen - DataSize];
            for (b = 0; (c = in.read()) != -1; b++)  
                 SelectDataArray[b] = (byte) c;

            int item_Type = item.getIntVar(sbs_generic.V_INT_TYPE);

            switch (item_Type) {
            case sbs_generic.FUNCTION:
            out.write ("\ncall\n" + item.getStringVar(sbs_generic.V_STR_CTYPENAME) + "\n" +  
                                    environ.getSymbolTree().getChildCount() + "\n");
            // no break : fall down to dump data bytes
            case sbs_generic.MESSAGE:
            in.seek(0);
            if (item.isaSend() == false)    
                for (b = 0; b < DataSize; b++) { 
                     c = in.read();
                     out.write(((c<16) ? "0" : "") + Integer.toHexString(c) + 
                               (((b+1)% NUM_BYTE_ROW == 0) ? "\n" : " "));
                }
            else
            for (b = 0; b < DataSize; b++) { 
                 c = in.read();
                 if ((SelectDataArray[b/8] & (1 << (b%8)))  != 0) 
                     out.write(((c<16) ? "0" : "") + Integer.toHexString(c));
                 else
                     out.write(" *");
                
                out.write(((b+1) % NUM_BYTE_ROW == 0) ? "\n" : " ");
            }
            break;

            case sbs_generic.READ_VAR:
            case sbs_generic.WRITE_VAR:
            Offset   = 0;
            num_byte = 0;
            for (b = 0; b < SelectDataArray.length * 8; b++) {  // b here means number of bit not byte
                
                if (((SelectDataArray[b/8] & (1 << (b%8)))  == 0)  ||
                     (num_byte == NUM_BYTE_BLOCK)) {
                         flush (item, item_Type, in, out, Offset, num_byte);
                         num_byte = 0;
                }
                else {
                    if (num_byte == 0) 
                        Offset = b*8;
                    num_byte ++;
                }         
            }
            flush (item, item_Type, in, out, Offset, num_byte);

            break;
                
            default:
                 break;
             }
        } catch (IOException e) {System.out.println("Unable to export to LIS " + FileNameIn +
                                                    " (Data Size:" + DataSize + ")");}
    }
}


//       String line = isaSend() ?  "send\n" : "receive\n";  
//       line += getIntVar(sbs_generic.V_INT_CTYPESIZE) + "\n";
//  
//  String line = "read_var\n" + getStringVar(sbs_generic.V_STR_CTYPENAME) + "\n";
//
//  String line = "set_var\n"  + getStringVar(sbs_generic.V_STR_CTYPENAME) + "\n";  
