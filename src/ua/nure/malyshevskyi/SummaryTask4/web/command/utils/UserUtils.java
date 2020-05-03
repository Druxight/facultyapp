package ua.nure.malyshevskyi.SummaryTask4.web.command.utils;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.dao.UserDao;
import ua.nure.malyshevskyi.SummaryTask4.dao.impl.DefaultUserDao;
import ua.nure.malyshevskyi.SummaryTask4.db.entity.User;
import ua.nure.malyshevskyi.SummaryTask4.exception.AppException;

public class UserUtils {

	private static final Logger LOG = Logger.getLogger(UserUtils.class);

	public static boolean isEmailAvailable(String email) throws AppException {
		UserDao userDao = DefaultUserDao.getInstance();
		User user = userDao.getUserByEmail(email);
		LOG.trace("Obtained by email: user -->" + user);
		return user == null ? true : false;
	}

	public static boolean isLoginAvailable(String login) throws AppException {
		UserDao userDao = DefaultUserDao.getInstance();
		User user = userDao.getUserByLogin(login);
		LOG.trace("Obtained by login: user -->" + user);
		return user == null ? true : false;
	}

}
