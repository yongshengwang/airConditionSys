package cn.bupt.airsys.client.controller;

import cn.bupt.airsys.client.Configure;
import cn.bupt.airsys.client.model.Slave;
import cn.bupt.airsys.client.service.DataSender;
import cn.bupt.airsys.client.view.OverViewPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by ALSO on 2015/6/7.
 */
public class ViewController {
    private DataSender sender;
    private OverViewPanel view;
    private Slave model;
    private InetAddress remoteAddr;

    public ViewController(OverViewPanel view, Slave slave) throws UnknownHostException {
        this.view = view;
        this.model = slave;
        try {
            sender = new DataSender();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        remoteAddr =  InetAddress.getByName(Configure.REMOTE_IP);
        setupEvent();
    }

    public void changeWorkModel(int mode) {
        if(mode != model.getWorkMode() &&
            (mode == Slave.HOT_MODE || mode == Slave.COLD_MODE)) {
            model.setWorkMode(mode);
            view.changeWorkMode();
        }

    }

    private void setupEvent() {
        view.bootButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sender.connetc(remoteAddr, Configure.REMOTE_PORT, Integer.valueOf(model.getId()));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                view.initStatus();
                view.setTargetTemp((int) model.getCurrtentTemp());
                view.setCurrTemp(28);
                view.setPayment(0.0);
                view.bootButton.setText("关机");
            }
        });

        view.powerButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.powerChange();
            }
        });

        view.upButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int temp = (int) model.getTargetTemp();
                System.out.println("target: " + temp);
                if(model.setTargetTemp(++temp)) {
                    view.setTargetTemp(temp);
                }
            }
        });

        view.downButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int temp = (int) model.getTargetTemp();
                System.out.println("target: " + temp);
                if(model.setTargetTemp(--temp)) {
                    view.setTargetTemp(temp);
                }
            }
        });
    }
}
