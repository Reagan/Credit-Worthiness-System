/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import AppActions.AbstractedAction;
import AppActions.AppAction;
import AppActions.GoToNextMonthAction;
import AppActions.GoToPreviousMonthAction;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class MonthChartNavigator extends JPanel
{
    private final int COMPONENT_WIDTH = 260;
    private final int COMPONENT_HEIGHT = 30;
    private DepthButton previousMonthButton = null ;
    private DepthButton nextMonthButton = null ;
    private static JLabel monthNameLabel ;
    private static String monthName =  new String() ;
    private Font monthFont ;
    private AppAction monthMoveNextAction ;
    private AppAction monthMovePreviousAction ;
    private static final String[] months = {"January", "February", "March", "April", "May",
                "June", "July", "August", "September", "October",
                "November", "December" } ;
    
    public MonthChartNavigator()
    {                
        // set the monthName and year
        monthFont = new Font("sanSerif", Font.PLAIN, 17) ;
               
        // create the month Label and year
        monthNameLabel = new JLabel();   
        monthNameLabel.setFont(monthFont);
        monthNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        
        // set the label for the month and year
        int[] monthYear = getCurrentMonthAndYear();
        setMonthAndYear(monthYear[0], monthYear[1]);        
                      
        // add the action for the previous month        
        monthMovePreviousAction = new AppAction(nextMonthButton, "<", 
                                false, KeyEvent.VK_RIGHT);
        monthMovePreviousAction.addActionClass(new GoToPreviousMonthAction());
        previousMonthButton = new DepthButton(monthMovePreviousAction);
        previousMonthButton.addActionListener(monthMovePreviousAction);
        
        // add the action for the next month        
        monthMoveNextAction = new AppAction(nextMonthButton, ">", 
                                false, KeyEvent.VK_RIGHT);
        monthMoveNextAction.addActionClass(new GoToNextMonthAction());
        nextMonthButton = new DepthButton(monthMoveNextAction);
        nextMonthButton.addActionListener(monthMoveNextAction);
              
        
        // lay out the components
        setPreferredSize(new Dimension(COMPONENT_WIDTH, COMPONENT_HEIGHT));        
        
        setLayout(new BorderLayout());
        add(previousMonthButton, BorderLayout.LINE_START);
        add(nextMonthButton, BorderLayout.LINE_END); 
        add(monthNameLabel, BorderLayout.CENTER);
        
        setOpaque(false);
    }       
    
    public static void setMonthAndYear(int month, int year)
    {        
        monthName = months[month] + ", " +year ;
        monthNameLabel.setText(monthName);
    }
    
    public int[] getCurrentMonthAndYear() 
    {
        int [] monthYear = new int[2] ;
        
        Calendar cal = Calendar.getInstance();    
        monthYear[0] = cal.get(Calendar.MONTH) ;
        monthYear[1] = cal.get(Calendar.YEAR) ;
                
        return  monthYear ;
    }
}
