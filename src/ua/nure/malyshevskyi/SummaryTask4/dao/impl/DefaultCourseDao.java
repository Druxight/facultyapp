package ua.nure.malyshevskyi.SummaryTask4.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.dao.CourseDao;
import ua.nure.malyshevskyi.SummaryTask4.db.ConnectionManager;
import ua.nure.malyshevskyi.SummaryTask4.db.DBUtils;
import ua.nure.malyshevskyi.SummaryTask4.db.Fields;
import ua.nure.malyshevskyi.SummaryTask4.db.entity.Course;
import ua.nure.malyshevskyi.SummaryTask4.exception.DBException;
import ua.nure.malyshevskyi.SummaryTask4.exception.Messages;

public class DefaultCourseDao implements CourseDao {

	private static final Logger LOG = Logger.getLogger(DefaultCourseDao.class);

	// //////////////////////////////////////////////////////////
	// singleton
	// //////////////////////////////////////////////////////////

	private static DefaultCourseDao instance = null;

	private DefaultCourseDao() {
		// no op
	}

	public static synchronized DefaultCourseDao getInstance() {
		if (instance == null) {
			instance = new DefaultCourseDao();
		}
		return instance;
	}

	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////

	private static final String SQL_INSERT_COURSE = "INSERT INTO courses VALUES (DEFAULT, ? , ? , ? , ? , ? )";

	private static final String SQL_UPDATE_COURSE = "UPDATE courses SET name = ? , start_date = ? , end_date = ? , subject_id = ? , professor_id = ? WHERE id = ? ";

	private static final String SQL_DELETE_COURSE = "DELETE FROM courses WHERE id = ? ";

	private static final String SQL_GET_ALL_COURSES = "SELECT * FROM courses";

	private static final String SQL_GET_COURSE_BY_ID = "SELECT * from courses WHERE id = ? ";

	private static final String SQL_GET_COURSES_BY_SUBJECT_ID = "SELECT * FROM courses WHERE subject_id = ? ";

	private static final String SQL_GET_ALL_COURSES_BY_PROFESSOR_ID = "SELECT * FROM courses WHERE professor_id = ? ";

	/**
	 * Insert course to the database.
	 * 
	 * @param course
	 *            Course entity
	 * @return Course id.
	 * @throws DBException
	 */
	@Override
	public long insertCourse(Course course) throws DBException {
		Connection con = null;
		long id = 0;
		try {
			con = ConnectionManager.getInstance().getConnection();
			id = insertCourse(con, course);
			con.commit();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_INSERT_COURSE, ex);
			throw new DBException(Messages.ERR_CANNOT_INSERT_COURSE, ex);
		} finally {
			DBUtils.close(con);
		}
		return id;

	}

	/**
	 * Update user.
	 * 
	 * @param course
	 *            Course to update
	 * @throws DBException
	 */
	@Override
	public void updateCourse(Course course) throws DBException {
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			updateCourse(con, course);
			con.commit();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_UPDATE_COURSE, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_COURSE, ex);
		} finally {
			DBUtils.close(con);
		}

	}

	/**
	 * Returns a list of courses.
	 * 
	 * @throws DBException
	 */
	@Override
	public List<Course> getCourses() throws DBException {
		List<Course> courseList = new ArrayList<Course>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_GET_ALL_COURSES);
			while (rs.next()) {
				courseList.add(extractCourse(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBUtils.rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
		} finally {
			DBUtils.close(con, stmt, rs);
		}
		return courseList;
	}

	/**
	 * Return courses by subject id.
	 * 
	 * @param subjectId
	 * 
	 * @throws DBException
	 */

	@Override
	public List<Course> getCoursesBySubject(long subjectId) throws DBException {
		List<Course> courseList = new ArrayList<Course>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			stmt = con.prepareStatement(SQL_GET_COURSES_BY_SUBJECT_ID);
			stmt.setLong(1, subjectId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				courseList.add(extractCourse(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBUtils.rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
		} finally {
			DBUtils.close(con, stmt, rs);
		}
		return courseList;
	}

	/**
	 * Return courses by professor id.
	 * 
	 * @param userId
	 * 
	 * @throws DBException
	 */
	@Override
	public List<Course> getCoursesByProfessor(long professorId) throws DBException {
		List<Course> courseList = new ArrayList<Course>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			stmt = con.prepareStatement(SQL_GET_ALL_COURSES_BY_PROFESSOR_ID);
			stmt.setLong(1, professorId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				courseList.add(extractCourse(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBUtils.rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
		} finally {
			DBUtils.close(con, stmt, rs);
		}
		return courseList;
	}

	/**
	 * Return the course by id.
	 * 
	 * @param id
	 *            Course id
	 * @return Course entity.
	 * @throws DBException
	 */

	// // NOT WORKING
	@Override
	public Course getCourseById(long id) throws DBException {
		Course course = new Course();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			stmt = con.prepareStatement(SQL_GET_COURSE_BY_ID);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				course = extractCourse(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
		} finally {
			DBUtils.close(con, stmt, rs);
		}
		return course;
	}

	/**
	 * Delete the course by id.
	 * 
	 * @param id
	 *            Course id
	 * @return Affected rows.
	 * @throws DBException
	 */
	@Override
	public int deleteCourseById(long courseId) throws DBException {
		int affectedRows = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_COURSE);
			pstmt.setLong(1, courseId);
			affectedRows = pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_DELETE_COURSE, ex);
			throw new DBException(Messages.ERR_CANNOT_DELETE_COURSE, ex);
		} finally {
			DBUtils.close(pstmt);
			DBUtils.close(con);
		}
		return affectedRows;
	}

	/**
	 * Insert course.
	 * 
	 * @param course
	 *            Course entity
	 * @return Course id.
	 * @throws SQLException
	 */
	private long insertCourse(Connection con, Course course) throws SQLException {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		long id = 0;
		try {
			pstmt = con.prepareStatement(SQL_INSERT_COURSE, Statement.RETURN_GENERATED_KEYS);
			int k = 1;
			pstmt.setString(k++, course.getName());
			pstmt.setString(k++, course.getStartDate().toString());
			pstmt.setString(k++, course.getEndDate().toString());
			pstmt.setLong(k++, course.getSubjectId());
			pstmt.setLong(k++, course.getProfessorId());

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
	 * Update course.
	 * 
	 * @param course
	 *            Course to update
	 * @throws SQLException
	 */
	private void updateCourse(Connection con, Course course) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_COURSE);
			int k = 1;
			pstmt.setString(k++, course.getName());
			pstmt.setDate(k++, course.getStartDate());
			pstmt.setDate(k++, course.getEndDate());
			pstmt.setLong(k++, course.getSubjectId());
			pstmt.setLong(k++, course.getProfessorId());
			pstmt.setLong(k, course.getId());
			pstmt.executeUpdate();
			con.commit();
		} finally {
			DBUtils.close(pstmt);
		}

	}

	/**
	 * Extract course.
	 * 
	 * @param rs
	 *            ResultSet with required info.
	 * @throws DBException
	 */
	private Course extractCourse(ResultSet rs) throws SQLException {
		Course course = new Course();
		course.setId(rs.getLong(Fields.ENTITY_ID));
		course.setName(rs.getString(Fields.COURSE_NAME));
		course.setStartDate(rs.getDate(Fields.COURSE_START_DATE));
		course.setEndDate(rs.getDate(Fields.COURSE_END_DATE));
		course.setSubjectId(rs.getLong(Fields.COURSE_SUBJECT_ID));
		course.setProfessorId(rs.getLong(Fields.COURSE_PROFESSOR_ID));
		return course;
	}

}
