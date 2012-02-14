package AppActions;

import UI.BottomCenterPanel;
import UI.BottomRightPanel;
import UI.Models.UserTransactionsModel;
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
 * This class is triggered when a user selects a node from the 
 * transactions chart. it programmatically selects the referenced to transaction
 * from the list of transactions in the bottonm center Jlist thus displaying
 * its details on the bottom right panel for editing
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
        
        // obtain the node for the selected transaction 
        transactionID = transID ;
    }

    @Override
    public void run() 
    {
        /**
        new UpdateTansactionDialog(new JFrame(), "Update Transaction", 
                "Dialog to update Transaction Details");
         **/
        // get the model for the Jlist with the 
        // List of transactions
        UserTransactionsModel currTransactions = BottomCenterPanel.getUserTransactionsModel();
        int transToSelect = -1; 
        
        // loop through the model to find one where the 
        // selected node is equal to the transaction ID
        for (int i = 0; i < currTransactions.getSize(); i++)
        {
            if(transactionID == currTransactions.transIDs[i])
            {
                // note the transaction of interest
                transToSelect = i ;
                break ;
            }
        }
        
        // select the transaction programmatically from the 
        // list of transactions
        if(-1 != transToSelect)
        {
            BottomCenterPanel.transactionsList.setSelectedIndex(transToSelect);
            BottomCenterPanel.transactionsList.ensureIndexIsVisible(transToSelect); 
        }
    }      
    
    /**
    // the inner class will display the
    // JFrame with the main components for the
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
    **/
}

