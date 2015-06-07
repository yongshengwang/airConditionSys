package cn.bupt.airsys.client.view;

import cn.bupt.airsys.client.controller.ViewController;
import cn.bupt.airsys.client.model.Slave;

import javax.swing.*;
import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * Created by ALSO on 2015/6/7.
 */
public class MainWindow extends JFrame{
    private OverViewPanel panel;
    private ViewController controller;
    protected Slave model;

    public MainWindow() throws UnknownHostException {
        panel = new OverViewPanel();
        add(panel);
        model = new Slave("1", Inet4Address.getLocalHost().getHostAddress());
        model.setCurrtentTemp(32);
        model.setTargetTemp(20);
        model.setWorkMode(Slave.COLD_MODE);
        model.setPower(Slave.MID_POWER);
        controller = new ViewController(panel, model);
        setSize(400, 200);
        setVisible(true);
    }
}
