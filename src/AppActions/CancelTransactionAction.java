/**
 * Credit Worthiness System Version 1.0
 */
package AppActions;

/**
 * This class closes the New Transactions frame
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class CancelTransactionAction extends AbstractedAction
{
    @Override
    public void run() 
    {
        NewTransactionAction.closeFrame();
    }
}
