package ua.nure.malyshevskyi.SummaryTask4.web.command.common;

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
 * Command allows the user unsubscribe from a course which has not started yet.
 */

public class DropOutCommand extends Command {

	private static final long serialVersionUID = 8738414463459883535L;

	private static final Logger LOG = Logger.getLogger(DropOutCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		Object userObj = session.getAttribute("user");
		LOG.trace("Session attribute: user --> " + userObj);

		String courseId = request.getParameter("courseId");
		LOG.trace("Request parameter: courseId --> " + courseId);

		if (userObj == null) {
			LOG.debug("Command finished");
			return Path.COMMAND_LOGIN;
		}

		User user = null;
		if (userObj.getClass() == User.class) {
			user = (User) userObj;
		} else {
			throw new AppException("User attribute contains not valid info: user --> " + userObj);
		}

		RatingDao ratingDao = DefaultRatingDao.getInstance();

		List<Rating> ratingList = ratingDao.getRatingByCourseId(Long.valueOf(courseId));
		LOG.trace("Rating list obtained: ratingList --> " + ratingList);

		for (Rating r : ratingList) {
			if (r.getUserId() == user.getId()) {
				ratingDao.deleteRating(r);
				break;
			}
		}

		return Path.COMMAND_SHOW_PROFILE;
	}

}
