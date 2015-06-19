package cn.bupt.airsys.client.model;

import cn.bupt.airsys.client.Configure;
import com.sun.org.apache.xml.internal.dtm.DTMAxisTraverser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ALSO on 2015/5/26.
 */
public class Slave {

    public static final int PENDING_POWER = 0;
    public static final int SML_POWER = 1;
    public static final int MID_POWER = 2;
    public static final int HIGHT_POWER = 3;
    public static final int COLD_MODE = 0;
    public static final int HOT_MODE = 1;

    public static final int PENDING = 0;
    public static final int WORKING = 1;

    private static final float TEMP_GRID = 0.25f;

    private String id;
    private int workMode = COLD_MODE;
    private String ipAddr;
    private float currentTemp;
    private float targetTemp;
    private int power;
    private float currentPay = 0;
    private int status;

    private DataChangedListener listener;

    public Slave(String id, String ipAddr) {
        this.id = id;
        this.ipAddr = ipAddr;
        this.power = PENDING_POWER;
        this.currentTemp = Configure.CURRENT_TEMP;
    }

    public void addDataChangedListener(DataChangedListener listner) {
        this.listener = listner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public float getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(float currentTemp) {
        this.currentTemp = currentTemp;
    }

    public float getTargetTemp() {
        return targetTemp;
    }

    public boolean setTargetTemp(float targetTemp) {
        System.out.println("work mode: " + workMode + "target: " + targetTemp);
        if((workMode == COLD_MODE && targetTemp <= 25.0f && targetTemp >= 18.0f) ||
            (workMode == HOT_MODE && targetTemp <= 30.0f && targetTemp >= 25.0f)) {
            this.targetTemp = targetTemp;
            return true;
        } else {
            // this is very well
            this.targetTemp = Configure.DEFAULT_TARGET_TEMP;
        }
        return false;
    }

    public int getPower() {
        return power;
    }


    public void setPower(int power) {
        if (power != this.power && power >= PENDING_POWER && power <= HIGHT_POWER) {
            this.power = power;
            System.out.println("power: " + power);
            listener.powerChanged(power);
        }
    }

    public String toString() {
        return "Slave #" + id + "ip: " + ipAddr + " current temperature: " + currentTemp + " power: " +
                targetTemp + " power: " + power;
    }

    public int getWorkMode() {
        return workMode;
    }

    public void setWorkMode(int workMode) {
        if(workMode != this.workMode && (workMode == COLD_MODE || workMode == HOT_MODE)) {
            this.workMode = workMode;
            listener.workModeChanged(workMode);
        }
    }

    public void addTempChangeDaemon() {
        new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // code below is not a good way
                if (workMode == HOT_MODE && currentTemp > 10.0f) {
                    currentTemp += power * TEMP_GRID;
                    currentTemp -= TEMP_GRID * 0.5;
                } else if(workMode == COLD_MODE && currentTemp < 33.0f) {
                    currentTemp -= power * TEMP_GRID;
                    currentTemp += TEMP_GRID * 0.5;
                }
                listener.temperatureChanged(currentTemp);
            }
        }).start();
    }

    public float getCurrentPay() {
        return currentPay;
    }

    public void setCurrentPay(float currentPay) {
        this.currentPay = currentPay;
        listener.paymentChanged(currentPay);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        if(status == PENDING || status == WORKING) {
            this.status = status;
        }
    }
}
