/*
 * Created on Feb 3, 2012
 *
 * 
 * @author  Basile Stefano
 */
package ui;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.*;
import java.awt.event.*;
import common.Environ;
import common.MyTableModel;
import sbs.sbs_item;
import sbs.sbs_generic;


import javax.swing.*;

/**
 * @author  Basile Stefano
 */
//#####################################
public class DBOpenItemDialog implements ActionListener {
    
    final static String  STR_SEARCH = "Search";
    final static String  STR_OPEN   = "Select";
    final static String  STR_CANCEL = "Cancel";
    
    static final String  DescSqlCmd = "SELECT tkey, size, StructName from STRUCT_DESC";
    
    static final String[] ColNamesDesc = {"Struct Id",
                                          "size", 
                                          "Code Name"};

    static final String[] ColNamesData = {"Data Id",
                                          "Name"};
    JDialog           dialog;
    JPanel            jNorthPane;
    JPanel            jcontentPane;
    JPanel            jButtonPane1;
    JPanel            jButtonPane2;
    JSplitPane        splitPane;
    JScrollPane       scrollPaneDesc;
    JScrollPane       scrollPaneData;
    JButton           jButtonSearch;
    JButton           jButtonOpen;
    JButton           jButtonCancel;


    MyTableModel      tModelDesc;
    MyTableModel      tModelData;
    JTable            tableDesc;
    JTable            tableData;
    
    Environ           environ;
    sbs_item          item;

    //#####################################
        public DBOpenItemDialog (Environ environ, sbs_item item)  {
            
            this.environ = environ;
            this.item    = item;
            
            tModelDesc = new MyTableModel (ColNamesDesc);
            tModelData = new MyTableModel (ColNamesData);
            
            tableDesc  = new JTable(tModelDesc);
            tableData  = new JTable(tModelData);
            environ.getDataBase().DBquery2JTable(tModelDesc, DescSqlCmd);

            
            (tableDesc.getTableHeader()).setReorderingAllowed (false);
            (tableData.getTableHeader()).setReorderingAllowed (false);
            
            tableDesc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            tableData.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            
            scrollPaneDesc = new JScrollPane(tableDesc);
            scrollPaneData = new JScrollPane(tableData);

            dialog         = new JDialog(environ.getMainFrame(), true);
            jcontentPane   = new JPanel(new BorderLayout());
            jNorthPane     = new JPanel(new BorderLayout());
            jButtonPane1   = new JPanel(new FlowLayout());
            jButtonPane2   = new JPanel(new FlowLayout());
            
            jButtonSearch = new JButton (STR_SEARCH);
            jButtonSearch.setActionCommand(STR_SEARCH);
            jButtonSearch.addActionListener(this);

            jButtonOpen   = new JButton (STR_OPEN);
            jButtonOpen.setActionCommand(STR_OPEN);
            jButtonOpen.addActionListener(this);

            jButtonCancel = new JButton   (STR_CANCEL);
            jButtonCancel.setActionCommand(STR_CANCEL);
            jButtonCancel.addActionListener(this);

            jButtonPane1.add(jButtonSearch);
            jButtonPane2.add(jButtonOpen);
            jButtonPane2.add(jButtonCancel);

            jNorthPane.add (scrollPaneDesc,  BorderLayout.CENTER);
            jNorthPane.add (jButtonPane1,    BorderLayout.SOUTH);
            
            splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                       jNorthPane, scrollPaneData);
        
            jcontentPane.add (splitPane,    BorderLayout.CENTER);
            jcontentPane.add (jButtonPane2, BorderLayout.SOUTH);
            
            dialog.setContentPane(jcontentPane);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
           (dialog.getRootPane()).setDefaultButton(jButtonOpen);
            dialog.setLocationRelativeTo(environ.getMainFrame());
            dialog.pack();
            dialog.setVisible(true);
        }

    //#####################################
    //#####################################
      public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();
        int    row;
        int    key;
        do {
           if (STR_SEARCH.equals(command)) {
               row = tableDesc.getSelectedRow();
               if (row != -1) {
                   
                   for (int r = tModelData.getRowCount(); r > 0;  r--)
                        tModelData.removeRow(r);
                   
                   key = Integer.parseInt(tModelDesc.getValueAt(row, 1).toString());
                   String DataSqlCmd = 
                          "SELECT tkey, UserName from STRUCT_DATA where DescTableKey="+key;
                   environ.getDataBase().DBquery2JTable(tModelDesc, DataSqlCmd );
               }
               break;
           }
           if (STR_OPEN.equals(command)) {
               row = tableDesc.getSelectedRow();
               if (row != -1) {
                   key = Integer.parseInt(tModelDesc.getValueAt(row, 1).toString());
                   item.setIntVar(sbs_generic.V_INT_DBDATKEY, key);
                   environ.getDataBase().getItemData(item);
               }
               dialog.dispose();
               break;
           }
           if (STR_CANCEL.equals(command)) {
               dialog.dispose();
               break;
           }         
        } while(false) ;
      }
} // end class OpenTestDialog
