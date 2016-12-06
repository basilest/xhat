/*
 * Created on Dec 18, 2011
 *
 * 
 * @author  Basile Stefano
 */
package  sbs;

import common.Environ;
import common.FileManager;
import java.awt.*;
import java.io.FileWriter;
import java.io.File;

/**
 * @author   Basile Stefano
 */
//####################
public class sbs_item {
    
    protected    sbs_generic  item;
    protected    Environ      environ;
    
    
    //####################
    public sbs_item (Environ environ, sbs_generic item) {
         this.environ = environ;
         this.item    = item;
    }
    //####################
    public sbs_item (sbs_item ori, int num, int y) {
        environ = ori.environ;
        sbs_generic  i = ori.item;
        int type = i.getIntVar(sbs_generic.V_INT_TYPE);
        switch (type) {
        case sbs_generic.MESSAGE:     item = new sbsMessage   ((sbsMessage)   i, num, y);  break;
        case sbs_generic.CHECK_VAR:   item = new sbs_check_var((sbs_check_var)i, num, y);  break;
        case sbs_generic.GET_VAR:     item = new sbs_get_var  ((sbs_get_var)  i, num, y);  break;
        case sbs_generic.WRITE_VAR:   item = new sbs_write_var((sbs_write_var)i, num, y);  break;
        case sbs_generic.WAIT:        item = new sbs_wait     ((sbs_wait)     i, num, y);  break;
        case sbs_generic.FUNCTION:    item = new sbs_function ((sbs_function) i, num, y);  break;
        default:  break;
        }
        if (type != sbs_generic.WAIT) {
            FileManager.CopyFile (environ.getTempFullFileName(
                                          ori.getStringVar(sbs_generic.V_STR_DESCFILE)), 
                                  environ.getTempFullFileName(
                                          item.getStringVar(sbs_generic.V_STR_DESCFILE)));      
            FileManager.CopyFile (environ.getTempFullFileName(
                                          ori.getStringVar(sbs_generic.V_STR_DATAFILE)), 
                                  environ.getTempFullFileName(
                                          item.getStringVar(sbs_generic.V_STR_DATAFILE)));
        }
    }
    //####################
    public int getType () {
        return (item.getIntVar(sbs_generic.V_INT_TYPE));
    }
    //#####################################
    public void setIntVar (int varType, int val) {
        switch (item.getIntVar(sbs_generic.V_INT_TYPE)) {
        case sbs_generic.MESSAGE:     ((sbsMessage)   item).setIntVar(varType, val);  break;
        case sbs_generic.CHECK_VAR:   ((sbs_check_var)item).setIntVar(varType, val);  break;
        case sbs_generic.GET_VAR:     ((sbs_get_var)  item).setIntVar(varType, val);  break;
        case sbs_generic.WRITE_VAR:   ((sbs_write_var)item).setIntVar(varType, val);  break;
        case sbs_generic.WAIT:        ((sbs_wait)     item).setIntVar(varType, val);  break;
        case sbs_generic.FUNCTION:    ((sbs_function) item).setIntVar(varType, val);  break;
        default:  break;
        }
    }
    //#####################################
    public int getIntVar (int varType) {
        switch (item.getIntVar(sbs_generic.V_INT_TYPE)) {
        case sbs_generic.MESSAGE:     return ((sbsMessage)   item) .getIntVar(varType);
        case sbs_generic.CHECK_VAR:   return ((sbs_check_var) item).getIntVar(varType);
        case sbs_generic.GET_VAR:     return ((sbs_get_var) item)  .getIntVar(varType);
        case sbs_generic.WRITE_VAR:   return ((sbs_write_var)item) .getIntVar(varType);
        case sbs_generic.WAIT:        return ((sbs_wait)     item) .getIntVar(varType);
        case sbs_generic.FUNCTION:    return ((sbs_function) item) .getIntVar(varType);
        default:  return -1;
        }
    }
    //#####################################
    public void setStringVar (int varType, String val) {
        switch (item.getIntVar(sbs_generic.V_INT_TYPE)) {
        case sbs_generic.MESSAGE:     ((sbsMessage)   item).setStringVar(varType, val);  break;
        case sbs_generic.CHECK_VAR:   ((sbs_check_var)item).setStringVar(varType, val);  break;
        case sbs_generic.GET_VAR:     ((sbs_get_var)  item).setStringVar(varType, val);  break;
        case sbs_generic.WRITE_VAR:   ((sbs_write_var)item).setStringVar(varType, val);  break;
        case sbs_generic.WAIT:        ((sbs_wait)     item).setStringVar(varType, val);  break;
        case sbs_generic.FUNCTION:    ((sbs_function) item).setStringVar(varType, val);  break;
        default:  break;
        }
    }
    //#####################################
    public String getStringVar (int varType) {
        switch (item.getIntVar(sbs_generic.V_INT_TYPE)) {
        case sbs_generic.MESSAGE:     return ((sbsMessage)   item).getStringVar(varType);
        case sbs_generic.CHECK_VAR:   return ((sbs_check_var)item).getStringVar(varType);
        case sbs_generic.GET_VAR:     return ((sbs_get_var)  item).getStringVar(varType);
        case sbs_generic.WRITE_VAR:   return ((sbs_write_var)item).getStringVar(varType);
        case sbs_generic.WAIT:        return ((sbs_wait)     item).getStringVar(varType);
        case sbs_generic.FUNCTION:    return ((sbs_function) item).getStringVar(varType);
        default:  return "";
        }
    }
    //####################
    public void LoadFromDB() {
        switch (item.getIntVar(sbs_generic.V_INT_TYPE)) {
        case sbs_generic.MESSAGE:
        case sbs_generic.CHECK_VAR:
        case sbs_generic.GET_VAR:
        case sbs_generic.WRITE_VAR:
        case sbs_generic.FUNCTION:  environ.getDataBase().getItemData(this); break;
        case sbs_generic.WAIT:      break;
        default:                    break; 
        }
    }
    //####################
    public void DeleteFiles() {
        switch (item.getIntVar(sbs_generic.V_INT_TYPE)) {
        case sbs_generic.MESSAGE:
        case sbs_generic.CHECK_VAR:
        case sbs_generic.GET_VAR:
        case sbs_generic.WRITE_VAR:
        case sbs_generic.FUNCTION:
             String PathName= environ.getTempFullFileName(
                                         getStringVar(sbs_generic.V_STR_DESCFILE));
            try {
             File file = new File(PathName);
             file.delete();
             PathName= environ.getTempFullFileName(
                                        getStringVar(sbs_generic.V_STR_DATAFILE));
             file = new File(PathName);
             file.delete();
             } catch(Exception e) { System.out.println(" Unable to delete file" + PathName); }
             break;
        case sbs_generic.WAIT:      break;
        default:                    break; 
        }
    }
    //####################
    public boolean  isValidFile ( ) {
           File  f = new File(environ.getTempFullFileName( 
                              item.getStringVar(sbs_generic.V_STR_DESCFILE))); 
           return  f.exists(); 
    }
    //#####################################
    public void setColor(Color c)    {
           item.setColor(c);
    }
    //#####################################
    public Color getColor( )    {
           return item.getColor() ;
    }
    //#####################################
    public sbsTask getTask1 ( ) {
        switch (item.getIntVar(sbs_generic.V_INT_TYPE)) {
        case sbs_generic.MESSAGE:
             return ((sbsMessage)item).getTask1();
        default:
             break;
        }
        return null;
    }  
    //#####################################
    public void setTask1 (sbsTask task1) {
        switch (item.getIntVar(sbs_generic.V_INT_TYPE)) {
        case sbs_generic.MESSAGE:
             ((sbsMessage)item).setTask1(task1);
             break;
        default:
             break;
        }
    }     
    //#####################################
    public void setTask2 (sbsTask task2) {
        switch (item.getIntVar(sbs_generic.V_INT_TYPE)) {
        case sbs_generic.MESSAGE:
             ((sbsMessage)item).setTask2(task2);
             break;
        default:
             break;
        }
    }     
    //#####################################
    public sbsTask getTask2 ( ) {
        switch (item.getIntVar(sbs_generic.V_INT_TYPE)) {
        case sbs_generic.MESSAGE:
             return ((sbsMessage)item).getTask2();
        default:
             break;
        }
        return null;
    }     
    //#####################################
    public boolean isaSend ( ) {
        switch (item.getIntVar(sbs_generic.V_INT_TYPE)) {
        case sbs_generic.MESSAGE:
             return ((sbsMessage)item).isaSend();
        default:
             break;
        }
        return false;
    }  
    //####################
    public boolean concernTask (sbsTask task){
        switch (item.getIntVar(sbs_generic.V_INT_TYPE)) {
        case sbs_generic.MESSAGE:
             return ((sbsMessage)item).concernTask(task);
        default:
             break;
         }
        return false;
    }
    //#####################################
    public void exportInTestFile (FileWriter out) {
       switch (item.getIntVar(sbs_generic.V_INT_TYPE)) {
       case sbs_generic.MESSAGE:
            ((sbsMessage)item).exportInTestFile(out);
            break;
       case sbs_generic.CHECK_VAR:
           ((sbs_check_var)item).exportInTestFile(out);
            break;
       case sbs_generic.GET_VAR:
           ((sbs_get_var)item).exportInTestFile(out);
            break;
       case sbs_generic.WRITE_VAR:
           ((sbs_write_var)item).exportInTestFile(out);
            break;
       case sbs_generic.WAIT:
           ((sbs_wait)item).exportInTestFile(out);
            break;
       case sbs_generic.FUNCTION:
           ((sbs_function)item).exportInTestFile(out);
            break;
       default:
            break;
        }
    }  
    //#####################################
    public void export2Lis (FileWriter out) {
       switch (item.getIntVar(sbs_generic.V_INT_TYPE)) {
       case sbs_generic.MESSAGE:
//            ((sbsMessage)item).export2Lis(out);
//            environ.getItemDataManager().export2Lis(this, out);
//            break;
       case sbs_generic.CHECK_VAR:
       case sbs_generic.GET_VAR:
       case sbs_generic.FUNCTION:
       case sbs_generic.WRITE_VAR:
            environ.getItemDataManager().export2Lis(this, out);
            break;
       case sbs_generic.WAIT:
           ((sbs_wait)item).export2Lis(out);
            break;
       default:
            break;
        }
    }  
    //#####################################
    public void save2DB ( ) {

       switch (item.getIntVar(sbs_generic.V_INT_TYPE)) {
       case sbs_generic.MESSAGE:
       case sbs_generic.CHECK_VAR:
       case sbs_generic.GET_VAR:
       case sbs_generic.WRITE_VAR:
       case sbs_generic.FUNCTION:
            environ.getDataBase().InsertStructDesc(this);
            environ.getDataBase().InsertStructData(this);
       case sbs_generic.WAIT:
            break;
       default:
            break;
        }
    }  
    //#####################################
    public void paintItem (Graphics g) {
        switch (item.getIntVar(sbs_generic.V_INT_TYPE)) {
        case sbs_generic.MESSAGE:    ((sbsMessage)   item) .paintItem(g);  break;
        case sbs_generic.CHECK_VAR:  ((sbs_check_var) item).paintItem(g);  break;
        case sbs_generic.GET_VAR:    ((sbs_get_var) item)  .paintItem(g);  break;
        case sbs_generic.WRITE_VAR:  ((sbs_write_var)item) .paintItem(g);  break;
        case sbs_generic.WAIT:       ((sbs_wait)     item) .paintItem(g);  break;
        case sbs_generic.FUNCTION:   ((sbs_function) item) .paintItem(g);  break;
        default:             break;
        }
    }
}  // class sbs-item

