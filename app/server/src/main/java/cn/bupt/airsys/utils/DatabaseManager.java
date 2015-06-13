package cn.bupt.airsys.utils;


import cn.bupt.airsys.Configure;

import java.sql.*;

/**
 * Created by ALSO on 2015/5/16.
 */
public class DatabaseManager {
    private final static String DRIVER = "com.mysql.jdbc.Driver";
    private static DatabaseManager singleton = null;
    private static Connection con;

    private DatabaseManager() { }

    public synchronized static DatabaseManager getInstance() {
        if(singleton == null) {
            try {
                // Load Driver
                Class.forName(DRIVER);
                singleton = new DatabaseManager();
                singleton.con = DriverManager.getConnection(Configure.DB_DSN, Configure.DB_USER, Configure.DB_PASSWD);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return singleton;
    }

    public static void close() {
       if(singleton != null)   {
           try {
               con.close();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
    }

    public static ResultSet query(String sql) throws SQLException {
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(sql);
        return rs;
    }

    public void insert(String sql) throws SQLException {
        Statement stm = con.createStatement();
        stm.executeUpdate(sql);
        stm.close();
    }
}
