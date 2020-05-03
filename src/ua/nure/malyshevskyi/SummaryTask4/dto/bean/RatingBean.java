package ua.nure.malyshevskyi.SummaryTask4.dto.bean;

public class RatingBean {

	private long id;

	private long userId;

	private long courseId;

	private String userName;

	private String userLastName;

	private int mark;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	@Override
	public String toString() {
		return "RatingBean [id=" + id + ", userId=" + userId + ", courseId=" + courseId + ", userName=" + userName
				+ ", userLastName=" + userLastName + ", mark=" + mark + "]";
	}

}
