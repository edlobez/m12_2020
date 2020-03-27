<%-- 
    Document   : pruebaImagenVisor
    Created on : 26-Mar-2020, 11:41:40
    Author     : edlobez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Image View</title>
    </head>
    <body>
        <h1>Visor de Imagen</h1>
        <h4>Mostrando la imagen: <i>${nombre}</i></h4>
        <img  src="${pageContext.servletContext.contextPath}/user/uploaded?nombre=${nombre}" />
        

        
    </body>
</html>
