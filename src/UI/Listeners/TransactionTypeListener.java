/**
 * Credit Worthiness System Version 1.0
 */
package UI.Listeners;

import DbConnection.TransactionTypes;
import UI.NewTransactionPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class TransactionTypeListener implements ChangeListener
{
    @Override
    public void stateChanged(ChangeEvent ce) 
    {
        JTabbedPane tabbedPane = (JTabbedPane) ce.getSource() ;
        int index = tabbedPane.getSelectedIndex();

        switch(index)
        {
            case(0):
                NewTransactionPanel.currSelectedTransactionType = 
                        TransactionTypes.CREDIT_TRANSACTION ;
                break ;

            case(1):
                NewTransactionPanel.currSelectedTransactionType = 
                        TransactionTypes.DEBIT_TRANSACTION ;
                break ;
        }
    }
}
        