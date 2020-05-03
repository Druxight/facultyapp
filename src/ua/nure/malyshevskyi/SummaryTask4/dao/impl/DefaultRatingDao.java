package ua.nure.malyshevskyi.SummaryTask4.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.dao.RatingDao;
import ua.nure.malyshevskyi.SummaryTask4.db.ConnectionManager;
import ua.nure.malyshevskyi.SummaryTask4.db.DBUtils;
import ua.nure.malyshevskyi.SummaryTask4.db.Fields;
import ua.nure.malyshevskyi.SummaryTask4.db.entity.Rating;
import ua.nure.malyshevskyi.SummaryTask4.exception.DBException;
import ua.nure.malyshevskyi.SummaryTask4.exception.Messages;

public class DefaultRatingDao implements RatingDao {

	private static final Logger LOG = Logger.getLogger(DefaultRatingDao.class);

	// //////////////////////////////////////////////////////////
	// singleton
	// //////////////////////////////////////////////////////////

	private static DefaultRatingDao instance = null;

	private DefaultRatingDao() {
		// no op
	}

	public static synchronized DefaultRatingDao getInstance() {
		if (instance == null) {
			instance = new DefaultRatingDao();
		}
		return instance;
	}

	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////

	private static final String SQL_INSERT_RATING = "INSERT INTO rating VALUES (DEFAULT, ? , ? , DEFAULT)";

	private static final String SQL_GET_RATING_BY_USER_ID = "SELECT * FROM rating WHERE user_id = ? ";

	private static final String SQL_GET_RATING_BY_COURSE_ID = "SELECT * FROM rating WHERE course_id = ? ";

	private static final String SQL_UPDATE_RATING = "UPDATE rating SET user_id = ? , course_id = ? , mark = ? WHERE id = ?";

	private static final String SQL_DELETE_RATING = "DELETE FROM rating WHERE id = ?";

	private static final String SQL_GET_RATING_BY_ID = "SELECT * FROM rating WHERE id = ?";

	/**
	 * Save the rating to database.
	 * 
	 * @return Rating id
	 * @throws DBException
	 */
	@Override
	public long insertRating(Rating rating) throws DBException {
		Connection con = null;
		long id = 0;
		try {
			con = ConnectionManager.getInstance().getConnection();
			id = insertRating(con, rating);
			con.commit();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_INSERT_RATING, ex);
			throw new DBException(Messages.ERR_CANNOT_INSERT_RATING, ex);
		} finally {
			DBUtils.close(con);
		}
		return id;
	}

	/**
	 * Returns list of rating by course id.
	 * 
	 * @return List of category entities.
	 * @throws DBException
	 */
	@Override
	public List<Rating> getRatingByCourseId(long id) throws DBException {
		List<Rating> ratingList = new ArrayList<Rating>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_GET_RATING_BY_COURSE_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ratingList.add(extractRating(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBUtils.rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_RATING, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_RATING, ex);
		} finally {
			DBUtils.close(con, pstmt, rs);
		}
		return ratingList;
	}

	/**
	 * Returns list of rating.
	 * 
	 * @return List of category entities.
	 * @throws DBException
	 */
	@Override
	public List<Rating> getRatingByUserId(long id) throws DBException {
		List<Rating> ratingList = new ArrayList<Rating>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_GET_RATING_BY_USER_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ratingList.add(extractRating(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBUtils.rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_RATING, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_RATING, ex);
		} finally {
			DBUtils.close(con, pstmt, rs);
		}
		return ratingList;
	}

	/**
	 * Returns rating by id.
	 * 
	 * @return Rating entity
	 * @throws DBException
	 */
	@Override
	public Rating getRatingById(long id) throws DBException {
		Rating rating = new Rating();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_GET_RATING_BY_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				rating = extractRating(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_RATING, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_RATING, ex);
		} finally {
			DBUtils.close(con, pstmt, rs);
		}
		return rating;
	}

	/**
	 * Update rating in database.
	 * 
	 * @throws DBException
	 */
	@Override
	public void updateRating(Rating rating) throws DBException {
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			updateRating(con, rating);
			con.commit();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_UPDATE_RATING, ex);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_RATING, ex);
		} finally {
			DBUtils.close(con);
		}
	}

	/**
	 * Delete rating from database.
	 * 
	 * @param rating
	 *            Rating entity
	 * @return Affected rows.
	 * @throws DBException
	 */
	@Override
	public int deleteRating(Rating rating) throws DBException {
		int affectedRows = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_RATING);
			pstmt.setLong(1, rating.getId());
			affectedRows = pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_DELETE_RATING, ex);
			throw new DBException(Messages.ERR_CANNOT_DELETE_RATING, ex);
		} finally {
			DBUtils.close(pstmt);
			DBUtils.close(con);
		}
		return affectedRows;
	}

	/**
	 * Insert rating.
	 * 
	 * @param rating
	 *            Rating entity to insert
	 * @throws SQLException
	 */
	private long insertRating(Connection con, Rating rating) throws SQLException {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		long id = 0;
		try {
			pstmt = con.prepareStatement(SQL_INSERT_RATING, Statement.RETURN_GENERATED_KEYS);
			int k = 1;
			pstmt.setLong(k++, rating.getUserId());
			pstmt.setLong(k++, rating.getCourseId());

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
	 * Extract rating entity form ResultSet.
	 * 
	 * @param rs
	 *            ResultSet with required info.
	 * @throws SQLException
	 */
	private Rating extractRating(ResultSet rs) throws SQLException {
		Rating rating = new Rating();
		rating.setId(rs.getLong(Fields.ENTITY_ID));
		rating.setUserId(rs.getLong(Fields.RATING_USER_ID));
		rating.setCourseId(rs.getLong(Fields.RATING_COURSE_ID));
		rating.setMark(rs.getInt(Fields.RATING_MARK));
		return rating;
	}

	/**
	 * Update rating.
	 * 
	 * @param rating
	 *            Rating entity.
	 * @throws SQLException
	 */
	private void updateRating(Connection con, Rating rating) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_RATING);
			int k = 1;
			pstmt.setLong(k++, rating.getUserId());
			pstmt.setLong(k++, rating.getCourseId());
			pstmt.setLong(k++, rating.getMark());
			pstmt.setLong(k, rating.getId());
			pstmt.executeUpdate();
			con.commit();
		} finally {
			DBUtils.close(pstmt);
		}
	}
}
