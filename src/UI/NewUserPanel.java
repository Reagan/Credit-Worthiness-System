/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import UI.Listeners.NewUserListener;
import UI.Listeners.UserPictureListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class NewUserPanel extends JPanel
{
    private JPanel topPanel ;
    private JPanel lowerPanel ;
    
    private JLabel nameLabel ;
    public JTextField nameTextField ;
    private JLabel uploadPictureLabel ;
    private UserImage pictureArea ;
    
    private DepthButton browseButton ;
    private DepthButton saveButton ;
    private DepthButton cancelButton ;
    
    private JSeparator separator;
    private UserPictureListener userPicListener ;
    private NewUserListener newUserListener ;
    
    public String userPicPath = null ; // stores the URL to the picture 
    public String userPicName = null ; // stores the name to the picture
    
    public JFrame parent ; // stores the containing JDialog
    
    public NewUserPanel(JFrame dialog)
    {
        // initialise the variables
        topPanel = new JPanel();
        lowerPanel = new JPanel();
        parent = dialog ;
    
        nameLabel = new JLabel("Name");
        nameTextField = new JTextField();
        nameTextField.setPreferredSize(new Dimension(140, 20));
        nameTextField.setMinimumSize(new Dimension(140, 20));
        nameTextField.setMaximumSize(new Dimension(140, 20));
        
        uploadPictureLabel = new JLabel("Upload Picture");        
        pictureArea = new UserImage();    
        browseButton = new DepthButton("Browse");
        
        // set the browse button to the action listener
        userPicListener = new UserPictureListener(NewUserPanel.this, pictureArea) ;
        browseButton.addActionListener(userPicListener);
        
        // add the sava and the cancel buttons
        saveButton = new DepthButton("Save");
        cancelButton = new DepthButton("Cancel");
        
        // create the listeners and add them
        newUserListener = new NewUserListener(NewUserPanel.this) ;
        saveButton.addActionListener(newUserListener);
        cancelButton.addActionListener(newUserListener);
        
        // add a separator
        separator = new JSeparator(SwingConstants.VERTICAL);
        
        //finalise and display panel 
        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(330, 280)); 
        
        layoutComponents();
    }
    
    private void layoutComponents()
    {
        // sort out the top panel
        topPanel.setPreferredSize(new Dimension(327, 224));
        topPanel.setMaximumSize(new Dimension(327, 224));
        topPanel.setBorder(BorderFactory.createTitledBorder("Enter New User Details"));
        
        // lay out the components in the top layout
        GroupLayout layout = new GroupLayout(topPanel);
        topPanel.setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        
        // lay out the components horizontally
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                    .addComponent(nameLabel)
                    .addComponent(nameTextField))
                .addComponent(separator)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(uploadPictureLabel)
                    .addComponent(pictureArea)
                    .addComponent(browseButton)));
        
        // lay out the components vertically
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameLabel)
                        .addComponent(nameTextField))
                    .addComponent(separator)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(uploadPictureLabel)
                        .addComponent(pictureArea)))
                .addComponent(browseButton));
                
        // sort out the lower panel
        lowerPanel.setPreferredSize(new Dimension(388, 40));
        lowerPanel.setMaximumSize(new Dimension(388, 40));
        lowerPanel.setLayout(new FlowLayout(SwingConstants.RIGHT, 10, 10));
        lowerPanel.add(saveButton);
        lowerPanel.add(cancelButton);
        
        // add everything and finalise the components display
        add(topPanel, BorderLayout.CENTER);
        add(lowerPanel, BorderLayout.SOUTH);
    }    
}
