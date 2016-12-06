/*
 * Created on 7-feb-2012
 *
 * 
 * @author  Basile Stefano
 */
package common;
import java.io.*;
import java.security.*;
import java.util.zip.*;

/**
 * @author  Basile Stefano
 */
//#####################################
public class ZipUnzip {

    //#####################################
      public final static String GZIP_SUFFIX = ".gz";

    //#####################################
      public static void Zip (String FullFileName) {

          try {
            FileInputStream  in   = new FileInputStream (FullFileName);      
            FileOutputStream fout = new FileOutputStream(FullFileName + GZIP_SUFFIX);
            GZIPOutputStream out  = new GZIPOutputStream(fout);
            byte[] buffer = new byte[256];
            while (true) {
              int bytesRead = in.read(buffer);
              if (bytesRead == -1) break;
              out.write(buffer, 0, bytesRead);
            }        
            in.close();
            out.close();
          }
          catch (IOException e) {
            System.err.println(e);     
          }
     }
    //#####################################
      public static String DigestHexFormat (byte [] digest) {
          String HexString = "";
          int b;
          
          for (int i = 0; i < digest.length; i++) {
              b = 0xFF & digest[i];
              HexString += Integer.toHexString(b);
          }
          return HexString;
     }
    //#####################################
      public static byte [] ZipWithDigest (String FullFileName) {

          try {
            FileInputStream  in       = new FileInputStream (FullFileName);      
            FileOutputStream fos      = new FileOutputStream(FullFileName + GZIP_SUFFIX);
            GZIPOutputStream gzipout  = new GZIPOutputStream(fos);
            MessageDigest      md  = MessageDigest.getInstance("MD5");
            DigestOutputStream dos = new DigestOutputStream(gzipout, md);
            byte[] buffer = new byte[256];
            
            dos.on(true);
            while (true) {
              int bytesRead = in.read(buffer);
              if (bytesRead == -1) break;
              gzipout.write(buffer, 0, bytesRead);
              md.update(buffer, 0, bytesRead);
            }        
            in.close();
            gzipout.close();
            dos.on(false);
            return md.digest();
          }
          catch (IOException e)              {System.err.println(e);}
          catch (NoSuchAlgorithmException e) {System.err.println(e);}
          return null;
     }
   //#####################################
      public static void Unzip(String FullFileName) {

          if (FullFileName.toLowerCase().endsWith(GZIP_SUFFIX)) {
            try {
              FileInputStream  fis     = new FileInputStream (FullFileName);      
              GZIPInputStream  gzipin  = new GZIPInputStream (fis);
              FileOutputStream fos     = new FileOutputStream(
                                             FullFileName.substring(0, FullFileName.length()-3));
              byte[] buffer = new byte[256];
              while (true) {
                int bytesRead = gzipin.read(buffer);
                if (bytesRead == -1) break;
                fos.write(buffer, 0, bytesRead);
              }
              fis.close();
              fos.close();
            }
            catch (IOException e) {
              System.err.println(e);     
            }
          }
          else {
            System.err.println(FullFileName + " does not appear to be a gzipped file.");
          }
        }
} // end class ZipUnzip

