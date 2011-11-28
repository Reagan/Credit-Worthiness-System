/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import java.util.Vector;
import javax.swing.AbstractListModel;

/**
 *
 * @author rmbitiru
 */
public class UserTransacationsModel extends AbstractListModel
{

    // create the object to store the data for the 
    // transactions JList
    
    // @TODO : make sure that this JList is populated programmatically
    private int currentUserID ; // current User ID's transactions
    
    private Vector <String> transactions ; // list of all the transactions for 
                                // the JList
    
    public UserTransacationsModel(int userID)
    {
        currentUserID = userID ;
                
        // populate the transactions list
        transactions = populateTransactionsList() ;
    }
    
    @Override
    public int getSize() 
    {
        return transactions.size();
    }

    @Override
    public Object getElementAt(int index) 
    {
        return (String) transactions.get(index) ;
    }
    
    /**
     * populates the transaction list for the 
     * user's transactions
     */
    public Vector populateTransactionsList()
    {
        transactions = new Vector <String> ();
        
        transactions.add("Milk, 20th Jan 2011") ;
        transactions.add("Butter, 22nd Jan 2011") ;
        transactions.add("Syrup, 24th Jan 2011");
        transactions.add("Bread, 1st Feb 2011 ");
        
        return transactions ;
    }
    
}
