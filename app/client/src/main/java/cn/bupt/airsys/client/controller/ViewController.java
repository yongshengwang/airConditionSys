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
                        sender.request(remoteAddr, port, model.getTargetTemp(), Slave.SML_POWER);
                        dataTimer.start();
                        view.initStatus();
                        view.setCurrTemp((int) model.getCurrentTemp());
                        view.setTargetTemp((int) model.getTargetTemp());
                        view.setPayment(0.0f);
                        view.bootButton.setText("关机");
                        model.addTempChangeDaemon();
                        model.addDataChangedListener(new DataChangedListener() {
                            @Override
                            public void temperatureChanged(float temp) {
                                view.setCurrTemp((int)temp);
                                // if temp get the target, slave need to send stop request
                                if(model.getStatus() == Slave.WORKING && (model.getWorkMode() == Slave.COLD_MODE && temp <= model.getTargetTemp())
                                        || (model.getWorkMode() == Slave.HOT_MODE && temp >= model.getTargetTemp())) {
                                    try {
                                        model.setPower(Slave.PENDING_POWER);
                                        model.setStatus(Slave.PENDING);
                                        sender.request(remoteAddr, port, model.getTargetTemp(), Slave.PENDING_POWER);
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                                // need to re-request for work mode
                                // Attention: slave's working mode does not mean it will get >0 power
                                if(model.getStatus() == Slave.PENDING && Math.abs(temp-model.getTargetTemp()) > 1.0f ) {
                                    try {
                                        model.setPower(Slave.SML_POWER);
                                        model.setStatus(Slave.WORKING);
                                        sender.request(remoteAddr, port, model.getTargetTemp(), Slave.SML_POWER);
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void paymentChanged(float pay) {
                                view.setPayment(pay);
                            }

                            @Override
                            public void powerChanged(int power) {
                                view.powerChange(power);
                            }

                            @Override
                            public void workModeChanged(int workMode) {
                                view.changeWorkMode();
                            }

                            @Override
                            public void onException(Exception e) {
                                e.getStackTrace();
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
                try {
                    sender.request(remoteAddr, port, model.getTargetTemp(), view.powerChange());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
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
