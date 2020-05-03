package ua.nure.malyshevskyi.SummaryTask4.web.command.student;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.malyshevskyi.SummaryTask4.Path;
import ua.nure.malyshevskyi.SummaryTask4.dao.RatingDao;
import ua.nure.malyshevskyi.SummaryTask4.dao.impl.DefaultRatingDao;
import ua.nure.malyshevskyi.SummaryTask4.db.entity.Rating;
import ua.nure.malyshevskyi.SummaryTask4.db.entity.User;
import ua.nure.malyshevskyi.SummaryTask4.dto.bean.CourseBean;
import ua.nure.malyshevskyi.SummaryTask4.exception.AppException;
import ua.nure.malyshevskyi.SummaryTask4.web.command.Command;

/**
 * Main data manager for Student profile.
 * Obtain and sort courses for a specific user.
 */
public class CourseDateSortingCommand extends Command {

	private static final long serialVersionUID = 4016631355477892068L;

	private static final Logger LOG = Logger.getLogger(CourseDateSortingCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		
		Object userObj = session.getAttribute("user");
		LOG.trace("Session attribute: user -->" + userObj);
		
		User user = null;
		if(userObj.getClass() == User.class){
			user = (User)userObj;
		} else {
			throw new AppException("User attribute is not valid");
		}

		Object courseListObj = session.getAttribute("userCourseList");
		List<CourseBean> data = null;
		if (courseListObj != null) {
			data = cast(courseListObj);
		} else {
			throw new AppException("Received information is not valid");
		}

		LOG.trace("Current time: " + LocalDateTime.now());
		List<CourseBean> futureCourseList;
		List<CourseBean> progressCourseList;
		List<CourseBean> finishedCourseList;
		futureCourseList = getFutureCourses(data);
		progressCourseList = getCoursesInProgress(data);
		finishedCourseList = getCompletedCourses(data);
		
		RatingDao ratingDao = DefaultRatingDao.getInstance();
		List<Rating> ratingList = ratingDao.getRatingByUserId(user.getId());
		
		int finishedCourses = 0;
		Map<CourseBean, Integer> finishedCourseMap = new HashMap<>();
		for(int i = 0; i<finishedCourseList.size(); i++){
			CourseBean currentCourse = finishedCourseList.get(i);
			for(int j = 0; j<ratingList.size(); j++){
				Rating currentRating = ratingList.get(j);
				if(currentCourse.getId() == currentRating.getCourseId()){
					finishedCourseMap.put(currentCourse, currentRating.getMark());
					finishedCourses++;
				}
			}
		}
		
		session.removeAttribute("filtredUserCourseList");
		
		request.setAttribute("futureList", futureCourseList);
		LOG.trace("Set request attribute: futureList --> " + futureCourseList);
		request.setAttribute("progressList", progressCourseList);
		LOG.trace("Set request attribute: progressList --> " + progressCourseList);
		request.setAttribute("finishedMap", finishedCourseMap);
		LOG.trace("Set request attribute: finishedMap --> " + finishedCourseMap);
		request.setAttribute("finishedCoursesAmount", finishedCourses);
		LOG.trace("Set request attribute: finishedCoursesAmount --> " + finishedCourses);
		LOG.trace("Command finished");
		return Path.PAGE_PROFILE;
	}

	private static List<CourseBean> getFutureCourses(List<CourseBean> data) {
		List<CourseBean> filtredList = new ArrayList<>();
		long now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		for (CourseBean c : data) {
			long startDate = c.getStartDate().getTime();
			if (now < startDate) {
				filtredList.add(c);
			}
		}
		return filtredList;
	}

	private static List<CourseBean> getCoursesInProgress(List<CourseBean> data) {
		List<CourseBean> filtredList = new ArrayList<>();
		long now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		for (CourseBean c : data) {
			long startDate = c.getStartDate().getTime();
			long endDate = c.getEndDate().getTime();
			if (now > startDate && now < endDate) {
				filtredList.add(c);
			}
		}
		return filtredList;
	}

	private static List<CourseBean> getCompletedCourses(List<CourseBean> data) {
		List<CourseBean> filtredList = new ArrayList<>();
		long now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		for (CourseBean c : data) {
			long endDate = c.getEndDate().getTime();
			if (now > endDate) {
				filtredList.add(c);
			}
		}
		return filtredList;
	}

	@SuppressWarnings("unchecked")
	private static <T extends List<?>> T cast(Object obj) {
		return (T) obj;
	}

}
