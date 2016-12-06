/*
 * Created on Nov 28, 2011
 *
 * 
 * @author  Basile Stefano
 */
package treetable;

import common.*;
import java.util.*;

/**
 * @author  Basile Stefano
 */
   //######################################################
   //######################################################
public class TreeNodeInfo {
    
    public final static int C_TYPE_UNKNOWN   =   0; 
    public final static int C_TYPE_BASE      =   1; 
    public final static int C_TYPE_ENUM      =   2; 
    public final static int C_TYPE_POINTER   =   3;
    public final static int C_TYPE_MEMBER    =   4;
    public final static int C_TYPE_UNION     =   5;
    public final static int C_TYPE_STRUCT    =   6;
    public final static int C_TYPE_ARRAY     =   7;
    public final static int C_TYPE_RANGE     =   8;
    public final static int C_TYPE_FUN       =   9;
    public final static int C_TYPE_FUN_PARAM =  10;

     int     NodeType ;
     int     Size;
     int     Offset;

     String  FieldName;
     String  FieldTypeName;
     Environ environ;

     Vector  RangeArray;        // For ARRAY-TYPE Nodes, the Vector of Ranges info

     //#########    CONSTRUCTOR     ###########
     public TreeNodeInfo (Environ environ, int type){

        this.environ     = environ;
        NodeType         = type;
        Size             = 0;
        Offset           = 0;
        FieldName        = "";
        FieldTypeName    = "";
        RangeArray       = null;
    }
     
    //####################
//    public void resetNode(String newFieldName) {
      public void resetNode() {
         NodeType         = C_TYPE_UNKNOWN;
         Size             = 0;
         Offset           = 0;
//         FieldName        = newFieldName;
         FieldName        = "";
         FieldTypeName    = "";
         RangeArray       = null;
     } 
    //####################
     public int getNodeType() {
            return NodeType;
     } 
    //####################
     public void setNodeType(int type) {
            NodeType = type;
     } 
    //####################
     public void setSize (int size) {
            Size = size;
     }
    //####################
     public int getSize (){
        return Size;
     }
    //####################
     public void setOffset (int offset) {
            Offset = offset;
     }
    //####################
     public int getOffset (){
        return Offset;
     }
    //####################
     public void   setFieldName (String name) {
         FieldName = name;
     } 
    //####################
     public String  getTypeName () {
         return FieldTypeName;
     } 
    //####################
     public void CatTypeName (String cat) {
         if (FieldTypeName.equals(""))
             FieldTypeName = cat;
         else
             FieldTypeName += (" " + cat);
     } 
    //#########    toString    ###########
     public String toString()  {
            return FieldName;
     }
    //####################
    public int getNumSubRange () {
         return (RangeArray == null) ? 0 : RangeArray.size();
    } 
    //####################
    public void AddArraySubrange (int cardinality) {
        
        if (cardinality > 0) {
            if (RangeArray == null)
                RangeArray   = new Vector (2);

            int spinnerIndex     = environ.getSymbolTree().addArrayRangeNode(this, cardinality);

            RangeArray.add (new ArrayRangeInfo (cardinality,  spinnerIndex));
        } 
    } 
    //####################
    public int getMaxSubRange_i (int i) {
        
        ArrayRangeInfo rangeInfo;
        int      max = -1;
        
        if ( i >= 0 && i < RangeArray.size()) {
             rangeInfo = (ArrayRangeInfo) RangeArray.get(i);
             max       = rangeInfo.cardinality; 
        }
        return max;  
    } 
    //####################
    public int getSpinnerIdxSubRange_i (int i) {
        
        ArrayRangeInfo rangeInfo;
        int      spinnerIndex = -1;
        
        if ( i >= 0 && i < RangeArray.size()) {
             rangeInfo    = (ArrayRangeInfo) RangeArray.get(i);
             spinnerIndex = rangeInfo.spinnerIndex; 
        }
        return spinnerIndex;   
    } 

    //####################
    //####################
    class ArrayRangeInfo {
        int cardinality;
        int spinnerIndex;
        
        ArrayRangeInfo(int cardinality, int spinnerIndex) {
            this.cardinality   = cardinality;
            this.spinnerIndex  = spinnerIndex;
        }
        
    } // --- class ArrayRangeInfo

} // --- class TreeNodeInfo

