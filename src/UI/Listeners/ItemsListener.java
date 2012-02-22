/**
 * Credit Worthiness System Version 1.0
 */
package UI.Listeners;

import AppActions.SaveItemAction;
import DbConnection.ItemsDetails;
import UI.EditItemsPanel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * This class listens for changes in the selected item and updates the left panel
 * with details of the currently selected item
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class ItemsListener implements ListSelectionListener
{
    private EditItemsPanel panel ;
    
    public ItemsListener(EditItemsPanel editPanel)
    {
        panel = editPanel ;
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) 
    {
        // check if the user has saved the currently edited items
        if( EditItemsPanel.dirty )
        {
            int selection = JOptionPane.showConfirmDialog(panel, "Do you want to save the currently"
                    + " edited transaction?", "Save transaction", JOptionPane.YES_NO_CANCEL_OPTION) ;
            
            if ( JOptionPane.CANCEL_OPTION ==  selection )
                return ;
            else if ( JOptionPane.YES_OPTION ==  selection )
            {
                saveTransactionDetails() ;
            }
            
        }
        // make sure to do everything in the EDT            
        if(SwingUtilities.isEventDispatchThread())
        {
            boolean adjusting = e.getValueIsAdjusting() ;

            // if not adjusting, then get the selected index
            if(false == adjusting)
            {
                JList list = (JList) e.getSource() ;

                // get the selected item
                String selectedItem = (String) list.getSelectedValue();

                // cater for instances where the list of items
                // is revalidating
                if ( selectedItem.equals(null) )
                    return ;
                
                // make the textfields editable
                panel.itemName.setEditable(true);
                panel.itemCost.setEditable(true);
                
                // obtain the details of the item from the database
                // !+ This code should only be run when editing an exisitng item
                if ( SaveItemAction.itemTypeBeingEdited 
                        == SaveItemAction.OLD_ITEM_EDITED )
                {
                    ItemsDetails items = new ItemsDetails() ;
                    String[] itemDetails = items.getItemDetails(selectedItem) ;

                    // populate the fields with the transaction details                    
                    EditItemsPanel.currentlySelectedItemId = Integer.parseInt(itemDetails[0]) ;
                    panel.itemName.setText(itemDetails[1]);
                    panel.itemCost.setText(itemDetails[2]);
                }

                // enable the delete item button
                panel.deleteItemAction.setEnabled(true);
                
                // enable the save button
                panel.saveItemAction.setEnabled(true);

            }
        }
    }

    /**
     * This method saves transaction details before loading new
     * transaction details on the right side of the edit items panel
     */
    private void saveTransactionDetails() 
    {
        panel.saveCurrentItemDetails() ;
    }
}
