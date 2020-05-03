package ua.nure.malyshevskyi.SummaryTask4.db.entity;

public class Rating extends Entity {

	private static final long serialVersionUID = -5021927193825810554L;

	private long userId;

	private long courseId;

	private int mark;

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

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	@Override
	public String toString() {
		return "Journal [userId=" + userId + ", courseId=" + courseId + ", mark=" + mark + ", getId()=" + getId() + "]";
	}

}
