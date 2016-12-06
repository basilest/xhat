/*
 * Created on Dec 18, 2011
 *
 * 
 * @author  Basile Stefano
 */
package  sbs;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.io.FileWriter;

/**
 * @author   Basile Stefano
 */
//####################
public class sbs_get_var extends sbs_generic {
    
    int     x1;
    int     x2;
    int     textHigh  = 20;
    
    public static final String  STR_KEYWORD = "getvalue";
    
    String  user_name;
    String  CTypeName;
    int     CTypeSize;
    
    //####################
    public sbs_get_var (sbsTest test, int num, int x1, int x2, int y) {
         super(test, num, sbs_generic.GET_VAR, y);
         user_name      = STR_KEYWORD +":?";
         CTypeName      = null;
         CTypeSize      = 0;         
         this.x1        = x1; 
         this.x2        = x2;
         Y_UpReserved   = textHigh;
         color          = Color.lightGray;
    }
    //####################
    public sbs_get_var (sbsTest test, int num) {
         super(test, num, sbs_generic.GET_VAR, 0);
         user_name      = null;
         CTypeName      = null;
         CTypeSize      = 0;         
         x1             = 0; 
         x2             = 0;
         Y_UpReserved   = textHigh;
         color          = Color.lightGray;
    }
    //####################
    public sbs_get_var (sbs_get_var ori, int num, int y) {
         super(ori, num, y);
         user_name      = ori.getStringVar(sbs_generic.V_STR_USERNAME);
         CTypeName      = ori.getStringVar(sbs_generic.V_STR_CTYPENAME);
         CTypeSize      = ori.getIntVar(sbs_generic.V_INT_CTYPESIZE);         
         x1             = ori.getIntVar(sbs_generic.V_INT_X1);  
         x2             = ori.getIntVar(sbs_generic.V_INT_X2); 
    }
    //#####################################
    public void setIntVar (int varType, int val) {
           switch(varType) {
               case V_INT_X1:         x1        = val;   break; 
               case V_INT_X2:         x2        = val;   break; 
               case V_INT_TEXT_H:     textHigh  = val;   break;
               case V_INT_CTYPESIZE:  CTypeSize = val;   break;
               default : super.setIntVar(varType, val);
           }
    }
    //#####################################
    public int getIntVar (int varType) {
           switch(varType) {
               case V_INT_X1:         return x1; 
               case V_INT_X2:         return x2; 
               case V_INT_TEXT_H:     return textHigh; 
               case V_INT_CTYPESIZE:  return CTypeSize;
               default :              return super.getIntVar(varType);
           }
    }
    //#####################################
    public void setStringVar (int varType, String val) {
           switch(varType) {
               case V_STR_USERNAME:      user_name  = val;    break;
               case V_STR_CTYPENAME:     CTypeName  = val;    break;
               default :  super.setStringVar(varType, val);   break;
           }
    }
    //#####################################
    public String getStringVar (int varType) {
           switch(varType) {
               case V_STR_USERNAME:      return user_name;
               case V_STR_CTYPENAME:     return CTypeName;
               case V_STR_KEYWORD:       return STR_KEYWORD;
               default :                 return super.getStringVar(varType);
           }
    }
    //####################
    public void exportInTestFile (FileWriter out) {
       try {
           super.export(out);
           out.write (ParseTestFile.T_STR_ITMUNAME  + user_name + "\n");
           out.write (ParseTestFile.T_STR_ITMCNAME  + CTypeName + "\n");
//           out.write (ParseTestFile.T_STR_ITMDSCKEY + DBDesckey + "\n");
           out.write (ParseTestFile.T_STR_ITMDATKEY + DBDatakey + "\n");
           out.write (ParseTestFile.T_STR_ITMSIZE   + CTypeSize + "\n");
           out.write (ParseTestFile.T_STR_ITMX1     + x1        + "\n");
           out.write (ParseTestFile.T_STR_ITMX2     + x2        + "\n");
           out.write (ParseTestFile.T_STR_ITMEND    + "\n\n");
        } catch (Exception e) {System.out.println("Unable to export Get Var "+ user_name);}  
    }       
//    //####################
//    public void export2Lis (FileWriter out) {
//       try {
//           String line = "read_var\n" + getStringVar(sbs_generic.V_STR_CTYPENAME) + "\n";
//           out.write(line);
//
//        } catch (Exception e) {System.out.println("Unable to export to Lis Rea Var "+ user_name);}  
//    }       
    //####################
    public void paintItem (Graphics g) {

        float      dash[] = {10.0f};
        Graphics2D g2         = (Graphics2D) g;
        Color      oldColor   = g2.getColor();
        Stroke     oldStroke  = g2.getStroke();
        FontRenderContext frc = g2.getFontRenderContext();
        TextLayout textL      = new TextLayout("get:" + user_name, font, frc);

        g2.setColor(color);
        g2.setStroke(new BasicStroke(3.0f,
                         BasicStroke.CAP_BUTT,
                         BasicStroke.JOIN_MITER,
                         7.0f, dash, 0.0f));
        textL.draw(g2, x1+5, Y);
        Line2D.Double line = new Line2D.Double(x1, Y, x2, Y);
        g2.draw(line);

        // restore Graphics
        g2.setColor(oldColor);
        g2.setStroke(oldStroke);
        
  }
}



