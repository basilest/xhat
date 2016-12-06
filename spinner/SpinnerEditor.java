/*
 * Created on Nov 28, 2011
 *
 * 
 * @author  Basile Stefano
 */
package spinner;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.util.*;

import common.*;
import javax.swing.table.*;

import treetable.*;

/**
 * @author  Basile Stefano
 */
public class SpinnerEditor extends    treetable.AbstractCellEditor 
                           implements TableCellEditor  {

    Environ     environ;
    JLabel      blankLabel;
    MySpinner   spinner;

   public SpinnerEditor (Environ environ) {
           this.environ = environ;
           blankLabel = new JLabel("-");
    }
    

//    private MySpinner getSpinner (DefaultMutableTreeNode treeNode, int subrange) { 
//        TreeNodeInfo           info = null;
//
//        if (treeNode.isRoot() == false ) {
//            info = (TreeNodeInfo) treeNode.getUserObject();
//        }
//      
//        if (info != null &&
//            info.getNodeType() == TreeNodeInfo.C_TYPE_ARRAY) {
//            
////            return info.getSpinner_i(subrange);
//            return environ.getSymbolTree().getSpinner_i(info.getSpinnerIdxSubRange_i(subrange));
//        }
//        return null;
//    }

      private MySpinner getSpinner (DefaultMutableTreeNode treeNode, int subrange) { 
          TreeNodeInfo info = (TreeNodeInfo) treeNode.getUserObject();

          if (info != null &&
              info.getNodeType() == TreeNodeInfo.C_TYPE_ARRAY) {
            
//              return info.getSpinner_i(subrange);
              return environ.getSymbolTree().getSpinner_i(info.getSpinnerIdxSubRange_i(subrange));
          }
          return null;
      }
    
    public Component getTableCellEditorComponent(JTable  table,
                                                 Object  value,
                                                 boolean isSelected,
                                                 int     row, 
                                                 int     column) 
    {

        spinner = getSpinner ((DefaultMutableTreeNode) value,
                               column - CStructTreeTableModel.SPINNER_INDEX); 

        if (spinner != null ) {
            return spinner;
        }

        return blankLabel;
      }

   // Enable Editor
   public boolean isCellEditable ( EventObject evt) {
          return  true;
   }


    // Returns the spinners current value.
    public Object getCellEditorValue() {
            if (spinner != null ) {
                return spinner.getValue();
            }
            return blankLabel;
    }
}
