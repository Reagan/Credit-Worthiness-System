/**
 * Credit Worthiness System Version 1.0
 */
package DbConnection;

import UI.LeftPanel;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class UsersDetails 
{
    private DatabaseConnection dbConn = null ;
    private String getUsersNamesQuery = null ;
    private String getUserIDQuery = null ;
    private String getUserJoiningDateQuery = null ;
    private String getUserAvatarQuery = null ;
    private String getUserTransactionsQuery = null ;
    private String getUserTransactionsIDsQuery = null ;    
    private final String [] months =  {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov", "Dec" };
    private String getDetailedTransactionDetails ;
    private String setNewUserDetailsQuery ;
    private String [] deleteUserDetailsQuery ;
    private String getCustCreditOrDebitAmountQuery ; // get the amount by which 
                                                    // a customer is in credit 
                                                    // or debit
    private String getTotalExpenditureQuery ;
    private String getCreditAmountQuery ;
    
    public UsersDetails(){}

    /**
     * This method returns the names for all 
     * users stored in the database
     * @return 
     */
    public Vector getUsersNames()
    {               
        getUsersNamesQuery = "SELECT customers_firstname, customers_secondname"
            + " FROM customers" ;
        
        Vector usersNames = null ;
        Vector results = new Vector() ;
        
        dbConn = new DatabaseConnection();
        dbConn.connect();
        
        usersNames = dbConn.fetch(getUsersNamesQuery);
        
        // open up the returned values and 
        // return the user names in the desired 
        // format [firstname lastname]
        for(int usersNo = 0, s = usersNames.size();
                usersNo < s ; usersNo ++)
        {
            String [] currUser = (String[]) usersNames.get(usersNo) ;
            results.add(currUser[0] + " " + currUser[1]);
        }
        
        return results ;
    }
    
    public String getUserID(String userName)
    {
        Vector userID = null ;
        String[] result = new String[1] ;
        
        getUserIDQuery = "SELECT customers_id FROM customers WHERE "                 
                + "CONCAT(customers_firstname, \" \", customers_secondname)" 
                + " = \"" 
                + userName
                + "\"";
                        
        
        dbConn = new DatabaseConnection();
        dbConn.connect();
        
        userID = dbConn.fetch(getUserIDQuery);  
        
        result = (String[]) userID.get(0) ;
        
        return result[0]; 
    }
    
    public String getUserJoiningDate(String userName)
    {                        
        Vector joiningDate = null ;
        String [] results = new String[3] ;
        
        getUserJoiningDateQuery = "SELECT joining_day, joining_month, year FROM customers WHERE "
                + "CONCAT(customers_firstname, \" \", customers_secondname)" 
                + " = \"" 
                + userName
                + "\"";
        
        dbConn = new DatabaseConnection();
        dbConn.connect();
        
        joiningDate = dbConn.fetch(getUserJoiningDateQuery);
        
        results = (String[]) joiningDate.get(0) ;
        
        // return the [ day, month & year ]
        return  results[0] + "," + months[Integer.parseInt(results[1])] + " "
                + results[2] ;
    }
    
    public String getUserAvatarName(String userName)
    {
        Vector avatarName = null ;
        String [] results = new String[1] ;
        
        getUserAvatarQuery = "SELECT images_name FROM customers WHERE "
                + "CONCAT(customers_firstname, \" \", customers_secondname)" 
                + " = \"" 
                + userName
                + "\"";
        
        dbConn = new DatabaseConnection();
        dbConn.connect();
        
        avatarName = dbConn.fetch(getUserAvatarQuery);
        results = (String[]) avatarName.get(0) ;
        return (results[0] == null) ? LeftPanel.DEFAULTUSERIMAGE: 
                results[0];
    }
    
    /**
     * This method obtains the transactions for a user
     * as identified by a specific user ID and returns
     * the transactions data in the following structure
     * for each transaction
     * [ Item {#}, date ]
     * @param userID
     * @return 
     */
    public Vector getUserTransactions(int userID)
    {
        Vector userTransactions = null;
        Vector userTransactionsRes = new Vector() ;
        
        getUserTransactionsQuery = "SELECT IF(transaction_type=1,(SELECT items_name FROM items WHERE "
                + "items.items_id = (SELECT items_id FROM credit_transactions WHERE "
                + "credit_transactions.transaction_id = s.transaction_id)),(SELECT CONCAT (\"Kshs \", amount) "
                + "FROM debit_transactions WHERE "
                + "debit_transactions.transaction_id = s.transaction_id)) "
                + "AS item_name, if(transaction_type=1,(SELECT items_number "
                + "FROM credit_transactions WHERE "
                + "credit_transactions.transaction_id = s.transaction_id),\"Loan\") "
                + "AS item_number, day, month, year FROM (SELECT * FROM transactions "
                + "WHERE customers_id = "
                + userID
                + ") AS s ORDER BY year, month, day";        
        
        dbConn = new DatabaseConnection();
        dbConn.connect();
        
        userTransactions = dbConn.fetch(getUserTransactionsQuery);
        
        // open up the returned values and 
        // return the use transactions in the following
        // format
        // 
        // [item_id, item_number, day, month, year ]
        
        for(int transNo = 0, s = userTransactions.size();
                transNo < s ; transNo ++)
        {
            String [] currTransaction = (String[]) userTransactions.get(transNo) ;
            userTransactionsRes.add(currTransaction[0] 
                    + " [" 
                    + currTransaction[1]
                    + "], "
                    + currTransaction[2]
                    + " "
                    + months[Integer.parseInt(currTransaction[3])]
                    + " "
                    + currTransaction[4]);                         
        }                
        
        return userTransactionsRes ;
    }
    
    /**
     * This method obtains the transaction IDs for the
     * various transactions for a specific user
     * @param userID
     * @return 
     */
    public int[] getUserTransactionIDs(int userID, int transactionsNumber)
    {
        Vector userTransIDs = null ;
        int [] transIDs = new int[transactionsNumber] ;
        
        getUserTransactionsIDsQuery = "SELECT transaction_id FROM transactions "
                + "WHERE customers_id = "
                + userID 
                + "  ORDER BY year, month, day";        
                
        dbConn = new DatabaseConnection();
        dbConn.connect();
        
        userTransIDs = dbConn.fetch(getUserTransactionsIDsQuery);
        
        // open up the returned IDs and               
        for(int transIDsNo = 0, s = userTransIDs.size();
                transIDsNo < s ; transIDsNo ++)
        {            
            String [] results = new String[1] ;
            results = (String[]) userTransIDs.get(transIDsNo) ;
            transIDs[transIDsNo] 
                    = Integer.parseInt(results[0]) ;            
        }                               
        
        return transIDs ;
    }

    /**
     * This method populates the transactions list for a
     * specific user for printing
     * @param currentUserID
     * @return 
     */
    public Object[][] getDetailedUserTransactions(int currentUserID) 
    {
        String [][] detailedTransactionDetails = null ;
        Vector detailedTransDetails = new Vector() ;
        
        getDetailedTransactionDetails = "SELECT s.day,s.month, s.year, (SELECT  "
                + "items_name FROM items AS i WHERE i.items_id = c.items_id)  "
                + " AS item_name , items_number, (SELECT "
                + " items_number*items_cost  FROM items AS k "
                + " where k.items_id = c.items_id) AS total_items_cost "
                + " FROM credit_transactions AS c, (SELECT "
                + " transaction_id,transaction_type, day, month, year FROM "
                + " transactions where customers_id= "
                + currentUserID
                + " ) AS s WHERE "
                + " c.transaction_id = s.transaction_id ";
        
        dbConn = new DatabaseConnection();
        dbConn.connect();
        
        detailedTransDetails = dbConn.fetch(getDetailedTransactionDetails);
        
        // initialise the returned multi dimensional array
        detailedTransactionDetails = new String[detailedTransDetails.size()][] ;
        
        for(int transIDsNo = 0, s = detailedTransDetails.size();
                transIDsNo < s ; transIDsNo ++)
        {            
            // every row returns data in the format 
            // [ day, month, year, item_name, items_number, total_items_cost ]
            String [] results  ;
            results = (String[]) detailedTransDetails.get(transIDsNo) ;
            detailedTransactionDetails[transIDsNo] = results ;
        } 
         
        return detailedTransactionDetails ;
    }
    
    /**
     * This method adds a new user's name and 
     * path to the avatar to the database
     * @param username
     * @param avatarPath 
     */
    public boolean setNewUserDetails(String username, String avatarPath) throws SQLException
    {
        // get the current day, month & year
        Calendar cal = Calendar.getInstance(); 
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) ;
        int year = cal.get(Calendar.YEAR);
        
        setNewUserDetailsQuery = "INSERT into CUSTOMERS (customers_firstname, "
                + " customers_secondname, images_name, joining_day, "
                + " joining_month,year) values (\"" 
                + username
                + "\", \" \", \""
                + avatarPath
                + "\","
                + day 
                + ","
                + month
                + ","
                + year
                + ")";
        
        dbConn = new DatabaseConnection();
        dbConn.connect();
        
        boolean result = dbConn.update(setNewUserDetailsQuery);        
       
        return result ;
    }
    
    /**
     * This method deletes a user and all transactions related to
     * the user from the database
     * @param userID
     * @return 
     */
    public boolean deleteUserAndDetails(int userID) throws SQLException
    {
        deleteUserDetailsQuery = new String[5];
        
        // delete data in debit_transactions table
        deleteUserDetailsQuery[0] = "DELETE FROM debit_transactions WHERE "
                + "debit_transactions.transaction_id IN (SELECT transaction_id "
                + " FROM transactions WHERE customers_id = "
                + userID
                + ") " ;
    
        // delete data in credit transactions table
        deleteUserDetailsQuery[1] = "DELETE FROM credit_transactions WHERE "
                + "credit_transactions.transaction_id IN (select "
                + "transaction_id FROM transactions WHERE customers_id = "
                + userID
                + ")" ;
                
        // delete data from other tables
        deleteUserDetailsQuery[2] = "DELETE FROM customers WHERE customers_id = "
                + userID ;
        
        // Delete from the transactions query
        deleteUserDetailsQuery[3] = "DELETE FROM transactions WHERE customers_id = "
                + userID ;
        
        // delete from the credit query
        deleteUserDetailsQuery[4] = "DELETE FROM credit WHERE customers_id = "
                + userID ;
        
        
        // run all the queries
        for(int i= 0 ; i < deleteUserDetailsQuery.length ; i ++)
        {
            dbConn = new DatabaseConnection();
            dbConn.connect();

            boolean result = dbConn.update(deleteUserDetailsQuery[i]);
            
            if(false == result)
            {
                return false ;
            }
        }
        
        // everything OK
        return true ;
    }
    
    /**
     * This method queries the database and returns the actual credit limit
     * by which a customer as specified by the custID is in credit or debt
     * @param custID
     * @return 
     */
    public double getCustCreditOrDebitAmount(int custID)
    {
        double totalExpenditure = 0 ;
        double creditOrDebitAmount  = 0 ;
        
        // get the user's total expenditure
        getTotalExpenditureQuery = "SELECT total_items_cost FROM (SELECT transaction_id, "
                + "(c.items_number*items_cost) AS total_items_cost FROM items AS "
                + "k, credit_transactions AS c WHERE k.items_id = c.items_id) AS t"
                + ", (SELECT transaction_id,transaction_type FROM transactions "
                + "WHERE customers_id="
                + custID
                + ") AS s WHERE "
                + "t.transaction_id = s.trans"
                + "action_id " ;
        
        // get the total expenditure        
        Vector expenditureAmount = null ;
        
        dbConn = new DatabaseConnection();
        dbConn.connect();
        
        expenditureAmount = dbConn.fetch(getTotalExpenditureQuery);
        
        // open up the returned values and 
        // return the user names in the desired 
        // format [firstname lastname]
        for(int counter = 0, s = expenditureAmount.size();
                counter < s ; counter ++)
        {
            
            String [] results = new String[1] ;
            results = (String[]) expenditureAmount.get(counter) ;
            totalExpenditure = Double.parseDouble(results[0]) ;                                                             
        }
        
        // get the user's credit limit
        getCreditAmountQuery = "SELECT credit_allowed FROM credit "
                + "WHERE customers_id = "
                + custID;
        
        // get the total Credit limit        
        Vector creditAmount = null ;
        
        dbConn = new DatabaseConnection();
        dbConn.connect();
        
        creditAmount = dbConn.fetch(getCreditAmountQuery);
        
        // open up the returned values and 
        // return the user names in the desired 
        // format [firstname lastname]
        for(int counter = 0, s = creditAmount.size();
                counter < s ; counter ++)
        {
            
            String [] results = new String[1] ;
            results = (String[]) creditAmount.get(counter) ;
            creditOrDebitAmount = Double.parseDouble(results[0]) ;
 
        }
        
        // get the difference to see if the customer is 
        // underspending or overspending              
        return creditOrDebitAmount - totalExpenditure ;
    }        
}