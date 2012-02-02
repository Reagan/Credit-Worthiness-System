/**
 * Credit Worthiness System Version 1.0
 */
package UI.Listeners;

import DbConnection.TransactionDetails;
import UI.BottomCenterPanel;
import UI.Charts.Chart;
import UI.LeftPanel;
import UI.NewTransactionPanel;
import UI.NewUserPanel;
import credit.worthiness.system.CreditWorthinessSystem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
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
        {System.out.println("Currently selected Transaction Type : " +
                NewTransactionPanel.currSelectedTransactionType);
            // check if all the required transaction details
            // are entered
            if(NewTransactionPanel.currSelectedTransactionType == 
                    NewTransactionPanel.CREDIT_TRANSACTION)
            {System.out.println("1 XXXXX") ;
                // get the currently selected panel
                int currPanel = NewTransactionPanel.currSelectedTransactionType; 
                
                if( NewTransactionPanel.date.getText().length() == 0 
                        || NewTransactionPanel.numberOfItems.getText().length() == 0
                        || NewTransactionPanel.items.getSelectedIndex() == -1 
                        || NewTransactionPanel.transactionNotes.getText().length() == 0 )
                {
                    JOptionPane.showMessageDialog(newTransPanel, "1. Please insert all transaction details"
                            , "Transaction Details Error", JOptionPane.INFORMATION_MESSAGE); 
                    return ;
                }
            }
            else if (NewTransactionPanel.currSelectedTransactionType == 
                    NewTransactionPanel.DEBIT_TRANSACTION)
            {System.out.println("2 XXXXX") ;
                if(NewTransactionPanel.debitDate.getText().length() == 0
                        || NewTransactionPanel.debitAmount.getText().length() == 0
                         || NewTransactionPanel.debitTransactionNotes.getText().length() == 0 )
                {
                    JOptionPane.showMessageDialog(newTransPanel, "2. Please insert all transaction details"
                            , "Transaction Details Error", JOptionPane.INFORMATION_MESSAGE); 
                    return ;
                }
            }                    
            
            // insert the details into the database
            String [] date ;
            int noOfItems ;
            int selectedItem ;
            String transactionNotes = null ;
            int dateTokensCounter = 0 ;
            int debitAmount = 0;
            
            if(NewTransactionPanel.currSelectedTransactionType == 
                    NewTransactionPanel.CREDIT_TRANSACTION)
            {
                // split the date 
                StringTokenizer tokens = new StringTokenizer(newTransPanel.date.getText().trim()
                        , "/");

                // initialise the date variable
                date = new String[tokens.countTokens()];

                // populate the date variable
                while( tokens.hasMoreTokens() )
                {
                    date[dateTokensCounter] = tokens.nextToken() ;
                    dateTokensCounter ++ ;
                }

                // get the number of items
                noOfItems = Integer.parseInt(newTransPanel.numberOfItems.getText().trim()) ;

                // get the selected item
                selectedItem = newTransPanel.items.getSelectedIndex() ;

                // get the transaction notes
                String notes = newTransPanel.transactionNotes.getText() ;

                // populate the database with details of the new transaction
                TransactionDetails tDetails = new TransactionDetails();
                try 
                {
                    if( tDetails.insertCreditTransactionDetails(date, noOfItems, selectedItem, notes) )
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
                    NewTransactionPanel.DEBIT_TRANSACTION)
            {
                // split the date 
                StringTokenizer tokens = new StringTokenizer(newTransPanel.debitDate.getText().trim()
                        , "/");

                // initialise the date variable
                date = new String[tokens.countTokens()];

                // populate the date variable
                while( tokens.hasMoreTokens() )
                {
                    date[dateTokensCounter] = tokens.nextToken() ;
                    dateTokensCounter ++ ;
                }

                // get the debitted amount
                debitAmount = Integer.parseInt(newTransPanel.debitAmount.getText().trim()) ;
                
                // get the transaction notes
                String notes = newTransPanel.debitTransactionNotes.getText() ;

                // populate the database with details of the new transaction
                TransactionDetails tDetails = new TransactionDetails();
                try 
                {
                    if( tDetails.insertDebitTransactionDetails(date, debitAmount, notes) )
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
    
}
