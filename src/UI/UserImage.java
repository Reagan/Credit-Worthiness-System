/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class UserImage extends JPanel
{
    // set a label showing that image for 
    // a user is being loaded
    private JLabel loadingMessage = new JLabel();
    private String loadingText = "Loading..." ;
    private Color loadingLabelColor = Color.RED ;
    
    
    public UserImage()
    {
        // add details for the UserImage Panel
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(111, 103));
        setMaximumSize(new Dimension(111, 103));
        
        // specify details for the loading message
        loadingMessage.setBackground(loadingLabelColor);
        add(loadingMessage, BorderLayout.SOUTH) ;
    }
    
    public void setLoadingLabel(boolean placeLabelOrNot)
    {
        if(true == placeLabelOrNot)
        {
            loadingMessage.setText(loadingText);
            add(loadingMessage, BorderLayout.SOUTH) ;
        }
        else if(false == placeLabelOrNot)
        {
            loadingMessage.setText("");
            remove(loadingMessage);
        }
    }
    
    public void setUserImage(BufferedImage ic)
    {
        JLabel userImageLabel = new JLabel(new ImageIcon(ic)) ;
        userImageLabel.setPreferredSize(new Dimension(111, 103));
        userImageLabel.setMinimumSize(new Dimension(111, 103)) ;
        userImageLabel.setBackground(Color.red);
        
        add(userImageLabel, BorderLayout.CENTER) ;
    }
}
