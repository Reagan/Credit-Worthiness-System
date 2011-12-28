/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AppActions;

import DbConnection.TransactionDetails;
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

    public UpdateTransactionDetailsAction(BottomRightPanel panel)
    {
        aboutDialogTitle = "Alert!";
        appMessage = "Please fill missing information?";
        messageType = JOptionPane.YES_NO_OPTION;
        transPanel = panel ;
        //transactionID = transID ;
    }

    @Override
    public void run() 
    {
        JOptionPane.showMessageDialog(null, "Saving details for transaction # " 
                + transPanel.currTransactionID, 
                "Editing details", JOptionPane.PLAIN_MESSAGE);
        
        // check if any changes made
        if(true == transPanel.dirty)
        {
            // get the transaction type
            int transType = transPanel.transactionType ;
            
            // get the current transaction ID
            int transID = transPanel.currTransactionID ;
            
            // get the date
            String date = transPanel.date.getText() ;
            
            // get the number of items
            int itemsNo = Integer.parseInt(BottomRightPanel.numberOfItems.getText()) ;
            
            // get the notes
            String notes = transPanel.transactionNotes.getText() ;
            
            // get selected item
            // get the selected index
            int selectedIndexItem = transPanel.items.getSelectedIndex() ;
            
            // get the selected item
            String selectedItem = (String) transPanel.itemsObt.get(selectedIndexItem) ;
                        
            // insert the updated information to the database
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy") ;
            Date d = null;
            try {
                d = df.parse(date);
            } catch (ParseException ex) {
                Logger.getLogger(UpdateTransactionDetailsAction.class.getName()).log(Level.SEVERE, null, ex);
            }
            Calendar cal = Calendar.getInstance() ;
            cal.setTime(d);                        
            
            TransactionDetails tDetails = new TransactionDetails();
            boolean result = false ;
            
            try 
            {
                result = tDetails.updateTransactionDetails(transType, transID,
                itemsNo, notes, selectedItem, cal.get(Calendar.DATE), 
                cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
            } 
            catch (SQLException ex) 
            {
                Logger.getLogger(UpdateTransactionDetailsAction.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if( false==result )
            {
                JOptionPane.showMessageDialog(null, "There was an error updating the "
                        + "transaction details. Please try again", "Alert", 
                        JOptionPane.ERROR_MESSAGE);
            }
            // set the dirty tag to false
            transPanel.dirty = false ;
        }
        else
        {
            JOptionPane.showMessageDialog(null, "No changes made to the transaction"
                    , "Alert", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
