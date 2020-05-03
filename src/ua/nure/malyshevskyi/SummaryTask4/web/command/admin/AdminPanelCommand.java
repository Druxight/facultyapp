package ua.nure.malyshevskyi.SummaryTask4.web.command.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.Path;
import ua.nure.malyshevskyi.SummaryTask4.exception.AppException;
import ua.nure.malyshevskyi.SummaryTask4.web.command.Command;
import ua.nure.malyshevskyi.SummaryTask4.web.command.utils.AdminUtils;

/**
 * Command checks user access to admin panel
 */
public class AdminPanelCommand extends Command {

	private static final long serialVersionUID = 6452207708057113972L;

	private static final Logger LOG = Logger.getLogger(AdminPanelCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		String forward = Path.ERROR_PAGE;

		AdminUtils.checkAccess(request);

		forward = Path.PAGE_ADMIN;

		LOG.debug("Command finished");
		return forward;
	}

}
