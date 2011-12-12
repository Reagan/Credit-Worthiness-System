/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import AppActions.AppAction;
import AppActions.DeleteUserAction;
import ClientImages.DisplayUserImage;
import UIListeners.UsersSelectionListener;
import UIModels.UsersModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class LeftPanel extends JPanel
{
    String labels[] = {"Angie Muthoni", "Reagan Mbitiru"};
    private JComboBox userSelectCombo ;
    private static UserImage userImage ;
    private DepthButton deleteUserButton ;
    private AppAction deleteUserAction ;
    private static JLabel userName ;
    private static JLabel joinedDate ;
    
    // add the model and listeners for the JComboBox
    private UsersModel users ;
    private UsersSelectionListener usersSelectionListener ;
       
            
    public LeftPanel()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // initialise the components
        users  = new UsersModel();
        usersSelectionListener = new UsersSelectionListener() ;
        
        // create the JComboBox for selecting users
        userSelectCombo = new JComboBox();
        
        // add the model & listener for the JComboBox
        userSelectCombo.setModel(users);
        userSelectCombo.addActionListener(usersSelectionListener);
        
        // ensure that only data in the model 
        // is available for display
        userSelectCombo.setEditable(false);
        
        userSelectCombo.setPreferredSize(new Dimension(150, 27));
        userSelectCombo.setMaximumSize(new Dimension(150, 27));
        
        
        userImage = new UserImage();        
        deleteUserAction = new AppAction(deleteUserButton, "Delete User", 
                                false, KeyEvent.VK_D);
        deleteUserAction.addActionClass(new DeleteUserAction());
        deleteUserButton = new DepthButton(deleteUserAction);
        
        userName = new JLabel(" ");
        userName.setFont(new Font("Serif", Font.PLAIN, 18));
        
        joinedDate = new JLabel(" ");
        joinedDate.setFont(new Font("Serif", Font.PLAIN, 11));
        
        // lay out the components        
        add(Box.createRigidArea(new Dimension(0,35)));
        
        userSelectCombo.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(userSelectCombo);
        
        add(Box.createRigidArea(new Dimension(0,35)));
        
        userImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(userImage);
        
        add(Box.createRigidArea(new Dimension(0, 12)));
        
        userName.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(userName);
        
        joinedDate.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(joinedDate);
        
        add(Box.createRigidArea(new Dimension(0, 29)));
        
        deleteUserButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(deleteUserButton);
        add(Box.createHorizontalGlue());
        
        // finalise and display the panel
        setOpaque(false);
        setPreferredSize(new Dimension(210, 312));
    }
    
    /**
     * This method sets the user name for the user
     * and the joining date for that user
     * @param userName 
     */
    public static void setUserDetails(String newUserName, int userID)
    {
        userName.setText(newUserName);
        
        // @TODO: this value should be obtained from the 
        // a database query or from a cached object
        joinedDate.setText(newUserName);
    }
    
    /**
     * This method obtains the userImage and sets the 
     * path for the image that is displayed on the left of 
     * the screen
     * @param imagePath 
     */
    public static void setUserImage(String imageName) throws IOException
    {
        // stores the instance of the 
        // image fetched 
        BufferedImage ic ;
        
        DisplayUserImage d = new DisplayUserImage() ;
        ic = d.getUserImage(imageName, userImage) ;                
    }
    
    /**
     * displays or hides the JLabel showing that 
     * the user image is loading
     * 
     * @param stateOfLabel 
     */
    
    public static void showLoadingLabel(boolean stateOfLabel)
    {
        userImage.setLoadingLabel(stateOfLabel);
    }
}