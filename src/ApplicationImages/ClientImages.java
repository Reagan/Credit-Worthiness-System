/**
 * Credit Worthiness System Version 1.0
 */
package ApplicationImages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class ClientImages 
{
    private static File imagesFolder = null;
    private static String imagesPath = null ;
    private static ClientImages cImages = null ;
    
    private ClientImages()
    {       
       imagesFolder =  new File(getImagePath());
    }
    
    public static ClientImages getInstance()
    {
        if ( null == cImages)
        {
            cImages =  new ClientImages(); 
        }
        return cImages ;
    }
    
    /**
     * This method gets the image path to where the images 
     * folder are stored...images path is picked from the db
     * @return 
     */
    public String getImagesPath()
    {
        return imagesPath ;
    }
    
    public static boolean checkImagesFolder()
    {
        if(imagesFolder.exists())
        {
            return true;
        }
        
        return false;        
    }
    
     /**
     * This method copies files from the source to the images 
     * folder
     * @param userPicPath
     * @param fileDestination 
     */
    public boolean copyFiles(String userPicPath, String fileDestination) 
            throws FileNotFoundException, IOException 
    {
        if(userPicPath == null || fileDestination == null)
        {
            System.out.println("Error: The destination or source file is invalid");
            return false ;
        }
        
        // initialise the files
        File source = new File(userPicPath) ;
        File destination = new File(fileDestination) ;
            
        InputStream in = new FileInputStream(source) ;
        OutputStream ou = new FileOutputStream(destination) ;
        
        byte[] buffer = new byte[1024] ;
        int len ;
        while((len = in.read(buffer)) > 0 )
        {
            ou.write(buffer, 0, len) ;
        }
        
        in.close();
        ou.close();
        
        return true ;        
    }    
    
    /**
     * This method obtains the config for the image path and sets it 
     * @TODO :  pick from the config file
     */
    private String getImagePath()
    {
        imagesPath = "runtime_required" +  File.separator + "images" 
            + File.separator;
        
        return imagesPath ;
    }
}
