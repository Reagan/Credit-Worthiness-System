/**
 * Credit Worthiness System Version 1.0
 */
package DbConnection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class DatabaseConnection 
{
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String DATABASE_URL = "jdbc:mysql://localhost/cws";
    private Connection connection = null;
    private String user = "root";
    private String password = "reagan7351";
    
    public DatabaseConnection(){}
    
    public boolean connect()
    {
        try
        {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL,user,password);
            
            if(null != connection)
            {
                return true;
            }
        }
        catch(Exception ce)
        {}
        
        return false;
    }
}
