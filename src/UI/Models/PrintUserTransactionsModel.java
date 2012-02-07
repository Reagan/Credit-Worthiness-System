/**
 * Credit Worthiness System Version 1.0
 */
package UI.Models;

import DbConnection.UsersDetails;
import credit.worthiness.system.CreditWorthinessSystem;
import java.util.Vector;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class PrintUserTransactionsModel implements TableModel 
{
    private int currentUserID ;
    private UsersDetails userTransactions ;    
    private Vector returnedTransactions ;             
    private Object[][] transactions ;
    
    public PrintUserTransactionsModel(int userID)
    {
        // This method determines the transactions for a 
        // specific user
        currentUserID = userID ;          
        
        // populate the Transactions Array
        populateTransactions() ;
    }
        
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}
    
    @Override
    public void addTableModelListener(TableModelListener l) {}
    
    @Override
    public void removeTableModelListener(TableModelListener l) {}

    public boolean isCellEditable(int rowIndex, int columnIndex) 
    {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int col) 
    {
        switch(col) 
        {
            case 0:
            case 1:
                return String.class;
            case 2:
            case 3:             
                return Integer.class;
        }

        throw new AssertionError("invalid column");
    }

    public int getRowCount() 
    {
        return transactions.length;
    }

    @Override
    public int getColumnCount() 
    {
        return 4;
    }

    @Override
    public String getColumnName(int col) 
    {
        switch(col) 
        {
            case 0: return "Date" ;
            case 1: return "Item" ;
            case 2: return "Items Number" ;
            case 3: return "Item Worth" ;
        }

        throw new AssertionError("invalid column");
    }

    @Override
    public Object getValueAt(int row, int col) 
    {
        switch(col) 
        {
            case 0:
            case 1:
            case 2:
            case 3:            
                return transactions[row][col];            
        }

        throw new AssertionError("invalid column");
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
        Object [] t0 = {"1/9/2011" ,"Cooking Oil" ,1, 326 } ;
        Object [] t1 = {"1/9/2011" ,"Two kg Maize Flour ",1, 69 } ;
        Object [] t2 = {"1/9/2011" ," One kg Sugar" ,1, 98 } ;
        Object [] t3 = {"12/9/2011" ," Quarter Kg Rice ",1, 30 } ;
        
        transactions[0] = t0 ;
        transactions[1] = t1 ;
        transactions[2] = t2 ;
        transactions[3] = t3 ;
    }
}