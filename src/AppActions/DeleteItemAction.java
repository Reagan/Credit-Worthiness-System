/**
 * Credit Worthiness System Version 1.0
 */
package AppActions;

import DbConnection.ItemsDetails;
import UI.EditItemsPanel;
import javax.swing.JOptionPane;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class DeleteItemAction extends  AbstractedAction
{
    private EditItemsPanel mPanel ; 
    private ItemsDetails itemD ;
    
    public DeleteItemAction(EditItemsPanel panel)
    {
        mPanel =  panel ;
    }
    
    @Override
    public void run()
    {
        // ask the user if sure
        if ( JOptionPane.YES_OPTION ==  JOptionPane.showConfirmDialog(mPanel, "Are you sure that you want"
                + " to delete the item?", "Confirm Deletion", JOptionPane.YES_NO_OPTION)) 
        {
            // create database object and pass the item id to it
            itemD = new ItemsDetails() ;
            
            // make sure that the item is not used in a transaction
            // for any of the users
            if ( isItemUsed(mPanel.currentlySelectedItemId) )
            {
                JOptionPane.showMessageDialog(mPanel, "Sorry, that item is used"
                        + " in "
                        + ItemsDetails.usedInTransNumber
                        + " transactions. Please delete the transactions using this item "
                        + "and try again", "Item used", JOptionPane.ERROR_MESSAGE) ;
                
                return ;
            }
            
            // delete the item
            if ( itemD.deleteItem(mPanel.currentlySelectedItemId) )
            {
                // clear the currently selected item
                mPanel.currentlySelectedItemId = -1 ;

                // clear the JTextFields with the item's details 
                mPanel.itemName.setText("");
                mPanel.itemCost.setText("");
                
                // make them uneditable
                mPanel.itemName.setEditable(false) ;
                mPanel.itemCost.setEditable(false) ;

                // update the JList
                mPanel.revalidateItemsList();

                // alert user
                JOptionPane.showMessageDialog(null, "The item was successfully deleted", 
                        "Item details deleted successfully", JOptionPane.INFORMATION_MESSAGE); 

            }                
            else
            {
                JOptionPane.showMessageDialog(null, "There was an error deleting the item. "
                        + "Please try again", "Error deleting item", JOptionPane.ERROR_MESSAGE);
            }
        }  
    }

    /**
     * This method checks as to whether a particular item is being 
     * used in any of the transactions
     * @param currentlySelectedItemId
     * @return 
     */
    private boolean isItemUsed(int currentlySelectedItemId) 
    {
        return itemD.isItemUsed(currentlySelectedItemId) ;
    }
}
