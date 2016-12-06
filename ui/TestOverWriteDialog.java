/*
 * Created on 23-nov-2011
 *
 * 
 * @author  Basile Stefano
 */
package ui;

import common.Environ;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author  Basile Stefano
 */
public class TestOverWriteDialog implements ActionListener{

    static final String STR_OVER     = "OverWrite";
    static final String STR_NEW      = "New Test";
    static final String STR_CANCEL   = "Cancel";
    
    public static final int   INT_OVER   = 1;
    public static final int   INT_NEW    = 2;
    public static final int   INT_CANCEL = 3;


      JDialog          dialog;
      JPanel           jcontentPane;
      JPanel           jButtonPane;
      JLabel           label;
      JButton          jButtonOver;
      JButton          jButtonNew;
      JButton          jButtonCancel;
      
      int choice;

      
    //#####################################
      public TestOverWriteDialog (Environ environ)  {
          
        dialog    = new JDialog(environ.getMainFrame(), true);
        choice    = INT_CANCEL;

        jcontentPane   = new JPanel();
        jcontentPane.setLayout(new BorderLayout());
        jButtonPane    = new JPanel(new FlowLayout());


        label  = new JLabel("Overwrite Test ?");
        label.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        jButtonOver   = new JButton(STR_OVER);
        jButtonNew    = new JButton(STR_NEW);
        jButtonCancel = new JButton(STR_CANCEL);

        jButtonOver   .setActionCommand(STR_OVER);
        jButtonNew    .setActionCommand(STR_NEW);
        jButtonCancel .setActionCommand(STR_CANCEL);

        jButtonOver   .addActionListener(this);
        jButtonNew    .addActionListener(this);
        jButtonCancel .addActionListener(this);

        jButtonPane.add(jButtonOver);
        jButtonPane.add(jButtonNew);
        jButtonPane.add(jButtonCancel);
        
        jcontentPane.add(label,       BorderLayout.CENTER);
        jcontentPane.add(jButtonPane, BorderLayout.SOUTH);

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        (dialog.getRootPane()).setDefaultButton(jButtonCancel);

        dialog.setContentPane(jcontentPane);

        dialog.setLocationRelativeTo(environ.getMainFrame());
        dialog.pack();
        dialog.setVisible(true);
        
      }

    //######################################################
    public int getChoice() {
        return choice;
    }
    //#####################################
      public void actionPerformed(ActionEvent e) {
          String command = e.getActionCommand();
          do {
             if (STR_OVER.equals(command)) {
                 dialog.dispose();
                 choice = INT_OVER;
                 break;
             }
             if (STR_NEW.equals(command)) {
                 dialog.dispose();
                 choice = INT_NEW;
                 break;
             }         
             if (STR_CANCEL.equals(command)) {
                 dialog.dispose();
                 choice = INT_CANCEL;
                 break;
             }         
          } while(false) ;
      }
      
  } // class WaitDialog
