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
                + ") AS s ";        
        
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
                + userID ;        
                
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
}
