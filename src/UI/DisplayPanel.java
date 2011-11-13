/**
 * Credit Worthiness System Version 1.0
 */
package UI;

import AppActions.MenuBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
public class DisplayPanel extends JPanel
{
    private JPanel mainPanel ;
    private LeftPanel leftPanel  ;
    private CenterPanel centerPanel ;
    private JSeparator separator ;
    private BottomCenterPanel bottomCenterPanel  ;
    private BottomRightPanel bottomRightPanel ;
    private StatusBar statusBar ;
    private GroupLayout grouplayout ;
    
    public DisplayPanel()
    {
        // initialise the variables
        mainPanel = new JPanel();
        leftPanel  = new LeftPanel();
        centerPanel = new CenterPanel();
        separator = new JSeparator(SwingConstants.HORIZONTAL);
        bottomCenterPanel  = new BottomCenterPanel();
        bottomRightPanel = new BottomRightPanel();
        statusBar = new StatusBar();
    
        // lay out the components
        setLayout(new BorderLayout());
        mainPanel.setOpaque(false);
        
        // add the various panels to the mainPanel
        grouplayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(grouplayout);
        
        // horizontal layout to mainPanel
        grouplayout.setHorizontalGroup(grouplayout.createSequentialGroup()
                .addComponent(leftPanel)
                .addGroup(grouplayout.createParallelGroup()
                            .addComponent(centerPanel)
                            .addGroup(grouplayout.createSequentialGroup()
                                        .addComponent(bottomCenterPanel)
                                        .addComponent(bottomRightPanel))));
        
        // vertical layout to mainPanel
        grouplayout.setVerticalGroup(grouplayout.createSequentialGroup()
                     .addGroup(grouplayout.createParallelGroup()
                                .addComponent(leftPanel)
                                .addComponent(centerPanel))
                    .addGroup(grouplayout.createParallelGroup()
                                .addComponent(bottomCenterPanel)
                                .addComponent(bottomRightPanel)));
        
        // add the main components to the content pane
        add(mainPanel, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH );   
        setOpaque(false);
    }
    
    public DisplayPanel(LayoutManager layout)
    {
        super(layout);          
    }
}
