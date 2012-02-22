/**
 * Credit Worthiness System Version 1.0
 */
package DbConnection;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * 
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
/**
 * !+ ACTION_RECONF - adding support for using H2 database
 * @author reagan
 */
public class DatabaseConnection 
{
    // private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String DRIVER = "org.h2.Driver" ;
    
    // private final String DATABASE_URL = "jdbc:mysql://localhost/cws";
    private final String DATABASE_URL = "jdbc:h2:runtime_required" + File.separator + "database";
    
    public Connection connection = null;
    //private String user = "root";
    private String user = "sa";
    // private String password = "reagan7351";
    private String password = "";
    
    private Statement statement = null ;
    private DatabaseConnection dbConn = null ;
    private ResultSet result = null ;
    
    private int mostRecentTransactionID = -1 ;
    
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
                    innerRes[resCounter-1] = (String) result.getObject(resCounter).toString() ;                     
                }
                 
                retResults.add(innerRes) ;
            }                                
        } 
        catch (SQLException ex) 
        {
            System.out.println("Error: [SQL Statement " + SQLStatement + "] " 
                    + ex.toString());
        }
          
        return retResults ;
    }
    
    /** This method updates a database transaction and gets the 
     * most recent transaction details
     * @param SQLStatement
     * @return
     * @throws SQLException 
     */
    public boolean update(String SQLStatement)
    {
         try 
        {       
            // create a statement for connecting to the
            // database
            statement = connection.createStatement() ;
            
            // update the database
            statement.executeUpdate(SQLStatement); 
            
            // get the most recent transaction ID
            ResultSet rs = statement.getGeneratedKeys() ;
            if(null != rs)
            {
                while (rs.next())
                {
                    mostRecentTransactionID = rs.getInt(1) ;
                }
            }
        } 
        catch(SQLException ex) 
        {
             System.out.println("SQLException: " + ex.getMessage());
             return false; 
        }
         
         return true; 
    }
    
    public boolean insert(String SQLStatement)
    {
         try 
        {       
            // create a statement for connecting to the
            // database
            statement = connection.createStatement() ;
            
            // update the database
            statement.executeUpdate(SQLStatement); 
            
            // get the most recent transaction ID
            ResultSet rs = statement.getGeneratedKeys() ;
            if(null != rs)
            {
                while (rs.next())
                {
                    mostRecentTransactionID = rs.getInt(1) ;
                }
            }
        } 
        catch(SQLException ex) 
        {
             System.out.println("SQLException: " + ex.getMessage());
             return false; 
        }
         
        return true; 
    }
    
    /**
     * This method closes the database connection and 
     * destroys the various objects associated with it. 
     * This method must be called for all instances where a
     * database object is created
     */
    public void closeDatabaseConnection() throws SQLException
    {
        statement.close();
        connection.close();
    }
    
    
    /**
     * This method obtains the most recent transaction ID
     * @return 
     */
    public int getMostRecentTransactionID()
    {
        return mostRecentTransactionID ;
    }
}
