package cn.bupt.airsys.model.table;

import cn.bupt.airsys.model.Slave;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;

/**
 * Created by ALSO on 2015/6/1.
 */
public class SlaveListTableModel extends AbstractTableModel {
    private ListModel<Slave> listModel = new DefaultListModel<Slave>();
    ;
    private ListModelChangeListener listModelChangeListener = new ListModelChangeListener();

    public final void setListModel(ListModel<Slave> listModel) {
        if (this.listModel != null) {
            this.listModel.removeListDataListener(listModelChangeListener);
        }
        this.listModel = listModel;
        if (listModel != null) {
            listModel.addListDataListener(listModelChangeListener);
        }
        fireTableDataChanged();
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
