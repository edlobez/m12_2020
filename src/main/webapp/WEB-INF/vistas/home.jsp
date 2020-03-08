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
        <link rel="stylesheet" type="text/css" href="static/css/bootstrap-4.4.1/bootstrap.css" />
        <script src="static/js/jquery/jquery-3.3.1.min.js"></script>
        <script src="static/js/jquery/jquery-ui.js"></script>
        <script src="static/js/myjs/myjs.js"></script>
        <title>Rescue Management</title>
    </head>
    <body>
        <h1>Benvingut a RescueManagement</h1>
        <br> 
        <sec:authorize access="hasAuthority('admin')">
            <br>Este contenido sólo lo ve el admin.
        </sec:authorize>

        <sec:authorize access="hasAuthority('user')">
            Este contenido sólo lo ve el usuario.
        </sec:authorize>

        <br>
        <a class="logout-link" href="${pageContext.servletContext.contextPath}/admin">Ir a página del admin</a> 
        <br>
        <%-- Boton a la vista admin 
        <div>
        <form action="${pageContext.request.contextPath}/admin" method="post">
        <button type="button" name="admin" value="Vista admin" />
        </form>
        </div>
        <br>    --%>

        <%-- Boton logout --%>
        <div>
        <form action="${pageContext.request.contextPath}/logout" method="post">
        <input type="submit" name="logout" value="Desconnectar" />
        </form>
        </div>
        <br>
        
        <div>
        <h1>Usuari:</h1>
        <sec:authentication property="principal.username" /><br>
        <sec:authentication property="principal.authorities"/>
        </div>
        <br>
         <%-- logo --%>
         <div class="logo">
             <img src='<c:url value="/static/resources/imgs/kitty.png"></c:url>' />  
            </div>
        
    </body>
</html>
