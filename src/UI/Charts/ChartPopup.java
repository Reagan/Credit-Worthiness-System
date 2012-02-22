/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Charts;

import UI.DepthButton;
import java.awt.Color;
import javax.swing.*;

/**
 * This class displays the popup that appers when a user selects the 
 * left mouse key while the mouse is hovered on the chart component
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */

// !+ This class was initially displayed using JpopupMenu.
// However, due to Bug http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4628933, 
// [ solution : http://www.coderanch.com/t/533322/GUI/java/Prevent-JPopupMenu-hiding-when-clicked ]
// where Swing is unable to display 2 lightweight items (JPopupMenu & JComboBox popupMenu)
// will have to use JDialog rather than JPopupMenu

public class ChartPopup extends JDialog
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
        
        // set the preferred dimensions
        setSize(180,60);
        
        // set undecorated
        setUndecorated(true);
        
        // add the border
        mPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        // lay out the components
        layoutComponents() ;
    }
    
    // lays out the components
    private void layoutComponents()
    {
        GroupLayout layout = new GroupLayout(mPanel) ;
        mPanel.setLayout(layout);
        
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
    }
}
