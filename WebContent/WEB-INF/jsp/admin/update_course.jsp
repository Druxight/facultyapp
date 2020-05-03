<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<link rel="stylesheet" href="resources/css/update-course.css">
<html>
<head>
<title>Update course</title>
</head>
<body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<div class="update-page">
		<div class="form">
			<form class="update-form" action="controller" method="post">
				<input type="hidden" name="command" value="courseCommand" /> <input
					type="hidden" name="operation" value="update" /> <input
					type="hidden" name="courseId" value="${course.id }" />
				<p class="message"><fmt:message key = 'update_course_jsp.form.input.p.change_course_info'/></p>
				<p class="course-name">${course.name }</p>
				<input type="hidden" name="operation" value="update" /> <input
					type="hidden" name="courseId" value="${course.id }" /> <a><fmt:message key = 'update_course_jsp.form.a.name'/></a>
				<input type="text" name="courseName" value="${course.name }" />
				<a><fmt:message key = 'update_course_jsp.form.a.start'/></a> <input type="date" name="startDate"
					value="${course.startDate}" /> <a><fmt:message key = 'update_course_jsp.form.a.finish'/></a> <input
					type="date" name="endDate" value="${course.endDate}" /> <a><fmt:message key = 'update_course_jsp.form.a.subject'/></a>
				<span class="custom-dropdown"> <select name="subjectId">
						<c:forEach var="subject" items="${subjectList}">
							<option value="${subject.id }">${subject.name}</option>
						</c:forEach>
				</select>
				</span> <a> <fmt:message key = 'update_course_jsp.form.a.professor'/> </a> <span class="custom-dropdown big"> <select
					name="professorId">
						<c:forEach var="professor" items="${professorList}">
							<option value="${professor.id }">${professor.firstName}&nbsp${professor.lastName
								}</option>
						</c:forEach>
				</select>
				</span>
				<hr>
				<button type="submit"><fmt:message key = 'update_course_jsp.form.button.save'/></button>
			</form>

			<form action="controller" method="post">
				<input type="hidden" name="command" value="courseCommand" /> <input
					type="hidden" name="operation" value="delete" /> <input
					type="hidden" name="courseId" value="${course.id }" />
				<button type="submit"><fmt:message key = 'update_course_jsp.form.button.delete'/></button>
			</form>
		</div>
	</div>



</body>
</html>