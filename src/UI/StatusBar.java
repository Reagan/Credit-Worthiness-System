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
   public static int statusBarHeight = 25 ;
   private static JProgressBar jpbar ;
   private static JLabel statusMessage = new JLabel();
   private JSeparator verticalSeparator ;
   private FlowLayout fLayout;
   
   // spevify the types of progress bars
   private static final int INDETERMINATE_PROGRESS_BAR = 0 ;
   private static final int PROGRESSIVE_PROGRESS_BAR = 1 ;

   private static StatusBar statusBarClass = null ;
   
   private StatusBar() 
   {
       jpbar = new JProgressBar();
       
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
   
   public static void updateStatusMessage(String message)
   {
       statusMessage.setText(message);
   }
   
   /**
    * This method sets the progress bar to the desired type and 
    * returns an instance of the class
    * @param progressBarType
    * @return 
    */
   public static StatusBar getInstance()
   {
       if  ( null== statusBarClass )
       {
          statusBarClass = new StatusBar()  ;
       }
       return statusBarClass ;
   }
   
   /**
    * This method sets the progress bar instance to the desired JProgressBar
    * type
    * @param progressBarType this sets the type of the progress bar
    */
    public void setProgressBarType(int progressBarType)
    {
       // set the progress bar type
       if ( null == jpbar )
       {
           jpbar = new JProgressBar() ;
       }
       
       // set the progress bar type
       if( progressBarType == INDETERMINATE_PROGRESS_BAR)
       {
           jpbar.setIndeterminate(true);
       }
       else if ( progressBarType ==  PROGRESSIVE_PROGRESS_BAR)
       {
           jpbar.setIndeterminate(false);
       }
    }  
       
   /**
    * This method updates the progress bar with the desired value
    * for the progress bar
    * @param count 
    */
   public static void updateProgressBar(final int count)
   {
       // make sure that the progress bar is indeterminate
       jpbar.setIndeterminate(false);
       
       Runnable updateProgressBar = new Runnable() {
            @Override
            public void run() {
                jpbar.setValue(count);
            }           
       };
       
       updateProgressBar.run();
   }
}
