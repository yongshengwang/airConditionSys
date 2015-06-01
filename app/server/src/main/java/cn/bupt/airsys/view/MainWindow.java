package cn.bupt.airsys.view;

import cn.bupt.airsys.controller.ServerController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

/**
 * Created by ALSO on 2015/5/26.
 */
public class MainWindow extends JFrame {
    JComboBox monthSelect;
    JComboBox weekSelect;
    JComboBox daySelect;
    JCheckBox month;
    JCheckBox week;
    JCheckBox day;
    JTable statementTable;
    private ServerController serverController;
    private JList roomList;
    private JPanel mainView;
    private JPanel roomInfoView;
    private JTextArea logArea;
    private JButton coldBtn;
    private JButton heatBtn;

    public MainWindow() {
        super();
        initView();
    }

    public static void main(String arg[]) {
        new MainWindow();
    }

    private void initView() {
        this.setBounds(200, 100, 600, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(2, 1));

        this.getContentPane().add(initTopContent());
        this.getContentPane().add(initCtlContent());
    }

    private JPanel initTopContent() {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(initRoomList(), BorderLayout.WEST);
        p.add(initRoomInfo(), BorderLayout.CENTER);
        return p;
    }

    private JPanel initRoomList() {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("房间列表"));

        DefaultListModel dlm = new DefaultListModel();
        roomList = new JList(dlm);
        roomList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        roomList.setBackground(new Color(238, 238, 238));
        roomList.setSelectionBackground(Color.white);
        roomList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int index = roomList.locationToIndex(e.getPoint());
                String roomID = (String) roomList.getModel().getElementAt(index);
                roomID = roomID.split(" ")[0];
                // TODO update info.
            }
        });

        JScrollPane sp = new JScrollPane(roomList);
        sp.setBorder(null);
        p.add(sp, BorderLayout.CENTER);
        return p;
    }

    private JPanel initRoomInfo() {
        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createTitledBorder("房间信息"));
        p.setLayout(new GridLayout(1, 2));
        String[] infoTitle = {"当前温度", "目标温度", "工作模式", "工作风速", "消费金额", "工作状态"};
        roomInfoView = new JPanel();

        roomInfoView.setLayout(new GridLayout(6, 1));
        for (int i = 0; i < infoTitle.length; i++) {
            roomInfoView.add(new JLabel(infoTitle[i], SwingConstants.CENTER));
            roomInfoView.add(new JLabel("**", SwingConstants.LEFT));
        }
        p.add(roomInfoView);

        JPanel tmpPanel = new JPanel();
        tmpPanel.setLayout(new BorderLayout());

        p.add(tmpPanel);
        return p;
    }

    private JPanel initCtlContent() {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("控制面板"));

        p.add(initMainView(), BorderLayout.CENTER);
        p.add(initCtlView(), BorderLayout.SOUTH);
        return p;
    }

    private JPanel initCtlView() {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.LEFT));
        p.setBorder(BorderFactory.createLineBorder(Color.gray));
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton b = (JButton) e.getSource();
                if (b.getText().equals("设置面板")) {
                    ((CardLayout) mainView.getLayout()).show(mainView, "ctl");
                } else if (b.getText().equals("日志文件")) {
                    ((CardLayout) mainView.getLayout()).show(mainView, "log");
                } else if (b.getText().equals("报表")) {
                    ((CardLayout) mainView.getLayout()).show(mainView, "statement");
                }
            }
        };
        JButton btn = new JButton("设置面板");
        btn.addActionListener(al);
        p.add(btn);
        btn = new JButton("日志文件");
        btn.addActionListener(al);
        p.add(btn);
        btn = new JButton("报表");
        btn.addActionListener(al);
        p.add(btn);
        return p;
    }

    private JPanel initMainView() {
        mainView = new JPanel();
        mainView.setLayout(new CardLayout());

        JPanel logPanel = new JPanel(new BorderLayout());
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setBackground(new Color(238, 238, 238));
        logArea.setBorder(null);
        JScrollPane sp = new JScrollPane(logArea);
        logPanel.add(sp, BorderLayout.CENTER);
        mainView.add("log", logPanel);

        mainView.add("ctl", initCtlPanel());//设置面板

        mainView.add("statement", initStatementView());//设置面板

        ((CardLayout) mainView.getLayout()).show(mainView, "ctl");
        return mainView;
    }

    private JPanel initCtlPanel() {
        JPanel ctlPanel = new JPanel();
        ctlPanel.setLayout(new GridLayout(4, 1));

        String[] ctlTitle = {"默认温度", "更新频率", "工作模式"};
        String[] refresh = {"快", "一般", "慢"};
        String[] workModel = {"制冷", "解热"};

        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.LEFT));
        p.add(new JLabel(ctlTitle[0], SwingConstants.LEFT));
        final JTextField aimTempTextField = new JTextField("25", 2);
        aimTempTextField.setEditable(false);
        p.add(aimTempTextField);
        JSlider aim = new JSlider(18, 30);
        aim.setMajorTickSpacing(12);
        aim.setMinorTickSpacing(1);
        aim.setPreferredSize(new Dimension(150, 30));
        aim.setPaintLabels(true);
        aim.setValue(25);
        aim.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                aimTempTextField.setText(((JSlider) e.getSource()).getValue() + "");
            }
        });
        p.add(aim);
        ctlPanel.add(p);

        p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.LEFT));
        p.add(new JLabel(ctlTitle[1], SwingConstants.LEFT));
        ButtonGroup modelSelectBtn = new ButtonGroup();
        ActionListener actL = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String refreshModelSelect = e.getActionCommand();
            }
        };
        for (int i = 0; i < refresh.length; i++) {
            JRadioButton modelItem = new JRadioButton(refresh[i]);
            modelItem.addActionListener(actL);
            modelSelectBtn.add(modelItem);
            if (i == (refresh.length - 1)) modelItem.setSelected(true);
            p.add(modelItem);
        }
        ctlPanel.add(p);

        p = new JPanel();
        p.setLayout(new FlowLayout());
        p.setLayout(new FlowLayout(FlowLayout.LEFT));
        p.add(new JLabel(ctlTitle[2], SwingConstants.LEFT));
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("btn click");
                JButton b = (JButton) e.getSource();
                if (b.getText().equals("制冷")) {
                    coldBtn.setEnabled(false);
                    heatBtn.setEnabled(true);

                } else if (b.getText().equals("加热")) {
                    heatBtn.setEnabled(false);
                    coldBtn.setEnabled(true);
                }
            }
        };
        coldBtn = new JButton("制冷");
        coldBtn.addActionListener(al);
        p.add(coldBtn);
        heatBtn = new JButton("加热");
        heatBtn.addActionListener(al);
        heatBtn.setEnabled(false);
        p.add(heatBtn);
        ctlPanel.add(p);


        return ctlPanel;
    }

    private JPanel initStatementView() {
        String[] titleList = {"月", "周", "日"};
        JPanel sPanel = new JPanel();
        sPanel.setLayout(new BorderLayout());

        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.LEFT));
        month = new JCheckBox("月");
        week = new JCheckBox("周");
        day = new JCheckBox("日");
        ItemListener il = new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                JCheckBox cb = (JCheckBox) e.getSource();
                if (cb.isSelected()) {
                    //if(cb.getText().equals("月"))monthSelect.setEnabled(true);
                    if (cb.getText().equals("周")) {
                        weekSelect.setEnabled(true);
                        day.setEnabled(true);
                        if (day.isSelected()) daySelect.setEnabled(true);
                        else daySelect.setEnabled(false);
                    }
                    if (cb.getText().equals("日")) daySelect.setEnabled(true);
                } else {
                    //if(cb.getText().equals("月"))monthSelect.setEnabled(false);
                    if (cb.getText().equals("周")) {
                        weekSelect.setEnabled(false);
                        day.setEnabled(false);
                        daySelect.setEnabled(false);
                    }
                    if (cb.getText().equals("日")) {
                        daySelect.setEnabled(false);
                    }
                }
            }
        };
        month.addItemListener(il);
        month.setEnabled(false);
        week.addItemListener(il);
        day.addItemListener(il);
        day.setEnabled(false);
        String[] m = {"1", "2", "3"};
        monthSelect = new JComboBox(m);
        weekSelect = new JComboBox(m);
        weekSelect.setEnabled(false);
        daySelect = new JComboBox(m);
        daySelect.setEnabled(false);
        JButton btn = new JButton("查询");
        p.add(month);
        p.add(monthSelect);
        p.add(week);
        p.add(weekSelect);
        p.add(day);
        p.add(daySelect);
        p.add(btn);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showStatementTable();
                int month = Integer.parseInt(monthSelect.getSelectedItem().toString());
                int week = Integer.parseInt(weekSelect.getSelectedItem().toString());
                int day = Integer.parseInt(daySelect.getSelectedItem().toString());
                System.out.println(month + " " + week + " " + day);
            }
        });
        sPanel.add(p, BorderLayout.NORTH);

        p = new JPanel();
        p.setLayout(new BorderLayout());
        //报表
        DefaultTableModel dtm = new TableModel();
        statementTable = new JTable(dtm);
        statementTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        statementTable.getTableHeader().setReorderingAllowed(false);
        statementTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = statementTable.getSelectedRow();
                    String roomID = (String) statementTable.getValueAt(row, 0);
                    showStatementDetail(roomID);
                }
            }
        });
        p.add(new JScrollPane(statementTable), BorderLayout.CENTER);
        sPanel.add(p, BorderLayout.CENTER);

        return sPanel;
    }

    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
    }

    private void showStatementTable() {
        System.out.println("show statementTable");
        Vector<Vector<String>> data = new Vector();
        String[][] empty = {};
        String[] title = {"房间号", "总费用", "开关机次数", "请求总时间时间"};

        DefaultTableModel dtm = (DefaultTableModel) statementTable.getModel();
        dtm.setDataVector(empty, title);


    }

    private void showStatementDetail(String roomID) {
        System.out.println("detail statement:" + roomID);
    }

    public void appendLog(String log) {
        logArea.append(log + "\n");
        logArea.setCaretPosition(logArea.getText().length());
    }

    class TableModel extends DefaultTableModel {
        String[][] data = {};
        String[] title = {"房间号", "总费用", "开关机次数", "请求总时间时间"};

        public TableModel() {
            super.setDataVector(data, title);
        }

        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}
