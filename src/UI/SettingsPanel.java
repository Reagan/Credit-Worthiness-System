/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import AppActions.AppAction;
import AppActions.SettingsUserAction.SettingsDialog;
import AppProperties.AppProperties;
import UI.Listeners.SettingsListener;
import UI.Listeners.SettingsTypesListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * This method creates a JPanel that allows the user 
 * to change the settings for the application
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class SettingsPanel extends JPanel 
{
    public JTabbedPane settingsPane ;
    private SettingsDialog mainDialog ;
    
    public final String generalSettingsTitle = "General Settings" ;
    public final String databaseSettingsTitle = "Database Settings" ;
    public final String chartSettingsTitle = "Chart Settings" ;
    public final String userSettingsTitle = "User Settings" ;
    
    public JPanel generalSettings ;
    public JPanel databaseSettings ;
    public JPanel chartSettings ;
    public JPanel userSettings ;
    private JPanel buttonsPanel ;
    
    private DepthButton saveSettingsButton ;
    private DepthButton cancelSettingsButton ;
    private AppAction saveSettingsAction ;
    private AppAction cancelSettingsAction ;
    
    public static boolean dirty ; // to note when any of the application settings is modified    
    public static int currentSettingsPane  = 0 ; //indicates the current set of settings 
                // being modified by the user
    
    private Properties properties ;
    
    private static final int INPUT_FIELD = 0 ;
    private static final int TEXT_AREA = 1 ;
    private static final int COMBO_BOX = 2 ;
    private static final int CREDIT_DISPLAY_RADIO_GROUP = 3 ;
    private static final int HOVER_POPUP_DISPLAY_RADIO_GROUP = 4 ;
    
    private String[]  chartDisplayMonths = {"Current", "January","February","March",
        "April", "May", "June", "July", "August", "September", "October","November",
        "December" } ;
    private String[] radioGroupOptions = {"Yes", "No"};
    
    public SettingsPanel(SettingsDialog c)
    {
        // initialise the container dialog
        mainDialog = c ;
        
        // Create the JTabbbed pane and the relevant panels for each 
        // of the panes
        settingsPane = new JTabbedPane() ;
        settingsPane.addChangeListener(new SettingsTypesListener()) ;

        // create and add the various tabs
        generateGenerateGeneralSettingsTab();
        generateGenerateDatabaseSettingsTab();
        generateGenerateChartSettingsTab();
        generateGenerateUserSettingsTab();
        
        // create the lower panel with the various buttons
        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)) ;
        buttonsPanel.setPreferredSize(new Dimension(430, 40));
        
        //  create the actions for the lower buttons
        saveSettingsAction = new AppAction(saveSettingsButton, "Save"
                                        , true, KeyEvent.VK_S);
        
        // create the save button
        saveSettingsButton = new DepthButton(saveSettingsAction) ;
        saveSettingsButton.addActionListener(
                            new SettingsListener(SettingsPanel.this));
        
        // create the cancel button
        cancelSettingsAction = new AppAction(cancelSettingsButton, "Cancel"
                                        , true, KeyEvent.VK_S);
        cancelSettingsButton = new DepthButton(cancelSettingsAction) ; 
        cancelSettingsButton.addActionListener(
                            new SettingsListener(SettingsPanel.this));
        
        // add  the buttons to the lowerpanel
        buttonsPanel.add(saveSettingsButton);
        buttonsPanel.add(cancelSettingsButton);
        
        // add the JTabbedPane and the lower panel
        setLayout(new BorderLayout());
        add(settingsPane, BorderLayout.CENTER); 
        add(buttonsPanel, BorderLayout.SOUTH) ;                
        
        // display the panel
        setOpaque(false);                   
    }       
    
    private void generateGenerateGeneralSettingsTab()
    {
        // create and populate the general settings tab
        generalSettings = new JPanel() ;
        generalSettings.setPreferredSize(new Dimension(430, 235));        
        generalSettings.setLayout(new BoxLayout(generalSettings, BoxLayout.Y_AXIS));
        
        // create the input fields for the Panel
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(430, 105));
        
        // create the labels
        JLabel appNameLabel = new JLabel("Application Name") ;
        JLabel appVersionLabel = new JLabel("Application Version") ;
        
        JTextField appName = (JTextField) createUserInputField(INPUT_FIELD, "appName");
        //appName.setEditable(false);
        
        JTextField appVersion = (JTextField) createUserInputField(INPUT_FIELD, "appVersion");
        //appVersion.setEditable(false);
        
        JTextArea appInfo = (JTextArea) createUserInputField(TEXT_AREA, "appInfo");
        appInfo.setPreferredSize(new Dimension(400, 120));
        appInfo.setBorder(BorderFactory.createTitledBorder("Application Information"));
        //appInfo.setEditable(false);
        
        // add the components to the top Panel
        topPanel.setLayout(new GridLayout(2,2));
        topPanel.add(appNameLabel,0,0);
        topPanel.add(appName,0,1);
        topPanel.add(appVersionLabel,1,0);
        topPanel.add(appVersion,1,1);
        
        // add the components to the tabbed pane
        generalSettings.add(topPanel);
        generalSettings.add(appInfo);
        
        // add the tab
        settingsPane.addTab(generalSettingsTitle, generalSettings );
    }
    
    private void generateGenerateDatabaseSettingsTab()
    {
        
        // create and populate the database settings tab
        databaseSettings = new JPanel() ;
        databaseSettings.setPreferredSize(new Dimension(430, 235));       
        databaseSettings.setLayout(new BoxLayout(databaseSettings, BoxLayout.Y_AXIS));
        
        // create the container top panel
       JPanel topPanel = new JPanel(); 
       topPanel.setLayout(new GridLayout(5,2));
       topPanel.setPreferredSize(new Dimension(430,180));
       
       // create & add the labels
       JLabel dbNameLabel = new JLabel("Database Name");
       topPanel.add(dbNameLabel, 1,0) ;
       
       JLabel dbHostLabel = new JLabel("Database Host");
       topPanel.add(dbHostLabel, 2,0) ;
       
       JLabel dbPortLabel = new JLabel("Database Port");
       topPanel.add(dbPortLabel, 3,0) ;
       
       JLabel dbUserNameLabel = new JLabel("Database Username");
       topPanel.add(dbUserNameLabel, 4,0) ;
       
       JLabel dbPasswordLabel = new JLabel("Database Password");
       topPanel.add(dbPasswordLabel, 5,0) ;
       
       // create and add the input text fields
       JTextField dbName = (JTextField) createUserInputField(INPUT_FIELD, "dbName");
       topPanel.add(dbName, 0 ,1);
       
       JTextField dbHost = (JTextField) createUserInputField(INPUT_FIELD, "dbHost");
       topPanel.add(dbHost, 1 ,1);

       JTextField dbPort = (JTextField) createUserInputField(INPUT_FIELD, "dbPort");
       topPanel.add(dbPort, 2 ,1);

       JTextField dbUsername = (JTextField) createUserInputField(INPUT_FIELD, "dbUsername");
       topPanel.add(dbUsername, 3 ,1);

       JTextField dbPassword = (JTextField) createUserInputField(INPUT_FIELD, "dbPassword");
       topPanel.add(dbPassword, 4 ,1);
       
       // create the database connection string 
       JTextArea connectionString = (JTextArea) createUserInputField(TEXT_AREA, "dbConnectionString");
       connectionString.setPreferredSize(new Dimension(400, 120));
       connectionString.setBorder(BorderFactory.createTitledBorder("Database Connection String"));
       
       // add the sections
       databaseSettings.add(topPanel);
       databaseSettings.add(connectionString);
       
       // add the panel to the tab
       settingsPane.addTab(databaseSettingsTitle, databaseSettings); 
    }
        
    private void generateGenerateChartSettingsTab()
    {
        
        // create and populate the chart settings tab
        chartSettings = new JPanel();
        chartSettings.setPreferredSize(new Dimension(430, 235));                     
        chartSettings.setLayout(new GridLayout(3,2));
        
        //  create & add the labels
        JLabel displayCreditPlotLabel = new JLabel("Display Credit Plot");
        chartSettings.add(displayCreditPlotLabel,0,0);
        
        JLabel hoverPopupLabel = new JLabel ("Always display chart popups") ;
        chartSettings.add(hoverPopupLabel, 1,0);
        
        JLabel initialMonthLabel = new JLabel("Initial chart month displayed") ;
        chartSettings.add(initialMonthLabel, 2,0);
        
        // create the components and add 
        JPanel creditRadioGroup = (JPanel) createUserInputField(CREDIT_DISPLAY_RADIO_GROUP,
                "creditPlotDisplayed");
        chartSettings.add(creditRadioGroup,0,1);
        
        JPanel hoverTransPopup = (JPanel) createUserInputField(HOVER_POPUP_DISPLAY_RADIO_GROUP, 
                "chartHoverPopupAlwaysDisplayed");
        chartSettings.add(hoverTransPopup,1,1);
        
        JComboBox monthsList = (JComboBox) createUserInputField(COMBO_BOX,
                "chartInitialMonthDisplayed");
        chartSettings.add(monthsList, 2,1);
        
        // add the main panel to the tabbed pane
        settingsPane.addTab(chartSettingsTitle, chartSettings);
    }
            
    private void generateGenerateUserSettingsTab()
    {
        
        // create and populate the user settings tab
        userSettings = new JPanel() ;
        userSettings.setPreferredSize(new Dimension(430, 235));                     
        
        // create the labels, buttons and textfields 
        JLabel imagesLocationLabel = new JLabel("Images Location");
        JLabel defaultImagesLocation = new JLabel("Default User Image Icon");
        
        JTextField imagesLocation = (JTextField) createUserInputField(INPUT_FIELD, 
                "imagesLocation");
        UserImage pictureArea = new UserImage();
        
        DepthButton browseButton = new DepthButton("Browse") ; // @TODO: replace arg with action
        DepthButton changeButton = new DepthButton("Change...");
        
        // add the components to the userSettingsPanel
        GroupLayout usLayout = new GroupLayout(userSettings);
        userSettings.setLayout(usLayout);
        
        // specify the horizontal layout
        usLayout.setHorizontalGroup(usLayout.createSequentialGroup()
                .addGroup(usLayout.createParallelGroup()
                    .addComponent(imagesLocationLabel)
                    .addComponent(defaultImagesLocation)
                    )
                .addGroup(usLayout.createParallelGroup()
                    .addComponent(imagesLocation)
                    .addComponent(browseButton)
                    .addComponent(pictureArea)
                    .addComponent(changeButton)
                    )
            );
        
        // specify the vertical layout
        usLayout.setVerticalGroup(usLayout.createSequentialGroup()
                    .addGroup(usLayout.createParallelGroup()
                        .addComponent(imagesLocationLabel)
                        .addComponent(imagesLocation)                
                        )
                    .addComponent(browseButton)
                    .addGroup(usLayout.createParallelGroup()
                        .addComponent(defaultImagesLocation)
                        .addComponent(pictureArea)
                        )
                    .addComponent(changeButton)
                );
        
        // add the user settings panel to the tabbed Pane
        settingsPane.addTab(userSettingsTitle, userSettings); 
    }
    
    /**
     * This method creates each of the fields that populates 
     * the various tabs of the Settings window and loads them with the 
     * settings as captured in the configs file
     * @param fieldType
     * @param label
     * @param configOptionName
     * @return 
     */
    private JComponent createUserInputField(int fieldType,
            String configOptionName)
    {  
       switch(fieldType)
       {
           case(INPUT_FIELD):
               JTextField comp = new JTextField();               
               comp.setPreferredSize(new Dimension(120, 20));
               comp.setMaximumSize(new Dimension(120, 20));
               //comp.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
               //comp.setText(AppProperties.getInstance().getProperty(configOptionName));
               return comp ;
               
           case(TEXT_AREA):
               JTextArea comp1 = new JTextArea() ;
               //comp1.setText(AppProperties.getInstance().getProperty(configOptionName));
               return comp1 ;
               
           case(COMBO_BOX):
               JComboBox comp2 = new JComboBox(chartDisplayMonths);
               //comp2.setSelectedItem(AppProperties.getInstance().getProperty(configOptionName));
               return comp2;           
                   
           case(CREDIT_DISPLAY_RADIO_GROUP):
           case(HOVER_POPUP_DISPLAY_RADIO_GROUP):
               JRadioButton yes = new JRadioButton("Yes", 
                       //(AppProperties.getInstance().getProperty(configOptionName).equals("Yes"))
                       false);               
               JRadioButton no = new JRadioButton("No", 
                       // (AppProperties.getInstance().getProperty(configOptionName).equals("No"))
                       false); 
               
               ButtonGroup bt = new ButtonGroup();
               bt.add(yes);
               bt.add(no);
               
               JPanel radioPanel = new JPanel();
               radioPanel.setLayout(new GridLayout(1,2));
               radioPanel.add(yes) ;
               radioPanel.add(no) ;
               
               return radioPanel;        
               
           default: 
               return null ;
       }       
    }                
}