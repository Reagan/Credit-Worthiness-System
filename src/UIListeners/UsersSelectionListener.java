/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIListeners;

import UI.BottomCenterPanel;
import UI.LeftPanel;
import credit.worthiness.system.CreditWorthinessSystem;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * This class is activated when the JComboBox is selected
 * and performs the following actions
 *      1. Changes the Image on the left Panel
 *      2. Changes the label below the image & date of joining
 *      3. Specifies the current user as the indicated user
 *      4. Set the Model for the JList with the user's model
 *      5. Sets the model of the Chart with the
 *          transaction details of the user
 * @author rmbitiru
 */
public class UsersSelectionListener implements ActionListener
{   
    // @TODO : this should be determined dynamically     
    private int userID = 1;
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(SwingUtilities.isEventDispatchThread())
        {
            // make sure that the user really wants to change the
            // current user
            int resp = JOptionPane.showConfirmDialog(null, 
                    "Are you sure you want to change users?", 
                    "Change User", JOptionPane.YES_NO_OPTION) ;
            
            if(resp==JOptionPane.YES_OPTION)
            {
                // set the current User ID
                // @TODO: get the current user ID by query to the
                // database or call to the model
                CreditWorthinessSystem.specifyCurrentUserID(userID);
                
                try 
                {
                    // change the buffered Image, get the
                    // correct image for the user
                    // @TODO: this should actually come from
                    // the database
                    LeftPanel.setUserImage("1.gif");
                } 
                catch (IOException ex) 
                {
                    Logger.getLogger(UsersSelectionListener.class.getName()).log(Level.SEVERE, null, ex);
                }
        
                // change the label with the user's name
                ItemSelectable is = (ItemSelectable) e.getSource() ;
                LeftPanel.setUserDetails(selectedItem(is),userID);            
                
                // set the model for the JList with the list 
                // of transactions from the current user's model
                BottomCenterPanel.setUserTransactionsModel(userID);
                
            }
        }
    }

    private String selectedItem(ItemSelectable is) 
    {
        Object selected[] = is.getSelectedObjects() ;
        return (selected.length == 0) ? "null" : (String)selected [0];
    }
    
}
