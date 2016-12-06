/*
 * Created on Nov 28, 2011
 *
 * 
 * @author  Basile Stefano
 */
package treetable;

/**
 * @author  Basile Stefano
 */
   //######################################################
   //######################################################
public class TreeRootInfo {

    String   Name;
    public static final String ROOT_DEFAULT_STRING = "Set Var";

     public TreeRootInfo() {
         Name = ROOT_DEFAULT_STRING;
     }
     
    public void setName(String  newName) {
        if (newName == null)
            Name = ROOT_DEFAULT_STRING;
        else
            Name = newName;
    }

    public String toString() {
           return Name;
    }
     
} // --- class TreeNodeInfo

