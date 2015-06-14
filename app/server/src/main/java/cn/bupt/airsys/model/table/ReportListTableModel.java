package cn.bupt.airsys.model.table;

import cn.bupt.airsys.model.ReportBean;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by ALSO on 2015/6/14.
 */
public class ReportListTableModel extends AbstractTableModel {

    private final String[] columnNames = {
            "房间ID", "时间段", "功率", "花费"
    };
    private ArrayList<ReportBean> listModel;

    @Override
    public int getRowCount() {
        return listModel.size();
    }

    @Override
    public int getColumnCount() {
        return Columns.values().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object columnValue = null;
        ReportBean bean = listModel.get(rowIndex);
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];

        switch (column) {
            case ID:
                columnValue = bean.slave_id;
                break;

            case TIME:
                columnValue = bean.time;
                break;

            case POWER:
                columnValue = bean.power;
                break;

            case COST:
                columnValue = bean.cost;
                break;

            default:
                break;
        }
        return columnValue;
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

    public ArrayList<ReportBean> getListModel() {
        return listModel;
    }

    public void setListModel(ArrayList<ReportBean> listModel) {
        this.listModel = listModel;
        fireTableDataChanged();
    }

    private enum Columns {
        ID, TIME, POWER, COST
    }
}
