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
                // previsously selected user while querying
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
                        userDetails[3] = userInfo.getUserAvatarName(sName) ;
                        
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
                            
                            // load the transactions for the chart model                                                     
                            updateTransactionsChart();                            
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
        
        // get the minimum and maximum value for transactions
        int [] minMaxTransValues =  t.getMinMaxTransValues(CreditWorthinessSystem.getCurrentUserID(), 
                currYearAndMonth[0],currYearAndMonth[1]) ;
        
        // get the transaction details for the selected user for
        // the selected month and year. These details should be packaged in Plots   
        //
        // create plot instance
        ChartPlot[] allChartPlots ;
        allChartPlots = new ChartPlot[1] ;
        
        // start with the credit plot
        // determine the number of nodes in creditplot
        ChartPlot plot1 = new ChartPlot(ChartPlot.TRANSACTION_PLOT,
                2);
        GraphNode[] nodes = new GraphNode[2];
        
        // set the transaction IDs
        int[] transactionsID0 = {1234, 4567 } ;
        String[] info = {"Rice, Biscuits, Milk", "200"} ;
        
        int [] transactionsID1 = {2345};
        String[] info1 = {"Rice", "200"} ;
        
        // set the plot locations for the nodes
        Point2D.Double plotsLocationA = new Point2D.Double(12,100) ;
        
        // get the number of days in a month
        int noOfDaysInMonth ;
        Calendar cal = new GregorianCalendar(currYearAndMonth[0], 
                currYearAndMonth[1],  1) ;
        noOfDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH) ;      
        
        // get the translated values
        Point2D.Double plotsLocation0 = new Point2D.Double(( 72 
                + (plotsLocationA.x * (minMaxTransValues[1]-minMaxTransValues[0]-2)/noOfDaysInMonth )),
                ( 200  - 2 - ( plotsLocationA.y * 198.0/(minMaxTransValues[1]-minMaxTransValues[0]-2) ))) ;
        
        // actual values for  node
        Point2D.Double plotsLocationB = new Point2D.Double(18,480) ;
        
        // translated values for a node
        Point2D.Double plotsLocation1 = new Point2D.Double(( 72 
                + (plotsLocationB.x * (minMaxTransValues[1]-minMaxTransValues[0]-2)/noOfDaysInMonth )),
                ( 200  - 2 - ( plotsLocationB.y * 198.0/(minMaxTransValues[1]-minMaxTransValues[0]-2) ))) ;
        
        // add all the variables into the nodes
        nodes[0] = new GraphNode(transactionsID0, Node.TRANSACTION_ITEM_NODE, plotsLocation0, info) ;
        nodes[1] = new GraphNode(transactionsID1, Node.TRANSACTION_ITEM_NODE, plotsLocation1, info1) ;
        
        // add the nodes to the plot
        plot1.addNode(nodes[0]);
        plot1.addNode(nodes[1]);
        
        // add these to the array of plots
        allChartPlots[0] = plot1 ;
        
        // go to the debit plot
        
        // get the transactions for the selected user
        // for the obtained month and year
        CenterPanel.setChartModel(CenterPanel.month, CenterPanel.year, 
                minMaxTransValues, allChartPlots);
    }
}
