package ua.nure.malyshevskyi.SummaryTask4.web.command.professor;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.Path;
import ua.nure.malyshevskyi.SummaryTask4.db.Role;
import ua.nure.malyshevskyi.SummaryTask4.db.entity.User;
import ua.nure.malyshevskyi.SummaryTask4.dto.bean.CourseBean;
import ua.nure.malyshevskyi.SummaryTask4.dto.impl.DefaultCourseDto;
import ua.nure.malyshevskyi.SummaryTask4.exception.AppException;
import ua.nure.malyshevskyi.SummaryTask4.web.command.Command;

/**
 * 
 * Command gets the necessary data from DB for creating professor page.
 *
 */
public class ProfessorCommand extends Command {

	private static final long serialVersionUID = -8609681854042048223L;

	private static final Logger LOG = Logger.getLogger(ProfessorCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		Object userObj = session.getAttribute("user");
		LOG.trace("Session attribute: user --> " + userObj);
		Object roleObj = session.getAttribute("userRole");
		LOG.trace("Session attribute: userRole --> " + roleObj);

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

		User user = null;
		if (userObj.getClass() == User.class) {
			user = (User) userObj;
		} else {
			throw new AppException("User attribute contains inappropriate information");
		}
		LOG.trace("Obtained user --> " + user);

		List<CourseBean> courseList = DefaultCourseDto.getInstance().getCoursesByProfessor(user.getId());
		LOG.trace("Found in DB: courseList --> " + courseList);

		request.setAttribute("courseList", courseList);
		LOG.trace("Set the request attribute: courseList --> " + courseList);
		LOG.debug("Command finished");
		return Path.PAGE_PROFESSOR;
	}

}
