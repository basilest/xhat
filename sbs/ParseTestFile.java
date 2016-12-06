/*
 * Created on Jan 14, 2012
 *
 * 
 * @author  Basile Stefano
 */
package sbs;

import java.util.*;

import java.io.*;
import java.util.regex.*;
import common.*;
/**
 * @author  Basile Stefano
 */
public class ParseTestFile {
    
//    public final static String T_STR_TESTUNAME   = "TESTUNAME:";
    public final static String T_STR_TESTITEMNUM = "TSTITMNUM:";
    public final static String T_STR_TESTTASK    = "TSTTASK:";
    public final static String T_STR_TESTINITFUN = "TSTINITFUN:";
    public final static String T_STR_TESTINITLEV = "TSTINITLEV:";

    public final static String T_STR_TASK        = "TASK:";
    public final static String T_STR_TSKNAME     = "TSKNAME:";
    public final static String T_STR_TSKPROC     = "TSKPROC:";
    public final static String T_STR_TSKMBX      = "TSKMBX:";
    public final static String T_STR_TSKMBXNAME  = "TSKMBXNAME:";
    public final static String T_STR_TSKRECTX    = "TSKRECTX:";
    public final static String T_STR_TSKRECTY    = "TSKRECTY:";
    public final static String T_STR_TSKMAIN     = "TSKMAIN:";
    public final static String T_STR_TSKEND      = "TSKEND.";

    public final static String T_STR_ITEM        = "ITEM:";
    public final static String T_STR_ITMTYPE     = "ITMTYPE:";
    public final static String T_STR_ITMTSK1     = "ITMTSK1:";
    public final static String T_STR_ITMTSK2     = "ITMTSK2:";
    public final static String T_STR_ITMUNAME    = "ITMUNAME:";
    public final static String T_STR_ITMCNAME    = "ITMCNAME:";
    public final static String T_STR_ITMSIZE     = "ITMSIZE:";
    public final static String T_STR_ITMY        = "ITMY:";
    public final static String T_STR_ITMX1       = "ITMX1:";
    public final static String T_STR_ITMX2       = "ITMX2:";
    public final static String T_STR_ITMWAIT     = "ITMWAIT:";
    public final static String T_STR_ITMDATKEY   = "ITMDATKEY:";
    public final static String T_STR_ITMEND      = "ITMEND.";


    public final static int T_ID_UNKNOWN       =   0;

//    public final static int T_ID_TESTUNAME   =   1;
    public final static int T_ID_TESTITEMNUM   =   2;
    public final static int T_ID_TESTTASK      =   3;
    public final static int T_ID_TESTINITFUN   =   4;
    public final static int T_ID_TESTINITLEV   =   5;
    
    public final static int T_ID_TASK          =  10;
    public final static int T_ID_TSKNAME       =  20;
    public final static int T_ID_TSKPROC       =  30;
    public final static int T_ID_TSKMBX        =  40;
    public final static int T_ID_TSKMBXNAME    =  50;
    public final static int T_ID_TSKRECTX      =  60;
    public final static int T_ID_TSKRECTY      =  70;
    public final static int T_ID_TSKMAIN       =  80;
    public final static int T_ID_TSKEND        =  90;

    public final static int T_ID_ITEM          = 100;
    public final static int T_ID_ITMTYPE       = 101;
    public final static int T_ID_ITMTSK1       = 102;
    public final static int T_ID_ITMTSK2       = 103;
    public final static int T_ID_ITMUNAME      = 104;
    public final static int T_ID_ITMCNAME      = 105;
    public final static int T_ID_ITMSIZE       = 106;
    public final static int T_ID_ITMY          = 107;
    public final static int T_ID_ITMX1         = 108;
    public final static int T_ID_ITMX2         = 109;
    public final static int T_ID_ITMWAIT       = 110;
    public final static int T_ID_ITMDATKEY     = 111;
    public final static int T_ID_ITMEND        = 112;

    Pattern     pattFIELD;
    Pattern     pattEND;
    Matcher     m;
    
    BufferedReader   TestBuffFile;
    String           read_line;
    int              num_line;

    int     T_F_lev;
    int     T_F_type;
    String  T_F_Name;
    String  T_F_tail;
    
    Vector  LevelNodes;   // stores the Current parsing Node for each Level
    Environ environ;
    //######################################################
     public  ParseTestFile(Environ environ)
     {
         this.environ = environ;
         pattFIELD  = Pattern.compile("^([^:]+:)(.*)");
         LevelNodes = new Vector(5);  // 5 levels for Tree depth.  
     }
    //######################################################
    private void OpenDefFile( String fileName) 
    {
        try {
              System.out.println("Opening:["+fileName+"]");

            TestBuffFile  = new BufferedReader(new FileReader(new File(fileName)));
            num_line = 0;

        } catch (FileNotFoundException e) {
                 System.out.println("Unable to Open Test File " + fileName);
          }
    }
    //######################################################
    private int  testLine () {

         String  field     = "";
         int     LineType  = T_ID_UNKNOWN;

         T_F_tail  = "";
         
//        System.out.println("Reading line "+num_line + ":" + read_line);

         m = pattFIELD.matcher(read_line);
         if ( m.find())      // found 'xxxx:...'
         {
              field     = m.group(1);
              T_F_tail  = m.group(2);

              do {
                 if (field.equals(T_STR_TESTITEMNUM)){
                 LineType = T_ID_TESTITEMNUM; 
                 break;
                 }
                 if (field.equals(T_STR_TESTTASK)){
                 LineType = T_ID_TESTTASK; 
                 break;
                 }
                 if (field.equals(T_STR_TESTINITFUN)){
                 LineType = T_ID_TESTINITFUN; 
                 break;
                 }
                 if (field.equals(T_STR_TESTINITLEV)){
                 LineType = T_ID_TESTINITLEV; 
                 break;
                 }

                 if (field.equals(T_STR_TSKNAME)){
                 LineType = T_ID_TSKNAME; 
                 break;
                 }                     
                 if (field.equals(T_STR_TSKPROC)){
                 LineType = T_ID_TSKPROC; 
                 break;
                 }                    
                 if (field.equals(T_STR_TSKMBX)){
                 LineType = T_ID_TSKMBX; 
                 break;
                 }                    
                 if (field.equals(T_STR_TSKMBXNAME)){
                 LineType = T_ID_TSKMBXNAME; 
                 break;
                 }                    
                 if (field.equals(T_STR_TSKMAIN)){
                 LineType = T_ID_TSKMAIN; 
                 break;
                 }                    
                 if (field.equals(T_STR_TSKRECTX)){
                 LineType = T_ID_TSKRECTX; 
                 break;
                 }                    
                 if (field.equals(T_STR_TSKRECTY)){
                 LineType = T_ID_TSKRECTY; 
                 break;
                 }               
                 //________________________________T_STR_ITEM
                 if (field.equals(T_STR_ITMTYPE)){
                 LineType = T_ID_ITMTYPE; 
                 break;
                 }               
                 if (field.equals(T_STR_ITMTSK1)){
                 LineType = T_ID_ITMTSK1; 
                 break;
                 }               
                 if (field.equals(T_STR_ITMTSK2)){
                 LineType = T_ID_ITMTSK2; 
                 break;
                 }               
                 if (field.equals(T_STR_ITMY)){
                 LineType = T_ID_ITMY; 
                 break;
                 }               
                 if (field.equals(T_STR_ITMX1)){
                 LineType = T_ID_ITMX1; 
                 break;
                 }               
                 if (field.equals(T_STR_ITMX2)){
                 LineType = T_ID_ITMX2; 
                 break;
                 }               
                 if (field.equals(T_STR_ITMUNAME)){
                 LineType = T_ID_ITMUNAME; 
                 break;
                 }               
                 if (field.equals(T_STR_ITMCNAME)){
                 LineType = T_ID_ITMCNAME; 
                 break;
                 }               
                 if (field.equals(T_STR_ITMSIZE)){
                 LineType = T_ID_ITMSIZE; 
                 break;
                 }               
                 if (field.equals(T_STR_ITMWAIT)){
                 LineType = T_ID_ITMWAIT; 
                 break;
                 }               
                 if (field.equals(T_STR_ITMDATKEY)){
                 LineType = T_ID_ITMDATKEY; 
                 break;
                 }               
             } while (false);
         }
         else { 
             if ( read_line.length() > 0) {
                 if (read_line.startsWith(T_STR_TSKEND))
                  LineType = T_ID_TSKEND;
                 else if  (read_line.startsWith(T_STR_ITMEND))
                  LineType = T_ID_ITMEND; 
             }               
         }
         return LineType;
      }
    
    //######################################################
    private sbs_item NewItem (sbsTest test, int numItem, int type) {

         sbs_item item;
         switch (type) {
         case sbs_generic.MESSAGE:
              item = new sbs_item (environ, new sbsMessage(test, numItem));
              break;
         case sbs_generic.GET_VAR:
              item = new sbs_item (environ, new sbs_get_var(test, numItem));
              break;
         case sbs_generic.CHECK_VAR:
              item = new sbs_item (environ, new sbs_check_var(test, numItem));
              break;
         case sbs_generic.WRITE_VAR:
              item = new sbs_item (environ, new sbs_write_var(test, numItem));
              break;
         case sbs_generic.FUNCTION:
              item = new sbs_item (environ, new sbs_function(test, numItem));
              break;
         case sbs_generic.WAIT:
              item = new sbs_item (environ, new sbs_wait(test, numItem));
              break;
         default:
              item = null;
              break;         
         }
         return item;
      }
    //######################################################
    private int  getIntFieldValue(String str) {

         int val = 0;
         
         try {
               val = Integer.parseInt(str);
        } catch (NumberFormatException e) {
                 System.out.println("Test File line " + num_line + ":" + read_line + " not a number.");
          }
         return val;
      }
   //######################################################
   public void loadTest (sbsTest test, String FullFileName) {

       int LineType ;
       sbsTask       task = null;
       sbs_item      item = null;
       int           numItem = 0;

       try {

       LevelNodes.clear();   
       OpenDefFile(FullFileName);
       
       while ((read_line = TestBuffFile.readLine()) != null) {
           num_line++;
           LineType = testLine();
    
           switch(LineType) {
           case T_ID_TESTITEMNUM:
                test.setItemNum(getIntFieldValue(T_F_tail));
                break;
           case T_ID_TESTTASK:
                test.setInitFunction(sbsTest.INIT_TASK, T_F_tail);
                break;
           case T_ID_TESTINITFUN:
                test.setInitFunction(sbsTest.INIT_FUN,  T_F_tail);
                break;
           case T_ID_TESTINITLEV:
                test.setInitFunction(sbsTest.INIT_LEVEL, T_F_tail);
                break;
           case T_ID_TSKNAME:
                task = new sbsTask (T_F_tail);
                break;
           case T_ID_TSKPROC:
                task.setProcessor(getIntFieldValue(T_F_tail));
                break;
           case T_ID_TSKMBX:
                task.setMailbox(getIntFieldValue(T_F_tail));
                break;
           case T_ID_TSKMBXNAME:
                task.setMailboxName(T_F_tail);
                break;
           case T_ID_TSKRECTX:
                task.set_rectX(getIntFieldValue(T_F_tail));
                break;
           case T_ID_TSKRECTY:
                task.set_rectY(getIntFieldValue(T_F_tail));
                break;
           case T_ID_TSKMAIN:
                task.setMainTask(true);
                break;
           case T_ID_TSKEND:
                test.addNewTask(task);
                break;

               //_______________________________
           case T_ID_ITMTYPE:
                item = NewItem(test, numItem, getIntFieldValue(T_F_tail));
                numItem ++;
                break;
           case T_ID_ITMTSK1:
                item.setTask1(test.getTask_i(getIntFieldValue(T_F_tail)));
                break;
           case T_ID_ITMTSK2:
                item.setTask2(test.getTask_i(getIntFieldValue(T_F_tail)));
                break;
           case T_ID_ITMY:
                item.setIntVar(sbs_generic.V_INT_Y, getIntFieldValue(T_F_tail));
                break;
           case T_ID_ITMX1:
                item.setIntVar(sbs_generic.V_INT_X1, getIntFieldValue(T_F_tail));
                break;
           case T_ID_ITMX2:
                item.setIntVar(sbs_generic.V_INT_X2, getIntFieldValue(T_F_tail));
                break;
           case T_ID_ITMUNAME:
                item.setStringVar(sbs_generic.V_STR_USERNAME, T_F_tail);
                break;
           case T_ID_ITMCNAME:
                item.setStringVar(sbs_generic.V_STR_CTYPENAME, T_F_tail);
                break;
           case T_ID_ITMSIZE:
                item.setIntVar(sbs_generic.V_INT_CTYPESIZE, getIntFieldValue(T_F_tail));
                break;
           case T_ID_ITMWAIT:
                item.setIntVar(sbs_generic.V_INT_TIME, getIntFieldValue(T_F_tail));
                break;
           case T_ID_ITMDATKEY:
                item.setIntVar(sbs_generic.V_INT_DBDATKEY, getIntFieldValue(T_F_tail));
                break;
           case T_ID_ITMEND:
                test.addNewItem(item);
                break;

           default : 
                break;
           }
         } // end while cycling

        TestBuffFile.close();
        
      } catch (IOException e) {
               System.out.println("Unable to parse Test File at line "+num_line);
               }
    
   }
   
}
