package ua.nure.malyshevskyi.SummaryTask4.dto;

import java.util.List;

import ua.nure.malyshevskyi.SummaryTask4.dto.bean.CourseBean;
import ua.nure.malyshevskyi.SummaryTask4.exception.DBException;

public interface CourseDto {

	List<CourseBean> getDtoCourses() throws DBException;
	
	List<CourseBean> getCoursesFromCurrentDate() throws DBException;

	List<CourseBean> getCoursesByProfessor(long professorId) throws DBException;

	List<CourseBean> getCoursesByUser(long userId) throws DBException;

	List<CourseBean> getCourseBeanBySubjectId(long id) throws DBException;

	CourseBean getCourseBeanById(long id) throws DBException;

}
