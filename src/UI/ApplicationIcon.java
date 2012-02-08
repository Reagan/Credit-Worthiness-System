/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * This class uses a singleton design and allows each of the 
 * dialogs to embed the application icon on the title frame
 * 
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class ApplicationIcon 
{
    private String appPathIcon = "runtime_required" + File.separator + "images"
            + File.separator + "AppIcon.png" ;
    private static ApplicationIcon appIconClass = null ;
    private JFrame pFrame = null ;
    private JDialog pDialog = null ;
    private Image appIcon = null ;
    
    private ApplicationIcon() 
    {
        // get the image icon
        try {            
            appIcon = ImageIO.read(new File(appPathIcon));
        } catch (IOException ex) {
            System.out.println("Error accessing the application icon : " + ex) ;
        }
    }
    
    /**
     * This method returns an instance of the 
     * Application Icon class
     * @return 
     */
    public static ApplicationIcon getInstance()
    {
        if (null == appIconClass)
        {
            appIconClass = new ApplicationIcon();
        }
        return appIconClass ;
    }
    
    /**
     * This method sets the icon to the JFrame
     * @param currFrame 
     */
    public void setApplicationIcon(JFrame currFrame)
    {    
             pFrame = currFrame ;
             pFrame.setIconImage(appIcon);
    }

    public void setApplicationIcon(JDialog dialog) {
        pDialog = dialog ;
        pDialog.setIconImage(appIcon) ;        
    }    
}
