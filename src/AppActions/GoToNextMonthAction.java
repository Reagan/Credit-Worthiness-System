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
public class GoToNextMonthAction extends AbstractedAction
{
    private int[] currentMonthAndYear = new int[2];
    
    public GoToNextMonthAction() {}
    
    @Override
    public void run() 
    {
        currentMonthAndYear = Chart.goToNextMonth();
        MonthChartNavigator.setMonthAndYear(currentMonthAndYear[0],
                currentMonthAndYear[1]);
    }  
}
