/*
 * Created on 6-gen-2012
 *
 * 
 * @author  Basile Stefano
 */
package common;

import ui.*;
import C_dataClass.*;
import dwarf.DwarfMain;
import treetable.*;
import sbs.ParseTestFile;
import sbs.ItemDataManager;
import database.DataBase;

/**
 * @author  Basile Stefano
 */
//######################################################
//######################################################
 public class Environ {


    public static final   int V_INT_TESTNUM  = 1;
    
     
    MainFrame         c_MainFrame;                // refer the Main Window
//    FileManager       c_FileManager;
    DBFileManager     c_DBFileManager;
    ItemDataManager   c_ItemDataManager;
    
    OutputDisplay     c_OutFrame;
    ConfigFrame       c_ConfigFrame;
    DataBase          c_database;
    
    DwarfMain         c_Dwarf;
    JTreeTable        c_treeTable;
    
    ElfFileChooser    c_elfChooser;

    String            c_TempDir;
//    String            c_TestDir;
    
    Symbol_Tree           c_SymbTree;
    CStructTreeTableModel c_TreeTabModel;
    
    ParseDefFile      c_ParseDefFile;
    ParseTestFile     c_ParseTestFile;
    ParseEnvFile      c_ParseEnvFile;
    int               c_TestNum;
     
    //####################
    public Environ ()
    {
        c_TempDir     = "./temp/";
//        c_TestDir     = "./tests/";

//        c_BinFileManager = new BinaryFileManager(this);
        c_ItemDataManager = new ItemDataManager(this);
        c_DBFileManager   = new DBFileManager(this);  
        c_ParseDefFile    = new ParseDefFile(this);
        c_ParseTestFile   = new ParseTestFile(this);
        c_ParseEnvFile    = new ParseEnvFile(this);
        c_TestNum         = 0;
        c_ParseEnvFile.loadRes();
 
        c_database        = new DataBase(this);
        c_OutFrame        = new OutputDisplay(this);
        c_ConfigFrame     = new ConfigFrame(this);

        String FileExtensions [] = {"obj","o"};
        c_elfChooser = new ElfFileChooser(this, FileExtensions, "Just Object Files");

        c_Dwarf         = new DwarfMain(this);
        
        c_SymbTree      = new Symbol_Tree(this);
        c_treeTable     = new JTreeTable(this);
        c_TreeTabModel  = new CStructTreeTableModel (this);
        
        c_SymbTree.getTree().setModel(c_TreeTabModel);

        // Install a tableModel representing the visible rows in the tree.
        c_treeTable.setModel(new AdapterTable2Tree(c_TreeTabModel, c_SymbTree.getTree()));
        
    }
    
    //####################
    public void setMainFrame (MainFrame f) { 
        c_MainFrame = f; 
    }   
    //####################
    public MainFrame getMainFrame () { 
        return c_MainFrame; 
    }   
    //####################
//    public BinaryFileManager getBinaryFileManager () { 
//           return c_BinFileManager;
//    } 
     //####################
     public ItemDataManager getItemDataManager () { 
            return c_ItemDataManager;
     } 
    //####################
    public DBFileManager getDBFileManager () { 
           return c_DBFileManager;
    } 
    //####################
    public ParseDefFile getParseDefFile () { 
           return c_ParseDefFile;
    } 
    //####################
    public ParseTestFile getParseTestFile () { 
           return c_ParseTestFile;
    } 
    //####################
    public DataBase  getDataBase() { 
           return c_database;
    } 
    //####################
    public void setDwarf (String FullFileName) { 
        try {
            c_Dwarf.InitDwarf(FullFileName);
        }     catch (Exception ex) {
                     System.out.println(ex.getMessage());
                     }
    }   
    //####################
    public DwarfMain getDwarf () { 
        return c_Dwarf; 
    }
    //####################
    public ElfFileChooser getElfChooser () { 
        return c_elfChooser; 
    }
    //####################
    public JTreeTable getTreeTable () { 
           return c_treeTable;
    }
    //####################
    public CStructTreeTableModel   getTreeTableModel () {
           return c_TreeTabModel;
    }
    //####################
    public Symbol_Tree   getSymbolTree () { 
           return c_SymbTree;
    }
    //####################
    public String getTempDir() {
        return c_TempDir;
    }
    //####################
    public String getTempFullFileName(String FileName) {
        return c_TempDir + FileName;
    }
//    //####################
//    public String getTestDir() {
//        return c_TestDir;
//    }
    //####################
    public void Save () {
        c_ParseEnvFile.ExportRes();
    }
    //#####################################
    public void setIntVar (int varType, int val) {
           switch(varType) {
               case V_INT_TESTNUM:  
               if(val <= 5000)  // after some time restart from 0
                  c_TestNum  = val;   
               break;
               default : break;
           }
    }
    //#####################################
    public int getIntVar (int varType) {
           switch(varType) {
               case V_INT_TESTNUM:     return c_TestNum; 
               default : return -1;
           }
    }
    //#####################################
    public OutputDisplay getOutFrame () {
           return c_OutFrame;
    }
    //#####################################
    public ConfigFrame getConfigFrame () {
           return c_ConfigFrame;
    }
    
    
} // end classEnviron
