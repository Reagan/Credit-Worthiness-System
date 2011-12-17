/**
 * Credit Worthiness System Version 1.0
 */
package UI.Charts;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class GraphNode extends AbstractNode
{
    /**
     * 
     * @param nodeType
     * @param info 
     * @param plotLocation
     */
    public GraphNode(int[] transactionIDs, int nodeType, 
                    Point2D.Double plotLocation, String[] info)  
    {
        super(transactionIDs, plotLocation, nodeType, info) ;
        
    }

    @Override
    public void drawNode(Graphics2D g)
    {
        super.drawNode(g);   
    }
    
    @Override
    public void setNodeID(int nodeID) 
    {
        super.setNodeID(nodeID);
    }         
}
