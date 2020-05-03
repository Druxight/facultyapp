package ua.nure.malyshevskyi.SummaryTask4;

/**
 * Path holder (jsp pages, controller commands).
 * 
 */
public final class Path {

	// pages
	public static final String PAGE_LOGIN = "/login.jsp";
	public static final String PAGE_ELECTIVES = "/WEB-INF/jsp/common/electives.jsp";
	public static final String ERROR_PAGE = "/WEB-INF/jsp/common/error_page.jsp";
	public static final String PAGE_REGISTER = "/WEB-INF/jsp/common/registration.jsp";
	public static final String PAGE_PROFILE = "/WEB-INF/jsp/common/profile.jsp";
	public static final String PAGE_PROFESSOR = "/WEB-INF/jsp/professor/professor_page.jsp";
	public static final String PAGE_RATING_MANAGMENT = "/WEB-INF/jsp/professor/rating_managment.jsp";
	public static final String PAGE_ADMIN = "/WEB-INF/jsp/admin/admin_page.jsp";
	public static final String PAGE_UPDATE_COURSE = "/WEB-INF/jsp/admin/update_course.jsp";
	public static final String PAGE_EDIT_PROFILE = "/WEB-INF/jsp/common/edit_profile.jsp";
	
	// commands
	public static final String COMMAND_SHOW_ELECTIVES = "/controller?command=electives";
	public static final String COMMAND_LOGIN = "/controller?command=login";
	public static final String COMMAND_SHOW_PROFILE = "/controller?command=profile";
	public static final String COMMAND_PROFESSOR = "/controller?command=professor";
	public static final String COMMAND_RATING_MANAGMENT = "/controller?command=ratingManagment";
	public static final String COMMAND_SHOW_PROFESSOR_TO_COURSE = "/controller?command=professorToCourse";
	public static final String COMMAND_COURSE_DATE_SORTING = "/controller?command=filter";
}
