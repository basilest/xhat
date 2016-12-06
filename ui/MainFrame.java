/*
 * Created on Dec 18, 2011
 *
 * 
 * @author  Basile Stefano
 */
package  ui;
import java.io.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import sbs.*;
import common.*;

/**
 * @author   Basile Stefano
 */
/*
 * MainFrame.java application that requires:
 *   sbsTest.java
 */
public class MainFrame extends JFrame  implements ActionListener,
                                                  TreeSelectionListener {

    
    static final String MENU_1              = "File";
    static final String MENU_1_NEW_TEST     = "New Test";
    static final String MENU_1_TEST_PROP    = "Test Properties..";
    static final String MENU_1_OPEN         = "Open..";
//    static final String MENU_1_RETRIEVEDB   = "Retrieve from DB..";
    static final String MENU_1_CHOOSE_ELF   = "Resource File..";
    static final String MENU_1_CLOSE        = "Close";
    static final String MENU_1_SAVE         = "Save";
//    static final String MENU_1_SAVE_AS      = "Save As..";
    static final String MENU_1_CLOSE_ALL    = "Close All";
    static final String MENU_1_QUIT         = "Quit";

    static final String MENU_2              = "Edit";
    static final String MENU_2_UNDO         = "Undo";
    static final String MENU_2_COPY         = "Copy";
    static final String MENU_2_CUT          = "Cut";
    static final String MENU_2_PASTE        = "Paste";
    static final String MENU_2_SELECT_ALL   = "Select All";

    static final String MENU_3              = "Window";
    static final String MENU_3_CASCADE      = "Cascade";
    static final String MENU_3_NEXT         = "Next Window";
    static final String MENU_3_PREVIOUS     = "Previous Window";
    static final String MENU_3_TILE_H       = "Tile Horizontal";
    static final String MENU_3_TILE_V       = "Tile Vertical";
    static final String MENU_3_MINIMIZE_ALL = "Minimize all Windows";

    static final String MENU_HELP           = "Help";
    static final String MENU_HELP_HELP      = "Help";
    static final String MENU_HELP_ABOUT     = "About..";

    static final String TOOL_NEW_MESSAGE    = "New Message";
    static final String TOOL_NEW_INTERFACE  = "New Interface";
    static final String TOOL_ADD_WAIT       = "Wait some time";
    static final String TOOL_INSERT_TEST    = "Insert Test";
    static final String TOOL_CHECK_VAR      = "Check Var";
    static final String TOOL_GET_VAR        = "Get Var";
    static final String TOOL_WRITE_VAR      = "Set Var";
    static final String TOOL_SELECTION      = "Select Item";
    static final String TOOL_FUNCTION       = "Call a Function";
    static final String TOOL_GO             = "Execute Hatt";
    static final String TOOL_LIS_FILE       = "Create Lis File";
    static final String TOOL_GO_LIS         = "Execute Lis";
    static final String TOOL_READ_LIS_OUT   = "Read Output";
    static final String TOOL_CONFIG_FILE    = "Config File";
    

      
    public static final int TOOL_BUTTON_NONE          = 0;
    public static final int TOOL_BUTTON_NEW_MESSAGE   = 1;
    public static final int TOOL_BUTTON_NEW_INTERFACE = 2;
    public static final int TOOL_BUTTON_ADD_WAIT      = 3;
    public static final int TOOL_BUTTON_INSERT_TEST   = 4;
    public static final int TOOL_BUTTON_CHECK_VAR     = 5;
    public static final int TOOL_BUTTON_GET_VAR       = 6;
    public static final int TOOL_BUTTON_WRITE_VAR     = 7;
    public static final int TOOL_BUTTON_FUNCTION      = 8;
    public static final int TOOL_BUTTON_SELECTION     = 9;
    public static final int TOOL_BUTTON_LIS_FILE      = 10;
    public static final int TOOL_BUTTON_GO            = 11;
    public static final int TOOL_BUTTON_READ_LIS_OUT  = 12;
    public static final int TOOL_BUTTON_CONFIG_FILE   = 13;
    

    private int ToolButtonSelected;
    static final String newline = "\n";

	//static final String ImagesPath = "/xhat/classes/com/siemens/icm/images";
	static final String ImagesPath = "images";
    
    byte icon_dim = 16;


    JDesktopPane desktop;
    
    JPanel       MyDesktop;
    
    JPanel       contentPane;

    private      ArrayList TestList;

    JScrollPane            TreeScrollPanel;
    final JTree            tree;
    DefaultMutableTreeNode treeRoot;
    DefaultTreeModel       treeModel;
    

     Environ      environ;

    //######################################################
    public MainFrame(Environ environ) {
        super("Test_Lab");
         
        this.environ = environ;
        environ.setMainFrame(this);
        
        int inset = 50;

        JFrame.setDefaultLookAndFeelDecorated(true);

        ToolButtonSelected = TOOL_BUTTON_NONE;
        
		//Make the big window be indented 50 pixels from each edge
        //of the screen.
        contentPane  = new JPanel();
        TestList     = new ArrayList(10);

        //----- CREATE TEST TREE VIEW
        treeRoot   = new DefaultMutableTreeNode("Opened Test");
        treeModel  = new DefaultTreeModel(treeRoot);
        treeModel.addTreeModelListener(new MyTreeModelListener());


        tree       = new JTree(treeModel);
        TreeScrollPanel   = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                                                  JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        
        TreeScrollPanel.setPreferredSize(new Dimension(90, 120));
        tree.addTreeSelectionListener(this);
        tree.setToggleClickCount(1);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                  screenSize.width  - inset*2,
                  screenSize.height - inset*2);


        setJMenuBar(createMenuBar());
        CreateDesktop();

        contentPane.setLayout(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(400, 100));
        contentPane.add(createMainToolBar(), BorderLayout.NORTH);
        contentPane.add(MyDesktop, BorderLayout.CENTER);
        contentPane.add(createTestToolBar(), BorderLayout.WEST);

        
        
        setContentPane(contentPane);

//        AddNewTest(); //create first "window"
//        try {
//            sbsTest test = (sbsTest) TestList.get(0);
//            test.setSelected(true);
//            test.setMaximum(true);
//        } catch (Exception exp) {}
        
        //Create and set up the window.
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        this.setIconImage(set_frame_icon());

        //Display the window.
        this.setVisible(true);

        DataBaseLogDialog DBdialog = new DataBaseLogDialog(environ);
        if (DBdialog.isConnected() == false)
            quit();
            
 }

  //######################################################
     private void CreateDesktop() { 
             
         MyDesktop  = new JPanel();
         MyDesktop.setLayout(new BorderLayout());
             
         desktop = new JDesktopPane(); //a specialized layered pane
         desktop.setDragMode(JDesktopPane.LIVE_DRAG_MODE );
    
         MyDesktop.add(desktop, BorderLayout.CENTER);
     }
   //######################################################
    public void set_DB_Profile (String DataBase, 
                                String User, 
                                char [] Password) {
        System.out.println(DataBase);
        System.out.println(User);
        System.out.println(Password);
    }

    //######################################################
    //---------------------------- CREATE THE MENU BAR
    private JMenuBar createMenuBar() {
        JMenuBar   menuBar = new JMenuBar();
        JMenu      menu_File,
                   menu_Edit,
                   menu_Window,
                   menu_Help;
         JMenuItem menuItem;
        //---------------------------
        //--------------------------- FILE
        //---------------------------
        menu_File = new JMenu(MENU_1);
        menu_File.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu_File);

        //--------------------------- NEW TEST
        menuItem = new JMenuItem(MENU_1_NEW_TEST);
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.CTRL_MASK ));
        menuItem.setActionCommand(MENU_1_NEW_TEST);
        menuItem.addActionListener(this);
        menu_File.add(menuItem);

        //--------------------------- TEST PROPERTIES
        menuItem = new JMenuItem(MENU_1_TEST_PROP);
        menuItem.setMnemonic(KeyEvent.VK_M);
        menuItem.setActionCommand(MENU_1_TEST_PROP);
        menuItem.addActionListener(this);
        menu_File.add(menuItem);
        
        
        //--------------------------- ELF FILE
        menuItem = new JMenuItem(MENU_1_CHOOSE_ELF);
        menuItem.setMnemonic(KeyEvent.VK_R);
        menuItem.setActionCommand(MENU_1_CHOOSE_ELF);
        menuItem.addActionListener(this);
        menu_File.add(menuItem);

        //--------------------------- OPEN
        menuItem = new JMenuItem(MENU_1_OPEN);
        menuItem.setMnemonic(KeyEvent.VK_O);
        menuItem.setActionCommand(MENU_1_OPEN);
        menuItem.addActionListener(this);
        menu_File.add(menuItem);

//        //--------------------------- RETRIEVE FROM DB
//        menuItem = new JMenuItem(MENU_1_RETRIEVEDB);
//        menuItem.setMnemonic(KeyEvent.VK_D);
//        menuItem.setActionCommand(MENU_1_RETRIEVEDB);
//        menuItem.addActionListener(this);
//        menu_File.add(menuItem);

        //--------------------------- CLOSE
        menuItem = new JMenuItem(MENU_1_CLOSE);
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F4, ActionEvent.CTRL_MASK ));
        menuItem.setActionCommand(MENU_1_CLOSE);
        menuItem.addActionListener(this);
        menu_File.add(menuItem);

        //--------------------------- SAVE
        menuItem = new JMenuItem(MENU_1_SAVE);
        menuItem.setMnemonic(KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.CTRL_MASK ));
        menuItem.setActionCommand(MENU_1_SAVE);
        menuItem.addActionListener(this);
        menu_File.add(menuItem);
        //--------------------------- SAVE AS ..
//        menuItem = new JMenuItem(MENU_1_SAVE_AS);
//        menuItem.setMnemonic(KeyEvent.VK_A);
//        menuItem.setActionCommand(MENU_1_SAVE_AS);
//        menuItem.addActionListener(this);
//        menu_File.add(menuItem);

        menu_File.addSeparator();
        //--------------------------- CLOSE ALL ..
		menuItem = new JMenuItem(MENU_1_CLOSE_ALL, new ImageIcon(ImagesPath + "/close_all16.gif"));
        menuItem.setMnemonic(KeyEvent.VK_L);
        menuItem.setActionCommand(MENU_1_CLOSE_ALL);
        menuItem.addActionListener(this);
        menu_File.add(menuItem);
        
        menu_File.addSeparator();
        //--------------------------- QUIT
        menuItem = new JMenuItem(MENU_1_QUIT);
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setActionCommand(MENU_1_QUIT);
        menuItem.addActionListener(this);
        menu_File.add(menuItem);
        //---------------------------
        //--------------------------- EDIT
        //---------------------------
        menu_Edit = new JMenu(MENU_2);
        menu_Edit.setMnemonic(KeyEvent.VK_E);
        menuBar.add(menu_Edit);
        //--------------------------- UNDO
        menuItem = new JMenuItem(MENU_2_UNDO, new ImageIcon(ImagesPath + "/Undo16.gif"));
        menuItem.setMnemonic(KeyEvent.VK_U);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Z, ActionEvent.CTRL_MASK ));
        menuItem.setActionCommand(MENU_2_UNDO);
        menuItem.addActionListener(this);
        menu_Edit.add(menuItem);

        menu_Edit.addSeparator();

        //--------------------------- COPY
        menuItem = new JMenuItem(MENU_2_COPY);
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.setActionCommand(MENU_2_COPY);
        menuItem.addActionListener(this);
        menu_Edit.add(menuItem);
        //--------------------------- CUT
        menuItem = new JMenuItem(MENU_2_CUT);
        menuItem.setMnemonic(KeyEvent.VK_T);
        menuItem.setActionCommand(MENU_2_CUT);
        menuItem.addActionListener(this);
        menu_Edit.add(menuItem);
        //--------------------------- PASTE
        menuItem = new JMenuItem(MENU_2_PASTE);
        menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.setActionCommand(MENU_2_PASTE);
        menuItem.addActionListener(this);
        menu_Edit.add(menuItem);

        menu_Edit.addSeparator();
        //--------------------------- SELECT ALL
        menuItem = new JMenuItem(MENU_2_SELECT_ALL);
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setActionCommand(MENU_2_SELECT_ALL);
        menuItem.addActionListener(this);
        menu_Edit.add(menuItem);

        //---------------------------
        //--------------------------- WINDOW
        //---------------------------
        menu_Window = new JMenu(MENU_3);
        menu_Window.setMnemonic(KeyEvent.VK_W);
        menuBar.add(menu_Window);
        //--------------------------- CASCADE
        menuItem = new JMenuItem(MENU_3_CASCADE, new ImageIcon(ImagesPath + "/w_cascade16.gif"));
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.setActionCommand(MENU_3_CASCADE);
        menuItem.addActionListener(this);
        menu_Window.add(menuItem);

        //--------------------------- NEXT WINDOW
        menuItem = new JMenuItem(MENU_3_NEXT, new ImageIcon(ImagesPath + "/w_next16.gif"));
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F6, ActionEvent.CTRL_MASK ));
        menuItem.setActionCommand(MENU_3_NEXT);
        menuItem.addActionListener(this);
        menu_Window.add(menuItem);

        //--------------------------- PREVIOUS WINDOW
        menuItem = new JMenuItem(MENU_3_PREVIOUS, new ImageIcon(ImagesPath + "/w_prev16.gif"));
        menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F6, ActionEvent.SHIFT_MASK ));
        menuItem.setActionCommand(MENU_3_PREVIOUS);
        menuItem.addActionListener(this);
        menu_Window.add(menuItem);

        //--------------------------- TILE HORIZONTAL
        menuItem = new JMenuItem(MENU_3_TILE_H, new ImageIcon(ImagesPath + "/w_tile_h16.gif"));
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.setActionCommand(MENU_3_TILE_H);
        menuItem.addActionListener(this);
        menu_Window.add(menuItem);
        //--------------------------- TILE VERTICAL
        menuItem = new JMenuItem(MENU_3_TILE_V, new ImageIcon(ImagesPath + "/w_tile_v16.gif"));
        menuItem.setMnemonic(KeyEvent.VK_C);
        menuItem.setActionCommand(MENU_3_TILE_V);
        menuItem.addActionListener(this);
        menu_Window.add(menuItem);

        menu_Window.addSeparator();

        //---------------------------  MINIMIZE ALL
        menuItem = new JMenuItem(MENU_3_MINIMIZE_ALL);
        menuItem.setMnemonic(KeyEvent.VK_M);
        menuItem.setActionCommand(MENU_3_MINIMIZE_ALL);
        menuItem.addActionListener(this);
        menu_Window.add(menuItem);


        menuBar.add(Box.createHorizontalGlue());

        //---------------------------
        //--------------------------- HELP
        //---------------------------
        menu_Help = new JMenu(MENU_HELP);
        menu_Help.setMnemonic(KeyEvent.VK_H);
        menuBar.add(menu_Help);
        //--------------------------- HELP
        menuItem = new JMenuItem(MENU_HELP_HELP);
        menuItem.setMnemonic(KeyEvent.VK_H);
        menuItem.setActionCommand(MENU_HELP_HELP);
        menuItem.addActionListener(this);
        menu_Help.add(menuItem);
        //--------------------------- ABOUT
        menuItem = new JMenuItem(MENU_HELP_ABOUT);
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setActionCommand(MENU_HELP_ABOUT);
        menuItem.addActionListener(this);
        menu_Help.add(menuItem);

        return menuBar;
    }

    //######################################################
    //---------------------------- CREATE THE MAIN TOOL BAR
    private JToolBar createMainToolBar() {
        JToolBar main_toolBar = new JToolBar();
        JButton button = null;

        main_toolBar.setFloatable(false);

        //--------------------------- NEW TEST
        button = new JButton(new ImageIcon(ImagesPath + "/New16.gif"));
        button.setToolTipText(MENU_1_NEW_TEST);
        button.setActionCommand(MENU_1_NEW_TEST);
        button.addActionListener(this);
        main_toolBar.add(button);

        //--------------------------- OPEN
        button = new JButton(new ImageIcon(ImagesPath + "/Open16.gif"));
        button.setToolTipText(MENU_1_OPEN);
        button.setActionCommand(MENU_1_OPEN);
        button.addActionListener(this);
        main_toolBar.add(button);

        //--------------------------- CLOSE
        button = new JButton(new ImageIcon(ImagesPath + "/Close16.gif"));
        button.setToolTipText(MENU_1_CLOSE);
        button.setActionCommand(MENU_1_CLOSE);
        button.addActionListener(this);
        main_toolBar.add(button);

        //--------------------------- SAVE
        button = new JButton(new ImageIcon(ImagesPath + "/Save16.gif"));
        button.setToolTipText(MENU_1_SAVE);
        button.setActionCommand(MENU_1_SAVE);
        button.addActionListener(this);
        main_toolBar.add(button);

        main_toolBar.addSeparator();


        //--------------------------- CUT
        button = new JButton(new ImageIcon(ImagesPath + "/Cut16.gif"));
        button.setToolTipText(MENU_2_CUT);
        button.setActionCommand(MENU_2_CUT);
        button.addActionListener(this);
        main_toolBar.add(button);

        //--------------------------- COPY
        button = new JButton(new ImageIcon(ImagesPath + "/Copy16.gif"));
        button.setToolTipText(MENU_2_COPY);
        button.setActionCommand(MENU_2_COPY);
        button.addActionListener(this);
        main_toolBar.add(button);

        //--------------------------- PASTE
        button = new JButton(new ImageIcon(ImagesPath + "/Paste16.gif"));
        button.setToolTipText(MENU_2_PASTE);
        button.setActionCommand(MENU_2_PASTE);
        button.addActionListener(this);
        main_toolBar.add(button);

        main_toolBar.addSeparator();


        //--------------------------- TILE HORIZONTAL
        button = new JButton(new ImageIcon(ImagesPath + "/w_tile_h16.gif"));
        button.setToolTipText(MENU_3_TILE_H);
        button.setActionCommand(MENU_3_TILE_H);
        button.addActionListener(this);
        main_toolBar.add(button);

        //--------------------------- TILE VERTICAL
        button = new JButton(new ImageIcon(ImagesPath + "/w_tile_v16.gif"));
        button.setToolTipText(MENU_3_TILE_V);
        button.setActionCommand(MENU_3_TILE_V);
        button.addActionListener(this);
        main_toolBar.add(button);

        //--------------------------- CASCADE
        button = new JButton(new ImageIcon(ImagesPath + "/w_cascade16.gif"));
        button.setToolTipText(MENU_3_CASCADE);
        button.setActionCommand(MENU_3_CASCADE);
        button.addActionListener(this);
        main_toolBar.add(button);

        return main_toolBar;
    }


    //######################################################
    //---------------------------- CREATE THE TEST TOOL BAR
    private JPanel createTestToolBar() {

        JButton button  = null;
	    JPanel BoxPanel = new JPanel();

	    JPanel ButtonPanel = new JPanel();
	    ButtonPanel.setLayout(new BoxLayout(ButtonPanel, BoxLayout.Y_AXIS));


        BoxPanel.setBorder(BorderFactory.createTitledBorder("Tool Bar"));
	    BoxPanel.setLayout(new BoxLayout(BoxPanel, BoxLayout.Y_AXIS));
        BoxPanel.add(Box.createRigidArea(new Dimension(0,35)));
        BoxPanel.add(this.TreeScrollPanel);
        //BoxPanel.add(this.scrollPane);

	    ButtonPanel.add(Box.createVerticalGlue());

        //--------------------------- INSERT TEST
//        //button = new JButton(TOOL_INSERT_TEST, new ImageIcon(ImagesPath + "/tt_insert16.gif"));
//        button = new JButton(new ImageIcon(ImagesPath + "/InsertTest.gif"));
//        
//        button.setToolTipText(TOOL_INSERT_TEST);
//        button.setVerticalTextPosition(AbstractButton.CENTER);
//        button.setHorizontalTextPosition(AbstractButton.RIGHT);
//        button.setActionCommand(TOOL_INSERT_TEST);
//        button.addActionListener(this);
//        ButtonPanel.add(button);
//
       //--------------------------- CONFIG FILE
        button = new JButton(new ImageIcon(ImagesPath + "/Chart10.gif"));
        button.setToolTipText(TOOL_CONFIG_FILE);
        button.setVerticalTextPosition(AbstractButton.CENTER);
        button.setHorizontalTextPosition(AbstractButton.RIGHT);
        button.setActionCommand(TOOL_CONFIG_FILE);
        button.addActionListener(this);
        ButtonPanel.add(button);
        
        //--------------------------- NEW MESSAGE
        //button = new JButton(TOOL_NEW_MESSAGE, new ImageIcon(ImagesPath + "/tt_message16.gif"));
        button = new JButton(new ImageIcon(ImagesPath + "/Message3.gif"));
        button.setToolTipText(TOOL_NEW_MESSAGE);
        button.setVerticalTextPosition(AbstractButton.CENTER);
        button.setHorizontalTextPosition(AbstractButton.RIGHT);
        button.setActionCommand(TOOL_NEW_MESSAGE);
        button.addActionListener(this);
        ButtonPanel.add(button);

        //--------------------------- NEW INTERFACE
//        button = new JButton(TOOL_NEW_INTERFACE, new ImageIcon(ImagesPath + "/tt_task16.gif"));
        button = new JButton(new ImageIcon(ImagesPath + "/Interface3.gif"));
        button.setToolTipText(TOOL_NEW_INTERFACE);
        button.setVerticalTextPosition(AbstractButton.CENTER);
        button.setHorizontalTextPosition(AbstractButton.RIGHT);
        button.setActionCommand(TOOL_NEW_INTERFACE);
        button.addActionListener(this);
        ButtonPanel.add(button);

        //--------------------------- WRITE VAR
//        button = new JButton(TOOL_WRITE_VAR, new ImageIcon(ImagesPath + "/tt_write_var16.gif"));
        button = new JButton(new ImageIcon(ImagesPath + "/Write_var.gif"));
        button.setToolTipText(TOOL_WRITE_VAR);
        button.setVerticalTextPosition(AbstractButton.CENTER);
        button.setHorizontalTextPosition(AbstractButton.RIGHT);
        button.setActionCommand(TOOL_WRITE_VAR);
        button.addActionListener(this);
        ButtonPanel.add(button);

        //--------------------------- GET VAR
        button = new JButton(new ImageIcon(ImagesPath + "/getVar2.gif"));
        button.setToolTipText(TOOL_GET_VAR);
        button.setVerticalTextPosition(AbstractButton.CENTER);
        button.setHorizontalTextPosition(AbstractButton.RIGHT);
        button.setActionCommand(TOOL_GET_VAR);
        button.addActionListener(this);
        ButtonPanel.add(button);

        //--------------------------- CHECK VAR
        button = new JButton(new ImageIcon(ImagesPath + "/read_var.gif"));
        button.setToolTipText(TOOL_CHECK_VAR);
        button.setVerticalTextPosition(AbstractButton.CENTER);
        button.setHorizontalTextPosition(AbstractButton.RIGHT);
        button.setActionCommand(TOOL_CHECK_VAR);
        button.addActionListener(this);
        ButtonPanel.add(button);

        //--------------------------- CALL FUNCTION
        button = new JButton(new ImageIcon(ImagesPath + "/Call_Function.gif"));
        button.setToolTipText(TOOL_FUNCTION);
        button.setVerticalTextPosition(AbstractButton.CENTER);
        button.setHorizontalTextPosition(AbstractButton.RIGHT);
        button.setActionCommand(TOOL_FUNCTION);
        button.addActionListener(this);
        ButtonPanel.add(button);

        //--------------------------- ADD WAIT
//        button = new JButton(TOOL_ADD_WAIT, new ImageIcon(ImagesPath + "/tt_wait16.gif"));
//        button = new JButton(new ImageIcon(ImagesPath + "/wait2.gif"));
        button = new JButton(new ImageIcon(ImagesPath + "/timeout.gif"));
        button.setToolTipText(TOOL_ADD_WAIT);
        button.setVerticalTextPosition(AbstractButton.CENTER);
        button.setHorizontalTextPosition(AbstractButton.RIGHT);
        button.setActionCommand(TOOL_ADD_WAIT);
        button.addActionListener(this);
        ButtonPanel.add(button);

//        //--------------------------- SELECT ITEM
////        button = new JButton(TOOL_SELECTION, new ImageIcon(ImagesPath + "/w_tile_h16.gif"));
//        button = new JButton(new ImageIcon(ImagesPath + "/select.gif"));
//        button.setToolTipText(TOOL_SELECTION);
//        button.setVerticalTextPosition(AbstractButton.CENTER);
//        button.setHorizontalTextPosition(AbstractButton.RIGHT);
//        button.setActionCommand(TOOL_SELECTION);
//        button.addActionListener(this);
//        ButtonPanel.add(button);

        button = new JButton(new ImageIcon(ImagesPath + "/LisInput.gif"));
        button.setToolTipText(TOOL_LIS_FILE);
        button.setVerticalTextPosition(AbstractButton.CENTER);
        button.setHorizontalTextPosition(AbstractButton.RIGHT);
        button.setActionCommand(TOOL_LIS_FILE);
        button.addActionListener(this);
        ButtonPanel.add(button);

      button = new JButton(new ImageIcon(ImagesPath + "/executeLis3.gif"));
//      button.setToolTipText(TOOL_GO);
      button.setToolTipText(TOOL_GO_LIS);
      button.setVerticalTextPosition(AbstractButton.CENTER);
      button.setHorizontalTextPosition(AbstractButton.RIGHT);
//      button.setActionCommand(TOOL_GO);
      button.setActionCommand(TOOL_GO_LIS);
      button.addActionListener(this);
      ButtonPanel.add(button);

      button = new JButton(new ImageIcon(ImagesPath + "/InsertTest.gif"));
      button.setToolTipText(TOOL_READ_LIS_OUT);
      button.setVerticalTextPosition(AbstractButton.CENTER);
      button.setHorizontalTextPosition(AbstractButton.RIGHT);
      button.setActionCommand(TOOL_READ_LIS_OUT);
      button.addActionListener(this);
      ButtonPanel.add(button);

      //adjustButton(ButtonPanel);
      BoxPanel.add(ButtonPanel);

        return BoxPanel;
    }

    //######################################################
    private void adjustButton(JPanel ButtonPanel){
        int i;
        Dimension dim =  new Dimension();
        double height = 0,
               width  = 0;

        Component[] Buttons =  ButtonPanel.getComponents();

        for (i = 0;  i < Buttons.length; i++)
        {
            System.out.println(i+") Classe:"+ Buttons[i].getClass());
           if (((Buttons[i].getClass()).getName()).equals("JButton")) 
           { 
               dim = Buttons[i].getSize();
               System.out.println("..."+width +"---"+height+"---"+dim.getHeight()+"---"+dim.getWidth()+"--"+"\n");
               if (height < dim.getHeight()) { height = dim.getHeight();}
               if (width  < dim.getWidth())  { width  = dim.getWidth();}
           }
        }

        dim.setSize(width, height);

        System.out.println("..."+width +"---"+height+"\n");
        for (i=0; i < Buttons.length; i++)
        {
            ((JComponent)Buttons[i]).setPreferredSize(dim);
        }
    }


    //######################################################
    //TreeSelectionListener
    public void valueChanged(TreeSelectionEvent e) {

           DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                                  tree.getLastSelectedPathComponent();
            if (node == null) return;

            int nodenum = treeRoot.getIndex(node);

            if ( nodenum != -1)
            {
              sbsTest  test = (sbsTest)TestList.get(nodenum);
              test.show();
              try {
                  test.setSelected(true);
              } catch (java.beans.PropertyVetoException exp) {}

              test.toFront();
            }
    }

    //######################################################
    //React to menu selections.
    public void actionPerformed(ActionEvent e) {
      String s = null;
      sbsTest test;
//      while(true)
      do
      {
        ToolButtonSelected = TOOL_BUTTON_NONE;
        if (MENU_1_NEW_TEST.equals(e.getActionCommand())) { //new
            test = new sbsTest(environ, "Test"+TestList.size(), -1);
            TestDialog td = new TestDialog(environ, test);
            if (td.isValid())
                AddNewTest(test);
            break;
        }
        if (MENU_1_TEST_PROP.equals(e.getActionCommand())) { //Test Properties
            test = GetFocusTest();
            if (test != null)
            {
              new TestDialog(environ, test);
            }
            break;
        }
//        if (MENU_1_OPEN.equals(e.getActionCommand())) { //Open
//            JFileChooser fc = new JFileChooser(environ.getTestDir());
//            int val = fc.showOpenDialog(environ.getMainFrame());
//            if (val  == JFileChooser.APPROVE_OPTION) {
//                test = new sbsTest(environ, "Test"+TestList.size());
//                test.LoadFromFile(fc.getSelectedFile());
//                AddNewTest(test);
//            }
////            test.LoadFromDB();
//            break;
//        }
        if (MENU_1_OPEN.equals(e.getActionCommand())) { //Open
            OpenTestDialog otd = new OpenTestDialog (environ);
            if (otd.isValid()) {
                test = new sbsTest(environ, otd.getTestUserName(), otd.getTestKey());
                test.setFeature(otd.getTestFeature());
                test.LoadFromDB();
                AddNewTest(test);
            }
//            test.LoadFromDB();
            break;
        }
//        if (MENU_1_RETRIEVEDB.equals(e.getActionCommand())) {//Retrieve DB
//            OpenTestDialog o = new OpenTestDialog(environ);
//            break;
//        }
        if (MENU_1_CHOOSE_ELF.equals(e.getActionCommand())) {//Elf File

//            environ.getDwarf().GenerateFromObj();
            ElfFileChooser elfChooser = environ.getElfChooser();

            elfChooser.displayDialog();
            if (elfChooser.getFullFileName() != null) {
                      environ.setDwarf(elfChooser.getFullFileName());
            }
            break;
        }
        if (MENU_1_CLOSE.equals(e.getActionCommand())) { //Close
            test = GetFocusTest();
            if (test != null)
            {
//              TestOverWriteDialog towd =  new TestOverWriteDialog(environ);
//              switch(towd.getChoice()) {
//                  case TestOverWriteDialog.INT_OVER:
//                       test.save2DB();
//                       break;
//                  case TestOverWriteDialog.INT_OVER:
//                  break;
//              }
//              System.out.println("----------------");
              test.save2DB();
              test.dispose();
            }
            break;
        }
        if (MENU_1_SAVE.equals(e.getActionCommand())) { //Save
            test = GetFocusTest();
            if (test != null)
            {
              test.exportTest();
//              test.save2DB();
            }
            break;
        }
//        if (MENU_1_SAVE_AS.equals(e.getActionCommand())) { //Save As..
//            
//            
////            JFileChooser fc = new JFileChooser("./");
////            int returnVal = fc.showOpenDialog(this);
////            if (returnVal == JFileChooser.APPROVE_OPTION) {
////                        String FullFileName = fc.getSelectedFile().getPath();
////                        byte [] digest = ZipUnzip.ZipWithDigest(FullFileName);
////                        String output = "File "+FullFileName+":("+digest.length+")";
////                        for( int  i=0; i<digest.length;i++){
////                            output += " " + String.valueOf(digest[i]);
////                        }
////                        System.out.println(output);
////            }
//            break;
//        }
        if (MENU_1_CLOSE_ALL.equals(e.getActionCommand())) { //Close All
            break;
        }
        if (MENU_2_UNDO.equals(e.getActionCommand())) { //Undo
            break;
        }
        if (MENU_2_COPY.equals(e.getActionCommand())) { //Copy
            break;
        }
        if (MENU_2_CUT.equals(e.getActionCommand())) { //Cut
            break;
        }
        if (MENU_2_PASTE.equals(e.getActionCommand())) { //Paste
            break;
        }
        if (MENU_2_SELECT_ALL.equals(e.getActionCommand())) { //Select All
            break;
        }
        if (MENU_3_CASCADE.equals(e.getActionCommand())) { //Cascade
            break;
        }
        if (MENU_3_NEXT.equals(e.getActionCommand())) { //Next Window
            break;
        }
        if (MENU_3_PREVIOUS.equals(e.getActionCommand())) { //Previous Window
            break;
        }
        if (MENU_3_TILE_H.equals(e.getActionCommand())) { //Tile Horizontal
            break;
        }
        if (MENU_3_TILE_V.equals(e.getActionCommand())) { //Tile Vertical
            break;
        }
        if (MENU_3_MINIMIZE_ALL.equals(e.getActionCommand())) { //Minimize all Windows
            break;
        }
        if (MENU_HELP_HELP.equals(e.getActionCommand())) { //Help
            break;
        }
        if (MENU_HELP_ABOUT.equals(e.getActionCommand())) { //About
            break;
        }
        if (TOOL_INSERT_TEST.equals(e.getActionCommand())) { //Insert Test
            ToolButtonSelected = TOOL_BUTTON_INSERT_TEST;
            break;
        }
        if (TOOL_CONFIG_FILE.equals(e.getActionCommand())) { //Config File
            ToolButtonSelected = TOOL_BUTTON_CONFIG_FILE;
            test = GetFocusTest();
            if (test != null) {
              String FullFileName = environ.getTempFullFileName(test.getConfigFileName());
              environ.getConfigFrame().DisplayFrame(FullFileName);
            }
            break;
        }
        if (TOOL_NEW_MESSAGE.equals(e.getActionCommand())) { //New Message
            ToolButtonSelected = TOOL_BUTTON_NEW_MESSAGE;
            test = GetFocusTest();
            break;
        }
        if (TOOL_NEW_INTERFACE.equals(e.getActionCommand())) { //New Interface
            ToolButtonSelected = TOOL_BUTTON_NEW_INTERFACE;
            test = GetFocusTest();
			if (test != null)
			{
              TaskDialog  dialog = new TaskDialog(environ, null);
              if (dialog.isValid())
              test.addNewTask(dialog.getTaskUserName(), 
                              dialog.getTaskMailboxName(),
                              dialog.getTaskMailbox(),
                              dialog.getTaskProcessor(),
                              dialog.isMainTask());
			}
            break;
        }
        if (TOOL_ADD_WAIT.equals(e.getActionCommand())) { //Wait
            ToolButtonSelected = TOOL_BUTTON_ADD_WAIT;
            break;
        }
        if (TOOL_CHECK_VAR.equals(e.getActionCommand())) { //Check Var
            ToolButtonSelected = TOOL_BUTTON_CHECK_VAR;
            break;
        }
        if (TOOL_GET_VAR.equals(e.getActionCommand())) { //Get Var
            ToolButtonSelected = TOOL_BUTTON_GET_VAR;
            break;
        }
        if (TOOL_WRITE_VAR.equals(e.getActionCommand())) { //Write Var
            ToolButtonSelected = TOOL_BUTTON_WRITE_VAR;
            break;
        }
        if (TOOL_FUNCTION.equals(e.getActionCommand())) { //Call Function
            ToolButtonSelected = TOOL_BUTTON_FUNCTION;
            break;
        }
        if (TOOL_SELECTION.equals(e.getActionCommand())) { //Select Item
            ToolButtonSelected = TOOL_BUTTON_SELECTION;
            break;
        }
                        
        if (TOOL_LIS_FILE.equals(e.getActionCommand())) { //Create Lis File
            ToolButtonSelected = TOOL_BUTTON_LIS_FILE;
            test = GetFocusTest();
            if (test != null) {
                String LisFullFileName = environ.getTempFullFileName("input.txt");
                test.CreateLISFile(LisFullFileName);
                environ.getOutFrame().DisplayFrame("Generated Lis File", LisFullFileName);
            }
            break;
        }
        if (TOOL_GO_LIS.equals(e.getActionCommand())) { //Execute Lis
            ToolButtonSelected = TOOL_BUTTON_GO;
            test = GetFocusTest();
            if (test != null) {
                String[] cmd = {"/bin/sh", "-c", "../redhatt/start&"};
                Runtime runtime = Runtime.getRuntime();
                try {
                    Process proc    = runtime.exec(cmd);
                } catch (IOException e1) { System.out.println("Unable to execute Lis");}
            }
            break;
        }
        if (TOOL_READ_LIS_OUT.equals(e.getActionCommand())) { //Read Lis Output
            ToolButtonSelected = TOOL_BUTTON_READ_LIS_OUT;
            String LisFullFileName = environ.getTempFullFileName("output.txt");
            OutputDisplay o = new OutputDisplay(environ);
            o.DisplayFrame("Output Result", LisFullFileName);
            System.out.println(" Opening" + LisFullFileName);
            break;
        }
        quit();
      }
      while(false);
    }
    //######################################################
    public int getToolButtonSelected (){
           return ToolButtonSelected;
    }
    //######################################################
    public void resetToolButtonSelected (){
//        ToolButtonSelected = TOOL_BUTTON_NONE;
        ToolButtonSelected = TOOL_BUTTON_SELECTION;
    }
    //######################################################
    public void RemoveTest (sbsTest test) {
        int             index = TestList.indexOf(test);
        MutableTreeNode node  = (MutableTreeNode) treeModel.getChild(treeRoot,index); 
        TestList.remove (index);
        treeModel.removeNodeFromParent(node);
    }    
    //######################################################
    //Create a new internal frame.
    protected void AddNewTest(sbsTest test) {
        int i;

//        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(test.getTestUserName());
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(test);

        treeModel.insertNodeInto(newNode, treeRoot, treeRoot.getChildCount());
        tree.scrollPathToVisible(new TreePath(newNode.getPath()));


    	TestList.add(test);
        test.setVisible(true);
        desktop.add(test);
//        DefaultMutableTreeNode nn = (DefaultMutableTreeNode)treeRoot.getFirstChild();
//
        try {
            test.setSelected(true);
            test.setMaximum(true);    // take all desktop 
        } catch (java.beans.PropertyVetoException e) {}
        
    }
    //######################################################
    protected void quit() {
//        System.exit(0);
          this.dispose();
          new WindowEvent(this, WindowEvent.WINDOW_CLOSED); 
    }
    //######################################################
    private sbsTest GetFocusTest() {
    	sbsTest focusTest = null;
    	int i;
    	for ( i = 0;  i < TestList.size(); i++)
    	{
    	   if (((sbsTest)TestList.get(i)).isSelected())
    	   {
    	       focusTest = (sbsTest)TestList.get(i);
    	       break;
    	   }
	}
	return focusTest;
    }
    //######################################################
    private  static Image set_frame_icon() {
        java.net.URL iconURL = ClassLoader.getSystemResource(ImagesPath + "/logo.gif");
        if (iconURL != null) {
            return new ImageIcon(iconURL).getImage();
        } else {
            return null;
        }
    }
}
