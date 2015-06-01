package cn.bupt.airsys.model;

/**
 * Created by ALSO on 2015/5/26.
 */
public class Slave {

    private String id;
    ;
    private Status currStatus;
    private String ipAddr;
    private float currtentTemp;
    private float targetTemp;
    private String power;
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

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        power = power;
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
