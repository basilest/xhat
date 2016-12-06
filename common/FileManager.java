/*
 * Created on Dec 18, 2011
 *
 * 
 * @author  Basile Stefano
 */
package common;

import java.io.*;
    

/**
 * @author   Basile Stefano
 *
 */
//##########################
public class FileManager {
    
    Environ environ;
    
    //##########################
    public FileManager  (Environ environ)
    {
        this.environ = environ;
    }
    //##########################
     static public void CopyFile (String FullFileNameIn, String FullFileNameOut) {
         
         try {
           FileInputStream  in  = new FileInputStream (FullFileNameIn);      
           FileOutputStream out = new FileOutputStream(FullFileNameOut);
           byte[] buffer = new byte[256];
           int    bytesRead;
           
           while (true) {
             bytesRead = in.read(buffer);
             if (bytesRead == -1) break;
             out.write(buffer, 0, bytesRead);
           }        
           in.close();
           out.close();
        } catch (Exception e) {System.out.println("Unable to Copy " + FullFileNameIn + 
                                                             " to " + FullFileNameOut) ;}
     }
//    //##########################
//     public  void CopyItemFiles (sbs_item itemOri, sbs_item itemNew) {
//         CopyFile (itemOri.getStringVar(sbs_generic.V_STR_DATAFILE), 
//                   itemNew.getStringVar(sbs_generic.V_STR_DATAFILE));      
//         CopyFile (itemOri.getStringVar(sbs_generic.V_STR_DESCFILE), 
//                   itemNew.getStringVar(sbs_generic.V_STR_DESCFILE));      
//     }    
}
