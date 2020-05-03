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
import ua.nure.malyshevskyi.SummaryTask4.web.command.utils.AdminUtils;

/**
 * Сommand intended for blocking and unblocking a user. During unlock process,
 * the administrator installs user role into userRole parameter. In case of GET
 * method obtain required info for creating jspf page and puts everything in
 * request.
 */

public class BlockUserCommand extends Command {

	private static final long serialVersionUID = -2732647974213763135L;

	private static final Logger LOG = Logger.getLogger(Command.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		AdminUtils.checkAccess(request);

		String forward = Path.PAGE_ADMIN;
		UserDao userDao = DefaultUserDao.getInstance();

		if ("GET".equals(request.getMethod())) {
			LOG.trace("Request --> GET");

			List<User> userList = userDao.getAllUsers();
			LOG.trace("User list obtained: userList");

			request.setAttribute("userList", userList);
			LOG.trace("Set request attribute: userList");

			request.setAttribute("action", "blockUser");
			LOG.trace("Ser request attribute: action --> blockUser");
			LOG.debug("Command finished");
			return forward;
		}

		String userId = request.getParameter("userId");
		LOG.trace("Request parameter: userId --> " + userId);

		String newRole = request.getParameter("newRole");
		LOG.trace("Request parameter: newRole --> " + newRole);

		User user = userDao.getUserById(Long.valueOf(userId));
		LOG.trace("User obtained from DB --> " + user);

		Role userRole = Role.getRole(user);
		LOG.trace("User role -->" + userRole);

		if (userRole != Role.BLOCKED) {

			user.setRoleId(Role.BLOCKED.ordinal());
			LOG.trace("User role set to BLOCKED: user --> " + user);
		} else {
			if ("student".equals(newRole)) {
				user.setRoleId(Role.STUDENT.ordinal());
				LOG.trace("User role set to STUDENT: user --> " + user);
			} else if ("professor".equals(newRole)) {
				user.setRoleId(Role.PROFESSOR.ordinal());
				LOG.trace("User role set to PROFESSOR: user --> " + user);
			}
		}

		userDao.updateUser(user);
		LOG.trace("User updated");

		forward = Path.PAGE_ADMIN;

		LOG.trace("Command finished");

		return forward;
	}

}
