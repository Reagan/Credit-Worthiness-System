/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIModels;

import java.util.HashMap;
import java.util.Vector;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author rmbitiru
 */
public class UsersModel extends AbstractListModel
    implements ComboBoxModel
{
    // create the object to store 
    // a user identifier and user names
    // @TODO: will be populated by database access code
    private Vector <Integer> userIDs ;
    private Object selectedItem ;
    
    // @TODO: make this obtained from the database at each change
    private Vector <String> users  ;
    
    public UsersModel()
    {
        // initialise the HashMap
        userIDs = new Vector <Integer>() ;        
        
        // get the user IDs
        userIDs = getUserIDs();
        
        // get the usernames for which the IDs belong to
        users = populateUsersData(userIDs);
    }
    
    @Override
    public int getSize() 
    {
        return userIDs.size();
    }

    @Override
    public Object getElementAt(int index) 
    {
        return users.get(index) ;
    }

    @Override
    public void setSelectedItem(Object anItem) 
    {
        // append the selcted item to the 
        // object model
        selectedItem = anItem ;
    }

    @Override
    public Object getSelectedItem() 
    {
        return selectedItem ;
    }

    /**
     * This method returns the names of the
     * users with user IDs obtained
     * @param userIDs the IDs of the all the users
     * this ID will be unique even if the names are not
     */
    private Vector<String> populateUsersData( Vector<Integer> userIDs ) 
    {
        users = new Vector<String>();
        users.add("Angie Muthoni") ;
        users.add("Reagan Mbitiru") ;
        
        return users ;
    }

    private Vector<Integer> getUserIDs() 
    {
        userIDs = new  Vector<Integer>();
        userIDs.add(1234);
        userIDs.add(3456);
        
        return userIDs ;
    }
}
