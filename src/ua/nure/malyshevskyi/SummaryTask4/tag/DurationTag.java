package ua.nure.malyshevskyi.SummaryTask4.tag;

import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import ua.nure.malyshevskyi.SummaryTask4.dto.bean.CourseBean;

public class DurationTag extends TagSupport {

	private static final long serialVersionUID = 7099965317874048041L;

	private CourseBean course;

	public void setCourse(CourseBean course) {
		this.course = course;
	}

	public int doStartTag() throws JspException {
		Date date = new Date();
		long diff = date.getTime() - course.getStartDate().getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		return diffDays < 0 ? EVAL_PAGE : SKIP_BODY;
	}
}