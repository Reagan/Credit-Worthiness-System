/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Listeners;

import AppActions.MenuBar;
import DbConnection.UsersDetails;
import UI.BottomCenterPanel;
import UI.LeftPanel;
import credit.worthiness.system.CreditWorthinessSystem;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
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
}
