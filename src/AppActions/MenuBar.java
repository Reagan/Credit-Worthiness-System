/**
 * Credit Worthiness System Version 1.0
 */
package AppActions;

import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class MenuBar extends JMenuBar
{
    // JMenu Items
    private JMenu fileMenu ; 
    private JMenu transactionsMenu ;
    private JMenu printMenu ;
    private JMenu aboutMenu ;
    
    // JMenuBarItems
    private JMenuItem newUserMenuItem ;
    private JMenuItem deleteCurrentUserMenuItem ;
    private JMenuItem changeSettingsMenuItem ;
    private JMenuItem exitMenuItem;
    private JMenuItem newTransactionMenuItem ;
    private JMenuItem deleteTransactionMenuItem ;
    private JMenuItem saveTransactionDetailsMenuItem ;
    private JMenuItem deleteTransactionDetailsMenuItem ;
    private JMenuItem userTransLogMenuItem ;
    private JMenuItem monthlyReportMenuItem ;
    private JMenuItem pendingCreditItemsMenuItem ;
    private JMenuItem aboutMenuItem ;
    
    public MenuBar()
    {
        fileMenu = new JMenu("File") ; 
        transactionsMenu = new JMenu("Transactions");
        printMenu = new JMenu("Print");
        aboutMenu = new JMenu("About");

        // JMenuBarItems
        newUserMenuItem = new JMenuItem();
        deleteCurrentUserMenuItem = new JMenuItem();
        changeSettingsMenuItem = new JMenuItem();
        exitMenuItem = new JMenuItem();
        newTransactionMenuItem = new JMenuItem();
        deleteTransactionMenuItem = new JMenuItem();
        saveTransactionDetailsMenuItem = new JMenuItem();
        deleteTransactionDetailsMenuItem = new JMenuItem();
        userTransLogMenuItem = new JMenuItem();
        monthlyReportMenuItem = new JMenuItem();
        pendingCreditItemsMenuItem = new JMenuItem();
        aboutMenuItem = new JMenuItem();

        // Build the MenuBar
        buildMenuBar();
    }

    private void buildMenuBar() 
    {
        // add the JMenus
        add(fileMenu);
        add(transactionsMenu);
        add(printMenu);
        add(aboutMenu);
        
        // add the File JMenuItems
        AppAction newUserAction = new AppAction(newUserMenuItem, "New User"
                                    , true , KeyEvent.VK_N);
        newUserMenuItem.setAction(newUserAction);
        fileMenu.add(newUserMenuItem);
        
        
        AppAction deleteCurrentUserAction = new AppAction(deleteCurrentUserMenuItem, 
                                              "Delete Current User", false , KeyEvent.VK_D);
        deleteCurrentUserMenuItem.setAction(deleteCurrentUserAction);
        fileMenu.add(deleteCurrentUserMenuItem);
        
        
        AppAction changeSettingsAction = new AppAction(changeSettingsMenuItem, 
                                                "Change Settings", true, KeyEvent.VK_G);
        changeSettingsMenuItem.setAction(changeSettingsAction);
        fileMenu.add(changeSettingsMenuItem);
              
        fileMenu.addSeparator();
        
        AppAction exitAction = new AppAction(exitMenuItem, 
                                               "Exit", true , KeyEvent.VK_X);
        exitMenuItem.setAction(exitAction);
        fileMenu.add(exitMenuItem);
        
        
        // add the Transactions JMenuItems
        AppAction newTransactionAction = new AppAction(newTransactionMenuItem,
                                      "New Transaction", true, KeyEvent.VK_T);
        newTransactionMenuItem.setAction(newTransactionAction);
        transactionsMenu.add(newTransactionMenuItem);
        
        AppAction deleteTransactionAction = new AppAction(deleteTransactionMenuItem,
                                       "Delete Transaction", false, KeyEvent.VK_E);
        deleteTransactionMenuItem.setAction(deleteTransactionAction);
        transactionsMenu.add(deleteTransactionMenuItem);
        
        AppAction saveTransactionDetailsAction = new AppAction(saveTransactionDetailsMenuItem,
                                      "Save Transaction Details", false, KeyEvent.VK_S);
        saveTransactionDetailsMenuItem.setAction(saveTransactionDetailsAction);
        transactionsMenu.add(saveTransactionDetailsMenuItem);
        
        AppAction deleteTransactionDetailsAction = new AppAction(deleteTransactionDetailsMenuItem,
                                      "Delete Transaction Details", false, KeyEvent.VK_L);
        deleteTransactionDetailsMenuItem.setAction(deleteTransactionDetailsAction);
        transactionsMenu.add(deleteTransactionDetailsMenuItem);
        
        
        // add the Print JMenuItems
        AppAction userTransLogAction = new AppAction(userTransLogMenuItem,
                                       "Print User Transaction Log", false, KeyEvent.VK_P);
        userTransLogMenuItem.setAction(userTransLogAction);
        printMenu.add(userTransLogMenuItem);
        
        AppAction monthlyReportAction = new AppAction(monthlyReportMenuItem,
                                        "Print Monthly Report", true, KeyEvent.VK_R);
        monthlyReportMenuItem.setAction(monthlyReportAction);
        printMenu.add(monthlyReportMenuItem);
        
        AppAction pendingCreditItemsAction = new AppAction(pendingCreditItemsMenuItem,
                                      "Pending Credit Items", true, KeyEvent.VK_C);
        pendingCreditItemsMenuItem.setAction(pendingCreditItemsAction);
        printMenu.add(pendingCreditItemsMenuItem);
        
        
        // add the About JMenuItems
        AppAction aboutAction = new AppAction(aboutMenuItem,
                                        "About", true, KeyEvent.VK_A);
        aboutMenuItem.setAction(aboutAction);
        aboutMenu.add(aboutMenuItem);
    }
    
}
