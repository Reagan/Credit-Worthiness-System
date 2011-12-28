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
        alerter = new AlertLabel(214.55);
        monthNav = new MonthChartNavigator();
        title = new JLabel("Transactions");
        title.setFont(new Font("Serif", Font.PLAIN, 31));
        yAxisLabel = new VerticalJLabel("Amount, Kshs");
        
        // get the current month and year
        getCurrentMonthAndYear();
        
        // create a new Chart instance
        chart = new Chart();
               
        /*
        // #######################################################
        // create the chart component and add the 
        // model for the chart
        
        // get the current month and year
        getCurrentMonthAndYear();
        
        // initialise values for the chart
        month = 3 ;
        year = 2011 ;
        yMinMax[0] = 0 ;
        yMinMax[1] = 480 ;
        
        // get the number of days in a month
        int noOfDaysInMonth ;
        Calendar cal = new GregorianCalendar(year, month, 1) ;
        noOfDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH) ;      
               
        // plotsLocation  == [ date for transaction, amount for transaction ] 
        // actual values for a node
        Point2D.Double plotsLocationA = new Point2D.Double(12,100) ;
        
        // get the translated values
        Point2D.Double plotsLocation0 = new Point2D.Double(( 72 
                + (plotsLocationA.x * (yMinMax[1]-yMinMax[0]-2)/noOfDaysInMonth )),
                ( 200  - 2 - ( plotsLocationA.y * 198.0/(yMinMax[1]-yMinMax[0]-2) ))) ;
        
        // actual values for  node
        Point2D.Double plotsLocationB = new Point2D.Double(18,480) ;
        
        // translated values for a node
        Point2D.Double plotsLocation1 = new Point2D.Double(( 72 
                + (plotsLocationB.x * (yMinMax[1]-yMinMax[0]-2)/noOfDaysInMonth )),
                ( 200  - 2 - ( plotsLocationB.y * 198.0/(yMinMax[1]-yMinMax[0]-2) ))) ;
        
        // information sent to node is in the structure
        // [ Items, Items_Worth ]
        int[] transactionsID0 = {1234, 4567 } ;
        String[] info = {"Rice, Biscuits, Milk", "200"} ;
        
        int [] transactionsID1 = {2345};
        String[] info1 = {"Rice", "200"} ;
        
        // initialise the nodes
        // Chartplot args [ plot type and plots number ]
        
        // initialise the object to store the plots
        // no more than 2 plots anticipated
        // these are for the credit & transaction plots        
        allChartPlots = null ;
        allChartPlots = new ChartPlot[1] ;
        
         
        ChartPlot plot1 = new ChartPlot(ChartPlot.TRANSACTION_PLOT,
                2);  // number of plots are 2
        GraphNode[] nodes = new GraphNode[2];
        
        // variables for the Node are
        // NodeType, plots, info
        nodes[0] = new GraphNode(transactionsID0, Node.TRANSACTION_ITEM_NODE, plotsLocation0, info) ;
        nodes[1] = new GraphNode(transactionsID1, Node.TRANSACTION_ITEM_NODE, plotsLocation1, info1) ;
        
        // add the nodes to the plot
        plot1.addNode(nodes[0]);
        plot1.addNode(nodes[1]);
        
        // add these to the array of plots
        allChartPlots[0] = plot1 ;
        
        /*
        Point2D.Double plotsLocation2 = new Point2D.Double(72, 200) ;
        Point2D.Double plotsLocation3 = new Point2D.Double(145, 180) ;
        Point2D.Double plotsLocation4 = new Point2D.Double(570, 2) ;
        
        ChartPlot plot2 = new ChartPlot(ChartPlot.CREDIT_PLOT,
                3);  // number of plots are 2
        GraphNode[] nodes2 = new GraphNode[3];
        
        // variables for the Node are
        // NodeType, plots, info
        nodes2[0] = new GraphNode(Node.CREDIT_ITEM_NODE, plotsLocation2, info) ;
        nodes2[1] = new GraphNode(Node.CREDIT_ITEM_NODE, plotsLocation3, info) ;
        nodes2[2] = new GraphNode(Node.CREDIT_ITEM_NODE, plotsLocation4, info) ;
        
        // add the nodes to the plot
        plot2.addNode(nodes2[0]);
        plot2.addNode(nodes2[1]);
        plot2.addNode(nodes2[2]);
        
        // add these to the array of plots
        allChartPlots[0] = plot1 ;
        allChartPlots[1] = plot2 ;                    
        */
        
        // set the model to null since no user has been selected
        allChartPlots = null ;
        
        // set the chart model
        setChartModel(month, year, yMinMax, allChartPlots);
        
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
        alerter = new AlertLabel(amount) ;
        
        if(amount < 0 ) 
        {
            alerter.alertStatus = AlertLabel.OVERSPENT_CONDITION ;
        }            
        else 
        {
            alerter.alertStatus = AlertLabel.UNDERSPENT_CONDITION ;
        }               
    }
}
