<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  isELIgnored="false"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
	<h4>success page</h4>
	time : ${requestScope.time}
	<br>
	<br>
	names : ${requestScope.names}
	
	<br><br>
	request User : ${requestScope.user}
	<br><br>
	session User : ${sessionScope.user}
	
		
	<br><br>
	request School : ${requestScope.School}
	<br><br>
	session School : ${sessionScope.School}
</body>
</html>