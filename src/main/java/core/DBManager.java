package core;

import java.sql.*;
import java.util.*;


/**
 * //数据库连接管理
 * Manager the databases Context
 * Maintain the connected object (use the connected pool)
 */
public class DBManager {
	private static String connErrorMsg;	//显示数据库连接错误信息

    private static final Properties configs = new Properties();  //保存配置信息对象

    private static final DBCPool pool;    //创建一个连接池对象

    //静态代码块,只在类加载时候执行一次,读取数据库配置文件
    static {
        try {
        	//Thread.currentThread().getContextClassLoader()的路径是  WEB-INF/classes/
        	configs.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("WEB-INF/db.properties"));
        } catch (Exception e) {
        	System.out.println("数据库连接失败：请在WEB-INF目录下创建数据库连接配置文件db.config后重试！");
        }
        //初始化连接池对象
        pool = new DBCPool();
    }

    //获取连接
    public static Connection getConnection() throws SQLException{
    	Connection conn = pool.getConnection();
    	if(!connErrorMsg.equals(""))
    		throw new SQLException(connErrorMsg);
        return conn;
    }

    
    /**
     * //创建连接
     * @return A SQL Connection or null
     */
    static Connection createConnection() {
    	connErrorMsg = "";  //清空错误信息
        try {
            //使用类反射机制导入jdbc驱动到内存
            Class.forName(configs.getProperty("driverClassName"));
            /* 使用sql包的驱动管理器建立数据库连接
		             * 建立连接，连接对象Connection中包含了一个socket对象
		             * 通过连接池管理连接，提高连接效率
             */
            return DriverManager.getConnection("jdbc:"
                    +configs.getProperty("usingDB")
                    +"://"+configs.getProperty("host")
                    +":"+configs.getProperty("port")+"/"
                    +configs.getProperty("dbName")+"?serverTimezone=GMT%2B8&useSSL=false",
                    configs.getProperty("username"),configs.getProperty("password"));
        } catch (ClassNotFoundException e) {
        	connErrorMsg = "数据库连接失败：请先导入jdbc驱动jar包！";
        } catch (SQLException e) {
        	connErrorMsg += "数据库连接失败：" + e.getMessage();
        }
        return null;
    }

    /**
     * //使用完要释放资源 (后开先关)
     */
    public static void 
    closeConnection(ResultSet rs, PreparedStatement pstmt, Statement stmt, Connection conn) {
        try{
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
        
        pool.setConnection(conn);  //用完连接对象要归还连接池
    }
}
