/**
 * Credit Worthiness System Version 1.0
 */
package AppActions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class AppAction extends AbstractAction
{
    private Component parentComponent ;
    private ActionEvent actionEvent ;
    private AbstractedAction action ;
    
    public AppAction(Component parentComponent, String actionName, 
                    boolean enabledStatus, Integer keyEvent)
    {
        super(actionName);
        action = new AbstractedAction();
        
        this.parentComponent = parentComponent ;
        putValue(Action.MNEMONIC_KEY, new Integer(keyEvent));
        enableAction(enabledStatus);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        actionEvent = e ;
        action.run();
    }
    
    public void addActionClass(AbstractedAction action)
    {
        // instantiate the class and run the desired 
        // method
        this.action = action ;        
    }   
    
    /**
     * This method disables or enables an action
     * @param state 
     */
    public void enableAction(boolean status)
    {
        setEnabled(status);
    }
}
