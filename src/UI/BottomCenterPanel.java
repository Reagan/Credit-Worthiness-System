/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import AppActions.AppAction;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class BottomCenterPanel extends JPanel
{
    private DepthButton createNewButton ;
    private DepthButton deleteButton ;
    private JList transactionsList ;
    private String labels[] = {"Milk, 25th Jan", "Rice, 26th Jan", "Choclate, 27th Jan", 
        "Milo, 28th Jan", "Cocoa & Bread, 29th Jan", "Milk, 14th Feb", "Baby Powder, 19th Feb", 
        "Royco, 21st Feb", "Vaseline, 22nd Feb", "Loan, 1st March","Milk, 25th Jan", "Rice, 26th Jan", "Choclate, 27th Jan", 
        "Milo, 28th Jan", "Cocoa & Bread, 29th Jan", "Milk, 14th Feb", "Baby Powder, 19th Feb", 
        "Royco, 21st Feb", "Vaseline, 22nd Feb", "Loan, 1st March", "Milk, 25th Jan", "Rice, 26th Jan", "Choclate, 27th Jan", 
        "Milo, 28th Jan", "Cocoa & Bread, 29th Jan", "Milk, 14th Feb", "Baby Powder, 19th Feb", 
        "Royco, 21st Feb", "Vaseline, 22nd Feb", "Loan, 1st March"};

    
    public BottomCenterPanel()
    {
        // initialise the variables
        AppAction createTransaction = new AppAction(createNewButton, "Create"
                                        , true, KeyEvent.VK_T);
        createNewButton = new DepthButton(createTransaction);
        
        
        AppAction deleteTransaction = new AppAction(deleteButton, "Delete"
                                        , false, KeyEvent.VK_L);
        deleteButton = new DepthButton(deleteTransaction);
        
        
        transactionsList = new JList(labels);
        transactionsList.setOpaque(false);
        
        // lay out the components
        setLayout(new BorderLayout());
        JScrollPane scroll = new JScrollPane(transactionsList);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        scroll.setOpaque(false);
        
        add(scroll, BorderLayout.CENTER);
        
        JPanel buttonsPane = new JPanel();
        buttonsPane.setOpaque(false);
        buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.X_AXIS));
        buttonsPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        buttonsPane.add(createNewButton);
        buttonsPane.add(Box.createHorizontalGlue());
        buttonsPane.add(deleteButton);
        
        add(buttonsPane, BorderLayout.SOUTH);
        
        //finalise and display panel        
        setOpaque(false);        
        setPreferredSize(new Dimension(278, 295));
        setBorder(BorderFactory.createTitledBorder("Transactions"));
    }
}
