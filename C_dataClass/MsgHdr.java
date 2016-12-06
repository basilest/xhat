/*
 * Created on Jan 29, 2012
 * @author Basile Stefano
 */
package C_dataClass;
import javax.swing.tree.DefaultMutableTreeNode;
/**
 * @author Basile Stefano
 *
 */
public class MsgHdr {

    public static final String STR_SOURCE_SYS = "source_sys";
    public static final String STR_DEST_SYS   = "dest_sys";
    public static final String STR_SOURCE_MBX = "source_mbx";
    public static final String STR_DEST_MBX   = "destination";
    public static final String STR_NAME       = "name";

    
    
    public DefaultMutableTreeNode source_sys;
    public DefaultMutableTreeNode dest_sys;
    public DefaultMutableTreeNode source_mbx;
    public DefaultMutableTreeNode dest_mbx;
    
    public void reset () {
        source_sys  =  null;
        dest_sys    =  null;
        source_mbx  =  null;
        dest_mbx    =  null;
    }
}
