/*
 * Created on Nov 28, 2011
 *
 * 
 * @author  Basile Stefano
 */
package treetable;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;

/**
 * @author  Basile Stefano
 */
public class MyTreeRenderer extends DefaultTreeCellRenderer {

    
    public MyTreeRenderer () {
           super();
    }
    
    public Component getTreeCellRendererComponent(JTree tree,
                                                  Object value,
                                                  boolean selected,
                                                  boolean expanded,
                                                  boolean leaf,
                                                  int row,
                                                  boolean hasFocus)
    {
        DefaultMutableTreeNode node      = (DefaultMutableTreeNode)value;
          
        if (node.isRoot()) {
//            TreeRootInfo    rootInfo  = (TreeRootInfo) node.getUserObject();
//            System.out.println(rootInfo.toString());
//            setText(rootInfo.toString());  //remeber DefaultTreeCellRenderer extends JLabel.
//            System.out.println("TreeCellRenderer  ROOT"+node.toString());
            setText(node.toString());  //remeber DefaultTreeCellRenderer extends JLabel.
        }
        else {
//            TreeNodeInfo    nodeInfo  = (TreeNodeInfo) node.getUserObject();
//            System.out.println(nodeInfo.toString());
//            setText(nodeInfo.toString());
//            System.out.println("TreeCellRenderer  ! ROOT"+node.toString());
            System.out.println(node.toString());
            setText(node.toString());
        }
        return this;
    }
}


//SpinnerNumberModel model = new SpinnerNumberModel(0, 0, cardinality, 1);
//JSpinner              sp = new JSpinner(model);                
//Panel.add(sp);
//arraySpinners.add (sp);

