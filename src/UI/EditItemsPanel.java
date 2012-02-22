/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import AppActions.EditItemsAction.EditItemsDialog;
import AppActions.*;
import DbConnection.ItemsDetails;
import UI.Listeners.ItemsListener;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.*;

/**
 * This class allows a user to view the existing items 
 * as contained in the database and add additional item details
 * i.e name and cost of item
 * @author reagan mbitiru <reaganmbitiru@gmail.com>
 */
public class EditItemsPanel extends JPanel 
{    
    // create the labels
    private JLabel itemsNameLabel ; 
    private JLabel itemsCostLabel ;
    
    // the buttons
    private DepthButton createItemButton ; 
    private DepthButton deleteItemButton ;
    private DepthButton saveItemDetailsButton ;
    private DepthButton closeButton ;
    
    // create the Input fields
    public JTextField itemName ;
    public JTextField itemCost ;
    public JList itemsList ;
    private JScrollPane itemsListScrollPane ;
    
    public static boolean dirty = false ; // shows when an items details are being edited   
    public static String currentlySelectedItem = null ; // saves the currently selected item
    public static int currentlySelectedItemId = -1 ; // stores the id for the current item
    private ItemsDetails itemsCont ; // stores an instance of the currently selected item
    
    // actions for the buttons
    public AppAction createItemAction ;
    public AppAction deleteItemAction ;
    public AppAction saveItemAction ;
    public AppAction closePanelAction ;
    
    public EditItemsDialog mDialog = null ;
    
    public EditItemsPanel(final EditItemsDialog aThis) 
    {
        // initialise all the UI elements
        itemsNameLabel = new JLabel("Items Name"); 
        itemsCostLabel = new JLabel("Items Cost") ;
        
        // initialise the parent dialog
        mDialog = aThis ;
        
        createItemButton = new DepthButton("Create") ; 
        deleteItemButton = new DepthButton("Delete");
        saveItemDetailsButton = new DepthButton("Save");
        closeButton = new DepthButton("Close");
        
        // initialise the actions and add them to the buttons
        createItemAction = new AppAction(createItemButton, "Create", true, KeyEvent.VK_T) ;
        createItemAction.addActionClass(new CreateItemAction(EditItemsPanel.this));
        createItemButton.setAction(createItemAction);
        
        saveItemAction = new AppAction(saveItemDetailsButton, "Save", false, KeyEvent.VK_L) ;
        saveItemAction.addActionClass(new SaveItemAction(EditItemsPanel.this));
        saveItemDetailsButton.setAction(saveItemAction);
        
        closePanelAction = new AppAction(closeButton, "Close", true, KeyEvent.VK_O) ;
        closePanelAction.addActionClass(new CloseItemsPanelAction(EditItemsPanel.this));
        closeButton.setAction(closePanelAction);                
        
        deleteItemAction = new AppAction(deleteItemButton, "Delete", false, KeyEvent.VK_D) ;
        deleteItemAction.addActionClass(new DeleteItemAction(EditItemsPanel.this));
        deleteItemButton.setAction(deleteItemAction);
        
        
        itemName = new JTextField();
        itemName.setMaximumSize(new Dimension(195, 25));
        itemName.setEditable(false);
        
        itemCost = new JTextField();
        itemCost.setMaximumSize(new Dimension(195, 25));
        itemCost.setEditable(false);
        
        itemsList = new JList();        
        setItemsModel();
        itemsList.revalidate();
        itemsListScrollPane = new JScrollPane(itemsList);
        itemsListScrollPane.setMaximumSize(new Dimension(130, 180));
        
        // add the items listener to the list
        itemsList.addListSelectionListener(new ItemsListener(this));
        itemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // layout the components
        setPreferredSize(new Dimension(390, 270));
        layoutComponents() ;
        
        // add a titled border
        setBorder(BorderFactory.createTitledBorder("Edit Items Details"));
    }
    
    /**
     * This method lays out the components for the Edit Items panel
     */
    private void layoutComponents()
    {
        // initialise the layout obj
        GroupLayout layout = new GroupLayout(this) ;
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        // lay out the components horozontally
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                    .addComponent(itemsListScrollPane)
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(createItemButton)
                            .addComponent(deleteItemButton)
                        )
                    )                
                .addGroup(layout.createParallelGroup()
                    .addComponent(itemsNameLabel)
                    .addComponent(itemName)
                    .addComponent(itemsCostLabel)
                    .addComponent(itemCost)
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(saveItemDetailsButton)
                            .addComponent(closeButton)
                        )
                    )
        );
        
        // layout the components vertically
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(itemsListScrollPane)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(itemsNameLabel)
                            .addComponent(itemName)
                            .addComponent(itemsCostLabel)
                            .addComponent(itemCost)
                        )
                    )
                .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(createItemButton)
                                .addComponent(deleteItemButton)
                            )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(saveItemDetailsButton)
                                .addComponent(closeButton)
                            )
                    )
         );
    }
    
    private String[] getItems() 
    {                
        itemsCont = new ItemsDetails() ;
        
        Vector<String[]> itemsDetailsVector =  itemsCont.getAllItemsDetails() ;
        String[] items = new String[itemsDetailsVector.size()] ;
        
        // loop through the vector obtaining the items details 
        for ( int itemsCounter =  0; itemsCounter <  itemsDetailsVector.size() ;
                itemsCounter++ )
        {
            String[] currItems = itemsDetailsVector.get(itemsCounter) ;
            String currItemName = currItems[1] ;
            items[itemsCounter] = currItemName ;
            
            // save the currently selected item
            if ( currentlySelectedItem == null ) 
            {
                currentlySelectedItem = items[itemsCounter] ;
            }
        }
        return items ;
    }
    
    /**
     * This method saves the details for the currently selected transaction
     */
    public void  saveCurrentItemDetails()
    {
        // get the details of the currently selected item
        if ( itemName.getText().length() < 0 || Integer.parseInt(itemCost.getText()) < 0 )
        {
            JOptionPane.showMessageDialog(null, "Please enter all the details for the item", 
                    "Enter all details", JOptionPane.ERROR_MESSAGE);
            return ;
        }
        
        // and update the database
        if ( itemsCont.updateItemDetails(currentlySelectedItemId, itemName.getText().trim(), 
                Integer.parseInt(itemCost.getText().trim())) )
        {
            JOptionPane.showMessageDialog(null, "The item details were updated successfully",
                    "Item details updated" , JOptionPane.INFORMATION_MESSAGE);
        }
        
        dirty = false ;
    }
    
    /**
     * This method revalidates the items displayed in the list of 
     * items in the JList
     */
    public void revalidateItemsList()
    {
        setItemsModel();
    }
    
    /**
     * This method extracts the names of the items from the database and adds
     * them as the model for the JList displayed
     */
    public void setItemsModel()
    {
        Runnable getItems = new Runnable() 
        {
            @Override
            public void run() 
            {
                DefaultListModel listModel = new DefaultListModel();
                
                // loop through adding the array elements
                String [] items =  getItems() ;
                
                for ( String item : items )
                {
                    listModel.addElement(item);
                }
                
                // set the model to the JList
                itemsList.setModel(listModel) ;
            }
        };
        
        getItems.run();
    }
}
