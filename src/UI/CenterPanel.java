/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import DbConnection.TransactionDetails;
import DbConnection.UsersDetails;
import UI.Charts.Chart;
import UI.Charts.ChartPlot;
import UI.Charts.GraphNode;
import UI.Charts.Grid;
import UI.Charts.Node;
import credit.worthiness.system.CreditWorthinessSystem;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class CenterPanel extends JPanel
{
    private static AlertLabel alerter ;
    private MonthChartNavigator monthNav ;
    private final JLabel title ;
    private final VerticalJLabel yAxisLabel; 
    private static Chart chart;
    public static int month ;
    public static int year ;
    public static int[] minMaxTransValues= {0, 0};    
    public static ChartPlot[] allChartPlots = null ;
    
    public CenterPanel()
    {
        // initialise the components
        alerter = new AlertLabel(0.00);
        monthNav = new MonthChartNavigator();
        title = new JLabel("Transactions");
        title.setFont(new Font("sanSerif", Font.PLAIN, 31));
        yAxisLabel = new VerticalJLabel("Amount, Kshs");
        
        // get the current month and year
        getCurrentMonthAndYear();
        
        // create a new Chart instance
        chart = new Chart();    
        
        // add a test border
        /**
        Border outterBorder = BorderFactory.createEmptyBorder(10,10,10,10);
        Border innerBorder = BorderFactory.createTitledBorder("Chart");
        Border compoundBorder = BorderFactory.createCompoundBorder(outterBorder, innerBorder);
        chart.setBorder(compoundBorder);
        **/
        
        // set the model to null since no user has been selected
        allChartPlots = null ;
        
        // set the chart model
        setChartModel(month, year, minMaxTransValues, allChartPlots);                
        
        // lay out and add the components
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // topmost panel
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(Box.createRigidArea(new Dimension(72, 0)));
        topPanel.add(title);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(alerter);
        topPanel.add(Box.createRigidArea(new Dimension(25, 0)));
        
        // center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        centerPanel.add(yAxisLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        centerPanel.add(chart);
        centerPanel.add(Box.createHorizontalGlue());
        
        // lower panel
        JPanel lowerPanel = new JPanel();
        lowerPanel.setOpaque(false);
        lowerPanel.setLayout(new FlowLayout());
        lowerPanel.add(monthNav);
        
        // now add the components vertically
        add(Box.createRigidArea(new Dimension(0, 10 )));
        add(topPanel);
        add(Box.createRigidArea(new Dimension(0, 25)));
        add(centerPanel);
        add(Box.createRigidArea(new Dimension(0, 13)));
        add(lowerPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(new JSeparator(SwingConstants.HORIZONTAL));
        add(Box.createRigidArea(new Dimension(0, 10)));
                        
        // finalize the JPanel
        setOpaque(false);
        setPreferredSize(new Dimension(686, 344));
    }
    
    public static void setChartModel(int chartMonth, int chartYear, 
            int[] yMinMax,ChartPlot[] allChartPlots)
    {        
         chart.setModel(chartMonth, chartYear, yMinMax, allChartPlots);
    }

    public static int[] getCurrentMonthAndYear() 
    {
        Calendar cal = Calendar.getInstance();        
        month = cal.get(Calendar.MONTH) ;
        year = cal.get(Calendar.YEAR) ;
        
        int[] currMonthAndYear = new int[2];
        currMonthAndYear[0] = month ;
        currMonthAndYear[1] = year ;
        return currMonthAndYear ;
    }  
    
    public static int[] getChartMonthAndYear()
    {
        if( Chart.currTime[0] == 0 )
        {            
            return getCurrentMonthAndYear() ;
        }
        else
        {            
            return Chart.currTime ;
        }
    }
    
    /**
     * This method displays the alerter with the 
     * required color and the credit/debit amount 
     * for the currently selected user
     * @param amount 
     */
    public static void updateAlertLabel(double amount)
    {
        //alerter = new AlertLabel(amount) ;
        alerter.setAlerterAmount(amount);
        
        if(amount < 0 ) 
        {
            alerter.alertStatus = AlertLabel.OVERSPENT_CONDITION ;
        }            
        else 
        {
            alerter.alertStatus = AlertLabel.UNDERSPENT_CONDITION ;
        }       
        
        // repaint the alerter
        alerter.repaint();
    }
    
    public static void updateTransactionsChart() 
    {        
        // set the initial variables
        int numberOfTransForUserForMonth = 0; 
        double creditLimit = 0 ; // stores the credit limit
        double[] transactionCredits ; // stores the credit items for the transactions
        
        // get the current year and month
        int [] currYearAndMonth = getChartMonthAndYear() ;  
        
        // initialise the chart plots and max and min transaction values to null
        allChartPlots = null ;
        minMaxTransValues[0] = 0 ;
        minMaxTransValues[1] = 0 ;
            
        // get the number of transactions for the current user
        // for the current month and year   
        TransactionDetails t = new TransactionDetails();
        numberOfTransForUserForMonth = t.getUserTransactionsNumberForMonth(CreditWorthinessSystem.getCurrentUserID()
                                    , currYearAndMonth[0],currYearAndMonth[1]);
                        
        if(numberOfTransForUserForMonth > 0)
        {                                  
            // get the credit limit for a user
             UsersDetails userDetails = new UsersDetails();
             creditLimit = Double.parseDouble(
                     userDetails.getUserCreditLimit(CreditWorthinessSystem
                        .getCurrentUserID(), currYearAndMonth[0],currYearAndMonth[1])) ;
                        
            // get the minimum and maximum value for transactions
            minMaxTransValues =  t.getMinMaxTransValues(CreditWorthinessSystem.getCurrentUserID(), 
                    currYearAndMonth[0],currYearAndMonth[1]) ;

            // correct for similar values of minimum and maximum to
            // ensure that a NAN is not obtained
            if( minMaxTransValues[0] == minMaxTransValues[1] )
            {
                minMaxTransValues[0] = 0 ;
            }
            
            // get the number of days in a month
            int noOfDaysInMonth ;
            Calendar cal = new GregorianCalendar(currYearAndMonth[0], 
                    currYearAndMonth[1],  1) ;
            noOfDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH) ; 

            // get the transaction details for the selected user for
            // the selected month and year. These details should be packaged in Plots   
            //
            // create plot instance
            // ChartPlots should be 2 to have one for credit transactions
            // and another for debit transactions
            allChartPlots = new ChartPlot[2] ;

            // start with the credit plot
            // determine the number of nodes in creditplot
            ChartPlot plot1 = new ChartPlot(ChartPlot.TRANSACTION_PLOT,
                    numberOfTransForUserForMonth);
            
            ChartPlot plot2 = new ChartPlot(ChartPlot.CREDIT_PLOT,
                    numberOfTransForUserForMonth);

             // get the transactions details
            String [][] transactionDetails = new String[numberOfTransForUserForMonth][] ; // string mulridimensional array stores the 
                                                            // details of the transactions

            transactionDetails = t.getPlottedTransactionDetails(CreditWorthinessSystem.getCurrentUserID(), 
                    currYearAndMonth[0],currYearAndMonth[1], numberOfTransForUserForMonth ) ;

            // set the number of nodes for the transanction details
            GraphNode[] nodes = new GraphNode[numberOfTransForUserForMonth];
            GraphNode[] creditNodes = new GraphNode[numberOfTransForUserForMonth];

            // initialise the variables to store the results
            Vector<String> transactionIDs = new Vector<String>() ; 
            Vector<String> itemNames = new Vector<String>() ;
            Vector<String> transactionDays = new Vector<String>() ;
            Vector<String> itemsNumber = new Vector<String>() ;
            Vector<String> totalItemsCost = new Vector<String>() ;

            // initialise the credit transactions limit
            transactionCredits = new double[transactionDetails.length];
            
            // get the minimum transaction amounts
            for(int i = 0 ; i < transactionDetails.length ; i++)
            { 
                Point2D.Double plotsLocationA = new Point2D.Double(
                        Double.parseDouble(transactionDetails[i][1])
                        ,Double.parseDouble(transactionDetails[i][4])) ;
                
                // get the credit transactions items for each transaction
                transactionCredits[i] = creditLimit ;
                creditLimit = transactionCredits[i] - plotsLocationA.y ;                   
            }
            
            // compare the transaction details to the minimum 
            // value for the transactions and use this to 
            // scale the plotted transactions
            double[] storedCoords = new double[transactionDetails.length] ;
           
            // copy each of the variables from one array to
            // another to prevent copying arrays by reference
            for (int i=0; i<transactionCredits.length;i++)
            {
                storedCoords[i] = transactionCredits[i];
            }
            
            Arrays.sort(storedCoords);
            
            // set the max value
            if( minMaxTransValues[minMaxTransValues.length-1]
                    < storedCoords[storedCoords.length-1]) 
            {
                minMaxTransValues[minMaxTransValues.length-1] =
                       (int) storedCoords[storedCoords.length-1];
            }
            
            // set the minimum value
             if( storedCoords[0] < minMaxTransValues[0] ) 
            {
                minMaxTransValues[0] =
                       (int) storedCoords[0];
            }
            
            // unpack the transaction details for plotting
            for(int i = 0 ; i < transactionDetails.length ; i++)
            {                    
                // get the current transaction day
                int currTransDay = Integer.parseInt(transactionDetails[i][1]);                                 

                // get the transaction ID
                transactionIDs.add(transactionDetails[i][0]);

                // get the day
                transactionDays.add(transactionDetails[i][1]);

                // get the item name
                itemNames.add(transactionDetails[i][2]);

                // get the items number
                itemsNumber.add(transactionDetails[i][3]);

                // get the total items cost
                totalItemsCost.add(transactionDetails[i][4]);                    


                // set the plot locations for the node
                Point2D.Double plotsLocationA = new Point2D.Double(Double.parseDouble(transactionDetails[i][1])
                        ,Double.parseDouble(transactionDetails[i][4])) ;              

                // scale the node
                // get the translated values
                // use the values from the grid to scale
                Point2D.Double plotsLocation0 = new Point2D.Double(( Grid.X_POS - 4 
                    + ( (plotsLocationA.x - 1)* Grid.GRID_WIDTH/(noOfDaysInMonth-1) )),
                    ( Grid.GRID_HEIGHT - ( (plotsLocationA.y - 
                        minMaxTransValues[0]) * Grid.GRID_HEIGHT/
                        (minMaxTransValues[1]-minMaxTransValues[0]) ))) ;
                
                // scale the credits plot
                // get the credit plot translated
                Point2D.Double plotsLocationC = new Point2D.Double(( Grid.X_POS - 4
                    + ( (plotsLocationA.x - 1)* Grid.GRID_WIDTH/(noOfDaysInMonth-1) )),
                    ( Grid.GRID_HEIGHT - ( (transactionCredits[i] - 
                        minMaxTransValues[0]) * Grid.GRID_HEIGHT/
                        (minMaxTransValues[1]-minMaxTransValues[0]) ))) ;                
                
                String[] infor = {transactionDetails[i][2], 
                                transactionDetails[i][4]}; 

                // create the node
                int[] transIDs = {Integer.parseInt(transactionDetails[i][0])};
                nodes[i] = new GraphNode(transIDs , 
                        Node.TRANSACTION_ITEM_NODE, plotsLocation0, infor) ;
                
                creditNodes[i] = new GraphNode(transIDs , 
                        Node.CREDIT_ITEM_NODE, plotsLocationC, infor) ;

                // add the node to the plot
                plot1.addNode(nodes[i]);
                plot2.addNode(creditNodes[i]);
            }          
            
            // add these to the array of plots
            allChartPlots[0] = plot1 ;
            allChartPlots[1] = plot2 ;           
        }        
    }
}
