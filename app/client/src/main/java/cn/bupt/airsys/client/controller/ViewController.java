package cn.bupt.airsys.client.controller;

import cn.bupt.airsys.client.Configure;
import cn.bupt.airsys.client.model.Slave;
import cn.bupt.airsys.client.model.DataChangedListener;
import cn.bupt.airsys.client.service.DataSender;
import cn.bupt.airsys.client.view.OverViewPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by ALSO on 2015/6/7.
 */
public class ViewController {

    private DataSender sender;
    private OverViewPanel view;
    private Slave model;
    private InetAddress remoteAddr;
    private int port = Configure.REMOTE_PORT;
    private boolean isOn = false;
    private Timer dataTimer; // for send data to master daemon
    private int tick = Configure.DEFAULT_TICK;

    public ViewController(OverViewPanel view, Slave slave) throws UnknownHostException {
        this.view = view;
        this.model = slave;
        try {
            sender = new DataSender();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        remoteAddr =  InetAddress.getByName(Configure.REMOTE_IP);
        dataTimer = new Timer(tick, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sender.sendStatus(remoteAddr, port, model.getCurrentTemp());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
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
                if(!isOn) {
                    try {
                        sender.connetc(remoteAddr, port, Integer.valueOf(model.getId()));
                        sender.request(remoteAddr, port, model.getTargetTemp(), model.getPower());
                        dataTimer.start();
                        view.initStatus();
                        view.setTargetTemp((int) model.getTargetTemp());
                        view.setPayment(0.0f);
                        view.bootButton.setText("关机");
                        model.addDataChangedListener(new DataChangedListener() {
                            @Override
                            public void temperatureChanged(float temp) {
                                view.setCurrTemp((int)temp);
                            }

                            @Override
                            public void paymentChanged(float pay) {
                                view.setPayment(pay);
                            }

                            @Override
                            public void onException(Exception e) {
                                // TODO
                            }
                        });

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    isOn = true;
                } else {
                    try {
                        sender.disconnetc(remoteAddr, Configure.REMOTE_PORT, Integer.valueOf(model.getId()));
                        view.bootButton.setText("开机");
                        view.disableBtn();
                        isOn = false;
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
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
                    try {
                        sender.request(remoteAddr, port, temp, model.getPower());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
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
                    try {
                        sender.request(remoteAddr, port, temp, model.getPower());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }
}
