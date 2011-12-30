/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AppActions;

import DbConnection.TransactionDetails;
import UI.BottomCenterPanel;
import UI.BottomRightPanel;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author reagan
 */
public class UpdateTransactionDetailsAction extends AbstractedAction
{
    // JOptionPane Options
    private String appMessage ;
    private String aboutDialogTitle ;
    private int messageType ;
    private int transactionID ; 
    private BottomRightPanel transPanel ;

    public UpdateTransactionDetailsAction()
    {
        aboutDialogTitle = "Alert!";
        appMessage = "Please fill missing information?";
        messageType = JOptionPane.YES_NO_OPTION;
        //transactionID = transID ;
    }

    @Override
    public void run() 
    {            
        // check if any changes made
        if(true == transPanel.dirty)
        {System.out.println("Transaction Panel Dirty") ;
            // get the transaction type
            int transType = BottomRightPanel.transactionType ;
            
            // get the current transaction ID
            int transID = BottomRightPanel.currTransactionID ;
            System.out.println("TransID: " + transID + " TransType: " + transType);
            // get the date
            String date = BottomRightPanel.date.getText() ;
            System.out.println("Date: " + date) ;
            // get the number of items
            int itemsNo = Integer.parseInt(BottomRightPanel.numberOfItems.getText()) ;
            System.out.println("Items #: " + itemsNo) ;
            // get the notes
            String notes = BottomRightPanel.transactionNotes.getText() ;
            System.out.println("Notes: " + notes) ;
            // get selected item
            // get the selected index
            int selectedIndexItem = BottomRightPanel.items.getSelectedIndex() ;
            System.out.println("Selected Index Item: " + selectedIndexItem) ;
            // get the selected item
            String selectedItem = (String) BottomRightPanel.itemsObt.get(selectedIndexItem) ;
            System.out.println("Selected Item: " + selectedItem) ;
            // insert the updated information to the database
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy") ;
            Date d = null;
            try 
            {
                d = df.parse(date);
            } 
            catch (ParseException ex) 
            {
                System.out.println("Error: " + ex.toString()) ;
            }
            
            Calendar cal = Calendar.getInstance() ;
            cal.setTime(d);                        
            
            TransactionDetails tDetails = new TransactionDetails();
            boolean result = false ;
            
            try 
            {
                result = tDetails.updateTransactionDetails(transType, transID,
                itemsNo, notes, selectedItem, cal.get(Calendar.DATE), 
                cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR));
            } 
            catch (SQLException ex) 
            {
                System.out.println("Error: " + ex.toString()) ;
            }
            
            if( false==result )
            {
                JOptionPane.showMessageDialog(null, "There was an error updating the "
                        + "transaction details. Please try again", "Alert", 
                        JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Saved details for transaction" 
                + transPanel.currTransactionID
                + " successfully",
                "Editing details", JOptionPane.PLAIN_MESSAGE);
                
                // update the list of transactions to reflect the change
                // in that transaction
                BottomCenterPanel.setUserTransactionsModel();
            }
            // set the dirty tag to false
            transPanel.dirty = false ;
        }
        else
        {
            JOptionPane.showMessageDialog(null, "You have not made any changes "
                    + "for this transaction # [" 
                    + transPanel.currTransactionID
                    + "] ", 
                    "Alert", JOptionPane.ERROR_MESSAGE);
        }
    }
}
