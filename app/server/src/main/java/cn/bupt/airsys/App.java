package cn.bupt.airsys;

import cn.bupt.airsys.controller.ServerController;
import cn.bupt.airsys.model.SysProperty;
import cn.bupt.airsys.view.MainWindow;

import javax.swing.*;

/**
 * Created by ALSO on 2015/5/26.
 */
public class App {
    private ServerController serverController;
    private MainWindow mainWindow;
    private SysProperty sysProperty;

    public App() throws Exception {
        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        UIManager.put("RootPane.setupButtonVisible", false);
        mainWindow = new MainWindow();
    }

    public static void main(String args[]) {
        try {
            new App();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
