package AppActions;

import UI.BottomRightPanel;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 * Credit Worthiness System Version 1.0
 */
/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class UpdateTransactionAction extends AbstractedAction
{
    // JOptionPane Options
    private String appMessage ;
    private String aboutDialogTitle ;
    private int messageType ;
    private int transactionID ;    
    
    public UpdateTransactionAction(int transID)
    {
        aboutDialogTitle = "Alert!";
        appMessage = "Please fill missing information?";
        messageType = JOptionPane.YES_NO_OPTION;
        transactionID = transID ;
    }

    @Override
    public void run() 
    {
        new UpdateTansactionDialog(new JFrame(), "Update Transaction", 
                "Dialog to update Transaction Details");
    }      
    
    
    // the inner class will display the
    // JFrame with the main ciomponents for the
    // transaction details
    class UpdateTansactionDialog extends JDialog implements ActionListener 
    {
        public UpdateTansactionDialog(JFrame parent, String title, String message) 
        {
            super(parent, title, true);
            
            if (parent != null) 
            {
                Dimension parentSize = parent.getSize(); 
                Point p = parent.getLocation(); 
                setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
            }

            BottomRightPanel panel = new BottomRightPanel();   
           // BottomRightPanel.setTransactionDetails(transactionID) ;
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

