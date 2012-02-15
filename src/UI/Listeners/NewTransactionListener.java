/**
 * Credit Worthiness System Version 1.0
 */
package UI.Listeners;

import DbConnection.TransactionDetails;
import DbConnection.TransactionTypes;
import DbConnection.UsersDetails;
import UI.BottomCenterPanel;
import UI.CenterPanel;
import UI.Charts.Chart;
import UI.NewTransactionPanel;
import credit.worthiness.system.CreditWorthinessSystem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.StringTokenizer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class NewTransactionListener implements ActionListener
{
    private NewTransactionPanel newTransPanel ;
    private JFrame g;    
    
    public NewTransactionListener(NewTransactionPanel nPanel)
    {
        newTransPanel = nPanel ;
        g = newTransPanel.parent ;
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        if ("Cancel".equals(ae.getActionCommand()))
        {
             if( newTransPanel.date.getText().length() > 0 
                    || NewTransactionPanel.numberOfItems.getText().length() > 0
                    || NewTransactionPanel.items.getSelectedIndex() > -1 
                    || NewTransactionPanel.transactionNotes.getText().length() > 0 )
            {
                    int closeNewTransaction = JOptionPane.showConfirmDialog(newTransPanel, "There are some details entered. "
                            + "Are you sure that you want to cancel the transaction?", 
                            "Details entered", JOptionPane.YES_NO_OPTION);
                    
                    if(closeNewTransaction == 0)
                    {
                        closeNewTransactionDialog(); 
                    }
                    
                    // else, do nothing and wait for user to add more 
                    // details and close the JOptionPane
            }                      
        }
        else if("Save".equals(ae.getActionCommand()))
        {
            // check if all the required transaction details
            // are entered
            if(NewTransactionPanel.currSelectedTransactionType == 
                    TransactionTypes.CREDIT_TRANSACTION)
            {                               
                if( NewTransactionPanel.date.getText().length() == 0 
                        || NewTransactionPanel.numberOfItems.getText().length() == 0
                        || NewTransactionPanel.items.getSelectedIndex() == -1 
                        || NewTransactionPanel.transactionNotes.getText().length() == 0 )
                {
                    JOptionPane.showMessageDialog(newTransPanel, "Please insert all Credit transaction details"
                            , "Transaction Details Error", JOptionPane.INFORMATION_MESSAGE); 
                    return ;
                }
            }
            else if (NewTransactionPanel.currSelectedTransactionType == 
                    TransactionTypes.DEBIT_TRANSACTION)
            {
                if(NewTransactionPanel.debitDate.getText().length() == 0
                        || NewTransactionPanel.debitAmount.getText().length() == 0
                         || NewTransactionPanel.debitTransactionNotes.getText().length() == 0 )
                {
                    JOptionPane.showMessageDialog(newTransPanel, "Please insert all debit transaction details"
                            , "Transaction Details Error", JOptionPane.INFORMATION_MESSAGE); 
                    return ;
                }
            }                    
            
            // insert the details into the database
            String [] date ;
            int noOfItems ;
            String selectedItem ;
            String transactionNotes ;
            int debitAmount = 0;
            
            if(NewTransactionPanel.currSelectedTransactionType == 
                    TransactionTypes.CREDIT_TRANSACTION)
            {
                // get the date components
                date = getDateTokens(NewTransactionPanel.date.getText().trim()) ;
                                
                // get the number of items
                noOfItems = Integer.parseInt(NewTransactionPanel.numberOfItems.getText().trim()) ;

                // get the selected item
                selectedItem = (String) NewTransactionPanel.items.getSelectedItem() ;

                // get the transaction notes
                transactionNotes = NewTransactionPanel.transactionNotes.getText() ;

                // populate the database with details of the new transaction
                TransactionDetails tDetails = new TransactionDetails();
                try 
                {
                    if( tDetails.insertCreditTransactionDetails(date, noOfItems, selectedItem, transactionNotes) )
                    {
                        // alert the user of the success in updating the message
                        JOptionPane.showMessageDialog(newTransPanel, "Transaction details "
                            + "entered successfully", "Success", JOptionPane.INFORMATION_MESSAGE); 

                        // close the dialog 
                        closeNewTransactionDialog();
                        
                        // refresh the models for the transactions list and the 
                        // chart
                        updateApplication() ;
                    }
                    else
                    {
                        // alert the user of the success in updating the message
                        JOptionPane.showMessageDialog(newTransPanel, "There was an error inserting"
                                + " new transaction details into the database", 
                                "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } 
                catch (SQLException ex) 
                {
                    System.out.println("Error: " + ex.toString());
                }
            }
            else if (NewTransactionPanel.currSelectedTransactionType == 
                    TransactionTypes.DEBIT_TRANSACTION)
            {                
                // initialise the date variable
                date = getDateTokens(NewTransactionPanel.debitDate.getText().trim());

                // get the debited amount
                debitAmount = Integer.parseInt(NewTransactionPanel.debitAmount.getText().trim()) ;
                
                // get the transaction notes
                transactionNotes = NewTransactionPanel.debitTransactionNotes.getText() ;

                // populate the database with details of the new transaction
                TransactionDetails tDetails = new TransactionDetails();
                try 
                {
                    if( tDetails.insertDebitTransactionDetails(date, debitAmount, transactionNotes) )
                    {
                        // alert the user of the success in updating the message
                        JOptionPane.showMessageDialog(newTransPanel, "Transaction details "
                            + "entered successfully", "Success", JOptionPane.INFORMATION_MESSAGE); 

                        // close the dialog
                        closeNewTransactionDialog();
                        
                        // refresh the models for the transactions list and the 
                        // chart
                        updateApplication() ;                         
                    }
                    else
                    {
                        // alert the user of the success in updating the message
                        JOptionPane.showMessageDialog(newTransPanel, "There was an error inserting"
                                + " new transaction details into the database", 
                                "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } 
                catch (SQLException ex) 
                {
                    System.out.println("Error : " + ex.toString());
                }
            }
            
            // update the alerter showing the amount of credit/debit user has
            CenterPanel.updateAlertLabel(
                    getUserCreditOrDebitAmount(
                        CreditWorthinessSystem.getCurrentUserID()
                    ));
            
            // close the dialog
            closeNewTransactionDialog();
        }
    }

    /**
     * This method closes the new transaction dialog
     */
    private void closeNewTransactionDialog() 
    {
        // close the new User dialog       
        g.setVisible(false);
        g.dispose();
    }

    /**
     * This method updates the UI based on the newly entered 
     * chart plots datas
     */
    private void updateApplication() 
    {
        // update the transactions list
        BottomCenterPanel.setUserTransactionsModel();
        
        // update the chart model
        // add 0 meaning go to the current month
        Chart.goToMonth(0);                
    }
    
    private String[] getDateTokens ( String trimmedDate )
    {
        String [] date ;
        int dateTokensCounter = 0 ;
            
        // split the date 
        StringTokenizer tokens = new StringTokenizer(trimmedDate, "/");

        // initialise the date variable
        date = new String[tokens.countTokens()];

        // populate the date variable
        while( tokens.hasMoreTokens() )
        {
            date[dateTokensCounter] = tokens.nextToken() ;
            dateTokensCounter ++ ;
        }
        
        return date ;
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