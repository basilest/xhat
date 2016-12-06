/*
 * Created on Nov 28, 2011
 *
 * 
 * @author  Basile Stefano
 */
package treetable;

import javax.swing.*;
import javax.swing.tree.*;

import common.*;
/**
 * @author  Basile Stefano
 */

/* To create a TableModel as subclass of AbstractTableModel 
   you need only provide implementations for the following 3 methods: 

  public int    getColumnCount();
  public int    getRowCount   ();
  public Object getValueAt    (int row, int column)

*/


public class CStructTreeTableModel extends DefaultTreeModel {
    
    public static final int     SPINNER_INDEX = 2;

    ColumnInfo []    BaseColumn;
    ColumnInfo []    Column;
    
    int              numTreeNode;
    JLabel           blankLabel;
    Environ          environ;

        
    //######################################################
    public CStructTreeTableModel (Environ environ ) {

           super(environ.getSymbolTree().getTreeRoot());

           this.environ = environ;
   
           blankLabel = new JLabel();

           CreateBaseColumn();
           Column = new ColumnInfo [BaseColumn.length];
           System.arraycopy(BaseColumn, 0, Column, 0, BaseColumn.length);
        }
    //######################################################
        private void CreateBaseColumn () {
            BaseColumn    = new ColumnInfo[5];
            BaseColumn[0] = new ColumnInfo ("name",   ColumnInfo.COL_JTREE);  
            BaseColumn[1] = new ColumnInfo ("type",   ColumnInfo.COL_C_TYPE);  
            BaseColumn[2] = new ColumnInfo ("value",  ColumnInfo.COL_VALUE);  
            BaseColumn[3] = new ColumnInfo ("size",   ColumnInfo.COL_SIZE);  
            BaseColumn[4] = new ColumnInfo ("offset", ColumnInfo.COL_OFFSET);  
        }
    //######################################################
       public boolean  setColumns ( ) {
           int i;
           int baseLen = BaseColumn.length;
           boolean modified = false;
           int MaxArrayLevels = environ.getSymbolTree().getMaxArrayLevels();

           if (Column.length != ( baseLen + MaxArrayLevels)) {
           
               Column = new ColumnInfo [baseLen + MaxArrayLevels];
    
               Column[0] = BaseColumn[0];  
               Column[1] = BaseColumn[1];  
               for (i = 0; i < MaxArrayLevels; i++){
                 Column[i+SPINNER_INDEX] = new ColumnInfo ("i" + i,  ColumnInfo.COL_SPINNER);    
               }
               i += SPINNER_INDEX;
               Column[i++] = BaseColumn[2];  
               Column[i++] = BaseColumn[3];  
               Column[i]   = BaseColumn[4];  
               modified = true;    
          }
          return modified;
        } 
    //####################################
        public int getColumnCount() {
        return Column.length;
        }
        
    //####################################
        public int getRowCount() {
            
            if (environ.getSymbolTree().getTreeRoot().isLeaf()) 
                numTreeNode = 1;
            return numTreeNode;
        }

    //####################################
        public String getColumnName(int col) {
        return Column[col].getName();
        }

    //####################################
        public Class getColumnClass(int c) {
        return getValueAt(environ.getSymbolTree().getTreeRoot(), c).getClass();
        }
        
    //####################################
       public void insertNodeInto (MutableTreeNode newChild, 
								   MutableTreeNode parent, 
								   int index)
       {
           super.insertNodeInto(newChild, parent, index);
           numTreeNode ++;
       }
 
    //####################################
    public boolean isCellEditable(Object node, int col)  {

        //Note that data/cell address is constant,
        //no matter where the cell appears onscreen.
        switch (Column[col].getType()) {
    
        case ColumnInfo.COL_JTREE:    
        case ColumnInfo.COL_SPINNER:    
        case ColumnInfo.COL_VALUE:    
             return true;
    
        default:
        return false;
        }
    }
    //####################################
      public Object  getValueAt    (Object node, int col) {

            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)node;
            TreeNodeInfo           info = null;
            int                    GlobalOffset;
            

//           if (treeNode.isRoot() == false )  
               info = (TreeNodeInfo) treeNode.getUserObject();

           switch (Column[col].getType()) {

           case ColumnInfo.COL_JTREE:    
                return environ.getSymbolTree().getTree();

           case ColumnInfo.COL_C_TYPE:   
                return (info == null) ? "-" : info.getTypeName();

           case ColumnInfo.COL_SPINNER:
                return treeNode;

//           case ColumnInfo.COL_VALUE:    
//                if (info != null && treeNode.isLeaf()) {
//                    GlobalOffset = getGlobalOffset (treeNode);
//                    value        = environ.getBinaryFileManager().getValue(info.getSize(), 
//                                                                           GlobalOffset + info.getOffset());
//                }
//                else value = -1;
//                return new Integer(value);
                
           case ColumnInfo.COL_VALUE:    
                if (info != null && treeNode.isLeaf()) {
                    GlobalOffset = getGlobalOffset (treeNode);
                    return environ.getItemDataManager().getValue(info.getSize(), 
                                                                 GlobalOffset + info.getOffset());
                }
                else return "";
//               else return new Integer(-1);
                
           case ColumnInfo.COL_SIZE:
                return new Integer((info == null) ? 0 : info.getSize());

           case ColumnInfo.COL_OFFSET:   
                return new Integer((info == null) ? 0 : info.getOffset());
               
           default:
               return null;
           }
        }

    //####################################
        public void    setValueAt    (Object aValue, Object node, int col) {

           DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)node;
           TreeNodeInfo           info = null;
           
           if (treeNode != null )
           {
               info = (TreeNodeInfo) treeNode.getUserObject();
                
               switch (Column[col].getType()) {
    
               case ColumnInfo.COL_VALUE:    
                    if (info != null && treeNode.isLeaf() && aValue != null) {
                        int GlobalOffset = getGlobalOffset (treeNode);
                        String s   = aValue.toString();
                        int    val = 0;
                        boolean ok = false;
                        
                        try {
                            val = Integer.parseInt(s, 10);
                            ok = true;
                        } catch (NumberFormatException e) {}
                        if (ok == false && (s.startsWith("0x") || s.startsWith("0X")))
                        try {
                            val = Integer.parseInt(s.substring(2), 16);
                            ok = true;
                        } catch (NumberFormatException e) {}
                        if (ok)
                        environ.getItemDataManager().setValue(info.getSize(), 
                                                              GlobalOffset + info.getOffset(), 
                                                              val);
                        else if (s.equals("*"))
                        environ.getItemDataManager().resetValue(info.getSize(), 
                                                              GlobalOffset + info.getOffset());
//                        } catch (Exception e) {if (aValue != null)
//                                               System.out.println("Invalid Number '" + aValue.toString() + "'");}
    //                    fireTableCellUpdated(row, col);
                    }
                
               default:
                    break;
               }
           }
           else
           System.out.println("Set Value col=" + col + " Value=" + aValue + " node=" + node);
        }
    //####################################
    // If no ARRAY is present in the structure, then Offset field stored 
    // in TreeNodeInfo is also the ABSOLUTE Offset (getGlobalOffset will return 0).
    // When otherwise any ARRAY is present, the ABSOLUTE Offset must be calculated
    public int  getGlobalOffset (DefaultMutableTreeNode node) {
        int          Offset = 0;
        int          i;
        int          sub;
        int          ArrayTotEntry;
        int          ArrayEntrySize;
        int          numNumSubrange;
        int          CurrentRangeVal;
        TreeNodeInfo info; 
        TreeNode []  pathArray = node.getPath();
          
        for ( i=0; i < pathArray.length; i++) {  
              node = (DefaultMutableTreeNode) pathArray[i];
              info = (TreeNodeInfo) node.getUserObject();
                
              if (info.getNodeType() == TreeNodeInfo.C_TYPE_ARRAY) {

                  ArrayTotEntry  = 1;
                  numNumSubrange = info.getNumSubRange();
//                  System.out.println("#####OK Node Array subrange:"+numNumSubrange);

                  for (sub=0; sub < numNumSubrange; sub++ ) {
                      ArrayTotEntry *= info.getMaxSubRange_i(sub);
//                      System.out.println("..sub="+sub+" val="+info.getMaxSubRange_i(sub)+" Tot="+ArrayTotEntry);
                  }
                  ArrayEntrySize = info.getSize() / ArrayTotEntry;
                    
                    
                  for (sub = numNumSubrange - 1; sub >=0 ; sub-- )
                  {
                      CurrentRangeVal = environ.getSymbolTree().getCurrentValueRange_i(info.getSpinnerIdxSubRange_i(sub));
                      Offset += (ArrayEntrySize * CurrentRangeVal); 
                      ArrayTotEntry *= info.getMaxSubRange_i(sub);
//                      System.out.println("---------sub="+sub+" val="+info.getCurrentValueRange_i(sub)+"Entry_sz="+ArrayEntrySize+
//                                         "Offset="+Offset);
                  }
              }
              
        }
        return Offset;       
    }
   //######################################################
   //######################################################
        class ColumnInfo {

              // identify the COLUMN with this number:
              final static int  COL_JTREE   = 1;  
              final static int  COL_SPINNER = 2;  
              final static int  COL_C_TYPE  = 3;  
              final static int  COL_VALUE   = 4;  
              final static int  COL_SIZE    = 5;  
              final static int  COL_OFFSET  = 6;  

              String colName;
              int    colType;              

             ColumnInfo (String name, int type) {
                         colName  = name;    // store Column Name
                         colType  = type;    // store Column type
             }

             public String getName () {
                    return colName;  
             }

             public int  getType () {
                    return colType;  
             }
        }

}
