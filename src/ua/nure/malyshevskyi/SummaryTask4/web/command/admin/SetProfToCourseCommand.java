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
import ua.nure.malyshevskyi.SummaryTask4.dto.CourseDto;
import ua.nure.malyshevskyi.SummaryTask4.dto.bean.CourseBean;
import ua.nure.malyshevskyi.SummaryTask4.dto.impl.DefaultCourseDto;
import ua.nure.malyshevskyi.SummaryTask4.exception.AppException;
import ua.nure.malyshevskyi.SummaryTask4.web.command.Command;
import ua.nure.malyshevskyi.SummaryTask4.web.command.utils.AdminUtils;

/**
 * Command appoints a professor to lead a specific course.
 */

public class SetProfToCourseCommand extends Command {

	private static final long serialVersionUID = -6959221271574932944L;

	public static final Logger LOG = Logger.getLogger(SetProfToCourseCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		AdminUtils.checkAccess(request);

		UserDao userDao = DefaultUserDao.getInstance();
		List<User> professorList = userDao.getUsersByRole(Role.PROFESSOR.ordinal());
		LOG.trace("Pofessor list obtained --> " + professorList);
		request.setAttribute("professorList", professorList);
		LOG.trace("Set request attribute: professorList --> " + professorList);

		CourseDto courseDto = DefaultCourseDto.getInstance();

		List<CourseBean> courseList = courseDto.getDtoCourses();

		request.setAttribute("courseList", courseList);

		if ("GET".equals(request.getMethod())) {
			request.setAttribute("action", "profToCourse");
			LOG.trace("Set request attribute: action --> profToCourse");
			return Path.PAGE_ADMIN;
		}

		String courseId = request.getParameter("courseId");
		String professorId = request.getParameter("professorId");

		if (courseId == null || professorId == null) {
			LOG.trace("Not enought parameters: courseId/professorId");
			throw new AppException("Not enought parameters: courseId/professorId");
		}

		if (courseId.length() == 0 && professorId.length() == 0) {
			LOG.trace("Obtained empty parameters: courseId/professorId");
			throw new AppException("Required parameters not valid");
		}

		CourseDao courseDao = DefaultCourseDao.getInstance();

		Course course = courseDao.getCourseById(Long.valueOf(courseId));
		LOG.trace("Course obtained from db --> " + course);

		course.setProfessorId(Long.valueOf(professorId));
		LOG.trace("Set professorId: professorId --> " + professorId);

		courseDao.updateCourse(course);
		LOG.trace("Course updated");

		// FINISH IT
		LOG.debug("Command finished");

		return Path.PAGE_ADMIN;
	}

}
