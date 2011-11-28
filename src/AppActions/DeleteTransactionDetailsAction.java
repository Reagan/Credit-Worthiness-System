/**
 * Credit Worthiness System Version 1.0
 */
package AppActions;

import javax.swing.JOptionPane;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class DeleteTransactionDetailsAction extends AbstractedAction
{
    private String appMessage ;
    private String aboutDialogTitle ;
    private int messageType ;
    
    public DeleteTransactionDetailsAction()
    {
        aboutDialogTitle = "Delete Transaction Details";
        appMessage = "Are you sure that you want to delete all the transaction's details?";
        messageType = JOptionPane.YES_NO_OPTION;
    }

    @Override
    public void run() 
    {
        JOptionPane.showConfirmDialog(null, appMessage, 
                aboutDialogTitle, messageType);
    }  
}
