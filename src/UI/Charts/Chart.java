/**
 * Credit Worthiness System Version 1.0
 */
package UI.Charts;

import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class Chart extends JPanel 
{
    private final int CHART_WIDTH = 646 ;
    private final int CHART_HEIGHT = 233 ;
    
    public Chart()
    {
        setPreferredSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));
    }
}
