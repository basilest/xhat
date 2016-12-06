/*
 * Created on Jan 14, 2012
 *
 * 
 * @author  Basile Stefano
 */
package common;

import java.io.*;
import java.util.regex.*;
/**
 * @author  Basile Stefano
 */
public class ParseEnvFile {
    
    public final static String ENVFILENAME      = ".xhatrc";
    public final static String T_STR_TESTNUM    = "TESTNUM:";

    public final static int    T_ID_UNKNOWN     = 0;
    public final static int    T_ID_TESTNUM     = 1;
    
    Pattern     pattFIELD;
    Pattern     pattINT;
    Matcher     m;
    
    BufferedReader   BuffFile;
    String           read_line;
    int              num_line;

    int     T_F_lev;
    int     T_F_type;
    String  T_F_Name;
    String  T_F_tail;
    
    Environ environ;
    //######################################################
     public  ParseEnvFile(Environ environ)
     {
         this.environ = environ;
         pattFIELD    = Pattern.compile("^([^:]+:)(.*)");
         pattINT      = Pattern.compile("(\\d+)");
     }
    //######################################################
    private void OpenResFile() throws IOException 
    {
        try {
            BuffFile  = new BufferedReader(new FileReader(new File(ENVFILENAME)));
            num_line = 0;

        } catch (FileNotFoundException e) {
                 System.out.println("Unable to Open Res File " + ENVFILENAME);
                 throw new IOException();
          }
    }
    //######################################################
    private int  testLine () {

         String  field     = "";
         int     LineType  = T_ID_UNKNOWN;

         T_F_tail  = "";

         m = pattFIELD.matcher(read_line);
         if ( m.find())      // found 'xxxx:...'
         {
              field     = m.group(1);
              T_F_tail  = m.group(2);

              do {
                 if (field.equals(T_STR_TESTNUM)){
                 LineType = T_ID_TESTNUM; 
                 break;
                 }                     
             } while (false);
         }
         return LineType;
      }
    
    //######################################################
    private int  getIntFieldValue(String str) {

         int val = 0;

         m = pattINT.matcher(str);
         if ( m.find())      // found '123'
         {
              val   = Integer.parseInt(m.group(1));
         }
         return val;
      }
   //######################################################
   public void loadRes () {

       int LineType ;
       try {

       OpenResFile();
       
       while ((read_line = BuffFile.readLine()) != null) {
           num_line++;
           LineType = testLine();
    
           switch(LineType) {
           case T_ID_TESTNUM:
                environ.setIntVar(Environ.V_INT_TESTNUM, getIntFieldValue(T_F_tail));
                break;
           default : 
                break;
           }
         } // end while cycling

        BuffFile.close();
        
      } catch (IOException e) {
               System.out.println("Unable to parse Res File at line "+num_line);
               }
   }
   //######################################################
   public void ExportRes () {

       int LineType ;
       try {

           FileWriter out = new FileWriter(ENVFILENAME);
           out.write(T_STR_TESTNUM + environ.getIntVar(Environ.V_INT_TESTNUM) + "\n");
           out.close();
      } catch (IOException e) {
               System.out.println("Unable to Write Res File");
               }
   }
}
