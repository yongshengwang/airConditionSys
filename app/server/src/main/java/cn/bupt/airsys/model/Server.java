package cn.bupt.airsys.model;

import cn.bupt.airsys.Configure;
import cn.bupt.airsys.exception.ServerException;
import cn.bupt.airsys.service.ServerDaemon;
import cn.bupt.airsys.service.ServerListener;
import cn.bupt.airsys.utils.Utility;

import java.util.LinkedHashMap;

/**
 * Created by ALSO on 2015/5/26.
 */
public class Server {
    private LinkedHashMap<String, Slave> slaves;

    public Server() {
        this(SysProperty.COLD, 23.0f);
    }

    public Server(int workMode, float initTemp) {
        slaves = new LinkedHashMap<String, Slave>();
        SysProperty sys = SysProperty.getInstance();
        sys.setInitTemp(initTemp);
        sys.setWorkMode(workMode);

    }

    public void bootUp() {
        SysProperty.getInstance().bootUp();
        initNet();
    }

    public void initNet() {
        Thread serv = new Thread(new ServerDaemon(Configure.DEFAULT_PORT, new ServerListener() {
            @Override
            public void onReceive(String inetAddr, byte[] requestData) {
                int type = requestData[0];
                switch (type) {
                    case 2:
                        if (!slaves.containsValue(inetAddr)) {
                            Slave s = new Slave(String.valueOf(requestData[1]), inetAddr);
                        }
                        break;

                    case 3:
                        slaves.remove(inetAddr);
                        break;

                    case 4:
                        if (slaves.containsValue(inetAddr)) {
                            byte data[] = new byte[4];
                            for (int i = 0; i < 4; i++) {
                                data[i] = requestData[i + 1];
                            }
                            float temp = Utility.byte2float(data);
                            slaves.get(inetAddr).setCurrtentTemp(temp);
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
                            Slave s = slaves.get(inetAddr);
                            s.setPower(power);
                            if ((SysProperty.getInstance().getWorkMode() == SysProperty.COLD && temp <= 25.0f && temp >= 18.0f) ||
                                    (SysProperty.getInstance().getWorkMode() == SysProperty.HOT && temp >= 25.0f && temp <= 30.0f)) {
                                s.setTargetTemp(temp);
                            }
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
    }

    public String getSlave(String ip) {
        return slaves.get(ip).toString();
    }

    public void addSlave(Slave slave) {
        if (slave != null) {
            slaves.put(slave.getIpAddr(), slave);
        }
    }

    public void addSlave(String id, String ip) {
        if (id != null && ip != null) {
            Slave s = new Slave(id, ip);
            slaves.put(ip, s);
        }
    }
}
