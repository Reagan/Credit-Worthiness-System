/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Charts;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;

/**
 *
 * @author undesa
 */
public class ChartPopupListener extends MouseAdapter {
   
    private JPopupMenu popupMenu ;
    
    public ChartPopupListener(JPopupMenu pMenu)
    {
        popupMenu = pMenu ;
    }
    
    public void mousePressed(MouseEvent e) 
    {
        maybeShowPopup(e);
    }

    public void mouseReleased(MouseEvent e) 
    {
        maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e) 
    {
        if (e.isPopupTrigger()) {
            popupMenu.show(e.getComponent(),
                       e.getX(), e.getY());
        }
    }
    
}
