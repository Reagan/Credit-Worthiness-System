/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import UI.Charts.Chart;
import UI.Charts.ChartPlot;
import UI.Charts.GraphNode;
import UI.Charts.Node;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
    private static AlertLabel alerter ;
    private MonthChartNavigator monthNav ;
    private final JLabel title ;
    private final VerticalJLabel yAxisLabel; 
    private static Chart chart;
    public static int month ;
    public static int year ;
    private int[] yMinMax = {0, 0};    
    private ChartPlot[] allChartPlots = null ;
    
    public CenterPanel()
    {
        // initialise the components
        alerter = new AlertLabel(0.00);
        monthNav = new MonthChartNavigator();
        title = new JLabel("Transactions");
        title.setFont(new Font("sanSerif", Font.PLAIN, 31));
        yAxisLabel = new VerticalJLabel("Amount, Kshs");
        
        // get the current month and year
        getCurrentMonthAndYear();
        
        // create a new Chart instance
        chart = new Chart();       
        
        // set the model to null since no user has been selected
        allChartPlots = null ;
        
        // set the chart model
        setChartModel(month, year, yMinMax, allChartPlots);                
        
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
    
    public static void setChartModel(int chartMonth, int chartYear, 
            int[] yMinMax,ChartPlot[] allChartPlots)
    {        
         chart.setModel(chartMonth, chartYear, yMinMax, allChartPlots);
    }

    public static int[] getCurrentMonthAndYear() 
    {
        Calendar cal = Calendar.getInstance();        
        month = cal.get(Calendar.MONTH) ;
        year = cal.get(Calendar.YEAR) ;
        
        int[] currMonthAndYear = new int[2];
        currMonthAndYear[0] = month ;
        currMonthAndYear[1] = year ;
        return currMonthAndYear ;
    }    
    
    /**
     * This method displays the alerter with the 
     * required color and the credit/debit amount 
     * for the currently selected user
     * @param amount 
     */
    public static void updateAlertLabel(double amount)
    {
        //alerter = new AlertLabel(amount) ;
        alerter.setAlerterAmount(amount);
        
        if(amount < 0 ) 
        {
            alerter.alertStatus = AlertLabel.OVERSPENT_CONDITION ;
        }            
        else 
        {
            alerter.alertStatus = AlertLabel.UNDERSPENT_CONDITION ;
        }       
        
        // repaint the alerter
        alerter.repaint();
    }
}
