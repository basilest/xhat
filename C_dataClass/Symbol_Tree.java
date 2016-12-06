/*
 * Created on Jan 5, 2012
 * @author Basile Stefano
 */
package C_dataClass;


import common.*;
import javax.swing.tree.*;

import java.util.*;
import java.io.*;

import treetable.*;

import javax.swing.*;

import spinner.*;

/**
 * @author Basile Stefano
 *
 */
//####################
//####################
public class Symbol_Tree {
    
    Environ                 environ;
    DefaultMutableTreeNode  treeRoot;
    
    FileWriter out; 
    
    MyJTree                 Tree;    // it is only used to render the table
                                     // as any nodes add/remove-operations 
                                     // are performed via 
    
    Vector arraySpinners;            // Vector of Spinners for Array SUBRANGE
    
    //####################
    public Symbol_Tree (Environ environ) {
        
        this.environ = environ;
        
        arraySpinners   = new Vector (10);
        Tree            = new MyJTree(environ);
        Tree.setToggleClickCount(1);

        treeRoot        = new DefaultMutableTreeNode (new TreeNodeInfo(environ, TreeNodeInfo.C_TYPE_UNKNOWN));
    }
    //####################
    public MyJTree   getTree () {
           return Tree;
    }
    //####################
    public int   getSize () {
//           System.out.println("root GetSize "+((TreeNodeInfo) treeRoot.getUserObject()).getSize());
           return  ((TreeNodeInfo) treeRoot.getUserObject()).getSize();
    }
    //####################
    public int    getMaxArrayLevels () {
        int i;
        int max = 0;
        int MAX = 0;
        for (i = 0; i < arraySpinners.size(); i++) {
             max = ((SpinnerInfo) arraySpinners.get(i)).node.getNumSubRange();
             if (max > MAX)
                 MAX = max;
           }
        return MAX;
    }
    //####################
    public DefaultMutableTreeNode getTreeRoot () {
           return treeRoot;
    }
    //####################
    public void resetTree ( ) {
       ((TreeNodeInfo)(treeRoot.getUserObject())).resetNode();
        treeRoot.removeAllChildren();
        environ.getTreeTableModel().reload();

        // In spite of arraySpinners.clear some spinner still remains not disposed
        // make them not visible. 
        for (int i = 0; i < arraySpinners.size(); i++) {
//            ((SpinnerInfo) arraySpinners.get(i)).spinner.getGraphics().dispose();
            ((SpinnerInfo) arraySpinners.get(i)).spinner.setVisible(false);
        }
        arraySpinners.clear();
    }
    //####################
    public int addArrayRangeNode (TreeNodeInfo node, int cardinality) {
        
        int position = arraySpinners.size();
        
        arraySpinners.add (new SpinnerInfo(node, cardinality));

        return position;
    }
    //####################
    public MySpinner getSpinner_i (int arrayIdx) {
        
             if ( arrayIdx >= 0 && arrayIdx < arraySpinners.size())
             return ((SpinnerInfo) arraySpinners.get(arrayIdx)).spinner;
             else 
             return null;
    } 
    //####################
    public int getCurrentValueRange_i (int arrayIdx) {
        
        MySpinner jsp;
        SpinnerNumberModel model; 
        int      val = -1;
        
        if ( arrayIdx >= 0 && arrayIdx < arraySpinners.size()) 
        {
             jsp = ((SpinnerInfo) arraySpinners.get(arrayIdx)).spinner;
             model = ((SpinnerNumberModel) (jsp.getModel())); 
             val   = ((Integer)(model.getValue())).intValue(); 
        }
        return val;
    } 
    //#####################################
   public int getChildCount () {
       int n = 0;
       CStructTreeTableModel   model = environ.getTreeTableModel();
       if ( ! model.isLeaf(treeRoot))
            n =  model.getChildCount(treeRoot); 
       return n;
   }  
   //#####################################
   public void SetMsgHdr(MsgHdr hdr) {
       
       CStructTreeTableModel   model = environ.getTreeTableModel();
       DefaultMutableTreeNode  node;
       DefaultMutableTreeNode  son;
       int num_son;
       int i,j;
       int s_m = 0;
       TreeNodeInfo   ninfo;
       
       hdr.reset();  // set all to null
       
       if (model.isLeaf(treeRoot) == false) {
           
            for (i = 0; i < model.getChildCount(treeRoot); i++) {
                  
                 node  = (DefaultMutableTreeNode) model.getChild (treeRoot, i );
                 ninfo = (TreeNodeInfo) node.getUserObject();
                 
                 if (ninfo.getNodeType() == TreeNodeInfo.C_TYPE_BASE &&
                     ninfo.toString().equals(MsgHdr.STR_SOURCE_SYS))
                 {
                     hdr.source_sys = node;
                 }
                 else if (ninfo.getNodeType() == TreeNodeInfo.C_TYPE_BASE &&
                          ninfo.toString().equals(MsgHdr.STR_DEST_SYS))
                 {
                     hdr.dest_sys = node;
                 }
                 else if (ninfo.getNodeType() == TreeNodeInfo.C_TYPE_UNION ) 
                 {
                      if (ninfo.toString().equals(MsgHdr.STR_SOURCE_MBX))
                          s_m = 1;
                      else if (ninfo.toString().equals(MsgHdr.STR_DEST_MBX))
                          s_m = 2;
                      
                      if (s_m != 0 ) 
                            for (j = 0; j < model.getChildCount(node); j++) {
                                son   = (DefaultMutableTreeNode) model.getChild (node, j );
                                ninfo = (TreeNodeInfo) son.getUserObject();
                 
                                if (ninfo.getNodeType() == TreeNodeInfo.C_TYPE_BASE &&
                                    ninfo.toString().equals(MsgHdr.STR_NAME))
                                    
                                    if (s_m == 1 ) 
                                        hdr.source_mbx = son;
                                    else {
                                        hdr.dest_mbx   = son;
                                        return;
                                    }
                          }
                 } // else
         } // for
     } 
   }
    //#####################################
//    public void SetMsgHdr(MsgHdr hdr) {
//       
//        CStructTreeTableModel   model = environ.getTreeTableModel();
//        DefaultMutableTreeNode  node;
//        DefaultMutableTreeNode  son;
//        DefaultMutableTreeNode  hdrNode = null;
//        int num_son;
//        int i,j;
//        int s_m = 0;
//        TreeNodeInfo   info;
//        
//        
//       
//        hdr.reset();  // set all to null
//       
//            node = treeRoot;
//             for (i = 0; 
//                 (node.isLeaf() == false) &&  i < model.getChildCount(node); 
//                  i++) {
//                  
//                  son = (DefaultMutableTreeNode) model.getChild (treeRoot, i );
//                  info = (TreeNodeInfo) son.getUserObject();
//                 
//                  if (info.getNodeType() == TreeNodeInfo.C_TYPE_BASE &&
//                      info.toString().equals(MsgHdr.STR_SOURCE_SYS))
//                  {
//                      hdr.source_sys = node;
//                  }
//                  else if (info.getNodeType() == TreeNodeInfo.C_TYPE_BASE &&
//                           info.toString().equals(MsgHdr.STR_DEST_SYS))
//                  {
//                      hdr.dest_sys = node;
//                  }
//                  else if (info.getNodeType() == TreeNodeInfo.C_TYPE_UNION ) 
//                  {
//                       if (info.toString().equals(MsgHdr.STR_SOURCE_MBX))
//                           s_m = 1;
//                       else if (info.toString().equals(MsgHdr.STR_DEST_MBX))
//                           s_m = 2;
//                      
//                       if (s_m != 0 ) 
//                             for (j = 0; j < model.getChildCount(node); j++) {
//                                 son   = (DefaultMutableTreeNode) model.getChild (node, j );
//                                 ninfo = (TreeNodeInfo) son.getUserObject();
//                 
//                                 if (ninfo.getNodeType() == TreeNodeInfo.C_TYPE_BASE &&
//                                     ninfo.toString().equals(MsgHdr.STR_NAME))
//                                    
//                                     if (s_m == 1 ) 
//                                         hdr.source_mbx = son;
//                                     else {
//                                         hdr.dest_mbx   = son;
//                                         return;
//                                     }
//                           }
//                  } // else
//          } // for
//      } 
//    }
   //####################
    private void exportNodeInfo (CStructTreeTableModel   model,
                                 Object                  node,
                                 int                     lev) {
        int            i;
        int            num_son;
        Object         son;
        TreeNodeInfo   ninfo = (TreeNodeInfo)((DefaultMutableTreeNode)node). getUserObject();
        
        try {
            out.write ("\n");
            out.write (lev + " " + ParseDefFile.T_STR_FLDNAME     + ninfo.toString()    + "\n");
            out.write (lev + " " + ParseDefFile.T_STR_FLDTYPE     + ninfo.getNodeType() + "\n");
            out.write (lev + " " + ParseDefFile.T_STR_FLDTYPENAME + ninfo.getTypeName() + "\n");
            out.write (lev + " " + ParseDefFile.T_STR_FLDSIZE     + ninfo.getSize()     + "\n");
            out.write (lev + " " + ParseDefFile.T_STR_FLDOFFST    + ninfo.getOffset()   + "\n");
            
            if (ninfo.getNodeType() == TreeNodeInfo.C_TYPE_ARRAY) 

                for (i=0; i < ninfo.getNumSubRange(); i++)
                     out.write (lev + " " + ParseDefFile.T_STR_ARYRNG + ninfo.getMaxSubRange_i(i) + "\n");
    
           out.write (ParseDefFile.T_STR_FLDEND + "\n");
           
        } catch (IOException e) { System.out.println("Unable to write Def file"); }
        
        if ( ! model.isLeaf(node)) {
             num_son = model.getChildCount(node);
             for (i = 0; i < num_son; i++) {
                  
                  son = model.getChild (node, i );
                  exportNodeInfo (model, son, lev + 1 );
             }
        }
     }            
//    //####################
//    public void exportTree (String FileName) {
//        TreeRootInfo  rinfo = (TreeRootInfo)treeRoot.getUserObject();
//       try {
//            out = new FileWriter(environ.getTempDir() + FileName);
//            out.write(" " + "\n");
//
//           out.write ("0 " + ParseDefFile.T_STR_ROOT    + rinfo.toString()+"\n");
//           out.write ("0 " + ParseDefFile.T_STR_TOTSIZE + ((TreeNodeInfo)varRoot.getUserObject()).getSize() + "\n");
//
//            exportNodeInfo(environ.getTreeTableModel(), varRoot, 0);
//            
//            out.close(); 
//
//        } catch (Exception e) {e.printStackTrace();}  
//} 
    //####################
    public void exportTree (String FileName) {
        TreeNodeInfo  rinfo = (TreeNodeInfo)treeRoot.getUserObject();
       try {
            out = new FileWriter(environ.getTempFullFileName(FileName));
//            out.write(" " + "\n");
//            out.write (ParseDefFile.T_STR_CSYMBOL + CSymbol  + "\n");
//
            exportNodeInfo(environ.getTreeTableModel(), treeRoot, 0);
            
            out.close(); 

        } catch (Exception e) {e.printStackTrace();}  
} 
    //####################
    //####################
    class SpinnerInfo {

        TreeNodeInfo node;
        MySpinner    spinner; 
        
        public SpinnerInfo (TreeNodeInfo node, int cardinality) {
            this.node = node;
            SpinnerNumberModel model   = new SpinnerNumberModel(0, 0, cardinality-1, 1);
            spinner = new MySpinner(model, environ.getTreeTable());
        }
    }
    
    

}
