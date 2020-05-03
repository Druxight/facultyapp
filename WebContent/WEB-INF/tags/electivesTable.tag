<%@ attribute name="courseList" type="java.util.Collection"
	required="true"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<%@ taglib prefix="d" uri="/WEB-INF/tld/duration.tld"%>
<table id="mainTable">
	<tr>
		<th><fmt:message key='electives_jsp.table.th.course_name' /></th>
		<th><fmt:message key='electives_jsp.table.th.start_date' /></th>
		<th><fmt:message key='electives_jsp.table.th.end_date' /></th>
		<th><fmt:message key='electives_jsp.table.th.subject' /></th>
		<th><fmt:message key='electives_jsp.table.th.professor' /></th>
		<th><fmt:message key='electives_jsp.table.th.duration' /></th>
		<th><fmt:message key='electives_jsp.table.th.students_amount' /></th>
		<c:choose>
			<c:when test="${userRole.name == 'admin'}">
				<th></th>
			</c:when>
			<c:when test="${userRole.name == 'student' }">
				<th></th>
			</c:when>
		</c:choose>
	</tr>
	<tbody>

		<c:forEach var="course" items="${courseList}">

			<tr>
				<td>${course.name}</td>
				<td>${course.startDate }</td>
				<td>${course.endDate }</td>
				<td>${course.subjectName }</td>
				<td>${course.professorName }&nbsp${course.professorLastName}</td>
				<td>${course.duration }</td>
				<td>${course.studentAmount }</td>

				<c:choose>
					<c:when test="${userRole.name == 'admin'}">
						<td>
							<form action="controller" method="get">
								<input type="hidden" name="command" value="courseCommand" /> <input
									type="hidden" name="operation" value="update" /> <input
									type="hidden" name="courseId" value="${course.id }" />
								<button id="managebtn" type="submit">
									<fmt:message key='electives_jsp.table.td.manage' />
								</button>
							</form>
						</td>
					</c:when>
					<c:when test="${userRole.name == 'student' }">
						<td><d:duration course="${course }">
								<form action="controller" method="post">
									<input type="hidden" name="command" value="setStudentToCourse" />
									<input type="hidden" name="courseId" value="${course.id }" />
									<button id="managebtn" type="submit">
										<fmt:message key='electives_jsp.table.td.enroll' />
									</button>
								</form>
							</d:duration></td>
					</c:when>
				</c:choose>
			</tr>
		</c:forEach>
	</tbody>
</table>