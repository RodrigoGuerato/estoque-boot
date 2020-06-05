<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
	
		<link rel="stylesheet" href="/css/style.css">
	</head>
	<body>
		<c:forEach items="${dados}" var="registro" >
			
		</c:forEach>		
	</body>
</html>