package ua.nure.malyshevskyi.SummaryTask4.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.dao.UserDao;
import ua.nure.malyshevskyi.SummaryTask4.db.ConnectionManager;
import ua.nure.malyshevskyi.SummaryTask4.db.DBUtils;
import ua.nure.malyshevskyi.SummaryTask4.db.Fields;
import ua.nure.malyshevskyi.SummaryTask4.db.Role;
import ua.nure.malyshevskyi.SummaryTask4.db.entity.User;
import ua.nure.malyshevskyi.SummaryTask4.exception.DBException;
import ua.nure.malyshevskyi.SummaryTask4.exception.Messages;

public class DefaultUserDao implements UserDao {

	private static final Logger LOG = Logger.getLogger(DefaultUserDao.class);

	// //////////////////////////////////////////////////////////
	// Singleton
	// //////////////////////////////////////////////////////////

	private static DefaultUserDao instance = null;

	private DefaultUserDao() {

	}

	public static synchronized DefaultUserDao getInstance() {
		if (instance == null) {
			instance = new DefaultUserDao();
		}
		return instance;
	}

	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////

	private static final String SQL_GET_ALL_USERS = "SELECT * FROM users";
	private static final String SQL_GET_USER_BY_ID = "SELECT * FROM users WHERE id = ? ";
	private static final String SQL_INSERT_USER = "INSERT INTO users VALUES (DEFAULT, ?,  ? , ? , ? , ?, ? )";
	private static final String SQL_UPDATE_USER = "UPDATE users SET email = ?, login = ? , password = ?, first_name = ? , last_name = ? , role_id = ? WHERE id = ? ";
	private static final String SQL_DELETE_USER = "DELETE FROM users WHERE id = ?";
	private static final String SQL_GET_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ? ";
	private static final String SQL_GET_USER_BY_ROLE_ID = "SELECT * FROM users WHERE role_id = ? ";
	private static final String SQL_GET_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ? ";

	/**
	 * Returns a list of users.
	 * 
	 * @return List of category entities.
	 * @throws DBException
	 */
	@Override
	public List<User> getAllUsers() throws DBException {
		List<User> userList = new ArrayList<User>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_GET_ALL_USERS);
			while (rs.next()) {
				userList.add(extractUser(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBUtils.rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USERS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USERS, ex);
		} finally {
			DBUtils.close(con, stmt, rs);
		}
		return userList;
	}

	/**
	 * Returns a user by id.
	 * 
	 * @param id
	 *            User id
	 * @return User entity.
	 * @throws DBException
	 */
	@Override
	public User getUserById(long id) throws DBException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_GET_USER_BY_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, ex);
		} finally {
			DBUtils.close(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * Returns a user by login.
	 * 
	 * @param id
	 *            User id
	 * @return User entity.
	 * @throws DBException
	 */
	@Override
	public User getUserByLogin(String login) throws DBException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_GET_USER_BY_LOGIN);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
		} finally {
			DBUtils.close(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * Returns a user by email.
	 * 
	 * @param email
	 *            User email
	 * @return User entity.
	 * @throws DBException
	 */
	@Override
	public User getUserByEmail(String email) throws DBException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_GET_USER_BY_EMAIL);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
		} finally {
			DBUtils.close(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * Insert user to database.
	 * 
	 * @param user
	 *            User entity
	 * @return User id.
	 * @throws DBException
	 */
	@Override
	public long insertUser(User user) throws DBException {
		Connection con = null;
		long id = 0;
		try {
			con = ConnectionManager.getInstance().getConnection();
			id = insertUser(con, user);
			con.commit();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_INSERT_USER, ex);
			throw new DBException(Messages.ERR_CANNOT_INSERT_USER, ex);
		}
		return id;
	}

	/**
	 * Update user.
	 * 
	 * @param user
	 *            User entity
	 * @throws DBException
	 */
	@Override
	public void updateUser(User user) throws DBException {
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			updateUser(con, user);
			con.commit();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_UPDATE_USER, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
		} finally {
			DBUtils.close(con);
		}

	}

	/**
	 * Delete the user from database.
	 * 
	 * @param user
	 *            User entity.
	 * @return Affected rows.
	 * @throws DBException
	 */
	@Override
	public int deleteUser(long userId) throws DBException {
		int affectedRows = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_USER);
			pstmt.setLong(1, userId);
			affectedRows = pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_DELETE_USER, ex);
			throw new DBException(Messages.ERR_CANNOT_DELETE_USER, ex);
		} finally {
			DBUtils.close(pstmt);
			DBUtils.close(con);
		}
		return affectedRows;
	}

	@Override
	public List<User> getUsersByRole(int roleId) throws DBException {
		List<User> userList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_GET_USER_BY_ROLE_ID);
			pstmt.setLong(1, roleId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				userList.add(extractUser(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBUtils.rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, ex);
		} finally {
			DBUtils.close(con, pstmt, rs);
		}
		return userList;
	}

	/**
	 * Update user.
	 * 
	 * @param user
	 *            User to update
	 * @throws SQLException
	 */
	private void updateUser(Connection con, User user) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_USER);
			int k = 1;
			pstmt.setString(k++, user.getEmail());
			pstmt.setString(k++, user.getLogin());
			pstmt.setString(k++, user.getPassword());
			pstmt.setString(k++, user.getFirstName());
			pstmt.setString(k++, user.getLastName());
			pstmt.setInt(k++, user.getRoleId());
			pstmt.setLong(k, user.getId());
			pstmt.executeUpdate();
			con.commit();
		} finally {
			DBUtils.close(pstmt);
		}

	}

	/**
	 * Insert user.
	 * 
	 * @param user
	 *            User to insert
	 * @throws SQLException
	 */
	private long insertUser(Connection con, User user) throws SQLException {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		long id = 0;
		try {
			pstmt = con.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
			int k = 1;
			pstmt.setString(k++, user.getEmail());
			pstmt.setString(k++, user.getLogin());
			pstmt.setString(k++, user.getPassword());
			pstmt.setString(k++, user.getFirstName());
			pstmt.setString(k++, user.getLastName());
			pstmt.setInt(k++, Role.getRole(user).ordinal());

			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getLong(1);
				}
			}
		} finally {
			DBUtils.close(rs);
			DBUtils.close(pstmt);
		}
		return id;
	}

	/**
	 * Extract user.
	 * 
	 * @param rs
	 *            ResultSet with user info
	 * @throws SQLException
	 */
	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getLong(Fields.ENTITY_ID));
		user.setEmail(rs.getString(Fields.USER_EMAIL));
		user.setLogin(rs.getString(Fields.USER_LOGIN));
		user.setPassword(rs.getString(Fields.USER_PASSWORD));
		user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
		user.setLastName(rs.getString(Fields.USER_LAST_NAME));
		user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
		return user;
	}

}
