/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DbConnection;

import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * This class obtains information about Items 
 * from the database. To ensure support for both MySQL and 
 * H2 (embedded database), the CASE WHEN command is used as opposed 
 * to the IF command that is unsupported by H2 and other embedded databases
 * @author rmbitiru
 */
public class ItemsDetails 
{
    private DatabaseConnection dbConn = null ;
    private String getItemNamesQuery = null ;
    private String getItemDetails = null ;
    private String getItemDetailsQuery = null ;
    private String error ; // stores database errors
    private String deleteItemQuery = null ;
    private String insertNewItemDetails = null ;
    private String getTransactionsWhereItemIsUsed = null ; // determines if the item has been used
    
    public static int usedInTransNumber = 0; // stores the number of times that
                            // a specific item has been used in transactions
    
    public ItemsDetails() {}
    
    /**
     * This method returns the names of the 
     * items from the database and passes them 
     * over in the same order as they appear
     * @return 
     */
    public Vector getItemNames()
    {
        Vector results = new Vector() ;
         
        getItemNamesQuery = "SELECT items_name FROM items" ;    
        
        Vector itemNamesDetails = new Vector() ;
        
        dbConn = new DatabaseConnection();
        dbConn.connect();
        
        itemNamesDetails = dbConn.fetch(getItemNamesQuery);        
         
        // open up the returned values and 
        // return the user names in the desired 
        // format [firstname lastname]
        for(int itemsNo = 0, s = itemNamesDetails.size();
                itemsNo < s ; itemsNo ++)
        {            
            String is []= (String[]) itemNamesDetails.get(itemsNo);         
            
            // add the results to the vector
            results.add(is[0]) ; 
        }
        
        return results ;
    }
    
    /**
     * This method returns the ID, NAME and Cost of all the items 
     * that are stored in the database
     * @return 
     */
    public Vector getAllItemsDetails()
    {
        Vector items  = new Vector() ;
        
        getItemDetails = "SELECT * FROM items" ;
        
        Vector itemsDetails = new Vector() ;
        
        dbConn = new DatabaseConnection() ;
        dbConn.connect();
        
        itemsDetails = dbConn.fetch(getItemDetails) ;
        
        // add the query results to the main vector
        for( int i = 0, s= itemsDetails.size() ; i < s ; i ++ )
        {
            items.add(itemsDetails.get(i));
        }
        
        return items ;
        
    }
    
    /**
     * This method obtains the details for a specific item i.e. the name and cost for 
     * the item
     * @param itemName
     * @return Name & Cost for specific item
     */
    public String[] getItemDetails (String itemName)
    {        
        Vector itemsDetails = null ;
        
        getItemDetailsQuery = "SELECT * FROM items WHERE items_name = '"
                + itemName
                + "'" ;
        
        dbConn = new DatabaseConnection() ;
        dbConn.connect();
        
        itemsDetails = dbConn.fetch(getItemDetailsQuery) ;
        String [] itemDetails = new String[3] ;
        
        // get the items details and add them to string array
        for (int i = 0, s = itemsDetails.size(); i < s ; i++ )
        {
            String[] currItem = (String[]) itemsDetails.get(i) ;
            itemDetails [0] = (String) currItem[0] ; // item_id
            itemDetails [1] = (String) currItem[1] ; // item_name 
            itemDetails [2] = (String) currItem[2] ; // item_cost
        }
        
        return itemDetails ;
    }
    
    /**
     * This method saves the current item details 
     * @param itemId
     * @param itemName
     * @param itemCost
     * @return 
     */
    public boolean updateItemDetails(int itemId, String itemName, int itemCost)
    {
        boolean result = false ;
        
        // populate the item details into SQL query
        String saveItemDetailsQuery = "UPDATE items SET items_name = '"
                + itemName
                + "', items_cost ="
                + itemCost
                + " WHERE items_id="
                + itemId ;
        
        // run the query in the db
        dbConn = new DatabaseConnection() ;
        dbConn.connect();
        
        if ( !dbConn.update(saveItemDetailsQuery) )
        {
            JOptionPane.showMessageDialog(null, "There was an error updating the "
                    + "transaction details. "
                    + "Please try again1", "Error updating!", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "The item details were updated successfully", 
                    "Item Details updated", JOptionPane.INFORMATION_MESSAGE);
            return true ;
        }
        
        return result ;
    }
    
    /**
     * This method inserts new item details into databases
     * @param itemName
     * @param itemCost 
     */
    public boolean insertNewItemDetails(String itemName, int itemCost)
    {
        insertNewItemDetails = "INSERT INTO items (items_name, Items_cost) VALUES ('"
                + itemName
                + "', "
                + itemCost
                +")"; 
        
        // run the query in the db
        dbConn = new DatabaseConnection() ;
        dbConn.connect();
        
        return dbConn.insert(insertNewItemDetails) ;
    }
    
    /**
     * This method deletes the details for a specific item from the dbs
     * @param itemId
     * @return 
     */
    public boolean deleteItem(int itemId)
    {        
        deleteItemQuery = " DELETE FROM items WHERE items_id="
                + itemId ;
        
        dbConn = new DatabaseConnection() ;
        dbConn.connect();
        
        return dbConn.update(deleteItemQuery) ;                
    }
    
    /**
     * this method returns any error that may have been encoutered before
     * or while processing a database transaction
     * @return 
     */
    public String getError()
    {
        return error ;
    }
    
    /** 
     * This method determines whether or not a particular item is being
     * used or not
     * @param itemsId
     * @return 
     */
    public boolean isItemUsed(int itemsId)
    {
        String[] usedTransNumber = null;
        Vector usedTransDetails = new Vector() ;
        
        getTransactionsWhereItemIsUsed = "SELECT COUNT(*) FROM credit_transactions "
                + "WHERE items_id = "
                + itemsId ; 
        
        dbConn = new DatabaseConnection() ;
        dbConn.connect();
        
        usedTransDetails = dbConn.fetch(getTransactionsWhereItemIsUsed);
        
        for(int usedTransNo = 0, s = usedTransDetails.size();
                usedTransNo < s ; usedTransNo ++)
        {
            usedTransNumber = (String[]) usedTransDetails.get(usedTransNo) ;            
        }
        
        // assign the number of times that the item has been used
        usedInTransNumber = Integer.parseInt(usedTransNumber[0]) ;
        
        // format the response
        if ( Integer.parseInt(usedTransNumber[0]) > 0 )
            return true ;
        else
            return false ;
    }
    
    public boolean isItemNameUsed(String itemName)
    {
         String[] usedTransNumber = null;
        Vector usedTransDetails = new Vector() ;
        
        getTransactionsWhereItemIsUsed = "SELECT COUNT(*) FROM items "
                + "WHERE items_name = '"
                + itemName 
                + "'"; 
        
        dbConn = new DatabaseConnection() ;
        dbConn.connect();
        
        usedTransDetails = dbConn.fetch(getTransactionsWhereItemIsUsed);
        
        for(int usedTransNo = 0, s = usedTransDetails.size();
                usedTransNo < s ; usedTransNo ++)
        {
            usedTransNumber = (String[]) usedTransDetails.get(usedTransNo) ;            
        }
        
        // assign the number of times that the item has been used
        usedInTransNumber = Integer.parseInt(usedTransNumber[0]) ;
        
        // format the response
        if ( Integer.parseInt(usedTransNumber[0]) > 0 )
            return true ;
        else
            return false ;
    }
}
