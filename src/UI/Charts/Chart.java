/**
 * Credit Worthiness System Version 1.0
 */
package UI.Charts;

import UI.CenterPanel;
import UI.StackLayout;
import UI.Models.ChartModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class Chart extends JPanel 
{
    public static int CHART_WIDTH = 640 ;
    public static int CHART_HEIGHT = 233 ;
    private static Grid grid; // the grid object store separately for 
                        // further customization
    private static GraphPanel graph ; // will store the nodes and the chart lines   
    
    private static String[] legendItems  = {"Credit Limit" , "Transactions"} ;
    private static Color[] legendColors = { new Color(255, 0, 0), new Color(0, 97, 0)} ;
            
    private static Calendar cal ;        
    private static ChartModel cModel ;   
    
    
    public static int currTime[] = { 0,0 } ;
    public static Insets insets ;
    
    public Chart()
    {
        CHART_WIDTH = getWidth()  ;
        CHART_HEIGHT = getHeight() ;
        
        // set the display properties       
        setPreferredSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));
        setMinimumSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));
        setOpaque(false);     
        setLayout(new StackLayout());
    }
      
    /**
     * lays out the components of the
     * Chart
     */
    private void setComponents() 
    {
        // add the legend to the grid
        // with the legend items
        grid.setLegendValues(legendItems, legendColors ) ;
        
        // add the grid
        add(grid, StackLayout.TOP) ;
        
        if(null != graph)
        {
            add(graph, StackLayout.TOP) ;        
        }
    }     
    
    /** 
     * This method attaches a particular model to a 
     * chart object
     * @param month 
     * @param year
     * @param yMinAndMaxValues
     * @param chartPlots  
     */
    public void setModel(int month, int year, 
               int[] yMinAndMaxValues, ChartPlot[] chartPlots)
    {      
        // check if a border has been applied around the 
        // component for scaling  the component within
        if(getBorder() != null )
        {
            // get the border insets
            insets = getBorder().getBorderInsets(this);
        }
        else
        {
            insets = new Insets(0, 0, 0, 0) ;
        }
        
        // initialise the model
        cModel = new ChartModel(month, year, 
                yMinAndMaxValues, chartPlots) ;        
                
        // add the model to the grid and graph
        grid = new Grid(cModel) ;
        graph = new GraphPanel(cModel) ; 
        
        // Lay out the visual components
        setComponents();                 
    }   
    
    /**
     * This method moves to the next month from 
     * that currently displayed on the chart
     */
    public static int[] goToMonth(int additionalNumberOfMonths)
    {     
        // initialise the time variables
        int DAY_OF_MONTH = 1 ;
        
        // get the current date and time
        // and move one month forward
        cal = new GregorianCalendar(cModel.getYear(), 
                cModel.getMonth(), DAY_OF_MONTH) ;
        cal.add(Calendar.MONTH, additionalNumberOfMonths);
        
        // set the new times to the time variable
        currTime[0] = cal.get(Calendar.MONTH);
        currTime[1] = cal.get(Calendar.YEAR);
        
        // update the transactions list 
        CenterPanel.updateTransactionsChart();  
        
        // set the model to the next month
        // get the new max & min values for model        
        cModel.setData(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR), 
                CenterPanel.minMaxTransValues,CenterPanel.allChartPlots);
        
        // ask the grid to month one month forward
        updateChartUI();              
        
        return currTime;
    }
    
    public static void updateChartUI()
    {
        // update the grid
        grid.goToMonth(cModel.getMonth(), cModel.getYear(), cModel.getYMinMax()); 
        
        // update the graph nodes      
        graph.redrawPlots(cModel.getPlotsData());
    }
    
    // set the dimensions for the layout managers
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(CHART_WIDTH, CHART_HEIGHT) ;
    }
    
    @Override
    public Dimension getMinimumSize()
    {
        return new Dimension(CHART_WIDTH, CHART_HEIGHT) ;
    }
    
    // set the dimensions for the correct drawing of the element
    @Override
    public int getWidth()
    {
        return CHART_WIDTH ;
    }
    
    @Override
    public int getHeight()
    {
        return CHART_HEIGHT ;
    }   
}
