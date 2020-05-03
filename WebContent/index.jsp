<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>HELLO</title>
</head>
<body>

	<form action="controller" method="post">
		<input type="hidden" name="command" value="login" />
		<div class="container">
			<label for="uname"><b>Username</b></label> <input type="text"
				placeholder="Enter Username" name="login" required> <label
				for="psw"><b>Password</b></label> <input type="password"
				placeholder="Enter Password" name="password" required>
			<button type="submit">Login</button>
		</div>

	</form>

</body>
</html>