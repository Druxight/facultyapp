package ua.nure.malyshevskyi.SummaryTask4.dao;

import java.util.List;

import ua.nure.malyshevskyi.SummaryTask4.db.entity.Course;
import ua.nure.malyshevskyi.SummaryTask4.exception.DBException;

public interface CourseDao {

	public long insertCourse(Course course) throws DBException;

	public void updateCourse(Course course) throws DBException;

	public List<Course> getCourses() throws DBException;

	public List<Course> getCoursesBySubject(long subjectId) throws DBException;

	public int deleteCourseById(long courseId) throws DBException;

	public List<Course> getCoursesByProfessor(long professorId) throws DBException;

	public Course getCourseById(long id) throws DBException;

}
