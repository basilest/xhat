/*
 * Created on 19-ott-2011
 *
 * 
 * @author  Basile Stefano
 */
package ui;

import common.Environ;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.plaf.basic.BasicComboBoxEditor;

import dwarf.*;
import sbs.*;
/**
 * @author  Basile Stefano
 */
public class MessageDialog implements ActionListener {

    static final String STR_BROWSE   = "Browse..";
    static final String STR_VARIABLE = "Variable";
    static final String STR_TYPEDEF  = "Typedef";
    static final String STR_OK       = "Ok";
    static final String STR_CANCEL   = "Cancel";

    JDialog             jMessageDialog;

    JPanel              contentPane;
    JPanel              jNorthPanel;
    JPanel              jboxPanel;
    JPanel              jMsgPanel;
    JPanel              jSouthPanel;

    JButton             jButtonBrowse;
    JButton             jButtonOK;
    JButton             jButtonCancel;

    JLabel              jChoiceLabel;
    
    BasicComboBoxEditor jboxEdior;
    JComboBox           jbox;

    Vector              JBoxNamesVector;
    Object[]            JBoxNamesArray;

    String              jboxString;
    JTextField          jtxtfieldMsgName;
    sbs_item            Item;

    boolean             ValidData;
    Environ             environ;
    
//  ######################################################
    public MessageDialog ( Environ environ, sbs_item item) {
        
        this.environ = environ;
        
        Item      = item;
        ValidData = false;
        
        jboxString = Item.getStringVar(sbs_generic.V_STR_CTYPENAME);
        
        contentPane  = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(500, 300));

        createNorthPanel();
        createSouthPanel();

        contentPane.add(jNorthPanel, BorderLayout.NORTH);

        JScrollPane treeScrollPanel = new JScrollPane(environ.getTreeTable());
        contentPane.add (treeScrollPanel , BorderLayout.CENTER);

        contentPane.add(jSouthPanel, BorderLayout.SOUTH);

        environ.getSymbolTree().resetTree();
        ChangeJtree(item);
        environ.getItemDataManager().LoadItemData(item);

        jMessageDialog    = new JDialog(environ.getMainFrame(), true);
        jMessageDialog.setContentPane(contentPane);

        jMessageDialog.setLocationRelativeTo(environ.getMainFrame());
        jMessageDialog.pack();
        jMessageDialog.setVisible(true);
    }
//  ######################################################
   private void createNorthPanel () {
      jButtonBrowse  = new JButton(STR_BROWSE);
      jButtonBrowse .setToolTipText  ("Choose an object file to load");
      jButtonBrowse.addActionListener(this);

      jboxPanel     = new JPanel(new FlowLayout());
      createJBox();
      jboxPanel.add(jbox);
      jboxPanel.add(jButtonBrowse);

      jMsgPanel    = new JPanel(new FlowLayout());
      JLabel jtxtfieldLabel  = new JLabel("display name", SwingConstants.LEFT);
      jtxtfieldLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
      jtxtfieldMsgName = new JTextField(15);
      jtxtfieldMsgName.setText(Item.getStringVar(sbs_generic.V_STR_USERNAME));
      
      jMsgPanel.add(jtxtfieldLabel);
      jMsgPanel.add(jtxtfieldMsgName);

      jNorthPanel = new JPanel();
      jNorthPanel.setLayout(new BoxLayout(jNorthPanel, BoxLayout.Y_AXIS));

      jNorthPanel.add(jMsgPanel);
      jNorthPanel.add(jboxPanel);

   }
   //######################################################
       private  void createSouthPanel() {

        jButtonOK      = new JButton(STR_OK);
        jButtonCancel  = new JButton(STR_CANCEL);

        jButtonOK.    addActionListener(this);
        jButtonCancel.addActionListener(this);

        jSouthPanel   = new JPanel(new FlowLayout());
        jSouthPanel.add(jButtonOK);
        jSouthPanel.add(jButtonCancel);
      }
   //######################################################
   public void actionPerformed(ActionEvent e) {

      String command = e.getActionCommand();
      do {
      if (STR_BROWSE.equals(command)) {
            
         environ.getDwarf().GenerateFromObj();
         PopulateJBox(null);
         break;
      }

      if (STR_OK.equals(command)) {
//        if (Item.getType() != sbs_generic.FUNCTION &&
//            jtxtfieldMsgName.getText().matches("^\\s*$") == false) {
            
           Item.setStringVar(sbs_generic.V_STR_USERNAME, jtxtfieldMsgName.getText());
           Item.setIntVar(sbs_generic.V_INT_CTYPESIZE, environ.getSymbolTree().getSize());
           
           environ.getSymbolTree().exportTree(Item.getStringVar(sbs_generic.V_STR_DESCFILE));
//           environ.getBinaryFileManager().SaveDumpFile(Item.getStringVar(sbs_generic.V_STR_DATAFILE));
          environ.getItemDataManager().SaveItemData(Item);

//           Item.setValidFile(true);

          
           jMessageDialog.dispose();
//        }
        break;
      }
      if (STR_CANCEL.equals(command)) {
          jMessageDialog.dispose();
          break;
      }
      }while (false);
   }

   //######################################################
   public boolean isValid() {
//     return ValidData;
       return true;
   }
   
   //######################################################
    private void createJBox() {
       JBoxNamesVector= new Vector(500);
       jbox = new JComboBox();

       jboxEdior = new BasicComboBoxEditor();
       jboxEdior.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
                       ValidData = true;
                       jboxString = (String)(jboxEdior.getItem());
                       PopulateJBox(jboxString);
                       jbox.showPopup();
                     }
          });

       PopulateJBox (Item.getStringVar(sbs_generic.V_STR_CTYPENAME));

       jbox.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
                      JComboBox cb = (JComboBox)e.getSource();
                      jboxString   = (String)cb.getSelectedItem();
                      
                      if (jboxString == null)
                          jboxString = "";

                      if (jboxString.equals(
                                     Item.getStringVar(sbs_generic.V_STR_CTYPENAME)) == false) {

                          Item.DeleteFiles();
                          Item.setStringVar(sbs_generic.V_STR_CTYPENAME, jboxString);
                          ChangeJtree(Item);
                          }
                     }
          });

       jbox.setEditable(true);
       jbox.setEditor(jboxEdior);

       //jbox.setSelectedIndex(1);
    }

    //######################################################
  private void PopulateJBox (String regx) {
     int i;
     JBoxNamesVector.removeAllElements();
     jbox.removeAllItems();
     
     switch (Item.getIntVar(sbs_generic.V_INT_TYPE)){
         case sbs_generic.MESSAGE:
         environ.getDwarf().PopulateVector(regx, DWARFdef.DW_TAG_typedef, JBoxNamesVector);
         break;
              
         case sbs_generic.CHECK_VAR:
         case sbs_generic.GET_VAR:
         case sbs_generic.WRITE_VAR:
         environ.getDwarf().PopulateVector(regx, DWARFdef.DW_TAG_variable, JBoxNamesVector);
         break;
              
         case sbs_generic.FUNCTION:
         environ.getDwarf().PopulateVector(regx, DWARFdef.DW_TAG_subprogram, JBoxNamesVector);
         break;
          
         default:
         break;
     }
     
     JBoxNamesArray =  JBoxNamesVector.toArray();
     Arrays.sort(JBoxNamesArray);
     for (i=0; i < JBoxNamesArray.length; i++)
          jbox.addItem(JBoxNamesArray[i]);
  }
  
  //######################################################
  private  void  ChangeJtree (sbs_item item) {

         String VarName = Item.getStringVar(sbs_generic.V_STR_CTYPENAME);
         int  TreeSize;
         
         if (item.isValidFile()) 
             environ.getParseDefFile().loadTree(environ.getTempFullFileName(
                                                item.getStringVar(sbs_generic.V_STR_DESCFILE)));
         else 
             environ.getDwarf().ChangeJtree(VarName);
         
         item.setIntVar(sbs_generic.V_INT_CTYPESIZE, environ.getSymbolTree().getSize());
         environ.getTreeTable().UpdateTable(item);
     }
} // class MessageDialog
