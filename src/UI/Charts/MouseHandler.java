/**
 * Credit Worthiness System Version 1.0
 */
package UI.Charts;

import AppActions.UpdateTransactionAction;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputAdapter;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class MouseHandler extends MouseInputAdapter
{
    private ChartPlot[] chartPlots ;
    private GraphPanel graph ;
    private Chart chart ;
    
    public MouseHandler(GraphPanel g, Chart c)
    {
        // attach the Graph Panel to the MouseHandler
        graph = g ;
        chart = c ;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) 
    {
        switch(e.getModifiers()) 
        {
            case InputEvent.BUTTON3_MASK: 
        
            // display the popup menu
            ChartPopup popUp = new ChartPopup() ;
            popUp.show(e.getComponent(), e.getX(), e.getY());
            break;
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e)
    {
        // determine if a node is contained 
        // below the mouse position and display 
        // a pop up
        chartPlots = graph.chartPlots ;
        
        if( null == chartPlots  || 1 > chartPlots.length  )
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
            graph.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e)
    {            
        if( null == chartPlots || 1 > chartPlots.length  )
        {
            return ;
        }

        Point p = e.getPoint();
        AbstractNode hoveredNode = getNodeAt(p);

        if(hoveredNode != null)
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
           
            if ( hoveredNode.getNodeType() != Node.CREDIT_ITEM_NODE )
                hoveredNode.selected = true ;                

            // add the border around the node
            graph.repaint();               

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
                if ( hoveredNode.getNodeType() != Node.CREDIT_ITEM_NODE )
                {
                    // obtain the transaction id for the selected 
                    // node
                    int selectedNodeID = transIDs[0] ;

                    // fire custom event
                    NodeSelected n = new NodeSelected(chart, selectedNodeID) ;
                    chart.fireNodeSelected(n);  
                }
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