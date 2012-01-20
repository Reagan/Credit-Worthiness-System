/**
 * Credit Worthiness System Version 1.0
 */
package UI.Listeners;

import DbConnection.TransactionDetails;
import UI.NewTransactionPanel;
import UI.NewUserPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class NewTransactionListener implements ActionListener
{
    private NewTransactionPanel newTransPanel ;
    
    public NewTransactionListener(NewTransactionPanel nPanel)
    {
        newTransPanel = nPanel ;
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        if ("Cancel".equals(ae.getActionCommand()))
        {
            // close the new User dialog
            JDialog g = newTransPanel.parent ;
            g.setVisible(false);
            g.dispose();
            
        }
        else if("Save".equals(ae.getActionCommand()))
        {
            // check if all the required transaction details
            // are entered
            if( newTransPanel.date.getText().length() == 0 
                    || NewTransactionPanel.numberOfItems.getText().length() == 0
                    || NewTransactionPanel.items.getSelectedIndex() == -1 
                    || NewTransactionPanel.transactionNotes.getText().length() == 0 )
            {
                JOptionPane.showMessageDialog(newTransPanel, "Please insert all transaction details"
                        , "Transaction Details Error", JOptionPane.INFORMATION_MESSAGE); 
                return ;
            }
            else
            {
                // insert the data for the transaction into
                // the database
                String [] date ;
                int noOfItems ;
                int selectedItem ;
                String transactionNotes = null ;
                int dateTokensCounter = 0 ;
                
                // split the date 
                StringTokenizer tokens = new StringTokenizer(newTransPanel.date.getText().trim()
                        , "/");
                
                // initialise the date variable
                date = new String[tokens.countTokens()];
                
                // populate the date variable
                while( tokens.hasMoreTokens() )
                {
                    date[dateTokensCounter] = tokens.nextToken() ;
                }
                
                // get the number of items
                noOfItems = Integer.parseInt(newTransPanel.numberOfItems.getText().trim()) ;
                
                // get the selected item
                selectedItem = newTransPanel.items.getSelectedIndex() ;
                
                // get the transaction notes
                String notes = newTransPanel.transactionNotes.getText() ;
                
                // populate the database with details of the new transaction
                TransactionDetails tDetails = new TransactionDetails();
                
                if( tDetails.insertTransactionDetails(date, noOfItems, selectedItem, transactionNotes) )
                {
                    // alert the user of the success in updating the message
                    JOptionPane.showMessageDialog(newTransPanel, "Transaction details "
                        + "entered successfully", "Success", JOptionPane.INFORMATION_MESSAGE); 
                }
                else
                {
                    // alert the user of the success in updating the message
                    JOptionPane.showMessageDialog(newTransPanel, "There was an error inserting"
                            + " new transaction details into the database", 
                            "Error!", JOptionPane.ERROR_MESSAGE);
                }
            }
            
            // alert the user of the success in updating the message
            JOptionPane.showMessageDialog(newTransPanel, "Transaction details "
                    + "entered successfully", "Success", JOptionPane.INFORMATION_MESSAGE); 
            
            // close the dialog
            JDialog g = newTransPanel.parent ;
            g.setVisible(false);
            g.dispose();
        }
    }
    
}
