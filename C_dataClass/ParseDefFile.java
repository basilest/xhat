/*
 * Created on Jan 14, 2012
 * @author Basile Stefano
 *
 */
package C_dataClass;

import javax.swing.tree.*;

import java.util.*;

import java.io.*;
import java.util.regex.*;
import common.*;
import treetable.*;
/**
 * @author Basile Stefano
 *
 */
public class ParseDefFile {
    
    public final static String T_STR_CSYMBOL     = ".CSYMB:";
    public final static String T_STR_FLDNAME     = "FLDNAME:";
    public final static String T_STR_TOTSIZE     = "TOTSIZE:";
    public final static String T_STR_FLDTYPE     = "FLDTYPE:";
    public final static String T_STR_FLDTYPENAME = "FLDTYPENAME:";
    public final static String T_STR_FLDSIZE     = "FLDSIZE:";
    public final static String T_STR_FLDOFFST    = "FLDOFFST:";
    public final static String T_STR_ARYRNG      = "ARYRNG:";
    public final static String T_STR_FLDEND      = ".FLDEND:";

    public final static int T_ID_UNKNOWN     =  0;
    public final static int T_ID_CSYMBOL     =  1;
    public final static int T_ID_FLDNAME     =  2;
    public final static int T_ID_TOTSIZE     =  3;
    public final static int T_ID_FLDTYPE     =  4;
    public final static int T_ID_FLDTYPENAME =  5;
    public final static int T_ID_FLDSIZE     =  6;
    public final static int T_ID_FLDOFFST    =  7;
    public final static int T_ID_ARYRNG      =  8;
    public final static int T_ID_FLDEND      =  9;

    Pattern     pattFIELD;
    Pattern     pattMACRO_CMD;
    Pattern     pattINT;
    Matcher     m;
    
    BufferedReader   DefBuffFile;
    String           read_line;
    int              num_line;
    
    int     T_F_lev;
    int     T_F_type;
    String  T_F_Name;
    String  T_F_tail;
    
    Vector  LevelNodes;   // stores the Current parsing Node for each Level
    Environ environ;
    //######################################################
     public  ParseDefFile(Environ environ)
     {
         this.environ  = environ;
         pattFIELD     = Pattern.compile("^(\\d+) ([^:]+:)(.*)");
         pattMACRO_CMD = Pattern.compile("^(\\.[^:]+:)(.*)");
         pattINT       = Pattern.compile("(\\d+)");
         LevelNodes    = new Vector(5);  // 5 levels for Tree depth.  
     }
    //######################################################
    private void OpenDefFile( String fileName) 
    {
        try {
              System.out.println("Parsing Struct Def File:["+fileName+"]");

            DefBuffFile  = new BufferedReader(new FileReader(new File(fileName)));
            num_line = 0;

        } catch (FileNotFoundException e) {
                 System.out.println("Unable to Open Def File " + fileName);
          }
    }
    //######################################################
    private int  testLine () {

         String  field     = "";
         int     LineType  = T_ID_UNKNOWN;

//         T_F_lev   = -1;
         T_F_tail  = "";

         m = pattFIELD.matcher(read_line);
         if ( m.find())      // found '1 xxxx:...'
         {
              T_F_lev   = Integer.parseInt(m.group(1));
              field     = m.group(2);
              T_F_tail  = m.group(3);

              do {
                 if (field.equals(T_STR_FLDNAME)){
                 LineType = T_ID_FLDNAME; 
                 break;
                 }                     
                 if (field.equals(T_STR_FLDTYPE)){
                 LineType = T_ID_FLDTYPE; 
                 break;
                 }                    
                 if (field.equals(T_STR_FLDTYPENAME)){
                 LineType = T_ID_FLDTYPENAME; 
                 break;
                 }                  
                 if (field.equals(T_STR_FLDSIZE)){
                 LineType = T_ID_FLDSIZE; 
                 break;
                 }                      
                 if (field.equals(T_STR_FLDOFFST)){
                 LineType = T_ID_FLDOFFST; 
                 break;
                 }                     
                 if (field.equals(T_STR_ARYRNG)){
                 LineType = T_ID_ARYRNG; 
                 break;
                 }                     
             } while (false);
         }
         else { 
             m = pattMACRO_CMD.matcher(read_line);
             if ( m.find())  {    // found '.XXXX:'
                 field     = m.group(1);
                 T_F_tail  = m.group(2);
                 do {
                    if (field.equals(T_STR_FLDEND)){
                    LineType = T_ID_FLDEND;
                    break;
                    }                     
                } while (false);
           }
         }
         return LineType;
      }
    
    //######################################################
    private int  getIntFieldValue(String str) {

         int val = 0;

         m = pattINT.matcher(str);
         if ( m.find())      // found '123'
         {
              val   = Integer.parseInt(m.group(1));
         }
         return val;
      }
    //######################################################
    private  DefaultMutableTreeNode NewNode (int level) {
        DefaultMutableTreeNode node;
        TreeNodeInfo           info = new TreeNodeInfo(environ, TreeNodeInfo.C_TYPE_UNKNOWN);
        
        if (level == 0) {
            node = environ.getSymbolTree().getTreeRoot();
            LevelNodes.add(node);                    
        }
        else {
            node = new DefaultMutableTreeNode (info);
        
            if (LevelNodes.size() <= level) 
                LevelNodes.add(node);                    
            else
                LevelNodes.setElementAt(node, level);    // set as current node for its level
        }
        return node;
    }    
   //######################################################
   public void loadTree (String FileName) {

       int LineType ;
       DefaultMutableTreeNode  currentNode = null;
       TreeNodeInfo            currentInfo = null;

       try {
       environ.getSymbolTree().resetTree();
       LevelNodes.clear();   
       OpenDefFile(FileName);
       
       while ((read_line = DefBuffFile.readLine()) != null) {
           num_line++;
           LineType = testLine();
    
           switch(LineType) {
           case T_ID_FLDNAME:
                currentNode = NewNode (T_F_lev);
                currentInfo = (TreeNodeInfo)currentNode.getUserObject();
                currentInfo.setFieldName(T_F_tail);
                break;
           case T_ID_FLDTYPE:
                currentInfo.setNodeType(getIntFieldValue(T_F_tail));
                break;
           case T_ID_FLDTYPENAME:
                currentInfo.CatTypeName(T_F_tail);
                break;
           case T_ID_FLDSIZE:
                currentInfo.setSize(getIntFieldValue(T_F_tail));
                break;
           case T_ID_FLDOFFST:
                currentInfo.setOffset(getIntFieldValue(T_F_tail));
                break;
           case T_ID_ARYRNG:
                currentInfo.AddArraySubrange(getIntFieldValue(T_F_tail));
                break;
           case T_ID_FLDEND:
                if (T_F_lev > 0){
                    DefaultMutableTreeNode  father = (DefaultMutableTreeNode) LevelNodes.get(T_F_lev-1); 
                    environ.getTreeTableModel().insertNodeInto(currentNode, father, father.getChildCount());
                } 
                break;
           default : 
                break;
           }
         } // end while cycling

        DefBuffFile.close();
        
      } catch (IOException e) {
               System.out.println("Unable to parse Def File at line "+num_line);
               }
    
   }
   
}
