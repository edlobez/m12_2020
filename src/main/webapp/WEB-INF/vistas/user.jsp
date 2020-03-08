<%-- 
    Document   : newUser
    Created on : 24-Feb-2020, 19:22:35
    Author     : edlobez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="mvc" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       <!-- <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" 
              rel="stylesheet" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous"> 
        -->
         <link href="<c:url value="../static/css/bootstrap-4.4.1/bootstrap.min.css"/>" rel="stylesheet"/>
        <title>Nuevo usuario</title>
    </head>
    <body>   
        <form action="${pageContext.request.contextPath}/logout" method="post">
        <input type="submit" name="logout" value="Desconnectar" />
        </form>
        <h1>Registro de Usuarios:</h1>
        <h1>${accion}</h1>

        <mvc:form action="${pageContext.servletContext.contextPath}/admin/saveUser" modelAttribute="usuario">  
            
            <mvc:errors path="*" cssClass="alert alert-danger" element="div"/>
            
            <div class="form-group">
            Username:  <mvc:input type="text" class="form-control" path="username"/>
            </div>
       <!--     <mvc:errors path="username" style="color:red"/>-->
            
            <div class="form-group">
            Password: <mvc:input type="password" class="form-control" path="password"/>
            </div>
            
            <div class="form-group">
            Nombre: <mvc:input type="text" class="form-control" path="nombre"/>
              </div>
            
            <div class="form-group">
            Apellido 1: <mvc:input type="text" class="form-control" path="apellido1"/>
              </div>
            
            <div class="form-group">
            Apellido 2: <mvc:input type="text" class="form-control" path="apellido2"/>
              </div>
           
            
            <div class="form-group">
            email: <mvc:input type="text" class="form-control" path="email"/> 
          <!--  <mvc:errors path="email" style="color:red"/>-->
              </div>
            
            <div class="form-group">
            enabled: <mvc:checkbox path="enabled" class="form-control" value="1"/>
              </div>
            
            Rols: <br>
            <input type="radio" name="rol" value="1">
            <label>Admin</label>
            <input type="radio" name="rol" value="2">
            <label>Responsable</label>
            <input type="radio" name="rol" value="3">
            <label>Voluntari</label>
            <input type="radio" name="rol" value="4">
            <label>Veterinari</label>
            
            Tipus d'animals: <br>
            <input type="radio" name="tipusAnimal" value="1">
            <label>Gos</label>
            <input type="radio" name="tipusAnimal" value="2">
            <label>Gat</label>

            <input type="hidden" name="accion" value="${accion}"/> 
            <br/><br/><br/>
            <input type="submit" value="Enviar">
        </mvc:form>        
    </body>
</html>
