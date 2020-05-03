package ua.nure.malyshevskyi.SummaryTask4.web.command.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.db.Role;
import ua.nure.malyshevskyi.SummaryTask4.exception.AppException;

/**
 * Admin utils.
 * Only have method checkAccess(), witch extracts user from the session, 
 * and then checks is user have admin role.
 *  
 */

public class AdminUtils {

	private static final Logger LOG = Logger.getLogger(AdminUtils.class);

	public static void checkAccess(HttpServletRequest request) throws AppException {
		HttpSession session = request.getSession();

		Object userObj = session.getAttribute("user");
		LOG.trace("Session attribute: user --> " + userObj);
		Object roleObj = session.getAttribute("userRole");
		LOG.trace("Session attribute: userRole --> " + roleObj);

		if (userObj == null || roleObj == null) {
			throw new AppException("Session does not contain required attributes: user, userRole");
		}

		Role userRole = null;
		if (roleObj.getClass() == Role.class) {
			userRole = (Role) roleObj;
		} else {
			LOG.error("Role attribute is not valid: userRole --> " + roleObj);
			throw new AppException("Role attribute is not valid");
		}

		if (userRole != Role.ADMIN) {
			LOG.trace("User tried get to admin page: userObj --> " + userObj);
			throw new AppException("Access denied");
		}
	}
}

