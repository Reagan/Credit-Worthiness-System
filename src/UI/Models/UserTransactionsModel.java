/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Models;

import DbConnection.UsersDetails;
import credit.worthiness.system.CreditWorthinessSystem;
import java.util.Vector;
import javax.swing.AbstractListModel;

/**
 *
 * @author rmbitiru
 */
public class UserTransactionsModel extends AbstractListModel
{

    // create the object to store the data for the 
    // transactions JList
    
    // @TODO : make sure that this JList is populated programmatically
    private int currentUserID ; // current User ID's transactions
    
    private Vector <String> transactions ; // list of all the transactions for 
                                // the JList
    public static int[] transIDs ; // stores the transaction IDs for the various transactions
    private UsersDetails userTransactions ; // connects to the DB and returns the
                                            // stored transactions
    
    public UserTransactionsModel(int userID)
    {
        currentUserID = userID ;
        userTransactions = new UsersDetails() ;
                
        // populate the transactions list
        // @TODO: this should be populated from 
        // the database
        transactions = populateTransactionsList() ;
        
        // populate the transactionIDs         
        transIDs = new int[transactions.size()] ;
        populateTransIDs();
    }
    
    @Override
    public int getSize() 
    {
        return transactions.size();
    }

    @Override
    public Object getElementAt(int index) 
    {
        return (String) transactions.get(index) ;
    }
    
    /**
     * populates the transaction list for the 
     * user's transactions
     */
    public Vector populateTransactionsList()
    {
        transactions = new Vector <String> ();
                
        // update the model for the transactions 
        // list
        //
        // get this from the database
        
        transactions = userTransactions
                .getUserTransactions(CreditWorthinessSystem.getCurrentUserID());       
                    
        return transactions ;
    }    
    
    private void populateTransIDs()
    {
         // set the transaction IDs
        transIDs = userTransactions
                .getUserTransactionIDs(CreditWorthinessSystem.getCurrentUserID(), 
                transIDs.length);        
    }
}