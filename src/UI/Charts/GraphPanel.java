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
    private ChartPlot[] chartPlots ;
    private GraphEdge graphEdge ;
    private MouseHandler mh ;    
       
    public GraphPanel(ChartModel cModel)
    {       
        setLayout(new BorderLayout());
        setOpaque(false);
       
        mh = new MouseHandler();
        addMouseListener(mh);
        addMouseMotionListener(mh); 
                
        // assign the nodes from the chart Model
        chartPlots =  (ChartPlot[]) cModel.getPlotsData() ;        
    }

    public void redrawPlots(ChartPlot[] plots)
    {System.out.println("redraw plots called");
        chartPlots = plots ;        
        repaint(0);
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {            System.out.println("paint component called (for plots) ")     ;
        // convert the graphics object to
        // obtain the required nodes
        Graphics2D graphics = (Graphics2D) g ;
        
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);                                        

        if( null==chartPlots)
        {
            System.out.println("chartPlots are null") ;
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
    
    final class MouseHandler extends MouseInputAdapter
    {
        @Override
        public void mouseMoved(MouseEvent e)
        {
            // determine if a node is contained 
            // below the mouse position and display 
            // a pop up
            if( chartPlots.length < 1 )
            {
                return ;
            }
            
            Point p = e.getPoint(); 
            AbstractNode hoveredNode = getNodeAt(p);
            
            // display the popup with details about the 
            // node
            if(hoveredNode != null)
            {
                // make sure that no popup for any node 
                // is displayed
                for(int plotsCounter = 0 , plots = chartPlots.length;
                plotsCounter < plots ; plotsCounter ++ )
                {
                    ChartPlot plot = chartPlots[plotsCounter] ;
                    AbstractNode[] plotNodes = plot.getPlot() ;

                    // for each of the plots get the nodes
                    for(int nodesCounter = 0, n = plotNodes.length ;
                            nodesCounter < n; nodesCounter ++)
                    {
                        plotNodes[nodesCounter].popUpEnabled = false ;
                    }
                }                
                
                hoveredNode.popUpEnabled = true ;
                repaint();
            }
        }
        
        @Override
        public void mousePressed(MouseEvent e)
        {            
            if( chartPlots.length < 1 )
            {
                return ;
            }
            
            Point p = e.getPoint();
            AbstractNode hoveredNode = getNodeAt(p);
            
            if(hoveredNode != null  && e.getClickCount() == 2)
            {
                // select the node
                // make sure first that no node has a border
                // around it
                for(int plotsCounter = 0 , plots = chartPlots.length;
                plotsCounter < plots ; plotsCounter ++ )
                {
                    ChartPlot plot = chartPlots[plotsCounter] ;
                    AbstractNode[] plotNodes = plot.getPlot() ;

                    // for each of the plots get the nodes
                    for(int nodesCounter = 0, n = plotNodes.length ;
                            nodesCounter < n; nodesCounter ++)
                    {
                        plotNodes[nodesCounter].selected = false ;
                    }
                }                  
             
                hoveredNode.selected = true ;                
                
                // add the border around the node
                repaint();               
                 
                // check if the transaction IDs are more than 1
                // if more than 1, ask user to select from below panel
                // if 1 then display transaction dialog
                int[] transIDs = hoveredNode.getNodeTransactionIDs() ;
                
                if(transIDs.length > 1)
                {
                    JOptionPane.showMessageDialog(null, "More than 1 transaction is included here. Please use Transactions List below "                         
                        ,"Multiple Transactions!", JOptionPane.PLAIN_MESSAGE);
                }
                else
                {
                    // display the update transactions dialog 
                    // to display the transaction details
                    UpdateTransactionAction updateAction = 
                            new UpdateTransactionAction(0);
                    updateAction.run();
                }              
            }
        }
        
        private AbstractNode getNodeAt(Point p)
        {
            AbstractNode hoveredNode = null;          
            
            for(int plotsCounter = 0 , plots = chartPlots.length;
                plotsCounter < plots ; plotsCounter ++ )
            {
                ChartPlot plot = chartPlots[plotsCounter] ;
                AbstractNode[] plotNodes = plot.getPlot() ;

                // for each of the plots get the nodes
                for(int nodesCounter = 0, n = plotNodes.length ;
                        nodesCounter < n; nodesCounter ++)
                {
                    hoveredNode = (AbstractNode)plotNodes[nodesCounter];
                    if (hoveredNode.contains(p))
                    {
                        return(hoveredNode);
                    }
                }                
            }
            
            // non of the nodes falls under the mouse
            // ignore
            return null;
        }            
    }
}