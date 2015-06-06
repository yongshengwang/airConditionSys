package cn.bupt.airsys.view;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;

/**
 * Created by ALSO on 2015/6/1.
 */
public class SysConfPanel extends BasePanel {
    public static final int COLD = 1;
    public static final int HOT = 2;
    public static final String HIGH_FREQ = "快-更新频率";
    public static final String MID_FREQ = "一般-更新频率";
    public static final String LOW_FREQ = "慢-更新频率";
    private static final String BORDER = "控制面板";
    private static final String[] WORKMODE = {"制冷", "制热"};
    private static final String[] BOOT = {"开机", "关机"};
    private static final String TEMPERATURE_FORMAT = "当前设置温度: {0} ℃ ";
    /**
     * power on button
     */
    public JButton onButton;

    /**
     * power off button
     */
    public JButton offButton;

    /**
     * button to change sys to hot mode
     */
    public JButton hotButton;

    /**
     * button to change sys to cold mode
     */
    public JButton coldButton;

    /**
     * update frequence comboBox
     */
    public JComboBox communiFreqBox;

    /**
     * slider of init temperature
     */
    public JSlider initHotTempSlider;

    /**
     * slider of init temperature
     */
    public JSlider initColdTempSlider;

    /**
     * display current init temp
     */
    private JLabel currentSetupTemp;

    public SysConfPanel() {
        super();
        iniView();
    }

    private void iniView() {
        setLayout(new GridLayout(4, 1));
        setBorder(BorderFactory.createTitledBorder(BORDER));
        /* add power on/ off buttons */
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.LEFT));
        onButton = new JButton(BOOT[0]);
        offButton = new JButton(BOOT[1]);
        onButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
        communiFreqBox = new JComboBox();
        communiFreqBox.addItem(MID_FREQ);
        communiFreqBox.addItem(HIGH_FREQ);
        communiFreqBox.addItem(LOW_FREQ);
        p.add(onButton);
        p.add(offButton);
        p.add(communiFreqBox);
        add(p);

        /* add work mode button and init temperature sliders */
        p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.LEFT));
        coldButton = new JButton(WORKMODE[0]);
        coldButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));
        initColdTempSlider = new JSlider(18, 25, 23);
        initColdTempSlider.setMajorTickSpacing(1);
        initColdTempSlider.setPaintTicks(true);
        initColdTempSlider.setPaintLabels(true);
        initColdTempSlider.setSnapToTicks(true);
        initColdTempSlider.getLabelTable().put(new Integer(25), new JLabel(String.valueOf(new Integer(25)), JLabel.CENTER));
        initColdTempSlider.setLabelTable(initColdTempSlider.getLabelTable());
        p.add(coldButton);
        p.add(initColdTempSlider);
        add(p);

        p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.LEFT));
        hotButton = new JButton(WORKMODE[1]);
        hotButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
        hotButton.setForeground(Color.white);
        initHotTempSlider = new JSlider(25, 30, 26);
        initHotTempSlider.setMajorTickSpacing(1);
        initHotTempSlider.setPaintTicks(true);
        initHotTempSlider.setPaintLabels(true);
        initHotTempSlider.setEnabled(false);
        initHotTempSlider.setSnapToTicks(true);
        initHotTempSlider.getLabelTable().put(new Integer(30), new JLabel(String.valueOf(new Integer(30)), JLabel.CENTER));
        initHotTempSlider.setLabelTable(initHotTempSlider.getLabelTable());
        p.add(hotButton);
        p.add(initHotTempSlider);
        add(p);

        p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.LEFT));
        currentSetupTemp = new JLabel();
        add(currentSetupTemp);
        MessageFormat tempLabelFormat = new MessageFormat(TEMPERATURE_FORMAT);
        Object[] a = new Object[]{initColdTempSlider.getValue()};
        String temp = tempLabelFormat.format(a);
        currentSetupTemp.setText(temp);
    }

    public int updateTempLabel() {
        MessageFormat tempLabelFormat = new MessageFormat(TEMPERATURE_FORMAT);
        int temp;
        if (initColdTempSlider.isEnabled() == false) {
            temp = initHotTempSlider.getValue();
        } else {
            temp = initColdTempSlider.getValue();
        }
        Object[] a = new Object[]{temp};
        String _temp = tempLabelFormat.format(a);
        currentSetupTemp.setText(_temp);
        return temp;
    }

    public void changeTempSlider(int mode) {
        if (mode == COLD && initColdTempSlider.isEnabled() == false) {
            initColdTempSlider.setEnabled(true);
            initHotTempSlider.setEnabled(false);
        } else if (mode == HOT && initHotTempSlider.isEnabled() == false) {
            initHotTempSlider.setEnabled(true);
            initColdTempSlider.setEnabled(false);
        }
    }

}
