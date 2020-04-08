<%-- 
    Document   : formAdopcion
    Created on : 08-Apr-2020, 19:28:14
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
        <h1>Hello World!</h1>
        
        <form action="${pageContext.servletContext.contextPath}" method="post">
            <input type="submit" value="Volver"/>
           <!-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->
        </form>
    </body>
</html>
