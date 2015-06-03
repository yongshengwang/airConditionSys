package cn.bupt.airsys.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ALSO on 2015/5/21.
 */
public class LoginDialog extends JDialog {
    Point naturalLocation;
    Timer shakeTimer;
    private JTextField userText;
    private JTextField passwdText;
    private JButton loginButton;

    public LoginDialog() {
        super();
        initView();
    }

    private void initView() {
        this.setTitle("身份验证");
        this.setSize(300, 225);
        final JLabel userLabel = new JLabel("用户名");
        userText = new JTextField(20);
        final JLabel passwdLabel = new JLabel("密码");
        passwdText = new JPasswordField(20);
        loginButton = new JButton("登录");
        JPanel topPanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(loginButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        topPanel.add(userLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        topPanel.add(userText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        topPanel.add(passwdLabel, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        topPanel.add(passwdText, gbc);

        this.add(topPanel);

        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void shake() {
        final long startTime;

        naturalLocation = getLocation();
        startTime = System.currentTimeMillis();
        shakeTimer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double TWO_PI = Math.PI * 2.0;
                double SHAKE_CYCLE = 50;

                long elapsed = System.currentTimeMillis() - startTime;
                double waveOffset = (elapsed % SHAKE_CYCLE) / SHAKE_CYCLE;
                double angle = waveOffset * TWO_PI;

                int SHAKE_DISTANCE = 10;

                int shakenX = (int) ((Math.sin(angle) * SHAKE_DISTANCE) + naturalLocation.x);
                setLocation(shakenX, naturalLocation.y);
                repaint();

                int SHAKE_DURATION = 200;
                if (elapsed >= SHAKE_DURATION) {
                    shakeTimer.stop();
                    setLocation(naturalLocation);
                    repaint();
                }
            }
        });
        shakeTimer.start();
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JTextField getPasswdText() {
        return passwdText;
    }

    public JTextField getUserText() {
        return userText;
    }
}

