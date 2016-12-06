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
public class WaitDialog implements ActionListener{

      JDialog          jWaitDialog;
      JPanel           jcontentPane;

      JLabel           jWaitLabel;

      JButton          jAddButton;

      int     waitSec = 1;
      
      String  ButtonCommand = "Add";

      SpinnerNumberModel spinModel;
      JSpinner           spinner;      
      
      sbs_item           waitItem;
      

    //#####################################
      public WaitDialog (Environ environ, sbs_item item)  {
          
        waitItem = item;
        
        jWaitDialog    = new JDialog(environ.getMainFrame(), true);
        jcontentPane   = new JPanel();
        jcontentPane.setLayout(new FlowLayout());

        jWaitLabel  = new JLabel("Wait (seconds):", SwingConstants.LEFT);
        jWaitLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        if (waitItem != null)
            waitSec = waitItem.getIntVar(sbs_generic.V_INT_TIME);

        else
            waitSec = 10;
        
        spinModel  = new SpinnerNumberModel(waitSec, 0, 1000, 1);
        spinner    = new JSpinner(spinModel);        
        
        jAddButton  = new JButton(ButtonCommand);
      
        jcontentPane.add(jWaitLabel);
        jcontentPane.add(spinner);
        jcontentPane.add(jAddButton);

        jAddButton.addActionListener(this);

        jWaitDialog.getContentPane().add(jcontentPane, BorderLayout.CENTER);
        jWaitDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        (jWaitDialog.getRootPane()).setDefaultButton(jAddButton);

        jWaitDialog.setLocationRelativeTo(environ.getMainFrame());
        jWaitDialog.pack();
        jWaitDialog.setVisible(true);
      }

    //######################################################
    public boolean isValid() {
        return true;
    }
    //#####################################
      public void actionPerformed(ActionEvent event) {
          waitSec = ((Integer)(spinModel.getValue())).intValue();
          waitItem.setIntVar(sbs_generic.V_INT_TIME, waitSec);
          jWaitDialog.dispose();
      }
      
  } // class WaitDialog
