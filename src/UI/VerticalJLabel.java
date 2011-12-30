/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class VerticalJLabel extends JPanel
{
    private int vLabelWidth = 30 ;
    private int vLabelHeight = 129 ;
    private Color labelFontColor ;
    private String text ;
    private Font stringFont ;
    
    public VerticalJLabel(String text)
    {
        labelFontColor = new Color(0, 0, 0);
        this.text = text;
        stringFont = new Font("sanSerif", Font.PLAIN, 17) ;
       
        // lay out the components and display them
        setOpaque(false);
        setPreferredSize(new Dimension(vLabelWidth, vLabelHeight));
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        
        // draw the font in the desired direction
        Graphics2D graphics = (Graphics2D) g;
        
        graphics.setColor(labelFontColor);
        AffineTransform tr = graphics.getTransform();
        
        graphics.rotate(  -Math.PI /2);
        graphics.translate(-vLabelHeight+5 , vLabelWidth-5 );
        
        graphics.setFont(stringFont);
        graphics.drawString(text, -20, 0);
        
        graphics.setTransform(tr);
        
        graphics.dispose();
        
    }        
}
