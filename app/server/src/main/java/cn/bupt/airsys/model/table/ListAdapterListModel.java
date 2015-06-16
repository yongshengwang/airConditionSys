package cn.bupt.airsys.model.table;

import cn.bupt.airsys.model.Slave;

import javax.swing.*;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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

    public void addSlave(Slave slave) {
        slaveList.add(slave);
    }

    public void changeSlave(Slave slave) {
        for (ListIterator<Slave> it = slaveList.listIterator(); it.hasNext(); ) {
            Slave s = it.next();
            if (s.getIpAddr().equals(slave.getIpAddr())) {
                it.set(slave);
            }
        }
    }

    public Slave getSlave(int index) {
        return slaveList.get(index);
    }

    public void removeSlave(int index) {
        slaveList.remove(index);
    }

    public void removeSlave(Slave slave) {
        Iterator<Slave> it = slaveList.iterator();
        while(it.hasNext()) {
            if(it.next().getIpAddr().equals(slave.getIpAddr())) {
                it.remove();
            }
        }
    }
}
