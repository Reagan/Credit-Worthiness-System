/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import UI.Charts.Chart;
import UI.Charts.CreditNode;
import UI.Charts.Node;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.geom.Point2D;
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
        
        // #######################################################
        // create the chart component and add the 
        // model for the chart
        Point2D.Double plots0 = new Point2D.Double(100, 100) ;
        Point2D.Double plots1 = new Point2D.Double(120, 130) ;
        String[] info = {"Rice", "200", "100"} ;
        
        CreditNode[] nodes = new CreditNode[2];
        nodes[0] = new CreditNode(Node.TRANSACTION_ITEM_NODE, plots0, info) ;
        nodes[1] = new CreditNode(Node.TRANSACTION_ITEM_NODE, plots1, info) ;
        
        int month = 11 ;
        int year = 2011 ;
        int[] yMinMax = {0, 500} ;
        
        // create a new Chart instance
        chart = new Chart();
        
        setChartModel(month, year, yMinMax, nodes);
        
        // #########################################################
        
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
    
    public void setChartModel(int chartMonth, int chartYear, 
            int[] yMinMax, Node[] nodes)
    {        
         chart.setModel(chartMonth, chartYear, yMinMax, nodes);
    }        
}
