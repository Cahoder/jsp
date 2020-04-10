package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

/**
 * 	使用Apache基金会提供的JDBC连接池
 * 	不依赖服务器的连接池（解耦）
 */
public class ApacheDBCP {
	
	private static BasicDataSource dataSource = null;
	
	private ApacheDBCP() {}
	
	static {
		Properties configs = new Properties();
		try {
        	//Thread.currentThread().getContextClassLoader()的路径是  WEB-INF/classes/
        	configs.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("WEB-INF/db.properties"));
        	configs.setProperty("url", "jdbc:"
        			+configs.getProperty("usingDB")
                    +"://"+configs.getProperty("host")
                    +":"+configs.getProperty("port")+"/"
                    +configs.getProperty("dbName")+"?serverTimezone=GMT%2B8&useSSL=false");
        	//以指定配置信息创建数据源
        	dataSource = (BasicDataSource) BasicDataSourceFactory.createDataSource(configs);
        } catch (Exception e) {
        	System.out.println("数据库连接失败：请在WEB-INF目录下创建数据库连接配置文件db.config后重试！");
        }
	}
	
	/**
	 *	apache提供的连接池创建
	 * @return 池中的一个连接句柄
	 * @throws SQLException SQL异常
	 */
	public synchronized static Connection getConnection() throws SQLException {
		Connection conn = null;
		if(dataSource!=null) 
			conn = dataSource.getConnection();
		return conn;
	}
	
	/**
	 *	使用完要释放各种资源（后开先关）
	 *	其中conn.close()是归还连接池而非真正关闭了
	 * @param rs ResultSet
	 * @param pstmt PreparedStatement
	 * @param stmt Statement
	 * @param conn Connection
	 */
	public static void closeConnection
	(ResultSet rs, PreparedStatement pstmt, Statement stmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
            	conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
