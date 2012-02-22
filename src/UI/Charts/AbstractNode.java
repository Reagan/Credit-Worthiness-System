/**
 * Credit Worthiness System Version 1.0
 */
package UI.Charts;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public abstract class AbstractNode 
                        implements Node
{
    private int nodeID ; // random number generated for each node
                            // may be translated to the nodeID
    private int nodeType ; // sets the type of a node
    private static int sequenceID = 0; // sets the number that the node
                            // falls in
    public transient Rectangle2D bounds;
    public Point2D.Double plotLocation = null ;
    public boolean popUpEnabled  = false ; // bu default all Nodes should
                                        // have transation details displayed
    private Graphics2D graphics ; 
    
    public String[] nodeInfo ;
    public boolean selected = false ; // indicates if a node is selected
                                    // or not           
    private final int smallerWidthNodes = 5 ;  // shows the width of the sorrounding 
                                  
    private int[] transactionIDs ; // stores the ID for the transaction that the node
                                    // represents
    
    public AbstractNode(int[] transIDs, Point2D.Double nodeLocation, int nodeKind, 
            String [] info) 
    {
        plotLocation = nodeLocation ;
        nodeType = nodeKind ;
        nodeInfo = info ;
        bounds = new Rectangle2D.Double();
        bounds.setFrame(plotLocation.x, plotLocation.y, 
                20, 20 );
        transactionIDs = transIDs ;
    }   
    
    /**
     * This method generates a unique ID
     * for the node
     */
    @Override
    public void setNodeID(int nodeID)
    {
        this.nodeID = nodeID ;
    }
    
    @Override
    public int getNodeID()
    {
        return nodeID ;
    }
    
    @Override
    public void setNodeType(int nodeType )
    {
        this.nodeType = nodeType ;
    }
    
    @Override
    public int getNodeType() 
    {
        return nodeType ;
    }
    
    /**
     * This method sets the sequence ID for 
     * a node
     * @return 
     */
    private void setSequenceID()
    {
        sequenceID ++ ;
    }
    
    /**
     * This method gets a sequence ID for 
     * each node
     * @return 
     */
    @Override
    public int getSequenceID()
    {
        return sequenceID ;
    }
    
    /** 
     * Displays a node on the grid for the specified
     * date and of the required model
     * @param nodeType
     */
    @Override
    public void drawNode(Graphics2D graphics)
    {
        int nodeRadius = 0 ;
        Color nodeColor ;
        Ellipse2D circle ;
        this.graphics = graphics ;
        
        // set the radius of the node type
        switch(nodeType)
        {
            case CREDIT_ITEM_NODE: nodeRadius = 10 ;
            case TRANSACTION_ITEM_NODE: nodeRadius = 10 ;
            case TRANSACTION_LOAN_NODE: nodeRadius = 12 ;
            case TRANSACTION_PAYMENT_NODE: nodeRadius = 15 ;
        }      
        
        // set the color of the node
        if(nodeType == CREDIT_ITEM_NODE)
        {
            nodeColor = new Color(255, 0, 0) ;
        }
        else 
        {
            nodeColor = new Color(0, 97, 0);
        }                   
        
        // if a node is selected, draw an imaginary border around
        // the node
        if(true == selected)
        {                      
            Color initialColor = graphics.getColor() ;
            
            graphics.setColor(Color.BLACK.RED) ;
            
            // draw the bigger rect
            graphics.drawRect((int) plotLocation.x-3, (int) plotLocation.y, 
                    nodeRadius + 1, nodeRadius);
            
            // draw the smaller rects at the 4 corners
            // top left
            graphics.fillRect((int) plotLocation.x - 5, (int) plotLocation.y - 2,
                    smallerWidthNodes, smallerWidthNodes);
            
            // top right
            graphics.fillRect((int) plotLocation.x + nodeRadius - 3, (int) plotLocation.y - 2,
                    smallerWidthNodes, smallerWidthNodes);
            
            // bottom right
            graphics.fillRect((int) plotLocation.x + nodeRadius - 3, 
                    (int) plotLocation.y + nodeRadius - 2, smallerWidthNodes, smallerWidthNodes);
            
            // bottom left
            graphics.fillRect((int) plotLocation.x - 5, (int) plotLocation.y + nodeRadius - 2,
                    smallerWidthNodes, smallerWidthNodes);
            
            graphics.setColor(initialColor);
        }
        
        // draw the node
        graphics.setColor(nodeColor);
        circle = new Ellipse2D.Double((int) plotLocation.x - 2, (int) plotLocation.y
                    , nodeRadius, nodeRadius) ;  
        graphics.fill(circle);
        
        if( true == popUpEnabled || Chart.hoverTransactionPopupDefaultOn == true )
        {
            showPopupInfo(nodeInfo);
        }                
    }
    
    @Override
    public void translate(double dx, double dy)
    {
        this.translate(dx, dy);
    }
    
    @Override
    public boolean contains(Point2D aPoint)
    {       
       return bounds.contains(aPoint); 
    }  
    
    
    public void drawNodeSelected() 
    {
        selected = true ;       
    }
    
    /** 
     * This method displays a pop up info box for 
     * a node that has been hovered on
     */
    @Override
    public void showPopupInfo(String[] info) 
    {
        // ensure that the Node is not
        // a credit node type        
        String items = null ; // will indicate the items
        String worth = null ; // will indicate the worth of the items
        String paid ; // will indicate how much the client is paying
        String loanTakenOrPaid ; // will store whether or not a loan
                                // is taken or paid
        String amountLoanedOrPaid ; // will store the amount taken or paid
        
        // dimensions for the popup
        int popUpwidth  ;
        int popupHeight = 35 ;
        int arcWidth = 10 ;
        int arcHeight = 10 ;
                  
        // add details as required
        if(nodeType != Node.CREDIT_ITEM_NODE)
        {
            // draw the popup with the pop up info
            // draw the back ground  
            int yLocation  ; 
            int yLocation1 ;
            int yLocation2 ;
            int yLocation3 ;
            
            if((int)plotLocation.y < 100)
            {
                yLocation = (int) plotLocation.y  + 15 ;
                yLocation1 = (int) plotLocation.y + 30 ;
                yLocation2 = (int) plotLocation.y + 47 ;
                yLocation3 = (int) plotLocation.y + 61 ; 
            }
            else
            {
                yLocation = (int) plotLocation.y - 50 ;
                yLocation1 = (int) plotLocation.y - 37 ;
                yLocation2 = (int) plotLocation.y - 20 ;
                yLocation3 = (int) plotLocation.y - 6 ; 
            }
            
           
            // specify the details for a transaction payment
            if(nodeType == Node.TRANSACTION_ITEM_NODE)
            {
                if(info.length !=2 )
                {
                    // there is an error; all the four fields
                    // must be captured
                    return ;
                }
                
                // populate the fields                
                items = info[0] ;
                worth = info[1] ;
            }            
            
            // draw the border
            FontMetrics metrics = graphics.getFontMetrics(graphics.getFont()) ;
            popUpwidth = (int) metrics.getStringBounds(items, graphics).getWidth() + 7  ;
            popUpwidth = (popUpwidth < 50 )? 50 : popUpwidth ;
            
            graphics.setColor(Color.WHITE);
            graphics.fillRoundRect((int) plotLocation.x + 15, yLocation,
                popUpwidth , popupHeight, arcWidth, arcHeight);
            
            graphics.setColor(Color.BLACK);
            graphics.drawRoundRect((int) plotLocation.x + 15, yLocation,
                popUpwidth , popupHeight, arcWidth, arcHeight);
            
            // specify the details for a transaction payment
            if(nodeType == Node.TRANSACTION_ITEM_NODE)
            {                               
                // draw the text
                // 1. draw the title
                graphics.drawString(items, (int) plotLocation.x + 18,
                        yLocation1) ;
                
                // 2. draw the worth of the items
                graphics.drawString("W: " + worth, (int) plotLocation.x + 18,
                        yLocation2) ;                          
            }
            else
            {
                if(!(info.length > 0))
                {
                    return ; // something's up with that data field
                }
                
                // cater for the other 2 transaction types
                loanTakenOrPaid = (nodeType == Node.TRANSACTION_LOAN_NODE)? "Loan Taken" :
                        "Loan Paid" ;
                loanTakenOrPaid = info[1];
                
                // draw the text
                graphics.drawString(loanTakenOrPaid, (int) plotLocation.x + 18,
                        yLocation1);
                
                graphics.drawString("W: " + loanTakenOrPaid, (int) plotLocation.x + 18,
                        yLocation2) ;
            }  
        }        
    }
    
    /**
     * This method returns the transaction ID for a specific
     * Node. 
     * @return 
     */
    public int[] getNodeTransactionIDs()
    {
        return transactionIDs ;
    }
}
