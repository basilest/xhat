/*
 * Created on Dec 18, 2011
 *
 * 
 * @author  Basile Stefano
 */
package  sbs;

import java.awt.*;
import java.io.FileWriter;

/**
 * @author   Basile Stefano
 */
//####################
public class sbs_generic {

    public final static int ITEM_MIN_GAP_Y = 10;
    
    public final static String  DESC_FILE = "desc.txt";
    public final static String  DATA_FILE = "data.txt";
    
    public final static int UNKNOWN    =  0;
    public final static int MESSAGE    =  1;
    public final static int GET_VAR    =  2;
    public final static int WRITE_VAR  =  3;
    public final static int CHECK_VAR  =  4;
    public final static int WAIT       =  5;
    public final static int FUNCTION   =  6;

    public final static int V_INT_TYPE      =  1;
    public final static int V_INT_Y         =  2;
    public final static int V_INT_YUPRSV    =  3;
    public final static int V_INT_YDOWNRSV  =  4;
    public final static int V_INT_NUM       =  5;
    public final static int V_INT_DBDSCKEY  =  6;
    public final static int V_INT_DBDATKEY  =  7;
    
    public final static int V_INT_X1        =  8;
    public final static int V_INT_X2        =  9;
    public final static int V_INT_TEXT_H    = 10;
    public final static int V_INT_CTYPESIZE = 11;
    public final static int V_INT_TIME      = 12;
    
    
    
    public final static int V_STR_DESCFILE  =  1;
    public final static int V_STR_DATAFILE  =  2;
    public final static int V_STR_USERNAME  =  3;
    public final static int V_STR_CTYPENAME =  4;
    public final static int V_STR_KEYWORD   =  5;
    
    //####################
    int     Type;
    int     Y;
    int     Y_UpReserved;
    int     Y_DownReserved;

    Font    font;
    Color   color;
    
    String  DescFileName;
    String  DataFileName;
    sbsTest test;
    int     num;
    
    int     DBDesckey;
    int     DBDatakey;
    
    //####################
    public sbs_generic (sbsTest test, int num, int type, int y) {
        this.test      = test;
        this.num       = num;
        Type           = type;
        Y              = y;
        DescFileName   = test.getTestUniqueName() + num + DESC_FILE;
        DataFileName   = test.getTestUniqueName() + num + DATA_FILE;
        DBDesckey      = -1;
        DBDatakey      = -1;
        Y_UpReserved   = 0;
        Y_DownReserved = 0;
        font           = new Font("Helvetica", 1, 12);
        color          = Color.blue;
    }
    
    //####################
    public sbs_generic (sbs_generic ori, int num, int y) {
        this.test      = ori.getTest();
        this.num       = num;
        Type           = ori.getIntVar(sbs_generic.V_INT_TYPE);
        Y              = y;
        DescFileName   = test.getTestUniqueName() + num + DESC_FILE;
        DataFileName   = test.getTestUniqueName() + num + DATA_FILE;
        DBDesckey      = -1;
        DBDatakey      = -1;
        Y_UpReserved   = ori.getIntVar(sbs_generic.V_INT_YUPRSV);;
        Y_DownReserved = ori.getIntVar(sbs_generic.V_INT_YDOWNRSV);;
        font           = new Font("Helvetica", 1, 12);
        color          = ori.getColor();
    }
    //#####################################
    public void setIntVar (int varType, int val) {
        
           switch(varType) {
               
               case V_INT_TYPE:      Type           = val;    break; 
               case V_INT_Y:         Y              = val;    break; 
               case V_INT_YUPRSV:    Y_UpReserved   = val;    break; 
               case V_INT_YDOWNRSV:  Y_DownReserved = val;    break; 
               case V_INT_NUM:       num            = val;    break; 
               case V_INT_DBDSCKEY:  DBDesckey      = val;    break; 
               case V_INT_DBDATKEY:  DBDatakey      = val;    break;
               default : break;
           }
    }
    //#####################################
    public int getIntVar (int varType) {
        
           switch(varType) {
               
               case V_INT_TYPE:      return Type; 
               case V_INT_Y:         return Y; 
               case V_INT_YUPRSV:    return Y_UpReserved; 
               case V_INT_YDOWNRSV:  return Y_DownReserved; 
               case V_INT_NUM:       return num; 
               case V_INT_DBDSCKEY:  return DBDesckey; 
               case V_INT_DBDATKEY:  return DBDatakey;
               default :             return -1;
           }
    }
    //#####################################
    public void setStringVar (int varType, String val) {
           switch(varType) {
               case V_STR_DESCFILE:      DescFileName  = val;    break; 
               case V_STR_DATAFILE:      DataFileName  = val;    break; 
               default : break;
           }
    }
    //#####################################
    public String getStringVar (int varType) {
           switch(varType) {
               case V_STR_DESCFILE:      return DescFileName; 
               case V_STR_DATAFILE:      return DataFileName; 
               default :                 return null;
           }
    }
    //####################
    public sbsTest getTest() {
           return  test;
    }
    //####################
    public void setTest(sbsTest test) {
           this.test = test;
    }
    //#####################################
    public void setColor(Color c){
           color = c;
    }
    //#####################################
    public Color getColor( ){
           return color ;
    }
    //####################
    public void export (FileWriter out) {
       try {
              out.write (ParseTestFile.T_STR_ITMTYPE  + Type + "\n");
              out.write (ParseTestFile.T_STR_ITMY     + Y    + "\n");
        } catch (Exception e) {e.printStackTrace();}  
    }       
    //#####################################
    public void paintItem (Graphics g) {
    }
}  // class sbs-item

