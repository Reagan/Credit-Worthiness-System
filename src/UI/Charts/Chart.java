/**
 * Credit Worthiness System Version 1.0
 */
package UI.Charts;

import AppProperties.AppProperties;
import UI.CenterPanel;
import UI.Models.ChartModel;
import UI.StackLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class Chart extends JPanel 
{
    // chart dimensions
    public static int CHART_WIDTH = 640 ;
    public static int CHART_HEIGHT = 233 ;
    private static Grid grid; // the grid object store separately for 
                        // further customization
    private static GraphPanel graph ; // will store the nodes and the chart lines   
    
    // legend items
    private static String[] legendItems  = {"Credit Limit" , "Transactions"} ;
    private static Color[] legendColors = { new Color(255, 0, 0), new Color(0, 97, 0)} ;
            
    // Calendar & Chart instances
    private static Calendar cal ;        
    private static ChartModel cModel ;   
    
    // stores current time
    public static int currTime[] = { 0,0 } ;
    
    // insets so that the border is observed when the component
    // is embedded in component with Border
    public static Insets insets ;
        
    // detects mouse motions on the chart
    private MouseHandler mh ; 
    
    // populated with the application's current 
    // year and month
    private static int month ; 
    private static int year ;
    
    // container for custom events for chart component
    protected EventListenerList listenerList = new EventListenerList() ;
    
    // chart settings
    public static boolean creditPlotDisplayed = true ; // determines 
                                // whether the credit plot will be displayed
    public static boolean hoverTransactionPopupDefaultOn = false ; // determines whether or not popups
                                // should be displayed by default or not
    public static int chartInitialDisplayMonth ; // displays the default month to 
                                // be displayed by the chart
    public static int chartInitialDisplayYear  ; // stores the default display year for the chart
    
    public Chart()
    {
        // initialise the dimensions to the chart component
        CHART_WIDTH = getWidth()  ;
        CHART_HEIGHT = getHeight() ;               
        
        // set the display properties       
        setPreferredSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));
        setMinimumSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));
        setOpaque(false);     
        setLayout(new StackLayout());
        
        // get the current month and year
        getCurrentMonthAndYear();
        
        // initialise chart properties
        initialiseChartProperties() ;
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
        
        // and the graph
        if(null != graph)
        {
            add(graph, StackLayout.TOP) ;  
            
            // initialise and add the mouse listener to the graph
            mh = new MouseHandler(graph, this);
            addMouseListener(mh);
            addMouseMotionListener(mh);
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
    public void setModel( int[] yMinAndMaxValues, ChartPlot[] chartPlots )
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
        cModel = new ChartModel(chartInitialDisplayMonth, chartInitialDisplayYear, 
                yMinAndMaxValues, chartPlots) ;        
                
        // add the model to the grid and graph
        grid = new Grid(cModel) ;
        graph = new GraphPanel(cModel) ; 
        
        // Lay out the visual components
        setComponents();                 
    }   
    
    /**
     * This method moves to the # of months from 
     * that currently displayed on the chart. The chart
     * may be moved months ahead or behind
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
    
    // Add the methods reposnsible for triggering the events
    // method for adding the listener
    public void addNodeSelectedListener(NodeSelectedListener listener) 
    {
            listenerList.add(NodeSelectedListener.class, listener);
    }

    // method for removing the listener
    public void removeNodeSelectedListener(NodeSelectedListener listener) 
    {
            listenerList.remove(NodeSelectedListener.class, listener);
    }

    // method that should be run to trigger the node selected events
    public void fireNodeSelected(NodeSelected evt) 
    {
            Object[] listeners = listenerList.getListenerList();
            for (int i = 0; i < listeners.length; i = i+2) 
            {
                    if (listeners[i] == NodeSelectedListener.class) 
                    {
                            ((NodeSelectedListener) listeners[i+1]).nodeSelected(evt);
                    }
            }
    }

    /**
     * This method initializes the chart properties
     */
    private void initialiseChartProperties() 
    {
        creditPlotDisplayed = (AppProperties.getInstance()
                .getValueOf(AppProperties.CREDIT_PLOT_DISPLAYED).equals("yes")) ? true : false ;
        
        hoverTransactionPopupDefaultOn = (AppProperties.getInstance()
                .getValueOf(AppProperties.POPUP_DEFAULT).equals("yes")) ? true : false ;
        
        String initialMonth = AppProperties.getInstance()
                .getValueOf(AppProperties.CHART_INITIAL_MONTH) ;
        
        if ( initialMonth.equals("current") || initialMonth.equals("") )
        {
            // set the current month and year
            chartInitialDisplayMonth = month ;
            chartInitialDisplayYear = year ;
        }
        else
        {
            // parse the entered string to obtain the initial month and 
            // year for the display of the chart
            String [] initialMonthAndYearTokens = initialMonth.split("/") ;
            chartInitialDisplayMonth = Integer.parseInt(initialMonthAndYearTokens[0]) ;
            chartInitialDisplayYear = Integer.parseInt(initialMonthAndYearTokens[1]) ;
        }
        
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
    
    public static int[] getChartMonthAndYear()
    {
        if( Chart.currTime[0] == 0 )
        {            
            return getCurrentMonthAndYear() ;
        }
        else
        {            
            return Chart.currTime ;
        }
    }
}
