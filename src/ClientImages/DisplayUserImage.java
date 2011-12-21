/**
 * Credit Worthiness System Version 1.0
 */
package ClientImages;

import UI.LeftPanel;
import UI.UserImage;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.SwingWorker;

/**
 * This class sets and gets the ImageIcon class
 * that displays each of the users profile picture
 * 
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class DisplayUserImage 
{
        private BufferedImage userImage = null  ;
        private String userImageName = null ;
        private UserImage imagePanel ;

       public BufferedImage getUserImage(String fileName, final UserImage iPanel) throws IOException                
       {  
           imagePanel = iPanel ;
           
           // show a messsage that an image is being loaded
           imagePanel.setLoadingLabel(true);
           
           // set the imageName
           userImageName = fileName ;
           final String path = userImageName ;
                                 
           SwingWorker loadImage = new SwingWorker()
           {
                @Override
                protected Object doInBackground() throws IOException 
                {
                    BufferedImage ic ;
                    
                    ic = createBufferedImage(path);      
                    
                    if(null == ic)
                    {
                        System.out.println("Error accessing the image at " +
                                 userImageName);
                    }
                    
                    return ic ;
                }
                
                @Override
                protected void done()
                {
                    // remove the loading label
                    imagePanel.setLoadingLabel(false);
                    
                    try 
                    {
                        userImage =  (BufferedImage) get();
                        System.out.println("Setting the user image") ;
                        // display the image when required
                        imagePanel.setUserImage(userImage);
                    } 
                    catch (InterruptedException ex) 
                    {
                        System.out.println("Error: " + ex.toString());
                    } 
                    catch (ExecutionException ex) 
                    {
                        System.out.println("Error: " + ex.toString());
                    }
                }
           };

           //  run the swing worker
           loadImage.execute();
            
           // return the picture
           return userImage;            
       }

       private BufferedImage createBufferedImage(String path) throws IOException
       {
            BufferedImage userPicture = ImageIO.read(new File(path));
            return userPicture;            
       }
}
