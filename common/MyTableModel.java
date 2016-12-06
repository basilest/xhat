/*
 * Created on Feb 3, 2012
 *
 * 
 * @author  Basile Stefano
 */
package common;

import javax.swing.table.DefaultTableModel;
/**
 * @author  Basile Stefano
 */
public class MyTableModel extends DefaultTableModel {
   
    public MyTableModel (String [] colNames) {
           super(colNames, 0);
    }
    
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
