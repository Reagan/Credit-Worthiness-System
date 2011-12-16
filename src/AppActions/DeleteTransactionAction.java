/**
 * Credit Worthiness System Version 1.0
 */
package AppActions;

import javax.swing.JOptionPane;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class DeleteTransactionAction extends AbstractedAction 
{
    private String appMessage ;
    private String aboutDialogTitle ;
    private int messageType ;
    
    public DeleteTransactionAction()
    {
        aboutDialogTitle = "Delete Transaction";
        appMessage = "Are you sure that you want to delete the transaction?";
        messageType = JOptionPane.YES_NO_OPTION;
    }

    @Override
    public void run() 
    {
        JOptionPane.showConfirmDialog(null, appMessage, 
                aboutDialogTitle, messageType);
    }  
}
