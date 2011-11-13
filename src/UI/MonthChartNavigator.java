/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class MonthChartNavigator extends JPanel
{
    private final int COMPONENT_WIDTH = 230;
    private final int COMPONENT_HEIGHT = 38;
    private final DepthButton previousMonthButton ;
    private final DepthButton nextMonth ;
    private final JLabel monthNameLabel ;
    private final String monthName ;
    
    public MonthChartNavigator()
    {
        // initialise the variables
        previousMonthButton = new DepthButton("<");
        nextMonth = new DepthButton(">");
        
        // set the monthName and year
        monthName = "November, 2011";
        monthNameLabel = new JLabel(monthName);   
        monthNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
       
        // lay out the components
        setPreferredSize(new Dimension(COMPONENT_WIDTH, COMPONENT_HEIGHT));        
        
        setLayout(new BorderLayout());
        add(previousMonthButton, BorderLayout.LINE_START);
        add(nextMonth, BorderLayout.LINE_END); 
        add(monthNameLabel, BorderLayout.CENTER);
        
        setOpaque(false);
    }           
}
