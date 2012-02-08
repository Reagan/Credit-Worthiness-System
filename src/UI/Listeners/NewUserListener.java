/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Listeners;

import ApplicationImages.ClientImages;
import DbConnection.UsersDetails;
import UI.LeftPanel;
import UI.NewUserPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * This class implements the New User Listener and enters details 
 * on a new user to the database
 * @author reagan
 */
public class NewUserListener implements ActionListener
{
    private NewUserPanel userPanel ; // stores the path to the picture name
    private boolean filesCopied ; // stores status on whether files were copied
    private String fileDestination = ""; // stores the destination file of the copied image    
    
    public NewUserListener(NewUserPanel uPanel)
    {
        userPanel = uPanel ;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        // if the user action is from the cancel 
        // window is from the cancel action, close the 
        // window
        if ("Cancel".equals(ae.getActionCommand()))
        {
            // close the new User dialog
            JDialog g = userPanel.parent ;
            g.setVisible(false);
            g.dispose();
            
        }
        else if("Save".equals(ae.getActionCommand()))
        {
            // get the entered details
            if( userPanel.nameTextField.getText().length() == 0 )
            // enter the details to the database
            {
                JOptionPane.showMessageDialog(null, "Kindly enter a name for customer",
                        "Enter Name", JOptionPane.PLAIN_MESSAGE);
                return ;
            }
            
            // check if an image has been entered
            if( null != userPanel.userPicName )
            {
                String userName = userPanel.nameTextField.getText() ;

                // copy the picture to the images folder
                fileDestination = ClientImages.getInstance().getImagesPath() + userPanel.userPicName ;

                try 
                {
                    filesCopied = ClientImages.getInstance().copyFiles(userPanel.userPicPath, fileDestination);
                } 
                catch (FileNotFoundException ex) 
                {
                    System.out.println("Error: " + ex.toString()) ;
                } 
                catch (IOException ex) 
                {
                    System.out.println("Error: " + ex.toString()) ;
                }    
            }
                        
            // add the information to the database
            if(true == filesCopied)
            {
                // copy images path to database                
                UsersDetails user = new UsersDetails();
                boolean result = false;
                
                try 
                {
                    result = user.setNewUserDetails(userPanel.nameTextField.getText()
                    , userPanel.userPicName);
                } 
                catch (SQLException ex) 
                {
                    System.out.println("Error: " + ex.toString()) ;
                }
                
                if(true == result)
                {
                    JOptionPane.showMessageDialog(null, "New User Details entered successfully"
                            , "Details entered", JOptionPane.PLAIN_MESSAGE);
                    
                    // close the new User dialog
                    JDialog g = userPanel.parent ;
                    g.setVisible(false);
                    g.dispose();
                    
                    // update the model for the drop
                    // down user select menu for user selection
                    LeftPanel.fetchUserNames();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "There was an error adding the new user details! Please try again."
                            , "Error in details entered", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }            
    }       
}