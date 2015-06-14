package cn.bupt.airsys.controller;

import cn.bupt.airsys.model.ReportBean;
import cn.bupt.airsys.model.table.ReportListTableModel;
import cn.bupt.airsys.view.ReportPanel;

import java.util.ArrayList;

/**
 * Created by ALSO on 2015/6/15.
 */
public class ReportController {
    private ReportPanel view;
    private ReportListTableModel listTableModel;
    private ReportDao reportDao;

    public ReportController(ReportPanel view) {
        this.view = view;
        this.listTableModel = new ReportListTableModel();
        reportDao = new ReportDao();
        ArrayList<ReportBean> listModel = reportDao.getDaily();
        listTableModel.setListModel(listModel);
        view.setDataModel(listTableModel);
    }
}
