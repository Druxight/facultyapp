package ua.nure.malyshevskyi.SummaryTask4.dto.bean;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class CourseBean {

	private long id;

	private String name;

	private Date startDate;

	private Date endDate;

	private long subjectId;

	private String subjectName;

	private long professorId;

	private String professorName;

	private String professorLastName;

	private int duration;

	private List<Long> studentsId;

	private int studentAmount;

	public CourseBean() {
		studentsId = new ArrayList<>();
	}

	public List<Long> getStudentsId() {
		return studentsId;
	}

	public void setStudentsId(List<Long> studentsId) {
		this.studentsId = studentsId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getStudentAmount() {
		return studentAmount;
	}

	public void setStudentAmount(int studentAmount) {
		this.studentAmount = studentAmount;
	}

	public void insertStudentId(long id) {
		studentsId.add(id);
	}

	public void deleteStudentId(long id) {
		studentsId.remove(id);
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public long getProfessorId() {
		return professorId;
	}

	public void setProfessorId(long professorId) {
		this.professorId = professorId;
	}

	public String getProfessorName() {
		return professorName;
	}

	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}

	public String getProfessorLastName() {
		return professorLastName;
	}

	public void setProfessorLastName(String professorLastName) {
		this.professorLastName = professorLastName;
	}

	public void calculateDuration() {
		long diff = endDate.getTime() - startDate.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		duration = (int) diffDays;
	}

	public void calculateStudentAmount() {
		studentAmount = studentsId.size();
	}

	@Override
	public String toString() {
		return "Course [name=" + name + ", startDate=" + startDate + ", endDate=" + endDate + ", subjectId=" + subjectId
				+ ", subjectName=" + subjectName + ", professorId=" + professorId + ", professorName=" + professorName
				+ ", professorLastName=" + professorLastName + "]";
	}

}
