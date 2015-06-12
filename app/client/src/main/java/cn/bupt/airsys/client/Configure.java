package cn.bupt.airsys.client;

/**
 * Created by ALSO on 2015/6/7.
 */
public interface Configure {
    public static final int DEFAULT_PORT = 8989;
    public static final String REMOTE_IP = "10.104.253.114";
    public static final int REMOTE_PORT = 8888;

    /** tick time of sending data(current status) to master */
    public static final int DEFAULT_TICK = 1000 * 5; // 5 sec
}

