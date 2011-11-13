/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
    
    private String alertStatus ;
    private final String overspentCondition = "OverSpent"  ;
    private final String underspentCondition = "UnderSpent"  ;
    
    private Double creditOrDebit ;
    private final int ALERT_WIDTH = 165 ;
    private final int ALERT_HEIGHT = 60 ;
    private final int ARC_WIDTH = 20 ;
    private final int ARC_HEIGHT = 20 ;
    private final Color textColor = new Color(255, 255, 255);
    
    private final Point2D.Double alertConditionPosition 
                = new Point2D.Double(10, 20);
    private final Point2D.Double amountOverOrUnderAmountPostion 
            = new Point2D.Double(10, 50);
    
    private Font statusFont ;
    private Font creditOrDebitAmountFont ;
    
    public AlertLabel()
    {
        // initialise the variables
        // @TODO ensure that the values available here 
        // are from the model rather than hardcoded
        alertColor = overSpentColor ;
        alertStatus = overspentCondition ;
        
        statusFont = new Font("Serif", Font.PLAIN, 11);
        
        creditOrDebit = 214.55 ;
        
        setPreferredSize(new Dimension(ALERT_WIDTH, ALERT_HEIGHT));
        setMaximumSize(new Dimension(ALERT_WIDTH, ALERT_HEIGHT));
        setMinimumSize(new Dimension(ALERT_WIDTH, ALERT_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) 
    {        
        // make a copy of the graphics Object
        Graphics2D graphics = (Graphics2D) g;
        
        // obtain the status of the alerter
        String currentStatus = (alertStatus == overspentCondition) ?
                overspentCondition : underspentCondition ;
        
        // obtain the color of the alerter
        Color alerterColor = (alertStatus == overspentCondition) ?
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
        
        setFont(statusFont);
        graphics.drawString(currentStatus, (int) alertConditionPosition.x 
                        , (int) alertConditionPosition.y );        
        
        // amount by which the user is overspent 
        // or underspent
        String amountOverOrUnderSpent = (currentStatus == overspentCondition) ?
                "+" :"-";
        
        amountOverOrUnderSpent += "Kshs "
                                    + Double.toString(roundTwoDecimals(creditOrDebit)) ;
        setFont(currentFont);
        graphics.drawString(amountOverOrUnderSpent, (int) amountOverOrUnderAmountPostion.x,
                (int) amountOverOrUnderAmountPostion.y );
        
        graphics.dispose();
    }
    
    private double roundTwoDecimals(double d) 
    {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    } 
}
