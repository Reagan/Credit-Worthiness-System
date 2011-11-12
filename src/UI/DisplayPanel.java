/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import AppActions.MenuBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class DisplayPanel extends JPanel
{
    private StatusBar statusBar = new StatusBar();
    
    public DisplayPanel()
    {
        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.SOUTH );   
        setOpaque(false);
    }
    
    public DisplayPanel(LayoutManager layout)
    {
        super(layout);          
    }
}
