package ua.nure.malyshevskyi.SummaryTask4.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.dao.SubjectDao;
import ua.nure.malyshevskyi.SummaryTask4.db.ConnectionManager;
import ua.nure.malyshevskyi.SummaryTask4.db.DBUtils;
import ua.nure.malyshevskyi.SummaryTask4.db.Fields;
import ua.nure.malyshevskyi.SummaryTask4.db.entity.Subject;
import ua.nure.malyshevskyi.SummaryTask4.exception.DBException;
import ua.nure.malyshevskyi.SummaryTask4.exception.Messages;

public class DefaultSubjectDao implements SubjectDao {

	private static final Logger LOG = Logger.getLogger(DefaultSubjectDao.class);

	// //////////////////////////////////////////////////////////
	// Singleton
	// //////////////////////////////////////////////////////////

	private static DefaultSubjectDao instance = null;

	private DefaultSubjectDao() {
		// no op
	}

	public static synchronized DefaultSubjectDao getInstance() {
		if (instance == null) {
			instance = new DefaultSubjectDao();
		}
		return instance;
	}

	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////

	private static final String SQL_INSERT_SUBJECT = "INSERT INTO subjects VALUES ( DEFAULT, ? )";
	private static final String SQL_GET_ALL_SUBJECTS = "SELECT * FROM subjects";
	private static final String SQL_UPDATE_SUBJECT = "UPDATE subjects SET name = ? WHERE id = ? ";
	private static final String SQL_DELETE_SUBJECT = "DELETE FROM subjects WHERE id = ?";

	/**
	 * Insert the subject to database.
	 * 
	 * @param subject
	 *            Subject entity.
	 * @return Generated subject id
	 * @throws DBException
	 */
	@Override
	public long insertSubject(Subject subject) throws DBException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		long id = 0;
		try {
			con = ConnectionManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_INSERT_SUBJECT, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, subject.getName());
			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getLong(1);
				}
			}
			con.commit();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_INSERT_SUBJECT, ex);
			throw new DBException(Messages.ERR_CANNOT_INSERT_SUBJECT, ex);
		} finally {
			DBUtils.close(con, pstmt, rs);
		}
		return id;

	}

	/**
	 * Returns a list of subjects.
	 * 
	 * @return List of category entities.
	 * @throws DBException
	 */
	@Override
	public List<Subject> getAllSubjects() throws DBException {
		List<Subject> subjectList = new ArrayList<Subject>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_GET_ALL_SUBJECTS);
			while (rs.next()) {
				subjectList.add(extractSubject(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBUtils.rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_SUBJECT, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_SUBJECT, ex);
		} finally {
			DBUtils.close(con, stmt, rs);
		}
		return subjectList;
	}

	/**
	 * Update subject in database.
	 * 
	 * @param subject
	 *            User entity
	 * @throws DBException
	 */
	@Override
	public void updateSubject(Subject subject) throws DBException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_SUBJECT);
			int k = 1;
			pstmt.setString(k++, subject.getName());
			pstmt.setLong(k, subject.getId());
			con.commit();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_UPDATE_SUBJECT, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_SUBJECT, ex);
		} finally {
			DBUtils.close(pstmt);
			DBUtils.close(con);
		}
	}

	/**
	 * Delete user from database.
	 * 
	 * @param subject
	 *            Subject entity to delete
	 * @return Affected rows
	 * @throws DBException
	 */

	@Override
	public long deleteSubject(long subjectId) throws DBException {
		int affectedRows = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_SUBJECT);
			pstmt.setLong(1, subjectId);
			affectedRows = pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_DELETE_SUBJECT, ex);
			throw new DBException(Messages.ERR_CANNOT_DELETE_SUBJECT, ex);
		} finally {
			DBUtils.close(pstmt);
			DBUtils.close(con);
		}
		return affectedRows;
	}

	/**
	 * Extract subject.
	 * 
	 * @param rs
	 *            ResultSet with required info.
	 * @return Subject entity.
	 * @throws SQLException
	 */

	private Subject extractSubject(ResultSet rs) throws SQLException {
		Subject subj = new Subject();
		subj.setId(rs.getLong(Fields.ENTITY_ID));
		subj.setName(rs.getString(Fields.SUBJECT_ENTITY_NAME));
		return subj;
	}
}
