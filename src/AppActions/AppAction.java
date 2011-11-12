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
    private Component parentComponent;
    
    public AppAction(Component parentComponent, String actionName, 
                    boolean enabledStatus, Integer keyEvent)
    {
        super(actionName);
        this.parentComponent = parentComponent ;
        putValue(Action.MNEMONIC_KEY, new Integer(keyEvent));
        setEnabled(enabledStatus);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
