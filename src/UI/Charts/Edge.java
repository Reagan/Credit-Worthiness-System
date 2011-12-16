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
public interface Edge 
{
    // types of edges
    int CREDIT_EDGE = 1 ;  
    int TRANSACTION_EDGE = 2 ;
    
    /**
     * This method draws the Edge depending on the
     * type of node that it is
     * @param graphics
     * @param edgeType
     * @param n1
     * @param n2  
     */
    void drawEdge(Graphics2D graphics, int edgeType, 
                    AbstractNode n1, AbstractNode n2) ;
    
    /** 
     * This method sets the edge type
     * @param edgeType 
     */
    void setEdgeType(int edgeType) ;
    
    /**
     * This method gets the edge type for an
     * edge and returns this 
     * @return 
     */
    int getEdgeType() ;
}
