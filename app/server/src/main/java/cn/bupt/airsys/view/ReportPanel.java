package cn.bupt.airsys.view;

import cn.bupt.airsys.model.table.SlaveListTableModel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ALSO on 2015/6/2.
 */
public class ReportPanel extends BasePanel {
    private JComboBox typeComnoBox;

    private JButton priterButton;

    private JScrollPane scrollPane;

    private JTable tableView;

    private SlaveListTableModel dataModel;

    public ReportPanel() {
        initView();
    }

    private void initView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(500, 75));
        p.setLayout(new FlowLayout(FlowLayout.CENTER));
        p.setBorder(BorderFactory.createTitledBorder("报表控制"));
        typeComnoBox = new JComboBox();
        typeComnoBox.addItem("日报表");
        typeComnoBox.addItem("周报表");
        typeComnoBox.addItem("月报表");
        typeComnoBox.setAlignmentX(JComboBox.LEFT_ALIGNMENT);
        p.add(typeComnoBox);
        priterButton = new JButton("打印");
        p.add(priterButton);
        add(p);
        p = new JPanel();
        p.setBorder(BorderFactory.createTitledBorder(""));
        tableView = new JTable(dataModel);
        scrollPane = new JScrollPane(tableView);
        p.add(scrollPane);
        add(p);
    }
}
