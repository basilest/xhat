/*
 * Created on Jan 29, 2012
 * @author Basile Stefano
 */
package database;

import java.io.*;
import java.sql.*;
import common.Environ;
import common.MyTableModel;
import common.ZipUnzip;
import java.util.zip.*;

import sbs.*;
/**
 * @author Basile Stefano
 *
 */

//final static String S_Ins_TEST = 
//                 "INSERT INTO TESTS (testName, author, area, date, time, testDesc, testComment) " +
//                 "VALUES(?,,?,select CURDATE(),select CURTIME(),?,?)";
//final static String S_Sel_ALL_TEST   = "SELECT tkey, testName, author, date from TESTS";
//final static String S_Sel_MSG_DESC   = "SELECT * from STRUCT_DESC where size=? AND structName=? AND FileCRC=?";
//final static String S_Sel_ALL_MSG    = "SELECT tkey, UserName from STRUCT_DATA where DescTableKey=?";
//final static String S_Sel_MSG_DATA   = "SELECT dataFile from STRUCT_DATA where tkey=?";
//PreparedStatement P_Sel_ALL_TEST;
//PreparedStatement P_Sel_MSG_DESC;
//PreparedStatement P_Sel_ALL_MSG;
//PreparedStatement P_Sel_MSG_DATA;
//P_Sel_ALL_TEST    = con.prepareStatement(S_Sel_ALL_TEST);
//P_Sel_MSG_DESC    = con.prepareStatement(S_Sel_MSG_DESC);;
//P_Sel_ALL_MSG     = con.prepareStatement(S_Sel_ALL_MSG);;
//P_Sel_MSG_DATA    = con.prepareStatement(S_Sel_MSG_DATA);;

public class DataBase {

  private Connection con;
  
  
  final static String S_Ins_STRUCT_DESC ="INSERT INTO STRUCT_DESC (size, StructName, FileCRC, descFile) VALUES(?,?,?,?)";
  final static String S_Ins_STRUCT_DATA ="INSERT INTO STRUCT_DATA (DescTableKey, UserName, FileCRC, dataFile) VALUES(?,?,?,?)";
  final static String S_Ins_TEST ="INSERT INTO TESTS (testName,author,area,Fsh,date,time,testDesc,testComment,keyConfig) VALUES(?,?,?,?,?,?,?,?,?)";
  final static String S_Ins_CONFIG ="INSERT INTO CONFIG (ConfigName,date,author,FileCRC,File) VALUES(?,?,?,?,?)";
  final static String S_Upd_TEST ="UPDATE TESTS SET testName=?,author=?,area=?,Fsh=?,date=?,time=?,testDesc=?,testComment=?,keyConfig=? WHERE tkey=?";
  final static String S_Sel_STRUCT_DATA     ="SELECT DescTableKey, dataFile from STRUCT_DATA where tkey=?";
  final static String S_Sel_STRUCT_DESC     ="SELECT descFile from STRUCT_DESC where tkey=?";
  final static String S_Sel_STRUCT_DSCEXIST ="SELECT tkey from STRUCT_DESC where size=? AND structName=? AND FileCRC=?";
  final static String S_Sel_STRUCT_DATEXIST ="SELECT tkey from STRUCT_DATA where DescTableKey=? AND UserName=? AND FileCRC=?";
  final static String S_Sel_CONFIG_EXIST    ="SELECT tkey from CONFIG where ConfigName=? AND FileCRC=?";
  final static String S_Sel_TEST_FILES  = "SELECT testName, Fsh, testDesc, testComment, keyConfig from TESTS where tkey=?";
  final static String S_Sel_CONFIG_FILE = "SELECT File from CONFIG where tkey=?";
  final static String S_Sel_COUNT_TEST  = "SELECT COUNT(*) FROM TESTS;";
  final static String S_Get_USER        = "SELECT substring_index(USER(),\"@\",1)";
  final static String S_Get_DATE        = "SELECT CURDATE()";
  final static String S_Get_CURDTIME    = "SELECT CURTIME()";
 
  PreparedStatement P_Ins_STRUCT_DESC;
  PreparedStatement P_Ins_STRUCT_DATA;
  PreparedStatement P_Ins_TEST;
  PreparedStatement P_Ins_CONFIG;
  PreparedStatement P_Upd_TEST;
  PreparedStatement P_Sel_STRUCT_DATA;
  PreparedStatement P_Sel_STRUCT_DESC;
  PreparedStatement P_Sel_TEST_FILES;
  PreparedStatement P_Sel_CONFIG_FILE;
  PreparedStatement P_Sel_STRUCT_DSCEXIST;
  PreparedStatement P_Sel_STRUCT_DATEXIST;
  PreparedStatement P_Sel_CONFIG_EXIST;
  PreparedStatement P_Sel_COUNT_TEST;
  PreparedStatement P_Get_USER;
  PreparedStatement P_Get_DATE;
  PreparedStatement P_Get_CURDTIME;


  Environ environ;
    
    //####################
    public DataBase (Environ environ) {
           this.environ = environ;
    }
    //####################
    public boolean Connect(String url, String user, String password) {
        boolean connected = false;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, user, password);
            
            P_Ins_STRUCT_DESC = con.prepareStatement(S_Ins_STRUCT_DESC, Statement.RETURN_GENERATED_KEYS);
            P_Ins_STRUCT_DATA = con.prepareStatement(S_Ins_STRUCT_DATA, Statement.RETURN_GENERATED_KEYS);
            P_Ins_TEST        = con.prepareStatement(S_Ins_TEST,        Statement.RETURN_GENERATED_KEYS);
            P_Ins_CONFIG      = con.prepareStatement(S_Ins_CONFIG,      Statement.RETURN_GENERATED_KEYS);
            P_Upd_TEST        = con.prepareStatement(S_Upd_TEST);
            
            P_Sel_TEST_FILES      = con.prepareStatement(S_Sel_TEST_FILES);
            P_Sel_CONFIG_FILE     = con.prepareStatement(S_Sel_CONFIG_FILE);
            P_Sel_COUNT_TEST      = con.prepareStatement(S_Sel_COUNT_TEST);
            P_Sel_STRUCT_DATA     = con.prepareStatement(S_Sel_STRUCT_DATA);
            P_Sel_STRUCT_DESC     = con.prepareStatement(S_Sel_STRUCT_DESC);
            P_Sel_STRUCT_DSCEXIST = con.prepareStatement(S_Sel_STRUCT_DSCEXIST);
            P_Sel_STRUCT_DATEXIST = con.prepareStatement(S_Sel_STRUCT_DATEXIST);
            P_Sel_CONFIG_EXIST    = con.prepareStatement(S_Sel_CONFIG_EXIST);
            
            P_Get_USER     = con.prepareStatement(S_Get_USER);
            P_Get_DATE     = con.prepareStatement(S_Get_DATE);
            P_Get_CURDTIME = con.prepareStatement(S_Get_CURDTIME);
            
            connected = true;
    
        } catch (Exception ex) {System.out.println("Unable to connect to database");}
        return connected;
    }
    //######################################################
    static private void ReadBlobFile(Blob blob, String FullFileName)    {
        int b;
        long len;
        try {
            InputStream      is  = blob.getBinaryStream();
            FileOutputStream fos = new FileOutputStream(FullFileName);
            byte[] buffer  = new byte[256];
            
            while (true) {
              int bytesRead = is.read(buffer);
              if (bytesRead == -1) break;
              fos.write(buffer, 0, bytesRead);
            }  
            fos.close();
        } catch (Exception e) { e.printStackTrace();}
    }
    //######################################################
    static private void ReadBlobZipFile(Blob blob, String FullFileName)    {
        int b;
        long len;
        try {
            InputStream      is      = blob.getBinaryStream();
            GZIPInputStream  gzipin  = new GZIPInputStream (is);
            FileOutputStream fos     = new FileOutputStream(FullFileName);
            byte[] buffer  = new byte[256];
            
            while (true) {
              int bytesRead = gzipin.read(buffer);
              if (bytesRead == -1) break;
              fos.write(buffer, 0, bytesRead);
            }  
            fos.close();
        } catch (Exception e) { e.printStackTrace();}
    }
    //####################
    public void DBquery2JTable (MyTableModel Tmodel, String SqlCmd)
    {
        int i;
        Object []          objects;
        try {
              Statement stmt    = con.createStatement();
              ResultSet rs      = stmt.executeQuery(SqlCmd);
              int       NumCol  = Tmodel.getColumnCount();
              while(rs.next()){ 
                    objects = new Object[NumCol]; 
                    for(i=0; i < NumCol; i++){ 
                        objects[i]=rs.getObject(i+1); 
                    } 
                    Tmodel.addRow(objects); 
              } 
        } catch (Exception ex) {System.out.println("Unable to execute " + SqlCmd);}
    }
    //####################
    public String getUser( )
    {
        try {
             ResultSet rs = P_Get_USER.executeQuery();
             if (rs.next()) 
                 return rs.getString(1);
        } catch (Exception ex) {System.out.println("Unable to get User");}
        return "";
    }
    //####################
    public  Date getDate( )
    {
        Date d = new java.sql.Date(0);
        try {
             ResultSet rs = P_Get_DATE.executeQuery();
             if (rs.next()) 
                 d = rs.getDate (1);
        } catch (Exception ex) {System.out.println("Unable to get Date");}
        return d;
    }
    //####################
    public  Time getTime( )
    {
        Time t = new java.sql.Time(0);
        try {
             ResultSet rs = P_Get_CURDTIME.executeQuery();
             if (rs.next()) 
                 t = rs.getTime  (1);
        } catch (Exception ex) {System.out.println("Unable to get Time");}
        return t;
    }
    //####################
    public int getNumTest( )
    {
        int numTest = -1;
        try {
             ResultSet rs = P_Sel_COUNT_TEST.executeQuery();
             if (rs.next()) 
                 numTest = rs.getInt(1);
        } catch (Exception ex) {System.out.println("Unable to get Number of Tests");}
        return numTest;
    }
    //####################
    public void getItemData( sbs_item item ) {
        ResultSet rs;
        Blob blob;
        int  DescKey;
        
        try { // "SELECT DescTableKey, dataFile from STRUCT_DATA where tkey=?";
            P_Sel_STRUCT_DATA.setInt (1, item.getIntVar(sbs_generic.V_INT_DBDATKEY));
            rs = P_Sel_STRUCT_DATA.executeQuery();
            
            if (rs.next()) 
            {
              DescKey = rs.getInt(1);
              blob    = rs.getBlob(2);
              ReadBlobZipFile(blob, 
                              environ.getTempFullFileName(
                                      item.getStringVar(sbs_generic.V_STR_DATAFILE)));
              rs.close();

              item.setIntVar(sbs_generic.V_INT_DBDSCKEY, DescKey);
              //"SELECT descFile from STRUCT_DESC where tkey=?";
              P_Sel_STRUCT_DESC.setInt (1, DescKey);
              rs = P_Sel_STRUCT_DESC.executeQuery();
            
              if (rs.next()) 
              {
                blob = rs.getBlob(1);
                ReadBlobZipFile(blob, 
                                environ.getTempFullFileName(
                                        item.getStringVar(sbs_generic.V_STR_DESCFILE)));
                rs.close();
              }
            }
        }  catch (Exception e) {System.out.println("Unable to Get Item Data from DB");}
    }
    //####################
    public void InsertStructDesc (sbs_item item) {
        
        ResultSet rs;
        int       key = -1;
            
        try {
            String  FullFileName = environ.getTempFullFileName(
                                          item.getStringVar(sbs_generic.V_STR_DESCFILE));
            byte [] digest       = ZipUnzip.ZipWithDigest(FullFileName);
            String  FileCRC      = ZipUnzip.DigestHexFormat(digest);
            //SELECT tkey from STRUCT_DESC where size=? AND structName=? AND FileCRC=?
            P_Sel_STRUCT_DSCEXIST.setInt   (1, item.getIntVar(sbs_generic.V_INT_CTYPESIZE));
            P_Sel_STRUCT_DSCEXIST.setString(2, item.getStringVar(sbs_generic.V_STR_CTYPENAME));
            P_Sel_STRUCT_DSCEXIST.setString(3, FileCRC);
            
            System.out.println("Testing size"+item.getIntVar(sbs_generic.V_INT_CTYPESIZE)+
                               " StructName"+item.getStringVar(sbs_generic.V_STR_CTYPENAME)+" CRC"+FileCRC);
            rs = P_Sel_STRUCT_DSCEXIST.executeQuery();
            if (rs.next()) 
                key = rs.getInt(1);
            else {
                rs.close();
                File    file         = new File(FullFileName + ZipUnzip.GZIP_SUFFIX);
                FileInputStream is   = new FileInputStream(file);
                
                //"INSERT INTO STRUCT_DESC (size, StructName, FileCRC, descFile) VALUES(?,?,?,?)
                P_Ins_STRUCT_DESC.setInt         (1, item.getIntVar   (sbs_generic.V_INT_CTYPESIZE));
                P_Ins_STRUCT_DESC.setString      (2, item.getStringVar(sbs_generic.V_STR_CTYPENAME));
                P_Ins_STRUCT_DESC.setString      (3, FileCRC);
                P_Ins_STRUCT_DESC.setBinaryStream(4, is, (int)file.length());
    
                P_Ins_STRUCT_DESC.executeUpdate();
    
                rs = P_Ins_STRUCT_DESC.getGeneratedKeys();
                if ( rs.next()) 
                     key = rs.getInt(1);
                is.close();
            }
            rs.close();
            item.setIntVar(sbs_generic.V_INT_DBDSCKEY, key);

        }  catch (Exception e) { System.out.println("Unable to Update Desc Table"); }
    }
    //####################
        public void  InsertStructData( sbs_item item) {
        
        ResultSet rs;
        int       key = -1;

        try {
            String  FullFileName = environ.getTempFullFileName(
                                          item.getStringVar(sbs_generic.V_STR_DATAFILE));
            byte [] digest       = ZipUnzip.ZipWithDigest(FullFileName);
            String  FileCRC      = ZipUnzip.DigestHexFormat(digest);
            
            //"SELECT tkey from STRUCT_DATA where DescTableKey=? AND UserName=? AND FileCRC=?"
            P_Sel_STRUCT_DATEXIST.setInt   (1, item.getIntVar(sbs_generic.V_INT_DBDSCKEY));
            P_Sel_STRUCT_DATEXIST.setString(2, item.getStringVar(sbs_generic.V_STR_USERNAME));
            P_Sel_STRUCT_DATEXIST.setString(3, FileCRC);
            
            rs = P_Sel_STRUCT_DATEXIST.executeQuery();
            if (rs.next()) 
                key = rs.getInt(1);
            else {
                rs.close();
                File    file         = new File(FullFileName + ZipUnzip.GZIP_SUFFIX);
                FileInputStream fis  = new FileInputStream(file);
                
                //"INSERT INTO STRUCT_DATA (DescTableKey, UserName, FileCRC, dataFile) VALUES(?,?,?,?)
                P_Ins_STRUCT_DATA.setInt         (1, item.getIntVar(sbs_generic.V_INT_DBDSCKEY));
                P_Ins_STRUCT_DATA.setString      (2, item.getStringVar(sbs_generic.V_STR_USERNAME));
                P_Ins_STRUCT_DATA.setString      (3, FileCRC);
                P_Ins_STRUCT_DATA.setBinaryStream(4, fis, (int)file.length());
                
                P_Ins_STRUCT_DATA.executeUpdate();

                rs = P_Ins_STRUCT_DATA.getGeneratedKeys();
                if ( rs.next()) 
                    key = rs.getInt(1);
                fis.close();
            }
            rs.close();
            item.setIntVar(sbs_generic.V_INT_DBDATKEY, key);

        }  catch (Exception e) { System.out.println("Unable to Update Data Table"); }
    }
    //####################
    public void getTestData( sbsTest test) {
        ResultSet rs;
        Blob      blob;
        int       DescKey;
        
        try {
           // "SELECT testName, Fsh, testDesc, testComment from TESTS where tkey=?";
            P_Sel_TEST_FILES.setInt (1, test.getDBKey());
            rs = P_Sel_TEST_FILES.executeQuery();
            
            if (rs.next()) 
            {
              test.setTestUserName(rs.getString(1));
              test.setFeature(rs.getString(2));
              blob = rs.getBlob(3);
              ReadBlobZipFile(blob, environ.getTempFullFileName(test.getDescFileName()));
              blob = rs.getBlob(4);
              ReadBlobZipFile(blob, environ.getTempFullFileName(test.getCommentFileName()));
              test.setDBConfKey(rs.getInt(5));
              rs.close();
              
              getConfigData(test);
            }
        }  catch (Exception e) {System.out.println("Unable to Get Test Data from DB");}
    }
    //####################
    public void  UpdateTest ( sbsTest test) {
        
        try {
            
            InsertConfig(test);

            
            String  TestFullFileName = environ.getTempFullFileName(test.getDescFileName());
            String  CmtFullFileName  = environ.getTempFullFileName(test.getCommentFileName());
            ZipUnzip.Zip(TestFullFileName);
            ZipUnzip.Zip(CmtFullFileName);
            File    fileTest      = new File(TestFullFileName + ZipUnzip.GZIP_SUFFIX);
            File    fileComment   = new File(CmtFullFileName  + ZipUnzip.GZIP_SUFFIX);
            FileInputStream ist   = new FileInputStream(fileTest);
            FileInputStream isc   = new FileInputStream(fileComment);
            
            //"UPDATE TESTS SET testName=?,author=?,area=?,Fsh=?,date=?,time=?,testDesc=?,testComment=?,keyConfig=? WHERE tkey=?";
            P_Upd_TEST.setString      (1, test.getTestUserName());
            P_Upd_TEST.setString      (2, getUser());
            P_Upd_TEST.setInt         (3, test.getTestArea());
            P_Upd_TEST.setString      (4, test.getFeature());
            P_Upd_TEST.setDate        (5, getDate());
            P_Upd_TEST.setTime        (6, getTime());
            P_Upd_TEST.setBinaryStream(7, ist, (int)fileTest.length());
            P_Upd_TEST.setBinaryStream(8, isc, (int)fileComment.length());
            P_Upd_TEST.setInt         (9, test.getDBConfKey());
            P_Upd_TEST.setInt         (10,test.getDBKey());

            P_Upd_TEST.executeUpdate();

            ist.close();
            isc.close();

            System.out.println("Test Updated");
        }  catch (Exception e) { System.out.println("Unable to Update Test"); }
    }
    //####################
    public void  InsertTest (sbsTest test) {
        
        ResultSet       rs;
        try {
            
            InsertConfig(test);
            
            String  TestFullFileName = environ.getTempFullFileName(test.getDescFileName());
            String  CmtFullFileName  = environ.getTempFullFileName(test.getCommentFileName());
            ZipUnzip.Zip(TestFullFileName);
            ZipUnzip.Zip(CmtFullFileName);
            File    fileTest      = new File(TestFullFileName + ZipUnzip.GZIP_SUFFIX);
            File    fileComment   = new File(CmtFullFileName  + ZipUnzip.GZIP_SUFFIX);
            FileInputStream ist   = new FileInputStream(fileTest);
            FileInputStream isc   = new FileInputStream(fileComment);
            
            //"INSERT INTO TESTS (testName,author,area,Fsh,date,time,testDesc,testComment,keyConfig) VALUES(?,?,?,?,?,?,?,?,?)"
            P_Ins_TEST.setString      (1, test.getTestUserName());
            P_Ins_TEST.setString      (2, getUser());
            P_Ins_TEST.setInt         (3, test.getTestArea());
            P_Ins_TEST.setString      (4, test.getFeature());
            P_Ins_TEST.setDate        (5, getDate());
            P_Ins_TEST.setTime        (6, getTime());
            P_Ins_TEST.setBinaryStream(7, ist, (int)fileTest.length());
            P_Ins_TEST.setBinaryStream(8, isc, (int)fileComment.length());
            P_Ins_TEST.setInt         (9, test.getDBConfKey());

            P_Ins_TEST.executeUpdate();

            rs = P_Ins_TEST.getGeneratedKeys();
            if ( rs.next()) 
                 test.setDBKey(rs.getInt(1));

            rs.close();
            ist.close();
            isc.close();

            System.out.println("Test Saved");
        }  catch (Exception e) { System.out.println("Unable to Insert Test"); }
    }
    //####################
    private void getConfigData( sbsTest test) {
        ResultSet rs;
        Blob      blob;
        
        try {
            // "SELECT File from CONFIG where tkey=?";
            P_Sel_CONFIG_FILE.setInt (1, test.getDBConfKey());
            rs = P_Sel_CONFIG_FILE.executeQuery();
            
            if (rs.next()) 
            {
              blob = rs.getBlob(1);
              ReadBlobZipFile(blob, environ.getTempFullFileName(test.getConfigFileName()));
              rs.close();
            }
        }  catch (Exception e) {System.out.println("Unable to Get Config File from DB");}
    }
    //####################
    public void  InsertConfig (sbsTest test) {
        
        ResultSet rs;
        int       key = -1;

        try {
            
            String  FullFileName = environ.getTempFullFileName(test.getConfigFileName());
            
            byte [] digest       = ZipUnzip.ZipWithDigest(FullFileName);
            String  FileCRC      = ZipUnzip.DigestHexFormat(digest);
            
            //SELECT tkey from CONFIG where ConfigName=? AND FileCRC=?"
            P_Sel_CONFIG_EXIST.setString(1, test.getConfigFileName());
            P_Sel_CONFIG_EXIST.setString(2, FileCRC);
            
            rs = P_Sel_CONFIG_EXIST.executeQuery();
            if (rs.next()) 
                key = rs.getInt(1);
            else {
                rs.close();
                File    file         = new File(FullFileName + ZipUnzip.GZIP_SUFFIX);
                FileInputStream fis  = new FileInputStream(file);
                
                //"INSERT INTO CONFIG (ConfigName,date,author,FileCRC,File) VALUES(?,?,?,?,?)";
                P_Ins_CONFIG.setString    (1, test.getConfigFileName());
                P_Ins_CONFIG.setDate      (2, getDate());
                P_Ins_CONFIG.setString    (3, getUser());
                P_Ins_CONFIG.setString    (4, FileCRC);
                P_Ins_CONFIG.setBinaryStream(5, fis, (int)file.length());
                
                P_Ins_CONFIG.executeUpdate();

                rs = P_Ins_CONFIG.getGeneratedKeys();
                if ( rs.next()) 
                    key = rs.getInt(1);
                fis.close();
            }
            rs.close();
            test.setDBConfKey(key);
            
            System.out.println("Config File Saved");
        }  catch (Exception e) { System.out.println("Unable to Insert Config File"); }
    }
    
    
    
}// class DataBase

