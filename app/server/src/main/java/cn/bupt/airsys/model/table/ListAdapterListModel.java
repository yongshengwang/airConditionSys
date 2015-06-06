package cn.bupt.airsys.model.table;

import cn.bupt.airsys.model.Slave;

import javax.swing.*;
import java.util.List;

/**
 * Created by ALSO on 2015/6/6.
 */
public class ListAdapterListModel extends AbstractListModel {

    private List<Slave> slaveList;

    public ListAdapterListModel(List<Slave> list) {
        this.slaveList = list;
    }

    public void setSlaveList(List<Slave> list) {
        this.slaveList = list;
    }

    @Override
    public int getSize() {
        return slaveList.size();
    }

    @Override
    public Object getElementAt(int index) {
        return slaveList.get(index);
    }

}
