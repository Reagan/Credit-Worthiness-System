/**
 * Credit Worthiness System Version 1.0
 */
package ClientImages;

import java.io.File;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class ClientImages 
{
    private static File imagesFolder = null;
    private String path = "../images";
    
    public ClientImages()
    {
       imagesFolder =  new File(path);
    }
    
    public static boolean checkImagesFolder()
    {
        if(imagesFolder.exists())
        {
            return true;
        }
        
        return false;        
    }
}
