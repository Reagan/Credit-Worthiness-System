/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AppActions;

import UI.EditItemsPanel;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author undesa
 */
public class CloseItemsPanelAction extends AbstractedAction
{
    private EditItemsPanel mPanel ;
    
    public CloseItemsPanelAction(EditItemsPanel panel)
    {
        mPanel = panel ;
    }
    
    public void run()
    {                
        // check if there is a currently unsaved item being edited
        if ( mPanel.dirty )
        {
            int selection = JOptionPane.showConfirmDialog(mPanel, "Do you want to"
                    + " save the currently edited item before closing this panel", "Save Item Details",
                    JOptionPane.YES_NO_CANCEL_OPTION) ;

            if ( selection ==  JOptionPane.CANCEL_OPTION )
            {
                return ;
            }
            else if ( selection == JOptionPane.YES_OPTION )
            {
                mPanel.saveCurrentItemDetails();
            }
        }
        
        // close the frame
        mPanel.mDialog.dispose();        

    }
}
