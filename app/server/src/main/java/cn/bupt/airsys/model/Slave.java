package cn.bupt.airsys.model;

/**
 * Created by ALSO on 2015/5/26.
 */
public class Slave {

    public static final int PENDING_POWER = 0;
    public static final int SML_POWER = 1;
    public static final int MID_POWER = 2;
    public static final int HIGHT_POWER = 3;
    public static final int PENDING = 0;
    public static final int WORKING = 1;

    private String id;
    private int currStatus;
    private String ipAddr;
    private float currtentTemp;
    private float targetTemp;
    private int power;
    private int requestPower;
    private float pay;

    public Slave(String id, String ipAddr) {
        this.id = id;
        this.ipAddr = ipAddr;
        this.power = SML_POWER;
        pay = 0.0f;
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

    public float getCurrtentTemp() {
        return currtentTemp;
    }

    public void setCurrtentTemp(float currtentTemp) {
        this.currtentTemp = currtentTemp;
    }

    public float getTargetTemp() {
        return targetTemp;
    }

    public void setTargetTemp(float targetTemp) {
        this.targetTemp = targetTemp;
    }

    public int getPower() {
        return power;
    }


    public void setPower(int power) {
        if (power >= 0 && power < 4) {
            if (currStatus == PENDING) {
                this.power = PENDING_POWER;
                this.requestPower = power;
            } else {
                this.power = power;
                this.requestPower = (power == 0 ? requestPower : power);
            }
        }
    }

    public int getCurrStatus() {
        return currStatus;
    }

    public void setCurrStatus(int currStatus) {
        if (currStatus != this.currStatus) {
            this.currStatus = currStatus;
            if (currStatus == WORKING) {
                if (requestPower != 0) {
                    power = requestPower;
                }
            }
        }
    }

    public String toString() {
        return "Slave #" + id + "ip: " + ipAddr + " current temperature: " + currtentTemp + " power: " +
                targetTemp + " power: " + power;
    }

    public float getPay() {
        return pay;
    }

    public void setPay(float pay) {
        this.pay = pay;
    }
}
