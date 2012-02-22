/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import AppActions.MenuBar;
import AppProperties.AppProperties;
import UI.Listeners.CWSWindowAdapter;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.URL;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public final class AppPanel extends JFrame
{
    // Application dimensions
    private int appWidth = 911 ;
    private int appHeight = 774 ;
    private String applicationName = (AppProperties.getInstance().getValueOf(AppProperties.APP_NAME).equals("")) ?
            "Credit Worthiness System" : (AppProperties.getInstance().getValueOf(AppProperties.APP_NAME)) ;
    private Dimension d = null; 
    private Toolkit toolKit = null; 
    private CWSWindowAdapter exitAction = new CWSWindowAdapter();
    
    public AppPanel()
    {
        // additional vars
        Dimension screenSize = null; 
        int screenHeight = 0;
        int screenWidth = 0;
        
        // set the JFrame properties
        d = new Dimension(appWidth,appHeight);
        setTitle(applicationName);
        setSize(d);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(exitAction);
        
        // set the application icon
        ApplicationIcon.getInstance().setApplicationIcon(this) ;       
        
        // center the main window
        toolKit = Toolkit.getDefaultToolkit(); 
        screenSize = toolKit.getScreenSize();
        screenHeight = screenSize.height;
        screenWidth = screenSize.width;
        
        setLocation(screenWidth/7, 0);
        
        // add the rest of the panels
        // using the StackLayout
        buildContentPane();
    }
    
    private void buildContentPane()
    {
        setLayout(new StackLayout());
        
        // add the various panels stacked in z-index
        GradientPanel gradient = new GradientPanel();
        CurvesPanel curves = new CurvesPanel();
        DisplayPanel displayPanel  = new DisplayPanel();
        
        add(gradient , StackLayout.TOP);
        add(curves , StackLayout.TOP);
        add(displayPanel , StackLayout.TOP);
        
        // add a JmenuBar
        MenuBar menu = new MenuBar();
        setJMenuBar(menu);
    }            
}
