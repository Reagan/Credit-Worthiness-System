/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class StatusBar extends JPanel
{
   public static int statusBarHeight = 21 ;
   private Color bgColor = new Color(255, 255, 255, 255);
   private JProgressBar jpbar = new JProgressBar();
   private JLabel statusMessage = new JLabel("Connected");
   private JSeparator verticalSeparator ;
   private FlowLayout fLayout;
    
   public StatusBar() 
   {
       fLayout = new FlowLayout(FlowLayout.RIGHT);
       verticalSeparator = new JSeparator(SwingConstants.VERTICAL);
       UIManager.getDefaults().put("Separator.foreground", Color.BLACK);
       
       setOpaque(false);
       setLayout(fLayout);
       setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
       setMinimumSize(new Dimension(getWidth(), statusBarHeight));

       add(jpbar);
       add(verticalSeparator);
       add(statusMessage); 
   }
   
   @Override
    protected void paintComponent(Graphics g) 
   {
        Graphics2D g2 = (Graphics2D) g;
        Paint oldPaint = g2.getPaint();
        
        LinearGradientPaint p;
            
        p = new LinearGradientPaint(0.0f, 0.0f, 0.0f, 20.0f,
          new float[] { 0.0f, 0.5f, 0.501f, 1.0f },
          new Color[] { new Color(224, 228, 231),
                        new Color(207, 210, 215),
                        new Color(207, 210, 215),
                        new Color(189, 193, 196) });
        g2.setPaint(p);
        g2.fillRect(0, 0, getWidth(), statusBarHeight);
        
        g2.setPaint(oldPaint);
        super.paintComponent(g);
    }
   
   public void updateStatusMessage(String message)
   {
       statusMessage.setText(message);
   }
   
   public void updateProgressBar(int count)
   {
       // @TODO
       // updates the progress bar
   }
}
