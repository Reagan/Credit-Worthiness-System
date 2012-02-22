/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Charts;

/**
 * This class represents a set of nodes that are
 * joined together by edges
 * The chart plot can either be a CREDIT_PLOT
 * or TRANSACTION_PLOT
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class ChartPlot 
{
    public final static int CREDIT_PLOT = 1 ;
    public final static int TRANSACTION_PLOT = 2 ;
    private int plotType ; // stores the plot type
    private AbstractNode[] nodes ; // stores the nodes 
                                // that compose the plot
    private int nodesCounter  = 0 ; // used to determine the current node that 
                                    // is displayed
    
    public ChartPlot(int plotType, int nodesNumber)
    {
        this.plotType = plotType ;
        nodes = new AbstractNode[nodesNumber] ;
    }
    
    /**
     * This method adds a node to 
     * the ChartPlot
     * */
    public void addNode(AbstractNode node)
    {
        nodes[nodesCounter] = node; 
        nodesCounter++ ;
    }
    
    /**
     * This method returns the object
     * with all the nodes collected
     * @return 
     */
    public AbstractNode[] getPlot()
    {
        return nodes ;
    }
    
    /**
     * This method returns the plot type
     * @return 
     */
    public int getPlotType()
    {
        return plotType ;
    }
}