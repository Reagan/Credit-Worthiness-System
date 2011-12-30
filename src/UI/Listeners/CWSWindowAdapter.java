/**
 * Credit Worthiness System Version 1.0
 */
package UI.Listeners;

import AppActions.ExitAction;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * This class performs basic checks on window closing to ensure
 * that data is committed to database e.t.c.
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */

public class CWSWindowAdapter extends WindowAdapter
{
    private ExitAction exitAction = new ExitAction() ;
    
    @Override
    public void windowClosing(WindowEvent e) 
    {
        // ask the user if the really want to close 
        // the application
        // @TODO: Ensure that any transaction data changed 
        // is commited to the database before closing
        
        exitAction.run();
        
    }    
}
