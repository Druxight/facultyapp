<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<link rel="stylesheet" href="resources/css/profile.css">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Profile</title>
<style>
</style>
</head>
<body>

	<%@ include file="/WEB-INF/jspf/header.jspf"%>

	<div class="wrapper">
		<div class="left_block">
			<div class="form">

				<input type="hidden" name="command" value="changeProf" />
				<p class="message"><fmt:message key = 'professor_page_jsp.p.your_profile'/></p>
				<img src="resources/images/user.png">
				<p>@${user.login }</p>
				<p class="fieldName">
					<fmt:message key = 'professor_page_jsp.p.name'/>: <a>${user.firstName }&nbsp${user.lastName }</a>
				</p>
				<p class="fieldName">
					<fmt:message key = 'professor_page_jsp.p.email'/>: <a>${user.email }</a>
				</p>
				<form action="controller" method="get">
					<input type="hidden" name="command" value="editProfile" />
					<button type="submit"><fmt:message key = 'professor_page_jsp.button.change_info'/></button>
				</form>
			</div>

		</div>
		<div class="client_right_block">
			<p><fmt:message key = 'professor_page_jsp.p.your_courses'/>:</p>
			<hr>
			<c:choose>
				<c:when test="${fn:length(courseList) == 0}"><fmt:message key = 'professor_page_jsp.p.no_courses'/></c:when>
				<c:otherwise>
					<table id="mainTable">

						<tr>
							<th><fmt:message key = 'professor_page_jsp.table.th.name'/></th>
							<th><fmt:message key = 'professor_page_jsp.table.th.start'/></th>
							<th><fmt:message key = 'professor_page_jsp.table.th.end'/></th>
							<th><fmt:message key = 'professor_page_jsp.table.th.subject'/></th>
							<th><fmt:message key = 'professor_page_jsp.table.th.duration'/></th>
							<th><fmt:message key = 'professor_page_jsp.table.th.amount'/></th>
							<th></th>
						</tr>

						<c:forEach var="course" items="${courseList}">

							<tr>
								<td>${course.name}</td>
								<td>${course.startDate }</td>
								<td>${course.endDate }</td>
								<td>${course.subjectName }</td>
								<td>${course.duration }</td>
								<td>${course.studentAmount }</td>
								<td><form action="controller" method="get">
										<input type="hidden" name="command" value="ratingManagment" />
										<input type="hidden" name="courseId" value="${course.id}" />
										<input class="managebtn" type="submit" value="<fmt:message key = 'professor_page_jsp.button.manage'/>">
									</form></td>
							</tr>

						</c:forEach>

					</table>

				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>