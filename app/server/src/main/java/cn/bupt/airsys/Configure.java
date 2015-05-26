package cn.bupt.airsys;

/**
 * Created by ALSO on 2015/5/16.
 */
public interface Configure {
    /** UDP port configure */
    public static final int DEFAULT_PORT = 8888;

    /** JDBC configure */
    public static final String DBMS = "mysql";
    public static final String DB_HOST = "10.104.252.161";
    public static final String DB_DATABASE = "airsys";
    public static final String DB_DSN = "jdbc:" + DBMS + "://" + DB_HOST + "/" + DB_DATABASE;
    public static final String DB_USER = "also";
    public static final String DB_PASSWD = "123";

    public static final String[] POWER = {"small", "middle", "high"};

    public static final String[] WORKMODE = {"cold", "hot"};
    public static final float[] TEMPERATURE_RANGE = {18.0f, 25.0f, 25.0f, 30.0f};
    public static final String DEFAULT_WORKMODE = WORKMODE[0];
    public static final float DEFAULT_INITTEMP = 23.0f;

}
