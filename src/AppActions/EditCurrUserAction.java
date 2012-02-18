/**
 * Credit Worthiness System Version 1.0
 */
package AppActions;

import UI.ApplicationIcon;
import UI.NewUserPanel;
import credit.worthiness.system.CreditWorthinessSystem;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class EditCurrUserAction extends AbstractedAction
{
    // JOptionPane Options
    private String appMessage ;
    private String aboutDialogTitle ;
    private int messageType ;
    
    // Main panel options
    
    
    public EditCurrUserAction()
    {
        aboutDialogTitle = "Alert!";
        appMessage = "Please fill missing information?";
        messageType = JOptionPane.YES_NO_OPTION;
    }

    @Override
    public void run() 
    {
        new EditCurrUserDialog("Edit Current User");
    }   
    
    private class EditCurrUserDialog extends JFrame
    {
        public EditCurrUserDialog(String title) 
        {
            super(title);
            
            Dimension parentSize = getSize(); 
            Point p = getLocation(); 
            setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
            ApplicationIcon.getInstance().setApplicationIcon(this);            

            NewUserPanel panel = new NewUserPanel(this, CreditWorthinessSystem.getCurrentUserID());                        
            getContentPane().add(panel);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            pack(); 
            setVisible(true);
            
             // allow window to be closed by pressing ESC
            Action escListener = new AbstractAction() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            } ;
            
            getRootPane().registerKeyboardAction(escListener,
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW);
        }        
        
        public void closeDialog()
        {
            setVisible(false) ;
            dispose();
        }
    }   
}
