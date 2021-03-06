/**
 * Credit Worthiness System Version 1.0
 *  @desc The system determines the credit worth of
 *  a potential customer and helps to determine if 
 *  the customer should obtain items on credit or not
*/

package credit.worthiness.system;

import AppSplashScreen.AppSplashScreen;
import UI.AppPanel;
import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class CreditWorthinessSystem
{
    /**
     * @param args the command line arguments
     */
    
    private static String currentUser = null ; // specifies the current user
    private static int currentUserID = 0 ; // will store the current user ID
                                            // at startup, the userID specified 
                                            // is 0
    
    public static void main(String[] args)
    {
        // set the look and feel
        try 
        {
            // Set System L&F
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        } 
        catch (UnsupportedLookAndFeelException e) {
           // handle exception
        }
        catch (ClassNotFoundException e) {
           // handle exception
        }
        catch (InstantiationException e) {
           // handle exception
        }
        catch (IllegalAccessException e) {
           // handle exception
        }
        
       // ensure that everything is done in the EDT
        Runnable mainApp = new Runnable() 
        {
            // the main panel to be displayed
            AppPanel panel = null;
       
            @Override
            public void run() 
            {
                // get the splash screen object
                // and display the splash screen
                AppSplashScreen splash = new AppSplashScreen();
                
                // check the database status
                // @TODO
                splash.DisplayLoadingOperation(splash.graphicsObj,
                                    "Checking Database status...");
                if(!SystemCheck.CheckDatabase())
                {
                    splash.DisplayLoadingOperation(splash.graphicsObj,
                                   SystemCheck.getStartupError());
                    System.exit(0);
                }
                
                // check and/or create folder for storing 
                // images
                splash.DisplayLoadingOperation(splash.graphicsObj,
                                    "Checking images folder...");
                if(!SystemCheck.checkImageFolder())
                {
                    splash.DisplayLoadingOperation(splash.graphicsObj,
                                   SystemCheck.getStartupError());
                    System.exit(0);
                }
                
                // display message showing that application is being
                // launched
                splash.DisplayLoadingOperation(splash.graphicsObj,
                                    "Launching Application...");
                
                splash.close();
                
                // display the main window
                panel = new AppPanel();
                panel.setVisible(true);
            }            
        };
                   
        // start the application
        EventQueue.invokeLater(mainApp);
    }
    
    public static void specifyCurrentUserID(int userID)
    {
        currentUserID = userID ;
    }
    
    public static int getCurrentUserID()
    {
        return currentUserID ;
    }
    
    public static void specifyCurrentUser(String username)
    {
        currentUser = username ;
    }
    
    public static String getCurrentUser()
    {
        return currentUser ;
    }       
}
