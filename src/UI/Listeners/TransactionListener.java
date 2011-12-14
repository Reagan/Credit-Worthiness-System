/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Listeners;

import UI.BottomRightPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * This class detects the selection events on the transactions list
 * and displays the components on the transactions content on 
 * the bottom left of the app
 * 
 * @author rmbitiru
 */
public class TransactionListener implements ListSelectionListener
{
    @Override
    public void valueChanged(ListSelectionEvent e) 
    {
         if(SwingUtilities.isEventDispatchThread())
        {
            // get the selected transaction ID
            
            // populate the fields on the bottom left 
            // of the application
            BottomRightPanel.setTransactionDetails(0);
        }
    }    
}
