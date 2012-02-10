/**
 * Credit Worthiness System Version 1.0
 */
package AppProperties;

import java.io.*;
import java.net.URL;
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
    private static String propertiesFile = 
            // "/home/reagan/Desktop/cws.config"; 
            // "C:\\Documents and Settings\\Reayn\\Desktop\\cws.config";
            "runtime_required" + File.separator + "cws.config" ;
    private static AppProperties thisInstance = null ;
    
    private AppProperties() 
    {      
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
     * @param properties
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
        // initialise the properties object
        properties = new Properties() ;
        
        // create the file input stream
       // InputStream in;
        
        // get the properties
        try 
        {  
            // in = getClass().getResourceAsStream(propertiesFile);
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
        System.out.println(AppProperties.getInstance().getValueOf("dbName"));
    }
}