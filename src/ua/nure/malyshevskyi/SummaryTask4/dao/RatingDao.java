package ua.nure.malyshevskyi.SummaryTask4.dao;

import java.util.List;

import ua.nure.malyshevskyi.SummaryTask4.db.entity.Rating;
import ua.nure.malyshevskyi.SummaryTask4.exception.DBException;

public interface RatingDao {
	
	public long insertRating(Rating rating) throws DBException;

	public List<Rating> getRatingByUserId(long id) throws DBException;

	public void updateRating(Rating rating) throws DBException;

	public int deleteRating(Rating rating) throws DBException;

	public List<Rating> getRatingByCourseId(long id) throws DBException;

	public Rating getRatingById(long id) throws DBException;
}
