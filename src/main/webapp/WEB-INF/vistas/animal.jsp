<%-- 
    Document   : animal
    Created on : 19-Mar-2020, 10:37:35
    Author     : edlobez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="mvc" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link href="<c:url value="../static/bootstrap-3.3.7-dist/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="../static/bootstrap-3.3.7-dist/css/bootstrap-theme.min.css"/>" rel="stylesheet" type="text/css"/>

        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" 
              integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

        <script src="../static/js/jquery/jquery-3.3.1.min.js"></script>

        <style>
            @font-face {
                font-family: 'Glyphicons Halflings';
                src: url('../fonts/glyphicons-halflings-regular.eot');
                src: url('../fonts/glyphicons-halflings-regular.eot?#iefix') 
                    format('embedded-opentype'), url('../fonts/glyphicons-halflings-regular.woff') 
                    format('woff'), url('../fonts/glyphicons-halflings-regular.ttf') 
                    format('truetype'), url('../fonts/glyphicons-halflings-regular.svg#glyphicons-halflingsregular') 
                    format('svg');
            }
        </style>

        <title>Formulari Animal</title>
    </head>
    <body>  
        <header>            

            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <a href="${pageContext.servletContext.contextPath}">
                            <span class="navbar-brand">                        
                                <img src="../static/resources/imgs/kitty_ico.png" width="30" height="30" alt="">                            
                            </span>
                            <span class="navbar-brand">RescueManagement</span>
                        </a>
                    </div>
                    <form class="nav navbar-nav navbar-right">
                        <span><sec:authentication property="principal.username" />&nbsp;&nbsp;&nbsp;</span>
                        <a href="${pageContext.servletContext.contextPath}/logout"><i class="fas fa-sign-out-alt">Sortir&nbsp;&nbsp;&nbsp;</i></a> 
                    </form>
                </div>
            </nav>


        </header>

        <div class="container">

            <div class="signup-form-container">

                <!-- form start -->
                <mvc:form method="post" role="form" id="register-form" autocomplete="off" 
                          action="saveAnimal" modelAttribute="animal">

                    <div class="form-header">
                        <h3 class="form-title"><i class="fa fa-user"></i>
                            <c:if test="${accion=='update'}">
                                <c:out value="Editar animal"/>
                            </c:if>
                            <c:if test="${accion=='create'}">
                                <c:out value="Crear animal"/>
                            </c:if>                      
                        </h3>

                    </div>

                    <div class="form-body">

                        <div class="row">

                            <div class="form-group col-lg-3">
                                <div class="input-group">
                                    <div class="input-group-addon"><span class="glyphicon glyphicon-user"></span></div>
                                        <mvc:input path="nom" name="nom" type="text" class="form-control" placeholder="Nom" />                                   
                                </div>
                                <span class="help-block" id="error"></span>
                            </div>

                            <div class="form-group col-lg-3">
                                <div class="input-group">
                                    <div class="input-group-addon"><span class="glyphicon glyphicon-user"></span></div>
                                        <mvc:input path="dataNaix" name="dataNaix" type="text" class="form-control" placeholder="Data de naixement (AAAA-MM-DD)" />                                   
                                </div>
                                <span class="help-block" id="error"></span>
                            </div>

                            <div class="form-group col-lg-3">
                                <div class="input-group">
                                    <div class="input-group-addon"><span class="glyphicon glyphicon-user"></span></div>
                                        <mvc:select path="tAnimal" name="tipusAnimal" class="form-control" items="${tAnimal}" />                                   
                                </div>
                                <span class="help-block" id="error"></span>
                            </div>

                            <div class="form-group col-lg-3">
                                <div class="input-group">
                                    <div class="input-group-addon"><span class="glyphicon glyphicon-user"></span></div>
                                        <mvc:select path="laRaza" name="raza" class="form-control" items="${laRaza}" />                                   
                                </div>
                                <span class="help-block" id="error"></span>
                            </div>

                        </div>

                        <br/> <br/>
                        <div class="form-footer">
                            <button type="submit" id="btn_enviar" class="btn btn-info">
                                <span class="glyphicon glyphicon-log-in"></span> Enviar
                            </button>
                            <input type="button" class="btn btn-info" onclick="location.href = '${pageContext.servletContext.contextPath}'"                        
                                   value=' Tornar'/>
                           <!-- <input type="hidden" name="accion" value="${accion}"/> -->


                        </div>

                    </div>

                </mvc:form>

            </div>
        </div>

        <script>

            jQuery(document).ready(function () {

            });





        </script>

    </body>
</html>
