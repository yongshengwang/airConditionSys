package cn.bupt.airsys.model;

/**
 * Created by ALSO on 2015/5/26.
 */
public class Slave {

    public static final int SML_POWER = 1;
    public static final int MID_POWER = 2;
    public static final int HIGHT_POWER = 3;

    private String id;
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

    public void setTargetTemp(float targetTemp) {
        this.targetTemp = targetTemp;
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

    private enum Status {
        PENDING, WORKING
    }
}
