package ua.nure.malyshevskyi.SummaryTask4.web.command.common;

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
 * Command is responsible for entering the profile. Forwards the user to
 * page/command depending on the role.
 */

public class ProfileCommand extends Command {

	private static final long serialVersionUID = 8804344909149991329L;

	private static final Logger LOG = Logger.getLogger(ProfileCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();
		String forward = Path.ERROR_PAGE;

		Object userObj = session.getAttribute("user");
		Object userRoleObj = session.getAttribute("userRole");

		if (userObj == null || userRoleObj == null) {
			forward = Path.COMMAND_LOGIN;
			LOG.debug("Command finished");
			return forward;
		}

		User user = null;
		Role userRole = null;
		if (userObj.getClass() == User.class && userRoleObj.getClass() == Role.class) {
			user = (User) userObj;
			userRole = (Role) userRoleObj;
		} else {
			throw new AppException("Attributes user and userRole are not valid");
		}

		List<CourseBean> courseList = DefaultCourseDto.getInstance().getCoursesByUser(user.getId());
		LOG.trace("Obtained courses by user: courseList");
		session.setAttribute("userCourseList", courseList);
		LOG.trace("Set the session attribute: userCourseList --> " + courseList);
		if (userRole == Role.ADMIN) {
			forward = Path.PAGE_ADMIN;
		}

		if (userRole == Role.PROFESSOR) {
			forward = Path.COMMAND_PROFESSOR;
		}

		if (userRole == Role.STUDENT) {
			forward = Path.COMMAND_COURSE_DATE_SORTING;
		}

		LOG.debug("Command finished");
		return forward;
	}

}
