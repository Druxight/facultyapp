package ua.nure.malyshevskyi.SummaryTask4.web.command.admin;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.Path;
import ua.nure.malyshevskyi.SummaryTask4.dao.CourseDao;
import ua.nure.malyshevskyi.SummaryTask4.dao.impl.DefaultCourseDao;
import ua.nure.malyshevskyi.SummaryTask4.db.entity.Course;
import ua.nure.malyshevskyi.SummaryTask4.exception.AppException;
import ua.nure.malyshevskyi.SummaryTask4.web.command.Command;
import ua.nure.malyshevskyi.SummaryTask4.web.command.utils.AdminUtils;

public class UpdateCourseCommand extends Command {

	private static final long serialVersionUID = 6458619755457035878L;

	private static final Logger LOG = Logger.getLogger(UpdateCourseCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		AdminUtils.checkAccess(request);

		CourseDao courseDao = DefaultCourseDao.getInstance();

		if ("GET".equals(request.getMethod())) {
			String courseId = request.getParameter("courseId");
			Course course = courseDao.getCourseById(Long.valueOf(courseId));
			LOG.trace("Course obtained from DB: course --> " + course);

			request.setAttribute("course", course);
			LOG.trace("Set request attribute: course");

			LOG.debug("Command finished");
			return Path.PAGE_UPDATE_COURSE;
		}

		String courseId = request.getParameter("crourseId");
		LOG.trace("Request parameter: courseId --> " + courseId);
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
		course.setId(Long.valueOf(courseId));
		course.setName(courseName);
		course.setStartDate(Date.valueOf(startDate));
		course.setEndDate(Date.valueOf(endDate));
		course.setSubjectId(Long.valueOf(subjectId));
		course.setProfessorId(Long.valueOf(profId));
		LOG.trace("Course object created --> " + course);

		courseDao.updateCourse(course);
		LOG.trace("Course was updated");
		LOG.debug("Command finished");
		return Path.COMMAND_SHOW_ELECTIVES;
	}

}
