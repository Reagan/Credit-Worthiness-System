/**
 * Credit Worthiness System Version 1.0
 */
package DbConnection;

import UI.LeftPanel;
import java.util.Vector;

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
}
