package ua.nure.malyshevskyi.SummaryTask4.web.command.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.Path;
import ua.nure.malyshevskyi.SummaryTask4.exception.AppException;
import ua.nure.malyshevskyi.SummaryTask4.web.command.Command;

/**
 * Command responsible for log out from the site.
 */

public class LogoutCommand extends Command {

	private static final long serialVersionUID = 7470580999696761360L;

	private static final Logger LOG = Logger.getLogger(LogoutCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
			LOG.trace("Session invalidated");
		}

		LOG.debug("Command finished");
		return Path.COMMAND_SHOW_ELECTIVES;

	}

}
