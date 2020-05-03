<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<link rel="stylesheet" href="resources/css/auth.css">
<html>
<head>
<title>Login</title>
</head>
<body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<div class="login-page">
		<div class="form">
			<form class="login-form" action="controller" method="post">
				<input type="hidden" name="command" value="login" />
				<p class="message"><fmt:message key = 'login_jsp.form.massage_login_into_account'/></p>
				<input type="text" placeholder="<fmt:message key = 'login_jsp.form.input.placeholder.login'/>" name="login" required /> <input
					type="password" placeholder="<fmt:message key = 'login_jsp.form.input.placeholder.password'/>" name="password" required />
				<button><fmt:message key = 'login_jsp.form.button.sign_in'/></button>
				<c:if test="${not empty errorMessage}">
					<p>
						<c:out value="${errorMessage }" />
					<p>
				</c:if>
				<p class="message">
					<fmt:message key = 'login_jsp.form.p.dont_have_account'/><a href="controller?command=register"><fmt:message key = 'login_jsp.form.p.create_account'/></a>
				</p>
			</form>
		</div>
	</div>
</body>
</html>