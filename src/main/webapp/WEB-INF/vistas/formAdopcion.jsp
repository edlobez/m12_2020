<%-- 
    Document   : formAdopcion
    Created on : 08-Apr-2020, 19:28:14
    Author     : edlobez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="mvc" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link href="../static/resources/imgs/favicon.ico" rel="icon" type="image/x-icon">

        <link href="../static/bootstrap-4.4.1-dist/css/bootstrap.min.css" rel="stylesheet"/>              

        <script src="https://kit.fontawesome.com/42bb3417c7.js" crossorigin="anonymous"></script>
        <script src="../static/js/jquery/jquery-3.3.1.min.js"></script>        
        <script src="../static/js/jquery/jquery-ui.js"></script>
        <script src="../static/bootstrap-4.4.1-dist/js/bootstrap.min.js"></script>

        <title>Formulari d'adopció</title>

    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <a href="${pageContext.servletContext.contextPath}">
                <span class="navbar-brand">
                    <img src="../static/resources/imgs/kitty_ico.png" width="30" height="30" alt="">
                    RescueManagement
                </span>
            </a>
            <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
                <ul class="navbar-nav mr-auto mt-2 mt-lg-0">

                    <!--  <li class="nav-item">
                          <a class="nav-link" href="#">Link</a>
                      </li>
                      <li class="nav-item">
                          <a class="nav-link disabled" href="#">Disabled</a>
                      </li>-->
                </ul>
                <form class="form-inline my-2 my-lg-0">
                    <span><sec:authentication property="principal.username" />&nbsp;&nbsp;&nbsp;</span>
                    <span>
                        <a href="${pageContext.servletContext.contextPath}/logout"><i class="fas fa-sign-out-alt">Sortir</i></a> </span>
                </form>
            </div>
        </nav>
        <div class="container">
            <br>
             <div id="img_container" class="row">
                <div id="myCarousel" class="carousel slide col-md-12" data-ride="carousel">
                    <!-- https://www.w3schools.com/bootstrap/tryit.asp?filename=trybs_carousel2&stacked=h -->
                    <h2>${nombre_animal}</h2>
                    <ol class="carousel-indicators">
                        <c:set var="aux_1" scope="page" value="0"/>
                        <c:forEach var="imagen" items="${imagenes}">
                            <c:set var="clase" scope="page" value=""/> 
                            <c:if test="${aux_1==0}">
                               <c:set var="clase" scope="page" value="active"/> 
                            </c:if>
                            <li data-target="#myCarousel" data-slide-to="${aux_1}" class="${clase}"></li>
                             <c:set var="aux_1" scope="page" value="${aux_1+1}"/>   
                        </c:forEach>
                    </ol>
                    
                    <div class="carousel-inner">                       
                        <c:set var="aux" scope="page" value="${true}"/>
                        <c:forEach var="imagen" items="${imagenes}">
                            <c:set var="clase" scope="page" value="carousel-item"/>
                            <c:if test="${aux}">
                                <c:set var="clase" scope="page" value="carousel-item active"/>
                                <c:set var="aux" scope="page" value="${false}"/>
                            </c:if>
                            <div class="${clase}">
                                <img id="imagen" src='data:${imagen.tipo};base64,${imagen.base64}' class="d-block w-100" style="max-width:15%; max-height:15%;display: block;margin-left: auto;margin-right: auto;" onerror="ocultarImagen();"/>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <mvc:form role="form" id="adopcio-form" autocomplete="off" action="saveAdopcio" modelAttribute="adoptante">  
                <div class="form-header">
                    <h3 class="form-title"><i class="fas fa-dove"></i>
                        Formulari d'adopció
                    </h3>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-4">
                        <label for="nom">Nom</label>
                        <div class="input-group">   
                            <div class="input-group-prepend"><span class="input-group-text"><i class="fas fa-user"></i></span></div>
                            <mvc:input path="nom" type="text" class="form-control" id="nom" placeholder="nom"/>
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                        <label for="cognom1">Cognom 1</label>
                        <div class="input-group">   
                            <div class="input-group-prepend"><span class="input-group-text"><i class="fas fa-user"></i></span></div>
                            <mvc:input path="cognom1" type="text" class="form-control" id="cognom1" placeholder="Cognom 1"/>
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                        <label for="cognom2">Cognom 2</label>
                        <div class="input-group">   
                            <div class="input-group-prepend"><span class="input-group-text"><i class="fas fa-user"></i></span></div>
                            <mvc:input path="cognom2" type="text" class="form-control" id="cognom2" placeholder="Cognom 1"/>
                        </div>
                    </div>
                </div>
                <div class="form-row">
                   <div class="form-group col-md-6">
                        <label for="email">Email</label>
                        <div class="input-group">   
                            <div class="input-group-prepend"><span class="input-group-text"><i class="fas fa-envelope"></i></span></div>
                            <mvc:input path="email" type="email" class="form-control" id="email" placeholder="email"/>
                        </div>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="telefon">Telefon</label>
                        <div class="input-group">   
                            <div class="input-group-prepend"><span class="input-group-text"><i class="fas fa-phone"></i></span></div>
                            <mvc:input path="telefon" type="" class="form-control" id="telefon" placeholder="Telefon"/>
                        </div>
                    </div> 
                </div>
                <div class="form-group">
                    <label for="direccio">Direccio</label>
                    <div class="input-group">   
                        <div class="input-group-prepend"><span class="input-group-text"><i class="fas fa-address-card"></i></span></div>
                        <mvc:input path="direccio" type="text" class="form-control" id="direccio" placeholder="Direccio"/>
                    </div>
                </div>          
                
                <div class="form-footer">                    
                    <button type="submit" class="btn btn-info">Enviar</button>
                    <button type="button" class="btn btn-info" onclick="location.href = '${pageContext.servletContext.contextPath}/animal/animalListDisponible'">Tornar</button>

                </div>
                 <mvc:errors path="*" cssClass="alert alert-danger" element="div"/>   
                 
            </mvc:form>  
            <br><br>
           

        </div>
    </body>
</html>
