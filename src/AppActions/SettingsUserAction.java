/**
 * Credit Worthiness System Version 1.0
 */
package AppActions;

import UI.ApplicationIcon;
import UI.SettingsPanel;
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
 * This class allows a user to adjust the settings to a class
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class SettingsUserAction extends AbstractedAction 
{
   private String settingsDialogTitle ;
   private String appMessage  ;
   private int messageType ;
   private SettingsDialog nDialog ;
        
    public SettingsUserAction() 
    {
        settingsDialogTitle = "Change Settings";
        appMessage = "Some changes were made. Do you want to save these settings?";
        messageType = JOptionPane.YES_NO_OPTION;
    }
    
     @Override
    public void run() 
    {
        nDialog  = new SettingsDialog(settingsDialogTitle);
    }      
    
    public void closeFrame()
    {
        nDialog.closeDialog();
    }
    
    // the inner class will display the
    // JFrame with the main ciomponents for the
    // transaction details
    public class SettingsDialog extends JFrame 
    {
        public SettingsDialog(String title) 
        {
            super(title);

            Dimension parentSize = getSize(); 
            Point p = getLocation(); 
            setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4); 
            ApplicationIcon.getInstance().setApplicationIcon(SettingsDialog.this);

            SettingsPanel panel = new SettingsPanel(this);   
            
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
