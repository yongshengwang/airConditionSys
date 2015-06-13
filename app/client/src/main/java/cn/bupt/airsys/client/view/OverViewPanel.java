package cn.bupt.airsys.client.view;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.MessageFormat;

/**
 * Created by ALSO on 2015/6/7.
 */
public class OverViewPanel extends JPanel {
    private static final String CURR_TEMP_FORMAT = "室温 {0} ℃ ";
    private static final String TAR_TEMP_FORMAT = "设定 {0} ℃ ";
    private static final String PAY_FORMAT = "当前消费 ￥ {0} ";
    private static final String CTRL_BORDER = "控制面板";

    private JPanel viewPanel;
    private JPanel powerPanel;
    private JPanel ctrlPanel;

    public JButton modeButton;
    public JButton powerButton;
    public JButton bootButton;
    public JButton upButton;
    public JButton downButton;
    private JLabel currTempLabel;
    private JLabel tarTempLabel;
    private JLabel payLabel;

    private int currPower = 0;

    public OverViewPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        initViewPanel();
        initCtrlPanel();
        disableBtn();
    }

    public void disableBtn() {
        powerButton.setEnabled(false);
        upButton.setEnabled(false);
        downButton.setEnabled(false);
    }

    public void initStatus() {
        powerButton.setEnabled(true);
        upButton.setEnabled(true);
        downButton.setEnabled(true);
    }

    private void initCtrlPanel() {
        ctrlPanel = new JPanel();
        ctrlPanel.setBorder(BorderFactory.createTitledBorder(CTRL_BORDER));
        ctrlPanel.setLayout(new BoxLayout(ctrlPanel, BoxLayout.Y_AXIS));
        add(ctrlPanel);
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.CENTER));
        bootButton = new JButton("开机");
        bootButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
        p.add(bootButton);
        powerButton = new JButton("风速");
        powerButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));

        p.add(powerButton);
        ctrlPanel.add(p);

        p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.CENTER));
        p.add(new JLabel("温度"));
        upButton = new JButton("△");
        downButton = new JButton("▽");
        p.add(upButton);
        p.add(downButton);
        ctrlPanel.add(p);
    }

    private void initViewPanel() {
        viewPanel = new JPanel();
        viewPanel.setPreferredSize(new Dimension(300, 200));
        add(viewPanel);
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));

        JPanel p = new JPanel(new GridLayout(0, 2));
        p.setBorder(BorderFactory.createTitledBorder(""));

        // power view
        powerPanel = new JPanel();
        FlowLayout f = new FlowLayout(FlowLayout.LEFT);
        f.setHgap(0);
        powerPanel.setLayout(f);
        addPowerImageBtn(currPower);
        p.add(powerPanel);

        // mode view
        modeButton = new JButton(createImageIcon("cold-32.png", "cold-mode"));
        modeButton.setDisabledIcon(createImageIcon("cold-32.png", "cold-mode"));
        modeButton.setContentAreaFilled(false);
        modeButton.setEnabled(false);
        JPanel modelPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        modelPanel.add(modeButton);
        p.add(modelPanel);
        viewPanel.add(p);

        p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.setBorder(BorderFactory.createTitledBorder(""));
        currTempLabel = new JLabel();
        MessageFormat tempLabelFormat = new MessageFormat(CURR_TEMP_FORMAT);
        Object[] a = new Object[]{ "--"};
        String temp = tempLabelFormat.format(a);
        currTempLabel.setText(temp);
        p.add(currTempLabel);
        p.add(new JLabel("    "));
        tarTempLabel = new JLabel();
        MessageFormat ttempLabelFormat = new MessageFormat(TAR_TEMP_FORMAT);
        String tarTemp = ttempLabelFormat.format(a);
        tarTempLabel.setText(tarTemp);
        p.add(tarTempLabel);
        viewPanel.add(p);

        p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.setBorder(BorderFactory.createTitledBorder(""));
        payLabel = new JLabel();
        MessageFormat payFormat = new MessageFormat(PAY_FORMAT);
        Object[]  obj = new Object[]{ "--" };
        String  pay = payFormat.format(obj);
        payLabel.setText(pay);
        p.add(payLabel);
        viewPanel.add(p);

    }

    protected ImageIcon createImageIcon(String filename, String description) {
        return new ImageIcon(getClass().getClassLoader().getResource(filename), description);
    }

    public int powerChange() {
        powerPanel.removeAll();
        currPower = (++currPower > 3) ? 1 : currPower;
        addPowerImageBtn(currPower);
        powerPanel.updateUI();
        return currPower;
    }

    public int powerChange(int power) {
        if(power == currPower) return power;
        powerPanel.removeAll();
        addPowerImageBtn(power);
        powerPanel.updateUI();
        return currPower;
    }


    public static void main(String args[]) throws Exception {
        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        UIManager.put("RootPane.setupButtonVisible", false);
        JFrame j = new JFrame();
        j.add(new OverViewPanel());
        j.setSize(400, 200);
        j.setVisible(true);
    }

    private void addPowerImageBtn(int num) {
        for(int i = 1; i <= num; i++) {
            JButton sp1 = new JButton(createImageIcon("speed3.png", "speed button"));
            sp1.setMargin(new Insets(0,0,0,0));
            sp1.setContentAreaFilled(false);
            sp1.setEnabled(false);
            powerPanel.add(sp1);
        }
    }

    public void setTargetTemp(int temp) {
        Object[] obj = {temp};
        MessageFormat ttempLabelFormat = new MessageFormat(TAR_TEMP_FORMAT);
        String tarTemp = ttempLabelFormat.format(obj);
        tarTempLabel.setText(tarTemp);
    }

    public void setCurrTemp(int temp) {
        Object[] obj = {temp};
        MessageFormat tempLabelFormat = new MessageFormat(CURR_TEMP_FORMAT);
        String t = tempLabelFormat.format(obj);
        currTempLabel.setText(t);
    }

    public void changeWorkMode() {
        modeButton.setDisabledIcon(createImageIcon("hot-32.png", "hot-mode"));
    }

    public void setPayment(float payment) {
        Object[] obj = {payment};
        MessageFormat payFormat = new MessageFormat(PAY_FORMAT);
        String  pay = payFormat.format(obj);
        payLabel.setText(pay);
    }
}
