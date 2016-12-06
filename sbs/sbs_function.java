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
public class sbs_function extends sbs_generic {
    
    int     x1;
    int     x2;
    int     textHigh  = 20;
    
    String  CTypeName;
    int     CTypeSize;
    
    //####################
    public sbs_function (sbsTest test, int num, int x1, int x2, int y) {
         super(test, num, sbs_generic.FUNCTION, y);
         CTypeName      = null;
         CTypeSize      = 0;         
         this.x1        = x1; 
         this.x2        = x2;
         Y_UpReserved   = textHigh;
         color          = Color.darkGray;
    }
    //####################
    public sbs_function (sbsTest test, int num) {
         super(test, num, sbs_generic.FUNCTION, 0);
         CTypeName      = null;
         CTypeSize      = 0;         
         x1             = 0; 
         x2             = 0;
         Y_UpReserved   = textHigh;
         color          = Color.darkGray;
    }
    //####################
    public sbs_function (sbs_function ori, int num, int y) {
         super(ori, num, y);
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
               case V_STR_CTYPENAME:     CTypeName  = val;    break;
               default :  super.setStringVar(varType, val);   break;
           }
    }
    //#####################################
    public String getStringVar (int varType) {
           switch(varType) {
               case V_STR_CTYPENAME:     return CTypeName;
               default :                 return super.getStringVar(varType);
           }
    }
    //####################
    public void exportInTestFile (FileWriter out) {
       try {
           super.export(out);
           out.write (ParseTestFile.T_STR_ITMCNAME + CTypeName + "\n");
           out.write (ParseTestFile.T_STR_ITMDATKEY + DBDatakey + "\n");
           out.write (ParseTestFile.T_STR_ITMSIZE   + CTypeSize + "\n");
           out.write (ParseTestFile.T_STR_ITMX1     + x1        + "\n");
           out.write (ParseTestFile.T_STR_ITMX2     + x2        + "\n");
           out.write (ParseTestFile.T_STR_ITMEND    + "\n\n");
     } catch (Exception e) {System.out.println("Unable to export Function "+ CTypeName);}  
    }       
   //####################
    public void paintItem (Graphics g) {

        float      dash[] = {10.0f};
        Graphics2D g2         = (Graphics2D) g;
        Color      oldColor   = g2.getColor();
        Stroke     oldStroke  = g2.getStroke();
        FontRenderContext frc = g2.getFontRenderContext();
        TextLayout textL      = new TextLayout("call:" + CTypeName, font, frc);

        g2.setColor(color);
//        g2.setStroke(new BasicStroke(3.0f,
//                         BasicStroke.CAP_BUTT,
//                         BasicStroke.JOIN_MITER,
//                         7.0f, dash, 0.0f));
        textL.draw(g2, x1+5, Y);
        Line2D.Double line = new Line2D.Double(x1, Y, x2, Y);
        g2.draw(line);

        // restore Graphics
        g2.setColor(oldColor);
        g2.setStroke(oldStroke);
        
  }
}



