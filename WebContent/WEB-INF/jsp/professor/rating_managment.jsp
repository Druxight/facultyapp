<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<link rel="stylesheet" href="resources/css/profile.css">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MyProfile</title>
<style>
</style>
</head>
<body>

	<%@ include file="/WEB-INF/jspf/header.jspf"%>

	<div class="wrapper">
		<div class="left_block">
			<div class="form">

				<input type="hidden" name="command" value="changeProf" />
				<p class="message"><fmt:message key = 'rating_managment_jsp.your_profile'/></p>
				<img src="resources/images/user.png">
				<p>@${user.login }</p>
				<p class="fieldName">
					<fmt:message key = 'rating_managment_jsp.name'/>: <a>${user.firstName }&nbsp${user.lastName }</a>
				</p>
				<p class="fieldName">
					<fmt:message key = 'rating_managment_jsp.email'/>: <a>${user.email }</a>
				</p>
				<form action="controller" method="get">
					<input type="hidden" name="command" value="editProfile" />
					<button type="submit"><fmt:message key = 'rating_managment_jsp.change_info'/></button>
				</form>
			</div>

		</div>
		<div class="right_block">
			<table id="mainTable">


				<c:choose>
					<c:when test="${fn:length(ratingBean) == 0}">No students</c:when>

					<c:otherwise>

						<table id="mainTable">
							<thead>
								<tr>
									<th><fmt:message key = 'rating_managment_jsp.student'/></th>
									<th><fmt:message key = 'rating_managment_jsp.mark'/></th>
								</tr>
							</thead>
							<tbody>

								<c:forEach var="rating" items="${ratingBean}">

									<tr>
										<td>${rating.userName }&nbsp${rating.userLastName }</td>
										<td><form action="controller" method="post">
												<input type="hidden" name="command" value="ratingManagment" />
												<input type="hidden" name="ratingId" value=${rating.id } />
												<input type="text" name="mark" value="${rating.mark }" />
												<button class="savebtn" value="submit"><fmt:message key = 'rating_managment_jsp.save'/></button>
											</form></td>
									</tr>

								</c:forEach>

							</tbody>
						</table>
					</c:otherwise>
				</c:choose>

			</table>
		</div>
	</div>
</body>
</html>