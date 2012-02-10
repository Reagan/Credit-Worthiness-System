/**
 * Credit Worthiness System Version 1.0
 */
package UI.Listeners;

import AppActions.SettingsUserAction.SettingsDialog;
import UI.SettingsPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * This class listens out for the close and save buttons in the settings
 * pane and saves the application settings or cancels the process
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class SettingsListener implements ActionListener
{
    private SettingsDialog settingsDialog = null ;
    private SettingsPanel settingsPanel = null ;
    
    public SettingsListener(SettingsDialog aThis, SettingsPanel aPanel) 
    {
        settingsDialog = aThis ;
        settingsPanel = aPanel ;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        JButton b = (JButton) ae.getSource();
        
        if (b.getActionCommand() == "cancel")
        {
            // close the frame
            settingsDialog.dispose();
        }
        else if(b.getActionCommand() == "save")
        {
            // save the properties details
            // settingsPanel.appName
        }
    }    
}
