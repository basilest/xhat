/*
 * Created on Dec 18, 2011
 *
 * 
 * @author  Basile Stefano
 */
package  sbs;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;
import java.util.*;

import java.io.*;
import common.Environ;
import ui.*;

/**
 * @author   Basile Stefano
 */
/* Used by MainFrame.java. */
public class  sbsTest extends      JInternalFrame
		              implements   MouseListener,
		                           MouseMotionListener,
		                           WindowListener {

                                           
    public final static int  INIT_TASK   = 1;
    public final static int  INIT_FUN    = 2;
    public final static int  INIT_LEVEL  = 3;

    public final static int  SBS_TASK = 1;
    public final static int  SBS_ITEM = 2;
 
    static int TaskMinGapX = 35;
    static int TaskOriginX = 15;
    static int TaskOriginY = 15;

    static int selectionTolleranceX = 3;
    static int selectionTolleranceY = 3;
    
    static final   int xOffset    = 30;
    static final   int yOffset    = 30;

    static int sbsTestopenCount = 0;

    int    mousePressedX;
    int    mousePressedY;
    CopyCutItem    copyCut;

    int    CurrentSbsItem;
    int    testArea;
    int    initLevel;
    int    DBKey;
    int    DBConfigKey;

    String testUserName;
    String testUniqueName;
    String Feature;
    String initTask;
    String initFunction;
    String ConfigName;

    Vector TaskList;
    Vector ItemList;

    Dimension ClientArea; //indicates area taken up by graphics


  	JPanel          contentPane;
  	DrawingPanel    drawingPanel;
    JScrollPane     drawingScrollPane;
    
    JTextArea       CmntArea;
    JScrollPane     CmntAreaScrollPane;
    
    JSplitPane      splitPane;
                               

  	Line2D.Double   line;
  	
    
    Environ    environ;

    //#############################
    public sbsTest (Environ environ, String userName, int TestDBKey) {
        super(userName,
              true, //resizable
              true, //closable
              true, //maximizable
              true);//iconifiable

        this.environ = environ;
        DBKey = TestDBKey;
        
        testArea     = 1;
        int testNum = environ.getIntVar(Environ.V_INT_TESTNUM) + 1;
        environ.setIntVar(Environ.V_INT_TESTNUM, testNum); 
                          
        if (TestDBKey < 0)
            testUniqueName = environ.getDataBase().getNumTest() + "_";
        else
            testUniqueName = DBKey + "_";
        
        initTask     = "";
        initFunction = "";
        Feature      = "";
        ConfigName   = "";
        initLevel    = 0;
        DBConfigKey  = -1;
        
        copyCut = new CopyCutItem();
        
        sbsTestopenCount++;

        ClientArea = new Dimension(0,0);

        testUserName = userName;
        
        CurrentSbsItem = 0;

        TaskList = new Vector(5);
    	ItemList = new Vector(10);

    	line = new Line2D.Double(0,0,0,0);

        contentPane   = new JPanel(new BorderLayout());
        drawingPanel  = new DrawingPanel();

    	drawingPanel.addMouseListener(this);
    	drawingPanel.addMouseMotionListener(this);
        drawingPanel.setOpaque(true);
        drawingPanel.setBackground(Color.white);
    	//contentPane.addWindowListener(this);

        drawingScrollPane = new JScrollPane(drawingPanel);
//    	scrollPane .setViewportBorder(BorderFactory.createLineBorder(Color.red));

        //scrollPane.setViewportView(drawingPanel);
//        scrollPane.setVerticalScrollBarPolicy  (JScrollPane.  VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        drawingScrollPane.setPreferredSize(new Dimension(200,200));
        
        
        CmntArea = new JTextArea();
        CmntArea.setMargin(new Insets(3,5,3,4));
        CmntArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        CmntAreaScrollPane = new JScrollPane(CmntArea);
             
        CmntAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        CmntAreaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
             
//        CmntAreaScrollPane.setPreferredSize(new Dimension(250, 250));
        CmntAreaScrollPane.setBorder(BorderFactory.createLoweredBevelBorder());
    
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                   drawingScrollPane, CmntAreaScrollPane);
        
        
        
    	contentPane.add(splitPane, BorderLayout.CENTER);

        //contentPane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        setSize(300,300);
        setLocation(xOffset*sbsTestopenCount, yOffset*sbsTestopenCount);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 

        setContentPane(contentPane);
    }
    //#########    toString    ###########
    public String toString()  {
           return testUserName;
    }
    //#############################
    public void setDBKey(int key) {
           DBKey = key;
    }
    public void setDBConfKey(int key) {
        DBConfigKey = key;
    }
    public void setItemNum(int num) {
           CurrentSbsItem = num;
    }
    public void setFeature(String fsh) {
        Feature = fsh;
    }
    public void setConfigName(String name) {
        ConfigName = name;
    }
    //#############################
    public void setTestUserName(String name) {
           testUserName = name;
           setTitle(name);
    }
//    public void setTestUniqueName(String name) {
//           testUniqueName = name;
//    }
    public void setInitFunction (int funType, String val) {
           switch(funType) {
           case INIT_TASK: initTask     = val; break;
           case INIT_FUN:  initFunction = val; break;
           case INIT_LEVEL:initLevel    = Integer.parseInt(val); break;
           default : break;
           }
    }
    public String getInitFunction (int funType) {
           switch(funType) {
           case INIT_TASK: return initTask;
           case INIT_FUN:  return initFunction;
           case INIT_LEVEL:return String.valueOf(initLevel);
           default :        return "";
           }
    }
    //#############################
    public String getTestUserName() {
        return testUserName;
    }
    public String getTestUniqueName() {
        return testUniqueName;
    }
    public String getDescFileName() {
        return testUniqueName + "desc.txt";
    }
    public String getCommentFileName() {
        return testUniqueName + "cmt.txt";
    }
    public String getFeature() {
        return Feature;
    }
    public String getConfigName() {
        return ConfigName;
    }
    public String getConfigFileName() {
        return testUniqueName + "confg.txt";
    }
    //#############################
    public int getTestArea() {
        return testArea;
    }
    public int getDBKey() {
        return DBKey;
    }
    public int getDBConfKey() {
        return DBConfigKey;
    }
    //#############################
    // --------------------------------
    // IMPLEMENT  MOUSE   LISTENERS (MouseListener and MouseMotionListener)
    // --------------------------------
    // --------------------------------MouseListener
    // Called just after the user clicks
    // the listened-to component.
    public void mouseClicked(MouseEvent e){

    	switch (environ.getMainFrame().getToolButtonSelected())
    	{
      	  case MainFrame.TOOL_BUTTON_NEW_MESSAGE:
              if (e.getButton() == MouseEvent.BUTTON1)
    	      {
    	  	     mousePressedX = e.getX();
    	  	     mousePressedY = e.getY();
    	      }
              break;
          default:
              environ.getMainFrame().resetToolButtonSelected();
              break;
    	}
    }
    //#############################
    // Called just after the cursor enters
    // the bounds of the listened-to component.
    public void mouseEntered(MouseEvent e){

    }
    //#############################
    // Called just after the cursor exits
    // the bounds of the listened-to component.
    public void mouseExited(MouseEvent e){

    }
    //#############################
    // Called just after the user presses a mouse button while
    // the cursor is over the listened-to component.
    public void mousePressed(MouseEvent e){

        int x1 ;
        int x2 ;
        Color oldColor;
        PopUpSelection popup;

        int       taskNum;
        int       itemNum;
        sbsTask   SelectedTask;
        sbs_item  SelectedItem;

        switch (environ.getMainFrame().getToolButtonSelected())
        {
          case MainFrame.TOOL_BUTTON_NEW_MESSAGE:
          if (e.getButton() == MouseEvent.BUTTON1)
          {
                 mousePressedX = e.getX();
                 mousePressedY = e.getY();
          }
          break;

          case MainFrame.TOOL_BUTTON_CHECK_VAR:
          case MainFrame.TOOL_BUTTON_GET_VAR:
          case MainFrame.TOOL_BUTTON_WRITE_VAR:
          case MainFrame.TOOL_BUTTON_ADD_WAIT:
          case MainFrame.TOOL_BUTTON_FUNCTION:
          if (e.getButton() == MouseEvent.BUTTON1)
          {
             x1 = ((sbsTask)TaskList.get(0)).getLineX();
             x2 = ((sbsTask)TaskList.get(TaskList.size()-1)).getLineX();
             x1 -= 3;
             x2 += 3;
             addNewReadWriteWait (x1, x2, e.getY());
          }
          environ.getMainFrame().resetToolButtonSelected();
          break;


          case MainFrame.TOOL_BUTTON_SELECTION:
          taskNum = getSelectedTask (e.getX());
          if (taskNum >= 0) {
              SelectedTask = (sbsTask)TaskList.get(taskNum);
              oldColor = SelectedTask.getColor();
              SelectedTask.setColor(Color.red);
              paintAllShapes ();
              SelectedTask.setColor(oldColor);
              popup = new PopUpSelection(this, SBS_TASK, taskNum, 
                                         PopUpSelection.COMMAND_DELETE | PopUpSelection.COMMAND_MODIFY,
                                         e.getComponent(), e.getX(), e.getY());
//              TaskSelectionCommand (taskNum, popup.getCommandSelected());
          }
          else {
               itemNum = getSelectedItem (e.getY());
               if (itemNum >= 0) {
                   SelectedItem  = (sbs_item) ItemList.get(itemNum);
                   oldColor = SelectedItem.getColor();
                   SelectedItem.setColor(Color.red);
                   paintAllShapes ();
                   SelectedItem.setColor(oldColor);
                   popup = new PopUpSelection(this, SBS_ITEM, itemNum, PopUpSelection.COMMAND_ALL, 
                                              e.getComponent(), e.getX(), e.getY());
//                   ItemSelectionCommand (itemNum, popup.getCommandSelected());
               }
               else {
                   if (copyCut.type != PopUpSelection.COMMAND_NONE) {
                       popup = new PopUpSelection(this, SBS_ITEM, -1, 
                                                  PopUpSelection.COMMAND_PASTE,
                                                  e.getComponent(), e.getX(), e.getY());
                       copyCut.pasteY = e.getY();
                   }
               }
          }
          environ.getMainFrame().resetToolButtonSelected();
          break;

          default:
          environ.getMainFrame().resetToolButtonSelected();
          break;
        }

    }
    //#############################
    public  void  TaskSelectionCommand ( int taskNum, int command ) {

        int i;
        sbsTask   SelectedTask = (sbsTask)TaskList.get(taskNum);
        sbs_item  item;

        switch (command) {
             case PopUpSelection.COMMAND_DELETE:
             if (SelectedTask.isMainTask() == false)
             {
                 for (i=ItemList.size()-1; i >=0 ; i--) {  
                      item  = (sbs_item)ItemList.get(i);
                      if (item.getIntVar(sbs_generic.V_INT_TYPE) == sbs_generic.MESSAGE &&
                         (item.concernTask(SelectedTask)))
                          ItemList.remove(i);
                 }
                 TaskList.remove(taskNum);
             }
             break;
            
             case PopUpSelection.COMMAND_MODIFY:
             TaskDialog  dialog = new TaskDialog (environ, SelectedTask);
             if (dialog.isValid()) {
                 SelectedTask.set_username  (dialog.getTaskUserName()); 
                 SelectedTask.setMailboxName(dialog.getTaskMailboxName());
                 SelectedTask.setMailbox    (dialog.getTaskMailbox());
                 SelectedTask.setProcessor  (dialog.getTaskProcessor());
             } 
             break;
            
             default:
             break;
        }
//        paintAllShapes ();
        (environ.getMainFrame()).repaint();

    }
    //#############################
    public  void ItemSelectionCommand ( int itemNum, int command ) {
    
        sbs_item  item;
        MessageDialog  dialog ;

        switch (command) {
            
        case PopUpSelection.COMMAND_DELETE:
             ShiftBackItems(itemNum); 
             ItemList.remove(itemNum);
             resizeScrollable();
             copyCut.reset();
        break;
            
        case PopUpSelection.COMMAND_MODIFY:
        copyCut.reset();
        item = (sbs_item) ItemList.get(itemNum);
        switch (item.getIntVar(sbs_generic.V_INT_TYPE)) {
            
             case  sbs_generic.WRITE_VAR :   
             case  sbs_generic.CHECK_VAR :
             case  sbs_generic.GET_VAR :
             case  sbs_generic.MESSAGE :
             case  sbs_generic.FUNCTION :
             dialog = new MessageDialog(environ, item);
             break;
                 
             case  sbs_generic.WAIT :
             WaitDialog dw = new WaitDialog (environ, item);
             break;
             
             default:  
             break;
        }
        break;
        
        case PopUpSelection.COMMAND_COPY:
        case PopUpSelection.COMMAND_CUT:
             copyCut.ItemNum = itemNum;
             copyCut.type    = command;
             break;
             
        case PopUpSelection.COMMAND_PASTE:
             PasteItem();
             copyCut.reset();
             break;
             
        default:
             copyCut.reset();
             break;
        }
//        paintAllShapes ();
        (environ.getMainFrame()).repaint();
  }
  //#############################
    // Called just after the user releases a mouse button after
    // a mouse press over the listened-to component.
    public void mouseReleased(MouseEvent e){

        switch (environ.getMainFrame().getToolButtonSelected())
        {
          case MainFrame.TOOL_BUTTON_NEW_MESSAGE:
          if (e.getButton() == MouseEvent.BUTTON1)
          {
              addNewMessage(mousePressedX, e.getX(), mousePressedY);
              environ.getMainFrame().resetToolButtonSelected();

          }
          break;

          default:
          break;
        }
//        environ.getMainFrame().resetToolButtonSelected();
    }
    //#############################
    // --------------------------------MouseMotionListener
    // Invoked when a mouse button is pressed
    //  on a component and then dragged.
    public void mouseDragged(MouseEvent e){
    //      textArea.append("g");
    //  	  if (e.getButton() == MouseEvent.BUTTON1)
    // 	  {
   	//        g2 = (Graphics2D) drawingPanel.getGraphics();
   	//        line.setLine(mousePressedX, mousePressedY, e.getX(), e.getY());
    //        g2.draw(line);
    //	  }
    }
    //#############################
    // Invoked when the mouse cursor has been moved
    // onto a component but no buttons have been pushed.
    public void mouseMoved(MouseEvent e){
           //System.out.println("x=" + e.getX() + "y=" + e.getY());

    }
    //#############################
    public void dispose() {
      environ.getMainFrame().RemoveTest(this);
      super.dispose();
    }
    //#############################
    // 1 - Invoked when the Window is set to be the active Window.
    public void windowActivated(WindowEvent e) {
          paintAllShapes ();

          //drawingPanel.paintAllShapes(getGraphics());
          //validateTree();
          //paintChildren(getGraphics());
          //paintComponents(getGraphics());
          //revalidate();
          //repaint();
    }
    //#############################
    // 2 - Invoked when a window has been closed as the result of calling dispose on the window.
    public void windowClosed(WindowEvent e) {
    }
    // 3 - Invoked when the user attempts to close the window from the window's system menu.
    public void windowClosing(WindowEvent e) { 
    }
    // 4 - Invoked when a Window is no longer the active Window.
    public void windowDeactivated(WindowEvent e) { 
    }
    // 5 - Invoked when a window is changed from a minimized to a normal state.
    public void windowDeiconified(WindowEvent e) {
          paintAllShapes ();
          //drawingPanel.paintAllShapes(getGraphics());
          validateTree();
          //paintChildren(getGraphics());
          //paintComponents(getGraphics());
          //revalidate();
          //repaint();
    }
    // 6 - Invoked when a window is changed from a normal to a minimized state.
    public void windowIconified(WindowEvent e) { }
    // 7 - Invoked the first time a window is made visible.
    public void windowOpened(WindowEvent e) {
          paintAllShapes ();
          //drawingPanel.paintAllShapes(getGraphics());
          validateTree();
          //paintChildren(getGraphics());
          //paintComponents(getGraphics());
          //revalidate();
          //repaint();
    }
    //###########################################
     private int  getSelectedTask (int x){
            sbsTask   task;
            int i;
        
            for ( i = 0; i < TaskList.size(); i++)
            {
               task = (sbsTask)TaskList.get(i);
               if (Math.abs(task.getLineX() - x) < selectionTolleranceX)
                   return i;
            } 
            return -1;
        }
    //###########################################
    public sbsTask  getTask_i (int i) {
        return ( i < TaskList.size()) ? (sbsTask)TaskList.get(i) : null;
    }
    //###########################################
    public int  getTaskPosition (sbsTask task) {
        sbsTask task_i;
        int i;
    
        for ( i = 0; i < TaskList.size(); i++)
        {
           task_i = (sbsTask)TaskList.get(i);
           if (task_i == task)
               return i;
        } 
        return -1;
    }
    //###########################################
    private int  getSelectedItem (int y){
        sbs_item  item;
        int i;
        
        for ( i = 0; i < ItemList.size(); i++)
        {
           item = (sbs_item)ItemList.get(i);
           if (Math.abs(item.getIntVar(sbs_generic.V_INT_Y) - y) < selectionTolleranceY)
               return i;
        }
        return -1;
    }
    //###########################################
    public void paintAllShapes ()  {
       Graphics g = drawingPanel.getGraphics();
       if ( g != null)
       {
            paintAllShapes(g);
       }
    }
    //###########################################
    public void paintAllShapes (Graphics g) {
       int i;
       sbsTask task;
       sbs_item item = null;

       Rectangle bounds = drawingPanel.getBounds(null);

       for ( i = 0; i < TaskList.size(); i++)
       {
          task = (sbsTask)TaskList.get(i);
  		  task.setBottom((int)bounds.getHeight());
          task.paintTask(g);
       }

       for ( i = 0; i < ItemList.size(); i++)
       {
           item = (sbs_item)ItemList.get(i);
           item.paintItem(g);
       }
//       if (item != null) {
//           int bottom = item.getIntVar(sbs_generic.V_INT_Y)        +
//                        item.getIntVar(sbs_generic.V_INT_YDOWNRSV) +
//                        30;  //  pixel reserved as bottom border
//
//           g.drawLine(0, bottom, 0+10, bottom);
//       }
    }

 // protected  void paintComponent(Graphics g) {
 //     //drawingPanel.setBackground(Color.white);
 //     paintAllShapes();
 //     super.paintComponent(g);
 //   }

 //###########################################
   public int getnumcomp(){
     return (contentPane.getComponentCount());
   }
   //###########################################
    public int getClosestTask( double x) {
          int   i,
                task_close = 0;
          sbsTask task;
          double   distance,
                   min_distance = Double.MAX_VALUE;

          for ( i = 0; i < TaskList.size(); i++)
          {
             task = (sbsTask)TaskList.get(i);
             distance = Math.abs(task.getLineX() - x);
             if (distance < min_distance)
             {
                 min_distance = distance;
                 task_close   = i;
             }
          }
          return task_close;
    }
   //###########################################
   public void addNewTask (sbsTask task) {
          if (task != null)
                TaskList.add(task);
  }
  //###########################################
  private void resizeScrollable () {
      int width  = 0;
      int height = 0;
      sbsTask  LastTask ; 
      sbs_item LastItem ; 
      
      if ( ! (TaskList.isEmpty())) {
          LastTask = (sbsTask) TaskList.get(TaskList.size() - 1); 
          width  = LastTask.getLineX() + LastTask.get_rectWidth() + 
                   50;  //  pixel reserved as rigth border
      }

      if ( ! (ItemList.isEmpty())) {
          LastItem = (sbs_item)ItemList.get(ItemList.size() - 1); 
          height = LastItem.getIntVar(sbs_generic.V_INT_Y)        +
                   LastItem.getIntVar(sbs_generic.V_INT_YDOWNRSV) +
                  60;  //  pixel reserved as bottom border
      }

      ClientArea.width  = width;
      ClientArea.height = height;

      drawingPanel.setPreferredSize(ClientArea);

      drawingPanel.revalidate();
  }
   //###########################################
   public void addNewTask (String taskname, String mailboxName, int mailbox, int proc, boolean MainTask) {
         int        x = TaskOriginX;
         int        tasknum = 0;
//         int        bottom ;
//         Rectangle  rect;
         sbsTask    task;
         sbs_item   item;

       if (MainTask == true)
           for (tasknum=0; tasknum<TaskList.size(); tasknum++) {
                task = (sbsTask)TaskList.get(tasknum);
                task.setMainTask(false);
           }
                
         if ( ! (TaskList.isEmpty()))
         {
            tasknum = TaskList.size();
            task = (sbsTask)TaskList.get(tasknum - 1);
            x = task.get_rectX() + task.get_rectWidth() + TaskMinGapX;
         }
         task = new sbsTask( taskname, mailboxName, mailbox, proc, x, TaskOriginY, MainTask);
                  
                  
         TaskList.add (task);
         task.paintTask(drawingPanel.getGraphics());


//         if ( ! (ItemList.isEmpty()))
//         {
//             item   = (sbs_item)ItemList.get(ItemList.size()-1);
//             bottom = item.getIntVar(sbs_generic.V_INT_Y)        +
//                      item.getIntVar(sbs_generic.V_INT_YDOWNRSV) +
//                      100;  //  pixel reserved as bottom border
//         }
//         else
//           bottom = 400 ;

//         ClientArea.width  = x;
//         ClientArea.height = bottom;
//
//         rect = new Rectangle(x, bottom, 100, 100);
//         drawingPanel.scrollRectToVisible(rect);
//
//         drawingPanel.setPreferredSize(ClientArea);
//         //Let the scroll pane know to update itself
//         //and its scrollbars.
//         drawingPanel.revalidate();

        resizeScrollable();
        (environ.getMainFrame()).repaint();
   }
 //###########################################
     private  void ShiftBackItems (int itemNum) {
    
     int      i;
     sbs_item item; 
     sbs_item old_item = (sbs_item)ItemList.get(itemNum);
     int removed_gap = 0;
    
     for (i=itemNum + 1; i < ItemList.size(); i++) {
          item  = (sbs_item)ItemList.get(i);
          if (removed_gap == 0) {
              removed_gap =     item.getIntVar(sbs_generic.V_INT_Y) - (
                            old_item.getIntVar(sbs_generic.V_INT_Y)      -
                            old_item.getIntVar(sbs_generic.V_INT_YUPRSV) +
                                item.getIntVar(sbs_generic.V_INT_YUPRSV));
          }
          item.setIntVar(sbs_generic.V_INT_Y,
                         item.getIntVar(sbs_generic.V_INT_Y) - removed_gap);
     } 
   }
   //###########################################
    public  void  addNewItem (sbs_item item) {
        if (item != null) {
            ItemList.add(item);
            resizeScrollable();
        }
   }
   //###########################################
    public  int  addNewItem (int y, sbs_generic generic) {
        sbs_item newItem = new sbs_item(environ, generic);
        return addNewItem (y, newItem);
   }
   //###########################################
   private  int  addNewItem (int y, sbs_item newItem ) {

   int      i;
   int      j;
   int      gap;
   sbs_item item; 


   for (i=0; i < ItemList.size(); i++) { // found first item AFTER (with y>=)
        item  = (sbs_item)ItemList.get(i);
        if (item.getIntVar(sbs_generic.V_INT_Y) >= y) 
            break;
   }

   if ( i != 0 )  {// not 1st item --> test gap from previous
        item = (sbs_item)ItemList.get(i-1);  
        gap = item.getIntVar(sbs_generic.V_INT_Y)         + 
              item.getIntVar(sbs_generic.V_INT_YDOWNRSV)  +
              newItem.getIntVar(sbs_generic.V_INT_YUPRSV);
        if ( y < gap)
             y = gap;
   }

   newItem.setIntVar(sbs_generic.V_INT_Y,  (int)y);

   if ( i < ItemList.size()) {  // shift Y_value for items following i.
       item  = (sbs_item)ItemList.get(i);
       gap = item.getIntVar(sbs_generic.V_INT_YDOWNRSV) + newItem.getIntVar(sbs_generic.V_INT_YUPRSV);
       if (item.getIntVar(sbs_generic.V_INT_Y) < (y + gap)) {
           item.setIntVar(sbs_generic.V_INT_Y,  (int)y + gap);

           for (j=i+1; j < ItemList.size(); j++) {  
                item  = (sbs_item)ItemList.get(j);
                item.setIntVar(sbs_generic.V_INT_Y,
                               item.getIntVar(sbs_generic.V_INT_Y) + gap);
           } 
       }
   }
   ItemList.insertElementAt (newItem, i);
   resizeScrollable();
   return i;
   }
   //###########################################
   private void PasteItem() {
       
     sbs_item  item    = (sbs_item) ItemList.get(copyCut.ItemNum);
     sbs_item  newItem = new sbs_item (item, CurrentSbsItem++, copyCut.pasteY);
     int i = addNewItem(copyCut.pasteY, newItem);
}
   //###########################################
   public void addNewMessage (double x1, double x2, double y) {

   sbsTask  task1;
   sbsTask  task2;
   int      ntask1 = getClosestTask(x1);
   int      ntask2 = getClosestTask(x2);
   int      i;
   
   if (ntask1 != ntask2)  
   {    
       task1 = (sbsTask)TaskList.get(ntask1);
       task2 = (sbsTask)TaskList.get(ntask2);
       
       if (task1.isMainTask() || task2.isMainTask())  {    
           
           sbsMessage  msg = new sbsMessage(this, CurrentSbsItem++, task1, task2, (int)y);
           i = addNewItem((int)y, (sbs_generic)msg);
           msg.paintItem(drawingPanel.getGraphics());
           MessageDialog  dialog = new MessageDialog(environ, (sbs_item)ItemList.get(i));
           if (dialog.isValid() == false)
               ItemList.remove(i);
//           msg.paintItem(drawingPanel.getGraphics());
           (environ.getMainFrame()).repaint();
       }
     }
   }
   //###########################################
   public void addNewReadWriteWait (double x1, double x2, double y) {

       MessageDialog  dialog; 
       sbs_generic    item = null;
       boolean        add  = false;
       int            i    = -1;
       
       switch (environ.getMainFrame().getToolButtonSelected())
       {
         case MainFrame.TOOL_BUTTON_CHECK_VAR:
         item = new sbs_check_var (this, CurrentSbsItem++, (int)y, (int) x1, (int)x2);
         i = addNewItem((int)y, (sbs_generic)item);
         ((sbs_check_var)item).paintItem(drawingPanel.getGraphics());
         dialog = new MessageDialog(environ, (sbs_item)ItemList.get(i));
         add    = dialog.isValid();
         break;
              
         case MainFrame.TOOL_BUTTON_GET_VAR:
         item = new sbs_get_var (this, CurrentSbsItem++, (int)y, (int) x1, (int)x2);
         i = addNewItem((int)y, (sbs_generic)item);
         ((sbs_get_var)item).paintItem(drawingPanel.getGraphics());
         dialog = new MessageDialog(environ, (sbs_item)ItemList.get(i));
         add    = dialog.isValid();
         break;
              
         case MainFrame.TOOL_BUTTON_WRITE_VAR:
         item = new sbs_write_var(this, CurrentSbsItem++, (int)y, (int) x1, (int)x2);
         i = addNewItem((int)y, (sbs_generic)item);
         ((sbs_write_var)item).paintItem(drawingPanel.getGraphics());
         dialog = new MessageDialog(environ, (sbs_item)ItemList.get(i));
         add    = dialog.isValid();
         break;
              
         case MainFrame.TOOL_BUTTON_ADD_WAIT:
         item = new sbs_wait (this, CurrentSbsItem++, 10, (int)y, (int) x1, (int)x2);
         i = addNewItem((int)y, (sbs_generic)item);
         ((sbs_wait)item).paintItem(drawingPanel.getGraphics());
         WaitDialog wd = new WaitDialog (environ, (sbs_item)ItemList.get(i)); 
         add           = wd.isValid();
         break;
              
         case MainFrame.TOOL_BUTTON_FUNCTION:
         item = new sbs_function (this, CurrentSbsItem++, (int)y, (int) x1, (int)x2);
         i = addNewItem((int)y, (sbs_generic)item);
         ((sbs_function)item).paintItem(drawingPanel.getGraphics());
         dialog = new MessageDialog(environ, (sbs_item)ItemList.get(i));
         add    = dialog.isValid();
         break;
              
         default:
         break;
       }
       if (add == false && i >= 0)
           ItemList.remove(i);
       else if (item != null)
//                item.paintItem(drawingPanel.getGraphics());
       (environ.getMainFrame()).repaint();
   }
   //###########################################
   public void exportTest () {
       
      int       i;
      sbsTask   task;
      sbs_item  item;
      
      try {
          FileWriter out = new FileWriter(environ.getTempFullFileName(getDescFileName()));
          
          out.write(ParseTestFile.T_STR_TESTITEMNUM + CurrentSbsItem + "\n");
          out.write(ParseTestFile.T_STR_TESTTASK    + initTask       + "\n");
          out.write(ParseTestFile.T_STR_TESTINITFUN + initFunction   + "\n");
          out.write(ParseTestFile.T_STR_TESTINITLEV + initLevel      + "\n");


          for ( i = 0; i < TaskList.size(); i++)
          {
             task = (sbsTask)TaskList.get(i);
             task.export(out);
          }
          for ( i = 0; i < ItemList.size(); i++)
          {
             item = (sbs_item)ItemList.get(i);
             item.exportInTestFile(out);
          }
          out.close();
          
          out = new FileWriter(environ.getTempFullFileName(getCommentFileName()));
          CmntArea.write(out);
          out.close();
       } catch (Exception e) {System.out.println("Unable to export Test");}  
   }       

   //###########################################
   public void save2DB () {
       
      int       i;
      sbs_item  item;
      
      try {
          for ( i = 0; i < ItemList.size(); i++)
          {
             item = (sbs_item)ItemList.get(i);
             item.save2DB();
//             item.DeleteFiles();
          }
          exportTest();
          
          if (DBKey < 0)
              environ.getDataBase().InsertTest(this);
          else {
                TestOverWriteDialog towd =  new TestOverWriteDialog(environ);
                switch(towd.getChoice()) {
                case TestOverWriteDialog.INT_NEW:
                     environ.getDataBase().InsertTest(this);
                     break;
                case TestOverWriteDialog.INT_OVER:
                     environ.getDataBase().UpdateTest(this);
                     break;
                default:
                     break;
               }
          }
       } catch (Exception e) {e.printStackTrace();}  
   }
   //###########################################
   public void LoadFromDB() {
      int       i = 0;
      sbs_item  item;
      
     try {
         environ.getDataBase().getTestData(this);
         environ.getParseTestFile().loadTest(this, 
                                             environ.getTempFullFileName(
                                                     getDescFileName()));
          for ( ; i < ItemList.size(); i++)
          {
             item = (sbs_item)ItemList.get(i);
             item.LoadFromDB();
          }
          FileReader in = new FileReader(environ.getTempFullFileName(
                                                 getCommentFileName()));
          CmntArea.read(in, null);
          in.close();
       } catch (Exception e) {System.out.println("Unable to Load Test from DB");}  
   }
   
   //###########################################
  public  void CreateLISFile (String LisFileName) {
            
      int              i;
      int              j;
      sbsTask          task;
      sbs_item         item;
      String           line;
      String           inFileName;
      FileInputStream  in;

      try {
           FileWriter       out = new FileWriter(LisFileName);

//           for (i=0; i < TaskList.size(); i++) { 
//               task  = (sbsTask)TaskList.get(i);
//               if (task.getMailboxName().equals("") == false) 
//                   out.write("redirect "+ task.getMailboxName() + "\n");
//          }
          out.write("call\nprHwOsBoot\n0\n");
          out.write("call\nmdhpr_init\n0\n");
//          out.write("environment\nmdhpr_decoder\nmdhpr_insync\n");
          out.write("environment\n" + initTask + "\n" + initFunction + "\n" + initLevel + "\n");
          for (i=0; i < ItemList.size(); i++) {   
              
               item  = (sbs_item)ItemList.get(i);
               item.export2Lis(out);
               out.write("\n");  
           }   
          
           out.close();

      } catch (Exception e) {System.out.println("Unable to write " + LisFileName);}
   }
   //###########################################
   //###########################################
    //-------------------------------------------------
    //-------------- DrawingPanel ---------------------
    //-------------------------------------------------
    class CopyCutItem  {

       int     ItemNum;
       int     type;
       int     pasteY;
       
       public void reset() {
          type = PopUpSelection.COMMAND_NONE;
       }
    }
   //###########################################
   //###########################################
    //-------------------------------------------------
    //-------------- DrawingPanel ---------------------
    //-------------------------------------------------
    class DrawingPanel extends JPanel {
//                       implements Scrollable {

    private int maxUnitIncrement = 10;


    //###########################################
    public DrawingPanel()
    {
       super();
          //super(new BorderLayout());
          //setOpaque(true);
          //setBackground(Color.white);
    }
    //###########################################
    public Dimension getPreferredSize() {
            return super.getPreferredSize();
    }

    //###########################################
    // Returns the preferred size of
    // the viewport for a view component.
    public Dimension getPreferredScrollableViewportSize() {
//        return getPreferredSize();
        return new Dimension(1000, 460);
    }

    //###########################################
    // Return true if a viewport should always force the height of this
    // Scrollable to match the height of the viewport.
    public boolean getScrollableTracksViewportHeight()
    {
        return true;
    }

    //###########################################
    // Return true if a viewport should always force the width of this
    // Scrollable to match the width of the viewport.
    public boolean getScrollableTracksViewportWidth()
    {
        return true;
    }

    //###########################################
    // Components that display logical rows or columns
    // should compute the scroll increment that will completely
    // expose one block of rows or columns, depending on the value of orientation.
    public int getScrollableBlockIncrement(Rectangle visibleRect,
                                           int       orientation,
                                           int       direction) {
        if (orientation == SwingConstants.HORIZONTAL) {
            return visibleRect.width  - maxUnitIncrement;
        } else {
            return visibleRect.height - maxUnitIncrement;
        }
    }
    //###########################################
    // Components that display logical rows or columns
    // should compute the scroll increment that will completely
    // expose one new row or column, depending on the value of orientation.
    public int getScrollableUnitIncrement(Rectangle visibleRect,
                                          int       orientation,
                                          int       direction) {
        //Get the current position.
        int currentPosition = 0;
        if (orientation == SwingConstants.HORIZONTAL) {
            currentPosition = visibleRect.x;
        } else {
            currentPosition = visibleRect.y;
        }

        //Return the number of pixels between currentPosition
        //and the nearest tick mark in the indicated direction.
        if (direction < 0) {
            int newPosition = currentPosition -
                             (currentPosition / maxUnitIncrement)
                              * maxUnitIncrement;
            return (newPosition == 0) ? maxUnitIncrement : newPosition;
        } else {
            return ((currentPosition / maxUnitIncrement) + 1)
                   * maxUnitIncrement
                   - currentPosition;
        }
    }
    //###########################################
    protected  void paintComponent(Graphics g) {
      super.paintComponent(g);
      paintAllShapes(g);
    }
  }
}  // class sbsTest
