<%-- 
    Document   : veterinari
    Created on : 02-Mar-2020, 19:40:45
    Author     : edlobez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="${pageContext.request.contextPath}/logout" method="post">
        <input type="submit" name="logout" value="Desconnectar" />
        </form>
        <h1>Vista del veterinari</h1>
        
        <br>
    </body>
</html>
