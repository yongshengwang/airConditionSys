package cn.bupt.airsys.client.view;

import cn.bupt.airsys.client.Configure;
import cn.bupt.airsys.client.controller.ViewController;
import cn.bupt.airsys.client.model.Slave;
import cn.bupt.airsys.client.service.ServerListener;
import cn.bupt.airsys.client.service.UdpServer;
import cn.bupt.airsys.client.utils.Utility;

import javax.swing.*;
import javax.swing.text.Utilities;
import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * Created by ALSO on 2015/6/7.
 */
public class MainWindow extends JFrame{
    private OverViewPanel panel;
    protected Slave model;

    public MainWindow() throws UnknownHostException {
        panel = new OverViewPanel();
        add(panel);
        model = new Slave(Configure.ROOM_ID, Inet4Address.getLocalHost().getHostAddress());
        model.setTargetTemp(Configure.DEFAULT_TARGET_TEMP);
        model.setWorkMode(Slave.COLD_MODE);
        ViewController controller = new ViewController(panel, model);

        setSize(400, 200);
        setVisible(true);
    }
}
