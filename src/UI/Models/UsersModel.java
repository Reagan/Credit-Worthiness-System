/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Models;

import java.util.HashMap;
import java.util.Vector;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author rmbitiru
 */
public class UsersModel extends AbstractListModel
    implements ComboBoxModel
{       
    // @TODO: make this obtained from the database at each change
    private Vector <String> users  ;
    
    // used by the ComboBox Model to determine the
    // selected Item
    private Object selectedItem ;
     
    public UsersModel(Vector usersNames)
    {                      
        // get the usernames for which the IDs belong to
        users = usersNames ;
    }
    
    @Override
    public int getSize() 
    {
        return users.size();
    }

    @Override
    public Object getElementAt(int index) 
    {       
        return users.get(index);
    }

    @Override
    public void setSelectedItem(Object anItem) 
    {
        // append the selected item to the 
        // object model
        selectedItem = anItem ;
    }

    
    @Override
    public Object getSelectedItem() 
    {
        // return the id for the user
        // rather than the name for the user
        // since the ID is always unique...
        // the name may not         
        return selectedItem;
    }
}
