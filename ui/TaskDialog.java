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

import sbs.*;

/**
 * @author  Basile Stefano
 */
public class TaskDialog implements ActionListener{

      JDialog          jTaskDialog;
      JPanel           jcontentPane;

      JLabel           jTaskUserNameLabel;
      JTextField       jTaskUserNameField;

//      JLabel           jTaskMailboxNameLabel;
//      JTextField       jTaskMailboxNameField;
      JLabel           jTaskMailboxLabel;
      JTextField       jTaskMailboxField;

      JLabel           jTaskProcessorLabel;
      JTextField       jTaskProcessorField;

      JButton          jAddButton;
      JCheckBox        jcx_MainTask ;

      String  TaskUserName  = "a";
      String  TaskMailboxName  = "";
      int     TaskMailbox   = -1;
      int     TaskProcessor = -1;  
      
      String  ButtonCommand = "Add";
      JFrame topFrame;

    //#####################################
      public TaskDialog (Environ environ, sbsTask task)
      {
        
        topFrame = environ.getMainFrame();  
          
        jTaskDialog    = new JDialog(topFrame, true);
        jcontentPane   = new JPanel();
        jcontentPane.setLayout(new BoxLayout(jcontentPane, BoxLayout.Y_AXIS));

        jTaskUserNameLabel  = new JLabel("User Name:", SwingConstants.LEFT);;
        jTaskUserNameLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        jTaskUserNameField  = new JTextField(TaskUserName, 10);
        if (task != null)
            jTaskUserNameField.setText(task.get_username());
        
//        jTaskMailboxNameLabel  = new JLabel("Mailbox Name:", SwingConstants.LEFT);;
//        jTaskMailboxNameLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
//        jTaskMailboxNameField  = new JTextField("", 15);
//        if (task != null)
//             jTaskMailboxNameField.setText(task.getMailboxName());

        jTaskMailboxLabel  = new JLabel("Mailbox:", SwingConstants.LEFT);;
        jTaskMailboxLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        jTaskMailboxField  = new JTextField(String.valueOf(TaskMailbox), 8);
        if (task != null)
            jTaskMailboxField.setText(String.valueOf(task.getMailbox()));

        jTaskProcessorLabel  = new JLabel("Processor:", SwingConstants.LEFT);;
        jTaskProcessorLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        jTaskProcessorField  = new JTextField(String.valueOf(TaskProcessor), 8);
        if (task != null)
            jTaskProcessorField.setText(String.valueOf(task.getProcessor()));

        jAddButton  = new JButton(ButtonCommand);
      
        
        jcx_MainTask = new JCheckBox("Task under Test");
        if (task == null || task.isMainTask()) {
            jcx_MainTask.setMnemonic(KeyEvent.VK_T); 
            jcx_MainTask.setSelected(false);
        }
        else jcx_MainTask.setEnabled(false);
                  

        jcontentPane.add(jTaskUserNameLabel);
        jcontentPane.add(jTaskUserNameField);
//        jcontentPane.add(jTaskMailboxNameLabel);
//        jcontentPane.add(jTaskMailboxNameField);
        jcontentPane.add(jTaskMailboxLabel);
        jcontentPane.add(jTaskMailboxField);
        jcontentPane.add(jTaskProcessorLabel);
        jcontentPane.add(jTaskProcessorField);
        jcontentPane.add(jAddButton);
        jcontentPane.add(jcx_MainTask);

        jAddButton.addActionListener(this);

        jTaskDialog.getContentPane().add(jcontentPane, BorderLayout.CENTER);
        jTaskDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        (jTaskDialog.getRootPane()).setDefaultButton(jAddButton);

        jTaskDialog.setLocationRelativeTo(topFrame);
        jTaskDialog.pack();
        jTaskDialog.setVisible(true);
      }
    //#####################################
      public TaskDialog (JFrame topFrame, sbsTask task)
      {
          TaskUserName  = task.get_username();
          TaskMailbox   = task.getMailbox();
          TaskProcessor = task.getProcessor();
          ButtonCommand = "Modify";
      }
    //#####################################
      public void actionPerformed(ActionEvent event) {
         String val;
         TaskUserName    = jTaskUserNameField.getText();
//         TaskMailboxName = jTaskMailboxNameField.getText();

         val  = jTaskMailboxField.getText();
         if (val != null) {
             if (val.matches("^[0-9]+$"))
                 TaskMailbox = Integer.parseInt(val);  
             else if (val.matches("^0[xX][0-9a-fA-F]+$"))
                 TaskMailbox = Integer.parseInt(val,16);  
              else
                 TaskMailbox = -1;  
         }
          val  = jTaskProcessorField.getText();
          if (val != null) {
              if (val.matches("^[0-9]+$"))
                  TaskProcessor = Integer.parseInt(val);  
              else if (val.matches("^0[xX][0-9a-fA-F]+$"))
                  TaskProcessor = Integer.parseInt(val,16);  
              else
                  TaskProcessor = -1;  
          }
          jTaskDialog.dispose();
      }
      
    public boolean isValid() {
        if ( TaskMailbox   >= 0  &&
             TaskProcessor >= 0  &&
             TaskUserName != null )
        return true;
        else
        return false;
    }
    //#####################################
      public String getTaskUserName() {
         return TaskUserName;
      }
    //#####################################
      public int getTaskMailbox() {
         return TaskMailbox;
      } 
    //#####################################
    public String  getTaskMailboxName() {
       return TaskMailboxName;
    }
    //#####################################
      public int  getTaskProcessor() {
         return TaskProcessor;
      }
    //#####################################
      public boolean isMainTask() {
         return jcx_MainTask.isSelected();
      }
  } // class TaskDialog
