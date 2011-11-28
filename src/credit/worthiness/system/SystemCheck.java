/**
 * Credit Worthiness System Version 1.0
 */
package credit.worthiness.system;

import ClientImages.ClientImages;
import DbConnection.DatabaseConnection;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class SystemCheck 
{
    private boolean imageFolderOK = false;
    private boolean databaseOK = false;
    private static String startupError = null;
    
    private static DatabaseConnection dbConn = null;
    private static ClientImages cImages = null;
    
    public SystemCheck(){}
    
    /**
     * Checks the MySQL Database and ensures 
     * that the required DB is available
     * @return state of the MySQL DB
     */
    public static boolean CheckDatabase()
    {
        dbConn = new DatabaseConnection();
        
        if(dbConn.connect())
        {
            return true;
        }
        
        // must be an error with accessing the database if 
        // we are getting here!
        startupError = "Error with database status...";
        
        return false;
    }
    
    /**
     * Checks for the images folder. This folder
     * stores the images of the various clients to the
     * CWS
     * @return state of the images folder
     */
    public static boolean checkImageFolder()
    {
        cImages = new ClientImages();
        
        if(cImages.checkImagesFolder())
        {
            return true; 
        }
        
        // error accessing the folder
        startupError = "Error accessing the images folder...";
                
        return false;
    }
    
    public static String getStartupError()
    {
        return SystemCheck.startupError;
    }
}
