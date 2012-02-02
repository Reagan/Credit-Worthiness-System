/**
 * Credit Worthiness System Version 1.0
 */
package AppProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * This class implmenents a singleton design pattern that allows 
 * the application to read and write to the properties file
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class AppProperties 
{
    private static Properties properties = null ;
    private static String propertiesfile = "cws.config" ;
    
    private AppProperties()
    {
        properties = new Properties() ;
    }
    
    /**
     * This method returns an instance of the Properties object
     * and the config file object 
     * @return 
     */
    public static Properties getInstance()
    {
        FileInputStream in;
        try 
        {
            URL url = AppProperties.class.getResource(propertiesfile) ;
            File propertiesFile = new File(url.getFile()) ;
            in = new FileInputStream(propertiesFile);
            
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
        
        return properties;        
    }
    
    /**
     * This method obtains the value of a specific key in the config file
     * @param properties
     * @return 
     */
    public String getProperty(String key)
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
    public boolean setProperty(String key, String value)
    {
        if(properties.containsKey(key))
        {
            properties.setProperty(key, value);
        }
                
        return false ;
    }
}
