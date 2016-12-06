/*
 * Created on Nov 28, 2011
 *
 * 
 * @author  Basile Stefano
 */
package treetable;

import javax.swing.*;
import java.awt.*;

import javax.swing.table.*; 

/**
 * @author  Basile Stefano
 */
public class CStructTree extends JTree implements TableCellRenderer  {
    
    protected int visibleRow;
    JTreeTable    Table;

    int i;

    public CStructTree(JTreeTable table) {
             super ();
             Table = table;
             //setCellRenderer(new MyTreeRenderer());
    }

    public void setBounds(int x, int y, int w, int h) {
        int hi = Table.getHeight();
        super.setBounds(x, 0, w, hi );
    }

    public void paint(Graphics g) {
        g.translate(0, -visibleRow * getRowHeight());
        super.paint(g);
    }

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
