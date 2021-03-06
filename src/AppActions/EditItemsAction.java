/**
 * Credit Worthiness System Version 1.0
 */
package AppActions;

import UI.ApplicationIcon;
import UI.EditItemsPanel;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class EditItemsAction extends AbstractedAction
{
    // JOptionPane Options
    private String appMessage ;
    private String aboutDialogTitle ;
    private int messageType ;
    private static EditItemsAction.EditItemsDialog nDialog ;
    
    // Main panel options
    
    
    public EditItemsAction()
    {
        aboutDialogTitle = "Alert!";
        appMessage = "Please fill missing information?";
        messageType = JOptionPane.YES_NO_OPTION;
    }

    @Override
    public void run() 
    {
        nDialog  = new EditItemsAction.EditItemsDialog("Edit Items");
    }      
    
    public static void closeFrame()
    {
        nDialog.dispose();
    }
    
    // the inner class will display the
    // JFrame with the main ciomponents for the
    // transaction details
    public class EditItemsDialog extends JFrame 
    {
        public EditItemsDialog(String title) 
        {
            super(title);

            Dimension parentSize = getSize(); 
            Point p = getLocation(); 
            setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4); 
            ApplicationIcon.getInstance().setApplicationIcon(EditItemsAction.EditItemsDialog.this);

            EditItemsPanel panel = new EditItemsPanel(this);   
            
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
