package ua.nure.malyshevskyi.SummaryTask4.web.command.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.Path;
import ua.nure.malyshevskyi.SummaryTask4.dao.SubjectDao;
import ua.nure.malyshevskyi.SummaryTask4.dao.impl.DefaultSubjectDao;
import ua.nure.malyshevskyi.SummaryTask4.db.entity.Subject;
import ua.nure.malyshevskyi.SummaryTask4.exception.AppException;
import ua.nure.malyshevskyi.SummaryTask4.web.command.Command;
import ua.nure.malyshevskyi.SummaryTask4.web.command.utils.AdminUtils;

/**
 * Subject management. Class can create and delete certain subject. Gets the
 * action command from the request.
 */

public class SubjectManagementCommand extends Command {

	private static final long serialVersionUID = -9144145323068277650L;

	private static final Logger LOG = Logger.getLogger(SubjectManagementCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		SubjectDao subjectDao = DefaultSubjectDao.getInstance();

		AdminUtils.checkAccess(request);

		if ("GET".equals(request.getMethod())) {
			LOG.trace("Request method --> GET");
			List<Subject> subjectList = subjectDao.getAllSubjects();
			LOG.trace("Subject list obtained from db: subjectList --> " + subjectList);
			request.setAttribute("subjectList", subjectList);
			LOG.trace("Set request attribute: subjectList --> " + subjectList);
			request.setAttribute("action", "subjectManagement");
			LOG.trace("Set request attrubute: action --> subjectManagement");
			return Path.PAGE_ADMIN;
		}
		
		AdminUtils.checkAccess(request);
		
		String operation = request.getParameter("operation");
		LOG.trace("Request parameter: operation --> " + operation);
		
		switch (operation) {
		case "create":
			String subjectName = request.getParameter("subjectName");
			LOG.trace("Session parameter: subjectName --> " + subjectName);
			Subject subject = new Subject();
			subject.setName(subjectName);
			LOG.trace("Subject entity created --> " + subject);
			subjectDao.insertSubject(subject);
			LOG.trace("Subject added");
			break;
		case "delete":
			String subjectId = request.getParameter("subjectId");
			LOG.trace("Request parameter: subjectId --> " + subjectId);
			subjectDao.deleteSubject(Long.valueOf(subjectId));
			break;
		default:
			LOG.error("No such operation --> " + operation);
			break;
		}

		LOG.debug("Command finished");

		return Path.PAGE_ADMIN;
	}

}
