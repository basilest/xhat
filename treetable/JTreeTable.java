/*
 * Created on 29-nov-2011
 *
 * 
 * @author  Basile Stefano
 */
package treetable;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import javax.swing.event.*;

import common.*;
import javax.swing.table.*; 
import sbs.*;
import spinner.*;
import C_dataClass.MsgHdr;

/**
 * @author  Basile Stefano
 */

//####################################
public class JTreeTable extends JTable {


    SpinnerRenderer  spinnerRend;
    SpinnerEditor    spinnerEdit;
    Environ          environ;
    MsgHdr           msgHdr;

    //####################################
    public JTreeTable (Environ environ) 
    {
        super();  // create a JTable

        this.environ = environ;
        msgHdr = new MsgHdr();

        setDragEnabled (false);
        (getTableHeader()).setReorderingAllowed (false);
 
        spinnerRend = new SpinnerRenderer(environ);
        spinnerEdit = new SpinnerEditor  (environ);
    
        // Force the JTable and JTree to share their row selection models. 
        ListToTreeSelectionModelWrapper selectionWrapper = new 
                                ListToTreeSelectionModelWrapper();
        environ.getSymbolTree().getTree().setSelectionModel(selectionWrapper);
        setSelectionModel(selectionWrapper.getListSelectionModel()); 

        // Make the tree and table row heights the same.
        environ.getSymbolTree().getTree().setRowHeight(getRowHeight());
    
        // Install the tree editor renderer and editor.
        setDefaultRenderer(MyJTree.class, environ.getSymbolTree().getTree());
        setDefaultEditor  (MyJTree.class, new TreeTableCellEditor());
        
        setDefaultRenderer(DefaultMutableTreeNode.class, spinnerRend);
        setDefaultEditor  (DefaultMutableTreeNode.class, spinnerEdit);
   
        setDefaultRenderer(String.class, new TableStringRenderer());

//        (getColumnModel().getColumn(i);

        setShowGrid(false);
        setIntercellSpacing(new Dimension(0, 0));
    }
    //####################################
    public void EditStop () {
           spinnerEdit.fireEditingStopped();
    }
    
    //####################################
    public void UpdateTable (sbs_item item) {

        boolean modified = environ.getTreeTableModel().setColumns();
        if (modified) {

//            TableColumnModel model = getColumnModel();    
//            for (int i = 1; i < treeTableModel.getColumnCount(); ++i) {    
//                 TableColumn column = model.getColumn(i);     
//                 if (i == 1) {                                
//                  column.setPreferredWidth(150);              
//                 }                                            
//                 else {                                       
//                  column.setPreferredWidth(80);               
//                 }                                            
//            }                                            
//            ((AbstractTableModel)(this.getModel())).fireTableStructureChanged();
            ((AdapterTable2Tree)(this.getModel())).fireTableStructureChanged();
        }
        
        environ.getSymbolTree().SetMsgHdr(msgHdr);
//        environ.getItemDataManager().InitBinaryDump(item, msgHdr);
        environ.getItemDataManager().InitData(msgHdr);

        ((AdapterTable2Tree)(this.getModel())).fireTableDataChanged();
    }
    
    //####################################
    public JScrollPane createScrollPane () {
           JScrollPane treeScrollPanel = new JScrollPane(this);
           return treeScrollPanel;
    }
    //####################################
    /* Workaround for BasicTableUI anomaly. Make sure the UI never tries to
     * paint the editor. The UI currently uses different techniques to
     * paint the renderers and editors and overriding setBounds() below
     * is not the right thing to do for an editor. Returning -1 for the
     * editing row in this case, ensures the editor is never painted.
     */
    public int getEditingRow() {
           return ((getColumnClass(editingColumn) == MyJTree.class) ? 
                   -1 : editingRow);
    }

    
    //#######################################################
    //#######################################################
    //
    // The editor used to interact with tree nodes, a JTree.
    //
    public class TreeTableCellEditor extends    AbstractCellEditor 
                                     implements TableCellEditor {
        
        public Component getTableCellEditorComponent(JTable table, 
                                                     Object value,
													 boolean isSelected, 
                                                     int r, 
                                                     int c) {
            return environ.getSymbolTree().getTree();
        }
    } // end class TreeTableCellEditor

    //#######################################################
    //#######################################################
    //
    // The editor used to interact with tree nodes, a JTree.
    //
    public class TableStringRenderer extends   DefaultTableCellRenderer {
        

    public TableStringRenderer() { super(); }

    public void setValue(Object value) {
           super.setValue(value);
           setHorizontalAlignment(SwingConstants.CENTER);
    }
    } // end class TableStringRenderer
//    //#######################################################
//    //#######################################################
//    //
//    // The editor used to interact with tree nodes, a JTree.
//    //
//    public class TableStringEditor extends   DefaultTableCellEditor 
//                                   implements TableCellEditor {
//        
//
//    public TableStringEditor () { super(); }
//
//    public void setValue(Object value) {
//           super.setValue(value);
//           setHorizontalAlignment(SwingConstants.CENTER);
//    }
//    } // end class TableStringRenderer

    /**
     * ListToTreeSelectionModelWrapper extends DefaultTreeSelectionModel
     * to listen for changes in the ListSelectionModel it maintains. Once
     * a change in the ListSelectionModel happens, the paths are updated
     * in the DefaultTreeSelectionModel.
     */
    class ListToTreeSelectionModelWrapper extends DefaultTreeSelectionModel { 
    /** Set to true when we are updating the ListSelectionModel. */
    protected boolean         updatingListSelectionModel;

    public ListToTreeSelectionModelWrapper() {
        super();
        getListSelectionModel().addListSelectionListener
                                (createListSelectionListener());
    }

    /**
     * Returns the list selection model. ListToTreeSelectionModelWrapper
     * listens for changes to this model and updates the selected paths
     * accordingly.
     */
    ListSelectionModel getListSelectionModel() {
        return listSelectionModel; 
    }

    /**
     * This is overridden to set <code>updatingListSelectionModel</code>
     * and message super. This is the only place DefaultTreeSelectionModel
     * alters the ListSelectionModel.
     */
    public void resetRowSelection() {
        if(!updatingListSelectionModel) {
        updatingListSelectionModel = true;
        try {
            super.resetRowSelection();
        }
        finally {
            updatingListSelectionModel = false;
        }
        }
        // Notice how we don't message super if
        // updatingListSelectionModel is true. If
        // updatingListSelectionModel is true, it implies the
        // ListSelectionModel has already been updated and the
        // paths are the only thing that needs to be updated.
    }

    /**
     * Creates and returns an instance of ListSelectionHandler.
     */
    protected ListSelectionListener createListSelectionListener() {
        return new ListSelectionHandler();
    }

    /**
     * If <code>updatingListSelectionModel</code> is false, this will
     * reset the selected paths from the selected rows in the list
     * selection model.
     */
    protected void updateSelectedPathsFromSelectedRows() {
        if(!updatingListSelectionModel) {
        updatingListSelectionModel = true;
        try {
            // This is way expensive, ListSelectionModel needs an
            // enumerator for iterating.
            int        min = listSelectionModel.getMinSelectionIndex();
            int        max = listSelectionModel.getMaxSelectionIndex();

            clearSelection();
            if(min != -1 && max != -1) {
            for(int counter = min; counter <= max; counter++) {
                if(listSelectionModel.isSelectedIndex(counter)) {
                TreePath     selPath = environ.getSymbolTree().getTree().getPathForRow (counter);

                if(selPath != null) {
                    addSelectionPath(selPath);
                }
                }
            }
            }
        }
        finally {
            updatingListSelectionModel = false;
        }
        }
    }

    /**
     * Class responsible for calling updateSelectedPathsFromSelectedRows
     * when the selection of the list changse.
     */
    class ListSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
        updateSelectedPathsFromSelectedRows();
        }
    }
    }
}




