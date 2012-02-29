/**
 * Credit Worthiness System Version 1.0
 */
package AppActions;

import DbConnection.UsersDetails;
import UI.ApplicationIcon;
import UI.DepthButton;
import UI.Models.PrintUserTransactionsModel;
import credit.worthiness.system.CreditWorthinessSystem;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.lang.Object;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 * This class extends the JTable print API
 * to print out a log of the user transactions 
 * for the currently selected user
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class PrintLogsAction extends AbstractedAction 
{
    private int currentUserID ; // stores the current user for whom the transactions
                        // are displayed. If no user is selected, transactions for 
                        // all users are displayed
    
    // variables relating to the transactions table
    private JTable userTransactionsTable ;
    private MessageFormat header = null;
    private String headerText = null ;
    private MessageFormat footer = null;
    private String footerText = null ;
    private boolean showPrintDialog = true;
    private boolean interactive = true;         
    
    private JDialog dialog ;
    private String dialogTitle = "Monthly Report" ; // initialise the monthly report
    
    // create the components for the top panel
    private DepthButton printButton ;
    private DepthButton cancelButton ;
    
    private JPanel buttonsPanel ; // hosts the lower buttons
    private JPanel topPanel ; // stores all the components for the top set of components
    private JPanel optionsPanel ; // stores the additional option buttons
    private JPanel customerPanel ; // stores the combo with all user details and the limit status
    
    private JLabel panelTitle ; // stores the details on whose transactions 
                            // details are being displayed
    private JComboBox customersSelect ; // stores the names for all the customers
    private JComboBox fromMonthsCombo ;
    private JComboBox fromYearsCombo ;   
    private JComboBox toMonthsCombo ;
    private JComboBox toYearsCombo ;
    
    private JLabel limitStatus ; //stores the details for the status of the credit limit
    private final JLabel FROM_LABEL = new JLabel ("From :") ;
    private final JLabel TO_LABEL = new JLabel ("To :") ;
    
    private JCheckBox viewDebitTransactionsCheckbox ;
    private JCheckBox viewCreditTransactionsCheckbox ;
    
    private static ArrayList<Integer> rowsForCreditTransactions 
            = new ArrayList<Integer>(0) ; // stores all rows that need formatting in table
    
    private int fromMonth ; // stores the from month
    private int fromYear ; //stores the from year
    private int toMonth ; // stores the to month
    private int toYear ; // stores the to year
    private boolean showDebitTransactions = true ; // determines whether or not debit transactions are shown
    private boolean showCreditTransactions = true ; // determines whether to show credit transactions
    private String currSelectedUser = "All Customers" ; // stores the current customer for whom the transactions are being displayed
    
    private ReportUserChangeListener changeListener = new ReportUserChangeListener() ;
    
    public PrintLogsAction() {}
    
    /**
     * This method initializes the UI components for the reports panel
     */
    private void init() 
    {
        // initialise all components
        dialog = new JDialog();
        customerPanel = new JPanel() ;
        optionsPanel = new JPanel() ;
        buttonsPanel = new JPanel();       
            
        // get the user for whom the transations are to be displayed
        currentUserID = credit.worthiness.system.CreditWorthinessSystem.getCurrentUserID() ;        
        
        // format the main dialog
        ApplicationIcon.getInstance().setApplicationIcon(dialog); // add the application icon
        dialog.setLayout(new BorderLayout());
        dialog.setTitle(dialogTitle);
        
        // allow window to be closed by pressing ESC
        Action escListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        } ;

        dialog.getRootPane().registerKeyboardAction(escListener,
        KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
        JComponent.WHEN_IN_FOCUSED_WINDOW);     
        
        // initialise all required components for the transactions panel
        customersSelect = new JComboBox() ;
        addCustomersToCombo(customersSelect) ; // populates the JComboBox with the names for the customers
        customersSelect.addActionListener(changeListener) ;
        
        // populate the credit limit items and add to label
        limitStatus = new JLabel(""); 
        
        // add to the customer panel
        customerPanel.setLayout(new GridLayout(1, 2));
        customerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        customerPanel.add(customersSelect) ;
        customerPanel.add(limitStatus) ;
        
        // initialise the JComboBox for the months and years
        fromMonthsCombo = new JComboBox(getMonths()) ;
        fromMonthsCombo.addActionListener(changeListener);
        
        toMonthsCombo = new JComboBox(getMonths()) ;
        toMonthsCombo.addActionListener(changeListener);
        
        fromYearsCombo = new JComboBox(getYears()) ;
        fromYearsCombo.addActionListener(changeListener);
        
        toYearsCombo = new  JComboBox(getYears()) ;
        toYearsCombo.addActionListener(changeListener);
        
        // initialise the JCheckBoxes for the displayed transaction types
        viewCreditTransactionsCheckbox = new JCheckBox("Credit Transactions", true) ;
        viewCreditTransactionsCheckbox.addActionListener(changeListener);
        
        viewDebitTransactionsCheckbox = new JCheckBox("Debit Transactions" , true) ;
        viewDebitTransactionsCheckbox.addActionListener(changeListener);
        
        // add JSeparator to view options
        JSeparator vSeparator = new JSeparator(JSeparator.VERTICAL) ;        
                
        // add to the options pane
        optionsPanel.setBorder(BorderFactory.createTitledBorder("Options")) ;
        GroupLayout layout = new GroupLayout(optionsPanel) ;
        optionsPanel.setLayout(layout);      
        
        // add the items horizontally
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                    .addComponent(FROM_LABEL)
                    .addComponent(TO_LABEL)
                )
                .addGroup(layout.createParallelGroup()
                    .addComponent(fromMonthsCombo)
                    .addComponent(toMonthsCombo)
                )
                .addGroup(layout.createParallelGroup()
                    .addComponent(fromYearsCombo)
                    .addComponent(toYearsCombo)
                )
                .addComponent(vSeparator)
                .addGroup(layout.createParallelGroup()
                    .addComponent(viewDebitTransactionsCheckbox)
                    .addComponent(viewCreditTransactionsCheckbox)                
                )                
          );
        
        // add the items vertically
        layout.setVerticalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(FROM_LABEL)
                                .addComponent(fromMonthsCombo)
                                .addComponent(fromYearsCombo)
                            )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(TO_LABEL)
                                .addComponent(toMonthsCombo)
                                .addComponent(toYearsCombo)
                            )
                    )
                .addComponent(vSeparator)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(viewDebitTransactionsCheckbox)
                        .addComponent(viewCreditTransactionsCheckbox)
                    )
            ); 
                                      
        // initialise the buttons and add to panel    
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
        
    }
    
    // creates a model for the table
    private JTable createTransactionsTable(TableModel model) 
    {
        return new JTable(model);
    }

    private String[] getMonths () 
    {
        String [] months = {"January", "February", "March" } ;
        return months ;
    }
    
    private String[] getYears () 
    {
        String[] years = {"1999", "2000", "2001"} ;
        return years ;
    }   
    
    /** 
     * This method prints out the Transactions table
     * !+ACTION_RECONF , edited this method so that a dialog first appears before 
     * the user selects the 'print' option
     */
    @Override
    public void run() 
    {                 
        // initialise the rest of the UI components
        init() ;
        
        // initialise the table and populate its 
        // model if a user has been selected already        
        userTransactionsTable = createTransactionsTable(new 
                PrintUserTransactionsModel(currentUserID));
        userTransactionsTable.setFillsViewportHeight(true);
       
        // add a cell renderer to the table
        TableCellRenderer transRenderer = new TransactionsRenderer () ;
        userTransactionsTable.setDefaultRenderer(Object.class, transRenderer);
        
        // set the additional renderer for the column with the transaction type
        // userTransactionsTable.getColumn("Type").setCellRenderer(new TransactionTypeColumnRenderer()) ;
        
        // add the pane with the title for the displayed dialog
        if ( CreditWorthinessSystem.getCurrentUserID() == 0 ) 
        {
            panelTitle = new JLabel("User Transaction log for all Customers") ;
        }
        else 
        {
            panelTitle = new JLabel("User Transaction log for " + CreditWorthinessSystem.getCurrentUser());
        }
        
        panelTitle.setFont(new Font("Dialog", Font.BOLD, 16)) ;
        panelTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 3));
        
        // create the panel and add the GUI components
        topPanel = new JPanel() ;
        JPanel titlePanel = new JPanel() ;
        titlePanel.setSize(450, 30);
        titlePanel.add(panelTitle) ;
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setSize(450, 600);                        
        topPanel.add(titlePanel) ;
        
        //  add the customers Panel
        topPanel.add(customerPanel) ;
        
        // add the transactions JTable
        topPanel.add(new JScrollPane(userTransactionsTable)) ;
        
        // add the options table
        topPanel.add(optionsPanel) ;
        
        // initialise the dialog and finalise the layout of the components
        dialog.setSize(450,630);        
        dialog.add(topPanel, BorderLayout.CENTER);
        dialog.add(buttonsPanel,BorderLayout.SOUTH) ;
        
        dialog.setVisible(true);        
    }
    
    /**
     * This method populates the JCombobox with the names of all customers
     */
    private void addCustomersToCombo(final JComboBox customersCombo )  
    {
        SwingWorker getCustomersDetails = new SwingWorker() {

            @Override
            protected Vector doInBackground() throws Exception 
            {
                UsersDetails users = new UsersDetails();
                return users.getUsersNames() ;                               
            }
            
            @Override
            public void done() 
            {
                try 
                {
                    Vector<String> userNames = new Vector<String>() ;
                    userNames.add("All Customers");
                    
                    Vector<String> obtainedNames = (Vector<String>) get() ;
                    
                    for ( int i = 0 , s = obtainedNames.size() ; i <s ;
                            i++ )
                    {
                        userNames.add(obtainedNames.get(i)) ;
                    }
                    
                    customersCombo.setModel(new DefaultComboBoxModel(userNames));
                    
                } catch (InterruptedException ex) {
                    System.out.println(ex);
                } catch (ExecutionException ex) {
                    System.out.println(ex);
                }
            }
        }; 
        
        // get the customers details
        getCustomersDetails.execute() ;
    }
    
    /**
     * This method obtains the credit amount for a specific user and 
     * displays it in a formatted string
     */
    private String getCustomerLimit() 
    {
        // String creditLimitStatement = "" ;
        String creditLimitStatement = "Overlimit: Kshs 2340" ;
        return creditLimitStatement ;
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
       
    /**
     * This class renders the cells for the transactions
     * dependant on whether the transactions are credit/debit
     * transactions
     */
    private class TransactionsRenderer extends DefaultTableCellRenderer
    {
        public final DefaultTableCellRenderer DEFAULT_RENDERER 
                = new DefaultTableCellRenderer();
       
        private final ImageIcon passedIcon
            = new ImageIcon("runtime_required" + File.separator 
                + "images" + File.separator + "passed.png");
         
        private final ImageIcon failedIcon
            = new ImageIcon("runtime_required" + File.separator 
                + "images" + File.separator + "failed.png");
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) 
        {
            Component renderer =
                    DEFAULT_RENDERER.getTableCellRendererComponent(table, value,
                    isSelected, hasFocus, row, column);
            
            // set the foreground & background colors
            Color foreground = null, background = null;            
            
            // find out if the cell contains a credit/debit transaction
            // and format appropriately
            if(column == 0)
            {
                // find out if the value for the column is 1
                if (value.equals(1))
                {
                    // mark the row as special
                    rowsForCreditTransactions.add(row);
                    
                    // style the cell
                    foreground = Color.WHITE;
                    background = Color.LIGHT_GRAY;  
                            
                    DEFAULT_RENDERER.setText("");
                    DEFAULT_RENDERER.setHorizontalAlignment(SwingConstants.CENTER);
                    DEFAULT_RENDERER.setIcon(passedIcon);
                }
                else if (value.equals(2))
                {
                    DEFAULT_RENDERER.setText("");
                    DEFAULT_RENDERER.setHorizontalAlignment(SwingConstants.CENTER);
                    DEFAULT_RENDERER.setIcon(failedIcon);
                }
            }
            else 
            {
                // check if the cell belongs to a special cell
                // and style it as appropriate
                DEFAULT_RENDERER.setIcon(null);
                
                for ( int currRow : rowsForCreditTransactions )
                {
                    if (row == currRow)
                    {
                        foreground = Color.WHITE;
                        background = Color.LIGHT_GRAY;   
                        
                        break ;
                    }
                }
            }
                        
            renderer.setForeground(foreground);
            renderer.setBackground(background);
            return renderer ;
        }        
    }
    
    /**
     * This method re-populates the fields for the options pane before they
     * are passed to the obtain the transactions for the table
     */
    private void repopulateOptionsField()
    {    
        fromMonth = fromMonthsCombo.getSelectedIndex() ; // stores the from month
        fromYear = Integer.parseInt((String) fromYearsCombo.getSelectedItem()) ; //stores the from year
        toMonth = toMonthsCombo.getSelectedIndex() ; // stores the to month
        toYear = Integer.parseInt((String) toYearsCombo.getSelectedItem()) ; // stores the to year

        showDebitTransactions = (viewDebitTransactionsCheckbox.isSelected()) ? true : false ; 
        showCreditTransactions = (viewCreditTransactionsCheckbox.isSelected()) ? true : false ;  
        
        currSelectedUser = (String) customersSelect.getSelectedItem() ;
    }
    
    /**
     * This class sets the action listener for a selected option
     */
    public class ReportUserChangeListener implements ActionListener {

        public ReportUserChangeListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // get the fields from options panel
            repopulateOptionsField() ;
            
            // repopulate the transactions table model
            // and set it
            PrintUserTransactionsModel currTransactionsModel 
                    = new PrintUserTransactionsModel(currSelectedUser, showDebitTransactions,
            showCreditTransactions, fromMonth, fromYear, toMonth, toYear) ;
            
            userTransactionsTable.setModel(currTransactionsModel);
            userTransactionsTable.revalidate();
            
            // set the dialog title for current user
            panelTitle.setText(currTransactionsModel.getTransactionsOwner());
            
            // set the credit/debit limit 
            if ( currTransactionsModel.getCreditOrDebitLimit() < 0 )
            {
               limitStatus.setText("Underlimit : Kshs " + 
                       currTransactionsModel.getCreditOrDebitLimit());
            }
            else if ( currTransactionsModel.getCreditOrDebitLimit() == 0 )
            {
                
               limitStatus.setText("Kshs " + 
                       currTransactionsModel.getCreditOrDebitLimit());
            }
            else
            {
                
               limitStatus.setText("Overlimit : Kshs " + 
                       currTransactionsModel.getCreditOrDebitLimit());
            }                
        }
    }
}