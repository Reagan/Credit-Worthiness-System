/**
 * Credit Worthiness System Version 1.0
 */
package AppActions;

import DbConnection.TransactionDetails;
import DbConnection.UsersDetails;
import UI.BottomCenterPanel;
import UI.BottomRightPanel;
import UI.CenterPanel;
import UI.Charts.Chart;
import credit.worthiness.system.CreditWorthinessSystem;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class DeleteTransactionAction extends AbstractedAction 
{
    private String appMessage ;
    private String aboutDialogTitle ;
    private int messageType ;
    
    public DeleteTransactionAction()
    {
        aboutDialogTitle = "Delete Transaction";
        appMessage = "Are you sure that you want to delete the transaction?";
        messageType = JOptionPane.YES_NO_OPTION;
    }

    @Override
    public void run() 
    {
        int res = JOptionPane.showConfirmDialog(null, appMessage, 
                aboutDialogTitle, messageType);
        
        if( res == JOptionPane.YES_OPTION )
        {
            // delete the currently selected transaction from 
            // from the database and refresh the list of transactions
            TransactionDetails tDetails = new TransactionDetails();
            boolean result = false ;
            
            try 
            {
                result = tDetails.deleteTransactionDetails(
                        BottomRightPanel.currTransactionID, BottomRightPanel.transactionType) ;
            }
            catch (SQLException ex) 
            {
                System.out.println("Error: " + ex.toString()) ;
            }
            
            // inform the user if necessary
            if( false==result )
            {
                JOptionPane.showMessageDialog(null, "There was an error deleting the "
                        + "transaction details. Please try again", "Alert", 
                        JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Deleted transaction details successfully",
                "Deleted", JOptionPane.PLAIN_MESSAGE);
                
                // update the list of transactions to reflect the change
                // in that transaction
                BottomCenterPanel.setUserTransactionsModel();
                
                // set all the fields in the bottom right panel 
                // as null
                BottomRightPanel.setTransactionDetails(-1) ;
                
                // set the current transaction as -1
                BottomRightPanel.currTransactionID = -1 ;
                
                // update the chart to show that the transaction has been deleted
                Chart.goToMonth(0) ;
                
                // update the alerter pane with the users credit/debit amount
                CenterPanel.updateAlertLabel(
                    getUserCreditOrDebitAmount(
                        CreditWorthinessSystem.getCurrentUserID()
                    ));
            }
                        
        }
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
        int [] currYearAndMonth = Chart.getCurrentMonthAndYear() ;
        
        UsersDetails user = new UsersDetails() ;
        creditOrDebitAmount = user.getCustCreditOrDebitAmount(currentUserID) ;
        
        return creditOrDebitAmount ;
    }  
}