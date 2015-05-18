package cn.bupt.airsys;

/**
 * Created by ALSO on 2015/5/16.
 */
public interface Configure {
    /** UDP port configure */
    static public int DEFAULT_PORT = 8888;

    /** JDBC configure */
    static public String DBMS= "mysql";
    static public String DB_HOST= "10.104.252.161";
    static public String DB_DATABASE = "airsys";
    static public String DB_DSN = "jdbc:" + DBMS + "://" + DB_HOST + "/" + DB_DATABASE;
    static public String DB_USER = "also";
    static public String DB_PASSWD = "123";
}
