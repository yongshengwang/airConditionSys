package cn.bupt.airsys.controller;

import cn.bupt.airsys.model.Server;
import cn.bupt.airsys.model.SysProperty;
import cn.bupt.airsys.model.User;
import cn.bupt.airsys.view.LoginDialog;
import cn.bupt.airsys.view.SysConfPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by ALSO on 2015/6/2.
 */
public class SysCtrlController {
    private SysConfPanel view;
    private SysProperty model;
    private User admin;
    private AbstractListener listener;

    public SysCtrlController(SysConfPanel view, SysProperty model, AbstractListener listener) {
        this.view = view;
        this.model = model;
        this.listener = listener;
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
                AuthController authController = new AuthController(loginDialog, new AbstractListener() {
                    @Override
                    public void onComplete(Object user) {
                        view.bootupInit();
                        listener.onComplete(null);
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
                    AuthController authController = new AuthController(loginDialog, new AbstractListener() {
                        @Override
                        public void onComplete(Object user) {
                            // TODO power off
                        }
                    });
                }
            }
        });

        view.communiFreqBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
//                System.out.println(((JComboBox)e.getSource()).getSelectedItem());
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println(e.getItem());
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
