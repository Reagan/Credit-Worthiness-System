/**
 * Credit Worthiness System Version 1.0
 */
package AppActions;

import UI.BottomRightPanel;
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
        new NewTansactionDialog(new JFrame(), "Create New Transaction", 
                "Dialog to create New Transaction Detils");
    }      
    
    
    // the inner class will display the
    // JFrame with the main ciomponents for the
    // transaction details
    class NewTansactionDialog extends JDialog implements ActionListener 
    {
        public NewTansactionDialog(JFrame parent, String title, String message) 
        {
            super(parent, title, true);
            
            if (parent != null) 
            {
                Dimension parentSize = parent.getSize(); 
                Point p = parent.getLocation(); 
                setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
            }

            BottomRightPanel panel = new BottomRightPanel();                        
            getContentPane().add(panel);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            pack(); 
            setVisible(true);
        }
  
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            setVisible(false); 
            dispose(); 
        }
    }
}