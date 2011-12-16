/**
 * Credit Worthiness System Version 1.0
 */
package UI.Models;

import UI.Charts.ChartPlot;
import UI.Charts.Node;

/**
 * This class defines the way that data for a chart is 
 * stored. A ChartModel is comprised of Plots. 
 * Plots are in turn made of Node Objects. Plots are 
 * simply an ArrayList/Vector  of Nodes
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class ChartModel 
{
    private ChartPlot[] plots ; // this stores the series of nodes making 
                        // up the various plots
    private int currMonth ; // sets the current month
    private int currYear ; // sets the current year
    private int [] yMinMaxValues = new int[2] ; // should store just 
                                        // the minimum and maximum value 
                                        // provided
    /**
     * The constructor initializes the model
     * with the plots data
     */
    public ChartModel() {}
    
    public ChartModel(int month, int year, 
                int [] yMinAndMaxValues, ChartPlot[] chartPlots)
    {
        setData(month, year, 
                yMinAndMaxValues, chartPlots);
    }
    
    /** 
     * This method returns the minimum value to be 
     * plotted by the chart
     * @return 
     */
    public int getMinimumValue()
    {
        return yMinMaxValues[0] ;
    }
    
    /**
     * This method returns the maximum value to
     * be plotted by the chart
     * @return 
     */
    public int getMaximumValue()
    {
        return  yMinMaxValues[1] ;
    }
    
    /**
     * This method returns the value to be plotted
     * for a specific node for a specific day
     * @param day
     * @param month
     * @param year
     * @return 
     */
    protected int getValueAt(int nodeID)
    {
        // uses int day, int month, int year
        // from node data to obtain information 
        // for that date
        return 0 ;
    }
    
    /**
     * This method returns information on a specific node
     * that is displayed on hover
     * @param nodeID
     * @return String[] nodeData [Items {String[]}, Worth {Double}, amountPaid {Double}]
     */
    protected String[] getInfoAt(int nodeID)
    {
        return null ;
    }
    
    /**
     * This method sets the plots (that are themselves 
     * composed of nodes - are thus node arrays) and sets 
     * them as the data source for the chart
     * @param plots 
     */
    public void setData(int month, int year, 
                int[] yMinAndMaxValues, ChartPlot[] chartPlots)
    {
        currMonth = month ;
        currYear = year ;
        yMinMaxValues = yMinAndMaxValues ;
        plots = chartPlots ;
    }
    
    /**
     * This method returns the node data
     * for a graph
     * @return 
     */
    public ChartPlot[] getPlotsData()
    {
        return plots ;
    }
    
    public int getMonth()
    {
        return currMonth ;
    }
    
    public int getYear()
    {
        return currYear ;
    }
    
    public int[] getYMinMax()
    {
        return yMinMaxValues ;
    }
}
