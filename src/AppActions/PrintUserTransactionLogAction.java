/**
 * Credit Worthiness System Version 1.0
 */
package AppActions;

import UI.Models.UserTransactionsModel;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
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
    
    public PrintUserTransactionLogAction() 
    {
        // @TODO: make a database query to 
        // automatically display the current user's 
        // transaction log
        currentUserID = credit.worthiness.system.CreditWorthinessSystem.getCurrentUserID() ;
        
        // initialise the table and populate its 
        // model
        userTransactionsTable = createTransactionsTable(
                new UserTransactionsModel(currentUserID));
        userTransactionsTable.setFillsViewportHeight(true);
        userTransactionsTable.setRowHeight(USER_TRANS_TABLE_ROW_HEIGHT);
        
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
        
        header = new MessageFormat(headerText);
        
        footer = new MessageFormat(footerText);                

        /* determine the print mode */
        JTable.PrintMode mode =  JTable.PrintMode.FIT_WIDTH ;
                                         

        try {
            /* print the table */
            boolean complete = userTransactionsTable.print(mode, header, footer,
                                                 showPrintDialog, null,
                                                 interactive, null);

            /* if printing completes */
            if (complete) 
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
        catch (PrinterException pe) 
        {
            /* Printing failed, report to the user */
            JOptionPane.showMessageDialog(null,
                                          "Printing Failed: " + pe.getMessage(),
                                          "Printing Result",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }    
}