/**
 * Credit Worthiness System Version 1.0
 */
package UI.Charts;

import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class Legend extends JPanel
{
    private final int LEGEND_WIDTH = 84 ;
    private final int LEGEND_HEIGHT = 32 ;
    
    public Legend()
    {
        setPreferredSize(new Dimension(LEGEND_WIDTH, LEGEND_HEIGHT));
        setOpaque(false);
    }
    
}
