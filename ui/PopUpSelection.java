/*
 * Created on 19-ott-2011
 *
 * 
 * @author  Basile Stefano
 */
package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import sbs.*;
/**
 * @author  Basile Stefano
 */
public class PopUpSelection implements ActionListener {

    JPopupMenu Popup;
    JMenuItem  menuItem;
    
    
    public static final int   COMMAND_NONE   =  0; 
    public static final int   COMMAND_COPY   =  1; 
    public static final int   COMMAND_CUT    =  2; 
    public static final int   COMMAND_PASTE  =  4; 
    public static final int   COMMAND_DELETE =  8; 
    public static final int   COMMAND_MODIFY = 16; 
    public static final int   COMMAND_ALL    = COMMAND_COPY   | 
                                               COMMAND_CUT    | 
                                               COMMAND_DELETE | 
                                               COMMAND_MODIFY;     
  
    
    static final String   STRING_COPY   = "Copy"; 
    static final String   STRING_CUT    = "Cut"; 
    static final String   STRING_PASTE  = "Paste"; 
    static final String   STRING_DELETE = "Delete"; 
    static final String   STRING_MODIFY = "Modify"; 

    int CommandSelected;
    int Item_type;
    int Item_num;
    
    sbsTest  Test;
    
    
    public PopUpSelection (sbsTest test, int item_type, int item_num, int cmdMask,
						   Component invoker, int x, int y ) {

           Test = test;
           Item_type = item_type;
           Item_num = item_num;
           String item; 
           
           item = (item_type == sbsTest.SBS_ITEM) ? "item" : "task";

           CommandSelected     = COMMAND_NONE;
           Popup = new JPopupMenu();

           if ((cmdMask & COMMAND_COPY) != 0) {
               menuItem = new JMenuItem(STRING_COPY + " " + item);
               menuItem.setActionCommand(STRING_COPY);
               menuItem.addActionListener(this);
               Popup.add(menuItem);
           }
           if ((cmdMask & COMMAND_CUT) != 0) {
               menuItem = new JMenuItem(STRING_CUT + " " + item);
               menuItem.setActionCommand(STRING_CUT);
               menuItem.addActionListener(this);
               Popup.add(menuItem);
           }
           if ((cmdMask & COMMAND_PASTE) != 0) {
               menuItem = new JMenuItem(STRING_PASTE + " " + item);
               menuItem.setActionCommand(STRING_PASTE);
               menuItem.addActionListener(this);
               Popup.add(menuItem);
           }
           if ((cmdMask & COMMAND_DELETE) != 0) {
               menuItem = new JMenuItem(STRING_DELETE + " " + item);
               menuItem.setActionCommand(STRING_DELETE);
               menuItem.addActionListener(this);
               Popup.add(menuItem);
           }
           if ((cmdMask & COMMAND_MODIFY) != 0) {
               menuItem = new JMenuItem(STRING_MODIFY + " " + item);
               menuItem.setActionCommand(STRING_MODIFY);
               menuItem.addActionListener(this);
               Popup.add(menuItem);
           }
           Popup.show(invoker, x, y);
       }

    public void actionPerformed(ActionEvent e) {
      String s = null;
      do 
      {
        CommandSelected = COMMAND_NONE;
        if (STRING_COPY.equals(e.getActionCommand())) { //Copy
            CommandSelected = COMMAND_COPY;
            break;
        }
        if (STRING_CUT.equals(e.getActionCommand())) { //Cut
            CommandSelected = COMMAND_CUT;
            break;
        }
        if (STRING_PASTE.equals(e.getActionCommand())) { //Paste
            CommandSelected = COMMAND_PASTE;
            break;
        }
        if (STRING_DELETE.equals(e.getActionCommand())) { //Delete
            CommandSelected = COMMAND_DELETE;
            break;
        }
        if (STRING_MODIFY.equals(e.getActionCommand())) { //Modify
            CommandSelected = COMMAND_MODIFY;
            break;
        }
      }
      while(false);
      if (Item_type == sbsTest.SBS_TASK)
          Test.TaskSelectionCommand(Item_num, CommandSelected);
      else
          Test.ItemSelectionCommand (Item_num, CommandSelected);
    }
    
 
    public int  getCommandSelected () {
          return CommandSelected;
    }
    
    
}
    
    
    
    
    
