/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import AppActions.AppAction;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
    private UserImage userImage ;
    private DepthButton deleteUserButton ;
    private AppAction deleteUserAction ;
    private JLabel userName ;
    private JLabel joinedDate ;
    
    public LeftPanel()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // initialise the components
        userSelectCombo = new JComboBox(labels);
        userSelectCombo.setPreferredSize(new Dimension(150, 27));
        userSelectCombo.setMaximumSize(new Dimension(150, 27));
        
        
        userImage = new UserImage();        
        deleteUserAction = new AppAction(deleteUserButton, "Delete User", 
                                true, KeyEvent.VK_D);
        deleteUserButton = new DepthButton(deleteUserAction);
        
        userName = new JLabel("Angie Muthoni");
        joinedDate = new JLabel("Joined: 12th Jan, 2011");
        
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
}
