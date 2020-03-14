<%-- 
    Document   : newUser
    Created on : 24-Feb-2020, 19:22:35
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

        <title>Formulario usuario</title>
    </head>
    <body>  
        <!-- amagar checkbox tipusanimal si rol !=  veterinari o responsable -->
        <script type="text/javascript" language="javascript">
            function isVoluntary() {
                let div = document.getElementsByClassName('hideable');
                let radio = document.getElementsByClassName('form-check-input');
                if (radio[0].checked || radio[3].checked) {
                    radio[4].disabled = true;
                    radio[5].disabled = true;
                    radio[4].checked = false;
                    radio[5].checked = false;                  
                } else {
                    radio[4].disabled = false;
                    radio[5].disabled = false;
                }}
        </script>

        <header>            

            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <span class="navbar-brand">
                            <img src="../static/resources/imgs/kitty_ico.png" width="30" height="30" alt="">
                        </span>
                        <span class="navbar-brand">RescueManagement</span>
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
                          action="saveUser" modelAttribute="usuario">

                    <div class="form-header">
                        <h3 class="form-title"><i class="fa fa-user"></i>
                            <c:if test="${accion=='update'}">
                                <c:out value="Editar usuari"/>
                                <c:set var="modificar_username" scope="page" value="${true}"/>
                            </c:if>
                            <c:if test="${accion=='create'}">
                                <c:out value="Crear usuari"/>
                                <c:set var="modificar_username" scope="page" value="${false}"/>
                                <c:set var="_modificar_username" scope="page" value="${true}"/>
                            </c:if>                        
                        </h3>

                        <!--      <div class="pull-right">
                                  <h3 class="form-title"><span class="glyphicon glyphicon-pencil"></span></h3>
                              </div>-->

                    </div>

                    <div class="form-body">

                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-addon"><span class="glyphicon glyphicon-user"></span></div>
                                    <mvc:input path="username" name="username" type="text" class="form-control" placeholder="ID usuari" disabled="${modificar_username}"/>
                                    <mvc:input path="username" name="username" type="hidden" disabled="${_modificar_username}"/>
                            </div>
                            <span class="help-block" id="error"></span>
                        </div>

                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-addon"><span class="glyphicon glyphicon-envelope"></span></div>
                                    <mvc:input path="email" name="email" type="text" class="form-control" placeholder="Email"/>
                            </div> 
                            <span class="help-block" id="error"></span>                     
                        </div>

                        <div class="row">

                            <div class="form-group col-lg-4">
                                <div class="input-group">
                                    <div class="input-group-addon"><span class="glyphicon glyphicon-user"></span></div>
                                        <mvc:input path="nombre" name="nombre" id="nombre" type="text" class="form-control" placeholder="Nom"/>
                                </div>  
                                <span class="help-block" id="error"></span>                    
                            </div>

                            <div class="form-group col-lg-4">
                                <div class="input-group">
                                    <div class="input-group-addon"><span class="glyphicon glyphicon-user"></span></div>
                                        <mvc:input path="apellido1" name="apellido1" type="text" class="form-control" placeholder="Cognom 1"/>
                                </div>  
                                <span class="help-block" id="error"></span>                    
                            </div>

                            <div class="form-group col-lg-4">
                                <div class="input-group">
                                    <div class="input-group-addon"><span class="glyphicon glyphicon-user"></span></div>
                                        <mvc:input path="apellido2" name="apellido2" type="text" class="form-control" placeholder="Cognom 2"/>
                                </div>  
                                <span class="help-block" id="error"></span>                    
                            </div>
                        </div>

                        <div class="row">

                            <div class="form-group col-lg-6">
                                <div class="input-group">
                                    <div class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></div>
                                        <mvc:input path="password" name="password" id="password" type="password" class="form-control" placeholder="Contrasenya"/>
                                </div>  
                                <span class="help-block" id="error"></span>                    
                            </div>

                            <div class="form-group col-lg-6">
                                <div class="input-group">
                                    <div class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></div>
                                    <input name="cpassword" type="password" class="form-control" placeholder="Confirma contrasenya">
                                </div>  
                                <span class="help-block" id="error"></span>                    
                            </div>

                        </div>

                        <sec:authorize access="hasAuthority('admin')">           
                            <div class="form-check">
                                Rols: <br/>
                                <c:if test="${not empty listaRoles}">
                                    <c:forEach var="roles" items="${listaRoles}">
                                        <input type="radio" class="form-check-input" name="rol" id="rol" value="${roles.idRol}" onclick="isVoluntary()">
                                        <label class="form-check-label" for="rol">
                                            <c:out value="${roles.rol}" />
                                        </label>                                    
                                    </c:forEach>
                                </c:if>
                            </div>
                        </sec:authorize>  

                        <sec:authorize access="hasAnyAuthority('admin')">  
                            <br/>


                            <div class="hideable">
                                <div class="form-check">
                                    Tipus d'animals: <br/>
                                    <c:if test="${not empty listaTipusAnimal}">
                                        <c:forEach var="tipusAnimal" items="${listaTipusAnimal}">
                                            <input type="radio" class="form-check-input" name="tipusAnimal" id="tipusAnimal" value="${tipusAnimal.idTipus}">
                                            <label class="form-check-label" for="tipusAnimal">
                                                <c:out value="${tipusAnimal.descripcio}" />
                                            </label>
                                        </c:forEach>
                                    </c:if>
                                </div>
                            </div>
                        </sec:authorize>

                        <br/> <br/>
                        <div class="form-footer">
                            <button type="submit" id="btn_enviar" class="btn btn-info">
                                <span class="glyphicon glyphicon-log-in"></span> Enviar
                            </button>
                            <input type="button" class="btn btn-info" onclick="location.href = '${pageContext.servletContext.contextPath}'"                        
                                   value=' Tornar'/>
                            <input type="hidden" name="accion" value="${accion}"/> 

                            <mvc:errors path="*" cssClass="alert alert-danger" element="div"/>
                            <br>
                            <c:choose>
                                <c:when test="${error=='password_error'}">
                                    <br><span class="alert alert-danger">Les contrasenyes no son coincidents</span>
                                </c:when>
                                <c:when test="${error=='password_error_long'}">
                                    <br><span class="alert alert-danger">Les contrasenyes no son coincidents</span>
                                </c:when>
                                <c:when test="${error=='username_repetido'}">
                                    <br><span class="alert alert-danger">Nom d'usuari ja existent</span>
                                </c:when>
                                <c:when test="${error=='email_repetido'}">
                                    <br><span class="alert alert-danger">Email ja existent</span>
                                </c:when>
                                <c:when test="${error=='error_update'}">
                                    <br><span class="alert alert-danger">Error al modificar usuari</span>
                                </c:when>
                                <c:when test="${error=='error_create'}">
                                    <br><span class="alert alert-danger">Error al crear usuari</span>
                                </c:when>
                                <c:when test="${error=='error_rol'}">
                                    <br><span class="alert alert-danger">És obligatori seleccionar un rol</span>
                                </c:when>
                                <c:when test="${error=='error_tAnimal'}">
                                    <br><span class="alert alert-danger">Es obligatori triar un tipus d'animal per aquest rol</span>
                                </c:when>
                            </c:choose>
                        </div>

                    </div>

                </mvc:form>

            </div>
        </div>          

    </body>
</html>
