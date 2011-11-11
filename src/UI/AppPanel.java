/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class AppPanel extends JFrame
{
    // Application dimensions
    private int appWidth = 911 ;
    private int appHeight = 774 ;
    private String applicationName = "Credit Worthiness System";
    private Dimension d = null; 
    
    public AppPanel()
    {
        d = new Dimension(appWidth,appHeight);
        setTitle(applicationName);
        setSize(d);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        buildContentPane();
    }
    
    private void buildContentPane()
    {
        setLayout(new StackLayout());
        
        GradientPanel gradient = new GradientPanel();
        CurvesPanel curves = new CurvesPanel();
        
        add(gradient , StackLayout.TOP);
        add(curves , StackLayout.TOP);
    }
}
