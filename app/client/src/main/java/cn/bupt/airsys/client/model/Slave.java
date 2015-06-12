package cn.bupt.airsys.client.model;

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
    private static final float TEMP_GRID = 0.25f;


    private String id;
    private int workMode = COLD_MODE;
    private Status currStatus;
    private String ipAddr;
    private float currentTemp;
    private float targetTemp;
    private int power;
    private float currentPay = 0;

    private DataChangedListener listener;

    public Slave(String id, String ipAddr) {
        this.id = id;
        this.ipAddr = ipAddr;
        this.power = PENDING_POWER;
        this.currStatus = Status.PENDING;
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
        }
        return false;
    }

    public int getPower() {
        return power;
    }


    public void setPower(int power) {
        if (power >= PENDING_POWER && power <= HIGHT_POWER ) {
            this.power = power;
        }
    }

    public String getCurrStatus() {
        if (currStatus == Status.PENDING) {
            return "PENDING";
        }
        return "WORKING";
    }

    public void setCurrStatus(String currStatus) {
        if (currStatus.equals("PEDING")) {
            this.currStatus = Status.PENDING;
        } else {
            this.currStatus = Status.WORKING;
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
        if(workMode == COLD_MODE || workMode == HOT_MODE) {
            this.workMode = workMode;
        }
    }

    public void addTempChangeDaemon() {
        new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // code below is not a good way
                if (workMode == HOT_MODE && currentTemp >= 10.0f) {
                    currentTemp += (power - 1) * TEMP_GRID;
                } else if(workMode == COLD_MODE && currentTemp <= 33.0f) {
                    currentTemp -= (power - 1) * TEMP_GRID;
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

    private enum Status {
        PENDING, WORKING
    }
}
