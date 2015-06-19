package cn.bupt.airsys.client;

/**
 * Created by ALSO on 2015/6/7.
 */
public interface Configure {
    public static final int DEFAULT_PORT = 8989;
    public static final int DEFAULT_RECV_PORT = 12345;
    public static final int REMOTE_PORT = 8888;

    public static String ROOM_ID = "3";

    ///public static final String REMOTE_IP = "10.205.21.117"; // fan
    public static final String REMOTE_IP = "10.205.23.171";

    /** tick time of sending data(current status) to master */
    public static final int DEFAULT_TICK = 1000 * 5; // 5 sec

    /** hot */
    public static float CURRENT_TEMP = 16.0f;
    public static float DEFAULT_TARGET_TEMP = 27.0f;

            /*
    public static float CURRENT_TEMP = 16.0f;
    public static float DEFAULT_TARGET_TEMP = 27.0f;
    */
}

