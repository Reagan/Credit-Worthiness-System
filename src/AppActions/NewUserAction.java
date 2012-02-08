/**
 * Credit Worthiness System Version 1.0
 */
package AppActions;

import UI.ApplicationIcon;
import UI.NewUserPanel;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class NewUserAction extends AbstractedAction
{
    // JOptionPane Options
    private String appMessage ;
    private String aboutDialogTitle ;
    private int messageType ;
    
    // Main panel options
    
    
    public NewUserAction()
    {
        aboutDialogTitle = "Alert!";
        appMessage = "Please fill missing information?";
        messageType = JOptionPane.YES_NO_OPTION;
    }

    @Override
    public void run() 
    {
        new NewUserDialog(new JFrame(), "Create New User");
    }   
    
    private class NewUserDialog extends JDialog
    {
        public NewUserDialog(JFrame parent, String title) 
        {
            super(parent, title, true);
            
            if (parent != null) 
            {
                Dimension parentSize = parent.getSize(); 
                Point p = parent.getLocation(); 
                setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
                ApplicationIcon.getInstance().setApplicationIcon(parent);
            }

            NewUserPanel panel = new NewUserPanel(this);                        
            getContentPane().add(panel);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            pack(); 
            setVisible(true);
        }        
        
        public void closeDialog()
        {
            setVisible(false) ;
            dispose();
        }
    }   
}
