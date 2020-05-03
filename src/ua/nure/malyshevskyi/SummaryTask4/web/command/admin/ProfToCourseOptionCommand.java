package ua.nure.malyshevskyi.SummaryTask4.web.command.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.Path;
import ua.nure.malyshevskyi.SummaryTask4.dao.CourseDao;
import ua.nure.malyshevskyi.SummaryTask4.dao.UserDao;
import ua.nure.malyshevskyi.SummaryTask4.dao.impl.DefaultCourseDao;
import ua.nure.malyshevskyi.SummaryTask4.dao.impl.DefaultUserDao;
import ua.nure.malyshevskyi.SummaryTask4.db.Role;
import ua.nure.malyshevskyi.SummaryTask4.db.entity.Course;
import ua.nure.malyshevskyi.SummaryTask4.db.entity.User;
import ua.nure.malyshevskyi.SummaryTask4.exception.AppException;
import ua.nure.malyshevskyi.SummaryTask4.web.command.Command;
import ua.nure.malyshevskyi.SummaryTask4.web.command.utils.AdminUtils;

/**
 * Class obtain required info from db to create teacher assignment page.
 */
public class ProfToCourseOptionCommand extends Command {

	private static final long serialVersionUID = -3395540486410129548L;

	private static final Logger LOG = Logger.getLogger(ProfToCourseOptionCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		AdminUtils.checkAccess(request);

		UserDao userDao = DefaultUserDao.getInstance();
		List<User> professorList = userDao.getUsersByRole(Role.PROFESSOR.ordinal());
		LOG.trace("Pofessor list obtained --> " + professorList);

		CourseDao courseDao = DefaultCourseDao.getInstance();
		List<Course> courseList = courseDao.getCourses();
		LOG.trace("Course list obtained --> " + professorList);

		request.setAttribute("professorList", professorList);
		LOG.trace("Set request attribute: professorList --> " + professorList);
		request.setAttribute("courseList", courseList);
		LOG.trace("Set request attribute: courseList --> " + courseList);
		request.setAttribute("action", "profToCourse");
		LOG.trace("Set request attribute: action --> profToCourse");
		LOG.debug("Command finished");
		return Path.PAGE_ADMIN;
	}

}
