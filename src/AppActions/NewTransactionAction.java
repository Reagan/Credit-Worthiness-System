/**
 * Credit Worthiness System Version 1.0
 */
package AppActions;

import UI.BottomRightPanel;
import UI.NewTransactionPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class NewTransactionAction extends AbstractedAction
{
    // JOptionPane Options
    private String appMessage ;
    private String aboutDialogTitle ;
    private int messageType ;
    private static NewTansactionDialog nDialog ;
    
    // Main panel options
    
    
    public NewTransactionAction()
    {
        aboutDialogTitle = "Alert!";
        appMessage = "Please fill missing information?";
        messageType = JOptionPane.YES_NO_OPTION;
    }

    @Override
    public void run() 
    {
        nDialog  = new NewTansactionDialog("Create New Transaction");
    }      
    
    public static void closeFrame()
    {
        nDialog.dispose();
    }
    
    // the inner class will display the
    // JFrame with the main ciomponents for the
    // transaction details
    private class NewTansactionDialog extends JFrame 
    {
        public NewTansactionDialog(String title) 
        {
            super(title);

            Dimension parentSize = getSize(); 
            Point p = getLocation(); 
            setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);            

            NewTransactionPanel panel = new NewTransactionPanel(this);   
            
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
