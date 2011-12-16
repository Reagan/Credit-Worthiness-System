/**
 * Credit Worthiness System Version 1.0
 */
package AppSplashScreen;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.Toolkit;
import java.util.Map;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class AppSplashScreen
{     
    // declare variables 
    public Graphics2D graphicsObj = null; // stores the graphics object
    private boolean GUIDisplayed = false ;  // indicates on whether the GUI is visible
    private SplashScreen splash = null; // stores the splash screen graphics
        
    private Toolkit tk = null ; // to make the fonts display as
                                // best as posible       
    private Map desktopHints = null;
        
    public AppSplashScreen()
    {      
        splash = SplashScreen.getSplashScreen();
        tk = Toolkit.getDefaultToolkit();
        desktopHints = (Map) (tk.getDesktopProperty("awt.fonts.desktophints"));
        
        if(splash == null ) // splash image not found
        {
            System.out.println("Error launching CWS vs 1.0...exiting application");
            System.exit(0);
        }
        
        // initialise the graphics object
        graphicsObj = splash.createGraphics();
        
        if(graphicsObj == null)
        {
            System.out.println("Error acquiring the Graphics Object...exiting Application");
            System.exit(0);
        }        
    }
    
    public void DisplayLoadingOperation(Graphics2D graphicsObject, String loadingOperation)
    {
        int yPosition = 160;  // point at which the animation starts
        
        for (int i = 0 ; i < 10 ; i++)
        {
            // Alpha composite for the loading operation
            // text that is displayed on the splash screen
            
            graphicsObject.setComposite(AlphaComposite.Clear);
            graphicsObject.fillRect(60, yPosition, 200, 40);
            graphicsObject.setPaintMode();
            
            // use desktop hints to make the font
            // look better depending on the platform
            if(null != desktopHints)
            {
                graphicsObject.addRenderingHints(desktopHints);
            }
            
            // now draw the text
            if(yPosition == 142)
            {
                graphicsObject.setColor(Color.WHITE);
            }
            else
            {
                graphicsObject.setColor(Color.BLACK);
            }

            graphicsObject.drawString(loadingOperation, 60, yPosition+10);

            // update the splash screen
            splash.update();         

            // wait a little so that the splash image is seen
            try
            {
                Thread.sleep(90);
            }
            catch(InterruptedException e)
            {
                   System.out.println(e.toString());
            }
            
            yPosition -= 2;
        }
    }

    /**
     * Updates the message displayed on the splash screen    
     */
    public void update() 
    {
        splash.update();
    }

    public void close() {
        splash.close();
    }    
}
