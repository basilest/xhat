/*
 * Created on 23-nov-2011
 *
 * 
 * @author  Basile Stefano
 */
package ui;

import common.Environ;

import javax.swing.*;
import javax.swing.event.*; 
import java.awt.*;
import java.awt.event.*;

import sbs.*;
import java.util.*;

import dwarf.*;

/**
 * @author  Basile Stefano
 */
public class TestDialog implements ActionListener,
                                           ListSelectionListener {

      final static String  STR_OK     = "Ok";
      final static String  STR_CANCEL = "Cancel";
      final static String  STR_BROWSE = "Browse..";
      final static String  STR_LTASK  = "Main Task";
      final static String  STR_LFUN   = "Init Function";
      final static String  STR_LEVEL  = "Init Level";
      final static String  STR_LFSH   = "Feature";

      JDialog          jFunDialog;

      JPanel           jcontentPane;
      JPanel           jCenterPane;
      JPanel           jWestPane;
      JPanel           jButtonPane;
      JScrollPane      jlistScroller;

      JLabel           jTestNameLabel;
      JLabel           jTaskLabel;
      JLabel           jFuntionLabel;
      JLabel           jLevelLabel;
      JLabel           jFeatureLbl;
      JTextField       jTaskTextField;
      JTextField       jFunctionTextField;
      JTextField       jLevelTextField;
      JTextField       jTestNameField;
      JTextField       jFeatureText;

      JButton          jButtonF;
      JButton          jButtonOK;
      JButton          jButtonCancel;
      JButton          jButtonBrowse;

      DefaultListModel  listModel;
      JList             jFunList;
      
      Vector            FunNamesVector;
      Object[]          ListFunNamesArray;
      
      sbsTest           test;
      Environ           environ;
      
      boolean           Valid;
      
    //#####################################
    public TestDialog (Environ environ, sbsTest test)  {
          
        this.environ = environ;
        this.test    = test;
        
        Valid  = false;

        jcontentPane   = new JPanel(new BorderLayout());

        jWestPane      = new JPanel();
        jCenterPane    = new JPanel();
        jButtonPane    = new JPanel(new FlowLayout());

        jWestPane.setLayout  (new BoxLayout(jWestPane,   BoxLayout.Y_AXIS));
        jCenterPane.setLayout(new BoxLayout(jCenterPane, BoxLayout.Y_AXIS));

        FunNamesVector = new Vector(100);    // 100 functions per File is supposed to be rigth big
        
        listModel = new DefaultListModel();
        
        jFunList = new JList (listModel);
        jFunList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jFunList.setLayoutOrientation(JList.VERTICAL);
        jFunList.addListSelectionListener(this);
        
        jlistScroller  = new JScrollPane(jFunList);

        CreateFunList();

        jFunDialog     = new JDialog(environ.getMainFrame(), true);

        jTaskLabel    = new JLabel(STR_LTASK, SwingConstants.LEFT);
        jFuntionLabel = new JLabel(STR_LFUN,  SwingConstants.LEFT);
        jLevelLabel   = new JLabel(STR_LEVEL, SwingConstants.LEFT);

        jTestNameLabel  = new JLabel("Test Name:", SwingConstants.LEFT);
        jTestNameLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        jTestNameField  = new JTextField(test.getTestUserName(), 25);

        jFeatureLbl     = new JLabel(STR_LFSH, SwingConstants.LEFT);
        jFeatureLbl.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        jFeatureText    = new JTextField(test.getFeature(), 25);

//        jTaskLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
//        jFuntionLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        jTaskTextField     = new JTextField(test.getInitFunction(sbsTest.INIT_TASK), 25);
        jFunctionTextField = new JTextField(test.getInitFunction(sbsTest.INIT_FUN),  25);
        jLevelTextField    = new JTextField(test.getInitFunction(sbsTest.INIT_LEVEL), 5);
        
//        jTaskTextField.setBorder(BorderFactory.createTitledBorder("First  Init Fun."));
//        jFunctionTextField.setBorder(BorderFactory.createTitledBorder("Second Init Fun."));
        
        jButtonOK     = new JButton(STR_OK);
        jButtonCancel = new JButton(STR_CANCEL);
        jButtonF      = new JButton(new ImageIcon("images/btn-back_inactive.gif"));
        jButtonBrowse = new JButton(STR_BROWSE);

        jButtonF.setToolTipText(STR_LFUN);

        jButtonOK     .setActionCommand(STR_OK);
        jButtonCancel .setActionCommand(STR_CANCEL);
        jButtonF      .setActionCommand(STR_LFUN);
        jButtonBrowse .setActionCommand(STR_BROWSE);

        jButtonOK.    addActionListener(this);
        jButtonCancel.addActionListener(this);
        jButtonF.     addActionListener(this);
        jButtonBrowse.addActionListener(this);
      
//        jButtonF1.setSelected(false);
//        jButtonF2.setSelected(false);

        jWestPane.add(Box.createVerticalGlue());
        jWestPane.add(jTestNameLabel);
        jWestPane.add(jTestNameField);
        jWestPane.add(jFuntionLabel);
        jWestPane.add(jFunctionTextField);
        jWestPane.add(jFeatureLbl);
        jWestPane.add(jFeatureText);
        jWestPane.add(Box.createVerticalGlue());
        jWestPane.add(jTaskLabel);
        jWestPane.add(jTaskTextField);
        jWestPane.add(Box.createVerticalGlue());
        jWestPane.add(jLevelLabel);
        jWestPane.add(jLevelTextField);
        
        jButtonPane.add(jButtonOK);
        jButtonPane.add(jButtonCancel);
        jButtonPane.add(jButtonBrowse);
        
        jCenterPane.add(Box.createVerticalGlue());
        jCenterPane.add(jButtonF);
//        jCenterPane.add(Box.createVerticalGlue());
//        jCenterPane.add(new JSeparator(SwingConstants.HORIZONTAL));
        jCenterPane.add(Box.createVerticalGlue());
        
        jcontentPane.add (jWestPane,     BorderLayout.WEST);
        jcontentPane.add (jCenterPane,   BorderLayout.CENTER);
        jcontentPane.add (jlistScroller, BorderLayout.EAST);
        jcontentPane.add (jButtonPane,   BorderLayout.SOUTH);
        
//        jcontentPane.setPreferredSize(new Dimension(400, 350));

        jFunDialog.setContentPane(jcontentPane);
        jFunDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
       (jFunDialog.getRootPane()).setDefaultButton(jButtonOK);
        jFunDialog.setLocationRelativeTo(environ.getMainFrame());
        jFunDialog.pack();
        jFunDialog.setVisible(true);
      }

    //#####################################
      private void CreateFunList ()  {
          int i;
          FunNamesVector.removeAllElements();
          listModel.removeAllElements();
          
          environ.getDwarf().PopulateVector(null, DWARFdef.DW_TAG_subprogram, FunNamesVector);
          ListFunNamesArray = FunNamesVector.toArray();
          Arrays.sort(ListFunNamesArray);
          for (i=0; i < ListFunNamesArray.length; i++)
               listModel.addElement(ListFunNamesArray[i]);   
     }
    //#####################################
     public boolean isValid() {
//         return    Valid;
           return    Valid;
     }
    //#####################################
      public void actionPerformed(ActionEvent e) {

         String command = e.getActionCommand();
         String str;
         Object value;
         
         do {
            if (STR_OK.equals(command)) {
                do {
//                    str = jTestNameField.getText();
//                    if (str.length() == 0) break;
//                    str = jTaskTextField.getText();
//                    if (str.length() == 0) break;
//                    str = jFunctionTextField.getText();
//                    if (str.length() == 0) break;
//                    str = jLevelTextField.getText();
//                    if (str.length() == 0) break;
//                    test.setTestUserName(jTestNameField.getText());
//                    test.setInitFunction(sbsTest.INIT_TASK, jTaskTextField.getText());
//                    test.setInitFunction(sbsTest.INIT_FUN,  jFunctionTextField.getText());
//                    test.setInitFunction(sbsTest.INIT_LEVEL,jLevelTextField.getText());
                    test.setTestUserName(jTestNameField.getText());
                    test.setFeature(jFeatureText.getText());
                    test.setInitFunction(sbsTest.INIT_TASK, jTaskTextField.getText());
                    test.setInitFunction(sbsTest.INIT_FUN,  jFunctionTextField.getText());
                    test.setInitFunction(sbsTest.INIT_LEVEL,jLevelTextField.getText());
                    jFunDialog.dispose();
                    Valid = true;
                } while (false);
                break;
            }
            if (STR_LFUN.equals(command)) {
                value = jFunList.getSelectedValue();
                if (value != null)
                jFunctionTextField.setText(value.toString());
                break;
            }
            if (STR_CANCEL.equals(command)) {
                jFunDialog.dispose();
                break;
            }         
            if (STR_BROWSE.equals(command)) {
                environ.getDwarf().GenerateFromObj();
                CreateFunList();
                break;
            }         

         } while(false) ;
      }
      //#####################################
      public void valueChanged(ListSelectionEvent e) {
          if (e.getValueIsAdjusting() == false) {

              if (jFunList.getSelectedIndex() == -1) {
              //No selection, disable buttons.
//              jButtonF.setEnabled(false);

              } else {
              //Selection, enable buttons.
//              jButtonF.setEnabled(true);
              }
          }
      }
      

      
  } // class TestDialog
