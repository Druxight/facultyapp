package ua.nure.malyshevskyi.SummaryTask4.web.command.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.Path;
import ua.nure.malyshevskyi.SummaryTask4.dao.UserDao;
import ua.nure.malyshevskyi.SummaryTask4.dao.impl.DefaultUserDao;
import ua.nure.malyshevskyi.SummaryTask4.db.entity.User;
import ua.nure.malyshevskyi.SummaryTask4.exception.AppException;
import ua.nure.malyshevskyi.SummaryTask4.web.command.Command;
import ua.nure.malyshevskyi.SummaryTask4.web.command.utils.UserUtils;

/**
 * Command allows the user change profile info.
 */

public class EditProfileCommand extends Command {

	private static final long serialVersionUID = -8876489360379524077L;

	private static final Logger LOG = Logger.getLogger(EditProfileCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		String forward = Path.PAGE_EDIT_PROFILE;

		if ("GET".equals(request.getMethod())) {
			return forward;
		}

		HttpSession session = request.getSession();

		Object userObj = session.getAttribute("user");
		LOG.trace("Session attrubute: user --> " + userObj);

		User user = null;
		if (userObj.getClass() == User.class) {
			user = (User) userObj;
		} else {
			throw new AppException("User attribute is not valid");
		}

		String email = request.getParameter("email");
		LOG.trace("Request parameter: email --> " + email);
		String login = request.getParameter("login");
		LOG.trace("Request parameter: login --> " + login);
		String password = request.getParameter("password");
		LOG.trace("Request parameter: password --> [PASSWORD]");
		String firstName = request.getParameter("firstName");
		LOG.trace("Request parameter: firstName --> " + firstName);
		String lastName = request.getParameter("lastName");
		LOG.trace("Request parameter: lastName --> " + lastName);

		if (!user.getEmail().equals(email)) {
			if (!UserUtils.isEmailAvailable(email)) {
				LOG.trace("Email is already in use: email --> " + email);
				LOG.trace("Command finished");
				request.setAttribute("errorMessage", "email");
				return forward;
			}
		}
		if (!user.getLogin().equals(login)) {
			if (!UserUtils.isLoginAvailable(login)) {
				LOG.trace("Login is already in use: login --> " + login);
				LOG.trace("Command finished");
				request.setAttribute("errorMessage", "login");
				return forward;
			}
		}

		UserDao userDao = DefaultUserDao.getInstance();

		user.setEmail(email);
		user.setLogin(login);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);

		userDao.updateUser(user);

		LOG.trace("User info updated");
		LOG.debug("Command finished");

		return Path.COMMAND_SHOW_PROFILE;
	}

}
