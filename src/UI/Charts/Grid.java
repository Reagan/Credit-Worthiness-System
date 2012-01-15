/**
 * Credit Worthiness System Version 1.0
 */
package UI.Charts;

import UI.Models.ChartModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.nio.DoubleBuffer;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JComponent;

/**
 * This class displays the grid that appears 
 * as the background of the Chart
 * 
 * Plottable Grid Dimensions where nodes 
 * may be drawn are 
 * 
 *   (72,2) xxxxxxxxxxxxxxxxxxxxxxxxxxxxx (570,2)
 *          x                           x
 *          x                           x
 *          x                           x
 *          x                           x
 *          x                           x
 * (72,200) xxxxxxxxxxxxxxxxxxxxxxxxxxxxx (570,200)
 * 
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public final class Grid extends JComponent
{
    public static int GRID_WIDTH  ;
    public static int GRID_HEIGHT  ;
    public static int Y_POS = 7 ;
    public static int X_POS = 30 ;
    private final int NO_OF_HORIZONTAL_LINES = 5 ;
    private final int NO_OF_VERTICAL_LINES = 10 ;
    private final int BUFFER_HEIGHT = 17 ;
    private final int BUFFER_WIDTH = 30 ;
   
    private final Color bgColor  = Color.WHITE ;
    private final Color gridBorderColor = new Color(51, 51, 51) ;
    private final Color gridLinesColor = new Color(204, 204, 204) ;
    private final Color gridAxisValues  = new Color(51, 51, 51);
    
    private int calendarMonth ; 
    private int calendarYear ;
    private int[] yMinAndMaxValues ;
    
    private String[] legendItems ;
    private Color[] legendColors ;
               
    private Legend legend = new Legend();
    
    public Grid(ChartModel cModel)
    {           
        // obtain the dimensions for the grid and its axis data    
        X_POS += Chart.insets.left ;
        Y_POS += Chart.insets.top ;
        
        // use this data to set the grid data information
        GRID_WIDTH = getWidth() -  ( X_POS  + BUFFER_WIDTH ) ;
        GRID_HEIGHT = getHeight() - ( Y_POS  + BUFFER_HEIGHT );               
        
        // initialise variables
        calendarMonth = cModel.getMonth() ;
        calendarYear = cModel.getYear() ;
        this.yMinAndMaxValues = cModel.getYMinMax() ;

        // set layout properties
        setOpaque(false);            
        setLayout(new BorderLayout());
    }

    public void goToMonth(int month, int year, 
            int[] yMinAndMaxValues)
    {
        calendarMonth = month ;
        calendarYear = year ;
        this.yMinAndMaxValues = yMinAndMaxValues ;
        
       repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {
        // get the graphics object
        Graphics2D graphics  = (Graphics2D) g ;
        
        // add the antialis to make sure that the 
        // displayed text and images all look 
        // OK
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
           
        // fill the background 
        graphics.setColor(bgColor);
        graphics.fillRect(X_POS , Y_POS, GRID_WIDTH, GRID_HEIGHT);
        
        // draw the grid 
        graphics.setColor(gridBorderColor);
        graphics.drawRect(X_POS, Y_POS , 
                GRID_WIDTH, GRID_HEIGHT);
        
        // draw the gridLines depending on the
        // month that the grid is for        
        graphics.setColor(gridLinesColor);
        int noOfDaysInMonth = getNumberOfDays(calendarMonth ,calendarYear) ;
        
        // get the separation values for the
        // lines and the values plotted
        int xSeparation = GRID_WIDTH / NO_OF_VERTICAL_LINES ;
        int ySeparation = GRID_HEIGHT / NO_OF_HORIZONTAL_LINES ;
        
        // y-values separation values
        Arrays.sort(yMinAndMaxValues);
        int ySeparationValue = ( yMinAndMaxValues[yMinAndMaxValues.length - 1] 
                                    - yMinAndMaxValues[0] ) / NO_OF_HORIZONTAL_LINES ;
         
        // x-values separation values
        float xSeparationValue = (float) ( (float) GRID_WIDTH / (noOfDaysInMonth - 1 ))  ;
        
        // plot the horizontal lines
        for(int i = 1, plottedYValue = Y_POS + ySeparation
                ; i < 5
                    ; i++, plottedYValue += ySeparation)
        {
            graphics.drawLine( X_POS + 1, plottedYValue, X_POS + GRID_WIDTH -1, 
                        plottedYValue);             
        }
        
        // plot the vertical lines
        for(int i = 1, plottedXValue = X_POS + xSeparation
                    ; i<10
                        ; i++, plottedXValue += xSeparation)
        {
            graphics.drawLine( plottedXValue, Y_POS + 1, plottedXValue, 
                        Y_POS + GRID_HEIGHT - 1);             
        }
        
        // draw the days of the month (x-axis)
        //
        // make sure that the last date is not plotted
        // this is best for days with noOfMonths%10 != 0
        // as the last day of the month
        graphics.setColor(gridAxisValues);
        
        // draw the last point on the x-axis
        graphics.drawString( Integer.toString(noOfDaysInMonth)
                        , X_POS + GRID_WIDTH 
                            , Y_POS + GRID_HEIGHT + BUFFER_HEIGHT);
        
        int monthDate = 1;
        
        // draw the days in each month
        for(float  i = 1 , plottedXValue = X_POS
                    ; i<= noOfDaysInMonth
                        ; i++, plottedXValue += xSeparationValue
                            , monthDate ++)
        {
            if((i==1 || i%((int)noOfDaysInMonth/NO_OF_VERTICAL_LINES) == 0 ) &&
                    i != noOfDaysInMonth)
            {     
                graphics.drawString( Integer.toString(monthDate)
                        , plottedXValue 
                            , Y_POS + GRID_HEIGHT + BUFFER_HEIGHT);                                                            
            }
        }
        
        // draw the values (y-axis)
        graphics.setColor(gridAxisValues);
        
        // make sure that the maximum value for the x-axis 
        // is not 0
        // this is the default condition when no user data has been loaded
        if( 0 != yMinAndMaxValues[yMinAndMaxValues.length-1] )
        {
            for(int i = 0, plottedYValue = Y_POS, 
                    yValue = yMinAndMaxValues[yMinAndMaxValues.length-1]
                    ; i <= 5
                    ; i++, plottedYValue += ySeparation, yValue -= ySeparationValue )
            {
                graphics.drawString( Integer.toString(yValue) , X_POS - BUFFER_WIDTH, 
                        plottedYValue + 5);             
            }
        }
        
        // add the legend
        drawLegend(graphics, legendItems, legendColors);                       
        
        // finally get rid of the graphics object        
        graphics = null ;            
    }

    /**
     * returns the number of days in the month
     * @param calendarMonth
     * @return 
     */
    private int getNumberOfDays(int calendarMonth, int calendarYear ) 
    {
        int DAY_OF_MONTH = 1 ;
        Calendar cal = new GregorianCalendar(calendarYear, calendarMonth, DAY_OF_MONTH) ;
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH) ;        
    }

    public void setLegendValues(String[] legendItems, Color[] legendColors) 
    {
        this.legendItems = legendItems ;
        this.legendColors = legendColors ;
    }

    private void drawLegend(Graphics2D graphics, 
                String[] legendItems, Color[] legendColors) 
    {
        if(legendColors == null || legendItems.length < 1)
            return;
        
        // these are the dimensions of the little 
        // squares drawn to represent the legend items
        int boxWidth = 9 ;
        int boxHeight = 9 ;
        String creditLimitText = "Credit Limit" ;
        String transactions = "Transactions" ;
        
        // draw each of the rects with the 
        // various color types for the drawn items
        graphics.setColor(new Color(255, 0, 0));
        graphics.fillRect(( GRID_WIDTH - 82 ), ( 22 + Y_POS ), boxWidth, boxHeight);
        
        graphics.setColor(new Color(0, 97, 0));
        graphics.fillRect(( GRID_WIDTH - 82 ), ( 37 + Y_POS ), boxWidth, boxHeight);
        
        // draw the strings for the legend items
        graphics.setColor(new Color(51, 51, 51)) ;
        graphics.drawString(creditLimitText, ( GRID_WIDTH - 71 ), ( 30 + Y_POS ) );
        graphics.drawString(transactions, ( GRID_WIDTH - 71 ), ( 47 + Y_POS ) );
        
    }  
    
    // provide the dimensions for the grid 
    // relative to the chart container
    @Override
    public int getHeight()
    {
        // difference betwee the chart and grid height
        // is 33 pixels
        return Chart.CHART_HEIGHT - Chart.insets.top - Chart.insets.bottom;
    }
    
    @Override
    public final int getWidth()
    {
        // difference in the width of the contained
        // grid and the chart is 140 pixels
        return Chart.CHART_WIDTH - Chart.insets.left - Chart.insets.right ;
    }
    
}
