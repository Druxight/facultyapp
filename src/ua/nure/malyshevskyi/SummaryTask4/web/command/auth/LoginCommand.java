package ua.nure.malyshevskyi.SummaryTask4.web.command.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.Path;
import ua.nure.malyshevskyi.SummaryTask4.dao.UserDao;
import ua.nure.malyshevskyi.SummaryTask4.dao.impl.DefaultUserDao;
import ua.nure.malyshevskyi.SummaryTask4.db.Role;
import ua.nure.malyshevskyi.SummaryTask4.db.entity.User;
import ua.nure.malyshevskyi.SummaryTask4.exception.AppException;
import ua.nure.malyshevskyi.SummaryTask4.web.command.Command;

/**
 * Command responsible for logging in to the site. In case of invalid info
 * informs the user about mistake.
 */
public class LoginCommand extends Command {

	private static final long serialVersionUID = -7102133854887071699L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		String forward = Path.ERROR_PAGE;

		if ("GET".equals(request.getMethod())) {
			LOG.debug("Command finished");
			return Path.PAGE_LOGIN;
		}

		HttpSession session = request.getSession();

		String login = request.getParameter("login");
		LOG.trace("Requst parameter: login --> " + login);

		String password = request.getParameter("password");
		if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
			throw new AppException("Login or password cannot be empty");
		}

		UserDao userDao = DefaultUserDao.getInstance();
		User user = userDao.getUserByLogin(login);
		LOG.trace("Found in DB: user --> " + user);
		if (user == null || !password.equals(user.getPassword())) {
			LOG.trace("Incorrect login/password");
			request.setAttribute("errorMessage", "Incorrect login/password. Try again.");
			forward = Path.PAGE_LOGIN;
			return forward;
		}

		Role userRole = Role.getRole(user);
		LOG.trace("userRole --> " + userRole);

		forward = Path.COMMAND_SHOW_ELECTIVES;

		if (userRole.equals(Role.BLOCKED)) {
			throw new AppException("Access denied! User blocked --> " + user.getLogin());
		}

		session.setAttribute("user", user);
		LOG.trace("Set the session attribute: user --> " + user);

		session.setAttribute("userRole", userRole);
		LOG.trace("Set the session attribute: userRole --> " + userRole);

		LOG.info("User " + user + " logged as " + userRole.toString().toLowerCase());

		LOG.debug("Command finished");
		return forward;
	}

}
