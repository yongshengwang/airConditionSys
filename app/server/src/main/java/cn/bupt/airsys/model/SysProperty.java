package cn.bupt.airsys.model;

/**
 * Created by ALSO on 2015/6/2.
 */
public class SysProperty {

    public static final int COLD = 1;
    public static final int HOT = 2;
    public static final int LOW_FREQ = 1;
    public static final int HIGH_FREQ = 2;
    public static final int MID_FREQ = 3;
    private static SysProperty singleton;
    private boolean mains;
    private float initTemp;
    private int communiFreq;
    private int workMode;
    private User user;

    private SysProperty() {
        mains = false;
        initTemp = 23.0f;
        workMode = COLD;
        communiFreq = MID_FREQ;
    }

    public static synchronized SysProperty getInstance() {
        if (singleton == null) {
            singleton = new SysProperty();
        }
        return singleton;
    }

    public void setAdmin(User u) {
        this.user = u;
    }

    public User getCurrentAdmin() {
        if (user != null) {
            return user;
        }
        return null;
    }

    public float getInitTemp() {
        return initTemp;
    }

    public void setInitTemp(float initTemp) {
        this.initTemp = initTemp;
    }

    public int getWorkMode() {
        return workMode;
    }

    public void setWorkMode(int workMode) {
        if (workMode == COLD || workMode == HOT) {
            this.workMode = workMode;
        }
    }

    public int getCommuniFreq() {
        return communiFreq;
    }

    public void setCommuniFreq(int communiFreq) {
        this.communiFreq = communiFreq;
    }

    public void poweroff() {
        this.mains = false;
    }

    public void bootUp() {
        this.mains = true;
    }
}
