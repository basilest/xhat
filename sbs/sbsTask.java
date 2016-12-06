/*
 * Created on Dec 18, 2011
 *
 * 
 * @author  Basile Stefano
 */
package  sbs;

import java.awt.*;
import java.awt.font.*;
import java.io.FileWriter;

import java.awt.geom.*;

/**
 * @author   Basile Stefano
 */
public class sbsTask {
    private  String         user_name;
    private  int            Mailbox;
    private  int            Processor;
    private  sbs_codeName   sbs_name;
    private  boolean        MainTask = false; 
    
    String                  MailboxName;

    private int  rectX;
    private int  rectY;
    private int  rectWidth;
    private int  rectHeight;
    
    private Color  color;

    private  Font font;

    private int bottom;

    private int LineX;
    private int LineY;

    public sbsTask(String name, String mailboxName, int mailbox, int proc, int x, int y, boolean maintask) {
        user_name   = name;
        sbs_name    = null;
        Mailbox     = mailbox;
        MailboxName = mailboxName;
        Processor   = proc;
		font        = new Font("Helvetica", 1, 12);
        rectX       = x;
        rectY       = y;
        rectWidth   = 0;
        rectHeight  = 0;
        LineX       = 0;
        LineY       = 0;
        MainTask    = maintask;
        if (maintask == true)
            color = Color.blue;
        else
            color = Color.black;
    }
    //#####################################
    public sbsTask (String name) {
        user_name   = name;
        sbs_name    = null;
        Mailbox     = -1;
        MailboxName = null;
        Processor   = -1;
        font        = new Font("Helvetica", 1, 12);
        rectX       = 0;
        rectY       = 0;
        rectWidth   = 0;
        rectHeight  = 0;
        LineX       = 0;
        LineY       = 0;
        color = Color.black;
        MainTask    = false;
    }
  //#####################################
    public void set_rectX (int newX) {
        rectX = newX;
    }
    //#####################################
    public void set_rectY (int newY) {
        rectY = newY;
    }
    //#####################################
    public void set_rectWidth (int newWidth) {
        rectWidth = newWidth;
    }
    //#####################################
    public void set_rectHeight (int newHeight) {
        rectHeight = newHeight;
    }
    //#####################################
    public int get_rectX () {
        return rectX;
    }
    //#####################################
    public int get_rectY () {
        return rectY;
    }
    //#####################################
    public int get_rectWidth () {
        return rectWidth;
    }
    //#####################################
    public int get_rectHeight () {
        return rectHeight;
    }
    //#####################################
    public void setLineX (int x) {
        LineX = x;
    }
    //#####################################
    public int getLineX () {
        return LineX;
    }
    //#####################################
    public void set_username (String name) {
        user_name = name;
    }
    //#####################################
    public String get_username () {
        return user_name;
    }
    //#####################################
    public void setMailboxName (String name) {
        MailboxName = name;
    }
    //#####################################
    public String getMailboxName () {
        return MailboxName;
    }
    //#####################################
    public void setMailbox (int mailbox) {
        Mailbox = mailbox;
    }
    //#####################################
    public int getMailbox () {
        return Mailbox;
    }
    //#####################################
    public void setProcessor(int proc) {
        Processor= proc;
    }
    //#####################################
    public int getProcessor () {
        return Processor;
    }
    //#####################################
    public boolean isMainTask () {
        return MainTask;
    }
    //#####################################
    public void setMainTask (boolean val) {
        MainTask = val;
        if (val == true)
            color = Color.blue;
        else
            color = Color.black;
    }
    //#####################################
    public void setBottom(int newBottom)
    {
        bottom = newBottom;
    }
    //#####################################
    public void setColor(Color c)
    {
        color = c;
    }
    //#####################################
    public Color getColor()
    {
        return color;
    }
    //#####################################
    public void export (FileWriter out) {
    try {
         out.write (ParseTestFile.T_STR_TSKNAME     + user_name   + "\n");
         out.write (ParseTestFile.T_STR_TSKPROC     + Processor   + "\n");
         out.write (ParseTestFile.T_STR_TSKMBX      + Mailbox     + "\n");
         out.write (ParseTestFile.T_STR_TSKMBXNAME  + MailboxName + "\n");
         out.write (ParseTestFile.T_STR_TSKRECTX    + rectX       + "\n");
         out.write (ParseTestFile.T_STR_TSKRECTY    + rectY       + "\n");
         if (MainTask)
             out.write (ParseTestFile.T_STR_TSKMAIN + "\n");
         out.write (ParseTestFile.T_STR_TSKEND      + "\n\n");
     } catch (Exception e) {System.out.println("Unable to export Task "+ user_name);}  
   }


    //#####################################
    public void paintTask (Graphics g) {

        Graphics2D        g2       = (Graphics2D) g;
        FontRenderContext frc      = g2.getFontRenderContext();
        TextLayout        textL    = new TextLayout(user_name, font, frc);
        Color             oldColor = g2.getColor();
        
        g2.setColor(color);

        textL.draw(g2, rectX, rectY);
        Rectangle2D bounds = textL.getBounds();
        rectWidth  = (int)bounds.getWidth();
        rectHeight = (int)bounds.getHeight();
        bounds.setRect(bounds.getX() + rectX,
                       bounds.getY() + rectY,
                       rectWidth,
                       rectHeight);
        LineX = rectX  + rectWidth/2;
        LineY = rectY  + rectHeight;

        Rectangle clip =  g2.getClipBounds();
        //Line2D.Double line = new Line2D.Double(LineX, LineY, LineX , 400 );
        Line2D.Double line = new Line2D.Double(LineX, LineY, LineX , bottom);

        //Line2D.Double line = new Line2D.Double(LineX, LineY, LineX , clip.getHeight());
        //g2.draw(bounds);
        g2.draw(line);
        
        g2.setColor(oldColor);  // restore Graphics
    }


}

