package cn.bupt.airsys.view;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;

/**
 * Created by ALSO on 2015/6/1.
 */
public class SysConfPanel extends BasePanel {
    private static final String BORDER = "控制面板";
    private static final String[] WORKMODE = {"制冷", "制热"};
    private static final String[] BOOT = {"开机", "关机"};
    private static final String TEMPERATURE_FORMAT = "当前设置温度: {0} ℃ ";

    /**
     * power on button
     */
    private JButton onButton;

    /**
     * power off button
     */
    private JButton offButton;

    /**
     * button to change sys to hot mode
     */
    private JButton hotButton;

    /**
     * button to change sys to cold mode
     */
    private JButton coldButton;

    /**
     * slider of init temperature
     */
    private JSlider initHotTempSlider;

    /**
     * slider of init temperature
     */
    private JSlider initColdTempSlider;

    /**
     * display current init temp
     */
    private JLabel currentSetupTemp;

    public SysConfPanel() {
        iniView();
        // TODO
    }

    public static void main(String args[]) {
        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame f = new JFrame();
        f.add(new SysConfPanel());
        f.setSize(500, 400);
        f.setVisible(true);
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
        p.add(onButton);
        p.add(offButton);
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
        p.add(createToolTip());
        add(p);

        MessageFormat tempLabelFormat = new MessageFormat(TEMPERATURE_FORMAT);
        Object[] a = new Object[]{initColdTempSlider.getValue()};
        String temp = tempLabelFormat.format(a);
        currentSetupTemp.setText(temp);
    }
}
