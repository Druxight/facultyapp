package ua.nure.malyshevskyi.SummaryTask4.web.command.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.Path;
import ua.nure.malyshevskyi.SummaryTask4.dao.impl.DefaultSubjectDao;
import ua.nure.malyshevskyi.SummaryTask4.dao.impl.DefaultUserDao;
import ua.nure.malyshevskyi.SummaryTask4.db.Role;
import ua.nure.malyshevskyi.SummaryTask4.db.entity.Subject;
import ua.nure.malyshevskyi.SummaryTask4.db.entity.User;
import ua.nure.malyshevskyi.SummaryTask4.dto.bean.CourseBean;
import ua.nure.malyshevskyi.SummaryTask4.dto.impl.DefaultCourseDto;
import ua.nure.malyshevskyi.SummaryTask4.exception.AppException;
import ua.nure.malyshevskyi.SummaryTask4.web.command.Command;

/**
 * Command obtains the necessary information from the database to create the
 * electives page. courseList contain courses witch starts wrom current date.
 * User can get courseList with all courses (including finished) by setting
 * "viewType" request attribute to "entirely"
 */

public class ElectiveCommand extends Command {

	private static final long serialVersionUID = -1931635623728051068L;

	private static final Logger LOG = Logger.getLogger(ElectiveCommand.class);

	private static final String PARAM_VIEW_TYPE = "viewType";
	private static final String SHOW_ALL_VIEW_TYPE = "entirely";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();
		String forward = Path.PAGE_ELECTIVES;

		DefaultCourseDto courseDto = DefaultCourseDto.getInstance();
		String showType = request.getParameter(PARAM_VIEW_TYPE);
		List<CourseBean> courseList;
		if (SHOW_ALL_VIEW_TYPE.equals(showType)) {
			courseList = courseDto.getDtoCourses();
		} else {
			courseList = courseDto.getCoursesFromCurrentDate();
		}

		LOG.trace("Course list retrieved --> " + courseList);
		List<Subject> subjectList = DefaultSubjectDao.getInstance().getAllSubjects();
		LOG.trace("Subjects list retrieved --> " + subjectList);
		List<User> userList = DefaultUserDao.getInstance().getUsersByRole(Role.PROFESSOR.ordinal());
		LOG.trace("Professor list retrieved --> " + userList);

		request.setAttribute("courseList", courseList);
		LOG.trace("Set request attribute: courseList --> " + courseList);
		session.setAttribute("subjectList", subjectList);
		LOG.trace("Set session attribute: subjectList --> " + subjectList);
		session.setAttribute("profList", userList);
		LOG.trace("Set session attribute: profList --> " + userList);

		LOG.debug("Command finished");
		return forward;
	}

}
