package cn.bupt.airsys.controller;

import cn.bupt.airsys.model.ReportBean;
import cn.bupt.airsys.utils.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by ALSO on 2015/6/15.
 * // TODO TODO TODO NOT Compeleted!!!!!!!!
 */
public class ReportDao {

    public ArrayList<ReportBean> getDaily() {
        ArrayList<ReportBean> ret = new ArrayList<ReportBean>();
        /*String sql = "select `slave_id`, `cost`, `power` from transaction_log "
                + "where start_time > ?";*/
        try {
            String sql = "select `slave_id`, `cost`, `power`, `start_time`, `end_time` from cost_report;";
            ResultSet set = DatabaseManager.getInstance().query(sql);
            while (set.next()) {
                ReportBean bean = new ReportBean();
                bean.slave_id = set.getString("slave_id");
                bean.power = set.getInt("power");
                bean.cost = set.getFloat("cost");
                bean.time = "" + set.getTimestamp("start_time") + "-" + set.getTimestamp("end_time");
                ret.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(ret.size());
        return ret;
    }

    public ArrayList<ReportBean> getWeekly(Time week) {
        return getDaily();
    }

    public ArrayList<ReportBean> getMonthly(Time month) {
        return getDaily();
    }
}
