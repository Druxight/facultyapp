<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<link rel="stylesheet" href="resources/css/profile.css">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>My Profile</title>
<style>
</style>
</head>
<body>

	<%@ include file="/WEB-INF/jspf/header.jspf"%>

	<div class="wrapper">
		<div class="left_block">
			<div class="form">

				<input type="hidden" name="command" value="changeProf" />
				<p class="message">
					<fmt:message key='profile_jsp.p.your_profile' />
				</p>
				<img src="resources/images/user.png">
				<p>@${user.login }</p>
				<p class="fieldName">
					<fmt:message key='profile_jsp.p.name' />
					: <a>${user.firstName }&nbsp${user.lastName }</a>
				</p>
				<p class="fieldName">
					<fmt:message key='profile_jsp.p.email' />
					: <a>${user.email }</a>
				</p>
				<p class="fieldName">
					<fmt:message key='profile_jsp.p.completed_courses' />
					: <a>${finishedCoursesAmount }</a>
				</p>
				<form action="controller" method="get">
					<input type="hidden" name="command" value="editProfile" />
					<button type="submit">
						<fmt:message key='profile_jsp.button.change_info' />
					</button>
				</form>
			</div>

		</div>
		<div class="client_right_block">
			<p>
				<fmt:message key='profile_jsp.p.your_courses' />
				:
			</p>
			<hr>
			<p class="table-name">
				<fmt:message key='profile_jsp.p.future' />
			</p>
			<c:choose>
				<c:when test="${fn:length(futureList) == 0}">
					<p>
						<fmt:message key='profile_jsp.p.no_courses' />
					</p>
				</c:when>
				<c:otherwise>


					<c:forEach var="course" items="${futureList}">
						<div class='courseinf'>
							<div class='cdata'>
								<form action="controller" method="post">
									<input type="hidden" name="command" value="dropOut" /> <input
										type="hidden" name="courseId" value="${course.id }" />
									<button class="dropoutbtn" type="submit">
										<fmt:message key='profile_jsp.button.drop_out' />
									</button>
								</form>
								<a id="cname">${course.name}</a>
								<hr>
								<p>
									<fmt:message key='profile_jps.subject' />
									: ${course.subjectName }
								</p>
								<p>
									<fmt:message key='profile_jps.professor' />
									: ${course.professorName }&nbsp${course.professorLastName }
								</p>
								<p>
									<fmt:message key='profile_jps.start' />
									: ${course.startDate }&nbsp
									<fmt:message key='profile_jps.end' />
									: ${course.endDate
									} &nbsp
									<fmt:message key='profile_jps.duration' />
									: ${course.duration }
								</p>
							</div>

						</div>

					</c:forEach>

				</c:otherwise>






			</c:choose>

			<hr>
			<p class="table-name">
				<fmt:message key='profile_jps.p.progress' />
			</p>
			<c:choose>
				<c:when test="${fn:length(progressList) == 0}">
					<p>
						<fmt:message key='profile_jsp.p.no_courses' />
					</p>
				</c:when>
				<c:otherwise>

					<c:forEach var="course" items="${progressList}">
						<div class='courseinf'>
							<div class='cdata'>
								<form action="controller" method="post">
									<input type="hidden" name="command" value="dropOut" /> <input
										type="hidden" name="courseId" value="${course.id }" />
								</form>
								<a id="cname">${course.name}</a>
								<hr>
								<p>
									<fmt:message key='profile_jps.subject' />
									: ${course.subjectName }
								</p>
								<p>
									<fmt:message key='profile_jps.professor' />
									: ${course.professorName }&nbsp${course.professorLastName }
								</p>
								<p>
									<fmt:message key='profile_jps.start' />
									: ${course.startDate }&nbsp
									<fmt:message key='profile_jps.end' />
									: ${course.endDate
									} &nbsp
									<fmt:message key='profile_jps.duration' />
									: ${course.duration }
								</p>
							</div>

						</div>

					</c:forEach>
				</c:otherwise>
			</c:choose>
			<hr>
			<p class="table-name">
				<fmt:message key='profile_jsp.p.completed' />
			</p>
			<c:choose>
				<c:when test="${ empty finishedMap}">
					<p>
						<fmt:message key='profile_jsp.p.no_courses' />
					</p>
				</c:when>
				<c:otherwise>
	
	
	<c:forEach var="type" items="${finishedMap}">
						<div class='courseinf'>
							<div class='cdata'>
								<form action="controller" method="post">
									<input type="hidden" name="command" value="dropOut" /> <input
										type="hidden" name="courseId" value="${course.id }" />
								</form>
								<a id="cname">${type.key.name}</a> <a id = "mark"><fmt:message key='profile_jsp.mark' />:${type.value }</a>
								<hr>
								<p>
									<fmt:message key='profile_jps.subject' />
									: ${type.key.subjectName }
								</p>
								<p>
									<fmt:message key='profile_jps.professor' />
									: ${type.key.professorName }&nbsp${type.key.professorLastName }
								</p>
								<p>
									<fmt:message key='profile_jps.start' />
									: ${type.key.startDate }&nbsp
									<fmt:message key='profile_jps.end' />
									: ${type.key.endDate } &nbsp
									<fmt:message key='profile_jps.duration' />
									: ${type.key.duration }
								</p>
							</div>

						</div>

					</c:forEach>
	
				</c:otherwise>
			</c:choose>

		</div>
	</div>
</body>
</html>