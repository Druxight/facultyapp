package ua.nure.malyshevskyi.SummaryTask4.dto.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.db.ConnectionManager;
import ua.nure.malyshevskyi.SummaryTask4.db.DBUtils;
import ua.nure.malyshevskyi.SummaryTask4.db.Fields;
import ua.nure.malyshevskyi.SummaryTask4.dto.CourseDto;
import ua.nure.malyshevskyi.SummaryTask4.dto.bean.CourseBean;
import ua.nure.malyshevskyi.SummaryTask4.exception.DBException;
import ua.nure.malyshevskyi.SummaryTask4.exception.Messages;

public class DefaultCourseDto implements CourseDto {

	private static final Logger LOG = Logger.getLogger(DefaultCourseDto.class);

	// //////////////////////////////////////////////////////////
	// singleton
	// //////////////////////////////////////////////////////////

	private static DefaultCourseDto instance = null;

	private DefaultCourseDto() {
		// no op
	}

	public static synchronized DefaultCourseDto getInstance() {
		if (instance == null) {
			instance = new DefaultCourseDto();
		}
		return instance;
	}

	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////

	private static final String SQL_GET_ALL_COURSES = "SELECT c.id, c.name, c.start_date, c.end_date, c.subject_id, c.professor_id, s.name as subject_name, "
			+ "u.first_name, u.last_name FROM courses c JOIN subjects s ON c.subject_id = s.id JOIN users u ON c.professor_id = u.id";

	private static final String SQL_GET_COURSE_STUDENTS = "SELECT * FROM rating WHERE course_id = ? ";

	private static final String SQL_GET_ALL_COURSES_BY_PROFESSOR_ID = "SELECT c.id, c.name, c.start_date, c.end_date, c.subject_id, c.professor_id, s.name as subject_name, "
			+ "u.first_name, u.last_name FROM courses c JOIN subjects s ON c.subject_id = s.id JOIN users u ON c.professor_id = u.id WHERE c.professor_id = ? ";

	private static final String SQL_GET_ALL_COURSES_BY_SUBJECT_ID = "SELECT c.id, c.name, c.start_date, c.end_date, c.subject_id, c.professor_id, s.name as subject_name, "
			+ "u.first_name, u.last_name FROM courses c JOIN subjects s ON c.subject_id = s.id JOIN users u ON c.professor_id = u.id WHERE c.subject_id = ? ";

	private static final String SQL_GET_COURSES_BY_USER_ID = "SELECT r.user_id, c.id, c.name, c.start_date, c.end_date, c.subject_id, c.professor_id, "
			+ "s.name as subject_name, u.first_name, u.last_name "
			+ "FROM courses c JOIN subjects s ON c.subject_id = s.id JOIN users u ON c.professor_id = u.id "
			+ "JOIN rating r ON c.id = course_id WHERE r.user_id =  ? ";

	private static final String SQL_GET_COURSES_BY_ID = "SELECT r.user_id, c.id, c.name, c.start_date, c.end_date, c.subject_id, c.professor_id, "
			+ "s.name as subject_name, u.first_name, u.last_name "
			+ "FROM courses c JOIN subjects s ON c.subject_id = s.id JOIN users u ON c.professor_id = u.id "
			+ "JOIN rating r ON c.id = course_id WHERE c.id =  ? ";

	private static final String SQL_GET_COURSES_FROM_CURRENT_DATE = "SELECT c.id, c.name, c.start_date, c.end_date, c.subject_id, c.professor_id, s.name as subject_name, "
			+ "u.first_name, u.last_name FROM courses c JOIN subjects s ON c.subject_id = s.id JOIN users u ON c.professor_id = u.id where c.start_date > now()";

	/**
	 * Returns a list of courses.
	 * 
	 * @return List<CourseBean>
	 * @throws DBException
	 */
	@Override
	public List<CourseBean> getDtoCourses() throws DBException {
		List<CourseBean> courseList = new ArrayList<CourseBean>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_GET_ALL_COURSES);
			while (rs.next()) {
				courseList.add(extractCourseDto(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBUtils.rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
		} finally {
			DBUtils.close(con, stmt, rs);
		}
		setStudentsToCourse(courseList);
		return courseList;
	}

	/**
	 * Returns a list of courses which do not start yet.
	 * 
	 * @return List<CourseBean>
	 * @throws DBException
	 */
	@Override
	public List<CourseBean> getCoursesFromCurrentDate() throws DBException {
		List<CourseBean> courseList = new ArrayList<CourseBean>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_GET_COURSES_FROM_CURRENT_DATE);
			while (rs.next()) {
				courseList.add(extractCourseDto(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBUtils.rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
		} finally {
			DBUtils.close(con, stmt, rs);
		}
		setStudentsToCourse(courseList);
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
	public List<CourseBean> getCoursesByProfessor(long professorId) throws DBException {
		List<CourseBean> courseList = new ArrayList<CourseBean>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			stmt = con.prepareStatement(SQL_GET_ALL_COURSES_BY_PROFESSOR_ID);
			stmt.setLong(1, professorId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				courseList.add(extractCourseDto(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBUtils.rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
		} finally {
			DBUtils.close(con, stmt, rs);
		}
		setStudentsToCourse(courseList);
		return courseList;
	}

	/**
	 * Return courses by subject id.
	 * 
	 * @param userId
	 * 
	 * @throws DBException
	 */
	@Override
	public List<CourseBean> getCourseBeanBySubjectId(long id) throws DBException {
		List<CourseBean> courseList = new ArrayList<CourseBean>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			stmt = con.prepareStatement(SQL_GET_ALL_COURSES_BY_SUBJECT_ID);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				courseList.add(extractCourseDto(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBUtils.rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
		} finally {
			DBUtils.close(con, stmt, rs);
		}
		setStudentsToCourse(courseList);
		return courseList;
	}

	/**
	 * Return CourseBean`s by user id.
	 * 
	 * @param userId
	 * 
	 * @throws DBException
	 */
	@Override
	public List<CourseBean> getCoursesByUser(long userId) throws DBException {
		List<CourseBean> courseList = new ArrayList<CourseBean>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			stmt = con.prepareStatement(SQL_GET_COURSES_BY_USER_ID);
			stmt.setLong(1, userId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				courseList.add(extractCourseDto(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBUtils.rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
		} finally {
			DBUtils.close(con, stmt, rs);
		}
		setStudentsToCourse(courseList);
		return courseList;
	}

	/**
	 * Extract course bean.
	 * 
	 * @param rs
	 *            ResultSet with required info.
	 * @throws DBException
	 */

	private CourseBean extractCourseDto(ResultSet rs) throws SQLException {
		CourseBean course = new CourseBean();
		course.setId(rs.getLong(Fields.ENTITY_ID));
		course.setName(rs.getString(Fields.COURSE_NAME));
		course.setStartDate(rs.getDate(Fields.COURSE_START_DATE));
		course.setEndDate(rs.getDate(Fields.COURSE_END_DATE));
		course.setSubjectId(rs.getLong(Fields.COURSE_SUBJECT_ID));
		course.setSubjectName(rs.getString(Fields.SUBJECT_NAME));
		course.setProfessorId(rs.getLong(Fields.COURSE_PROFESSOR_ID));
		course.setProfessorName(rs.getString(Fields.COURSE_PROFESSOR_NAME));
		course.setProfessorLastName(rs.getString(Fields.COURSE_PROFESSOR_L_NAME));
		course.calculateDuration();
		return course;
	}

	/**
	 * Complete user id`s which picked course.
	 * 
	 * @param courseList
	 *            List of CourseBean`s
	 * @throws DBException
	 */

	private void setStudentsToCourse(List<CourseBean> courseList) throws DBException {
		for (CourseBean c : courseList) {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			Connection con = null;
			try {
				con = ConnectionManager.getInstance().getConnection();
				stmt = con.prepareStatement(SQL_GET_COURSE_STUDENTS);
				stmt.setLong(1, c.getId());
				rs = stmt.executeQuery();
				while (rs.next()) {
					c.insertStudentId(rs.getLong(Fields.RATING_USER_ID));
				}
				c.calculateStudentAmount();
				con.commit();
			} catch (SQLException ex) {
				DBUtils.rollback(con);
				LOG.error(Messages.ERR_CANNOT_SET_USERS_TO_COURSE, ex);
				throw new DBException(Messages.ERR_CANNOT_SET_USERS_TO_COURSE, ex);
			} finally {
				DBUtils.close(con, stmt, rs);
			}
		}
	}

	private void setStudentsToCourse(CourseBean courseBean) throws DBException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			stmt = con.prepareStatement(SQL_GET_COURSE_STUDENTS);
			stmt.setLong(1, courseBean.getId());
			rs = stmt.executeQuery();
			if (rs.next()) {
				courseBean.insertStudentId(rs.getLong(Fields.RATING_USER_ID));
			}
			courseBean.calculateStudentAmount();
			con.commit();
		} catch (SQLException ex) {
			DBUtils.rollback(con);
			LOG.error(Messages.ERR_CANNOT_SET_USERS_TO_COURSE, ex);
			throw new DBException(Messages.ERR_CANNOT_SET_USERS_TO_COURSE, ex);
		} finally {
			DBUtils.close(con, stmt, rs);
		}
	}

	@Override
	public CourseBean getCourseBeanById(long id) throws DBException {
		CourseBean course = new CourseBean();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			stmt = con.prepareStatement(SQL_GET_COURSES_BY_ID);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				extractCourseDto(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			DBUtils.rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
		} finally {
			DBUtils.close(con, stmt, rs);
		}
		setStudentsToCourse(course);
		return course;
	}
}
