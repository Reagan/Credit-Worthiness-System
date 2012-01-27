/**
 * Credit Worthiness System Version 1.0
 */
package UI.Listeners;

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
            case(NewTransactionPanel.CREDIT_TRANSACTION):
                NewTransactionPanel.currSelectedTransactionType = 
                        NewTransactionPanel.CREDIT_TRANSACTION ;
                break ;

            case(NewTransactionPanel.DEBIT_TRANSACTION):
                NewTransactionPanel.currSelectedTransactionType = 
                        NewTransactionPanel.DEBIT_TRANSACTION ;
                break ;
        }
        
        System.out.println("Current Transaction Type : " + index + " " +
                NewTransactionPanel.currSelectedTransactionType) ;
    }
}
        