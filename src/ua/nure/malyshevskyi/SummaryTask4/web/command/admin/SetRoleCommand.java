package ua.nure.malyshevskyi.SummaryTask4.web.command.admin;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.Path;
import ua.nure.malyshevskyi.SummaryTask4.dao.UserDao;
import ua.nure.malyshevskyi.SummaryTask4.dao.impl.DefaultUserDao;
import ua.nure.malyshevskyi.SummaryTask4.db.Role;
import ua.nure.malyshevskyi.SummaryTask4.db.entity.User;
import ua.nure.malyshevskyi.SummaryTask4.exception.AppException;
import ua.nure.malyshevskyi.SummaryTask4.web.command.Command;

public class SetRoleCommand extends Command {

	private static final long serialVersionUID = -4049257050472325476L;

	private static final Logger LOG = Logger.getLogger(SetRoleCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command start");

		UserDao userDao = DefaultUserDao.getInstance();
		String forward = Path.PAGE_ADMIN;

		if ("GET".equals(request.getMethod())) {
			LOG.trace("Request --> GET");

			List<User> userList = userDao.getAllUsers();
			LOG.trace("User list obtained: userList");

			request.setAttribute("userList", userList);
			LOG.trace("Set request attribute: userList");

			request.setAttribute("action", "setRole");
			LOG.trace("Ser request attribute: action --> setRole");
			LOG.debug("Command finished");
			return forward;
		}

		String newRole = request.getParameter("newRole");
		String userId = request.getParameter("userId");

		User user = userDao.getUserById(Long.valueOf(userId));
		LOG.trace("User obtained from DB: user --> " + user);

		switch (newRole) {
		case "admin":
			user.setRoleId(Role.ADMIN.ordinal());
			LOG.trace("Set role --> ADMIN");
			break;
		case "student":
			user.setRoleId(Role.STUDENT.ordinal());
			LOG.trace("Set role --> STUDENT");
			break;
		case "professor":
			user.setRoleId(Role.PROFESSOR.ordinal());
			break;
		default:
			LOG.error("Command not found: newRole --> " + newRole);
			break;
		}

		userDao.updateUser(user);
		LOG.debug("Command finished");
		return forward;
	}

}
