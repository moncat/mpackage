package com.co.example.dbutil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.rowset.CachedRowSetImpl;

/**
 * @desc: lbjn
 * @author: Administrator
 * @createTime: 2014年11月10日 上午11:11:48
 * @history:
 * @version: v1.0
 */
public class UserDao {

	/**
	 * @author: kpchen
	 * @createTime: 2014年11月10日 下午8:22:26
	 * @history:
	 * @return List<User>
	 */
	public List<User> findAllUsers() {
		List<User> userLists = new ArrayList<User>();
		DBUtil dbUtil = null;
		User user = null;
		CachedRowSetImpl rs = null;
		String sql = "select * from tb_user";
		try {
			dbUtil = new DBUtil();
			rs = dbUtil.query(sql);

			while (rs.next()) {
				user = new User(rs.getInt("USERID"), rs.getString("USERNAME"),
						rs.getString("PASSWORD"), rs.getInt("ROLE"));
				userLists.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				dbUtil.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return userLists;
	}

	/**
	 * @author: kpchen
	 * @createTime: 2014年11月10日 下午8:22:20
	 * @history:
	 * @param id
	 *            void
	 */
	public void delUser(String id) {
		DBUtil dbUtil = null;
		String sql = "delete from tb_user where userId=" + id;
		try {
			dbUtil = new DBUtil();
			dbUtil.delete(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}

	}

	/**
	 * @author: kpchen
	 * @createTime: 2014年11月10日 下午8:40:26
	 * @history:
	 * @param user
	 *            void
	 */
	public void addUser(User user) {
		DBUtil dbUtil = null;
		String sql = "insert into tb_user(userId,userName,password,role)values(?,?,?,?)";
		Object[] obj = { user.getUserId(), user.getUserName(),
				user.getPassword(), user.getRole() };
		try {
			dbUtil = new DBUtil();
			dbUtil.insert(sql, obj);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}
	}

	/**
	 * @author: kpchen
	 * @createTime: 2014年11月10日 下午8:48:02
	 * @history:
	 * @param id
	 * @return User
	 */
	public User getUserById(String id) {
		DBUtil dbUtil = null;
		User user = null;
		CachedRowSetImpl rs = null;
		String sql = "select * from tb_user where userId=" + id;
		try {
			dbUtil = new DBUtil();
			rs = dbUtil.query(sql);

			if (rs.next()) {
				user = new User(rs.getInt("USERID"), rs.getString("USERNAME"),
						rs.getString("PASSWORD"), rs.getInt("ROLE"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				dbUtil.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return user;
	}

	/**
	 * @author: kpchen
	 * @createTime: 2014年11月10日 下午8:55:10
	 * @history:
	 * @param user
	 *            void
	 */
	public void updateUser(User user) {
		DBUtil dbUtil = null;
		String sql = "update tb_user set userName=?, password=? ,role=? where userId = ?";
		Object[] obj = { user.getUserName(), user.getPassword(),
				user.getRole(), user.getUserId() };
		try {
			dbUtil = new DBUtil();
			dbUtil.update(sql, obj);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil.close();
		}

	}

	/**
	 * @author: kpchen
	 * @createTime: 2014年11月10日 下午9:18:30
	 * @history:
	 * @param where1
	 * @return List<User>
	 */
	public List<User> findUsersByUserName(String where1) {
		List<User> userLists = new ArrayList<User>();
		DBUtil dbUtil = null;
		User user = null;
		CachedRowSetImpl rs = null;
		String sql = "select * from tb_user where 1=1 " + where1;
		try {
			dbUtil = new DBUtil();
			rs = dbUtil.query(sql);

			while (rs.next()) {
				user = new User(rs.getInt("USERID"), rs.getString("USERNAME"),
						rs.getString("PASSWORD"), rs.getInt("ROLE"));
				userLists.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				dbUtil.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return userLists;
	}

}
