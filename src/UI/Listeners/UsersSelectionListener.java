/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Listeners;

import AppActions.MenuBar;
import DbConnection.TransactionDetails;
import DbConnection.UsersDetails;
import UI.BottomCenterPanel;
import UI.CenterPanel;
import UI.Charts.ChartPlot;
import UI.Charts.GraphNode;
import UI.Charts.GraphPanel;
import UI.Charts.Node;
import UI.LeftPanel;
import UI.MonthChartNavigator;
import credit.worthiness.system.CreditWorthinessSystem;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 * This class is activated when the JComboBox is selected
 * and performs the following actions
 *      1. Changes the Image on the left Panel
 *      2. Changes the label below the image & date of joining
 *      3. Specifies the current user as the indicated user
 *      4. Set the Model for the JList with the user's model
 *      5. Sets the model of the Chart with the
 *          transaction details of the user
 * @author rmbitiru
 */
public class UsersSelectionListener implements 
        ActionListener
{          
    public UsersSelectionListener(){}
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(SwingUtilities.isEventDispatchThread())
        {
            // make sure that the user really wants to change the
            // current user
            int resp = JOptionPane.showConfirmDialog(null, 
                    "Are you sure you want to change to a new user?", 
                    "Change User?", JOptionPane.YES_NO_OPTION) ;
            
            if(resp==JOptionPane.YES_OPTION)
            {
                // disable the user options for the
                // previously selected user while querying
                // for the new user details
                MenuBar.enableUserMenuOptions(false);
                LeftPanel.deleteUserAction.setEnabled(false);
                
                // get the selected user and 
                // change the various labels with the user's name
                // and their joining date
                final ItemSelectable is = (ItemSelectable) e.getSource() ;
                Object selected[] = is.getSelectedObjects() ;
                
                final String sName = (selected.length == 0)? null: (String)selected[0];
                
                if(null == sName )
                {
                    System.out.println ("Error: Error with the selected user !" ) ;
                    return ;
                }
                
                SwingWorker updateLeftPanel = new SwingWorker<String[],Void>()
                {
                    String [] userDetails = new String[4] ; //stores the userID(0),
                                                   //  userName(1), joiningDate (2),
                                                    // image_path(3)
                    @Override
                    protected String[] doInBackground() 
                    {
                        // get the userID for the selected user
                        
                        UsersDetails userInfo = new UsersDetails() ;                        
                        userDetails[0] = userInfo.getUserID(sName) ;                                               
                         
                        // specify the current userID
                        CreditWorthinessSystem
                                .specifyCurrentUserID(Integer
                                        .parseInt(userDetails[0])
                                );               
                        
                        // make sure that left Panel is aware 
                        // of the new userID as well
                        LeftPanel.setCurrUserSelectedIndex(
                                Integer.parseInt(userDetails[0])); 
                        
                        // set the userName
                        userDetails[1] = sName;
                        
                        // specify the current user
                        CreditWorthinessSystem.specifyCurrentUser(userDetails[1]);
                        
                        // get the joining Date for the user
                        userDetails[2] = userInfo.getUserJoiningDate(sName);
                        
                        // get the avatar for the user
                        userDetails[3] = "../images/" + userInfo.getUserAvatarName(sName) ;
                        
                        return userDetails ;
                    }
                    
                    @Override
                    public void done()
                    {                          
                        try 
                        {
                            String[] usersDetails = (String[]) get() ;
                            LeftPanel.setUserDetails(usersDetails);   
                            
                            // Enable these menu bar options
                            // for the currently selected uses
                            MenuBar.enableUserMenuOptions(true);
                            LeftPanel.deleteUserAction.setEnabled(true);
                            
                            // set the model for the JList with the list 
                            // of transactions from the current user's model
                            BottomCenterPanel
                                    .setUserTransactionsModel();
                            
                            // set the credit limit status for user
                            // in the prominent right hand panel
                            /*
                           CenterPanel.updateAlertLabel(
                                   getUserCreditOrDebitAmount(
                                        CreditWorthinessSystem.getCurrentUserID()
                                   )); 
                           */
                            // load the transactions for the chart model                                                     
                            updateTransactionsChart();  
                            
                            MonthChartNavigator.activateMonthChartButtonNavigateButtons(true);
                        } 
                        catch (InterruptedException ex) 
                        {
                            System.out.println("Error: " + ex.toString()) ;
                        } 
                        catch (ExecutionException ex) 
                        {
                            System.out.println("Error: " + ex.toString()) ;
                        }
                        catch (IOException ex) 
                        {
                            System.out.println("Error: " + ex.toString());
                        }  
                    }                                       
                };   
                
                // schedule the thread
                updateLeftPanel.execute();
            }            
        }
    }
    
    /**
     * This method obtains the amount by which a customer
     * is in debt or credit and returns this value
     * @param currentUserID
     * @return 
     */
    private double getUserCreditOrDebitAmount(int currentUserID) 
    {
        double creditOrDebitAmount = 0 ;
        
        // get the current month and year
        int [] currYearAndMonth = CenterPanel.getCurrentMonthAndYear() ;
        
        UsersDetails user = new UsersDetails() ;
        creditOrDebitAmount = user.getCustCreditOrDebitAmount(currentUserID, 
                currYearAndMonth[0], currYearAndMonth[1]) ;
        
        return creditOrDebitAmount ;
    }
     
    private void updateTransactionsChart() 
    {        
        int numberOfTransForUserForMonth = 0; 
                
        // get the current year and month
        int [] currYearAndMonth = CenterPanel.getCurrentMonthAndYear() ;  
         
        // get the number of transactions for the current user
        // for the current month and year   
        TransactionDetails t = new TransactionDetails();
        numberOfTransForUserForMonth = t.getUserTransactionsNumberForMonth(CreditWorthinessSystem.getCurrentUserID()
                                    , currYearAndMonth[0],currYearAndMonth[1]);
        
        if(numberOfTransForUserForMonth < 1)
        {
            return ;
        }
          
        // get the minimum and maximum value for transactions
        int [] minMaxTransValues =  t.getMinMaxTransValues(CreditWorthinessSystem.getCurrentUserID(), 
                currYearAndMonth[0],currYearAndMonth[1]) ;
        
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
        ChartPlot[] allChartPlots ;
        allChartPlots = new ChartPlot[1] ;
               
        // start with the credit plot
        // determine the number of nodes in creditplot
        ChartPlot plot1 = new ChartPlot(ChartPlot.TRANSACTION_PLOT,
                numberOfTransForUserForMonth);
        
         // get the transactions details
        String [][] transactionDetails = new String[numberOfTransForUserForMonth][] ; // string mulridimensional array stores the 
                                                        // details of the transactions
        
        transactionDetails = t.getPlottedTransactionDetails(CreditWorthinessSystem.getCurrentUserID(), 
                currYearAndMonth[0],currYearAndMonth[1], numberOfTransForUserForMonth ) ;
         
        // set the number of nodes for the transanction details
        GraphNode[] nodes = new GraphNode[numberOfTransForUserForMonth];
        
        // initialise the variables to store the results
        Vector<String> transactionIDs = new Vector<String>() ; 
        Vector<String> itemNames = new Vector<String>() ;
        Vector<String> transactionDays = new Vector<String>() ;
        Vector<String> itemsNumber = new Vector<String>() ;
        Vector<String> totalItemsCost = new Vector<String>() ;
        
        // unpack the transaction details for plotting
        for(int i = 0 ; i < transactionDetails.length ; i++)
        {                    
                // get the current transaction day
                int currTransDay = Integer.parseInt(transactionDetails[i][1]);                 
                
                /*
                if(i > 0)
                {
                    int previousTransDay = Integer.parseInt(transactionDetails[i-1][1]);
                    if(currTransDay == previousTransDay)
                    {
                        // if the two transactiosn were done on the same day
                        //  then concatenate the results
                        
                    }
                }                 
                 */

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
            
            System.out.println("[" + plotsLocationA.x + "," + plotsLocationA.y + "] \n" ) ;
            
            // scale the node
            // get the translated values
            Point2D.Double plotsLocation0 = new Point2D.Double(( 72 
                + ( (plotsLocationA.x - 1)* (570-72)/(noOfDaysInMonth-1) )),
                ( 200  - 2 - ( (plotsLocationA.y - minMaxTransValues[0]) * 198.0/(minMaxTransValues[1]-minMaxTransValues[0]) ))) ;
            
            System.out.println("[" + plotsLocation0.x + "," + plotsLocation0.y + "] \n" ) ;
            
            String[] infor = {transactionDetails[i][2], 
                            transactionDetails[i][4]}; 
            
            // create the node
            int[] transIDs = {Integer.parseInt(transactionDetails[i][0])};
            nodes[i] = new GraphNode(transIDs , 
                    Node.TRANSACTION_ITEM_NODE, plotsLocation0, infor) ;
            
            // add the node to the plot
            plot1.addNode(nodes[i]);
        }
        
        /*
        
        // set the transaction IDs
        int[] transactionsID0 = {1234, 4567 } ;
        String[] info = {"Rice, Biscuits, Milk", "200"} ;
        
        int [] transactionsID1 = {2345};
        String[] info1 = {"Rice", "200"} ;
        
        int[] transactionsID2 = {1234, 4567 } ;
        String[] info2 = {"Rice, Biscuits, Milk", "200"} ;
        
        int [] transactionsID3 = {2345};
        String[] info3 = {"Rice", "200"} ;                                    
        
         // set the plot locations for the nodes
        Point2D.Double plotsLocationA = new Point2D.Double(12,100) ;
        
        // get the translated values
        Point2D.Double plotsLocation0 = new Point2D.Double(( 72 
                + (plotsLocationA.x * (minMaxTransValues[1]-minMaxTransValues[0]-2)/noOfDaysInMonth )),
                ( 200  - 2 - ( plotsLocationA.y * 198.0/(minMaxTransValues[1]-minMaxTransValues[0]-2) ))) ;
        
        // actual values for  node
        Point2D.Double plotsLocationB = new Point2D.Double(18,280) ;
        
        // translated values for a node
        Point2D.Double plotsLocation1 = new Point2D.Double(( 72 
                + (plotsLocationB.x * (minMaxTransValues[1]-minMaxTransValues[0]-2)/noOfDaysInMonth )),
                ( 200  - 2 - ( plotsLocationB.y * 198.0/(minMaxTransValues[1]-minMaxTransValues[0]-2) ))) ;
        
        // actual values for  node
        Point2D.Double plotsLocationC = new Point2D.Double(20,280) ;
        
        // translated values for a node
        Point2D.Double plotsLocation2 = new Point2D.Double(( 72 
                + (plotsLocationC.x * (minMaxTransValues[1]-minMaxTransValues[0]-2)/noOfDaysInMonth )),
                ( 200  - 2 - ( plotsLocationC.y * 198.0/(minMaxTransValues[1]-minMaxTransValues[0]-2) ))) ;
        
        // actual values for  node
        Point2D.Double plotsLocationD = new Point2D.Double(23,200) ;
        
        // translated values for a node
        Point2D.Double plotsLocation3 = new Point2D.Double(( 72 
                + (plotsLocationD.x * (minMaxTransValues[1]-minMaxTransValues[0]-2)/noOfDaysInMonth )),
                ( 200  - 2 - ( plotsLocationD.y * 198.0/(minMaxTransValues[1]-minMaxTransValues[0]-2) ))) ;
        
        // add all the variables into the nodes
        nodes[0] = new GraphNode(transactionsID0, Node.TRANSACTION_ITEM_NODE, plotsLocation0, info) ;
        nodes[1] = new GraphNode(transactionsID1, Node.TRANSACTION_ITEM_NODE, plotsLocation1, info1) ;
        nodes[2] = new GraphNode(transactionsID2, Node.TRANSACTION_ITEM_NODE, plotsLocation2, info1) ;
        nodes[3] = new GraphNode(transactionsID3, Node.TRANSACTION_ITEM_NODE, plotsLocation3, info1) ;
        
        // add the nodes to the plot
        plot1.addNode(nodes[0]);
        plot1.addNode(nodes[1]);
        plot1.addNode(nodes[2]);
        plot1.addNode(nodes[3]);
        
        */
        
        // add these to the array of plots
        allChartPlots[0] = plot1 ;
        
        // go to the debit plot
        System.out.println("Setting the model for the chart");   
        // get the transactions for the selected user
        // for the obtained month and year
        CenterPanel.setChartModel(CenterPanel.month, CenterPanel.year, 
                minMaxTransValues, allChartPlots);
    }
}
