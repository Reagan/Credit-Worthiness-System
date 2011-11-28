/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
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
    private JTextField nameTextField ;
    private JLabel uploadPictureLabel ;
    private JPanel pictureArea ;
    
    private DepthButton browseButton ;
    private DepthButton saveButton ;
    private DepthButton cancelButton ;
    
    private JSeparator separator;
    
    public NewUserPanel()
    {
        // initialise the variables
        topPanel = new JPanel();
        lowerPanel = new JPanel();
    
        nameLabel = new JLabel("Name");
        nameTextField = new JTextField();
        nameTextField.setPreferredSize(new Dimension(140, 20));
        nameTextField.setMinimumSize(new Dimension(140, 20));
        nameTextField.setMaximumSize(new Dimension(140, 20));
        
        uploadPictureLabel = new JLabel("Upload Picture");
        pictureArea = new JPanel();
        pictureArea.setPreferredSize(new Dimension(111, 103));
        pictureArea.setMaximumSize(new Dimension(111, 103));
        pictureArea.setMinimumSize(new Dimension(111, 103));
        pictureArea.setBackground(Color.red);
    
        browseButton = new DepthButton("Browse");
        saveButton = new DepthButton("Save");
        cancelButton = new DepthButton("Cancel");
        
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
                .addGroup(layout.createParallelGroup()
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
