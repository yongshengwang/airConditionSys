package cn.bupt.airsys.controller;

import cn.bupt.airsys.model.User;
import cn.bupt.airsys.view.LoginDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by ALSO on 2015/5/21.
 */
public class AuthController {
    private LoginDialog view;
    private AuthListener listner;

    public AuthController(LoginDialog loginDialog, AuthListener listner) {
        this.view = loginDialog;
        this.listner = listner;
        setUpEvent();
    }

    public boolean auth(String user, String password) {
        if(User.checkPasswd(user, password, false)) {
            return true;
        }
        return false;
    }

    private void setUpEvent() {
        view.getLoginButton().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean ret = auth(view.getUserText().getText().trim(), view.getPasswdText().getText().trim());
                if (ret) {
                    User user = new User(view.getUserText().getText().trim());
                    listner.onComplete(user);
                    view.dispose();
                } else {
                    view.shake();
                }
            }
        });
        view.getPasswdText().addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean ret = auth(view.getUserText().getText().trim(), view.getPasswdText().getText().trim());
                if (ret) {
                    view.dispose();
                } else {
                    view.shake();
                }
            }
        });
    }
}
