/*
 * Created on Dec 18, 2011
 *
 * 
 * @author  Basile Stefano
 */
package  ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import common.Environ;

/**
 * @author   Basile Stefano
 */
public class DataBaseLogDialog implements ActionListener {
    

    static final String STR_CONCT_ERROR = "Unable to Connect to Database";
    static final String STR_CONNECTION  = "Connection";
    static final String STR_USER_ID     = "User ID";
    static final String STR_PASSWORD    = "Password";

    static final String STR_CONNECT     = "Connect";
    static final String STR_CANCEL      = "Cancel";

    JDialog    jdg_LogDialog;
    JPanel     jpl_contentPane;
    JPanel     jpl_ButtonPanel;
    JPanel     jpl_CenterPanel;
    JButton    jbt_OKButton;
    JButton    jbt_CancelButton;
    
    JLabel     jlb_connection;
    JLabel     jlb_UserID;
    JLabel     jlb_Password;
    JLabel     jlb_Empty1;
    JLabel     jlb_conct_err;
    
    
//    String[]   str_DataBase = { "xhat80", "xhat70"};
    String[]   str_DataBase = { "status_manager_80"};
    JComboBox  jbx_connection;
    JCheckBox  jcx_SaveSettings ;
    
    JTextField     jtx_UserID;
    JPasswordField jpw_Password;

    boolean connected;
    
    Environ         environ;
    
    char [] Password;
    Dimension buttonDim; 
    
    public DataBaseLogDialog ( Environ environ) 
    {
        this.environ = environ;
        connected    = false;
        
        jpl_contentPane  = new JPanel();
        jpl_CenterPanel  = new JPanel();
        jpl_ButtonPanel  = new JPanel();
        
        jpl_contentPane.setLayout(new BorderLayout());
        jpl_CenterPanel.setLayout(new GridLayout(4,2,2,2));
        jpl_ButtonPanel.setLayout(new BoxLayout(jpl_ButtonPanel, BoxLayout.Y_AXIS));

        jlb_connection = new JLabel(STR_CONNECTION); 
        jlb_UserID     = new JLabel(STR_USER_ID); 
        jlb_Password   = new JLabel(STR_PASSWORD);
        jlb_conct_err  = new JLabel();
        jlb_Empty1     = new JLabel();
        
        jtx_UserID     = new JTextField(System.getProperty("user.name"), 15);
        jpw_Password   = new JPasswordField(15);
        
        jcx_SaveSettings = new JCheckBox("Save Settings");
        jcx_SaveSettings.setMnemonic(KeyEvent.VK_S); 
        jcx_SaveSettings.setSelected(true);
            
        jbx_connection = new JComboBox  (str_DataBase);
        jbx_connection.addActionListener(this);

        buttonDim  = new Dimension(90, 25);

        jbt_OKButton      = new JButton (STR_CONNECT);
        jbt_OKButton.setActionCommand   (STR_CONNECT);
        jbt_OKButton.addActionListener(this);
        
        jbt_CancelButton  = new JButton  (STR_CANCEL);
        jbt_CancelButton.setActionCommand(STR_CANCEL);
        jbt_CancelButton.addActionListener(this);

        jbt_OKButton.setMinimumSize  (buttonDim);
        jbt_OKButton.setPreferredSize(buttonDim);
        jbt_OKButton.setMaximumSize  (buttonDim);

        jbt_CancelButton.setMinimumSize  (buttonDim);
        jbt_CancelButton.setPreferredSize(buttonDim);
        jbt_CancelButton.setMaximumSize  (buttonDim);

        jpl_CenterPanel.add(jlb_connection);
        jpl_CenterPanel.add(jcx_SaveSettings);
        jpl_CenterPanel.add(jbx_connection);
        jpl_CenterPanel.add(jlb_Empty1);
        jpl_CenterPanel.add(jlb_UserID);
        jpl_CenterPanel.add(jlb_Password);
        jpl_CenterPanel.add(jtx_UserID);
        jpl_CenterPanel.add(jpw_Password);

        jpl_ButtonPanel.add(jbt_OKButton);
        jpl_ButtonPanel.add(jbt_CancelButton);

        
        jpl_contentPane.add (jpl_CenterPanel,BorderLayout.CENTER);
        jpl_contentPane.add (jpl_ButtonPanel,BorderLayout.EAST);
        jpl_contentPane.add (jlb_conct_err,  BorderLayout.SOUTH);
        
        //jpl_contentPane.setBorder(BorderFactory.createCompoundBorder());
        
        jdg_LogDialog    = new JDialog(environ.getMainFrame(), true);
        jdg_LogDialog.setContentPane(jpl_contentPane);
        
        (jdg_LogDialog.getRootPane()).setDefaultButton(jbt_OKButton);

        jdg_LogDialog.setLocationRelativeTo(environ.getMainFrame());
        jdg_LogDialog.pack();
        jdg_LogDialog.setVisible(true);
    }
    

   //######################################################
   public void actionPerformed(ActionEvent e) {
       
       String source = e.getActionCommand();
       
       do
       {   
//         if (STR_CONNECTION.equals(source)) { 
//             JComboBox cb = (JComboBox)e.getSource();
//             break;
//         }
         if (STR_CONNECT.equals(source)) {
             String User = jtx_UserID.getText();
             String DataBase = (String)jbx_connection.getSelectedItem();
             
             System.out.println("Connecting to database "+ DataBase + " as user:" + User);
             connected = environ.getDataBase().Connect("jdbc:mysql://localhost/" + 
                                                           DataBase, 
                                                           User, 
                                                           new String (jpw_Password.getPassword()));
             if (connected == false)
                 jlb_conct_err.setText(STR_CONCT_ERROR);
             else
                 jdg_LogDialog.dispose();
             
             break;
         }
         if (STR_CANCEL.equals(source)) { 
             jdg_LogDialog.dispose();
             break;
         }
       }
       while(false);
   }
   //######################################################
   public boolean isConnected() {
          return connected;    
   }
    
}
