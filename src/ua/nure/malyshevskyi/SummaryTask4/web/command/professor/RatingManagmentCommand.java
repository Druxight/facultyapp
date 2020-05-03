package ua.nure.malyshevskyi.SummaryTask4.web.command.professor;

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
import ua.nure.malyshevskyi.SummaryTask4.db.Role;
import ua.nure.malyshevskyi.SummaryTask4.db.entity.Rating;
import ua.nure.malyshevskyi.SummaryTask4.dto.RatingDto;
import ua.nure.malyshevskyi.SummaryTask4.dto.bean.RatingBean;
import ua.nure.malyshevskyi.SummaryTask4.dto.impl.DefaultRatingDto;
import ua.nure.malyshevskyi.SummaryTask4.exception.AppException;
import ua.nure.malyshevskyi.SummaryTask4.exception.DBException;
import ua.nure.malyshevskyi.SummaryTask4.web.command.Command;

/**
 * 
 * Command allows Professor to rate users.
 *
 */
public class RatingManagmentCommand extends Command {

	private static final long serialVersionUID = 1273806722496329073L;

	private static final Logger LOG = Logger.getLogger(RatingManagmentCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		String forward = Path.ERROR_PAGE;

		if ("POST".equals(request.getMethod())) {

			long ratingId = Long.valueOf(request.getParameter("ratingId"));
			int mark = Integer.valueOf(request.getParameter("mark"));
			LOG.trace("Rating id and new mark obtained: ratingId --> " + ratingId + ", mark --> " + mark);
			saveRatingChanges(ratingId, mark);
			forward = Path.COMMAND_PROFESSOR;
			LOG.debug("Command finished");

			return forward;
		}

		Object userObj = session.getAttribute("user");
		LOG.trace("Session attribute: user --> " + userObj);
		Object roleObj = session.getAttribute("userRole");
		LOG.trace("Session attribute: userRole --> " + roleObj);

		String courseIdStr = request.getParameter("courseId");
		LOG.trace("Requset parameter: courseId --> " + courseIdStr);

		long courseId = Long.valueOf(courseIdStr);

		if (userObj == null || roleObj == null) {
			throw new AppException("Session does not contain required attributes: user, userRole");
		}

		Role userRole = null;
		if (roleObj.getClass() == Role.class) {
			userRole = (Role) roleObj;
		} else {
			LOG.error("Role attribute is not valid: userRole --> " + roleObj);
			throw new AppException("Role attribute is not valid");
		}

		if (userRole != Role.PROFESSOR && userRole != Role.ADMIN) {
			throw new AppException("Access denied, userRole --> " + userRole);
		}
		LOG.trace("userRole is -->" + userRole);

		RatingDto ratingDto = DefaultRatingDto.getInstance();

		List<RatingBean> ratingBean = ratingDto.getRatingBeanByCourseId(courseId);

		request.setAttribute("ratingBean", ratingBean);
		LOG.trace("Set requset attribute: ratingBean --> " + ratingBean);

		forward = Path.PAGE_RATING_MANAGMENT;

		LOG.debug("Command finished");
		return forward;
	}

	private void saveRatingChanges(long ratingId, int mark) throws DBException {
		RatingDao ratingDao = DefaultRatingDao.getInstance();
		Rating rating = ratingDao.getRatingById(ratingId);
		rating.setMark(mark);
		ratingDao.updateRating(rating);
		LOG.trace("Rating updated by id --> " + ratingId + " new mark --> " + mark);
	}

}
