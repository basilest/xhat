/*
 * Created on Nov 28, 2011
 *
 * 
 * @author  Basile Stefano
 */
package spinner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.event.*;

import treetable.AdapterTable2Tree;
import treetable.JTreeTable;
      
/**
 * @author  Basile Stefano
 */
   //######################################################
   //######################################################
public class MySpinner extends JSpinner 
                       implements ChangeListener,
                                  FocusListener {

    int  translateRow;

    JTreeTable Table;

    public MySpinner(SpinnerModel model,  JTreeTable table) {
       super (model);
       setOpaque(true);
       addChangeListener(this);
       addFocusListener(this);
       Table = table;
    }

    public void stateChanged(ChangeEvent e) {
//          System.out.println("Click");
          if (isFocusOwner()== false) {
                requestFocusInWindow();
//               System.out.println("NO Focus");
          }
//          else
//          System.out.println("Focus Owner");
        ((AdapterTable2Tree)(Table.getModel())).fireTableDataChanged();
    }


     public void focusGained(FocusEvent e) {
//         System.out.println("(GAINED(");
     }

     public void focusLost(FocusEvent e) {
//         System.out.println(")LOST)");
         Table.EditStop();
     }


    public void setTranslateRow (int d)    {
           translateRow = d;    
    }
    
//    public void setBounds(int x, int y, int w, int h) {
//        //int hi = Table.getHeight();
//        super.setBounds(x, 0, w, h );
//    }

    public void paint(Graphics g) {
//        g.translate(0, -visibleRow * Table.getRowHeight());
//        g.translate(0, -translateRow );
        super.paint(g);
//        System.out.println("Paint!!!!!!!!!!");
    }
   
} // --- class MySpinner

