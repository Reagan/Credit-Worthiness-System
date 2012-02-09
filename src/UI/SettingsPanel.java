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
import javax.swing.*;

/**
 * This method creates a JPanel that allows the user 
 * to change the settings for the application
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class SettingsPanel extends JPanel 
{
    public JTabbedPane settingsPane ;
    private SettingsDialog mainDialog ;
    
    public final String generalSettingsTitle = "General" ;
    public final String databaseSettingsTitle = "Database" ;
    public final String chartSettingsTitle = "Chart" ;
    public final String userSettingsTitle = "User" ;
    
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
    private String[] dbTypes = { "MySQL","H2"};
    private final Dimension panelDimension = new Dimension(330, 235); 
    
    // add all the relevant input text fields
    // combo boxes and textareas
    public JTextField appName  ;
    private JTextField appVersion ;
    private JTextArea appInfo ;
    private JTextField dbName ;       
    private JTextField dbHost ;
    private JTextField dbPort ;
    private JTextField dbUsername ;
    private JTextField dbPassword ;
    private JComboBox connectionType ;
    private JPanel creditRadioGroup ;
    private JPanel hoverTransPopup ;
    private JComboBox monthsList ;
    private JTextField imagesLocation ;
    private UserImage pictureArea ;

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
        buttonsPanel.setPreferredSize(new Dimension(panelDimension.width, 40));
        
        //  create the actions for the lower buttons
        saveSettingsAction = new AppAction(saveSettingsButton, "Save"
                                        , true, KeyEvent.VK_S);
        saveSettingsButton = new DepthButton(saveSettingsAction) ;
        saveSettingsButton.setActionCommand("save");
        saveSettingsButton.addActionListener(
                            new SettingsListener(mainDialog, SettingsPanel.this));
        
        // create the cancel button
        cancelSettingsAction = new AppAction(cancelSettingsButton, "Cancel"
                                        , true, KeyEvent.VK_S);
        cancelSettingsButton = new DepthButton(cancelSettingsAction) ; 
        cancelSettingsButton.setActionCommand("cancel");
        cancelSettingsButton.addActionListener(
                            new SettingsListener(mainDialog, SettingsPanel.this));
        
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
        generalSettings.setPreferredSize(panelDimension);        
        
        // create the labels
        JLabel appNameLabel = new JLabel("Application Name") ;
        JLabel appVersionLabel = new JLabel("Application Version") ;
        
        appName = (JTextField) createUserInputField(INPUT_FIELD, "appName");
        appName.setEditable(false);
        
        appVersion = (JTextField) createUserInputField(INPUT_FIELD, "appVersion");
        appVersion.setEditable(false);
        
        appInfo = (JTextArea) createUserInputField(TEXT_AREA, "aboutInfo");
        appInfo.setPreferredSize(new Dimension(panelDimension.width-30, 120));
        appInfo.setBorder(BorderFactory.createTitledBorder("Application Information"));
        appInfo.setLineWrap(true);
        appInfo.setWrapStyleWord(true);        
        
        // layout the components for the settings panel
        GroupLayout layout = new GroupLayout(generalSettings) ;
        generalSettings.setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        
        // add horizontally
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                            .addComponent(appNameLabel)
                            .addComponent(appVersionLabel)
                         )
                         .addGroup(layout.createParallelGroup()
                            .addComponent(appName)
                            .addComponent(appVersion)
                         )
                    )
                .addComponent(appInfo)
             );
        
        // add vertically
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                    .addComponent(appNameLabel)
                    .addComponent(appName)
                  )
                .addGroup(layout.createParallelGroup()
                    .addComponent(appVersionLabel)
                    .addComponent(appVersion)
                  )
                .addComponent(appInfo)
           );
                
        // add the tab
        settingsPane.addTab(generalSettingsTitle, generalSettings );
    }
    
    private void generateGenerateDatabaseSettingsTab()
    {
        
        // create and populate the database settings tab
        databaseSettings = new JPanel() ;
        databaseSettings.setPreferredSize(panelDimension);       
        databaseSettings.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));    
       
       // create & add the labels
       JLabel dbNameLabel = new JLabel("Database Name");
       JLabel dbHostLabel = new JLabel("Database Host");       
       JLabel dbPortLabel = new JLabel("Database Port");       
       JLabel dbUsernameLabel = new JLabel("Database Username");       
       JLabel dbPasswordLabel = new JLabel("Database Password");
       JLabel dbTypeLabel = new JLabel("Database Type");
       
       // create and add the input text fields
       dbName = (JTextField) createUserInputField(INPUT_FIELD, "dbName");       
       dbHost = (JTextField) createUserInputField(INPUT_FIELD, "dbHost");
       dbPort = (JTextField) createUserInputField(INPUT_FIELD, "dbPort");
       dbUsername = (JTextField) createUserInputField(INPUT_FIELD, "dbUsername");
       dbPassword = (JTextField) createUserInputField(INPUT_FIELD, "dbPassword");
       connectionType = (JComboBox) createUserInputField(COMBO_BOX, "dbType");       
       
       // create the layout
       GroupLayout layout = new GroupLayout(databaseSettings); 
       databaseSettings.setLayout(layout);
       layout.setAutoCreateContainerGaps(true);
       layout.setAutoCreateGaps(true);
       
       // add horizontally
       layout.setHorizontalGroup(layout.createParallelGroup()
            .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                            .addComponent(dbNameLabel)
                            .addComponent(dbHostLabel)
                            .addComponent(dbPortLabel)
                            .addComponent(dbUsernameLabel)
                            .addComponent(dbPasswordLabel)
                            .addComponent(dbTypeLabel)
                        )
                    .addGroup(layout.createParallelGroup()
                            .addComponent(dbName)
                            .addComponent(dbHost)
                            .addComponent(dbPort)
                            .addComponent(dbUsername)
                            .addComponent(dbPassword)
                            .addComponent(connectionType)
                        )
               )   
            .addComponent(connectionType)     
          );
       
       // add vertically
       layout.setVerticalGroup(layout.createSequentialGroup()
               .addGroup(layout.createParallelGroup()
                    .addComponent(dbNameLabel)
                    .addComponent(dbName)
                )
               .addGroup(layout.createParallelGroup()
                    .addComponent(dbHostLabel)
                    .addComponent(dbHost)
                )
               .addGroup(layout.createParallelGroup()
                    .addComponent(dbPortLabel)
                    .addComponent(dbPort)
                )
               .addGroup(layout.createParallelGroup()
                    .addComponent(dbUsernameLabel)
                    .addComponent(dbUsername)
                )
               .addGroup(layout.createParallelGroup()
                    .addComponent(dbPasswordLabel)
                    .addComponent(dbPassword)
                )
               .addGroup(layout.createParallelGroup()
                    .addComponent(dbTypeLabel)
                    .addComponent(connectionType)
                )
               .addComponent(connectionType)
             );
       
       // add the panel to the tab
       settingsPane.addTab(databaseSettingsTitle, databaseSettings); 
    }
        
    private void generateGenerateChartSettingsTab()
    {
        
        // create and populate the chart settings tab
        chartSettings = new JPanel();
        chartSettings.setPreferredSize(panelDimension);                     
        chartSettings.setLayout(new GridLayout(3,2));
        
        //  create & add the labels
        JLabel displayCreditPlotLabel = new JLabel("Display Credit Plot");        
        JLabel hoverPopupLabel = new JLabel ("Always display chart popups") ;        
        JLabel initialMonthLabel = new JLabel("Initial chart month displayed") ;
        
        // create the components and add 
        creditRadioGroup = (JPanel) createUserInputField(CREDIT_DISPLAY_RADIO_GROUP,
                "creditPlotDisplayed");
        hoverTransPopup = (JPanel) createUserInputField(HOVER_POPUP_DISPLAY_RADIO_GROUP, 
                "hoverTransactionPopupDefaultOn");
        monthsList = (JComboBox) createUserInputField(COMBO_BOX,
                "chartInitialDisplayMonthYear");
        
        // create the layout and attach the panel
        GroupLayout layout = new GroupLayout(chartSettings);
        chartSettings.setLayout(layout);        
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        
        // add horizontally
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(displayCreditPlotLabel)
                        .addComponent(hoverPopupLabel)
                        .addComponent(initialMonthLabel)
                    )
                .addGroup(layout.createParallelGroup()
                        .addComponent(creditRadioGroup)
                        .addComponent(hoverTransPopup)
                        .addComponent(monthsList)
                    )
             ); 
        
        // add vertically
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(displayCreditPlotLabel)
                        .addComponent(creditRadioGroup)
                    )
                .addGroup(layout.createParallelGroup()
                        .addComponent(hoverPopupLabel)
                        .addComponent(hoverTransPopup)
                    )
                .addGroup(layout.createParallelGroup()
                        .addComponent(initialMonthLabel)
                        .addComponent(monthsList)
                    )                    
            );
        
        // add the main panel to the tabbed pane
        settingsPane.addTab(chartSettingsTitle, chartSettings);
    }
            
    private void generateGenerateUserSettingsTab()
    {
        
        // create and populate the user settings tab
        userSettings = new JPanel() ;
        userSettings.setPreferredSize(panelDimension);                     
        
        // create the labels, buttons and textfields 
        JLabel imagesLocationLabel = new JLabel("Images Location");
        JLabel defaultImagesLocation = new JLabel("Default User Image Icon");
        
        imagesLocation = (JTextField) createUserInputField(INPUT_FIELD, 
                "imagesLocation");
        pictureArea = new UserImage();
        
        DepthButton browseButton = new DepthButton("Browse") ; // @TODO: replace arg with action
        DepthButton changeButton = new DepthButton("Change...");
        
        // add the components to the userSettingsPanel
        GroupLayout layout = new GroupLayout(userSettings);
        userSettings.setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        
        // specify the horizontal layout
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                    .addComponent(imagesLocationLabel)
                    .addComponent(defaultImagesLocation)
                    )
                .addGroup(layout.createParallelGroup()
                    .addComponent(imagesLocation)
                    .addComponent(browseButton)
                    .addComponent(pictureArea)
                    .addComponent(changeButton)
                    )
            );
        
        // specify the vertical layout
        layout.setVerticalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                        .addComponent(imagesLocationLabel)
                        .addComponent(imagesLocation)                
                        )
                    .addComponent(browseButton)
                    .addGroup(layout.createParallelGroup()
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
               comp.setPreferredSize(new Dimension(180, 20));
               comp.setMaximumSize(new Dimension(180, 20));               
               comp.setText(AppProperties.getInstance().getValueOf(configOptionName));
               return comp ;
               
           case(TEXT_AREA):
               JTextArea comp1 = new JTextArea() ;
               comp1.setText(AppProperties.getInstance().getValueOf(configOptionName));
               return comp1 ;
               
           case(COMBO_BOX):
               JComboBox comp2 = null ;
               if(configOptionName.equals("chartInitialDisplayMonthYear"))
               {
                   comp2 = new JComboBox(chartDisplayMonths); // will display the chart month
                                                    // for initial display
               }
               else if(configOptionName.equals("dbType"))
               {
                   comp2 = new JComboBox(dbTypes) ; // will display the db types
               }
               
               comp2.setPreferredSize(new Dimension(180,20));
               comp2.setMaximumSize(new Dimension(180,20));
               comp2.setSelectedItem(AppProperties.getInstance().getValueOf(configOptionName));
               return comp2;           
                   
           case(CREDIT_DISPLAY_RADIO_GROUP):
           case(HOVER_POPUP_DISPLAY_RADIO_GROUP):
               JRadioButton yes = new JRadioButton("Yes", 
                      (AppProperties.getInstance().getValueOf(configOptionName).equals("yes")));               
               JRadioButton no = new JRadioButton("No", 
                       (AppProperties.getInstance().getValueOf(configOptionName).equals("no"))); 
               
               ButtonGroup bt = new ButtonGroup();
               bt.add(yes);
               bt.add(no);
               
               JPanel radioPanel = new JPanel();
               radioPanel.setPreferredSize(new Dimension(180,20));
               radioPanel.setLayout(new GridLayout(1,2));
               radioPanel.add(yes) ;
               radioPanel.add(no) ;
               
               return radioPanel;        
               
           default: 
               return null ;
       }       
    }
    
    /**
     * This method adds the properties to the config file
     */
    public void setProperties()
    {
        // add the db properties
        String dAppName = appName.getText().trim()  ;
        String dAppVersion = appVersion.getText().trim() ;
        String dAppInfo = appInfo.getText().trim() ;
        String dDbName = dbName.getText().trim() ;       
        String dDbHost = dbHost.getText().trim() ;
        String dDbPort = dbPort.getText().trim() ;
        String dDbUsername = dbUsername.getText().trim() ;
        String dDbPassword = dbPassword.getText().trim() ;
        String dConnectionType = (String) connectionType.getSelectedItem();
       // String creditRadioGroup ;
        // String hoverTransPopup ;
        String dMonthsList = (String) monthsList.getSelectedItem() ;
        String dImagesLocation = imagesLocation.getText().trim() ;
       // String dPictureArea = pictureArea.get;
    }
}