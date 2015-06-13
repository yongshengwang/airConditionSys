package cn.bupt.airsys.model.table;

import cn.bupt.airsys.model.Slave;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

/**
 * Created by ALSO on 2015/6/1.
 */
public class SlaveListTableModel extends AbstractTableModel {
    private final String[] columnNames = {
            "ID",
            "温度",
            "目标",
            "功率",
            "IP"
    };

    private ListAdapterListModel listModel;
    private ListModelChangeListener listModelChangeListener = new ListModelChangeListener();

    public SlaveListTableModel(ListAdapterListModel model) {
        this.listModel = model;
    }

    public final void setListModel(ListAdapterListModel listModel) {
        if (this.listModel != null) {
            this.listModel.removeListDataListener(listModelChangeListener);
        }
        this.listModel = listModel;
        if (listModel != null) {
            listModel.addListDataListener(listModelChangeListener);
        }
        fireTableDataChanged();
    }

    public Color getRowColor(int rowIndex) {
        Slave slave = (Slave) listModel.getElementAt(rowIndex);
        if (slave.getCurrStatus() == Slave.WORKING) {
            return Color.green;
        } else {
            return Color.gray;
        }
    }

    @Override
    public int getRowCount() {
        return listModel.getSize();
    }

    @Override
    public int getColumnCount() {
        return Columns.values().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object columnValue = null;
        Slave slave = (Slave) listModel.getElementAt(rowIndex);
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];

        switch (column) {
            case ID:
                columnValue = slave.getId();
                break;

            case CURRTEMP:
                columnValue = slave.getCurrtentTemp();
                break;

            case TARGETTEMP:
                columnValue = slave.getTargetTemp();
                break;

            case POWER:
                columnValue = slave.getPower();
                break;

            case IPADDR:
                columnValue = slave.getIpAddr();
                break;

            default:
                break;
        }
        return columnValue;
    }

    public void slaveAdded(Slave s) {
        listModel.addSlave(s);
        fireTableDataChanged();
    }

    public void slaveChanged(Slave s) {
        listModel.changeSlave(s);
        fireTableDataChanged();
    }

    public void slaveRemoved(Slave s) {
        listModel.removeSlave(s);
        fireTableDataChanged();
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

    private enum Columns {
        ID, CURRTEMP, TARGETTEMP, POWER, IPADDR
    }

    private class ListModelChangeListener implements ListDataListener {

        public void intervalAdded(ListDataEvent e) {
            fireTableDataChanged();
        }

        public void intervalRemoved(ListDataEvent e) {
            fireTableDataChanged();
        }

        public void contentsChanged(ListDataEvent e) {
            fireTableDataChanged();
        }

    }
}
