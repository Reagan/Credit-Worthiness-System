/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AppActions;

import UI.EditItemsPanel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 * This class creates a new instance of an item to be added to the database 
 * as well as the details for the item
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class CreateItemAction extends AbstractedAction 
{
    private EditItemsPanel mPanel = null ;
    
    public CreateItemAction(EditItemsPanel panel)
    {
        mPanel = panel ;
    }
    
    @Override
    public void run()
    {
        // first if there are any pending unsaved items details being
        // modified and ask user to save the item details or not
        if ( mPanel.dirty )
        {
            int selection = JOptionPane.showConfirmDialog(mPanel, "Do you wish to save the item"
                    + "details that are currently being selected first?", "Save transaction details"
                    , JOptionPane.YES_NO_CANCEL_OPTION) ;
            
            if ( selection == JOptionPane.CANCEL_OPTION )
            {
                return ;
            }
            if ( selection == JOptionPane.YES_OPTION )
            {
                // save the currently edited transaction details
                mPanel.saveCurrentItemDetails();
            }
        }
        
        // reset the currently selected item
        mPanel.currentlySelectedItemId = -1; 
        
        // clear the text fields 
        mPanel.itemName.setText("<Enter New Item Name>");
        mPanel.itemCost.setText("<Enter New Item Cost>");

        // enable the editing of the item details
        mPanel.itemName.setEditable(true);
        mPanel.itemCost.setEditable(true);
        
        // enable the save button
        mPanel.saveItemAction.setEnabled(true);                                    
    }
}
