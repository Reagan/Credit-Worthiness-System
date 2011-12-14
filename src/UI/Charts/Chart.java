/**
 * Credit Worthiness System Version 1.0
 */
package UI.Charts;

import UI.StackLayout;
import UI.Models.ChartModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class Chart extends JPanel 
{
    private final int CHART_WIDTH = 640 ;
    private final int CHART_HEIGHT = 233 ;
    //private Nodes chartNodes ;
    //private ChartEdges chartEdges ;
    private Legend legend ;
    private String [] yScaleItems ; // will store the values
                                        // displayed on the y axis
    private String [] xScaleItems ; // will display the items displayed on 
                                        // the x-axis
    private static Grid grid; // the grid object store separately for 
                        // further customization
    private  GraphPanel graph ; // will store the nodes and the chart lines
    
    private final Color bgColor = Color.WHITE; 
    
    private static int year ;
    private static int month ;
    private static int[] yMinAndMaxValues = {0, 500};
    
    private String[] legendItems  = {"Credit Limit" , "Transactions"} ;
    private Color[] legendColors = { new Color(255, 0, 0), new Color(0, 97, 0)} ;
    
    // Node Types
    public final int CREDIT_ITEM_NODE = 1 ;
    public final int TRANSACTION_ITEM_NODE = 2 ;
    public final int TRANSACTION_LOAN_NODE = 3 ;
    public final int TRANSACTION_PAYMENT_NODE = 4 ;    
    
    private static Calendar cal ;
    
    public Chart(int month, int year)
    {
        // initialise the components
        this.year = year ;
        this.month = month ;
        grid = new Grid(month, year, yMinAndMaxValues) ;
        graph = new GraphPanel() ;
        
        // set the display properties       
        setPreferredSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));
        setMinimumSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));
        setOpaque(false);
        
        // add the components
        setComponents();        
    }

    /**
     * lays out the components of the
     * Chart
     */
    private void setComponents() 
    {
        setLayout(new StackLayout());
        
        // add the legend to the grid
        // with the legend items
        grid.setLegendValues(legendItems, legendColors ) ;
        
        // add the grid
        add(grid, StackLayout.TOP) ;
        add(graph, StackLayout.TOP) ;
        
    }     
    
    /** 
     * This method attaches a particular model to a 
     * chart object
     * @param model 
     */
    public void setModel(ChartModel model)
    {
        
    }   
    
    /**
     * This method moves to the next month from 
     * that currently displayed on the chart
     */
    public static int[] goToNextMonth()
    {
        int currTime[] = new int[2] ;
        int DAY_OF_MONTH = 1 ;
        cal = new GregorianCalendar(year, month, DAY_OF_MONTH) ;
        cal.add(Calendar.MONTH, 1);
        
        month = cal.get(Calendar.MONTH) ;
        year = cal.get(Calendar.YEAR) ;
        
        grid.goToMonth(month, year, yMinAndMaxValues); 
        currTime[0] = month ;
        currTime[1] = year ;
        
        return currTime;
    }
    
    /** 
     * This method moves to the previous month from
     * that displayed on the chart
     * @return 
     */
    public static int[] goToPreviousMonth()
    {
        int currTime[] = new int[2] ;
        int DAY_OF_MONTH = 1 ;
        
        cal = new GregorianCalendar(year, month, DAY_OF_MONTH) ;
        cal.add(Calendar.MONTH, -1);
        
        month = cal.get(Calendar.MONTH) ;
        year = cal.get(Calendar.YEAR) ;                
        
        grid.goToMonth(month, year, yMinAndMaxValues);   
        currTime[0] = month ;
        currTime[1] = year ;
        
        return currTime;
    }
}
