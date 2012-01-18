/**
 * Credit Worthiness System Version 1.0
 */
package UI.Charts;

import java.util.EventObject;

/**
 * This class defines the NodeSelected event
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class NodeSelected extends EventObject 
{
    private int componentNumber ;

    public NodeSelected(Object source, int compNo) 
    {
            super(source);
            setComponentNumber(compNo) ;
    }

    public void setComponentNumber(int compNo)
    {
            componentNumber = compNo ;
    }

    public int getComponentNumber()
    {
            return componentNumber ;
    }    
}
