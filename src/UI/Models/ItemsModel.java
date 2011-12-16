/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Models;

import java.util.Vector;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author rmbitiru
 */
public class ItemsModel extends AbstractListModel
    implements ComboBoxModel
{
    private Vector itemsList ; 
    
    // used by the ComboBox Model to determine the
    // selected Item
    private Object selectedItem ;
    
    public ItemsModel(Vector items)
    {
        itemsList = items ;
    }
    
    @Override
    public int getSize() 
    {
        return itemsList.size() ;
    }

    @Override
    public Object getElementAt(int index) 
    {
        return itemsList.get(index) ;
    }

    @Override
    public void setSelectedItem(Object anItem)
    {
        selectedItem = anItem ;
    }

    @Override
    public Object getSelectedItem() 
    {
        return selectedItem; 
    }
}
