package cn.bupt.airsys.model;

import cn.bupt.airsys.utils.DatabaseManager;
import cn.bupt.airsys.utils.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ALSO on 2015/5/21.
 */
public class User {
    private String username;
    private String role;
    /** TODO other meta */

    public User(String username) {
        this.username = username;
        this.role = getRole(username);
    }

    public static boolean checkPasswd(String name, String passwd, boolean md5) {
        if (md5 == false) {
            passwd = Utility.MD5(passwd);
        }
        DatabaseManager db = DatabaseManager.getInstance();
        String sql = "select password from user where username = '" + name + "';";
        try {
            ResultSet rs = db.query(sql);
            while (rs.next()) {
                return passwd.equals(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void update(String username) {
        // TODO
    }

    public boolean changePasswd(String passwd) {
        passwd = Utility.MD5(passwd);
        // TODO
        return false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole(String username) {
        DatabaseManager db = DatabaseManager.getInstance();
        String sql = "select * from user where username = '" + username + "';";
        try {
            ResultSet rs = db.query(sql);
            rs.next();
            System.out.println(rs.getString("type"));
            return rs.getString("type");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createUser(String username, String passwd, boolean passMD5) {
        if(username == null) {
            return;
        }
        if(passMD5 == false) {
            passwd = Utility.MD5(passwd);
        }
        DatabaseManager db = DatabaseManager.getInstance();
        String sql = "insert into user (username, password, type) values('" + username + "','" + passwd + "','admin');";
        try {
            db.insert(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
