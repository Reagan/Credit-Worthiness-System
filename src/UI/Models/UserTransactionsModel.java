/**
 * Credit Worthiness System Version 1.0
 */
package UI.Models;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class UserTransactionsModel implements TableModel 
{
    private int currentUserID ;
    
    public UserTransactionsModel(int userID)
    {
        // @TODO: This method determines the transactions for a 
        // specific user
        currentUserID = userID ;
        
    }
    
    private final Object[][] Transactions = 
    {
        // structure of the transactions
        // #Date, #Item, #ItemWorth, #AmountPaid
        {"1st Jan, 2011", "Milk", 200, 100},
        {"1st Jan, 2011", "Milk", 200, 100},
        {"1st Jan, 2011", "Milk", 200, 100},
        {"1st Jan, 2011", "Milk", 200, 100},
        {"1st Jan, 2011", "Milk", 200, 100},
    };

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
        return Transactions.length;
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
            case 0: return "Date";
            case 1: return "Item";
            case 2: return "Item Worth";
            case 3: return "Amount Paid";
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
                return Transactions[row][col];            
        }

        throw new AssertionError("invalid column");
    }
}