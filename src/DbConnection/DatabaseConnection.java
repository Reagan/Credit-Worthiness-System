/**
 * Credit Worthiness System Version 1.0
 */
package DbConnection;

import AppProperties.AppProperties;
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
    // retrieve the database connection details from the 
    // config file -- defaults to connection on H2 embedded database
    private String user = null  ;    
    private String password = null ;            
    private String dbDriver = ""  ;        
    private String databaseURL = "" ;
    
    private String databaseName = null ;
    private String databaseHost = null ;
    private String databasePort = null ;
    
    public Connection connection = null;       
    private Statement statement = null ;
    private DatabaseConnection dbConn = null ;
    private ResultSet result = null ;
    
    private int mostRecentTransactionID = -1 ;
    
    public DatabaseConnection()
    {
       if ( dbDriver.equals("") && databaseURL.equals(""))
       {
           initialiseDbObect() ;
       }
    }
    
    
    public boolean connect()
    {
        try
        {
            Class.forName(dbDriver);
            connection = DriverManager.getConnection(databaseURL,user,password);
            
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

    private void initialiseDbObect() 
    {
     // set the user and the password
        user = (AppProperties.getInstance().getValueOf(AppProperties.DB_USERNAME).equals(""))?
            "sa" : (AppProperties.getInstance().getValueOf(AppProperties.DB_USERNAME))  ;
    
        password = (AppProperties.getInstance().getValueOf(AppProperties.DB_PASSWORD).equals(""))?
            "" : (AppProperties.getInstance().getValueOf(AppProperties.DB_USERNAME)) ;
    
        // get the database host, name & port
        databaseName = (AppProperties.getInstance().getValueOf(AppProperties.DB_NAME).equals(""))?
                "database" : (AppProperties.getInstance().getValueOf(AppProperties.DB_NAME)) ;
        databaseHost = (AppProperties.getInstance().getValueOf(AppProperties.DB_HOST).equals(""))?
                "localhost" : (AppProperties.getInstance().getValueOf(AppProperties.DB_HOST)) ;
        databasePort = (AppProperties.getInstance().getValueOf(AppProperties.DB_PORT).equals(""))?
                "8082" : (AppProperties.getInstance().getValueOf(AppProperties.DB_PORT)) ;
                
        // set the db DRIVER
        String dbType = (AppProperties.getInstance().getValueOf(AppProperties.DB_TYPE)) ; 
        if ( dbType.equals("") || dbType.equals("h2") )
        {
                dbDriver = "org.h2.Driver" ;
                databaseURL = "jdbc:h2:runtime_required" + File.separator + databaseName;
        }
        else if ( dbType.equals("MySQL") )
        {
            dbDriver = "com.mysql.jdbc.Driver" ;
            databaseURL = "jdbc:mysql://" + databaseHost+ ":" +databasePort+"/" +databaseName ; 
        }    
    }
}
