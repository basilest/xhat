/*
 * Created on 29-nov-2011
 *
 * 
 * @author  Basile Stefano
 */
package treetable;

import javax.swing.*;
import javax.swing.tree.*;

import javax.swing.table.AbstractTableModel;
import javax.swing.event.*;

/**
 * @author  Basile Stefano
 */
public class AdapterTable2Tree extends AbstractTableModel    {
        
        JTree                 tree;
        CStructTreeTableModel treeTableModel;

        public AdapterTable2Tree (CStructTreeTableModel treeTableModel, JTree tree) {
           
            this.tree = tree;
            this.treeTableModel = treeTableModel;
        
            tree.addTreeExpansionListener(new TreeExpansionListener() {
            // Don't use fireTableRowsInserted() here; 
            // the selection model would get  updated twice. 
                public void treeExpanded( TreeExpansionEvent event) {  
                            fireTableDataChanged(); 
                }
                public void treeCollapsed (TreeExpansionEvent event) {  
                            fireTableDataChanged(); 
            }
        });
      }

      // Wrappers, implementing TableModel interface. 

        public int getColumnCount() {
//           System.out.println("AdapterTable2Tree:getColumnCount");
        return treeTableModel.getColumnCount();
        }

        public String getColumnName(int column) {
//            System.out.println("AdapterTable2Tree:getColumnName");
        return treeTableModel.getColumnName(column);
        }

        public Class getColumnClass(int column) {
//            System.out.println("AdapterTable2Tree:getColumnClass");
        return treeTableModel.getColumnClass(column);
        }

        public int getRowCount() {
//            System.out.println("AdapterTable2Tree:getRowCount "+tree.getRowCount());
        return tree.getRowCount();
        }

        protected Object nodeForRow(int row) {
            
        Object   ob       = null;
        TreePath treePath = null;
        try {
            treePath = tree.getPathForRow(row);
            ob       = treePath.getLastPathComponent();
        } catch (Exception e) {
          System.out.println("Node for Row Error:row "+row+
                             " tree="+tree+" treePath="+treePath +" ob="+ ob);
          }
          return ob;
        }

        public Object getValueAt(int row, int column) {
        return treeTableModel.getValueAt(nodeForRow(row), column);
        }

        public boolean isCellEditable(int row, int column) {
             return treeTableModel.isCellEditable(nodeForRow(row), column); 
        }

        public void setValueAt(Object value, int row, int column) {
        treeTableModel.setValueAt(value, nodeForRow(row), column);
        }

}
