/*
 * Created on Nov 28, 2011
 *
 * 
 * @author  Basile Stefano
 */
package treetable;

import  common.Environ;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;

import javax.swing.table.*; 

/**
 * @author  Basile Stefano
 */
public class MyJTree extends JTree implements TableCellRenderer  {
    
    protected int visibleRow;
    Environ       environ;

    int i;

    //####################################
    public MyJTree(Environ environ) {
             super ();
             //setCellRenderer(new MyTreeRenderer());
             this.environ = environ;
    }

    //####################################
   public void setBounds(int x, int y, int w, int h) {
        int hi = environ.getTreeTable().getHeight();
        super.setBounds(x, 0, w, hi );
    }

    //####################################
    // Sets tree row height, and forwards row height to the table.
    public void setRowHeight(int rowHeight) { 
        if (rowHeight > 0) {
            super.setRowHeight(rowHeight); 

            if ((environ.getTreeTable() != null) && 
                (environ.getTreeTable().getRowHeight() != rowHeight))
                
                environ.getTreeTable().setRowHeight(getRowHeight()); 
        }
    }    
    //####################################
    /**
     * updateUI is overridden to set the colors of the Tree's renderer
     * to match that of the table.
     */
    public void updateUI() {
        super.updateUI();
        // Make the tree's cell renderer use the table's cell selection colors. 
        TreeCellRenderer tcr = getCellRenderer();
        
        if (tcr instanceof DefaultTreeCellRenderer) 
        {
            DefaultTreeCellRenderer dtcr = ((DefaultTreeCellRenderer)tcr); 
            dtcr.setTextSelectionColor(UIManager.getColor
                           ("Table.selectionForeground"));
            dtcr.setBackgroundSelectionColor(UIManager.getColor
                            ("Table.selectionBackground"));
        }
    }
    //####################################
    public void paint(Graphics g) {
        g.translate(0, -visibleRow * getRowHeight());
        super.paint(g);
    }
    //####################################
    public Component getTableCellRendererComponent(JTable  table,
												   Object  value,
												   boolean isSelected,
												   boolean hasFocus,
												   int     row, 
                                                   int     column) 
    {
        if (isSelected)
            setBackground(table.getSelectionBackground());
        else
            setBackground(table.getBackground());

        visibleRow = row;
        return this;
    }
}
//####################################

//    //
//    // The editor used to interact with tree nodes, a JTree.
//    //
//
//    public class TreeTableCellEditor extends    AbstractCellEditor 
//                                     implements TableCellEditor {
//        
//   
//        public Component getTableCellEditorComponent (JTable table, 
//                                                      Object value,
//													  boolean isSelected, 
//                                                      int r, 
//                                                      int c) {
//                return tree;
//        }
//    }
//
