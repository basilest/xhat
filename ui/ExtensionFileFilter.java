/*
 * Created on Dec 18, 2011
 *
 * 
 * @author  Basile Stefano
 */
package  ui;

import java.io.*;

/**
 * @author   Basile Stefano
 */
/*
 * ExtensionFileFilter.java
 */
    //#################  FileFilter    
    class ExtensionFileFilter  extends javax.swing.filechooser.FileFilter  {

        String Extensions[];
        String Description;
        
        public ExtensionFileFilter (String extensions[], String description)
        {
            Extensions = extensions;
            //Extensions = new String [ext.length];
            //System.arraycopy(ext, 0, Extensions, 0, ext.length);
            Description = description;
        }
            
        
        public boolean accept(File f) {

            String extension;
            int   i;
            
            if (f.isDirectory()) {
                return true;
            }

            extension = getExtension(f);
            if (extension != null && Extensions != null) {
                for ( i=0; i < Extensions.length; i++ )
                      if (extension.equals(Extensions[i]))  
                          return true;
            }

            return false;
        }


        private String getExtension (File f) {
            String ext = null;
            String s = f.getName();
            int i = s.lastIndexOf('.');

            if (i > 0 &&  i < s.length() - 1) {
                ext = s.substring(i+1).toLowerCase();
            }
            return ext;
        }

        

        //The description of this filter
        public String getDescription() {
            return Description;
        }
 } // ExtensionFileFilter
    
