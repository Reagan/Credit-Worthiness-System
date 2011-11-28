/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import UI.Charts.Chart;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Calendar;
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
    
    // sets the month & year for the chart
    // @TODO: make this automated
    private int chartMonth = Calendar.NOVEMBER ;
    private int chartYear = 2011 ;
    
    public CenterPanel()
    {
        // initialise the components
        alerter = new AlertLabel();
        monthNav = new MonthChartNavigator();
        title = new JLabel("Transactions");
        title.setFont(new Font("Serif", Font.PLAIN, 31));
        yAxisLabel = new VerticalJLabel("Amount, Kshs");
        
        // create the chart component and add the 
        // model for the chart
        setChart(chartMonth, chartYear);
                
        
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
    
    public void setChart(int chartMonth, int chartYear)
    {
        // create a new Chart instance
         chart = new Chart(chartMonth, chartYear);
    }
    
    /**
     * this method sets the model 
     * for the chart that plots all the 
     * nodes displayed on the chart
     */
    public void setChartModel()
    {
        
    }
}
