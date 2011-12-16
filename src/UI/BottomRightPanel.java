/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import AppActions.AppAction;
import DbConnection.ItemsDetails;
import DbConnection.TransactionDetails;
import UI.Models.ItemsModel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class BottomRightPanel extends JPanel
{
    private DepthButton settingsButton ;
    private DepthButton saveButton ;
    private DepthButton deleteButton ;
    private DepthButton printLogButton ;
    
    private JLabel dateLabel ;
    private JLabel itemLabel ;
    private JLabel itemNumberLabel ;
    
    private static JTextField date ;
    private static JTextField numberOfItems ;
    private final static JComboBox items = new JComboBox(); ;
    
    private JLabel notesLabel ;
    private static  JTextArea transactionNotes ;
    
    private JSeparator verticalSeparator ;      
    
    // this displays the list of items 
    // order as they are in the database
    private Vector list = null ;
    
    private GroupLayout layout;
    
    public BottomRightPanel()
    {
        // initialise the variablesprivate JLabel dateLabel ;
        // labels
        dateLabel = new JLabel("Date");
        dateLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        
        itemLabel = new JLabel("Item");
        itemLabel.setBorder(BorderFactory.createEmptyBorder(20, 5, 0, 5));
        
        itemNumberLabel = new JLabel("Number of Items");
        itemNumberLabel.setBorder(BorderFactory.createEmptyBorder(20, 5, 0, 5));
    
        // TextFields
        date = new JTextField();
        date.setMaximumSize(new Dimension(136, 20));
        
        numberOfItems = new JTextField();
        numberOfItems.setMaximumSize(new Dimension(136, 20));
                
        // set the dimensions for the maximum size
        items.setMaximumSize(new Dimension(136, 20));
        
        // populate the list
        getItems();
        
        notesLabel = new JLabel("Notes");
        
        transactionNotes = new JTextArea();
        transactionNotes.setMaximumSize(new Dimension(343, 198));
        
        verticalSeparator = new JSeparator(SwingConstants.VERTICAL);
        
        // buttons
        AppAction settingsAction = new AppAction(settingsButton, "Settings"
                                        , false, KeyEvent.VK_S);
        settingsButton = new DepthButton(settingsAction) ;
        
        AppAction saveAction = new AppAction(saveButton, "Save"
                                        , false, KeyEvent.VK_S);
        saveButton = new DepthButton(saveAction) ;
        
        AppAction deleteAction = new AppAction(deleteButton, "Delete"
                                        , false, KeyEvent.VK_S);
        deleteButton = new DepthButton(deleteAction) ;         
        
        AppAction printLogAction = new AppAction(printLogButton, "Print Transactions Log"
                                        , true, KeyEvent.VK_S);
        printLogButton = new DepthButton(printLogAction) ;
        
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
                    .addComponent(transactionNotes)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(settingsButton)
                        .addComponent(saveButton)
                        .addComponent(deleteButton))));
        
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
                        .addComponent(transactionNotes)))
                .addGroup(layout.createParallelGroup()
                    .addComponent(settingsButton)
                    .addComponent(saveButton)
                    .addComponent(deleteButton)));
            
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
                return itemNames.getItemNames() ;
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
            // obtain information from the database
            // and update the fields in this section
            TransactionDetails transDetails =  new TransactionDetails() ;
            
            String [] transactionDetails = transDetails
                    .getTransactionDetails(transactionID) ;
            JOptionPane.showMessageDialog(null, transactionDetails.toString(), 
                    "Transaction Details", JOptionPane.PLAIN_MESSAGE);
            
            String concatDate = transactionDetails[2] + "/" + transactionDetails[3]
                    + "/ " + transactionDetails[4] ;
            
            date.setText(concatDate) ;
            numberOfItems.setText(transactionDetails[1]);
            
            // update the JComboBox & repaint
            items.setSelectedIndex(Integer.parseInt(transactionDetails[0]) - 1 );
            items.repaint();
            
            transactionNotes.setText("Info");   
        }                
    }
}
