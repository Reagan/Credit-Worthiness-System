/**
 * Credit Worthiness System Version 1.0
 */
package AppActions;

import AppProperties.AppProperties;
import javax.swing.JOptionPane;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class AboutAction extends AbstractedAction
{
    private String appMessage = "Credit Worthiness System version 1.0 \n" +
            "was developed towards the completion of a \n " +
            "Masters Project";
    private String aboutDialogTitle = "About CWS vs 1.0";
    private int messageType = JOptionPane.PLAIN_MESSAGE;
    
    public AboutAction()
    {
        appMessage = (AppProperties.getInstance().getValueOf(AppProperties.ABOUT_INFO).equals("")) ?
                "Credit Worthiness System version 1.0 \n"+
            "was developed towards the completion of a Project" : (AppProperties.getInstance().getValueOf(AppProperties.ABOUT_INFO)) ;
        aboutDialogTitle = "About CWS vs 1.0";
        messageType = JOptionPane.PLAIN_MESSAGE;
    }

    @Override
    public void run() 
    {
        JOptionPane.showMessageDialog(null, appMessage, 
                aboutDialogTitle, messageType);
    }        
}
