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
        
        <title>Rescue Management</title>
    </head>
    <body>
        <h1>Benvingut a RescueManagement</h1>
        <br> 
        <div>
            <h1>Usuari:</h1>
            <sec:authentication property="principal.username" /><br>
            <sec:authentication property="principal.authorities"/>
        </div>
        
        <sec:authorize access="hasAuthority('admin')">   
            <h1>Sección del administrador</h1>
            <a class="logout-link" href="${pageContext.servletContext.contextPath}/admin/users">Gestión Usuarios</a>
            <br>
            <a class="logout-link" href="${pageContext.servletContext.contextPath}/admin/pets">Gestión Animales</a>
            <br>
        </sec:authorize>

        <sec:authorize access="hasAuthority('responsable')">
            <h1>Sección del responsable</h1>
            <a class="logout-link" href="${pageContext.servletContext.contextPath}/responsable/home">Gestionar animales</a> 
            <br>
        </sec:authorize>
            
        <sec:authorize access="hasAuthority('veterinari')">
            <h1>Sección del veterinari</h1>
            <a class="logout-link" href="${pageContext.servletContext.contextPath}/veterinari/home">Ir a página del veterinari</a> 
            <br>
        </sec:authorize>
            
        <sec:authorize access="hasAuthority('voluntari')">
            <h1>Sección del voluntari</h1>
            <a class="logout-link" href="${pageContext.servletContext.contextPath}/voluntari/home">Ir a página del voluntari</a> 
            <br>
        </sec:authorize>
            


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
     
        <%-- logo --%>
        <div class="logo">
            <img src='<c:url value="/static/resources/imgs/kitty.png"></c:url>' />  
        </div>

    </body>
</html>
