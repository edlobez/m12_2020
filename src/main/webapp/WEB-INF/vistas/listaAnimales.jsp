<%-- 
    Document   : veterinari
    Created on : 02-Mar-2020, 19:40:45
    Author     : edlobez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Llistat d'animals</title>
    </head>
    <body>
        <h1>Llistat d' animals</h1>
        <sec:authentication property="principal.username" /><br>
        <sec:authentication property="principal.authorities"/>
        <br>
         <form action="${pageContext.request.contextPath}/logout" method="post">
            <input type="submit" name="logout" value="Desconnectar" />
        </form>
    </body>
</html>
