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
        <!--        
         this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.email = email;-->

        <mvc:form action="${pageContext.servletContext.contextPath}/admin/saveUser" modelAttribute="usuario">                    

            Username:  <mvc:input type="text" path="username"/>
            <br>
            Password: <mvc:input type="password" path="password"/>
            <br>
            Nombre: <mvc:input type="text" path="nombre"/>
            <br>
            Apellido 1: <mvc:input type="text" path="apellido1"/>
            <br>
            Apellido 2: <mvc:input type="text" path="apellido2"/>
            <br>
            email: <mvc:input type="text" path="email"/>    
            <br>
            Roles <br>
            
          
            <br/><br/><br/>
            <input type="submit" value="Enviar">
        </mvc:form>
        
        <br>
        <a class="logout-link" href="${pageContext.servletContext.contextPath}/admin/home">Volver</a> 
    </body>
</html>
