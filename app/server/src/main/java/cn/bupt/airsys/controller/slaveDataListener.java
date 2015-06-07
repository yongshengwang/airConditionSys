package cn.bupt.airsys.controller;

import cn.bupt.airsys.model.Slave;

/**
 * Created by ALSO on 2015/6/7.
 */
public interface SlaveDataListener {
    public void onSlaveChangeed(Slave s);
    public void onSlaveAdded(Slave s);
    public void onSlaveRemoved(Slave s);
}
