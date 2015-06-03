package cn.bupt.airsys.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ALSO on 2015/6/2.
 */
public class OverViewPanel extends BasePanel {
    private static final String BORDER = "系统管理";
    private JTabbedPane tabbedPane;
    private SysConfPanel sysConfPanel;
    private MonitorPanel monitorPanel;

    public OverViewPanel() {
        initView();
    }

    public static void main(String args[]) {
        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame f = new JFrame();
        f.add(new OverViewPanel());
        f.setSize(650, 800);
        f.setVisible(true);
    }

    private void initView() {
        JPanel ctrlPanel;
        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
        ctrlPanel = new JPanel();
        ctrlPanel.setLayout(new BoxLayout(ctrlPanel, BoxLayout.Y_AXIS));
        sysConfPanel = new SysConfPanel();
        monitorPanel = new MonitorPanel();
        ctrlPanel.add(sysConfPanel);
        ctrlPanel.add(monitorPanel);

        tabbedPane.add(BORDER, ctrlPanel);
        tabbedPane.add("报表系统", new ReportPanel());
        tabbedPane.setTabPlacement(JTabbedPane.LEFT);
    }

    public SysConfPanel getSysConfPanel() {
        return sysConfPanel;
    }
}
