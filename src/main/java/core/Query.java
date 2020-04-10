package core;

import java.sql.*;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;


/**
 * 	对外提供服务的核心类
 * @author cahoder
 */
public class Query{
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	/**
	 * 	无参构造器
	 */
	public Query() {}

	/**
	 * 	定义一个模板方法,对所有的连接和关闭数据库操作进行封装，在回调进行具体操作
	 * @param callback 回调具体操作
	 * @return 具体执行结果
	 */
    private Object generalTemplate(QueryCallback callback) throws SQLException{
        try {
        	conn = TomcatDBCP.getConnection();
            return callback.doExecute();  //让回调做具体任务
        }
        catch (SQLException e) {
        	throw new SQLException(e);
        }
        //可以把数据库所有句柄关闭写在这里
        finally {
			TomcatDBCP.closeConnection(rs, pstmt, stmt, conn);
		}
    }
    
    /**
     * 	增|删|改 数据
     * @param SQL 预处理SQL语句
     * @param params 预处理语句参数
     * @return 数据库影响行数,大于1则插入成功
     * @throws SQLException SQL异常
     */
    public int executeUpdate(final String SQL, final Object[] params) throws SQLException {
    	return (int)generalTemplate(new QueryCallback() {
			
			@Override
			public Object doExecute() throws SQLException {
	    		pstmt = conn.prepareStatement(SQL);
				JDBCUtils.installParams(pstmt, params); //预处理语句填充数据
	    		return pstmt.executeUpdate();	//影响数据量
			}
		});
    }
    
    /**
     * 	查询SQL符合记录量
     * @param SQL 预处理SQL语句
     * @param params 预处理语句参数
     * @return 查询到的符合记录个数
     * @throws SQLException SQL异常
     */
    public int executeQueryCount(final String SQL, final Object[] params) throws SQLException {
    	return (int)generalTemplate(new QueryCallback() {
			
			@Override
			public Object doExecute() throws SQLException {
				pstmt = conn.prepareStatement(SQL);
	    		int count = 0;
	    		JDBCUtils.installParams(pstmt, params); //预处理语句填充数据
	    		
				rs = pstmt.executeQuery();
				// SQL 语句中使用了  count(*) 函数只会返回一行一列数据
				if(SQL.toLowerCase().contains("count")) {
					if(rs.next())
						count=rs.getInt(1);
				}
				// SQL 语句没有使用了  count(*) 函数也想查数据量
				else {
					rs.last();
					count = rs.getRow();
				}
	    		return count;
			}
		});
	}
    
    /**
     * 	查询SQL符合记录
     * 	jdk 要求大于1.7
     * @param SQL 预处理SQL语句
     * @param params 预处理语句参数
     * @return 查询到的符合记录
     * @throws SQLException SQL异常
     */
    public ResultSet executeQuery(final String SQL, final Object[] params) throws SQLException {
    	return (ResultSet)generalTemplate(new QueryCallback() {
			
			@Override
			public Object doExecute() throws SQLException {
				pstmt = conn.prepareStatement(SQL);
	    		JDBCUtils.installParams(pstmt, params); //预处理语句填充数据
	    		
				rs = pstmt.executeQuery();
				
				RowSetFactory factory = RowSetProvider.newFactory();
				CachedRowSet cachedRs = factory.createCachedRowSet();
				
				//ResultSet装填到RowSet缓存到内存，ResultSet就可以关闭掉了
				cachedRs.populate(rs);
	    		return cachedRs;
			}
		});
    }
}
