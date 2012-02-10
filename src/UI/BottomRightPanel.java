/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import AppActions.AppAction;
import AppActions.DeleteTransactionAction;
import AppActions.MenuBar;
import AppActions.UpdateTransactionDetailsAction;
import DbConnection.ItemsDetails;
import DbConnection.TransactionDetails;
import UI.Models.ItemsModel;
import java.awt.Dimension;
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
    private static AppAction deleteTransactionAction ;
    public static Vector itemsObt ;
    public static int transactionType ; // will store the transaction type
    
    public BottomRightPanel()
    {
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
                                        , false, KeyEvent.VK_S);
        saveTransactionAction.addActionClass(new UpdateTransactionDetailsAction());
        saveTransactionButton = new DepthButton(saveTransactionAction) ;
        
        deleteTransactionAction = new AppAction(deleteTransactionButton, "Delete"
                                        , false, KeyEvent.VK_S);
        deleteTransactionButton = new DepthButton(deleteTransactionAction) ; 
        deleteTransactionAction.addActionClass(new DeleteTransactionAction());
        
        // lay out the elements
        layout = new GroupLayout(this);
        setLayout(layout);
        
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
                    .addComponent(transNotesScrollpane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(settingsButton)
                        .addComponent(saveTransactionButton)
                        .addComponent(deleteTransactionButton))));
        
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
                        .addComponent(transNotesScrollpane)))
                .addGroup(layout.createParallelGroup()
                    .addComponent(settingsButton)
                    .addComponent(saveTransactionButton)
                    .addComponent(deleteTransactionButton)));
            
        //finalise and display panel   
        setOpaque(false);
        setPreferredSize(new Dimension(430, 305));                     
        setBorder(BorderFactory.createTitledBorder("Transaction Details"));
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
            deleteTransactionAction.enableAction(true);
            
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
}
