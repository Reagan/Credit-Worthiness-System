/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import AppActions.AppAction;
import AppActions.DeleteTransactionAction;
import AppActions.MenuBar;
import AppActions.SettingsUserAction;
import AppActions.UpdateTransactionDetailsAction;
import DbConnection.ItemsDetails;
import DbConnection.TransactionDetails;
import DbConnection.TransactionTypes;
import UI.Models.ItemsModel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
public class BottomRightPanel extends JPanel
{
    private DepthButton settingsButton ;
    private DepthButton saveTransactionButton ;
    private DepthButton deleteTransactionButton ;
    
    private JLabel dateLabel ;
    private JLabel itemLabel ;
    private static JLabel itemNumberLabel ;
    
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
    private static AppAction deleteTransactionAction ;
    public static Vector itemsObt ;
    public static int transactionType ; // will store the transaction type
    
    // create the various panes for the 2 types
    // of transactions (CREDIT & DEBIT)
    private JPanel creditTransactionsPanel ;
    private JPanel debitTransactionsPanel ;
    private final static String CREDIT_PANEL_TITLE = "Credit Transactions" ;
    private final static String DEBIT_PANEL_TITLE = "Debit Transactions" ;
    private static CardLayout cl ;
    
    private static BottomRightPanel pPanel ;
    
    // debit transactions panel components 
    private JLabel debitDateLabel ;        
    private JLabel debitAmountLabel ;
    private static JFormattedTextField debitDate ;
    private static JTextField debitAmount ;
    private JLabel debitNotesLabel ;
    private static JTextArea debitTransactionNotes ;
    private JSeparator debitVerticalSeparator ;
    private JScrollPane debitTransScrollpane ;
    
    // layout for the debit transactions
    private GroupLayout debitPaneLayout ;
    private static JPanel transactionFields ; // stores all the transaction components
    private JPanel buttonsPanel ; // contains all the buttons for the panels
    
    private BottomRightPanel()
    {        
        // set the layout to card layout
        setLayout(new BorderLayout());
     
        // initialise the panel with the transactions fields
        transactionFields = new JPanel(new CardLayout()) ;
        transactionFields.setOpaque(false);
        
        // create the creditTransactionsPanel, create its components
        // and add them to the credit panel
        creditTransactionsPanel = new JPanel() ;
        creditTransactionsPanel.setOpaque(false);
        
        // initialise the variablesprivate JLabel dateLabel ;
        // labels
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
        
        // adjust the JComboBox dimensions
        items.setMinimumSize(new Dimension(166, 28));
        items.setPreferredSize(new Dimension(166, 28));
        items.setMaximumSize(new Dimension(166, 28));
        items.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                // if the selected option is 'Cash', then change the 
                // 'Number of items' label text to Cash
                String selectedObject = (String) ie.getItem() ;
                if (selectedObject.equals("Cash"))
                {
                    itemNumberLabel.setText("Amount");
                }
                else
                {
                    itemNumberLabel.setText("Number of Items");
                }
            }
        });
        
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
        
        // lay out the elements in the credit transactions panel
        layout = new GroupLayout(creditTransactionsPanel);
        creditTransactionsPanel.setLayout(layout);
        
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
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
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
                        .addComponent(transNotesScrollpane))));
        
        // add the credit transactions panel
        transactionFields.add(creditTransactionsPanel, CREDIT_PANEL_TITLE) ;
        
        // create the debit transactions panel and add the elements
        debitTransactionsPanel = new JPanel();
        debitTransactionsPanel.setOpaque(false);
        
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
        debitPaneLayout = new GroupLayout(debitTransactionsPanel);
        debitTransactionsPanel.setLayout(debitPaneLayout);
        
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
        
        // add the debit transactions panel
        transactionFields.add(debitTransactionsPanel, DEBIT_PANEL_TITLE) ;
        
        // add the transactions fields panel
        add(transactionFields, BorderLayout.CENTER) ;
        
        // initialise the buttons panel and add it to the main panel
        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)) ;
        buttonsPanel.setOpaque(false);
        
        // add the settings, save & delete buttons for the transactions panel section
        settingsAction = new AppAction(settingsButton, "Settings"
                                        , true, KeyEvent.VK_S);
        settingsAction.addActionClass(new SettingsUserAction());
        settingsButton = new DepthButton(settingsAction) ;
        buttonsPanel.add(settingsButton) ;
        
        saveTransactionAction = new AppAction(saveTransactionButton, "Save"
                                        , false, KeyEvent.VK_S);
        saveTransactionAction.addActionClass(new UpdateTransactionDetailsAction());
        saveTransactionButton = new DepthButton(saveTransactionAction) ;
        buttonsPanel.add(saveTransactionButton);
        
        deleteTransactionAction = new AppAction(deleteTransactionButton, "Delete"
                                        , false, KeyEvent.VK_S);
        deleteTransactionButton = new DepthButton(deleteTransactionAction) ; 
        deleteTransactionAction.addActionClass(new DeleteTransactionAction());        
        buttonsPanel.add(deleteTransactionButton);
        
        // add the buttons panel to the main panel
        add(buttonsPanel, BorderLayout.SOUTH) ;
        
        // set the initial main panel as the default transactions panel
        cl = (CardLayout) transactionFields.getLayout() ;
        cl.show(transactionFields, CREDIT_PANEL_TITLE);                
            
        //finalise and display panel   
        setOpaque(false);
        setPreferredSize(new Dimension(430, 305));                     
        setBorder(BorderFactory.createTitledBorder("Transaction Details"));
    }
    
    /**
     * Uses a singleton design pattern to obtain a 
     * static instance of the class
     * @return 
     */
    public static BottomRightPanel getInstance()
    {
        if (null == pPanel)
        {
            pPanel = new BottomRightPanel() ;
        }
        
        return pPanel ;
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
    public static void setTransactionDetails(int transactionID)
    {      
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
            
            // determine if the transaction is a credit or debit transaction
            // and display the appropriate panel
            // set the transaction type
            transactionType = Integer.parseInt(transactionDetails[6]) ;
            
            //process the month
            int selMonth = Integer.parseInt(transactionDetails[3]) + 1 ;
            
            if (transactionType == TransactionTypes.CREDIT_TRANSACTION)
            {
                // set the transactions panel as the credit 
                // transactions pane
                cl.show(transactionFields, CREDIT_PANEL_TITLE) ;
            
                String concatDate = transactionDetails[2] + "/" + String.valueOf(selMonth)
                        + "/" + transactionDetails[4] ;

                date.setText(concatDate) ;
                
                // update the JComboBox & repaint
                items.setSelectedItem(transactionDetails[0]) ;
                items.repaint();
                
                numberOfItems.setText(transactionDetails[1]);
                        
                // if the item type is Cash, then set the label indicated
                // as 'Number of items' as 'Amount'
                if ("Cash".equals((String) items.getSelectedItem()))
                { 
                    itemNumberLabel.setText("Amount");
                }
                else
                {
                    itemNumberLabel.setText("Number of Items");
                }
                
                // add the transaction notes to the transactions textarea
                transactionNotes.setText(transactionDetails[5]);  
                
            }
            else // create and populate the debit transactions panel
            {
                // set the transactions panel to the debit transactions
                // panel and populate the various fields in the pane
                cl.show(transactionFields, DEBIT_PANEL_TITLE) ;
                
                // populate the fields in the debit pane                
                // create the date
                String concatDate = transactionDetails[2] + "/" + String.valueOf(selMonth)
                        + "/" + transactionDetails[4] ;

                // set the debit date
                debitAmount.setText(transactionDetails[1]) ;
                
                // set the debit amount
                debitDate.setText(concatDate);
                
                // add the transaction notes to the transactions textarea
                debitTransactionNotes.setText(transactionDetails[5]);      
            }
           
            // enable the save and delete transaction buttons
            saveTransactionAction.enableAction(true);
            deleteTransactionAction.enableAction(true);
            
            // also enable the same option in the menubar
            MenuBar.enableTransactionOptions(true);
        }  
        else
        {
            // in this case, the user wants all the fields 
            // for transactions cleared
            // set the credit fields to null
            date.setText("") ;
            numberOfItems.setText("");
            items.setSelectedIndex(0);
            transactionNotes.setText("");
            
            // set the debit fields to null
             debitAmount.setText("") ;
                
            // set the debit amount
            debitDate.setText("");
                
            // add the transaction notes to the transactions textarea
            debitTransactionNotes.setText("") ;
            
            // reset the current transaction ID
            currTransactionID = -1 ;
        }
            
    }
}
