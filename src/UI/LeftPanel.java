/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import AppActions.AppAction;
import AppActions.DeleteUserAction;
import AppActions.EditCurrUserAction;
import ClientImages.DisplayUserImage;
import DbConnection.UsersDetails;
import UI.Listeners.UsersSelectionListener;
import UI.Models.UsersModel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class LeftPanel extends JPanel
{    
    private static JComboBox userSelectCombo ;
    private static UserImage userImage ;
    private DepthButton deleteUserButton ;
    private DepthButton editCurrUserButton ;
    public static AppAction deleteUserAction ;
    public static AppAction editCurrUserAction ;
    private static JLabel userName ;
    private static JLabel joinedDate ;
    
    // add the model and listeners for the JComboBox
    private static Vector<String> usersNames;
    private static UsersModel users ;
    private UsersSelectionListener usersSelectionListener ;
    
    private static int currSelectedUserIndex = 0; // indicates the selected users
                                            // index in the JComboBox
    public static final String DEFAULTUSERIMAGE = "1.gif" ;           
            
    public LeftPanel()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));        
        
        // retireve the list of names, ID#s and for 
        // joining date for all the users 
        // as stored in the database
        usersNames = new Vector<String>() ;
        
        if(SwingUtilities.isEventDispatchThread())
        {
            // Inform the progress bar of retrieval of 
            // user's names
            StatusBar.updateStatusMessage("Fetching names...");
            
            // get the names of the users from the database
            // and add them to the model of the JComboBox
            // and then add this model to the JComboBox
            fetchUserNames();                        
        }
        
        // initialise the Selection Listener        
        usersSelectionListener = new UsersSelectionListener() ;
        
        // create the JComboBox for selecting users
        userSelectCombo = new JComboBox();            
        
        // add the listener for the JComboBox        
        userSelectCombo.addActionListener(usersSelectionListener);
        
        // ensure that only data in the model 
        // is available for display
        userSelectCombo.setEditable(false);
        
        // set the preferred dimensions
        userSelectCombo.setPreferredSize(new Dimension(150, 27));
        userSelectCombo.setMaximumSize(new Dimension(150, 27));        
        
        userImage = new UserImage();   
        
        // create the panel with the buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.setPreferredSize(new Dimension(150, 30));
        buttonsPanel.setOpaque(false) ;
        
        editCurrUserAction = new AppAction(editCurrUserButton, "Edit", 
                                false, KeyEvent.VK_E);
        editCurrUserAction.addActionClass(new EditCurrUserAction());
        editCurrUserButton = new DepthButton(editCurrUserAction);
        buttonsPanel.add(editCurrUserButton) ;
        
        deleteUserAction = new AppAction(deleteUserButton, "Delete", 
                                false, KeyEvent.VK_D);
        deleteUserAction.addActionClass(new DeleteUserAction());
        deleteUserButton = new DepthButton(deleteUserAction);
        buttonsPanel.add(deleteUserButton);
        
        userName = new JLabel(" ");
        userName.setFont(new Font("sanserif", Font.PLAIN, 18));
        
        joinedDate = new JLabel(" ");
        joinedDate.setFont(new Font("sanserif", Font.PLAIN, 11));
        
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
        
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(buttonsPanel);
        add(Box.createHorizontalGlue());
        
        // finalise and display the panel
        setOpaque(false);
        setPreferredSize(new Dimension(210, 312));
    }
    
    /**
     * This method get the user's ID, name and 
     * Joining Date and sets the user name for the user
     * and the joining date for that user in the JLabel
     * @param userName 
     */
    public static void setUserDetails(String[] userDetails) throws IOException 
                                                // userDetais stores the userID(0),
                                                //  userName(1), joiningDate (2),
                                                // image_path(3)
    {
        // set the userName
        userName.setText(userDetails[1]);
                
        // get the joining Date for the user        
        joinedDate.setText("Joined: " + userDetails[2]);
        
        // set the image for the user
        setUserImage(userDetails[3]);                                    
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

    public static void fetchUserNames() 
    {
        // create a swing worker thread to 
        // fetch the names of the users in the database
        // while updating the status bar
        //
        // get the names of the users from the database
        // and add them to the model of the JComboBox
        // and then add this model to the JComboBox
        
        SwingWorker worker = new SwingWorker<Vector, Void> ()
        {            
            @Override
            protected Vector doInBackground() 
            {
                UsersDetails users = new UsersDetails();
                return users.getUsersNames() ;
            }
            
            @Override
            public void done()
            {
                // inform the status bar that the
                // required users informatio has been obtained
                StatusBar.updateStatusMessage("Users' Names obtained");
                
                try 
                {
                    // set the obtained values as the 
                    // usersNames
                    usersNames = get() ;
                    
                    // add the names obtained to the model
                    users  = new UsersModel(usersNames);
                    
                    // add the model to the JComboBox
                    userSelectCombo.setModel(users);
                }
                catch (InterruptedException ie){}
                catch (ExecutionException e)
                {
                    Throwable cause = e.getCause() ;
                    
                    if(null != cause)
                    {
                        StatusBar.updateStatusMessage("Error: " + cause);
                    }
                }                                
            }        
        };
        
        // schedule the thread
        worker.execute();
    }
    
    public static void setCurrUserSelectedIndex(int index)
    {
        currSelectedUserIndex =  index ;
    }
    
    public static int getCurrUserSelectedIndex()
    {
        return currSelectedUserIndex ;
    }
    
    /**
     * This method automatically triggers the 
     * JComboBox and sets another user
     * @param index
     * @return 
     */
    public static void setSelectedIndex(int index)
    {
        userSelectCombo.setSelectedIndex(index);                
    }
}