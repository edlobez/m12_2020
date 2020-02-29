<%-- 
    Document   : presentacion
    Created on : 27-Feb-2020, 17:34:10
    Author     : edlobez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


        <style type="text/css">
            .video{
                width:640px;
                margin-left: auto;
                margin-right: auto;
                max-width: 100%;
            }            

        </style>

        <title>PetManagement</title>
    </head>
    <body>
        <div class="video">
            <video controls autoplay width="640" height="480">
                <source src="<c:url value="/static/resources/videos/RescueManagement.mp4"/>">
                <source src="<c:url value="/static/resources/videos/RescueManagement.ogg"/>">
                Tu navegador no soporta HTML5 video.            
            </video>
        </div>
        <div id="logout">
            <a href="${pageContext.servletContext.contextPath}/logout">--Logout--</a>
        </div>
    </body>
</html>
