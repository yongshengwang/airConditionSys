package cn.bupt.airsys.client;

import cn.bupt.airsys.client.view.MainWindow;

import javax.swing.*;

/**
 * Created by ALSO on 2015/6/7.
 */
public class App {
    private MainWindow mainWindow;
    public App() throws Exception {
        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        UIManager.put("RootPane.setupButtonVisible", false);
        mainWindow = new MainWindow();
    }

    public static void main(String args[]) throws Exception {
        new App();
    }
}
