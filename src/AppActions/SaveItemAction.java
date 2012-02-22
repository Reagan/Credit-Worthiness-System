/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AppActions;

import DbConnection.ItemsDetails;
import UI.EditItemsPanel;
import javax.swing.JOptionPane;

/**
 * This class saves the currently edited item to the database. It may save currently 
 * edited item details or add new item details
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class SaveItemAction extends AbstractedAction
{
    private EditItemsPanel mPanel = null ;
    public static final int NEW_ITEM_EDITED = 1 ;
    public static final int OLD_ITEM_EDITED = 2 ;
    
    public static int itemTypeBeingEdited = OLD_ITEM_EDITED ;
    private ItemsDetails itemsD ;
    
    public SaveItemAction(EditItemsPanel panel)
    {
        mPanel = panel ;
    }
    
    @Override
    public void run()
    {
        // create database instance
        itemsD = new ItemsDetails() ;
        
        // check if current item details or new item details are 
        // being edited
        itemTypeBeingEdited = ( EditItemsPanel.currentlySelectedItemId == -1 ) ? NEW_ITEM_EDITED  : 
                OLD_ITEM_EDITED ;
        
        // get the item details from the text fields 
        if ( mPanel.itemName.getText().length() < 0 || 
                Integer.parseInt(mPanel.itemCost.getText()) < 0 )
        {
            JOptionPane.showMessageDialog(mPanel, "Please fill in all required details for Item", 
                    "Enter All item Details", JOptionPane.ERROR_MESSAGE);
            return ;
        }                 
        
        // insert or update the item details into the database        
        String itemName = mPanel.itemName.getText().trim() ;
        int itemCost = Integer.parseInt(mPanel.itemCost.getText().trim());
        boolean result = false ;  // stores the result of updating the transaction details
        
        if ( itemTypeBeingEdited == NEW_ITEM_EDITED )
        {
            // check if the item name has been used before
            if ( itemsD.isItemNameUsed(mPanel.itemName.getText().trim()) )
            {
                JOptionPane.showMessageDialog(mPanel, "This item name has already been used! Please"
                        + " select another name", 
                        "Enter unique name", JOptionPane.ERROR_MESSAGE);
                return ;
            }
            
            result = itemsD.insertNewItemDetails(itemName, itemCost);
        }            
        else if ( itemTypeBeingEdited ==  OLD_ITEM_EDITED )
            result = itemsD.updateItemDetails(EditItemsPanel.currentlySelectedItemId, itemName, itemCost);
        
        
        // revalidate the JList
        if ( !result )
        {
            JOptionPane.showConfirmDialog(mPanel, "There was an error saving the Item details. "
                    + "Please try again", "Error saving the item", JOptionPane.ERROR_MESSAGE);
            return ;
        }        
         
        mPanel.revalidateItemsList() ;
        
        // set the text fields to null
        mPanel.itemCost.setText("") ;
        mPanel.itemName.setText("") ;
        
        // make them uneditable
        mPanel.itemCost.setEditable(false) ;
        mPanel.itemName.setEditable(false) ;
                
        // reset the index for the items panel
        EditItemsPanel.currentlySelectedItemId = -1 ;
        EditItemsPanel.currentlySelectedItem = null ;
        
        // reset the item type being edited
        itemTypeBeingEdited = OLD_ITEM_EDITED ;
        
        // alert the user that the new item details have been entered
        JOptionPane.showMessageDialog(mPanel, "The details for the item have "
                + "been entered successfully", "Item details saved", JOptionPane.INFORMATION_MESSAGE);
    }
}
