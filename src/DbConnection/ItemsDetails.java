/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DbConnection;

import java.util.Vector;

/**
 * This class obtains information about Items 
 * from the database
 * @author rmbitiru
 */
public class ItemsDetails 
{
    private DatabaseConnection dbConn = null ;
    private String getItemNamesQuery = null ;
    
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
}
