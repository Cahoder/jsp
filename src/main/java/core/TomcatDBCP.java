package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.*;
import javax.sql.DataSource;

/**
 * 	使用	Tomcat Web 服务器提供的实现连接池
 * Database Connection Pool
 * @author cahoder
 *
 */
public class TomcatDBCP {
	
	private TomcatDBCP() {}
	
	/**
	 * 	JDBC 提供了 javax.sql.DataSource 接口负责与数据库建立连接
	 * 	Tomcat服务器实现了这个接口，可以直接通过context.xml定义的参数与数据库建立连接
	 *	然后要通过Java的JNDI命名和目录接口来获取DataSource实例引用,不可以自行创建实例
	 * @return 数据库连接池中的一个连接
	 * @throws SQLException SQL异常
	 */
	public synchronized static Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			//Context 是 javax.naming 包提供的一个接口，用于查找数据库连接的配置文件
			Context envContext = new InitialContext();
			// java:comp/env 是 environment naming context（ENC）的标准JNDI CONTEXT
			envContext = (Context) envContext.lookup("java:comp/env");
			//DataSource 实例必须通过Tomcat提供的不可以自己实例化，lookup("xxx")要与context.xml中配置的name相同
			DataSource ds = (DataSource)envContext.lookup("jdbc/mysql");
			
            conn = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		}
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
