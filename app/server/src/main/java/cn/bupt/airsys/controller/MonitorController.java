package cn.bupt.airsys.controller;

import cn.bupt.airsys.model.Slave;
import cn.bupt.airsys.model.table.ListAdapterListModel;
import cn.bupt.airsys.model.table.SlaveListTableModel;
import cn.bupt.airsys.view.MonitorPanel;

import java.util.List;

/**
 * Created by ALSO on 2015/6/7.
 */
public class MonitorController {
    private MonitorPanel view;
    private SlaveListTableModel slaveListTableModel;
    private ListAdapterListModel listModel;

    public MonitorController(MonitorPanel view, List<Slave> slaves) {
        this.view = view;
        listModel = new ListAdapterListModel(slaves);
        slaveListTableModel = new SlaveListTableModel(listModel);
        view.setDataModel(slaveListTableModel);
    }

    public ListAdapterListModel getListModel() {
        return  listModel;
    }

    public SlaveListTableModel getTableListModel() {
        return slaveListTableModel;
    }
}
