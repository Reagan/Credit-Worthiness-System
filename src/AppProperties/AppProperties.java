/**
 * Credit Worthiness System Version 1.0
 */
package AppProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 * This class implements a singleton design pattern that allows 
 * the application to read and write to the properties file
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class AppProperties 
{
    private static Properties properties = null ;
    private static String propertiesFile = "runtime_required" + File.separator + "cws.config" ;
    private static AppProperties thisInstance = null ;
    
    // define the properties as  stored in the config file
    // Application properties 
    public final static String APP_NAME = "appName" ;
    public final static String APP_VERSION = "appVersion" ;
    public final static String APP_INFO = "appInfo" ;

    // database properties
    public final static String DB_NAME = "dbName" ;
    public final static String DB_HOST = "dbHost" ;
    public final static String DB_PORT = "dbPort" ;
    public final static String DB_USERNAME = "dbUsername" ;
    public final static String DB_PASSWORD = "dbPassword" ;
    public final static String DB_TYPE = "dbType" ;

    // about application information
    public final static String ABOUT_INFO = "aboutInfo" ;

    // chart settings
    public final static String CREDIT_PLOT_DISPLAYED = "creditPlotDisplayed" ;
    public final static String POPUP_DEFAULT = "hoverTransactionPopupDefaultOn" ;
    public final static String CHART_INITIAL_MONTH = "chartInitialDisplayMonthYear" ;

    // user icon images
    public final static String IMAGES_LOCATION = "imagesLocation" ;
    public final static String DEFAULT_USER_ICON = "defaultUserImageIcon" ;
    
    private AppProperties() 
    {      
        // initialise the properties object
        properties = new Properties() ;
                
        // initialise the variables
        initialise();        
    }
    
    /**
     * This method returns an instance of the Properties object
     * and the config file object 
     * @return 
     */
    public static AppProperties getInstance()
    {
        thisInstance = new AppProperties();
            
        return thisInstance ;
    }
    
    /**
     * This method obtains the value of a specific key in the config file
     * @param key 
     * @return 
     */
    public String getValueOf(String key)
    {
        String value = null ;
        
        if(properties.containsKey(key))
        {
            value = properties.getProperty(key);
        }
        
        return value ;
    }
    
    /**
     * This method sets the value for a specific key or adds that key if
     * it is not found in the properties file
     * @param key
     * @param value
     * @return 
     */
    public boolean setValueOf(String key, String value)
    {
        if(properties.containsKey(key))
        {
            properties.setProperty(key, value);
        }
                
        return false ;
    }
    
    /**
     * This method initializes the object variables 
     * required to fetch keys & values from the config file
     */
    private void initialise()
    {       
        // get the properties
        try 
        {             
            FileInputStream in = new FileInputStream(propertiesFile);
            
            if(null==in)
            {
                JOptionPane.showMessageDialog(null, "There was an error "
                        + "creating the properties object", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return ;
            }
            
            // load the properties file in the properties object
            properties.load(in);
            in.close() ;
        } 
        catch (FileNotFoundException ex) 
        {
            System.out.println("Error obtaining the properties file : " + ex) ;           
        }
        catch (IOException ex) 
        {
            System.out.println("Error loading the properties : " + ex) ;            
        }
    }
    
    // testing the retrieval of the properties from the 
    // config file
    public static void main(String[] args)
    {
        System.out.println(AppProperties.getInstance().getValueOf(AppProperties.ABOUT_INFO));
    }
}