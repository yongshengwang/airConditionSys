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
        model = new Slave("1", Inet4Address.getLocalHost().getHostAddress());
        model.setTargetTemp(23);
        model.setWorkMode(Slave.COLD_MODE);
        ViewController controller = new ViewController(panel, model);

        final Thread serv = new Thread(new UdpServer(Configure.DEFAULT_RECV_PORT, new ServerListener() {
            @Override
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

                    case 7:
                        byte targetByte[] = new byte[4];
                        // next 4 bytes is the target temp...然并卵, But necessary to check our request is pkg missing or not
                        /*for (int i = 0; i < 4; i++) {
                            targetByte[i] = data[i + 1];
                        }*/
                        model.setPower((int)data[5]);
                        break;

                    default:
                        break;
                }
            }
            @Override
            public void onException(Exception e) {
                // TODO
            }
        }));

        serv.start();


        setSize(400, 200);
        setVisible(true);
    }
}
