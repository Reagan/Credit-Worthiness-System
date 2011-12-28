/**
 * Credit Worthiness System Version 1.0
 */
package AppActions;

import DbConnection.UsersDetails;
import UI.LeftPanel;
import credit.worthiness.system.CreditWorthinessSystem;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class DeleteUserAction extends AbstractedAction
{
    private String appMessage ;
    private String aboutDialogTitle ;
    private int messageType ;
    
    public DeleteUserAction()
    {
        aboutDialogTitle = "Delete User";
        appMessage = "Are you sure that you want to delete this user's details?";
        messageType = JOptionPane.YES_NO_OPTION;
    }

    @Override
    public void run() 
    {
        int result = JOptionPane.showConfirmDialog(null, appMessage, 
                aboutDialogTitle, messageType);
        
        if(result == JOptionPane.OK_OPTION)
        {
            // get the next selected user's index
            int nextUser =  CreditWorthinessSystem.getCurrentUserID() + 1;
            
            // delete the currently selected user's
            // details (transactions & credit limit)
            System.out.println("Deleting Current User: " + CreditWorthinessSystem.getCurrentUser()
                    + ", "
                    + " User ID: " + CreditWorthinessSystem.getCurrentUserID()
                    + " details") ;
            
            UsersDetails usersInfo = new UsersDetails() ;
            try 
            {
                boolean res = usersInfo.deleteUserAndDetails(
                        CreditWorthinessSystem.getCurrentUserID());
                
                if(false == res)
                {
                    JOptionPane.showMessageDialog(null, "There was an error deleting the user. "
                            + " Please try again", "Error", JOptionPane.PLAIN_MESSAGE);
                }
                else
                {
                    // change the models to the first user
                    // in the JComboBox
                    // to the first person on the model
                    JOptionPane.showMessageDialog(null, "Deleting user " 
                            + CreditWorthinessSystem.getCurrentUser()
                            + " Completed.", "Success", JOptionPane.PLAIN_MESSAGE);
                    
                    LeftPanel.setSelectedIndex(1);
                }
            } 
            catch (SQLException ex) 
            {
                System.out.println("Error: " + ex.toString()) ;
            }
            
            // trigger the action listener to the next user
            
        }
    }  
}
