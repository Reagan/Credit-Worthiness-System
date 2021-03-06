/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import javax.swing.JComponent;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class AlertLabel extends JComponent 
{
    private Color alertColor ;
    private final Color overSpentColor = new Color(255, 102, 0);
    private final Color underSpentColor = new Color(0, 102, 0);
    
    public int alertStatus ;
    public static final int OVERSPENT_CONDITION = 1  ;
    public static final int UNDERSPENT_CONDITION = 2  ;
    private final String OVERSPENT_TEXT = "Overlimit" ;
    private final String UNDERSPENT_TEXT = "Underlimit" ;
    
    private double creditOrDebit ;
    private final int ALERT_WIDTH = 175 ;
    private final int ALERT_HEIGHT = 60 ;
    private final int ARC_WIDTH = 20 ;
    private final int ARC_HEIGHT = 20 ;
    private final Color textColor = new Color(255, 255, 255);
    
    private final Point2D.Double alertConditionPosition 
                = new Point2D.Double(5, 30);
    private final Point2D.Double amountOverOrUnderAmountPostion 
            = new Point2D.Double(9, 50);
    
    private Font statusFont ;
    private Font statusAmountFont ;
    
    private boolean alerterInitialised = false; // determines whether or not the
                    // alerter has been configured to initialise an amount
    
    public AlertLabel()
    {
        // initialise the variables
        // alertColor = overSpentColor ;
        // alertStatus = OVERSPENT_CONDITION ;
        
        statusFont = new Font("sanSerif", Font.PLAIN, 28);
        statusAmountFont = new Font("sanSerif", Font.PLAIN, 13) ;
        
        setPreferredSize(new Dimension(ALERT_WIDTH, ALERT_HEIGHT));
        setMaximumSize(new Dimension(ALERT_WIDTH, ALERT_HEIGHT));
        setMinimumSize(new Dimension(ALERT_WIDTH, ALERT_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) 
    {        
        // make a copy of the graphics Object
        Graphics2D graphics = (Graphics2D) g;
        
        // set the displayed font to be antialiased
        graphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        
        // check if the credit/debit amount has been set
        if ( !alerterInitialised )
        {
            graphics.dispose();
            return ;
        }            
        
        // obtain the status of the alerter
        String currentStatus = (alertStatus == OVERSPENT_CONDITION) ?
                OVERSPENT_TEXT : UNDERSPENT_TEXT ;
        
        
        // obtain the color of the alerter
        Color alerterColor = (alertStatus == OVERSPENT_CONDITION) ?
                overSpentColor : underSpentColor;
        
        // create the background
        graphics.setPaintMode();
        graphics.setColor(alerterColor);
        graphics.fillRoundRect(0, 0,getWidth(), getHeight()
                , ARC_WIDTH, ARC_HEIGHT);        
        
        // display the text
        // condition status
        graphics.setColor(textColor);                
        Font currentFont = graphics.getFont();
        
        graphics.setFont(statusFont);
        graphics.drawString(currentStatus, (int) alertConditionPosition.x 
                        , (int) alertConditionPosition.y );        
        
        // amount by which the user is overspent 
        // or underspent
        graphics.setFont(statusAmountFont) ;
        String amountOverOrUnderSpent = (alertStatus == OVERSPENT_CONDITION) ?
                "+" :"-";
        
        String creditOrDebitAmount = roundTwoDecimals(creditOrDebit) ;
        String displayedAmount = (creditOrDebitAmount.substring(0,1).equals("-") ?
               creditOrDebitAmount.substring(1) : creditOrDebitAmount) ;
        
        amountOverOrUnderSpent += "Kshs "
                                    +  displayedAmount ;
        graphics.setFont(currentFont);
        graphics.drawString(amountOverOrUnderSpent, (int) amountOverOrUnderAmountPostion.x,
                (int) amountOverOrUnderAmountPostion.y );
        
        graphics.dispose();
    }
    
    private String roundTwoDecimals(double d) 
    {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return twoDForm.format(d);        
    } 
    
    /**
     * This method sets the amount by which the alerter
     * should display
     * @param amount 
     */
    public void setAlerterAmount(double amount)
    {
        creditOrDebit = amount ;
        alerterInitialised = true ;
    }
}
