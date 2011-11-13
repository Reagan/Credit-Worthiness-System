/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import AppActions.AppAction;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
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
    
    private JTextField date ;
    private JTextField numberOfItems ;
    private JComboBox items ;
    
    private JLabel notesLabel ;
    private JTextArea transactionNotes ;
    
    private JSeparator verticalSeparator ;      
    
    String list[] = {"Royco", "Milk","Choclate"};
    
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
        
        items = new JComboBox(list);
        items.setMaximumSize(new Dimension(136, 20));
        
        notesLabel = new JLabel("Notes");
        
        transactionNotes = new JTextArea("important areas ");
        transactionNotes.setMaximumSize(new Dimension(343, 208));
        
        verticalSeparator = new JSeparator(SwingConstants.VERTICAL);
        
        // buttons
        AppAction settingsAction = new AppAction(settingsButton, "Settings"
                                        , true, KeyEvent.VK_S);
        settingsButton = new DepthButton(settingsAction) ;
        
        AppAction saveAction = new AppAction(saveButton, "Save"
                                        , true, KeyEvent.VK_S);
        saveButton = new DepthButton(saveAction) ;
        
        AppAction deleteAction = new AppAction(deleteButton, "Delete"
                                        , true, KeyEvent.VK_S);
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
        setPreferredSize(new Dimension(410, 295));                     
        setBorder(BorderFactory.createTitledBorder("Transaction Details"));
    }
}
