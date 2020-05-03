package ua.nure.malyshevskyi.SummaryTask4.dto.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.dao.impl.DefaultRatingDao;
import ua.nure.malyshevskyi.SummaryTask4.db.ConnectionManager;
import ua.nure.malyshevskyi.SummaryTask4.db.DBUtils;
import ua.nure.malyshevskyi.SummaryTask4.dto.FieldsDto;
import ua.nure.malyshevskyi.SummaryTask4.dto.RatingDto;
import ua.nure.malyshevskyi.SummaryTask4.dto.bean.RatingBean;
import ua.nure.malyshevskyi.SummaryTask4.exception.DBException;
import ua.nure.malyshevskyi.SummaryTask4.exception.Messages;

public class DefaultRatingDto implements RatingDto {

	private static final Logger LOG = Logger.getLogger(DefaultRatingDao.class);

	// //////////////////////////////////////////////////////////
	// singleton
	// //////////////////////////////////////////////////////////

	private static DefaultRatingDto instance = null;

	private DefaultRatingDto() {
		// no op
	}

	public static synchronized DefaultRatingDto getInstance() {
		if (instance == null) {
			instance = new DefaultRatingDto();
		}
		return instance;
	}

	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////

	private static final String SQL_GET_RATING_BEAN_BY_ID = "SELECT r.id, u.id AS user_id, c.id AS course_id, u.first_name, u.last_name, r.mark "
			+ "FROM rating r JOIN users u ON r.user_id = u.id JOIN courses c ON c.id = r.course_id WHERE c.id = ? ";

	@Override
	public List<RatingBean> getRatingBeanByCourseId(long id) throws DBException {
		List<RatingBean> ratingBeanList = new ArrayList<RatingBean>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getConnection();
			stmt = con.prepareStatement(SQL_GET_RATING_BEAN_BY_ID);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				ratingBeanList.add(extractRatingDto(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			DBUtils.rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_COURSES, ex);
		} finally {
			DBUtils.close(con, stmt, rs);
		}
		return ratingBeanList;
	}

	private RatingBean extractRatingDto(ResultSet rs) throws SQLException {
		RatingBean ratingBean = new RatingBean();
		ratingBean.setId(rs.getLong(FieldsDto.ENTITY_ID));
		ratingBean.setCourseId(rs.getLong(FieldsDto.COURSE_ID));
		ratingBean.setUserId(rs.getLong(FieldsDto.USER_ID));
		ratingBean.setUserName(rs.getString(FieldsDto.USER_NAME));
		ratingBean.setUserLastName(rs.getString(FieldsDto.USER_LAST_NAME));
		ratingBean.setMark(rs.getInt(FieldsDto.USER_MARK));
		return ratingBean;
	}

}
