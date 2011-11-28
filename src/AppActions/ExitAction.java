/**
 * Credit Worthiness System Version 1.0
 */
package AppActions;

import javax.swing.JOptionPane;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class ExitAction extends AbstractedAction
{
    // @TODO need to verify the state of the application
    // before closing the application
    private String appMessage ;
    private String aboutDialogTitle ;
    private int messageType ;
    
    public ExitAction()
    {
        aboutDialogTitle = "Exit Application?";
        appMessage = "Are you sure that you want to exit this application?";
        messageType = JOptionPane.YES_NO_OPTION;
    }

    @Override
    public void run() 
    {
        int res = JOptionPane.showConfirmDialog(null, appMessage, 
                aboutDialogTitle, messageType);
        
        if(res == JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }      
}
