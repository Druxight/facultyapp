package ua.nure.malyshevskyi.SummaryTask4.dao;

import java.util.List;

import ua.nure.malyshevskyi.SummaryTask4.db.entity.Subject;
import ua.nure.malyshevskyi.SummaryTask4.exception.DBException;

public interface SubjectDao {

	public long insertSubject(Subject subject) throws DBException;

	public List<Subject> getAllSubjects() throws DBException;

	public void updateSubject(Subject subject) throws DBException;

	public long deleteSubject(long subjectId) throws DBException;

}
