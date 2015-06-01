package cn.bupt.airsys;

import cn.bupt.airsys.controller.AuthController;
import cn.bupt.airsys.controller.ServerController;
import cn.bupt.airsys.view.LoginView;
import cn.bupt.airsys.view.MainWindow;

import javax.swing.*;

/**
 * Created by ALSO on 2015/5/26.
 */
public class App {
    private ServerController serverController;
    private MainWindow mainWindow;

    public App() {
        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginView view = new LoginView();
                AuthController controller = new AuthController(view);
                view.setController(controller);
                view.setVisible(true);
            }
        });
    }

    public static void main(String args[]) {
        new App();
    }

}
