/**
 * Credit Worthiness System Version 1.0
 */
package DbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class DatabaseConnection 
{
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String DATABASE_URL = "jdbc:mysql://localhost/cws";
    public Connection connection = null;
    private String user = "root";
    private String password = "reagan7351";
    
    private Statement statement = null ;
    private DatabaseConnection dbConn = null ;
    private ResultSet result = null ;
    
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
    
    public Vector fetch(String SQLStatement)
    {               
        Vector retResults = new Vector();
        
        try 
        {       
            // create a statement for connecting to the
            // database
            statement = connection.createStatement() ;
            
            // query the database
            statement.executeQuery(SQLStatement);
            
            result = statement.getResultSet();
             
            // return the query results
            ResultSetMetaData metaData = result.getMetaData();
            int noOfColumns = metaData.getColumnCount() ;
            
            while(result.next())
            {       
                String [] innerRes = new String[noOfColumns] ;
                
                
                for(int resCounter = 1; resCounter <= noOfColumns; 
                        resCounter ++)
                {                      
                    innerRes[resCounter-1] = result.getObject(resCounter).toString() ;                    
                }
                 
                retResults.add(innerRes) ;
            }                                
        } 
        catch (SQLException ex) 
        {
            System.out.println("Error: [SQL Statement " + SQLStatement + "] " 
                    + ex.toString());
        }
        finally
        {
            try 
            {
                // clean up everything
                result.close();
                statement.close(); 
                connection.close();
            } 
            catch (SQLException ex) 
            {
                System.out.println("Error: " + ex.toString());
            }            
        }          
        return retResults ;
    }
}
