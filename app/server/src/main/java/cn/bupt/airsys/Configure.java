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
    public static final String DB_DATABASE = "se";
    public static final String DB_DSN = "jdbc:" + DBMS + "://" + DB_HOST + "/" + DB_DATABASE;
    public static final String DB_USER = "also";
    public static final String DB_PASSWD = "123";
}
