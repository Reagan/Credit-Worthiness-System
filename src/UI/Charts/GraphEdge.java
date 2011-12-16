/**
 * Credit Worthiness System Version 1.0
 */
package UI.Charts;

import java.awt.Graphics2D;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class GraphEdge extends AbstractEdge
{
    // draw the edge
    public GraphEdge(Graphics2D g, int nodeType, 
                    AbstractNode n1, AbstractNode n2)    
    {
        drawEdge(g, nodeType, n1, n2);
    }    
}
