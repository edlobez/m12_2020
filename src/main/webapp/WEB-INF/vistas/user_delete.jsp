<%-- 
    Document   : newUser
    Created on : 24-Feb-2020, 19:22:35
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
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link href="<c:url value="static/bootstrap-4.4.1-dist/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>

        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" 
              integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">        

        <title>Manteniment d'usuaris</title>
    </head>

    <body>

        <div class="container">

            <div class="signup-form-container">

                <!-- form start -->
                <mvc:form method="post" role="form" id="register-form" autocomplete="off" action="${pageContext.servletContext.contextPath}/admin/saveUser" modelAttribute="usuario">

                    <div class="form-header">
                        <h3 class="form-title"><i class="fa fa-user"></i>
                            <c:if test="${accion=='update'}">
                                <c:out value="Edició d'usuari"/>
                            </c:if>
                            <c:if test="${accion=='create'}">
                                <c:out value="Nou usuari"/>
                            </c:if>                        
                        </h3>

                        <!--      <div class="pull-right">
                                  <h3 class="form-title"><span class="glyphicon glyphicon-pencil"></span></h3>
                              </div>-->

                    </div>

                    <div class="form-body">

                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-addon"></div>
                                    <mvc:input path="username" name="username" type="text" class="form-control" placeholder="Username"/>
                            </div>
                            <span class="help-block" id="error"></span>
                        </div>

                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-addon"></div>
                                    <mvc:input path="email" name="email" type="text" class="form-control" placeholder="Email"/>
                            </div> 
                            <span class="help-block" id="error"></span>                     
                        </div>

                        <div class="row">

                            <div class="form-group col-lg-4">
                                <div class="input-group">
                                    <div class="input-group-addon"></div>
                                        <mvc:input path="nombre" name="nombre" id="nombre" type="text" class="form-control" placeholder="Nombre"/>
                                </div>  
                                <span class="help-block" id="error"></span>                    
                            </div>

                            <div class="form-group col-lg-4">
                                <div class="input-group">
                                    <div class="input-group-addon"></div>
                                        <mvc:input path="apellido1" name="apellido1" type="text" class="form-control" placeholder="Apellido 1"/>
                                </div>  
                                <span class="help-block" id="error"></span>                    
                            </div>

                            <div class="form-group col-lg-4">
                                <div class="input-group">
                                    <div class="input-group-addon"></div>
                                        <mvc:input path="apellido2" name="apellido2" type="text" class="form-control" placeholder="Apellido 2"/>
                                </div>  
                                <span class="help-block" id="error"></span>                    
                            </div>
                        </div>

                        <div class="row">

                            <div class="form-group col-lg-6">
                                <div class="input-group">
                                    <div class="input-group-addon"></div>
                                        <mvc:input path="password" name="password" id="password" type="password" class="form-control" placeholder="Contrasenya"/>
                                </div>  
                                <span class="help-block" id="error"></span>                    
                            </div>

                            <div class="form-group col-lg-6">
                                <div class="input-group">
                                    <div class="input-group-addon"></div>
                                    <input name="cpassword" type="password" class="form-control" placeholder="Confirma contrasenya">
                                </div>  
                                <span class="help-block" id="error"></span>                    
                            </div>

                        </div>

                        <div class="row">

                            <div class="col-lg-6 form-check">
                                Rols: <br/>
                                <c:if test="${not empty listaRoles}">
                                    <c:forEach var="roles" items="${listaRoles}">
                                        <input type="radio" class="form-check-input" name="rol" id="rol" value="${roles.idRol}">
                                        <label class="form-check-label" for="rol">
                                            <c:out value="${roles.rol}" />
                                        </label> 
                                        <br/>
                                    </c:forEach>
                                </c:if>
                            </div>

                            <br/>

                            <div class="col-lg-6 form-group form-check">
                                Tipus d'animals: <br/>
                                <c:if test="${not empty listaTipusAnimal}">
                                    <c:forEach var="tipusAnimal" items="${listaTipusAnimal}">
                                        <input type="checkbox" class="form-check-input" name="tipusAnimal" id="tipusAnimal" value="${tipusAnimal.idTipus}">
                                        <label class="form-check-label" for="tipusAnimal">
                                            <c:out value="${tipusAnimal.descripcio}" />
                                        </label>
                                        <br/>
                                    </c:forEach>
                                </c:if>
                            </div>
                        </div>



                    </div>

                    <div class="form-footer">
                        <br/>
                        <button type="submit" class="btn btn-info">Enviar</button>

                        <input type="hidden" name="accion" value="${accion}"/> 

                        <mvc:errors path="*" cssClass="alert alert-danger" element="div"/>
                        <br>
                        <c:choose>
                            <c:when test="${error=='password_error'}">
                                <br><span class="alert alert-danger">Contrasenyes no coincidents</span>
                            </c:when>                            
                            <c:when test="${error=='username_repetido'}">
                                <br><span class="alert alert-danger">Nom d'usuari ja existent</span>
                            </c:when>
                            <c:when test="${error=='email_repetido'}">
                                <br><span class="alert alert-danger">Email ja existent</span>
                            </c:when>
                        </c:choose>

                        Errores: <br/>
                        <c:if test="${not empty errores}">
                            <c:forEach var="errores" items="${errores}">
                                <c:out value="${errores}" />
                                <br/>
                            </c:forEach>
                        </c:if>
                    </div>


                </mvc:form>

            </div>



        </div>

    </body>
</html>
