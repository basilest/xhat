/*
 * Created on Dec 18, 2011
 *
 * 
 * @author  Basile Stefano
 */
package sbs;

import java.io.*;
import java.util.Hashtable;
import java.util.regex.*;
import java.util.Enumeration;
import java.util.Arrays;
import common.Environ;
import C_dataClass.MsgHdr;
/**
 * @author   Basile Stefano
 *
 */

public class ItemDataManager {
    
    final static int    NUM_BYTE_ROW    = 5; // when reached wrap line  
    final static int    NUM_BLOCK_BYTES = 1024;  // when reached write a new block (check/write/get)-var
    
    
    //##########################
    private Hashtable           ht;

    Environ environ;

    Pattern     patt;
    Matcher     m;

    //##########################
    public ItemDataManager  (Environ environ)
    {
        this.environ = environ;
        ht           = new Hashtable(500);
        patt         = Pattern.compile("(\\d+):(\\d+)");
    }
    
    //######################################################
     private  void ReadDataFile(String FullFileName)
     {
        int      Offset;
        int      ByteVal;
        int      num_line = 0;
        String   read_line;

        try {
            ht.clear();   
            System.out.println("Opening:["+FullFileName+"]");
            File f = new File(FullFileName);
            if (f.exists()) {
                BufferedReader  br  = new BufferedReader(new FileReader(f));
        
                while ((read_line = br.readLine()) != null) {
                    num_line++;
                    m = patt.matcher(read_line);
                    if ( m.find())      // found 'offset:byte_val'
                    {
                          Offset   = Integer.parseInt(m.group(1));
                          ByteVal  = Integer.parseInt(m.group(2));
                          ht.put(new Integer(Offset), new Integer(ByteVal));
                    }
                    else { 
                          System.out.println(FullFileName + "syntax error line " + 
                                             num_line + ":\n" + read_line + "\n");
                    }
                }
                br.close();
            }
        } catch (Exception e) {System.out.println("Unable to Open File " + FullFileName);}
    }
    //######################################################
     private void WriteDataFile(String FullFileName)
     {
       try {
             FileWriter  out = new FileWriter(FullFileName);
    
             for (Enumeration e = ht.keys();  e.hasMoreElements(); ) {
                 Integer key = (Integer)e.nextElement();
                 Integer val = (Integer)ht.get(key);
                 out.write(key.toString() + ":" + val.toString() + "\n");
             }
             out.close();

       } catch (Exception e) {System.out.println("Unable to write " + FullFileName);}
     }
    //######################################################
     public void LoadItemData (sbs_item item) {
        String FullFileName = environ.getTempFullFileName(
                                      item.getStringVar(sbs_generic.V_STR_DATAFILE));
     
        ReadDataFile(FullFileName);
     }
    //######################################################
     public void SaveItemData (sbs_item item) {
        String FullFileName = environ.getTempFullFileName(
                                      item.getStringVar(sbs_generic.V_STR_DATAFILE));
     
        WriteDataFile(FullFileName);
     }
    //######################################################
     public void InitData (MsgHdr msgHdr) {
                 ht.clear ();
     }
    //#########    SET VALUE  ###########
     public void setValue (int Size,
                           int Offset, 
                           int val) {
         Integer Off;
         Integer ByteVal;
         try {
             for (; Size-- > 0; val >>= 8, Offset++) {

                 Off     = new Integer(Offset);
                 ByteVal = new Integer (val & 0xFF);
                 
                 if (ht.containsKey(Off))
                     ht.remove(Off);

                 ht.put(Off, ByteVal);
             }
        } catch (Exception e) {System.out.println("Unable to Set Hash. Val:" + 
                                                  val + "Size:"+ Size + " Offset:" + Offset);}
     }
     //#########    GET VALUE  ###########
      public String getValue (int Size,
                              int Offset) {
          int       b   = 0;
          int       val = 0;
          Integer   Off;
          Integer   ByteVal;
          try {
              for (; b < Size; b++, Offset++) {
                  
                  Off     = new Integer(Offset);
                  ByteVal = (Integer) ht.get(Off);
                  if (ByteVal == null) 
                      break;
                  
                  val |= (ByteVal.intValue() << (8*b));
              }
         } catch (Exception e) {System.out.println("Unable to Read Hash. Offset:" + Offset);}
          return ((b == 0) ?  "*" : String.valueOf(val) );
      }
    //#########    RESET VALUE  ###########
     public void resetValue (int Size,
                             int Offset) {
         Integer Off;
         try {
             for (; Size-- > 0; Offset++) {

                 Off  = new Integer(Offset);
                 if (ht.containsKey(Off))
                     ht.remove(Off);
             }
        } catch (Exception e) {System.out.println("Unable to ReSet Hash. Size:"+ Size + " Offset:" + Offset);}
     }
    //##########################
    private  void dumpLisBytes (sbs_item   item, 
                                FileWriter out, 
                                Integer[]  keyArray,       
                                int        j,              // index of first offset set by user
                                int        start,          // first offset to print
                                int        size,           // num bytes to print --> start+size=last offset
                                boolean    starChar) {
        int       o;
        int       val;
        int       c = 0;
        int       key_j;
    
        try {
            key_j = ((Integer) keyArray[j]).intValue();   // first offset set by user
            for (o = start;  o < (start + size);  o++) {  // cycle from first to last offset
                if (o == key_j) {  // offset set by user 
                    val = ((Integer) ht.get(keyArray[j])).intValue();
                    out.write((((c++) % NUM_BYTE_ROW == 0) ? "\n" : " ") + 
                               ((val<16) ? "0" : "") + Integer.toHexString(val));
                    if (++j < keyArray.length )
                        key_j = ((Integer) keyArray[j]).intValue();
                }
                else
                out.write((((c++) % NUM_BYTE_ROW == 0) ? "\n" : " ") + 
                         (starChar ? " *" : "00"));
           }
        } catch (Exception e) {System.out.println("Unable to Dump to LisFile");}
    }

    //##########################
    public   void export2Lis (sbs_item item, FileWriter out) {
        
    try {
      String  str;
      LoadItemData (item);
      Integer [] keyArray = (Integer []) ht.keySet().toArray(new Integer[0]);
      int size;
      if (keyArray.length > 0)  {
         Arrays.sort(keyArray);

         int item_Type = item.getIntVar(sbs_generic.V_INT_TYPE);
    
         switch (item_Type) {
             
         case sbs_generic.FUNCTION:
              size = item.getIntVar(sbs_generic.V_INT_CTYPESIZE);
              str  = "\ncall\n" + item.getStringVar(sbs_generic.V_STR_CTYPENAME) + "\n" +  
                                  environ.getSymbolTree().getChildCount();
              out.write(str);
              dumpLisBytes (item, out, keyArray, 0, 0, size, false);
              break;
              
         case sbs_generic.MESSAGE:
              size = item.getIntVar(sbs_generic.V_INT_CTYPESIZE);
              str  = (item.isaSend() ?  "\nsend\n" : "\nreceive\n") + size;  
              out.write(str);
              dumpLisBytes (item, out, keyArray, 0, 0, size, !item.isaSend());
              break;
    
         case sbs_generic.CHECK_VAR:
         case sbs_generic.GET_VAR:
         case sbs_generic.WRITE_VAR:
              int     idx,       // array index for key_i
                      num_byte,  // num bytes to print
                      idxStart,  // array index for keyStart
                      keyStart,  // first offset to print 
                      key_p,     // previous checked offset 
                      key_i;     // current offset to check
             
                  str = "\n" + item. getStringVar(sbs_generic.V_STR_KEYWORD)   + "\n"  +
                               item. getStringVar(sbs_generic.V_STR_CTYPENAME) + "\n";
                  
                  key_p = keyStart = keyArray[0].intValue();
                  for(idx=0, idxStart = 0; true; idx++) {
                      key_i = keyArray[idx].intValue();
                      num_byte   = key_p - keyStart + 1; 
              
                      if (((key_i - key_p) > 1) ||            // not consecutive bytes
                            num_byte >= NUM_BLOCK_BYTES) {     // too many consecutive bytes
                          out.write(str + keyStart + "\n" + num_byte); 
                          if (item_Type != sbs_generic.GET_VAR)
                              dumpLisBytes (item, out, keyArray, idxStart, keyStart, num_byte, false);
                          idxStart = idx; 
                          keyStart = key_i;
                      }
                      if (idx == (keyArray.length - 1)) {
                          num_byte   = key_i - keyStart + 1; 
                          out.write(str + keyStart + "\n" + num_byte); 
                          if (item_Type != sbs_generic.GET_VAR)
                               dumpLisBytes (item, out, keyArray, idxStart, keyStart, num_byte, false);
                          break;
                      }
                      key_p = key_i;
                  }
              break;
    
         default:
             break;
         }
       }
    } catch (IOException e) {System.out.println("Unable to export to LIS");}
  } 
} // end class StructDataManager



