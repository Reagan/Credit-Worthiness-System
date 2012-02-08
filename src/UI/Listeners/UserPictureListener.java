/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Listeners;

import ApplicationImages.DisplayUserImage;
import UI.NewUserPanel;
import UI.UserImage;
import credit.worthiness.system.CreditWorthinessSystem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

/**
 * This class uploads the picture information for a new user
 * It opens a file dialog and uploads the image
 * @author reagan
 */
public class UserPictureListener implements ActionListener
{
    private JFileChooser fileChooser ;
    private NewUserPanel newUserPanel ; 
    private UserImage userImage ;
    
    public UserPictureListener(JComponent nUserPanel, UserImage uImage)
    {
        newUserPanel = (NewUserPanel) nUserPanel ;
        userImage = uImage ;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        // initialise the file chooser
        fileChooser = new JFileChooser() ;
        fileChooser.addChoosableFileFilter(new ImageFilter());
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        // launch the dialog showing the images
        int retVal = fileChooser.showOpenDialog(newUserPanel) ;        
           
        // ensure that the returned file is a picture
        if(retVal == JFileChooser.APPROVE_OPTION)
        {
            try 
            {
                // get the selected file
                File picture = fileChooser.getSelectedFile() ;                
                
                // add the selected image to the 
                // userImage preview Pane
                
                String imageName = picture.getPath()  ;
                newUserPanel.userPicPath = picture.getPath() ;
                newUserPanel.userPicName = picture.getName() ;
                
                System.out.println("1. Selected Image: " + newUserPanel.userPicName ) ;
                
                BufferedImage ic ;
                
                DisplayUserImage d = new DisplayUserImage() ;
                ic = d.getUserImage(imageName, userImage) ;
            } 
            catch (IOException ex) 
            {
                System.out.println("Error: " + ex.toString());
            }
        }
    }
}