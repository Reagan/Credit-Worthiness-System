/**
 * Credit Worthiness System Version 1.0
 */
package AppActions;

import UI.ApplicationIcon;
import UI.DepthButton;
import UI.Models.PrintUserTransactionsModel;
import credit.worthiness.system.CreditWorthinessSystem;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.util.concurrent.ExecutionException;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.TitlePaneLayout;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * This class extends the JTable print API
 * to print out a log of the user transactions 
 * for the currently selected user
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class PrintLogsAction extends AbstractedAction 
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
    
    public static final int USER_TRANS_TABLE = 1 ;
    public static final int MONTHLY_REPORT_TABLE = 2 ;
    public static final int CREDIT_ITEMS_TABLE = 3 ;
    
    private final String USER_TRANS_TABLE_TITLE = "User Transactions" ;
    private final String MONTHLY_TRANS_REPORT_TITLE = "Monthly Report" ;
    private final String CREDIT_ITEMS_TITLE = "Credit Items";
    
    private JDialog dialog ;
    private String dialogTitle = null ;
    
    private int sectionType ;
                
    private DepthButton printButton ;
    private DepthButton cancelButton ;
    private JPanel buttonsPanel ;
    private JLabel panelTitle ;
    
    public PrintLogsAction(int sType) 
    {   
        // initialise the section Type for display
        sectionType = sType ;
        
        // initialise the dialog
        switch(sectionType)
        {
            case(USER_TRANS_TABLE):
                dialogTitle = USER_TRANS_TABLE_TITLE ;
                break ;
                
            case(MONTHLY_REPORT_TABLE):
                dialogTitle = MONTHLY_TRANS_REPORT_TITLE ;
                break ;
                    
            case(CREDIT_ITEMS_TABLE):
                dialogTitle = CREDIT_ITEMS_TITLE ;
                break ;
                        
                    default:
                        dialogTitle = "" ;
                
        }
        
        dialog = new JDialog();
        ApplicationIcon.getInstance().setApplicationIcon(dialog); // add the application icon
        dialog.setLayout(new BorderLayout());
        dialog.setTitle(dialogTitle);
        
        // initialise the buttons and add to panel
        buttonsPanel = new JPanel();
       // buttonsPanel.setPreferredSize(new Dimension(400,30));
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));                
        
        // create and add the print button
        printButton = new DepthButton("Print") ;
        printButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                // prints the table
                printTableDetails();
            }
        });
        buttonsPanel.add(printButton);       

        // create and add the cancel button
        cancelButton = new DepthButton("Cancel") ;
        cancelButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                // close the dialog
                dialog.dispose();
            }
        });
        buttonsPanel.add(cancelButton);
        
        // automatically display the current user's 
        // transaction log
        currentUserID = credit.worthiness.system.CreditWorthinessSystem.getCurrentUserID() ;        
    }
    
    // creates a model for the table
    private JTable createTransactionsTable(TableModel model) 
    {
        return new JTable(model);
    }

    /** 
     * This method prints out the Transactions table
     * !+ACTION_RECONF , edited this method so that a dialog first appears before 
     * the user selects the 'print' option
     */
    @Override
    public void run() 
    {        
        // initialise the table and populate its 
        // model if a user has been selected already        
        userTransactionsTable = createTransactionsTable(new 
                PrintUserTransactionsModel(currentUserID));
        userTransactionsTable.setFillsViewportHeight(true);
       
        // add the pane with the title for the displayed dialog
        panelTitle = new JLabel("User Transaction log for " + "Angie Muthoni");
        panelTitle.setFont(new Font("Dialog", Font.BOLD, 16)) ;
        panelTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 3));
        
        // create the panel and add the GUI components
        dialog.setSize(400,530);        
        dialog.add(panelTitle, BorderLayout.NORTH);
        dialog.add(new JScrollPane(userTransactionsTable), BorderLayout.CENTER);
        dialog.add(buttonsPanel,BorderLayout.SOUTH) ;
        
        dialog.setVisible(true);
    }
    
    private void printTableDetails()
    {
        // set the header and footer messages
        // name from the userID
        headerText = "User Transaction Log for "
                + CreditWorthinessSystem.getCurrentUser() ;
        footerText = "Page {0}" ;                
        
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