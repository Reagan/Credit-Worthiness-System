/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import UI.Charts.Chart;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class CenterPanel extends JPanel
{
    private AlertLabel alerter ;
    private MonthChartNavigator monthNav ;
    private final JLabel title ;
    private final VerticalJLabel yAxisLabel; 
    private Chart chart;
    
    public CenterPanel()
    {
        // initialise the components
        alerter = new AlertLabel();
        monthNav = new MonthChartNavigator();
        title = new JLabel("Transactions");
        yAxisLabel = new VerticalJLabel("Amount, Kshs");
        chart = new Chart();
        
        // lay out and add the components
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // topmost panel
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(Box.createRigidArea(new Dimension(72, 0)));
        topPanel.add(title);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(alerter);
        topPanel.add(Box.createRigidArea(new Dimension(25, 0)));
        
        // center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        centerPanel.add(yAxisLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        centerPanel.add(chart);
        centerPanel.add(Box.createHorizontalGlue());
        
        // lower panel
        JPanel lowerPanel = new JPanel();
        lowerPanel.setOpaque(false);
        lowerPanel.setLayout(new FlowLayout());
        lowerPanel.add(monthNav);
        
        // now add the components vertically
        add(Box.createRigidArea(new Dimension(0, 10 )));
        add(topPanel);
        add(Box.createRigidArea(new Dimension(0, 25)));
        add(centerPanel);
        add(Box.createRigidArea(new Dimension(0, 13)));
        add(lowerPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(new JSeparator(SwingConstants.HORIZONTAL));
        add(Box.createRigidArea(new Dimension(0, 10)));
                        
        // finalize the JPanel
        setOpaque(false);
        setPreferredSize(new Dimension(686, 344));
    }
}
