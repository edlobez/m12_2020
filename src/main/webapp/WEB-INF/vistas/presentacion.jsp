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
        <link rel="stylesheet" type="text/css" href="static/css/video.css" />
        <script src="static/js/jquery/jquery-3.3.1.min.js"></script>
        <script src="static/js/jquery/jquery-ui.js"></script>
        <script src="static/js/myjs/myjs.js"></script>
        
        <title>PetManagement</title>
    </head>
    <body>
        <form action="${pageContext.request.contextPath}/logout" method="post">
        <input type="submit" name="logout" value="Desconnectar" />
        </form>
        <div class="video">
            <video controls autoplay width="640" height="480">
                <source src="<c:url value="/static/resources/videos/RescueManagement.mp4"/>">
                <source src="<c:url value="/static/resources/videos/RescueManagement.ogg"/>">
                Tu navegador no soporta HTML5 video.            
            </video>
        </div>
    </body>
</html>
