/**
 * Credit Worthiness System Version 1.0
 */
package UI.Charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
    private Grid grid; // the grid object store separately for 
                        // further customization
    private final Color bgColor = Color.WHITE; 
    
    private int year ;
    private int month ;
    private int[] yMinAndMaxValues = {0, 500};
    
    private String[] legendItems  = {"Credit Limit" , "Transactions"} ;
    private Color[] legendColors = { new Color(255, 0, 0), new Color(0, 97, 0)} ;
    
    
    public Chart(int month, int year)
    {
        // initialise the components
        this.year = year ;
        this.month = month ;
        grid = new Grid(month, year, yMinAndMaxValues) ;
        
        // set the display properties       
        setPreferredSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));
        setMinimumSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));
        setOpaque(false);
        setLayout(new BorderLayout());
        
        // add the components
        setComponents();        
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
        add(grid, BorderLayout.CENTER) ;
        
    }        
}
