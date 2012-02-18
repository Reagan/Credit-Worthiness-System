/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import AppActions.EditItemsAction.EditItemsDialog;
import java.awt.Dimension;
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
    private DepthButton deleteButton ;
    private DepthButton saveButton ;
    private DepthButton closeButton ;
    
    // create the Input fields
    private JTextField itemName ;
    private JTextField itemCost ;
    private JList itemsList ;
    private JScrollPane itemsListScrollPane ;
    
    public EditItemsPanel(EditItemsDialog aThis) 
    {
        // initialise all the UI elements
        itemsNameLabel = new JLabel("Items Name"); 
        itemsCostLabel = new JLabel("Items Cost") ;
        
        createItemButton = new DepthButton("Create") ; 
        deleteButton = new DepthButton("Delete");
        saveButton = new DepthButton("Save");
        closeButton = new DepthButton("Close");
        
        itemName = new JTextField();
        itemName.setMaximumSize(new Dimension(155, 25));
        
        itemCost = new JTextField();
        itemCost.setMaximumSize(new Dimension(155, 25));
        
        itemsList = new JList();        
        itemsListScrollPane = new JScrollPane(itemsList);
        itemsListScrollPane.setMaximumSize(new Dimension(130, 180));
        
        // layout the components
        layoutComponents() ;
        
        // set final configs for the panel
        setPreferredSize(new Dimension(330, 240));
        
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
                            .addComponent(deleteButton)
                        )
                    )                
                .addGroup(layout.createParallelGroup()
                    .addComponent(itemsNameLabel)
                    .addComponent(itemName)
                    .addComponent(itemsCostLabel)
                    .addComponent(itemCost)
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(saveButton)
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
                                .addComponent(deleteButton)
                            )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(saveButton)
                                .addComponent(closeButton)
                            )
                    )
         );
    }
}
