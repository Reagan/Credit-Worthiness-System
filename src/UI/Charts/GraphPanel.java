/**
 * Credit Worthiness System Version 1.0
 */
package UI.Charts;

import AppActions.AppAction;
import AppActions.UpdateTransactionAction;
import UI.Models.ChartModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

/**
 * This class will read the chart model and draw the nodes and
 * lines and nodes for the graph. It also implements a 
 * mouse listener to ensure that the nodes are selectable as a 
 * way of changing values and a small pop-up
 * displays when a mouse over action occurs on a node
 * 
 * 
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class GraphPanel extends JPanel 
{
    public ChartPlot[] chartPlots ;
    private GraphEdge graphEdge ;   
       
    public GraphPanel(ChartModel cModel)
    {       
        // set the layout variables
        setLayout(new BorderLayout());
        setOpaque(false);                             
    }

    public void redrawPlots(ChartPlot[] plots)
    {
        chartPlots = plots ;        
        repaint(0);
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {            
        // convert the graphics object to
        // obtain the required nodes
        Graphics2D graphics = (Graphics2D) g ;
        
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);                                        

        if( null==chartPlots)
        {            
            return ;
            
        }
        
        // extract the plots from the ChartPlot[]
        // and then extract the nodes and then
        // draw the nodes and the interconnecting 
        // lines for the Nodes                
        for(int plotsCounter = 0 , plots = chartPlots.length;
                plotsCounter < plots ; plotsCounter ++ )
        {
            ChartPlot p = chartPlots[plotsCounter] ;
            int plotType = p.getPlotType() ;
            AbstractNode[] plot = p.getPlot() ;
            
            // for each of the plots get the nodes
            for(int nodesCounter = 0, n = plot.length ;
                    nodesCounter < n; nodesCounter ++)
            {
                plot[nodesCounter].drawNode(graphics);

                // add the edges to the nodes
                if(nodesCounter > 0)
                {
                    // join the nodes depending on the type
                    // of plot made
                    int edgeType = (plotType == ChartPlot.CREDIT_PLOT)
                            ? Edge.CREDIT_EDGE : Edge.TRANSACTION_EDGE ; 
                            
                    graphEdge = new GraphEdge(graphics, edgeType, 
                            (AbstractNode)plot[nodesCounter-1], 
                                (AbstractNode)plot[nodesCounter]);
                }
            }
            
        }
    }
}