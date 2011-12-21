/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Models;

import DbConnection.UsersDetails;
import java.util.Vector;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author reagan
 */
public class PrintMonthlyReportModel implements TableModel
{
    private int currentUserID ;
    private UsersDetails userTransactions ;    
    private Vector returnedTransactions ;             
    private Object[][] transactions ;
    
    public PrintMonthlyReportModel(int userID)
    {
        // @TODO: This method determines the transactions for a 
        // specific user
        currentUserID = userID ;  
        userTransactions = new UsersDetails() ;
        
        // get the returned transaction values
        returnedTransactions = userTransactions.getUserTransactions(userID);
        
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
            case 4: 
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
        return 5;
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
            case 4: return "Amount Paid";
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
            case 4:
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
        transactions = new Object[5][4] ;
        
        Object [] t0 = {"1st Jan, 2011", "Milk ", 1,  200,100 } ;
        Object [] t1 = {"1st Jan, 2011", "Milk ", 2, 200,100 } ;
        Object [] t2 = {"1st Jan, 2011", "Milk ", 3, 200,100 } ;
        Object [] t3 = {"1st Jan, 2011", "Milk ", 1, 200,100 } ;
        Object [] t4 = {"1st Jan, 2011", "Milk ", 4,  200,100 } ;
        
        transactions[0] = t0 ;
        transactions[1] = t1 ;
        transactions[2] = t2 ;
        transactions[3] = t3 ;
        transactions[4] = t4 ;
    }    
}
