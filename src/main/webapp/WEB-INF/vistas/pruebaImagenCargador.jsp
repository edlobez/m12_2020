<%-- 
    Document   : pruebaImagen
    Created on : 26-Mar-2020, 11:37:11
    Author     : edlobez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Cargador de imagen</h1>

        <form method="post" action="${pageContext.servletContext.contextPath}/user/form" enctype="multipart/form-data">
            <input type="file" name="file" accept="image/jpeg,image/png,image/gif"/>
            <input type="submit" value="Subir archivo"/>
        </form>

        <div style="margin-top: 30px;">
            
            <h3>Imágenes en la base de datos ${titulo}</h3>
            
            <table cellspacing="0" cellpadding="6" border="1">
                <tr bgcolor="grey" style="color: white">
                    <th>No</th>
                    <th>Nombre</th>
                    <th>Tipo</th>
                    <th>Tamaño (Bytes)</th>
                    <th>Ver imagen</th>
                </tr>
                <c:forEach var="img" items="${lista}">
                    <tr>
                        <td><b>${img.id}</b></td>
                        <td>${img.nombre} </td>
                        <td>${img.tipo} </td>
                        <td>${img.tamano} </td>
                        <td><a href="${pageContext.servletContext.contextPath}/user/imagen?nombre=${img.nombre}">ver</a> </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
