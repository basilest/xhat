/*
 * Created on Dec 18, 2011
 * @author Basile Stefano
 */
package common;

/**
 * @author Basile Stefano
 *
 */
public class DBFileManager {

    //######################################################
    private String              BinaryDumpFileName;
    private String              BinaryFileDir;
    Environ environ;
    //######################################################
    public DBFileManager  (Environ environ)
    {
        this.environ = environ;
        BinaryDumpFileName = "BinaryStructDump.bin";
        BinaryFileDir      = "./temp/";
    }
    //######################################################
    private String FullFileName(String FileName)    {
            return (BinaryFileDir + FileName);
    }
//    //######################################################
//    public void ReadBlobFile(Blob blob, String FileName)    {
//        int b;
//        long len;
//        BufferedOutputStream fileOut; 
//        
//        
//        try {
//          fileOut = new BufferedOutputStream 
//                   (new FileOutputStream (FullFileName(FileName)));
//          len = blob.length();
//                  
//          if (len < 500) { 
//              byte[] bytes = blob.getBytes(1, (int)len);
//              fileOut.write (bytes, 0, (int)len);
//          }
//          else
//          {
//            InputStream is = blob.getBinaryStream();
//            while ((b = is.read()) != -1) {
//                fileOut.write(b);   
//            }
//          }
//          fileOut.close();
//        } catch (Exception e) { e.printStackTrace();}
//    }
    //######################################################
}
