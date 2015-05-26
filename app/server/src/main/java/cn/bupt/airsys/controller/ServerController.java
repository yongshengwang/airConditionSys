package cn.bupt.airsys.controller;

import cn.bupt.airsys.model.Server;
import cn.bupt.airsys.model.User;
import cn.bupt.airsys.view.MainWindow;

/**
 * Created by ALSO on 2015/5/26.
 */
public class ServerController {
    private User admin;
    private MainWindow mainWindow;
    private Server server;

    public ServerController(MainWindow mainView, User admin) {
        this.mainWindow = mainView;
        this.admin = admin;
    }

    // getters and setters
    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User user) {
        this.admin = user;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
