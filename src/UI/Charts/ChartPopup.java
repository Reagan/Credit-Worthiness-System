/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Charts;

import UI.DepthButton;
import javax.swing.*;

/**
 * This class displays the popup that appers when a user selects the 
 * left mouse key on the chart component
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class ChartPopup extends JPopupMenu
{
    private DepthButton previousmonthbutton ;
    private DepthButton nextMonthButton ;
    private JCheckBox showPopupCheckBox ;
    private JComboBox monthsCombo ;
    private JComboBox yearsCombo ;
    private JPanel mPanel ;
    
    public ChartPopup()
    {
        String [] months = {"January", "February", "March", "April", "May","June","July",
            "August","September","October","November","December" } ;
        String [] years = { "2010", "2011" , "2012" } ;
        
        previousmonthbutton = new DepthButton("P") ;
        nextMonthButton = new DepthButton("N") ;
        showPopupCheckBox =  new JCheckBox("Show Popups") ;
        monthsCombo = new JComboBox(months) ;
        yearsCombo = new JComboBox(years) ;
        
        mPanel = new JPanel() ;        
        
        // lay out the components
        layoutComponents() ;
    }
    
    // lays out the components
    private void layoutComponents()
    {
        GroupLayout layout = new GroupLayout(mPanel) ;
        mPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        // add the items horizontally
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addComponent(previousmonthbutton)
                    .addComponent(nextMonthButton)
                    .addComponent(showPopupCheckBox)
                    )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(monthsCombo)
                    .addComponent(yearsCombo)
                    )
         );
        
        // add the items vertically
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(previousmonthbutton)
                        .addComponent(nextMonthButton)
                        .addComponent(showPopupCheckBox)
                    )
                .addGroup(layout.createParallelGroup()
                        .addComponent(monthsCombo)
                        .addComponent(yearsCombo)
                    )
             );
        
        add(mPanel) ;
        setOpaque(true);
    }
}
