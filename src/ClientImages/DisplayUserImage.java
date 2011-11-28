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
        private static BufferedImage userImage = null  ;
        private static String imgDir = "../images/" ;
        private static String userImageName = null ;
        private static UserImage imagePanel ;
    
       public BufferedImage getUserImage(String fileName, final UserImage imagePanel) throws IOException                
       {  
           this.imagePanel = imagePanel ;
           
           // show a messsage that an image is being loaded
           LeftPanel.showLoadingLabel(true);
           
           // set the imageName
           userImageName = fileName ;
           String path = imgDir + userImageName ;
                                 
           SwingWorker loadImage = new SwingWorker()
           {
                @Override
                protected Object doInBackground() throws IOException 
                {
                    BufferedImage ic ;
                    
                    ic = createBufferedImage(imgDir + userImageName);      
                    
                    if(null == ic)
                    {
                        System.out.println("Error accessing the image at " +
                                imgDir + userImageName);
                    }
                    
                    return ic ;
                }
                
                @Override
                protected void done()
                {
                    // remove the loading label
                    LeftPanel.showLoadingLabel(false);
                    try 
                    {
                        userImage =  (BufferedImage) get();
                        
                        // display the image when required
                        imagePanel.setUserImage(userImage);
                    } 
                    catch (InterruptedException ex) 
                    {
                        Logger.getLogger(DisplayUserImage.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                    catch (ExecutionException ex) 
                    {
                        Logger.getLogger(DisplayUserImage.class.getName()).log(Level.SEVERE, null, ex);
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
