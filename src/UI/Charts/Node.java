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
public interface Node 
{
    // Node Types
    int CREDIT_ITEM_NODE = 1 ;
    int TRANSACTION_ITEM_NODE = 2 ;
    int TRANSACTION_LOAN_NODE = 3 ;
    int TRANSACTION_PAYMENT_NODE = 4 ;
    
    int sequenceID = 0; // sets the number that the node
    
    // these variables indicate the action performed 
    // on a node to ensure that the right type
    // of cursor is displayed
    int HOVERED_ON = 1;
    
    
    /**
     * This method set a unique node ID for 
     * the node
     * @param nodeID will most likely be the 
     * transaction number
     */
    void setNodeID(int nodeID) ;
    
    /**
     * This method returns the node ID for
     * a particular node
     * @return 
     */
    int getNodeID() ;
    
    /**
     * This method sets the node type for the node
     * @param nodeType
     * @return
     */
    void setNodeType(int nodeType );
    
    /**
     * This method obtains the node Type
     * @return 
     */
    int getNodeType() ;
        
    int getSequenceID() ;
    
    /**
     * This method draws the node on the Graph Panel Canvas
     * @param graphics
     */
    void drawNode(Graphics2D graphics) ;
    
    void translate(double dx, double dy);
    
    boolean contains(Point2D aPoint);
    
    /**
     * This method displays a little popup with
     * information on the hovered node
     * @param graphics
     * @param info  
     */
    void showPopupInfo(String[] info) ;        
}
