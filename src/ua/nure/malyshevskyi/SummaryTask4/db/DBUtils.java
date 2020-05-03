package ua.nure.malyshevskyi.SummaryTask4.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.exception.Messages;

public class DBUtils {

	private static final Logger LOG = Logger.getLogger(ConnectionManager.class);

	/**
	 * Closes a connection.
	 * 
	 * @param con
	 *            Connection to be closed.
	 */
	public static void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION);
			}
		}
	}

	/**
	 * Closes a statement object.
	 */
	public static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
			}
		}
	}

	/**
	 * Closes a result set object.
	 */
	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
			}
		}
	}

	/**
	 * Closes resources.
	 */
	public static void close(Connection con, Statement stmt, ResultSet rs) {
		close(rs);
		close(stmt);
		close(con);
	}

	/**
	 * Rollbacks a connection.
	 * 
	 * @param con
	 *            Connection to be rollbacked.
	 */
	public static void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_ROLLBACK_TRANSACTION, ex);
			}
		}
	}

}
