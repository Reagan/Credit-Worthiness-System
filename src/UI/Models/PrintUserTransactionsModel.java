/**
 * Credit Worthiness System Version 1.0
 */
package UI.Models;

import DbConnection.UsersDetails;
import java.util.Vector;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *This class contains the model for the transactions for a user. The columns
 * for the table are 
 * <ol>
 *  <li>Transaction Type</li>
 *  <li>Date</li>
 *  <li>Item</li>
 *  <li># 0f Items</li>
 *  <li>Items Worth</li>
 * </ol>
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class PrintUserTransactionsModel implements TableModel 
{
    private int currentUserID ;
    private UsersDetails userTransactions ;    
    private Vector returnedTransactions ;             
    private Object[][] transactions ;
    private String currUser = "all customers" ;
    private int creditOrDebitLimit = 0;
    
    public PrintUserTransactionsModel(int userID)
    {
        // This method determines the transactions for a 
        // specific user
        currentUserID = userID ;          
        
        // populate the Transactions Array
        populateTransactions() ;
    }
    
    /**
     * This method obtains a more precise model for a user's transactions
     * @param viewDebitTransactions
     * @param viewCreditTransactions
     * @param fromMonth
     * @param fromYear
     * @param toMonth
     * @param toYear 
     */
    public PrintUserTransactionsModel(String user, boolean viewDebitTransactions,
            boolean viewCreditTransactions, int fromMonth, int fromYear, 
            int toMonth, int toYear)
    {
        populateTransactions(user, viewDebitTransactions,
            viewCreditTransactions, fromMonth, fromYear, 
            toMonth, toYear) ;
    }
        
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}
    
    @Override
    public void addTableModelListener(TableModelListener l) {}
    
    @Override
    public void removeTableModelListener(TableModelListener l) {}

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) 
    {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int col) 
    {
        switch(col) 
        {
            case 0 :
                return Integer.class ;
            case 1 :                
            case 2 :
            case 3 :
                return String.class;
            case 4 :      
            case 5 :
                return Integer.class;
        }

        throw new AssertionError("invalid column");
    }

    @Override
    public int getRowCount() 
    {
        return transactions.length;
    }

    @Override
    public int getColumnCount() 
    {
        return 6 ;
    }

    @Override
    public String getColumnName(int col) 
    {
        switch(col) 
        {
            case 0 : return "Type" ;
            case 1 : return "Customer" ;
            case 2 : return "Date" ;
            case 3 : return "Item" ;
            case 4 : return "Items #" ;
            case 5 : return "Items Worth" ;
        }

        throw new AssertionError("invalid column");
    }

    @Override
    public Object getValueAt(int row, int col) 
    {
        switch(col) 
        {
            case 0 :
            case 1 :
            case 2 :
            case 3 :  
            case 4 : 
            case 5 : 
                return transactions[row][col] ;            
        }

        throw new AssertionError("invalid column");
    }

    /**
     * Provides a more compact transactions summary for a specific user
     * @param userID
     * @param viewDebitTransactions
     * @param viewCreditTransactions
     * @param fromMonth
     * @param fromYear
     * @param toMonth
     * @param toYear 
     */
    private void populateTransactions (String user, boolean viewDebitTransactions,
            boolean viewCreditTransactions, int fromMonth, int fromYear, 
            int toMonth, int toYear)
    {
        // initialise the transactions object
         transactions = new Object[3][]; 
         
        // get the current user
        
        // get the credit or debit limit
        
        // get the transactions for the user
        Object [] t0 = {1, "Felicita Wanjiru", "1/9/2011" ,"Cooking Oil" ,1, 326 } ;
        Object [] t1 = {1, "Felicita Wanjiru", "1/9/2011" ,"Two kg Maize Flour ",1, 69 } ;
        Object [] t2 = {2, "Felicita Wanjiru", "1/9/2011" ," One kg Sugar" ,1, 98 } ;        
        
        transactions[0] = t0 ;
        transactions[1] = t1 ;
        transactions[2] = t2 ;        
    }
    
    public String getTransactionsOwner() 
    {
        return currUser ;
    }
    
    public int getCreditOrDebitLimit()
    {
        return creditOrDebitLimit ;
    }
    
    /**
     * This method updates the Transactions Double
     * array to format it to fit the model
     */
    private void populateTransactions() 
    {        
        // get the detailed transactions for the user 
        // from the database
        String[][] returnedTransactions ; 
        
      //  UsersDetails usersTransactions = new UsersDetails() ;
      //  returnedTransactions = (String[][]) usersTransactions
      //          .getDetailedUserTransactions(CreditWorthinessSystem.getCurrentUserID()) ;
        
        // loop through the returned data populating 
      //  transactions =  new Object[returnedTransactions.length][]; 
        transactions = new Object[4][]; 
      //  System.out.println("Current User ID: "+CreditWorthinessSystem.getCurrentUserID()) ;
      //  System.out.println("# of returned transactions: " + returnedTransactions.length);
        
        /*
        for (int i = 0;  i < returnedTransactions.length; i ++)
        {
            Object [] t = { returnedTransactions[i][0]
                    + "/"
                    + returnedTransactions[i][1] // date
                    +"/"  // month
                    + returnedTransactions[i][2] ,  // year
                    returnedTransactions[i][3], // Item
                    Integer.parseInt(returnedTransactions[i][4]), // Item Number
                    Integer.parseInt(returnedTransactions[i][5]) }; // Total cost
            
                System.out.println(returnedTransactions[i][0]
                    + "/"
                    + returnedTransactions[i][1]
                    +"/"
                    + returnedTransactions[i][2]
                    + " ," 
                    + returnedTransactions[i][3]
                    + " ,"
                    + Integer.parseInt(returnedTransactions[i][4])
                    + ", "
                    + Integer.parseInt(returnedTransactions[i][5])) ;
                
                transactions[i] =  t ;
        }
         */
        Object [] t0 = {1, "Felicita Wanjiru", "1/9/2011" ,"Cooking Oil" ,1, 326 } ;
        Object [] t1 = {1, "Felicita Wanjiru", "1/9/2011" ,"Two kg Maize Flour ",1, 69 } ;
        Object [] t2 = {2, "Felicita Wanjiru", "1/9/2011" ," One kg Sugar" ,1, 98 } ;
        Object [] t3 = {1, "Felicita Wanjiru", "12/9/2011" ," Quarter Kg Rice ",1, 30 } ;
        
        transactions[0] = t0 ;
        transactions[1] = t1 ;
        transactions[2] = t2 ;
        transactions[3] = t3 ;
    }
}