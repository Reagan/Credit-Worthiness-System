/**
 * Credit Worthiness System Version 1.0
 */
package UI.Charts;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public abstract class AbstractEdge 
            implements Edge
{
    private int edgeType ;
    private Color edgeColor ;
    
    @Override
    public void setEdgeType(int edgeType)
    {
        this.edgeType = edgeType ;
    }
    
    @Override
    public int getEdgeType()
    {
        return edgeType ;
    }
    
    @Override
    public void drawEdge(Graphics2D graphics, int edgeType, 
                    AbstractNode n1, AbstractNode n2) 
    {
        // the x & y values that will offset 
        // the values from which the line is 
        // being plotted from to ensure that it is 
        // always at the center of a Node
        
        int xOffset = 0 ;
        int yOffset = 0 ;
        
        switch(edgeType)
        {
            case CREDIT_EDGE: xOffset = 10; yOffset = 10 ;
            case TRANSACTION_EDGE: xOffset = 6; yOffset = 6 ;            
        } 
        
        // set the color of the edge
        if(edgeType == CREDIT_EDGE)
        {
            edgeColor = new Color(255, 0, 0) ;
        }
        else
        {
            edgeColor = new Color(0, 97, 0);
        }
        
        // draw the edge
        graphics.setColor(edgeColor);
        graphics.drawLine((int) n1.plotLocation.getX() + xOffset, 
                (int)n1.plotLocation.getY() + yOffset , 
                (int)n2.plotLocation.getX() + xOffset,
                (int)n2.plotLocation.getY() + yOffset);         
    }
}
