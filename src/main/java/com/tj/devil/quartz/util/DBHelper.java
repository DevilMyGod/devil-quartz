package com.tj.devil.quartz.util;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库工具类
 * @author Jean Zhang
 * @version 1.0
 */
public class DBHelper {

	private static Connection conn; // 普通jdbc连接对象
	private static DruidPlugin druidPlugin; //jfinal druid连接对象
	private static C3p0Plugin c3p0Plugin; //jfinal c3p0连接对象
	private static ActiveRecordPlugin activeRecordPlugin; //jfinal 连接对象
	
	/**
     * 初始化JFinal方法
     */
    public static void init() {
        initJfinalConnection();
    }
	
	/**
	 * 获取普通数据库连接
	 * @return 数据库连接
	 */
	public static Connection getConnection() {
	    String url = null;
        String username = null;
        String password = null;
        String driver = null;
        try {
            PropertiesUtils pro = new PropertiesUtils("/jdbc.properties");
            url = pro.getValueByKey("jdbc.url");
            username = pro.getValueByKey("jdbc.username");
            password = pro.getValueByKey("jdbc.password");
            driver = pro.getValueByKey("jdbc.driver");
        } catch (IOException e) {
            e.printStackTrace();
        }
		try {
			Class.forName(driver);
			conn = (Connection) DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
    /**
	 * 关闭数据库连接
	 * @param conn 连接connection
	 * @param st 操作statement
	 * @param rs 结果集resultset
	 */
	public static void closeAll(Connection conn, Statement st, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
     * 获取jfinal连接
     */
    public static void initJfinalConnection() {
        String url = null;
        String username = null;
        String password = null;
		String driver = null;
		try {
			PropertiesUtils pro = new PropertiesUtils("/jdbc.properties");
			url = pro.getValueByKey("jdbc.url");
			username = pro.getValueByKey("jdbc.username");
			password = pro.getValueByKey("jdbc.password");
			driver = pro.getValueByKey("jdbc.driver");
		} catch (IOException e) {
            e.printStackTrace();
        }
//        if (druidPlugin == null) {
//            druidPlugin = new DruidPlugin(url, username, password, driver);
//            if (driver.startsWith("oracle")) {
//				druidPlugin.setValidationQuery("select 1 from dual;");
//			}
//            druidPlugin.setMaxActive(10);
//            druidPlugin.start();
//        }
        if (c3p0Plugin == null) {
			c3p0Plugin = new C3p0Plugin(url, username, password);
			c3p0Plugin.setDriverClass(driver);
			c3p0Plugin.start();
        }
        if (activeRecordPlugin == null) {
            activeRecordPlugin = new ActiveRecordPlugin("Quartz", c3p0Plugin);
			activeRecordPlugin.setShowSql(true);
            activeRecordPlugin.start();
        }
    }
    
}
