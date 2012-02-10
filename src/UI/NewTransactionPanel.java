/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import AppActions.AppAction;
import DbConnection.ItemsDetails;
import UI.Listeners.NewTransactionListener;
import UI.Listeners.TransactionTypeListener;
import UI.Models.ItemsModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import javax.swing.*;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class NewTransactionPanel extends JPanel
{
    private DepthButton settingsButton ;
    private DepthButton saveTransactionButton ;
    private DepthButton cancelTransactionButton ;
    
    private JLabel dateLabel ;
    private JLabel itemLabel ;
    private JLabel itemNumberLabel ;
    
    public static JFormattedTextField date ;
    private final DateFormat dateFomat = new SimpleDateFormat("dd/MM/yyyy");
    
    public static JTextField numberOfItems ;
    public final static JComboBox items = new JComboBox(); ;
    
    private JLabel notesLabel ;
    public static JTextArea transactionNotes ;
    private JScrollPane transNotesScrollpane ;
    
    private JSeparator verticalSeparator ;      
    
    // this displays the list of items 
    // order as they are in the database
    private Vector list = null ;
    
    private GroupLayout layout;    
    public static boolean dirty = false ; // shows that details in the transaction panel
                                       // and text fields have changed
    
    public static int currTransactionID = -1 ; // marks the current transaction ID 
    
    private static AppAction settingsAction ;                
    private static AppAction saveTransactionAction ;                
    private static AppAction cancelTransactionAction ;
    public static Vector itemsObt ;
    public static int transactionType ; // will store the transaction type
    
    public JFrame parent ; // stores the containing JDialog
    public JTabbedPane transactionsPane ;
    public JPanel debitPane ; // store details for debit transactions
    public JPanel creditPane ; // store details for credit transactions
    private final String creditPaneTitle = "Credit Transaction" ; 
    private final String debitPaneTitle = "Debit Transaction" ;
    private JPanel lowerPanel ; // will store all the button options
    
    private JLabel debitDateLabel;
    private JLabel debitAmountLabel ;
    public static JFormattedTextField debitDate ;
    public static JTextField debitAmount ;
    private JLabel debitNotesLabel ;
    public static JTextArea debitTransactionNotes ;
    private JScrollPane debitTransScrollpane ;
    private JSeparator debitVerticalSeparator ;
    private GroupLayout debitPaneLayout ;
    
    public static final int CREDIT_TRANSACTION = 0 ;
    public static final int DEBIT_TRANSACTION = 1 ;
    
    // set the default transaction
    public static int currSelectedTransactionType = CREDIT_TRANSACTION ;
    
    
    public NewTransactionPanel(JFrame dialog)
    {
        // initialise the variables
        // labels
        parent = dialog ;                
        
        setLayout(new BorderLayout());      

        // initialise the JTabbedPane to hold the panels
        transactionsPane = new JTabbedPane();
        transactionsPane.addChangeListener(new TransactionTypeListener());
        
        creditPane = new JPanel();
        creditPane.setPreferredSize(new Dimension(430, 255));                     
        creditPane.setBorder(BorderFactory.createTitledBorder("Credit Transaction Details"));
        
        // create & add options for the credit pane   
        // Labels
        dateLabel = new JLabel("Date (dd/MM/YYYY)");
       
        
        itemLabel = new JLabel("Item");
       
        
        itemNumberLabel = new JLabel("Number of Items");
       
    
        // TextFields
        date = new JFormattedTextField(dateFomat);
        date.setMinimumSize(new Dimension(166, 28));
        date.setPreferredSize(new Dimension(166, 28));
        date.setMaximumSize(new Dimension(166, 28));
        
        numberOfItems = new JTextField();
        numberOfItems.setMinimumSize(new Dimension(166, 28));
        numberOfItems.setPreferredSize(new Dimension(166, 28));
        numberOfItems.setMaximumSize(new Dimension(166, 28));
        
                
        // set the dimensions for the maximum size
        items.setMinimumSize(new Dimension(166, 28));
        items.setPreferredSize(new Dimension(166, 28));
        items.setMaximumSize(new Dimension(166, 28));
        
        
        // populate the list
        getItems();
        
        notesLabel = new JLabel("Notes");
        
        transactionNotes = new JTextArea();
        transactionNotes.setLineWrap(true);
        transactionNotes.setWrapStyleWord(true);
        transactionNotes.setPreferredSize(new Dimension(215,150)) ;
        transactionNotes.setMinimumSize(new Dimension(215,150)) ;
        transNotesScrollpane = new JScrollPane(transactionNotes) ;
        
        verticalSeparator = new JSeparator(SwingConstants.VERTICAL);
        
        // buttons
        settingsAction = new AppAction(settingsButton, "Settings"
                                        , true, KeyEvent.VK_S);
        settingsButton = new DepthButton(settingsAction) ;
        
        saveTransactionAction = new AppAction(saveTransactionButton, "Save"
                                        , true, KeyEvent.VK_S);
        saveTransactionButton = new DepthButton(saveTransactionAction) ;
        saveTransactionButton.addActionListener(
                            new NewTransactionListener(NewTransactionPanel.this));
        
        cancelTransactionAction = new AppAction(cancelTransactionButton, "Cancel"
                                        , true, KeyEvent.VK_S);
        cancelTransactionButton = new DepthButton(cancelTransactionAction) ; 
        cancelTransactionButton.addActionListener(
                            new NewTransactionListener(NewTransactionPanel.this));
        
        // lay out the elements
        layout = new GroupLayout(creditPane);
        creditPane.setLayout(layout);
        
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        
        // lay out the horizontally
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                    .addComponent(dateLabel)
                    .addComponent(date)
                    .addComponent(itemLabel)
                    .addComponent(items)
                    .addComponent(itemNumberLabel)
                    .addComponent(numberOfItems))
                .addComponent(verticalSeparator)
                .addGroup(layout.createParallelGroup()
                    .addComponent(notesLabel)
                    .addComponent(transNotesScrollpane)));
        
        // lay out vertically
        layout.setVerticalGroup(layout.createParallelGroup()
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dateLabel)
                        .addComponent(date)
                        .addComponent(itemLabel)
                        .addComponent(items)
                        .addComponent(itemNumberLabel)
                        .addComponent(numberOfItems))
                    .addComponent(verticalSeparator)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(notesLabel)
                        .addComponent(transNotesScrollpane)));
        
        // create debitPane options        
        debitPane = new JPanel();      
        debitPane.setPreferredSize(new Dimension(430, 255));   
        debitPane.setBorder(BorderFactory.createTitledBorder("Debit Transaction Details"));
        
        debitDateLabel = new JLabel("Date (dd/MM/YYYY)");
        
        debitAmountLabel = new JLabel("Amount");
        
               
    
        // TextFields
        debitDate = new JFormattedTextField(dateFomat);
        debitDate.setMinimumSize(new Dimension(166, 28));
        debitDate.setPreferredSize(new Dimension(166, 28));
        debitDate.setMaximumSize(new Dimension(166, 28));
        
        debitAmount = new JTextField();
        debitAmount.setMinimumSize(new Dimension(166, 28));
        debitAmount.setPreferredSize(new Dimension(166, 28));
        debitAmount.setMaximumSize(new Dimension(166, 28));
        
        debitNotesLabel = new JLabel("Notes");
        
        debitTransactionNotes = new JTextArea();
        debitTransactionNotes.setLineWrap(true);
        debitTransactionNotes.setWrapStyleWord(true);
        debitTransScrollpane = new JScrollPane(debitTransactionNotes) ;
        debitTransactionNotes.setPreferredSize(new Dimension(215,150));
        debitTransactionNotes.setMinimumSize(new Dimension(215,150));
        
        debitVerticalSeparator = new JSeparator(SwingConstants.VERTICAL);
               
        
        // lay out the elements
        debitPaneLayout = new GroupLayout(debitPane);
        debitPane.setLayout(debitPaneLayout);
        
        debitPaneLayout.setAutoCreateContainerGaps(true);
        debitPaneLayout.setAutoCreateGaps(true);
        
        // lay out the horizontally
        debitPaneLayout.setHorizontalGroup(debitPaneLayout.createSequentialGroup()
                .addGroup(debitPaneLayout.createParallelGroup()
                    .addComponent(debitDateLabel)
                    .addComponent(debitDate)
                    .addComponent(debitAmountLabel)
                    .addComponent(debitAmount))
                .addComponent(debitVerticalSeparator)
                .addGroup(debitPaneLayout.createParallelGroup()
                    .addComponent(debitNotesLabel)
                    .addComponent(debitTransScrollpane)));
        
        // lay out vertically
        debitPaneLayout.setVerticalGroup(debitPaneLayout.createParallelGroup()
                    .addGroup(debitPaneLayout.createSequentialGroup()
                        .addComponent(debitDateLabel)
                        .addComponent(debitDate)
                        .addComponent(debitAmountLabel)
                        .addComponent(debitAmount))
                    .addComponent(debitVerticalSeparator)
                    .addGroup(debitPaneLayout.createSequentialGroup()
                        .addComponent(debitNotesLabel)
                        .addComponent(debitTransScrollpane)));

        // add them to tabbed pane
        transactionsPane.addTab(creditPaneTitle, creditPane);
        transactionsPane.addTab(debitPaneTitle, debitPane);  
        
        // add the transactions pane to the main panel
        add(transactionsPane, BorderLayout.CENTER);
        
        // add the buttons to the lower panel 
        lowerPanel = new JPanel();
        lowerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        lowerPanel.setPreferredSize(new Dimension(430, 40));

        lowerPanel.add(settingsButton);
        lowerPanel.add(saveTransactionButton);
        lowerPanel.add(cancelTransactionButton);         
        
        add(lowerPanel, BorderLayout.SOUTH);
        
        //finalise and display panel           
        setOpaque(false);             
    }
    
    private void getItems()
    {        
        SwingWorker getItems =  new SwingWorker<Vector, Void>()
        {
            @Override
            protected Vector doInBackground()
            {
                ItemsDetails itemNames = new ItemsDetails() ;
                itemsObt = itemNames.getItemNames() ;
                return itemsObt ;
            }  
            
            @Override
            protected void done()
            {
                try 
                {
                    list = get() ;
                    ItemsModel itemsModel = new ItemsModel(list);
                    items.setModel(itemsModel) ;
                } 
                catch (InterruptedException ex) 
                {
                    System.out.println("Error: " + ex.toString()) ;
                } 
                catch (ExecutionException ex) 
                {
                    System.out.println("Error: " + ex.toString()) ;
                }
            }
        };
        
        // schedule the thread
        getItems.run();
    }  
    
    
    
    /**
     * This method displays the transaction details 
     * for a selected transaction for editing
     * @param transactionID 
     */
    
    /**
    public static void setTransactionDetails(int transactionID)
    {      
        // ensure that the main application knows that 
        // things have changed
        if(transactionID != -1)
        {         
            // mark that a transaction has changed
            dirty = true ; 
            currTransactionID = transactionID ;
            
            // obtain information from the database
            // and update the fields in this section
            TransactionDetails transDetails =  new TransactionDetails() ;
            
            String [] transactionDetails = transDetails
                    .getTransactionDetails(transactionID) ;
            
            String concatDate = transactionDetails[2] + "/" + transactionDetails[3]
                    + "/" + transactionDetails[4] ;
            
            date.setText(concatDate) ;
            numberOfItems.setText(transactionDetails[1]);
            
            // update the JComboBox & repaint
            items.setSelectedIndex(Integer.parseInt(transactionDetails[0]) - 1 );
            items.repaint();
            
            transactionNotes.setText(transactionDetails[5]);  
            
            // set the transaction type
            transactionType = Integer.parseInt(transactionDetails[6]) ;
            
            // enable the save and delete transaction buttons
            saveTransactionAction.enableAction(true);
            cancelTransactionAction.enableAction(true);
            
            // also enable the same option in the menubar
            MenuBar.enableTransactionOptions(true);
        }  
        else
        {
            // in this case, the user wants all the fields 
            // for transactions cleared
            date.setText("") ;
            numberOfItems.setText("");
            items.setSelectedIndex(0);
            transactionNotes.setText("");            
        }
            
    }
    **/
}
