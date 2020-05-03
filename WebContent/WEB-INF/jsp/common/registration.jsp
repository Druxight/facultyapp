<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<link rel="stylesheet" href="resources/css/auth.css">
<html>
<head>
<title>Registration</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<div class="register-page">
		<div class="form">
			<form class="register-form" action="controller" method="post">
				<input type="hidden" name="command" value="register" />
				<p class="message"><fmt:message key = 'registration_jsp.form.p.registration'/></p>
				<input type="email" placeholder="<fmt:message key = 'registration_jsp.form.input.paceholder.email'/>" name="email" required />
				<input type="text" placeholder="<fmt:message key = 'registration_jsp.form.input.paceholder.login'/>" name="login" required />
				<input type="text" placeholder ="<fmt:message key = 'registration_jsp.form.input.paceholder.first_name'/>" name="firstName" required />
				<input type="text" placeholder ="<fmt:message key = 'registration_jsp.form.input.paceholder.last_name'/>" name="lastName" required />
				<input type="password" placeholder="<fmt:message key = 'registration_jsp.form.input.paceholder.password'/>" name="password" required />
				<button type="submit" ><fmt:message key = 'registration_jsp.form.button.create'/></button>
				<c:if test="${not empty errorMessage}">
					<p>
					<fmt:message key = 'registration_jsp.p.incorrect_data'/>
					<p>
				</c:if>
				<p class="message">
					<fmt:message key = 'registration_jsp.form.p.already_registered'/>?  <a href="controller?command=login"><fmt:message key = 'registration_jsp.form.href.sign_in'/></a>
				</p>
			</form>
		</div>
	</div>
</body>

</html>