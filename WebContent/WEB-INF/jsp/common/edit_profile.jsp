<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<link rel="stylesheet" href="resources/css/auth.css">
<html>
<head>
<title>Edit profile</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<div class="register-page">
		<div class="form">
			<form class="register-form" action="controller" method="post">
				<input type="hidden" name="command" value="editProfile" />
				<p class="message"><fmt:message key = 'edit_profile_jsp.form.p.change_your_info'/></p>
				<a><fmt:message key = 'edit_profile_jsp.form.a.email'/></a>
				<input type="email" value="${user.email }" name="email" required />
				<a><fmt:message key = 'edit_profile_jsp.form.a.login'/></a>
				<input type="text" value="${user.login }" name="login" required />
				<a><fmt:message key = 'edit_profile_jsp.form.a.first_name'/></a>
				<input type="text" value="${user.firstName}" name="firstName" required />
				<a><fmt:message key = 'edit_profile_jsp.form.a.last_name'/></a>
				<input type="text" value="${user.lastName }" name="lastName" required />
				<a><fmt:message key = 'edit_profile_jsp.form.a.password'/></a>
				<input type="password" value="${user.password }" name="password" required />
				
				<c:choose>
				<c:when test="${errorMessage eq 'login'}">
				<p>
				<fmt:message key = 'edit_profile_jsp.login_in_use'/>
				<p>
				</c:when>
				<c:when test="${errorMessage eq 'email'}">
				<p>
				<fmt:message key = 'edit_profile_jps.email_in_use'/>
				<p>
				</c:when>
				</c:choose>
				<button type="submit"><fmt:message key = 'edit_profile_jsp.form.button.change'/></button>
				<p><a href = "controller?command=profile"><fmt:message key = 'edit_profile_jsp.form.cancel'/></a></p>
			</form>
		</div>
	</div>
</body>

</html>