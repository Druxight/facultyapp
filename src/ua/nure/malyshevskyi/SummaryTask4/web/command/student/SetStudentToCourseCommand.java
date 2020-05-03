package ua.nure.malyshevskyi.SummaryTask4.web.command.student;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.Path;
import ua.nure.malyshevskyi.SummaryTask4.dao.RatingDao;
import ua.nure.malyshevskyi.SummaryTask4.dao.impl.DefaultRatingDao;
import ua.nure.malyshevskyi.SummaryTask4.db.entity.Rating;
import ua.nure.malyshevskyi.SummaryTask4.db.entity.User;
import ua.nure.malyshevskyi.SummaryTask4.exception.AppException;
import ua.nure.malyshevskyi.SummaryTask4.web.command.Command;

/**
 * Command allows user to sign up for a course. If a user is already enrolled in
 * a course, there is no re-entry.
 */

public class SetStudentToCourseCommand extends Command {

	private static final long serialVersionUID = 6302216613472360259L;

	private static final Logger LOG = Logger.getLogger(SetStudentToCourseCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		String forward = Path.COMMAND_LOGIN;

		HttpSession session = request.getSession();

		String courseId = request.getParameter("courseId");
		LOG.trace("Request parameter: courseId -->" + courseId);

		Object userObj = session.getAttribute("user");
		LOG.trace("Session attribute: user --> " + userObj);

		if (userObj == null) {
			LOG.trace("User didn't log in to the stystem");
			LOG.debug("Command finished");
			return forward;
		}

		User user = null;
		if (userObj.getClass() == User.class) {
			user = (User) userObj;
		} else {
			LOG.error("User attribute contain not valid info");
			throw new AppException("User attribute contain not valid info");
		}

		forward = Path.COMMAND_SHOW_PROFILE;

		RatingDao ratingDao = DefaultRatingDao.getInstance();

		List<Rating> ratingList = ratingDao.getRatingByCourseId(Long.valueOf(courseId));
		LOG.trace("Rating list for course obtained: ratingList -->" + ratingList);
		for (Rating r : ratingList) {
			if (r.getUserId() == user.getId()) {
				LOG.trace("User is already enrolled in the course");
				LOG.debug("Command finished");
				return forward;
			}
		}

		Rating rating = new Rating();
		rating.setCourseId(Long.valueOf(courseId));
		rating.setUserId(user.getId());
		ratingDao.insertRating(rating);
		LOG.trace("New rating record: rating --> " + rating);

		LOG.debug("Command finished");
		return forward;
	}

}
