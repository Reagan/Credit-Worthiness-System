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
    private int appWidth = 300 ;
    private int appHeight = 200 ;
    private String applicationName = "CWS vs 0.1";
    private Dimension d = null; 
    
    public AppPanel()
    {
        d = new Dimension(appWidth,appHeight);
        setName(applicationName);
        setSize(d);
    }
}
