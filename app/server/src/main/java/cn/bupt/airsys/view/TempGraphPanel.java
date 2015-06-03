package cn.bupt.airsys.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALSO on 2015/6/2.
 */
public class TempGraphPanel extends JPanel {
    private List<Integer> tempList = new ArrayList();
    private int maxPoint = 21;
    private int width;
    private int height;
    private int vMargin;
    private int hMargin;
    private int aimTemp = 25;
    private int upTempLimit = 35;
    private int downTempLimit = 15;

    public TempGraphPanel() {
        super();
    }

    public void appendPoint(double newTemp) {
        if (tempList.size() >= this.maxPoint) {
            tempList.remove(0);
        }
        if (newTemp > upTempLimit) newTemp = upTempLimit;
        if (newTemp < downTempLimit) newTemp = downTempLimit;

        tempList.add((int) ((newTemp - downTempLimit) * hMargin));
        this.repaint();
    }

    public void setAimTemp(double temp) {
        aimTemp = (int) temp;
    }

    public void resetGraph() {
        tempList.clear();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        width = this.getWidth();
        height = this.getHeight();
        vMargin = (this.width - 40) / (this.maxPoint - 1);
        hMargin = (this.height - 40) / (upTempLimit - downTempLimit); //15-35

        g.setColor(Color.red);
        int aimTempY = (aimTemp - downTempLimit) * hMargin;
        g.drawLine(20, height - 20 - aimTempY, width - 20, height - 20 - aimTempY);
        g.drawString(aimTemp + "", 5, height - 20 - aimTempY);

        g.setColor(Color.black);
        g.drawLine(20, height - 20, width - 20, height - 20);
        g.drawString("时刻", width - 40, height - 5);
        g.drawLine(20, height - 20, 20, 20);
        g.drawString("温度", 25, 30);
        g.drawString("15", 5, height - 20);
        g.drawString("35", 5, 35);
        if (tempList.size() <= 1) return;
        for (int i = 0; i < tempList.size() - 1; i++) {
            int fromTempY = height - 20 - tempList.get(i);
            int toTempY = height - 20 - tempList.get(i + 1);

            g.drawLine(20 + i * vMargin, fromTempY, 20 + (i + 1) * vMargin, toTempY);
        }

    }
}
