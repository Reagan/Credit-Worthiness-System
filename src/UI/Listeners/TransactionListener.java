/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Listeners;

import UI.BottomCenterPanel;
import UI.BottomRightPanel;
import UI.Models.UserTransactionsModel;
import javax.swing.JList;
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
    private int currentlySelectedTransactionID ; 
    
    @Override
    public void valueChanged(ListSelectionEvent e) 
    {
         if(SwingUtilities.isEventDispatchThread())
        {
            // get the selected transaction ID
            //
            // make sure that the selected value on
            // the JList is not adjusting
            boolean adjusting = e.getValueIsAdjusting() ;
            
            // if not adjusting, then get the selected index
            if(false == adjusting)
            {
                // populate the fields on the bottom left 
                // of the application with details on the 
                // transaction
                JList list = (JList) e.getSource() ;
                
                int selection = list.getSelectedIndex();                
                
                // now obtain the transaction ID for 
                // the selected item
                currentlySelectedTransactionID = 
                        UserTransactionsModel.transIDs[selection] ;                
                BottomRightPanel
                        .setTransactionDetails(UserTransactionsModel.transIDs[selection]);
                
                // enable the delete transaction button
                BottomCenterPanel.deleteButton.setEnabled(true);
            }                       
        }
    }    
}
