/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DbConnection;

import java.util.Vector;

/**
 *
 * @author rmbitiru
 */
public class TransactionDetails 
{
    private DatabaseConnection dbConn = null ;
    private String getTransactionDetailsQuery ;
    
    public TransactionDetails(){}
    
    /** This method returns information on a specific
     * transaction that is then displayed on the bottom
     * right panel
     * @param transactionID
     * @return 
     */
    public String[] getTransactionDetails(int transactionID)
    {  
        String [] results = new String[1] ;
                
        getTransactionDetailsQuery = "SELECT IF(transaction_type=1,(SELECT items_id FROM items WHERE "
                + "items.items_id = (SELECT items_id FROM credit_transactions WHERE "
                + "credit_transactions.transaction_id = s.transaction_id)),(SELECT CONCAT (\"Kshs \", amount) "
                + "FROM debit_transactions WHERE "
                + "debit_transactions.transaction_id = s.transaction_id)) "
                + "AS item_name, if(transaction_type=1,(SELECT items_number "
                + "FROM credit_transactions WHERE "
                + "credit_transactions.transaction_id = s.transaction_id),\"Loan\") "
                + "AS item_number, day, month, year FROM (SELECT * FROM transactions "
                + "WHERE transaction_id = "
                + transactionID
                + ") AS s ";    
        
        Vector transactionDetails = new Vector() ;
        
        dbConn = new DatabaseConnection();
        dbConn.connect();
        
        transactionDetails = dbConn.fetch(getTransactionDetailsQuery);
        
        // open up the returned values and 
        // return the user names in the desired 
        // format [firstname lastname]
        for(int transactionsNo = 0, s = transactionDetails.size();
                transactionsNo < s ; transactionsNo ++)
        {
            results = (String[]) transactionDetails.get(transactionsNo) ;            
        }
        
        return results ;
    }              
}
