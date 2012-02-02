/**
 * Credit Worthiness System Version 1.0
 */
package UI.Listeners;

import UI.SettingsPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class listens out for the close and save buttons in the settings
 * pane and saves the application settings or cancels the process
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class SettingsListener implements ActionListener
{
    private SettingsPanel settingsPanel = null ;
    
    public SettingsListener(SettingsPanel aThis) 
    {
        settingsPanel = aThis ;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) 
    {
        
    }
    
}
