/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Charts;

import java.util.EventListener;

/**
 * This interface defines the custom event listener triggered when a node
 * is selected
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public interface NodeSelectedListener extends EventListener
{
    public void nodeSelected(NodeSelected evt);
}
