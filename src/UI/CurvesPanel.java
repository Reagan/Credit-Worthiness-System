/**
 * Credit Worthiness System Version 1.0
 */
package UI;

/**
 *
 * @author Reagan Mbitiru <reaganmbitiru@gmail.com>
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;

import javax.swing.JPanel;

public class CurvesPanel extends JPanel {
    protected RenderingHints hints;
    protected int counter = 0;
    protected Color start = new Color(255, 255, 255, 200);
    protected Color end = new Color(255, 255, 255, 0);
    
    public CurvesPanel() {
        this(new BorderLayout());
    }
    
    public CurvesPanel(LayoutManager manager) {
        super(manager);
        hints = createRenderingHints();
    }
    
    protected RenderingHints createRenderingHints() {
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        hints.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        return hints;
    }
    
    public void animate() {
        counter++;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        RenderingHints oldHints = g2.getRenderingHints();
        g2.setRenderingHints(hints);
        
        float width = getWidth();
        float height = getHeight();
        
        g2.translate(0, -30);

        drawCurve(g2,
                20.0f, -10.0f, 20.0f, -10.0f,
                width / 2.0f - 40.0f, 10.0f,
                0.0f, -5.0f,
                width / 2.0f + 40, 1.0f,
                0.0f, 5.0f,
                50.0f, 5.0f, false);

        g2.translate(0, 30);
        g2.translate(0, height - 60);

        drawCurve(g2,
                30.0f, -15.0f, 50.0f, 15.0f,
                width / 2.0f - 40.0f, 1.0f,
                15.0f, -25.0f,
                width / 2.0f, 1.0f / 2.0f,
                0.0f, 25.0f,
                15.0f, 9.0f, false);

        g2.translate(0, -height + 60);
        
        drawCurve(g2,
                height - 35.0f, -5.0f, height - 50.0f, 10.0f,
                width / 2.0f - 40.0f, 1.0f,
                height - 35.0f, -25.0f,
                width / 2.0f, 1.0f / 2.0f,
                height - 20.0f, 25.0f,
                25.0f, 7.0f, true);
        
        g2.setRenderingHints(oldHints);
    }

    protected void drawCurve(Graphics2D g2,
            float y1, float y1_offset,
            float y2, float y2_offset,
            float cx1, float cx1_offset,
            float cy1, float cy1_offset,
            float cx2, float cx2_offset,
            float cy2, float cy2_offset,
            float thickness,
            float speed,
            boolean invert) {
        float width = getWidth();
       
        float offset = (float) Math.sin(counter / (speed * Math.PI));
        
        float start_x = 0.0f;
        float start_y = offset * y1_offset + y1;
        float end_x = width;
        float end_y = offset * y2_offset + y2;
        
        float ctrl1_x = offset * cx1_offset + cx1;
        float ctrl1_y = offset * cy1_offset + cy1;
        float ctrl2_x = offset * cx2_offset + cx2;
        float ctrl2_y = offset * cy2_offset + cy2;
       
        GeneralPath thickCurve = new GeneralPath();
        thickCurve.moveTo(start_x, start_y);
        thickCurve.curveTo(ctrl1_x, ctrl1_y,
                ctrl2_x, ctrl2_y,
                end_x, end_y);
        thickCurve.lineTo(end_x, end_y + thickness);
        thickCurve.curveTo(ctrl2_x, ctrl2_y + thickness,
                ctrl1_x, ctrl1_y + thickness,
                start_x, start_y + thickness);
        thickCurve.lineTo(start_x, start_y);
       
        Rectangle bounds = thickCurve.getBounds();
        if (!bounds.intersects(g2.getClipBounds())) {
            return;
        }
      
        GradientPaint painter = new GradientPaint(0, bounds.y,
                invert ? end : start,
                0, bounds.y + bounds.height,
                invert ? start : end);

        Paint oldPainter = g2.getPaint();
        g2.setPaint(painter);
        g2.fill(thickCurve);
       
        g2.setPaint(oldPainter);
    }
}
