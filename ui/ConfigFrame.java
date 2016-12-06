/*
 * Created on Feb 18, 2012
 * @author Basile Stefano
 */
package ui;

    import java.awt.*;
    import java.awt.event.*;
    import javax.swing.*;
    import java.io.*;
    import common.Environ;
    import common.FileManager;
    import sbs.sbsTest;
/**
 * @author Basile Stefano
 */
public class ConfigFrame implements ActionListener  {

    final static String  STR_SAVE   = "Save";
    final static String  STR_CHANGE = "Change";
    final static String  STR_CLOSE  = "Close";
    final static String  STR_CANCEL = "Cancel";

    JFrame       ConfigFrame;
    JPanel       contentPane;
    
    JTextArea    tArea;
    JScrollPane  tScrollPane;

     Environ      environ;
     String       ConfFullFileName;

     int          frameX;
     int          frameY;
     int          frameWidth;
     int          frameHeight;

    JPanel           jButtonPane;
    JButton          jButtonSave;
    JButton          jButtonChange;
    JButton          jButtonClose;
    JButton          jButtonCancel;
    
    //#############################
    public ConfigFrame (Environ environ) {
        this.environ = environ;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenSizeWidth      = screenSize.width; 
        int screenSizeHeight     = screenSize.height;

        frameX        = screenSizeWidth/3 * 2 - 15;
        frameY        = screenSizeHeight/3;
        frameWidth    = screenSizeWidth/3 - 2;
        frameHeight   = screenSizeHeight/3 * 2 - 80;

        ConfigFrame = new JFrame ();
        ConfigFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        contentPane  = new JPanel(new BorderLayout());
        jButtonPane  = new JPanel(new FlowLayout());
        contentPane.setPreferredSize(new Dimension(frameWidth, frameHeight));

        jButtonSave   = new JButton(STR_SAVE);
        jButtonCancel = new JButton(STR_CANCEL);
        jButtonClose  = new JButton(STR_CLOSE);
        jButtonChange = new JButton(STR_CHANGE);

        jButtonSave   .setActionCommand(STR_SAVE);
        jButtonCancel .setActionCommand(STR_CANCEL);
        jButtonClose  .setActionCommand(STR_CLOSE);
        jButtonChange .setActionCommand(STR_CHANGE);

        jButtonSave   .addActionListener(this);
        jButtonCancel .addActionListener(this);
        jButtonClose  .addActionListener(this);
        jButtonChange .addActionListener(this);

        jButtonPane.add(jButtonClose);
        jButtonPane.add(jButtonCancel);
        jButtonPane.add(jButtonSave);
        jButtonPane.add(jButtonChange);

        tArea = new JTextArea();
        tArea.setEditable(true);
        tArea.setMargin(new Insets(3,5,3,4));
        tArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        tScrollPane = new JScrollPane(tArea);
        tScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tScrollPane.setBorder(BorderFactory.createLoweredBevelBorder());

        contentPane.add(tScrollPane, BorderLayout.CENTER);
        contentPane.add(jButtonPane, BorderLayout.SOUTH);
        
        ConfigFrame.setContentPane(contentPane);
 }
   
    //######################################################
      public void DisplayFrame (String FullFileName) {
        
        ConfFullFileName = FullFileName;
        File f = new File (FullFileName);
        if (f.exists() == false) 
            ChooseFile();
        else
            loadTextArea(FullFileName);
        
        ConfigFrame.setBounds(frameX, frameY, frameWidth, frameHeight);
        ConfigFrame.pack();
        ConfigFrame.setVisible(true);
      }
      
    //######################################################
      private void loadTextArea (String FileName) {
      try {
            FileReader in = new FileReader(FileName);
            tArea.read(in, null);
            in.close();
       } catch (IOException e) { }
      }
    //######################################################
      private void SaveFile() {
      try {
            FileWriter out = new FileWriter(ConfFullFileName);
            tArea.write(out);
            out.close();
       } catch (Exception e) {System.out.println("Unable to export Test");}  
      }
    //######################################################
      public void ChooseFile() {
         JFileChooser fc = new JFileChooser(".");
         int val = fc.showOpenDialog(environ.getMainFrame());
                  
         if (val  == JFileChooser.APPROVE_OPTION) {
             loadTextArea(fc.getSelectedFile().getPath());
       }
      }
    //######################################################
    //#####################################
      public void actionPerformed(ActionEvent e) {

         String command = e.getActionCommand();
         String str;
         Object value;
         
         do {
            if (STR_SAVE.equals(command)) {
                SaveFile ();
                break;
            }
            if (STR_CLOSE.equals(command)) {
                SaveFile ();
                ConfigFrame.dispose();
                break;
            }
            if (STR_CANCEL.equals(command)) {
                ConfigFrame.dispose();
                break;
            }         
            if (STR_CHANGE.equals(command)) {
                ChooseFile();
                break;
            }         
         } while(false) ;
      }
      
      
      
} // end class OutputDisplay
