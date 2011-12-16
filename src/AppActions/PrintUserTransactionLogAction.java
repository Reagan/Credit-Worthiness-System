/**
 * Credit Worthiness System Version 1.0
 */
package AppActions;

import UI.Models.PrintUserTransactionsModel;
import java.awt.Dimension;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * This class extends the JTable print API
 * to print out a log of the user transactions 
 * for the currently selected user
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class PrintUserTransactionLogAction extends AbstractedAction 
{
    private int currentUserID ;// stores the ID for the current 
                                // user and for whom the transaction log 
                                // is to be printed
    private JTable userTransactionsTable ;
    private final int USER_TRANS_TABLE_ROW_HEIGHT = 24 ;
    private MessageFormat header = null;
    private String headerText = null ;
    private MessageFormat footer = null;
    private String footerText = null ;
    private boolean showPrintDialog = true;
    private boolean interactive = true;
    
    private final Object[][] Transactions = 
    {
        // structure of the transactions
        // #Date, #Item, #ItemWorth, #AmountPaid
        {"1st Jan, 2011", "Milk", 200, 100},
        {"1st Jan, 2011", "Milk", 200, 100},
        {"1st Jan, 2011", "Milk", 200, 100},
        {"1st Jan, 2011", "Milk", 200, 100},
        {"1st Jan, 2011", "Milk", 200, 100},
    };
    
    private final Object[] titles = {"Date", "Item", "Item Worth", "Item Paid"} ;
    
    public PrintUserTransactionLogAction() 
    {
        // @TODO: make a database query to 
        // automatically display the current user's 
        // transaction log
        currentUserID = credit.worthiness.system.CreditWorthinessSystem.getCurrentUserID() ;
        
        // initialise the table and populate its 
        // model        
        userTransactionsTable = createTransactionsTable(new PrintUserTransactionsModel(currentUserID));
        userTransactionsTable.setFillsViewportHeight(true);
        userTransactionsTable.setRowHeight(USER_TRANS_TABLE_ROW_HEIGHT);
       
        userTransactionsTable.setRowHeight(20);
        userTransactionsTable.setSize(userTransactionsTable.getPreferredSize());
        userTransactionsTable
                .setPreferredScrollableViewportSize(new Dimension(620, 500));        
        userTransactionsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumn column = userTransactionsTable.getColumnModel().getColumn(0);
        column.setPreferredWidth(300);
  
        // set the header and footer messages
        // @TODO: automatically get the current user's 
        // name from the userID
        headerText = "User Transaction Log for "
                + "Angie Muthoni" ;
        footerText = "Page {0}" ;
                
    }
    
    // creates a model for the table
    private JTable createTransactionsTable(TableModel model) 
    {
        return new JTable(model);
    }

    /** 
     * This method prints out the Transactions table
     */
    @Override
    public void run() 
    {
        // set the header
        header = new MessageFormat(headerText);
        
        // set the footer
        footer = new MessageFormat(footerText);                

        /* determine the print mode */
        final JTable.PrintMode mode =  JTable.PrintMode.NORMAL;
                                         
        // place the printing process in a SwingWorker
        SwingWorker printUserTransactions = new SwingWorker()
        {
            Boolean complete ;
            
            @Override
            protected Boolean doInBackground()  
            {
                try 
                {
                    /* print the table */
                    complete = userTransactionsTable.print(mode, header, footer,
                                                         showPrintDialog, null,
                                                         interactive, null);
                    
                }
                catch (PrinterException pe) 
                {
                    /* Printing failed, report to the user */
                    JOptionPane.showMessageDialog(null,
                                                  "Printing Failed: " + pe.getMessage(),
                                                  "Printing Result",
                                                  JOptionPane.ERROR_MESSAGE);
                }
                return complete ;
            }
            
            @Override
            protected void done()
            {
                try 
                {
                    /* if printing completes */
                    if (complete = (Boolean) get()) 
                    {
                        /* show a success message */
                        JOptionPane.showMessageDialog(null,
                                                      "Printing Complete",
                                                      "Printing Result",
                                                      JOptionPane.INFORMATION_MESSAGE);
                    } 
                    else 
                    {
                        /* show a message indicating that printing was cancelled */
                        JOptionPane.showMessageDialog(null,
                                                      "Printing Cancelled",
                                                      "Printing Result",
                                                      JOptionPane.INFORMATION_MESSAGE);
                    }
                } 
                catch (InterruptedException ex) 
                {
                    System.out.println("Error: " + ex.toString());
                } 
                catch (ExecutionException ex) 
                {
                    System.out.println("Error: " + ex.toString());
                }
            }
        };
        
        printUserTransactions.run();         
    }    
}