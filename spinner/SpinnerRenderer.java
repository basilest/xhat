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

import common.*;
import javax.swing.table.*;

import treetable.*;

/**
 * @author  Basile Stefano
 */
public class SpinnerRenderer implements TableCellRenderer  {

    int  visibleRow;

    Environ     environ;
    JLabel      blankLabel;

    public SpinnerRenderer(Environ   environ) {
             this.environ = environ;
             blankLabel = new JLabel("-");
    }

//    public void setBounds(int x, int y, int w, int h) {
//        int hi = Table.getHeight();
//        super.setBounds(x, 0, w, hi );
//    }

    public Component getTableCellRendererComponent(JTable  table,
                                                   Object  value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int     row, 
                                                   int     column) 
    {
         
        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)value ;
        MySpinner              jsp; 
        TreeNodeInfo           info = (TreeNodeInfo) treeNode.getUserObject();

        if (info != null &&
            info.getNodeType() == TreeNodeInfo.C_TYPE_ARRAY) 
        {
            int subrange  = column - CStructTreeTableModel.SPINNER_INDEX;
            jsp = environ.getSymbolTree().getSpinner_i(info.getSpinnerIdxSubRange_i(subrange));
            return jsp;
        }
        return blankLabel;
    }
//    public Component getTableCellRendererComponent(JTable  table,
//												   Object  value,
//												   boolean isSelected,
//												   boolean hasFocus,
//												   int     row, 
//                                                   int     column) 
//    {
//         
//        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)value ;
//        TreeNodeInfo           info = null;
//        MySpinner              jsp; 
//
//        if (treeNode.isRoot() == false ) {
//            info = (TreeNodeInfo) treeNode.getUserObject();
//        }
//            
//        if (info != null &&
//            info.getNodeType() == TreeNodeInfo.C_TYPE_ARRAY) 
//        {
//            int subrange  = column - CStructTreeTableModel.SPINNER_INDEX;
//            jsp = environ.getSymbolTree().getSpinner_i(info.getSpinnerIdxSubRange_i(subrange));
//            return jsp;
//        }
//        return blankLabel;
//    }
}

