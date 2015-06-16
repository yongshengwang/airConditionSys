package cn.bupt.airsys.controller;

import cn.bupt.airsys.model.Slave;
import cn.bupt.airsys.model.table.ListAdapterListModel;
import cn.bupt.airsys.model.table.SlaveListTableModel;
import cn.bupt.airsys.view.MonitorPanel;
import cn.bupt.airsys.view.TempGraphPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
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
        setupEvent();
    }

    public ListAdapterListModel getListModel() {
        return  listModel;
    }

    public void addTempPoint(float temp) {
        view.getTempGraph().appendPoint(temp);
    }

    public void setTargetTempPoint(float target) {
        view.getTempGraph().setAimTemp(target);
    }

    public void resetGraph(float targetTemp, ArrayList<Float> tempList) {
        TempGraphPanel p = view.getTempGraph();
        p.resetGraph();
        p.setAimTemp(targetTemp);
        for (Float temp : tempList) {
            p.appendPoint(temp);
        }
    }

    public SlaveListTableModel getTableListModel() {
        return slaveListTableModel;
    }

    public void setupEvent() {
        view.getTableView().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = view.getTableView().getSelectedRow();
                Slave s = listModel.getSlave(selectedRow);
                resetGraph(s.getTargetTemp(), s.tempList);
            }
        });
    }


}
