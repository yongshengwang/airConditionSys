package cn.bupt.airsys.view;

import cn.bupt.airsys.model.table.SlaveListTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by ALSO on 2015/6/1.
 */
public class MonitorPanel extends BasePanel {
    private static final String BORDER = "丛控机状态";
    private final int INITIAL_ROWHEIGHT = 25;

    private JScrollPane scrollPane;

    private TempGraphPanel tempGraph;

    private JTable tableView;

    private JSplitPane splitPane;

    private SlaveListTableModel dataModel;

    public MonitorPanel() {
        initView();
    }

    public void setDataModel(SlaveListTableModel dataModel) {
        if (dataModel != null) {
            this.dataModel = dataModel;
        }
    }

    private void initView() {
        setBorder(BorderFactory.createTitledBorder(BORDER));
        createTable();
        tempGraph = new TempGraphPanel();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, tempGraph);
        splitPane.setContinuousLayout(true);
        splitPane.setDividerLocation(200);
        add(splitPane, BorderLayout.CENTER);
    }

    private void createTable() {
        //final String[] names = { "id", "当前温度", "目标温度", "功率", "ip地址" };
        tableView = new JTable(dataModel);
        scrollPane = new JScrollPane(tableView);
        try {
            Class c = Class.forName("javax.swing.table.TableRowSorter");
            Constructor constructor = c.getConstructor(TableModel.class);
            Object trs = constructor.newInstance(dataModel);

            Method m2 = JTable.class.getMethod("setRowSorter", Class.forName("javax.swing.RowSorter"));
            m2.invoke(tableView, trs);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        tableView.setRowHeight(INITIAL_ROWHEIGHT);
    }
}
