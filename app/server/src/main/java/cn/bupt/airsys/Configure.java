package cn.bupt.airsys;

/**
 * Created by ALSO on 2015/5/16.
 */
public interface Configure {
    /** UDP port configure */
    public static final int DEFAULT_PORT = 8888;
    public static final int DEFAULT_SEND_PORT = 8889;

    public static final int SLAVE_PORT = 8787;

    /** JDBC configure */
    public static final String DBMS = "mysql";
    public static final String DB_HOST = "10.104.252.161";
    public static final String DB_DATABASE = "se";
    public static final String DB_DSN = "jdbc:" + DBMS + "://" + DB_HOST + "/" + DB_DATABASE;
    public static final String DB_USER = "also";
    public static final String DB_PASSWD = "123";

    public static final int HEARTBEAT_TICK = 1000 * 5; // 5 sec
    public static final int MAX_SERV_NUM = 3;
    public static final float PRICE[] = {0.0f, 0.8f * 5 * 0.0001f, 1.0f * 5 * 0.0001f, 1.3f * 5 * 0.0001f};
}
