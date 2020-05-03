package ua.nure.malyshevskyi.SummaryTask4.web.command.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.Path;
import ua.nure.malyshevskyi.SummaryTask4.dto.CourseDto;
import ua.nure.malyshevskyi.SummaryTask4.dto.bean.CourseBean;
import ua.nure.malyshevskyi.SummaryTask4.dto.impl.DefaultCourseDto;
import ua.nure.malyshevskyi.SummaryTask4.exception.AppException;
import ua.nure.malyshevskyi.SummaryTask4.web.command.Command;

/**
 * Command for sorting courses from electives page. Obtain type of sorting from
 * request, than sort the list and put it as request attribute.
 */

public class CourseSortingCommand extends Command {

	private static final long serialVersionUID = -9032751649320765228L;

	private static final Logger LOG = Logger.getLogger(CourseSortingCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		String forward = Path.PAGE_ELECTIVES;

		String type = request.getParameter("type");
		LOG.trace("Request parameter: type -->" + type);

		if (type == null) {
			throw new AppException("Sorting type is not specified");
		}

		CourseDto courseDto = DefaultCourseDto.getInstance();
		List<CourseBean> data = courseDto.getCoursesFromCurrentDate();

		switch (type) {
		case "az":
			data.sort((c1, c2) -> c1.getName().compareTo(c2.getName()));
			LOG.trace("List soted by alphabetical order");
			break;
		case "za":
			data.sort((c1, c2) -> -(c1.getName().compareTo(c2.getName())));
			LOG.trace("List sorted by reverse alphabetical order");
			break;
		case "minDuration":
			data.sort((c1, c2) -> -c1.getDuration() - c2.getDuration());
			LOG.trace("List sorted by duration more to less ");
			break;
		case "maxDuration":
			data.sort((c1, c2) -> (c1.getDuration() - c2.getDuration()));
			LOG.trace("List sorted by duration less to more ");
			break;
		case "minStudents":
			data.sort((c1, c2) -> -c1.getStudentAmount() - c2.getStudentAmount());
			LOG.trace("List sorted by students amount more to less ");
			break;
		case "maxStudents":
			data.sort((c1, c2) -> c1.getStudentAmount() - c2.getStudentAmount());
			LOG.trace("List sorted by students amount less to more ");
			break;
		case "subject":
			String subjectId = request.getParameter("subjectId");
			LOG.trace("Request attribute: subjectId --> " + subjectId);
			data = courseDto.getCourseBeanBySubjectId(Long.valueOf(subjectId));
			break;
		case "professor":
			String professorId = request.getParameter("professorId");
			LOG.trace("Request attribute: professorId --> " + professorId);
			data = courseDto.getCoursesByProfessor(Long.valueOf(professorId));
			break;
		default:
			LOG.error("List has not been sorted!");
			break;
		}

		request.setAttribute("courseList", data);
		LOG.trace("Set request attribute: courseList --> " + data);
		LOG.debug("Command finished");
		return forward;
	}

}
