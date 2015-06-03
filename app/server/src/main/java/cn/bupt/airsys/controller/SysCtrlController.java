package cn.bupt.airsys.controller;

import cn.bupt.airsys.model.SysProperty;
import cn.bupt.airsys.model.User;
import cn.bupt.airsys.view.LoginDialog;
import cn.bupt.airsys.view.SysConfPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;

/**
 * Created by ALSO on 2015/6/2.
 */
public class SysCtrlController {
    private SysConfPanel view;
    private SysProperty model;
    private User admin;

    public SysCtrlController(SysConfPanel view, SysProperty model) {
        this.view = view;
        this.model = model;
        setUpViewEvents();
    }

    private void setUpViewEvents() {
        view.onButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginDialog loginDialog = new LoginDialog();
                loginDialog.setVisible(true);
                loginDialog.setLocationRelativeTo(view.getParent());
                loginDialog.setAlwaysOnTop(true);
                AuthController authController = new AuthController(loginDialog, new AuthListener() {
                    @Override
                    public void onComplete(User user) {
                        // TODO System boot up
                    }
                });
            }
        });

        view.offButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(view, "确定要关机吗?");
                if (result == JOptionPane.YES_OPTION) {
                    LoginDialog loginDialog = new LoginDialog();
                    loginDialog.setVisible(true);
                    loginDialog.setLocationRelativeTo(view.getParent());
                    loginDialog.setAlwaysOnTop(true);
                    AuthController authController = new AuthController(loginDialog, new AuthListener() {
                        @Override
                        public void onComplete(User user) {
                            // TODO power off
                        }
                    });
                }
            }
        });

        view.coldButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.getInstance().getWorkMode() == SysProperty.HOT) {
                    model.setWorkMode(SysProperty.COLD);
                    view.changeTempSlider(SysConfPanel.COLD);
                    model.setInitTemp((float) view.updateTempLabel());
                }
            }
        });

        view.hotButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.getInstance().getWorkMode() == SysProperty.COLD) {
                    model.setWorkMode(SysProperty.HOT);
                    view.changeTempSlider(SysConfPanel.HOT);
                    model.setInitTemp((float) view.updateTempLabel());
                }
            }
        });

        view.initColdTempSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                model.getInstance().setInitTemp(view.updateTempLabel());
            }
        });

        view.initHotTempSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                model.getInstance().setInitTemp(view.updateTempLabel());
            }
        });
    }
}