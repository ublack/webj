<%@ page import="java.nio.file.Paths" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Home</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
        <%
        String mp = System.getProperty("user.home");
            String[] movies = Paths.get(mp + "\\vv\\m3u8").toFile().list();
            request.setAttribute("movies", movies);
        %>

    <c:forEach var="movie" items="${movies}">
        <h1><a href="one.jsp?movie=${movie}">${movie}</a></h1>
        <h5><a href="delete/?movie=${movie}">删除-${movie}</a></h5>
    </c:forEach>

</body>
</html>
