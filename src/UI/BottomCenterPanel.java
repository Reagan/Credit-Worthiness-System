/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import AppActions.AppAction;
import AppActions.DeleteTransactionAction;
import AppActions.NewTransactionAction;
import UI.Listeners.TransactionListener;
import UI.Models.UserTransactionsModel;
import credit.worthiness.system.CreditWorthinessSystem;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutionException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class BottomCenterPanel extends JPanel
{
    private DepthButton createNewButton ;
    private DepthButton deleteButton ;
    private static JList transactionsList ;    
    
    private static UserTransactionsModel userTransactions ;
    private TransactionListener transactionListener ;
    public static int currSelectedTransactionIndex = -1 ;  // stores the currently selected
                                                        // transaction 
                                                        // -1 indicates that the transaction 
                                                        // has not yet been selected    
    // set the app actions
    private static AppAction createTransactionAction ;
    private static AppAction deleteTransactionAction ;
    
    public BottomCenterPanel()
    {
        // initialise the variables
        createTransactionAction = new AppAction(createNewButton, "Create"
                                        , false, KeyEvent.VK_T);
        createTransactionAction.addActionClass(new NewTransactionAction());
        createNewButton = new DepthButton(createTransactionAction);
        
        
        deleteTransactionAction = new AppAction(deleteButton, "Delete"
                                        , false, KeyEvent.VK_L);
        deleteTransactionAction.addActionClass(new DeleteTransactionAction());
        deleteButton = new DepthButton(deleteTransactionAction);
        
        
        // set the transaction listener
        transactionListener = new TransactionListener(); 
        
        // create the JList and attache the transaction listener
        transactionsList = new JList();
        transactionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        transactionsList.addListSelectionListener(transactionListener);
        transactionsList.setOpaque(false);
        
        // lay out the components
        setLayout(new BorderLayout());
        JScrollPane scroll = new JScrollPane(transactionsList);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        scroll.setOpaque(false);
        
        add(scroll, BorderLayout.CENTER);
        
        JPanel buttonsPane = new JPanel();
        buttonsPane.setOpaque(false);
        buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.X_AXIS));
        buttonsPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        buttonsPane.add(createNewButton);
        buttonsPane.add(Box.createHorizontalGlue());
        buttonsPane.add(deleteButton);
        
        add(buttonsPane, BorderLayout.SOUTH);
        
        //finalise and display panel        
        setOpaque(false);        
        setPreferredSize(new Dimension(278, 295));
        setBorder(BorderFactory.createTitledBorder("Transactions"));
    }
    
    /**
     * sets the user transactions into the Transactions Model
     */
    public static void setUserTransactionsModel ()
    {
        // get the currently selected user
        final int userID = CreditWorthinessSystem.getCurrentUserID() ;
        
        JOptionPane.showMessageDialog(null, " Getting transactions for user : "
                + userID, "User Transaction Details", JOptionPane.PLAIN_MESSAGE);
        
        // Indicate on status bar that fetching for
        // user's transactions
        StatusBar.updateStatusMessage("Obtaining user's transactions");
        
        SwingWorker worker = new SwingWorker <UserTransactionsModel, Void> ()
        {            
            @Override
            protected UserTransactionsModel doInBackground() 
            {
                // create the transactions model
                userTransactions = new UserTransactionsModel(userID);
                return userTransactions ;
            }
            
            @Override
            public void done()
            {                                
                    // set the model with the user's
                    // list of transactions
                    transactionsList.setModel(userTransactions);                        
                    
                    // enable the create new Transaction button
                    createTransactionAction.enableAction(true);
                    
                    // inform user on the status bar
                    StatusBar.updateStatusMessage("User's transactions obtained " );                   
                }                                                   
        };
        
        // schedule the thread
        worker.execute();               
    }
}
