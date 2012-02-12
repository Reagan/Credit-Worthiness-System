/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DbConnection;

import credit.worthiness.system.CreditWorthinessSystem;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * This class creates and obtains the results for database queries involving
 * transaction details. To ensure support for both MySQL and 
 * H2 (embedded database), the CASE WHEN command is used as opposed 
 * to the IF command that is unsupported by H2 and other embedded databases
 * @author rmbitiru
 */
public class TransactionDetails 
{
    private DatabaseConnection dbConn = null ;
    private String getTransactionDetailsQuery ;
    private String getMonthTransNumberForUserQuery ;
    private String getMinMaxMonthTransValForUserQuery ;
    private String getPlottedTransactionDetailsQuery ;
    private String [] updateTransactionQuery = new String[2] ;
    private String [] deleteTransactionDetails = new String[2] ;
    private String [] insertCreditTransactionQuery = new String[2] ;
    private String [] insertDebitTransactionQuery = new String[2] ;
    private String mostRecentTransactionIDQuery = null ;    
    
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
                
        /**
        getTransactionDetailsQuery = "SELECT IF(transaction_type=1,(SELECT items_id "
                + " FROM items WHERE items.items_id = (SELECT items_id FROM "
                + "credit_transactions WHERE credit_transactions.transaction_id "
                + "= s.transaction_id)),(SELECT CONCAT ('Kshs ', amount) "
                + "FROM debit_transactions WHERE debit_transactions.transaction_id "
                + "= s.transaction_id)) AS item_name, if(transaction_type=1,"
                + "(SELECT items_number FROM credit_transactions WHERE "
                + "credit_transactions.transaction_id = s.transaction_id),'Loan') "
                + "AS item_number, day, month, year,(SELECT info FROM credit_transactions "
                + "WHERE credit_transactions.transaction_id = "
                + "s.transaction_id) as information, transaction_type FROM "
                + "(SELECT * FROM transactions WHERE transaction_id = "
                + transactionID
                + ") AS s ";
        **/
        
         getTransactionDetailsQuery = "SELECT "
                 + "CASE WHEN transaction_type=1 THEN (SELECT items_name "
                + " FROM items WHERE items.items_id = (SELECT items_id FROM "
                + "credit_transactions WHERE credit_transactions.transaction_id "
                + "= s.transaction_id)) ELSE 'Cash' END AS item_name, CASE WHEN transaction_type=1 THEN"
                + "(SELECT items_number FROM credit_transactions WHERE "
                + "credit_transactions.transaction_id = s.transaction_id) ELSE "
                + "(SELECT  amount FROM debit_transactions WHERE debit_transactions.transaction_id = s.transaction_id) "
                + " END "
                + "AS item_number, day, month, year,CASE WHEN transaction_type=1 "
                + " THEN (SELECT info FROM credit_transactions WHERE credit_transactions.transaction_id = s.transaction_id) "
                + " ELSE (SELECT info FROM debit_transactions WHERE debit_transactions.transaction_id = s.transaction_id) "
                + " END as information , transaction_type FROM "
                + "(SELECT * FROM transactions WHERE transaction_id = "
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
                + year
                + " AND transaction_type = 1";
        
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
        yMinAndMax[1] = vals[vals.length - 1] ;
        
        return yMinAndMax ;
    }

    /**
     * This method returns the details for the transactions
     * done by a user for specific month for plotting
     * @param customers_id
     * @param month
     * @param year
     * @return 
     */
    public String[][] getPlottedTransactionDetails(int customers_id, int month
            , int year, int transactionsNumber) 
    {
        String[][] transactionDetails = new String[transactionsNumber][] ;
        
        getPlottedTransactionDetailsQuery = "SELECT c.transaction_id, s.day, (SELECT "
                + " items_name FROM items AS i WHERE i.items_id = c.items_id) "
                + " AS item_name , items_number, (SELECT items_number*items_cost "
                +" FROM items AS k where k.items_id = c.items_id) AS total_items_cost "
                + " FROM credit_transactions AS c, (SELECT transaction_id,transaction_type, "
                + " day FROM transactions where customers_id= "
                + customers_id
                + " AND month= "
                + month
                + " AND year= "
                + year
                + ") AS s WHERE c.transaction_id = s.transaction_id ";
        
        Vector plottedTransValues = new Vector() ;
        
        dbConn = new DatabaseConnection();
        dbConn.connect();
        
        plottedTransValues = dbConn.fetch(getPlottedTransactionDetailsQuery);
        
        for(int plottedTransDetailsNo = 0, s = plottedTransValues.size();
                plottedTransDetailsNo < s ; plottedTransDetailsNo ++)
        {
            transactionDetails[plottedTransDetailsNo] = 
                    (String[]) plottedTransValues.get(plottedTransDetailsNo) ;  
        }
        
        return transactionDetails ;
    }
    
    /**
     * This method deletes all information related to a specific
     * transaction in the database
     * @param transID
     * @return 
     */
    public boolean deleteTransactionDetails(int transID, int transType) throws SQLException
    {                        
        if(1 == transType)
        {
            deleteTransactionDetails[0] = "DELETE FROM credit_transactions  "
                    + " WHERE transaction_id = "
                    + transID;                                
        }
        else if(2 == transType)
        {
            deleteTransactionDetails[0] = "DELETE FROM debit_transactions  "
                    + " WHERE transaction_id = "
                    + transID;    
        }
        
        deleteTransactionDetails[1] = "DELETE FROM transactions  "
                    + " WHERE transaction_id = "
                    + transID;   
        
        // now run the queries
         for (int queryCounter = 0 ; 
                queryCounter < deleteTransactionDetails.length ; queryCounter++)
        {
            // run the queries
            dbConn = new DatabaseConnection();
            dbConn.connect();
        
            boolean result = dbConn.update(
                    deleteTransactionDetails[queryCounter]); 
            
            if(false == result)
            {
                return result ; 
            }
        }        
        
        return true; 
    }
    
    /**
     * This method updates the details on a specific transaction
     * @param transType
     * @param transID
     * @param itemsNo
     * @param notes
     * @param selectedItem
     * @param day
     * @param month
     * @param year
     * @return
     * @throws SQLException 
     */
    public boolean updateTransactionDetails(int transType, int transID, int itemsNo, 
            String notes, String selectedItem, int amount, int day
            , int month, int year ) throws SQLException
    {
        if(transID == 0 || !(transType == 1 || transType == 2) )
        {
            // Error with the selected item             
            // alert error
            JOptionPane.showMessageDialog(null, "There was an error with the supplied transaction details",
                    "Error!", JOptionPane.WARNING_MESSAGE);
            
            return false ;
        }
        
        // insert the information into the database
        // update the CREDIT or DEBIT transactions table
        if(1 == transType)
        {
            updateTransactionQuery[0] = "UPDATE credit_transactions SET "
                    + "items_number = "
                    + itemsNo
                    + ", items_id = (SELECT items_id FROM items "
                    + "WHERE items_name='"
                    + selectedItem.trim()
                    + "'), info = '"
                    +  notes
                    + "' WHERE "
                    + "transaction_id = "
                    + transID;                                
        }
        else if(2 == transType)
        {
            updateTransactionQuery[0] = "UPDATE debit_transactions SET "
                    + " amount = "
                    + amount
                    + ", info = '"
                    + notes
                    + "' where transaction_id = "
                    + transID ;
        }
        
        // create the second part of the query
        // update the TRANSACTIONS table
        updateTransactionQuery[1] = "UPDATE transactions SET day =  "
                    + day
                    + ","
                    + " month = "
                    + month
                    + ", "
                    + "year = "
                    + year
                    + " WHERE transaction_id = "
                    + transID ;                
        
        for (int queryCounter = 0 ; 
                queryCounter < updateTransactionQuery.length ; queryCounter++)
        {
            // run the queries
            dbConn = new DatabaseConnection();
            dbConn.connect();
        
            boolean result = dbConn.update(
                    updateTransactionQuery[queryCounter]); 
            
            if(false == result)
            {
                return result ; 
            }
        }        
        
        return true ;
    }
    
    /**
     * This method inserts details for a new transaction into the database
     * inserts credit transactions
     * @param date
     * @param noOfItems
     * @param selectedItem
     * @param transactionNotes 
     * @return  
     */
    public boolean insertCreditTransactionDetails(String[] date, int noOfItems, 
            int selectedItem, String transactionNotes) throws SQLException
    {       
        int transactionType = 1 ;
        int day = Integer.parseInt(date[0]); 
        int month = Integer.parseInt(date[1]) - 1;
        int year = Integer.parseInt(date[2]);
        
        // insert the information into the database
        // start with the credit transactions
        // insert into transactions table
        insertCreditTransactionQuery[0] = "INSERT INTO transactions "
                + "(transaction_type, customers_id, day, month, year) "
                + " VALUES ("
                + transactionType
                + ","
                + CreditWorthinessSystem.getCurrentUserID()
                + ","
                + day
                + ","
                + month
                + ","
                + year 
                + ")";
        
        // insert into the debit transactions table  
        createDatabaseObject();
        boolean result = dbConn.insert(
                insertCreditTransactionQuery[0]);   // insert into the transactions 
                                                    // table        
        if(false == result)
        {
            closeDatabaseConnection();
            System.out.println("Result was null") ;
            return result ; 
        }
        
        // get the most recent transaction ID
        int mostRecentTransID = getMostRecentTransactionID();
        
        // insert into credit transactions table
        insertCreditTransactionQuery[1] = "INSERT INTO credit_transactions "
                + "( transaction_id, items_id, items_number, info )"
                + " VALUES ("
                + mostRecentTransID
                + ","
                + selectedItem
                + ","
                + noOfItems
                + ", '"
                + transactionNotes
                + "')";
                
        // run the queries and insert the details into the database             
        boolean result2 = dbConn.insert(
                insertCreditTransactionQuery[1]); 

        if(false == result2)
        {
            closeDatabaseConnection();
            return result ; 
        }              
        
        closeDatabaseConnection();
        return true ;
    }
    
    /** 
     * This method inserts the details for debit transactions 
     * into the database
     * @param date
     * @param selectedItem
     * @param transactionNotes
     * @return 
     */
    public boolean insertDebitTransactionDetails(String[] date, int debitAmount, 
            String transactionNotes) throws SQLException
    {
        int transactionType = 2 ;
        int day = Integer.parseInt(date[0]); 
        int month = Integer.parseInt(date[1]) - 1;
        int year = Integer.parseInt(date[2]);
        
        // insert the information into the database
        // start with the debit transactions
        //
        // insert into transactions table
        insertDebitTransactionQuery[0] = "INSERT INTO transactions "
                + "(transaction_type, customers_id, day, month, year) "
                + " VALUES ("
                + transactionType
                + ","
                + CreditWorthinessSystem.getCurrentUserID()
                + ","
                + day
                + ","
                + month
                + ","
                + year 
                + ")";                                                       
                
        // run the queries and insert the details into the database         
        // insert into the debit transactions table  
        createDatabaseObject();
        boolean result = dbConn.insert(
                insertDebitTransactionQuery[0]);   // insert into the transactions 
                                                    // table        
        if(false == result)
        {
            closeDatabaseConnection();
            System.out.println("Result was null") ;
            return result ; 
        }
               
        // get the most recent transactions ID
        int mostRecentTransID = getMostRecentTransactionID();        
        
        // insert the details into the debit_transactions table
        insertDebitTransactionQuery[1] = "INSERT INTO debit_transactions "
                + "( transaction_id, amount, info )"
                + " VALUES ("
                + mostRecentTransID
                + ","
                + debitAmount
                + ",'"
                + transactionNotes
                + "')";
        
        boolean result2 = dbConn.insert(
                insertDebitTransactionQuery[1]);   // insert into the transactions 
                                                   // table       
        if(false == result2)
        {
            closeDatabaseConnection();
            System.out.println("Result was null") ;
            return result ; 
        }
        
        closeDatabaseConnection();
        return true ;
    }

    /**
     * This method returns the ID of the most recent transaction ID
     * @return 
     */
    private int getMostRecentTransactionID() 
    {
        // this method returns the ID for the most 
        // recent transaction inserted/updated        
        return dbConn.getMostRecentTransactionID() ;
    }

    /**
     * This method creates a database object for use in connecting
     * to the database for the various transactions
     */
    private void createDatabaseObject() 
    {
        // run the queries
        dbConn = new DatabaseConnection();
        dbConn.connect();
    }
    
    /**
     * This method closes the database connection and destroys the 
     * database connection object
     */
    private void closeDatabaseConnection()
    {
        try 
        {
            dbConn.closeDatabaseConnection();
        } 
        catch (SQLException ex) 
        {
            System.out.println("Error with a database transaction : " + ex);
        }
    }
}
