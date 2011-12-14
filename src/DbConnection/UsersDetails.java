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
        String [] months =  {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov", "Dec" };
                
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
}
