/**
 * Credit Worthiness System Version 1.0
 */
package UI.Listeners;

import UI.NewTransactionPanel;
import UI.NewUserPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class NewTransactionListener implements ActionListener
{
    private NewTransactionPanel newTransPanel ;
    
    public NewTransactionListener(NewTransactionPanel nPanel)
    {
        newTransPanel = nPanel ;
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        if ("Cancel".equals(ae.getActionCommand()))
        {
            // close the new User dialog
            JDialog g = newTransPanel.parent ;
            g.setVisible(false);
            g.dispose();
            
        }
        else if("Save".equals(ae.getActionCommand()))
        {
            // @TODO : implement this code to actually insert data into a database
            JOptionPane.showMessageDialog(newTransPanel, "Transaction details "
                    + "entered successfully", "Success", JOptionPane.INFORMATION_MESSAGE); 
            
            // close the dialog
            JDialog g = newTransPanel.parent ;
            g.setVisible(false);
            g.dispose();
        }
    }
    
}
