package cn.bupt.airsys.view;

import cn.bupt.airsys.controller.SysCtrlController;
import cn.bupt.airsys.model.SysProperty;

import javax.swing.*;

/**
 * Created by ALSO on 2015/5/26.
 */
public class MainWindow extends JFrame {
    private OverViewPanel panel;
    private SysCtrlController sysCtrlController;

    public MainWindow() throws Exception {
        panel = new OverViewPanel();
        add(panel);
        sysCtrlController = new SysCtrlController(panel.getSysConfPanel(), SysProperty.getInstance());
        setSize(650, 800);
        setVisible(true);
    }

}

