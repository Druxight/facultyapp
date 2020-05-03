<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="customTag" tagdir="/WEB-INF/tags"%>
<link rel="stylesheet" href="resources/css/electives.css">
<script src="resources/js/electives.js"></script>
<head>
<title>Electives</title>
</head>
<body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<h3>
		<fmt:message key='electives_jsp.h3.all_electives' />
		:
	</h3>
	<hr>
	<div class="dropdown">
		<button class="dropbtn">
			<fmt:message key='electives_jsp.button.sort' />
		</button>
		<div class="dropdown-content">
			<a href="controller?command=courseSort&type=az"><fmt:message
					key='electives_jsp.button.a.name_az' /></a> <a
				href="controller?command=courseSort&type=za"><fmt:message
					key='electives_jsp.button.a.name_za' /></a> <a
				href="controller?command=courseSort&type=maxDuration"><fmt:message
					key='electives_jsp.button.a.duration_max_min' /></a> <a
				href="controller?command=courseSort&type=minDuration"><fmt:message
					key='electives_jsp.button.a.duration_min_max' /></a> <a
				href="controller?command=courseSort&type=maxStudents"><fmt:message
					key='electives_jsp.button.a.student_amount_max_min' /></a> <a
				href="controller?command=courseSort&type=minStudents"><fmt:message
					key='electives_jsp.button.a.student_amount_min_max' /></a>
		</div>
	</div>

	<div class="dropdown">
		<button class="dropbtn">
			<fmt:message key='electives_jsp.button.subject' />
		</button>
		<div class="dropdown-content">

			<c:forEach var="subject" items="${subjectList}">
				<a
					href="controller?command=courseSort&type=subject&subjectId=${subject.id }">${subject.name }</a>
			</c:forEach>
		</div>
	</div>

	<div class="dropdown">
		<button class="dropbtn">
			<fmt:message key='electives_jsp.button.professor' />
		</button>
		<div class="dropdown-content">

			<c:forEach var="prof" items="${profList}">
				<a
					href="controller?command=courseSort&type=professor&professorId=${prof.id }">${prof.firstName }&nbsp${prof.lastName
					}</a>
			</c:forEach>
		</div>
	</div>

	<a class = "viewAll" href="controller?command=electives&viewType=entirely"><fmt:message key = 'electives_jsp.a.viewAll'/></a>

	<input type="text" id="search" onkeyup="searchName()"
		placeholder="<fmt:message key = 'electives_jsp.input.placeholder.type_a_name'/>">
	<c:choose>
		<c:when test="${fn:length(courseList) == 0}"><fmt:message key = 'electives_jsp.no_courses'/></c:when>
		<c:otherwise>
			<customTag:electivesTable courseList="${courseList }"></customTag:electivesTable>
		</c:otherwise>
	</c:choose>
</body>
</html>