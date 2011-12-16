/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import javax.swing.Action;
import javax.swing.JButton;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class DepthButton extends JButton
{
    public DepthButton(String text) {
        super(text);
        setContentAreaFilled(false);
    }
    
    public DepthButton(Action action) {
        super();
        setAction(action);
        setContentAreaFilled(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {
        Graphics2D g2 = (Graphics2D) g;
        
        GradientPaint p;
        p = new GradientPaint(0, 0, new Color(0xFFFFFF),
                0, getHeight(), new Color(0xC8D2DE));
        
        Paint oldPaint = g2.getPaint();
        g2.setPaint(p);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setPaint(oldPaint);
        
        
        super.paintComponent(g);
    }
}
