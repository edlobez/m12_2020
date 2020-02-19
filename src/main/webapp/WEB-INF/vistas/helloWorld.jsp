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
        <h1>Hello World! Primera vista!</h1>
        
        <sec:authorize access="hasAuthority('ADMIN')">
            <br>Este contenido sólo lo ve el admin.
        </sec:authorize>
            
        <sec:authorize access="hasAuthority('TEC')">
            Este contenido sólo lo ve el técnico.
        </sec:authorize>
            
            <sec:authentication property="principal.username" />
            <sec:authentication property="principal.authorities"/>
        
    </body>
</html>
