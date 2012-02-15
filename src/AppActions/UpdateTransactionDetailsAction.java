/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AppActions;

import DbConnection.TransactionDetails;
import DbConnection.TransactionTypes;
import DbConnection.UsersDetails;
import UI.BottomCenterPanel;
import UI.BottomRightPanel;
import UI.CenterPanel;
import UI.Charts.Chart;
import credit.worthiness.system.CreditWorthinessSystem;
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

    public UpdateTransactionDetailsAction()
    {
        aboutDialogTitle = "Alert!";
        appMessage = "Please fill missing information?";
        messageType = JOptionPane.OK_OPTION;
        //transactionID = transID ;
    }

    @Override
    public void run() 
    {            
        // check if any changes made
        if(true == BottomRightPanel.dirty)
        {
            // get the transaction type
            int transType = BottomRightPanel.transactionType ;
            
            // get the current transaction ID
            int transID = BottomRightPanel.currTransactionID ;
            
            
            // branch depending on the type of transaction being made
            if ( transType == TransactionTypes.CREDIT_TRANSACTION )
            {
                // get the date
                String date = BottomRightPanel.date.getText() ;

                // get the number of items
                int itemsNo = Integer.parseInt(BottomRightPanel.numberOfItems.getText()) ;

                // get the notes
                String notes = BottomRightPanel.transactionNotes.getText() ;

                // get selected item
                // get the selected index
                int selectedIndexItem = BottomRightPanel.items.getSelectedIndex() ;

                // get the selected item
                String selectedItem = (String) BottomRightPanel.itemsObt.get(selectedIndexItem) ;

                Calendar cal = getDateObject(date) ; 
                
                if ( null != cal || selectedItem.length() > 0 )
                {
                    // insert into the database
                    updateTransactionDetails(transType, transID, notes,
                        selectedItem, cal, itemsNo, 0) ;                     
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "There was an error updating the "
                    + "transaction details. Please try again", "Alert", 
                    JOptionPane.ERROR_MESSAGE);
                }
                
            }
            else if ( transType == TransactionTypes.DEBIT_TRANSACTION)
            {
                // get the date
                String date = BottomRightPanel.debitDate.getText() ;
                
                // get the number of items
                int amount = Integer.parseInt(BottomRightPanel.debitAmount.getText()) ;

                // get the notes
                String notes = BottomRightPanel.debitTransactionNotes.getText() ;
                
                Calendar cal = getDateObject(date) ; 
                
                if ( null != cal )
                {
                    // insert into the database
                    updateTransactionDetails(transType, transID, notes,
                        "", cal, 0, amount) ;                     
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "There was an error updating the "
                    + "transaction details. Please try again", "Alert", 
                    JOptionPane.ERROR_MESSAGE);
                }
                
            }
            
            // set the dirty tag to false
            BottomRightPanel.dirty = false ;
            
            // update the alerter with the right credit/debit amount for the
            // user
            CenterPanel.updateAlertLabel(
                    getUserCreditOrDebitAmount(
                        CreditWorthinessSystem.getCurrentUserID()
                    ));
        }
        else
        {
            JOptionPane.showMessageDialog(null, "You have not made any changes "
                    + "for this transaction # [" 
                    + BottomRightPanel.currTransactionID
                    + "] ", 
                    "Alert", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateTransactionDetails(int transType, int transID, String transNotes,
            String selectedItem, Calendar cal, int itemsNo, int amount)
    {
        // get the transaction details for the currently
        // selected transaction
        TransactionDetails tDetails = new TransactionDetails();
        boolean result = false ;

        try 
        {
            result = tDetails.updateTransactionDetails(transType, transID,
            itemsNo, transNotes, selectedItem, amount, cal.get(Calendar.DATE), 
            cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
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
            + " successfully",
            "Editing details", JOptionPane.PLAIN_MESSAGE);

            // update the list of transactions to reflect the change
            // in that transaction
            BottomCenterPanel.setUserTransactionsModel();
            
            // update the chart model
            Chart.goToMonth(0) ;
        }
    }
    
    private Calendar getDateObject(String date)
    {
        Calendar cal = Calendar.getInstance() ;
        
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
            return null ;
        }
        
        cal.setTime(d);     
        return cal ;
    }
    
    
    /**
     * This method obtains the amount by which a customer
     * is in debt or credit and returns this value
     * @param currentUserID
     * @return 
     */
    private double getUserCreditOrDebitAmount(int currentUserID) 
    {
        double creditOrDebitAmount = 0 ;
        
        // get the current month and year
        int [] currYearAndMonth = CenterPanel.getCurrentMonthAndYear() ;
        
        UsersDetails user = new UsersDetails() ;
        creditOrDebitAmount = user.getCustCreditOrDebitAmount(currentUserID) ;
        
        return creditOrDebitAmount ;
    }      
}