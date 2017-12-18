package com.co.example.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.rowset.CachedRowSetImpl;

public class DBUtil {

	/**
	 * 数据库配置信息
	 */
	private static JDBC jdbc = new JDBC();

	/**
	 * 数据库连接
	 */
	private Connection conn;

	public DBUtil() {
		conn = getConnection();
	}

	// 注册驱动
	static {
		try {
			Class.forName(jdbc.getDriver());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 插入数据
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int insert(String sql, Object... params) {
		return executeUpdate(sql, params);
	}

	/**
	 * 更新数据
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int update(String sql, Object... params) {
		return executeUpdate(sql, params);
	}

	/**
	 * 删除数据
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int delete(String sql, Object... params) {
		return executeUpdate(sql, params);
	}

	/**
	 * 执行DDL DML
	 * 
	 * @param conn
	 * @param sql
	 * @return
	 */
	public int executeUpdate(String sql, Object... params) {
		int rlt = 0;
		try {
			PreparedStatement pst = null;
			pst = conn.prepareStatement(sql);
			putParams(pst, params);
			rlt = pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rlt;
	}

	/**
	 * 执行查询语句
	 * 
	 * @param conn
	 * @param sql
	 * @return
	 */
	public CachedRowSetImpl query(String sql, Object... params) {
		CachedRowSetImpl rowset = null;
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			putParams(pst, params);
			ResultSet rs = pst.executeQuery();
			rowset = new CachedRowSetImpl();
			rowset.populate(rs);
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowset;
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(jdbc.getUrl(), jdbc.getUser(),
					jdbc.getPass());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 往预编译语句中放置参数
	 * 
	 * @param pst
	 * @param params
	 *            []
	 * @throws SQLException
	 */
	private void putParams(PreparedStatement pst, Object... params)
			throws SQLException {
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				pst.setObject(i + 1, params[i]);
			}
		}
	}

	/**
	 * 关闭数据库链接
	 */
	public void close() {
		if (this.conn != null) {
			try {
				this.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
