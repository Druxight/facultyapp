<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<link rel="stylesheet" href="resources/css/profile.css">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Page</title>
<style>
</style>
</head>
<body>

	<%@ include file="/WEB-INF/jspf/header.jspf"%>

	<div class="wrapper">
		<div class="left_block">
			<div class="form">

				<input type="hidden" name="command" value="changeProf" />
				<p class="message"><fmt:message key = 'admin_jsp.form.p.your_profile'/></p>
				<img src="resources/images/user.png">
				<p>@${user.login }</p>
				<p class="fieldName">
					<fmt:message key = 'admin_jsp.form.p.name'/>:<a>${user.firstName }&nbsp${user.lastName }</a>
				</p>
				<p class="fieldName">
					<fmt:message key = 'admin_jsp.form.p.email'/>: <a>${user.email }</a>
				</p>
				<form action="controller" method="get">
					<input type="hidden" name="command" value="editProfile" />
					<button type="submit"><fmt:message key = 'admin_jsp.form.p.button.change_info'/></button>
				</form>
			</div>

		</div>
		<div class="right_block">
			<div class="form">
				<form action="controller" method="get">
					<input type="hidden" name="command" value="professorToCourse" />
					<button type="submit"><fmt:message key = 'admin_jsp.form.p.button.set_course_to_profile'/></button>
				</form>

				<form action="controller" method="get">
					<input type="hidden" name="command" value="courseCommand" /> <input
						type="hidden" name="operation" value="create" />
					<button type="submit"><fmt:message key = 'admin_jsp.form.p.button.create_course'/></button>
				</form>

				<form action="controller" method="get">
					<input type="hidden" name="command" value="blockUser" />
					<button type="submit"><fmt:message key = 'admin_jsp.form.p.button.block_user'/></button>
				</form>
				
				<form action="controller" method="get">
					<input type="hidden" name="command" value="subjectManagment" />
					<button type="submit"><fmt:message key = 'admin_jsp.form.p.button.subject_managment'/></button>
				</form>
				
			</div>

		</div>
		
		
		<c:choose>
		<c:when test="${action eq 'profToCourse'}">
			<div class="course_to_prof">
				<%@ include file="/WEB-INF/jspf/prof_to_course.jspf"%>
			</div>
		</c:when>

		<c:when test="${action eq 'createCourse'}">
			<div class="create_course">
				<%@ include file="/WEB-INF/jspf/create_course.jspf"%>
			</div>
		</c:when>

		<c:when test="${action eq 'blockUser'}">
			<div class="block_user">
				<%@ include file="/WEB-INF/jspf/block_user.jspf"%>
			</div>
		</c:when>
		
		<c:when test="${action eq 'subjectManagment'}">
			<div class="subject_managment">
				<%@ include file="/WEB-INF/jspf/subject_managment.jspf"%>
			</div>
		</c:when>
		
		</c:choose>
	</div>
</body>
</html>