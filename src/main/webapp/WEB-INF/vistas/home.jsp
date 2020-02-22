<%-- 
    Document   : helloWorld
    Created on : 17-Feb-2020, 15:44:35
    Author     : edlobez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Página de cualquier usuario logeado!</h1>
        <br>
        <sec:authorize access="hasAuthority('admin')">
            <br>Este contenido sólo lo ve el admin.
        </sec:authorize>

        <sec:authorize access="hasAuthority('user')">
            Este contenido sólo lo ve el usuario.
        </sec:authorize>

        <br>
        <a class="logout-link" href="${pageContext.servletContext.contextPath}/admin/home">Ir a página del admin</a> 
        <br>
        <a class="logout-link" href="${pageContext.servletContext.contextPath}/logout">logout</a>

        <h1>Usuario</h1>
        <sec:authentication property="principal.username" /><br>
        <sec:authentication property="principal.authorities"/>

    </body>
</html>
