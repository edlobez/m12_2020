<%-- 
    Document   : 403
    Created on : 25-Mar-2020, 12:02:46
    Author     : edlobez
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="ca">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="static/resources/imgs/favicon.ico" rel="icon" type="image/x-icon">
        <title>RescueManagement</title>
    </head>
    <body>

        <h1>Accés denegat</h1>

        <p><b><sec:authentication property="principal.username"/></b>, No tens permisos per veure aquest contingut.</p>
        
        <form action="${pageContext.servletContext.contextPath}" method="post">
            <input type="submit" value="Volver"/>
           <!-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->
        </form>

    </body>
</html>
