<%-- 
    Document   : login
    Created on : 18-Feb-2020, 15:17:41
    Author     : DAW_M12_grup2
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       <link href="static/css/login.css" type="text/css" rel="stylesheet" />
        <title>Login Form</title>
    </head>
    <body>

        <div class="main">
            <div class="box">

                <h1 class="title">Iniciar Sesión</h1>

                <c:url value="/login" var="loginUrl"/>

                <form action="${loginUrl}" method="post">

                    <c:if test="${param.error != null}">
                        <p class="error">Username y password incorrectos, intentalo nuevamente.</p>
                    </c:if>

                    <c:if test="${param.logout != null}">
                        <p class="logout">La sesión ha sido cerrada correctamente.</p>
                    </c:if>

                    <div>
                        <label for="username">Nombre</label>
                        <input type="text" id="username" name="username"/>
                    </div>

                    <div>
                        <label for="password">Contraseña</label>
                        <input type="password" id="password" name="password"/>
                    </div>

                    <button type="submit" class="btn">Log in</button>
                </form>

            </div>
        </div>

    </body>
</html>