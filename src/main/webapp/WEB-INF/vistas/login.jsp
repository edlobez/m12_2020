<%-- 
    Document   : login
    Created on : 18-Feb-2020, 15:17:41
    Author     : DAW_M12_grup2
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>



<!DOCTYPE html>
<html lang ="ca">
    <head>
        <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
        <meta name='viewport' content='width=500, initial-scale=1.0, maximum-scale=1.0, user-scalable=no' />
      <link href="<c:url value="static/css/login_11.css"/>"  rel="stylesheet"/>
      <link href="static/resources/imgs/favicon.ico" rel="icon" type="image/x-icon">

       
      
       
        <title>Rescue Management - Login</title>
    </head>
    <body>

        <div class="main">
            <div class="logo">
             <img src='<c:url value="/static/resources/imgs/kitty.png"></c:url>'  alt="logo"/>  
            </div>
            <div class="box">

                <h1 class="title">Iniciar sessió</h1>

                <c:url value="/login" var="loginUrl"/>

                <form action="${loginUrl}" method="post">

                    <c:if test="${param.error != null}">
                      <p class="snackbar">Usuari o contrasenya erronis.</p>
                     
                    </c:if>

                    <c:if test="${param.logout != null}">
                      <p class="snackbar">Sessió tancada correctament.</p>
                    </c:if>

                    <div>
                        <label for="username">Nom</label>
                        <input type="text" id="username" name="username"/>
                    </div>

                    <div>
                        <label for="password">Contrasenya</label>
                        <input type="password" id="password" name="password"/>
                    </div>

                    <button type="submit" class="btn">Accedir</button>
                </form>

            </div>
        </div>

    </body>
</html>