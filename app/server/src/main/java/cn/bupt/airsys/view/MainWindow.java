package cn.bupt.airsys.view;

import cn.bupt.airsys.controller.*;
import cn.bupt.airsys.model.Server;
import cn.bupt.airsys.model.Slave;
import cn.bupt.airsys.model.SysProperty;

import javax.swing.*;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Created by ALSO on 2015/5/26.
 */
public class MainWindow extends JFrame {
    private OverViewPanel panel;
    private SysCtrlController sysCtrlController;
    private MonitorController monitorController;
    private ReportController reportController;
    private Server server;

    public MainWindow() throws Exception {
        panel = new OverViewPanel();
        add(panel);
        sysCtrlController = new SysCtrlController(panel.getSysConfPanel(), SysProperty.getInstance(), new AbstractListener() {
            @Override
            public void onComplete(Object param) {
                monitorController = new MonitorController(panel.getMonitorPanel(), new ArrayList<Slave>());
                server = new Server(new SlaveDataListener() {
                    @Override
                    public void onSlaveChangeed(Slave s) {
                        monitorController.getTableListModel().slaveChanged(s);
                        monitorController.setTargetTempPoint(s.getTargetTemp());
                        monitorController.addTempPoint(s.getCurrtentTemp());
                    }

                    @Override
                    public void onSlaveAdded(Slave s) {
                        monitorController.getTableListModel().slaveAdded(s);
                    }

                    @Override
                    public void onSlaveRemoved(Slave s) {
                        monitorController.getTableListModel().slaveRemoved(s);
                    }
                });
                try {
                    server.bootUp();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }
        });
        reportController = new ReportController(panel.getReportPanel());
        setSize(650, 850);
        setVisible(true);
    }

}

