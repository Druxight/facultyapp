package ua.nure.malyshevskyi.SummaryTask4.web.command.admin;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.Path;
import ua.nure.malyshevskyi.SummaryTask4.dao.CourseDao;
import ua.nure.malyshevskyi.SummaryTask4.dao.SubjectDao;
import ua.nure.malyshevskyi.SummaryTask4.dao.UserDao;
import ua.nure.malyshevskyi.SummaryTask4.dao.impl.DefaultCourseDao;
import ua.nure.malyshevskyi.SummaryTask4.dao.impl.DefaultSubjectDao;
import ua.nure.malyshevskyi.SummaryTask4.dao.impl.DefaultUserDao;
import ua.nure.malyshevskyi.SummaryTask4.db.Role;
import ua.nure.malyshevskyi.SummaryTask4.db.entity.Course;
import ua.nure.malyshevskyi.SummaryTask4.db.entity.Subject;
import ua.nure.malyshevskyi.SummaryTask4.db.entity.User;
import ua.nure.malyshevskyi.SummaryTask4.exception.AppException;
import ua.nure.malyshevskyi.SummaryTask4.web.command.Command;
import ua.nure.malyshevskyi.SummaryTask4.web.command.utils.AdminUtils;

/**
 * Course managment. Class can create, update and delete certain course. Gets
 * the action command from the request.
 */

public class CourseManagmentCommand extends Command {

	private static final long serialVersionUID = -6917987877403902022L;

	private static final Logger LOG = Logger.getLogger(CourseManagmentCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		AdminUtils.checkAccess(request);

		String forward = Path.PAGE_ADMIN;

		String operation = request.getParameter("operation");
		LOG.trace("Request parameter: operation --> " + operation);

		if ("GET".equals(request.getMethod())) {
			LOG.trace("Request method --> GET");

			SubjectDao subjectDao = DefaultSubjectDao.getInstance();
			UserDao userDao = DefaultUserDao.getInstance();

			List<Subject> subjects = subjectDao.getAllSubjects();
			LOG.trace("Subjects list obtained from db --> " + subjects);
			List<User> professors = userDao.getUsersByRole(Role.PROFESSOR.ordinal());
			LOG.trace("Professors list obtained from db --> " + professors);

			request.setAttribute("subjectList", subjects);
			LOG.trace("Set request attribute: subjectList");
			request.setAttribute("professorList", professors);
			LOG.trace("Set request attribute: professorList");

			if ("update".equals(operation)) {
				CourseDao courseDao = DefaultCourseDao.getInstance();
				String courseId = request.getParameter("courseId");
				LOG.trace("Request parameter: courseId -->" + courseId);
				Course course = courseDao.getCourseById(Long.valueOf(courseId));
				LOG.trace("Course obtained from DB: course --> " + course);

				request.setAttribute("course", course);
				LOG.trace("Set request attribute: course");
				forward = Path.PAGE_UPDATE_COURSE;
				LOG.debug("Command finished");
				return forward;
			}

			request.setAttribute("action", "createCourse");
			LOG.trace("Set request atrribute: action --> createCourse");
			LOG.debug("Command finished");
			forward = Path.PAGE_ADMIN;
			return forward;
		}

		CourseDao courseDao = DefaultCourseDao.getInstance();

		if ("delete".equals(operation)) {
			String courseId = request.getParameter("courseId");
			int affectedRows = courseDao.deleteCourseById(Long.valueOf(courseId));
			if (affectedRows > 0) {
				LOG.trace("Course deleted");
			} else {
				LOG.error("Course was not deleted!: courseId --> " + courseId);
			}
			forward = Path.COMMAND_SHOW_ELECTIVES;
			LOG.debug("Command finished");
			return forward;
		}

		String courseName = request.getParameter("courseName");
		LOG.trace("Request parameter: courseName --> " + courseName);
		String startDate = request.getParameter("startDate");
		LOG.trace("Request parameter: startDate --> " + startDate);
		String endDate = request.getParameter("endDate");
		LOG.trace("Request parameter: startDate --> " + endDate);
		String subjectId = request.getParameter("subjectId");
		LOG.trace("Request parameter: subjectId -->" + subjectId);
		String profId = request.getParameter("professorId");
		LOG.trace("Request parameter: professorId --> " + profId);

		Course course = new Course();

		course.setName(courseName);
		course.setStartDate(Date.valueOf(startDate));
		course.setEndDate(Date.valueOf(endDate));
		course.setSubjectId(Long.valueOf(subjectId));
		course.setProfessorId(Long.valueOf(profId));
		LOG.trace("Course object created --> " + course);

		switch (operation) {
		case "create":
			courseDao.insertCourse(course);
			LOG.trace("Course was inserted into DB");
			forward = Path.PAGE_ADMIN;
			break;
		case "update":
			String courseId = request.getParameter("courseId");
			LOG.trace("Request parameter: courseId --> " + courseId);
			course.setId(Long.valueOf(courseId));
			courseDao.updateCourse(course);
			LOG.trace("Course was updated");
			forward = Path.COMMAND_SHOW_ELECTIVES;
			break;
		default:
			break;
		}

		LOG.debug("Command finished");
		return forward;
	}

}
