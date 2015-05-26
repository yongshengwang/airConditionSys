package cn.bupt.airsys.view;

import cn.bupt.airsys.controller.AuthController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by ALSO on 2015/5/21.
 */
public class LoginView extends JDialog {

    private AuthController controller;

    public LoginView() {
        super();
        initView();
    }

    private void initView() {
        this.setTitle("Login");
        this.setSize(300, 150);
        final JLabel userLabel = new JLabel("username");
        final JTextField userText = new JTextField(20);
        final JLabel passwdLabel = new JLabel("password");
        final JTextField passwdText = new JPasswordField(20);
        passwdText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    login(userText.getText().trim(), passwdText.getText().trim());
                }
            }
        });
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login(userText.getText().trim(), passwdText.getText().trim());
            }
        });

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

    private void login(String user, String password) {
        controller.auth(user, password);
    }

    public void setController(AuthController controller) {
        this.controller = controller;
    }

}
