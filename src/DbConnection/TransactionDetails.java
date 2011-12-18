/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DbConnection;

import java.util.Arrays;
import java.util.Vector;

/**
 *
 * @author rmbitiru
 */
public class TransactionDetails 
{
    private DatabaseConnection dbConn = null ;
    private String getTransactionDetailsQuery ;
    private String getMonthTransNumberForUserQuery ;
    private String getMinMaxMonthTransValForUserQuery ;
    
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
    
    /**
     * This method returns the number of transactions for a specific
     * user for a particular day in month for a specific user
     * @param customers_id
     * @param month
     * @param year
     * @return 
     */
    public int getUserTransactionsNumberForMonth(int customers_id, int month, int year)
    {
        String[] transNumber = null; 
        
        getMonthTransNumberForUserQuery = " SELECT COUNT(*) FROM transactions "
                + "WHERE customers_id = "
                + customers_id
                + " AND month = "
                + month
                + " AND year = "
                + year ;
        
        Vector userMonthTransNumber = new Vector() ;
        
        dbConn = new DatabaseConnection();
        dbConn.connect();
        
        userMonthTransNumber = dbConn.fetch(getMonthTransNumberForUserQuery);
        
        for(int transactionsNo = 0, s = userMonthTransNumber.size();
                transactionsNo < s ; transactionsNo ++)
        {
            transNumber = (String[]) userMonthTransNumber.get(transactionsNo) ;            
        }
        
        return Integer.parseInt(transNumber[0]) ;
    }
    
    public int[] getMinMaxTransValues(int customers_id, int month, int year)
    {
        int [] yMinAndMax = new int[2] ; 
        
        getMinMaxMonthTransValForUserQuery = "SELECT (SELECT items_number*items_cost FROM items "
                + "AS k WHERE k.items_id = c.items_id) AS total_items_cost FROM "
                + "credit_transactions AS c, (SELECT transaction_id,transaction_type FROM "
                + " transactions WHERE customers_id= "
                + customers_id
                + " AND month= "
                + month
                + " AND year= "
                + year
                + " ) AS s WHERE c.transaction_id = s.transaction_id "; 
        
        Vector transValues = new Vector() ;
        
        dbConn = new DatabaseConnection();
        dbConn.connect();
        
        transValues = dbConn.fetch(getMinMaxMonthTransValForUserQuery);
        
        int[] vals = new int[transValues.size()] ;
        
        for(int transValuesNo = 0, s = transValues.size();
                transValuesNo < s ; transValuesNo ++)
        {
            String[] r = (String[]) transValues.get(transValuesNo) ;            
            vals[transValuesNo]  = Integer.parseInt(r[0]) ;
        }
        
        // get the minimum and maximum values for 
        // the transactions
        Arrays.sort(vals);
        yMinAndMax[0] = vals[0] ;
        yMinAndMax[1] = vals[1] ;
        
        return yMinAndMax ;
    }
}
