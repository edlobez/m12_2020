<%-- 
    Document   : newUser
    Created on : 24-Feb-2020, 19:22:35
    Author     : edlobez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="mvc" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" 
              rel="stylesheet" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous"> 
        
        <title>Nuevo usuario</title>
    </head>
    <body>        
        <h1>Registro de Usuarios:</h1>

        <mvc:form action="${pageContext.servletContext.contextPath}/admin/saveUser" >                    

          
            <br/><br/><br/>
            <input type="submit" value="Enviar">
        </mvc:form>
        
        <br>
        <a class="logout-link" href="${pageContext.servletContext.contextPath}/admin/home">Volver</a> 
    </body>
</html>
