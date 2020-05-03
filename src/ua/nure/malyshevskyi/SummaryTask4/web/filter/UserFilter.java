package ua.nure.malyshevskyi.SummaryTask4.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.Path;
import ua.nure.malyshevskyi.SummaryTask4.db.Role;

/**
 * 
 * Filter check user access to site. If a user is blocked, access to any resource is denied.
 *
 */

public class UserFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(UserFilter.class);

	public void destroy() {
		LOG.debug("Filter destruction starts");
		// no op
		LOG.debug("Filter destruction finished");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		LOG.debug("Filter starts");

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		LOG.trace("Request uri --> " + httpRequest.getRequestURI());

		HttpSession session = httpRequest.getSession();

		Object userRoleObj = session.getAttribute("userRole");
		LOG.trace("Session attribute: userRole --> " + userRoleObj);

		if (userRoleObj == null) {
			LOG.debug("Filter finished");
			chain.doFilter(request, response);
			return;
		}

		Role role = null;
		if (userRoleObj.getClass() == Role.class) {
			role = (Role) userRoleObj;
		}

		if (role == Role.BLOCKED) {
			HttpServletResponse resp = (HttpServletResponse) response;
			request.setAttribute("message", "Your profile is blocked");
			resp.sendRedirect(Path.ERROR_PAGE);
		}

		LOG.debug("Filter finished");
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		LOG.debug("Filter initialization starts");
		// no op
		LOG.debug("Filter initialization finished");
	}

}
