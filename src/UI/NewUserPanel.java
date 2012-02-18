/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import DbConnection.UsersDetails;
import UI.Listeners.NewUserListener;
import UI.Listeners.UserPictureListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;
import javax.swing.*;

/**
 * This class loads the user details for a specific user for editing 
 * or presents an interface to enter details for a new user
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class NewUserPanel extends JPanel
{
    private JPanel topPanel ; // contains the text fields
    private JPanel lowerPanel ; // has the buttons
    
    // create the labels
    private JLabel firstNameLabel ;
    private JLabel lastNameLabel ;
    private JLabel contactLabel ; 
    private JLabel joiningDateLabel ;
    private JLabel creditLimitLabel ;
    private JLabel addressLabel ;
    private JLabel uploadPictureLabel ;
    
    // create the input fields
    private JTextField firstNameTextField ;
    private JTextField lastNameTextField ;
    private JTextField contactNumberTextField ;
    private JTextField creditLimitTextField ;
    private JTextField joiningDateTextField ;
    private JTextArea addressTextArea ; 
    private JScrollPane addressScrollPane ;
    private UserImage pictureArea ;
    
    private DepthButton browseButton ;
    private DepthButton saveButton ;
    private DepthButton cancelButton ;        
    
    private UserPictureListener userPicListener ;
    private NewUserListener newUserListener ;
    
    public String userPicPath = null ; // stores the URL to the picture 
    public String userPicName = null ; // stores the name to the picture
    
    public JFrame parent ; // stores the containing JDialog
    
    private int currUserID = -1 ; // stores the user ID for the currently selected user
    
    private UsersDetails userDetails = null ; // stores the user details settings 
                                        // for a specific user
    private String[] currUserSettings = {} ;
    
    // set the various fields to be returned as user settings details
    private static final int CUSTOMERS_ID = 0 ;
    private static final int CUSTOMERS_FIRSTNAME = 1 ;
    private static final int CUSTOMERS_LASTNAME = 2 ;
    private static final int CUSTOMERS_ADDRESS = 3 ;
    private static final int IMAGES_NAME = 4 ;
    private static final int CUSTOMERS_CONTACT_NUMBER = 5 ;
    private static final int CREDIT_ALLOWED = 6 ;
    private static final int JOINING_DAY = 7 ;
    private static final int JOINING_MONTH = 8 ;
    private static final int JOINING_YEAR = 9 ;
    
    public NewUserPanel(JFrame dialog, int userID)
    {
        // initialise the user ID
        currUserID = userID ;
        
        // initialise the userDetails object
        userDetails = new UsersDetails() ;   
        
        if ( -1 != currUserID )
            currUserSettings = userDetails.getUserSettingsDetails(userID) ;
        
        // initialise the variables
        topPanel = new JPanel();
        lowerPanel = new JPanel();
        parent = dialog ;
    
        // initialise the labels
        firstNameLabel = new JLabel("First Name") ;
        lastNameLabel = new JLabel("Last Name") ;
        contactLabel = new JLabel("Contact Number") ; 
        joiningDateLabel = new JLabel("Joining Date") ;
        creditLimitLabel = new JLabel("Credit Limit") ;
        addressLabel = new JLabel("Address") ;
        uploadPictureLabel = new JLabel("Upload Picture"); 
        
        // initialise the input fields
        firstNameTextField =  new JTextField(loadUserSetting(CUSTOMERS_FIRSTNAME));
        lastNameTextField = new JTextField(loadUserSetting(CUSTOMERS_LASTNAME)) ;
        contactNumberTextField = new JTextField(loadUserSetting(CUSTOMERS_CONTACT_NUMBER)) ;
        creditLimitTextField = new JTextField(loadUserSetting(CREDIT_ALLOWED)) ;
        
        if ( -1 == currUserID )
        {
            joiningDateTextField = new JTextField() ;
        }
        else
        {
            joiningDateTextField = new JTextField(loadUserSetting(JOINING_DAY) + "/" + 
               loadUserSetting(JOINING_MONTH) + "/" + loadUserSetting(JOINING_YEAR)) ;
        }
        
        addressTextArea = new JTextArea(loadUserSetting(CUSTOMERS_ADDRESS)); 
        addressScrollPane = new JScrollPane(addressTextArea) ;
        pictureArea = new UserImage();
        
        // format the text area & scroll Pane
        addressTextArea.setLineWrap(true);
        addressTextArea.setWrapStyleWord(true);
        addressTextArea.setPreferredSize(new Dimension(150, 0));                                           
        
        // set the browse button to the action listener
        browseButton = new DepthButton("Browse");
        userPicListener = new UserPictureListener(NewUserPanel.this, pictureArea) ;
        browseButton.addActionListener(userPicListener);
        
        // add the sava and the cancel buttons
        saveButton = new DepthButton("Save");
        cancelButton = new DepthButton("Cancel");
        
        // create the listeners and add them
        newUserListener = new NewUserListener(NewUserPanel.this) ;
        saveButton.addActionListener(newUserListener);
        cancelButton.addActionListener(newUserListener);     
        
        //finalise and display panel 
        setLayout(new BorderLayout());
        setOpaque(false);        
        
        layoutComponents();
    }
    
    /**
     * This method obtains the user ID from the populated hashMap
     * with the details for the specific user
     * @param desiredUserSetting
     * @param userID
     * @return 
     */
    private String loadUserSetting(int desiredUserSetting)
    {
        String userSettingValue = "" ;
                
        if ( -1 == currUserID && null != currUserSettings )
            return null ;
        
        // the currUserSettings are stored in the sequence
        // 0 ->  customers_id
        // 1 -> customers_firstname
        // 2 -> customers_secondname
        // 3 -> customers_address
        // 4 -> images_name
        // 5 -> customers_contact_number
        // 6 -> credit_allowed 
        // 7 -> joining_day
        // 8 -> joining_month 
        // 9 -> year
        
        if ( 0 < currUserSettings.length )
        {
            userSettingValue = currUserSettings[desiredUserSetting] ;
        }
        
        return userSettingValue ;        
    }
    
    private void layoutComponents()
    {
        // sort out the top panel
        topPanel.setPreferredSize(new Dimension(550, 324));
        topPanel.setMaximumSize(new Dimension(550, 324));
        
        // add the correct titled border depending on the preffered action
        if ( -1 == currUserID )
            topPanel.setBorder(BorderFactory.createTitledBorder("Enter New User Details"));
        else
            topPanel.setBorder(BorderFactory.createTitledBorder("Edit User Details"));
        
        // lay out the components in the top layout
        GroupLayout layout = new GroupLayout(topPanel);
        topPanel.setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);       
        
        // lay out the components horizontally
        layout.setHorizontalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(firstNameLabel)
                                .addComponent(firstNameTextField)
                                )
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(contactLabel)
                                .addComponent(contactNumberTextField)
                                )
                            .addGroup(layout.createSequentialGroup()
                                    .addComponent(joiningDateLabel)
                                    .addComponent(joiningDateTextField)
                                )
                            .addComponent(addressLabel)
                            .addComponent(addressScrollPane)
                    )
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(lastNameLabel)
                                .addComponent(lastNameTextField)
                            )
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(creditLimitLabel)
                                .addComponent(creditLimitTextField)
                            )
                        .addComponent(uploadPictureLabel)
                        .addComponent(pictureArea)
                        .addComponent(browseButton)
                    )
         );
        
        // lay out the components vertically
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                    .addComponent(firstNameLabel)
                    .addComponent(firstNameTextField)
                    .addComponent(lastNameLabel)
                    .addComponent(lastNameTextField)
                    )
                .addGroup(layout.createParallelGroup()
                    .addComponent(contactLabel)
                    .addComponent(contactNumberTextField)
                    .addComponent(creditLimitLabel)
                    .addComponent(creditLimitTextField)
                    )
                .addGroup(layout.createParallelGroup()
                    .addComponent(joiningDateLabel)
                    .addComponent(joiningDateTextField)
                    .addComponent(uploadPictureLabel)
                    )
                .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(addressLabel)
                            .addComponent(addressScrollPane)
                            )
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(pictureArea)
                                .addComponent(browseButton)
                            )
                    )
        );
        
        // sort out the lower panel
        lowerPanel.setPreferredSize(new Dimension(550, 40));
        lowerPanel.setMaximumSize(new Dimension(550, 40));
        lowerPanel.setLayout(new FlowLayout(SwingConstants.RIGHT, 10, 10));
        lowerPanel.add(saveButton);
        lowerPanel.add(cancelButton);
        
        // add everything and finalise the components display
        add(topPanel, BorderLayout.CENTER);
        add(lowerPanel, BorderLayout.SOUTH);
    }    
}
