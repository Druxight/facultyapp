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
import ua.nure.malyshevskyi.SummaryTask4.web.command.utils.UserUtils;

/**
 * Command responsible for registration. Checks is login or email availiable
 * before register new user.
 */

public class RegisterCommand extends Command {

	private static final int MIN_PASSWORD_SIZE = 8;

	private static final long serialVersionUID = -269154229891057403L;

	private static final Logger LOG = Logger.getLogger(RegisterCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		if ("GET".equals(request.getMethod())) {
			LOG.debug("Command finished");
			return Path.PAGE_REGISTER;
		}

		HttpSession session = request.getSession();
		String forward = Path.PAGE_REGISTER;

		Object userObj = session.getAttribute("user");
		if (userObj != null) {
			LOG.trace("Session was invalidated");
			session.invalidate();
		}
		String email = request.getParameter("email");
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");

		if (login == null || password == null || firstName == null || lastName == null) {
			throw new AppException("Not enought info for registration");
		}

		if (!UserUtils.isLoginAvailable(login)) {
			LOG.trace("Login is already in use!: login -->" + login);
			request.setAttribute("errorMessage", "Login is not available. Try another one.");
			return forward;
		}

		if (!UserUtils.isEmailAvailable(email)) {
			LOG.trace("Email is already in use!: email -->" + email);
			request.setAttribute("errorMessage", "Email is not available. Try another one.");
			return forward;
		}

		if (password.length() < MIN_PASSWORD_SIZE) {
			LOG.trace("Password is not big enough.");
			request.setAttribute("errorMessage", "Password must not be less than 8 characters.");
			return forward;
		}

		// create user
		UserDao userDao = DefaultUserDao.getInstance();

		User user = new User();
		user.setEmail(email);
		user.setLogin(login);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		Role userRole = Role.STUDENT;
		user.setRoleId(userRole.ordinal());
		LOG.trace("User created --> " + user);

		long userId = userDao.insertUser(user);
		user.setId(userId);

		forward = Path.COMMAND_SHOW_ELECTIVES;

		session.setAttribute("user", user);
		LOG.trace("Set the session attribute: user --> " + user);

		session.setAttribute("userRole", userRole);
		LOG.trace("Set the session attribute: userRole --> " + userRole);

		LOG.info("User " + user + " logged as " + userRole.toString().toLowerCase());

		LOG.debug("Command finished");
		return forward;
	}

}
