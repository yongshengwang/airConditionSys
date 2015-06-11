package cn.bupt.airsys.model;

import cn.bupt.airsys.Configure;
import cn.bupt.airsys.controller.SlaveDataListener;
import cn.bupt.airsys.exception.ServerException;
import cn.bupt.airsys.service.ServerDaemon;
import cn.bupt.airsys.service.ServerListener;
import cn.bupt.airsys.utils.Utility;

import javax.swing.*;
import java.util.LinkedHashMap;

/**
 * Created by ALSO on 2015/5/26.
 */
public class Server {
    private LinkedHashMap<String, Slave> slaves;
    private SlaveDataListener listener;

    public Server(SlaveDataListener listener) {
        this(SysProperty.COLD, 23.0f, listener);
    }

    public Server(int workMode, float initTemp, SlaveDataListener listener) {
        slaves = new LinkedHashMap<String, Slave>();
        this.listener = listener;
        SysProperty sys = SysProperty.getInstance();
        sys.setInitTemp(initTemp);
        sys.setWorkMode(workMode);

    }

    public void bootUp() {
        SysProperty.getInstance().bootUp();
        initNet();
    }

    public void initNet() {
        final Thread serv = new Thread(new ServerDaemon(Configure.DEFAULT_PORT, new ServerListener() {
            @Override
            public void onReceive(String inetAddr, byte[] requestData) {
                int type = requestData[0];
                System.out.println("data received: " + type);
                switch (type) {
                    case 2:
                        if (!slaves.containsValue(inetAddr)) {
                            System.out.println("new slave: " + String.valueOf(requestData[1]));
                            Slave s = new Slave(String.valueOf(requestData[1]), inetAddr);
                            addSlave(s);
                        }
                        break;

                    case 3:
                        Slave s = slaves.get(inetAddr);
                        listener.onSlaveRemoved(s);
                        slaves.remove(inetAddr);
                        break;

                    case 4:
                        if (slaves.containsValue(inetAddr)) {
                            byte data[] = new byte[4];
                            for (int i = 0; i < 4; i++) {
                                data[i] = requestData[i + 1];
                            }
                            float temp = Utility.byte2float(data);
                            Slave ss = slaves.get(inetAddr);
                            ss.setCurrtentTemp(temp);
                            listener.onSlaveChangeed(ss);
                        }
                        break;

                    case 5:
                        if (slaves.containsValue(inetAddr)) {
                            byte data[] = new byte[4];
                            for (int i = 0; i < 4; i++) {
                                data[i] = requestData[i + 1];
                            }
                            float temp = Utility.byte2float(data);
                            int power = requestData[5];
                            System.out.println("Slave: " + inetAddr + " req temp: " + temp + " req power: " + power);
                            Slave s2 = slaves.get(inetAddr);
                            s2.setPower(power);
                            if ((SysProperty.getInstance().getWorkMode() == SysProperty.COLD && temp <= 25.0f && temp >= 18.0f) ||
                                    (SysProperty.getInstance().getWorkMode() == SysProperty.HOT && temp >= 25.0f && temp <= 30.0f)) {
                                s2.setTargetTemp(temp);
                            }
                            listener.onSlaveChangeed(s2);
                        }
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onException(ServerException e) {

            }
        }));
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                serv.start();
            }
        });
    }

    public String getSlave(String ip) {
        return slaves.get(ip).toString();
    }

    public void addSlave(Slave slave) {
        if (slave != null) {
            slaves.put(slave.getIpAddr(), slave);
        }
        listener.onSlaveAdded(slave);
    }

    public void addSlave(String id, String ip) {
        if (id != null && ip != null) {
            Slave s = new Slave(id, ip);
            slaves.put(ip, s);
            listener.onSlaveAdded(s);
        }
    }
}
