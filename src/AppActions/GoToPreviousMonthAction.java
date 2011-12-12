/**
 * Credit Worthiness System Version 1.0
 */
package AppActions;

import UI.Charts.Chart;
import UI.MonthChartNavigator;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class GoToPreviousMonthAction extends AbstractedAction
{
    private int[] currentMonthAndYear = new int[2];
    
    public GoToPreviousMonthAction() {}
    
    @Override
    public void run() 
    {
        currentMonthAndYear = Chart.goToPreviousMonth();
        MonthChartNavigator.setMonthAndYear(currentMonthAndYear[0],
                currentMonthAndYear[1]);
    } 
}
