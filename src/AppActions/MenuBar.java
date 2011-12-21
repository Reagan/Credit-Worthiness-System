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
    
    // Add the actions
    private static AppAction newUserAction ;
    private static AppAction deleteCurrentUserAction ;
    private static AppAction changeSettingsAction ;
    private static AppAction exitAction ;
    private static AppAction newTransactionAction ;
    private static AppAction deleteTransactionAction ;
    private static AppAction saveTransactionDetailsAction ;
    private static AppAction deleteTransactionDetailsAction ;
    private static AppAction userTransLogAction ;
    private static AppAction monthlyReportAction ;
    private static AppAction pendingCreditItemsAction ;
    private static AppAction aboutAction  ;
        
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
        newUserAction = new AppAction(newUserMenuItem, "New User"
                                    , true , KeyEvent.VK_N);
        newUserAction.addActionClass(new NewUserAction());
        newUserMenuItem.setAction(newUserAction);
        fileMenu.add(newUserMenuItem);
        
        
        deleteCurrentUserAction = new AppAction(deleteCurrentUserMenuItem, 
                                              "Delete Current User", false , KeyEvent.VK_D);
        deleteCurrentUserAction.addActionClass(new DeleteUserAction());
        deleteCurrentUserMenuItem.setAction(deleteCurrentUserAction);
        fileMenu.add(deleteCurrentUserMenuItem);
        
        
        changeSettingsAction = new AppAction(changeSettingsMenuItem, 
                                                "Change Settings", true, KeyEvent.VK_G);
        changeSettingsMenuItem.setAction(changeSettingsAction);
        fileMenu.add(changeSettingsMenuItem);
              
        fileMenu.addSeparator();
        
        exitAction = new AppAction(exitMenuItem, 
                                               "Exit", true , KeyEvent.VK_X);
        exitAction.addActionClass(new ExitAction());
        exitMenuItem.setAction(exitAction);
        fileMenu.add(exitMenuItem);
        
        
        // add the Transactions JMenuItems
        newTransactionAction = new AppAction(newTransactionMenuItem,
                                      "New Transaction", false, KeyEvent.VK_T);
        newTransactionAction.addActionClass(new NewTransactionAction());
        newTransactionMenuItem.setAction(newTransactionAction);
        transactionsMenu.add(newTransactionMenuItem);
        
        deleteTransactionAction = new AppAction(deleteTransactionMenuItem,
                                       "Delete Transaction", false, KeyEvent.VK_E);
        deleteTransactionAction.addActionClass(new DeleteTransactionAction());
        deleteTransactionMenuItem.setAction(deleteTransactionAction);
        transactionsMenu.add(deleteTransactionMenuItem);
        
        saveTransactionDetailsAction = new AppAction(saveTransactionDetailsMenuItem,
                                      "Save Transaction Details", false, KeyEvent.VK_S);
        saveTransactionDetailsMenuItem.setAction(saveTransactionDetailsAction);
        transactionsMenu.add(saveTransactionDetailsMenuItem);        
        
        // add the Print JMenuItems
        userTransLogAction = new AppAction(userTransLogMenuItem,
                                       "Print User Transaction Log", false, KeyEvent.VK_P);
        userTransLogMenuItem.setAction(userTransLogAction);
        userTransLogAction.addActionClass(new PrintUserTransactionLogAction()) ;
        printMenu.add(userTransLogMenuItem);
        
        monthlyReportAction = new AppAction(monthlyReportMenuItem,
                                        "Print Monthly Report", true, KeyEvent.VK_R);
        monthlyReportMenuItem.setAction(monthlyReportAction);
        monthlyReportAction.addActionClass(new PrintMonthlyReportAction()) ;
        printMenu.add(monthlyReportMenuItem);
        
        pendingCreditItemsAction = new AppAction(pendingCreditItemsMenuItem,
                                      "Pending Credit Items", true, KeyEvent.VK_C);
        pendingCreditItemsMenuItem.setAction(pendingCreditItemsAction);
        pendingCreditItemsAction.addActionClass(new PrintCreditItemsAction()) ;
        printMenu.add(pendingCreditItemsMenuItem);
                
        // add the About JMenuItems
        aboutAction = new AppAction(aboutMenuItem,
                                        "About", true, KeyEvent.VK_A);
        aboutAction.addActionClass(new AboutAction());
        aboutMenuItem.setAction(aboutAction);
        aboutMenu.add(aboutMenuItem);
    }
 
    /**
     * This method enables user options enabled 
     * once a user is selected 
     */
    public static void enableUserMenuOptions(boolean state)
    {
        deleteCurrentUserAction.enableAction(true);
        newTransactionAction.enableAction(true);
        userTransLogAction.enableAction(true);
    }
}
