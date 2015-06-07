package cn.bupt.airsys.model.table;

import cn.bupt.airsys.model.Slave;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;
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

    public void addSlave(Slave slave) {
        slaveList.add(slave);
    }

    public void changeSlave(Slave slave) {
        Iterator<Slave> it = slaveList.iterator();
        while(it.hasNext()) {
            if(it.next().getIpAddr().equals(slave.getIpAddr())) {
                it.remove();
                slaveList.add(slave);
            }
        }
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
