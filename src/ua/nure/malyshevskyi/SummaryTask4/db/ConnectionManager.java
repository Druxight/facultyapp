package ua.nure.malyshevskyi.SummaryTask4.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.exception.DBException;
import ua.nure.malyshevskyi.SummaryTask4.exception.Messages;

public class ConnectionManager {

	private static final Logger LOG = Logger.getLogger(ConnectionManager.class);

	// //////////////////////////////////////////////////////////
	// singleton
	// //////////////////////////////////////////////////////////

	private static ConnectionManager instance;

	public static synchronized ConnectionManager getInstance() throws DBException {
		if (instance == null) {
			instance = new ConnectionManager();
		}
		return instance;
	}

	private DataSource ds;

	private ConnectionManager() throws DBException {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/faculty4app");
			LOG.trace("Data source ==> " + ds);
		} catch (NamingException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
		}
	}

	/**
	 * Returns a DB connection from the Pool Connections.
	 * 
	 * @return DB connection.
	 * @throws DBException
	 */

	public Connection getConnection() throws DBException {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
		}
		return con;
	}

}
