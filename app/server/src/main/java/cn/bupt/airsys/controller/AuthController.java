package cn.bupt.airsys.controller;

import cn.bupt.airsys.model.User;
import cn.bupt.airsys.view.LoginView;

/**
 * Created by ALSO on 2015/5/21.
 */
public class AuthController {
    private User user;
    private LoginView loginView;

    public AuthController(LoginView loginView) {
        this.loginView = loginView;
    }

    public boolean auth(String user, String password) {
        if(User.checkPasswd(user, password, false)) {
            this.user = User.getInstence(user);
        }
        return false;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
