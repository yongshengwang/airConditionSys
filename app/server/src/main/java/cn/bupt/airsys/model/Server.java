package cn.bupt.airsys.model;

import cn.bupt.airsys.Configure;
import cn.bupt.airsys.controller.SlaveDataListener;
import cn.bupt.airsys.exception.ServerException;
import cn.bupt.airsys.service.DataSender;
import cn.bupt.airsys.service.ServerDaemon;
import cn.bupt.airsys.service.ServerListener;
import cn.bupt.airsys.utils.DatabaseManager;
import cn.bupt.airsys.utils.Utility;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Created by ALSO on 2015/5/26.
 */
public class Server {
    private LinkedHashMap<String, Slave> slaves;
    // store slaves' ip
    private LinkedList<TimeSet> servSlaveList;
    private LinkedList<TimeSet> pendingSlaveList;
    private SlaveDataListener listener;
    private Timer heartbeatTimer;
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

    public void bootUp() throws SocketException {
        SysProperty.getInstance().bootUp();
        servSlaveList = new LinkedList<TimeSet>();
        pendingSlaveList = new LinkedList<TimeSet>();
        initNet();
        final DataSender sender = new DataSender();
        heartbeatTimer = new Timer(Configure.HEARTBEAT_TICK, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SysProperty sys = SysProperty.getInstance();
                for (Slave s : slaves.values()) {
                    try {
                        sender.sendWorkInfo(InetAddress.getByName(s.getIpAddr()), Configure.SLAVE_PORT, s.getTargetTemp(), s.getPower());
                        for (TimeSet set : servSlaveList) {
                            if (s.getIpAddr().equals(set.ip)) {
                                long tmp = System.currentTimeMillis() - set.start_time.getTime();
                                long dur = Configure.HEARTBEAT_TICK < tmp ? Configure.HEARTBEAT_TICK : tmp;
                                s.setPay(s.getPay() + Configure.PRICE[s.getPower()] * dur);
                            }
                        }
                        sender.sendStatus(InetAddress.getByName(s.getIpAddr()), Configure.SLAVE_PORT, sys.getWorkMode(), s.getPay());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        heartbeatTimer.start();
    }

    public void initNet() {
        final Thread serv = new Thread(new ServerDaemon(Configure.DEFAULT_PORT, new ServerListener() {
            @Override
            public void onReceive(String inetAddr, byte[] requestData) {
                int type = requestData[0];
                System.out.println("data received: " + type);
                //System.out.println("bytes: " + requestData[0] + " " + requestData[1] + " " + requestData[2] + " " + requestData[3] + " " + requestData[4] + " " + requestData[5]);
                switch (type) {
                    case 2: // login
                        if (!slaves.containsValue(inetAddr)) {
                            System.out.println("new slave: " + String.valueOf(requestData[1]));
                            Slave s = new Slave(String.valueOf(requestData[1]), inetAddr);
                            // IMPORTANT!!
                            TimeSet set = new TimeSet();
                            set.ip = inetAddr;
                            set.start_time = new Timestamp(System.currentTimeMillis());

                            if (servSlaveList.isEmpty() || servSlaveList.size() <= Configure.MAX_SERV_NUM) {
                                s.setCurrStatus(Slave.WORKING);
                                servSlaveList.push(set);
                            } else {
                                s.setCurrStatus(Slave.PENDING);
                                s.setPower(Slave.PENDING_POWER);
                                pendingSlaveList.push(set);
                            }
                            try {
                                String sql = "insert into access_log (`slave_id`, `type`) VALUES ('" + s.getId() + "', 'login');";
                                System.out.println(sql);
                                DatabaseManager.getInstance().insert(sql);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            addSlave(s);
                        }
                        break;

                    case 3: // disconnect
                        Slave s = slaves.get(inetAddr);
                        // remove from work list first
                        if (s.getCurrStatus() == Slave.WORKING) {
                            for (TimeSet set : servSlaveList) {
                                if (set.ip.equals(s.getIpAddr())) {
                                    servSlaveList.remove(set);
                                    break;
                                }
                            }
                        } else {
                            for (TimeSet set : pendingSlaveList) {
                                if (set.ip.equals(s.getIpAddr())) {
                                    pendingSlaveList.remove(set);
                                    break;
                                }
                            }
                        }

                        String sql = "insert into access_log (`slave_id`, `type`) VALUES ('" + s.getId() + "', 'logout');";
                        System.out.println(sql);
                        DatabaseManager db = DatabaseManager.getInstance();
                        try {
                            db.insert(sql);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        listener.onSlaveRemoved(s);
                        slaves.remove(inetAddr);
                        break;

                    case 4: // slave status
                        if (slaves.containsKey(inetAddr)) {
                            byte data[] = new byte[4];
                            for (int i = 0; i < 4; i++) {
                                data[i] = requestData[i + 1];
                            }
                            float temp = Utility.byte2float(data);
                            Slave ss = slaves.get(inetAddr);
                            try {
                                sql = "insert into transaction_log (`slave_id`, `type`, `power`, `temperature`) VALUES ('" + ss.getId() + "', 'status', '" + ss.getPower() + "', '" + temp + "');";
                                DatabaseManager.getInstance().insert(sql);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            System.out.println("slave: " + inetAddr + " curr temp: " + temp);

                            // if slave's temp reaches the target temperatuer
                            if (ss.getCurrStatus() == Slave.WORKING && ((temp <= ss.getTargetTemp() && SysProperty.getInstance().getWorkMode() == SysProperty.COLD) ||
                                    (temp >= ss.getTargetTemp() && SysProperty.getInstance().getWorkMode() == SysProperty.HOT))) {
                                ss.setCurrStatus(Slave.PENDING);
                                ss.setPower(Slave.PENDING_POWER);
                                System.out.println("power: " + ss.getPower());
                                listener.onSlaveChangeed(ss);
                                // move this slave from serv list to pending list

                                for (TimeSet set : servSlaveList) {
                                    if (set.ip.equals(inetAddr)) {
                                        pendingSlaveList.push(set);
                                        servSlaveList.remove(set);
                                        break;
                                    }
                                }

                                // TODO DB
                            } else {
                                ss.setCurrtentTemp(temp);
                                listener.onSlaveChangeed(ss);
                            }
                            // if servlist is still not full,try to move one slave from pending list to serv list
                            if ((servSlaveList.isEmpty() || servSlaveList.size() < Configure.MAX_SERV_NUM) && !pendingSlaveList.isEmpty()) {
                                TimeSet set = pendingSlaveList.getFirst();
                                ss = slaves.get(set.ip);
                                if (ss != null && Math.abs(ss.getCurrtentTemp() - ss.getTargetTemp()) > 2.0f) {
                                    servSlaveList.push(set);
                                    pendingSlaveList.remove(set);
                                    ss.setCurrStatus(Slave.WORKING);
                                    System.out.println("active slave: " + ss.getIpAddr());
                                }
                            }
                        }
                        break;

                    case 5: // slave request
                        if (slaves.containsKey(inetAddr)) {
                            byte data[] = new byte[4];
                            for (int i = 0; i < 4; i++) {
                                data[i] = requestData[i + 1];
                            }
                            float temp = Utility.byte2float(data);
                            int power = requestData[5];
                            System.out.println("Slave: " + inetAddr + " req temp: " + temp + " req power: " + power);
                            Slave s2 = slaves.get(inetAddr);
                            try {
                                sql = "insert into transaction_log (`slave_id`, `type`, `power`, `temperature`) VALUES ('" + s2.getId() + "', 'request', '" + power + "', '" + temp + "');";
                                DatabaseManager.getInstance().insert(sql);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            // if the slave is working we need to cal the pay
                            if (s2.getCurrStatus() == Slave.WORKING) {
                                for (TimeSet set : servSlaveList) {
                                    if (set.ip.equals(s2.getIpAddr())) {
                                        Timestamp now = new Timestamp(System.currentTimeMillis());
                                        int prePower = s2.getPower();
                                        float pay = (now.getTime() - set.start_time.getTime()) * Configure.PRICE[prePower];
                                        s2.setPay(s2.getPay() + pay);
                                        try {
                                            sql = "insert into cost_report (`slave_id`, `cost`, `power`, start_time, `end_time`) values ('" + s2.getId() + "', '" + pay +
                                                    "', '" + prePower + "', '" + set.start_time + "', '" + now + "');";
                                            System.out.println(sql);
                                            DatabaseManager.getInstance().insert(sql);
                                            set.start_time = now;
                                            s2.setPower(power);
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    }
                                }
                            }
                            if ((SysProperty.getInstance().getWorkMode() == SysProperty.COLD && temp <= 25.0f && temp >= 18.0f) ||
                                    (SysProperty.getInstance().getWorkMode() == SysProperty.HOT && temp >= 25.0f && temp <= 30.0f)) {
                                s2.setTargetTemp(temp);
                            }
                            // TODO DB
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
        serv.start();
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

    private class TimeSet {
        public String ip;
        public Timestamp start_time;
    }
}
