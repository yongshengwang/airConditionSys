package cn.bupt.airsys.client.model;

/**
 * Created by ALSO on 2015/5/26.
 */
public class Slave {

    public static final int SML_POWER = 1;
    public static final int MID_POWER = 2;
    public static final int HIGHT_POWER = 3;
    public static final int COLD_MODE = 1;
    public static final int HOT_MODE = 2;

    private String id;
    private int workMode = COLD_MODE;
    private Status currStatus;
    private String ipAddr;
    private float currtentTemp;
    private float targetTemp;
    private int power;
    public Slave(String id, String ipAddr) {
        this.id = id;
        this.ipAddr = ipAddr;
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
        if (power > 0 && power < 4) {
            switch (power - this.power) {
                case -1:
                    this.power = SML_POWER;
                    break;

                case 1:
                    this.power = HIGHT_POWER;
                    break;

                default:
                    break;
            }
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
        return "Slave #" + id + "ip: " + ipAddr + " current temperature: " + currtentTemp + " power: " +
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

    private enum Status {
        PENDING, WORKING
    }
}
