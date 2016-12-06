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
public class sbs_wait extends sbs_generic {
    
    int     x1;
    int     x2;
    int     textHigh  = 20;
    int     waitTime;
    
 //  ####################
    public sbs_wait (sbsTest test, int num, int waitTime, int y, int x1, int x2) {
         super(test, num, sbs_generic.WAIT, y);
         this.waitTime = waitTime; 
         this.x1       = x1; 
         this.x2       = x2;
         Y_UpReserved  = textHigh;
         color         = Color.gray;
    }
//  ####################
    public sbs_wait (sbsTest test, int num) {
         super(test, num, sbs_generic.WAIT, 0);
         this.waitTime = 0; 
         x1            = 0; 
         x2            = 0;
         Y_UpReserved  = textHigh;
         color         = Color.gray;
    }
    //####################
    public sbs_wait (sbs_wait ori, int num, int y) {
         super(ori, num, y);
         waitTime       = ori.getIntVar(sbs_generic.V_INT_TIME);         
         x1             = ori.getIntVar(sbs_generic.V_INT_X1);  
         x2             = ori.getIntVar(sbs_generic.V_INT_X2); 
    }
    //#####################################
    public void setIntVar (int varType, int val) {
           switch(varType) {
               case V_INT_X1:         x1        = val;   break; 
               case V_INT_X2:         x2        = val;   break; 
               case V_INT_TEXT_H:     textHigh  = val;   break;
               case V_INT_TIME:       waitTime = val;   break;
               default : super.setIntVar(varType, val);
           }
    }
    //#####################################
    public int getIntVar (int varType) {
           switch(varType) {
               case V_INT_X1:         return x1; 
               case V_INT_X2:         return x2; 
               case V_INT_TEXT_H:     return textHigh; 
               case V_INT_TIME:       return waitTime;
               default :              return super.getIntVar(varType);
           }
    }
    //####################
    public void exportInTestFile (FileWriter out) {
       try {
           super.export(out);
           out.write (ParseTestFile.T_STR_ITMWAIT + waitTime + "\n");
           out.write (ParseTestFile.T_STR_ITMX1    + x1      + "\n");
           out.write (ParseTestFile.T_STR_ITMX2    + x2      + "\n");
           out.write (ParseTestFile.T_STR_ITMEND   + "\n\n");
        } catch (Exception e) {System.out.println("Unable to export Wait ");}  
    }       
    //####################
    public void export2Lis (FileWriter out) {
       try {
           String line = "timeout\n" + getIntVar(sbs_generic.V_INT_TIME);
           out.write(line);

        } catch (Exception e) {System.out.println("Unable to export to Lis Wait");}  
    }       
//  ####################
    public void paintItem (Graphics g) {

        float      dash[] = {10.0f};
        Graphics2D g2         = (Graphics2D) g;
        Color      oldColor   = g2.getColor();
        Stroke     oldStroke  = g2.getStroke();
        FontRenderContext frc = g2.getFontRenderContext();
        TextLayout textL      = new TextLayout("timeout:" + waitTime, font, frc);


        g2.setColor(color);
//        g2.setStroke(new BasicStroke(3.0f,
//                         BasicStroke.CAP_BUTT,
//                         BasicStroke.JOIN_MITER,
//                         10.0f, dash, 0.0f));
        textL.draw(g2, x1+5, Y);
        Line2D.Double line = new Line2D.Double(x1, Y, x2, Y);
        g2.draw(line);

        // restore Graphics
        g2.setColor (oldColor);
        g2.setStroke(oldStroke);
        
  }
//####################
}



