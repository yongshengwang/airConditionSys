package cn.bupt.airsys.client.controller;

import cn.bupt.airsys.client.Configure;
import cn.bupt.airsys.client.model.Slave;
import cn.bupt.airsys.client.model.DataChangedListener;
import cn.bupt.airsys.client.service.DataSender;
import cn.bupt.airsys.client.service.ServerListener;
import cn.bupt.airsys.client.service.UdpServer;
import cn.bupt.airsys.client.utils.Utility;
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
    private Thread serv;
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
            public void actionPerformed(ActionEvent e) {
                try {
                    sender.sendStatus(remoteAddr, port, model.getCurrentTemp());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        serv = new Thread(new UdpServer(Configure.DEFAULT_RECV_PORT, new ServerListener() {
            public void onReceive(String inetAddr, byte[] data) {
                int type = (int)data[0];
                System.out.println("bytes: " + data[0] + " " + data[1] + " " + data[2] + " " + data[3] + " " + data[4] + " " + data[5]);
                switch(type) {
                    case 1: // ack
                        break;

                    case 6: // 6|mode|pay
                        model.setWorkMode((int)data[1]);
                        byte payByte[] = new byte[4];
                        for (int i = 0; i < 4; i++) {
                            payByte[i] = data[i + 2];
                        }
                        model.setCurrentPay(Utility.byte2float(payByte));
                        break;

                    case 7: //7|targetTemp|power
                        byte targetByte[] = new byte[4];
                        for (int i = 0; i < 4; i++) {
                            targetByte[i] = data[i + 1];
                        }
                        float targetTemp = Utility.byte2float(targetByte);
                        int power = (int) data[5];
                        if(power > Slave.PENDING_POWER) {
                            model.setStatus(Slave.WORKING);
                        } else {
                            model.setStatus(Slave.PENDING);
                        }
                        model.setTargetTemp(targetTemp);
                        model.setPower(power);
                        break;

                    default:
                        break;
                }
            }
            public void onException(Exception e) {
                // TODO
            }
        }));
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
                        serv.start();
                        model.addDataChangedListener(new DataChangedListener() {
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
                                        int sendPower = model.getPower() == 0 ? Slave.SML_POWER : model.getPower();
                                        sender.request(remoteAddr, port, model.getTargetTemp(), Slave.SML_POWER);
                                    } catch (IOException e1) { e1.printStackTrace(); }
                                }
                            }

                            public void paymentChanged(float pay) {
                                view.setPayment(pay);
                            }

                            public void powerChanged(int power) {
                                view.powerChange(power);
                            }

                            public void workModeChanged(int workMode) {
                                view.changeWorkMode();
                            }

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
                        model.setPower(Slave.PENDING_POWER);
                        view.bootButton.setText("开机");
                        serv.interrupt();
                        view.disableBtn();
                        isOn = false;
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        view.powerButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int power = model.getPower();
                    if(power == Slave.HIGHT_POWER) {
                        power = Slave.SML_POWER;
                    } else {
                        power++;
                    }
                    sender.request(remoteAddr, port, model.getTargetTemp(), power);
                    view.powerChange(power);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        view.upButton.addActionListener(new AbstractAction() {
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
