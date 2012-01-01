/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import AppActions.AbstractedAction;
import AppActions.AppAction;
import AppActions.GoToNextMonthAction;
import AppActions.GoToPreviousMonthAction;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
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
    private final int COMPONENT_WIDTH = 280;
    private final int COMPONENT_HEIGHT = 30;
    private static DepthButton previousMonthButton = null ;
    private static DepthButton nextMonthButton = null ;
    private static JLabel monthNameLabel ;
    private static String monthName =  new String() ;
    private Font monthFont ;
    private static AppAction monthMoveNextAction ;
    private static AppAction monthMovePreviousAction ;
    private static final String[] months = {"January", "February", "March", "April", "May",
                "June", "July", "August", "September", "October",
                "November", "December" } ;
    
    public MonthChartNavigator()
    {                
        // set the monthName and year
        monthFont = new Font("sanSerif", Font.PLAIN, 15) ;
               
        // create the month Label and year
        monthNameLabel = new JLabel();   
        monthNameLabel.setFont(monthFont);
        monthNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        // set the label for the month and year
        int[] monthYear = getCurrentMonthAndYear();
        setMonthAndYear(monthYear[0], monthYear[1]);        
                      
        // add the action for the previous month        
        monthMovePreviousAction = new AppAction(nextMonthButton, "<", 
                                false, KeyEvent.VK_LEFT);
        monthMovePreviousAction.addActionClass(new GoToPreviousMonthAction());
        previousMonthButton = new DepthButton(monthMovePreviousAction);
        
        // add the action for the next month        
        monthMoveNextAction = new AppAction(nextMonthButton, ">", 
                                false, KeyEvent.VK_RIGHT);
        monthMoveNextAction.addActionClass(new GoToNextMonthAction());
        nextMonthButton = new DepthButton(monthMoveNextAction);
              
        
        // lay out the components
        setPreferredSize(new Dimension(COMPONENT_WIDTH, COMPONENT_HEIGHT));        
        
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        add(previousMonthButton);
        add(monthNameLabel);
        add(nextMonthButton);         
        
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
    
    /**
     * This method activates/disables the buttons that allow a 
     * user to scroll from month to month
     * @param state 
     */
    public static void activateMonthChartButtonNavigateButtons(boolean state)
    {                
        monthMovePreviousAction.setEnabled(state);        
        monthMoveNextAction.setEnabled(state);
    }
}
