package cn.bupt.airsys.model;

/**
 * Created by ALSO on 2015/5/26.
 */
public class Server {
    /*
    private String workMode;
    private float initTemp;
    private LinkedHashMap<String, Slave> slaves;

    public Server() {
        this(Configure.DEFAULT_WORKMODE, Configure.DEFAULT_INITTEMP);
    }

    public Server(String workMode, float initTemp) {
        slaves = new LinkedHashMap<String, Slave>();
        this.workMode = workMode;
        this.initTemp = initTemp;
    }

    public void updateSlaveTargetTemperature(String slaveIp, float temperature) throws ServerException {
        if ((workMode == Configure.WORKMODE[0] && temperature > Configure.TEMPERATURE_RANGE[0] && temperature < Configure.TEMPERATURE_RANGE[1])
                | (workMode == Configure.WORKMODE[1] && temperature > Configure.TEMPERATURE_RANGE[2] && temperature < Configure.TEMPERATURE_RANGE[3])) {
            Slave s = slaves.get(slaveIp);
            if (s != null) {
                s.setTargetTemp(temperature);
            } else {
                throw new ServerException("No slave found.");
            }
        }
    }

    public void updateSlaveTemperature(String slaveIp, float currentTemp) throws ServerException {
        if ((workMode == Configure.WORKMODE[0] && currentTemp > Configure.TEMPERATURE_RANGE[0] && currentTemp < Configure.TEMPERATURE_RANGE[1])
                | (workMode == Configure.WORKMODE[1] && currentTemp > Configure.TEMPERATURE_RANGE[2] && currentTemp < Configure.TEMPERATURE_RANGE[3])) {
            Slave s = slaves.get(slaveIp);
            if (s != null) {
                s.setTargetTemp(currentTemp);
            } else {
                throw new ServerException("No slave found.");
            }
        }
    }

    public void updateSlavePower(String slaveIp, String power) throws ServerException {
        for (String p : Configure.POWER) {
            if (p.equals(power)) {
                Slave s = slaves.get(slaveIp);
                if (s != null) {
                    s.setPower(power);
                } else {
                    throw new ServerException("No slave found.");
                }
                break;
            }
        }
    }

    public String getWorkMode() {
        return workMode;
    }

    public void setWorkMode(String workMode) {
        this.workMode = workMode;
    }

    public float getInitTemp() {
        return initTemp;
    }

    public void setInitTemp(float initTemp) {
        this.initTemp = initTemp;
    }

    public String getSlave(String ip) {
        return slaves.get(ip).toString();
    }

    public void addSlave(Slave slave) {
        if (slave != null) {
            slaves.put(slave.getIpAddr(), slave);
        }
    }

    public void addSlave(String id, String ip) {
        if (id != null && ip != null) {
            Slave s = new Slave(id, ip);
            slaves.put(ip, s);
        }
    }
    */
}
