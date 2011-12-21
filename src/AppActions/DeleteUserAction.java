/**
 * Credit Worthiness System Version 1.0
 */
package AppActions;

import credit.worthiness.system.CreditWorthinessSystem;
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
            // details
        }
    }  
}
