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
    
    private String[] legendItems  = {"Credit Limit" , "Transactions"} ;
    private Color[] legendColors = { new Color(255, 0, 0), new Color(0, 97, 0)} ;
            
    private static Calendar cal ;
        
    private static ChartModel cModel ;       
    
    public Chart()
    {
        // set the display properties       
        setPreferredSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));
        setMinimumSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));
        setOpaque(false);                    
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
        
        if(null != graph)
        {
            add(graph, StackLayout.TOP) ;        
        }
    }     
    
    /** 
     * This method attaches a particular model to a 
     * chart object
     * @param model 
     */
    public void setModel(int month, int year, 
                int[] yMinAndMaxValues, ChartPlot[] chartPlots)
    {                       
        // initialise the model
        cModel = new ChartModel(month, year, 
                yMinAndMaxValues, chartPlots) ;        
                
        // add the model to the grid and graph
        grid = new Grid(cModel) ;
        
        if(null != chartPlots)
        {
            graph = new GraphPanel(cModel) ; 
        }
        
        // Lay out the visual components
        setComponents();                 
    }   
    
    /**
     * This method moves to the next month from 
     * that currently displayed on the chart
     */
    public static int[] goToNextMonth()
    {
        // initialise the time variables
        int currTime[] = new int[2] ;
        int DAY_OF_MONTH = 1 ;
        
        // get the current date and time
        // and move one month forward
        cal = new GregorianCalendar(cModel.getYear(), 
                cModel.getMonth(), DAY_OF_MONTH) ;
        cal.add(Calendar.MONTH, 1);
        
        // set the model to the next month
        cModel.setMonth(cal.get(Calendar.MONTH)) ;
        cModel.setYear(cal.get(Calendar.YEAR)) ;
        
        // ask the grid to month one month forward
        grid.goToMonth(cModel.getMonth(), cModel.getYear(), cModel.getYMinMax()); 
        currTime[0] = cModel.getMonth() ;
        currTime[1] = cModel.getYear() ;
        
        return currTime;
    }
    
    /** 
     * This method moves to the previous month from
     * that displayed on the chart
     * @return 
     */
    public static int[] goToPreviousMonth()
    {
        // initialise the time variables
        int currTime[] = new int[2] ;
        int DAY_OF_MONTH = 1 ;
        
        // get the current date and time
        // and move one month behind
        cal = new GregorianCalendar(cModel.getYear(), 
                cModel.getMonth(), DAY_OF_MONTH);
        cal.add(Calendar.MONTH, -1);
        
        cModel.setMonth(cal.get(Calendar.MONTH)) ;
        cModel.setYear(cal.get(Calendar.YEAR)) ;               
        
        // ask the grid to month one month behind
        grid.goToMonth(cModel.getMonth(), cModel.getYear(), cModel.getYMinMax());   
        currTime[0] = cModel.getMonth() ;
        currTime[1] = cModel.getYear() ;
        
        return currTime;
    }
}