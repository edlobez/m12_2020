<%-- 
    Document   : animal
    Created on : 19-Mar-2020, 10:37:35
    Author     : edlobez
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

        <script src="https://kit.fontawesome.com/42bb3417c7.js" crossorigin="anonymous"></script>

        <script src="../static/js/jquery/jquery-3.3.1.min.js"></script>


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
                          action="saveAnimal" modelAttribute="animal" methodParam="post">

                    <div class="form-header">
                        <h3 class="form-title"><i class="fas fa-paw"></i>
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
                                <mvc:label path="nom" for="nom">Nom</mvc:label>
                                    <div class="input-group">                                  
                                        <div class="input-group-addon"><i class="fas fa-cat"></i></div>                                                                     
                                        <mvc:input path="nom" name="nom" type="text" class="form-control" placeholder="Nom" />                                   
                                </div>
                                <span class="help-block" id="error"></span>
                            </div>

                            <div class="form-group col-lg-3">
                                <mvc:label path="dataNaix" for="dataNaix">Data de naixement</mvc:label>
                                    <div class="input-group">
                                        <div class="input-group-addon"><i class="far fa-calendar-alt"></i></div>
                                        <mvc:input path="dataNaix" name="dataNaix" type="date" class="form-control" placeholder="Data de naixement (AAAA-MM-DD)" />    

                                </div>
                                <span class="help-block" id="error"></span>
                            </div>

                            <div class="form-group col-lg-3">
                                <mvc:label path="tAnimal" for="tAnimal">Tipus d'animal</mvc:label>
                                    <div class="input-group">
                                        <div class="input-group-addon"><i class="fas fa-paw"></i></div>
                                        <mvc:select path="tAnimal" name="tipusAnimal" class="form-control" items="${tAnimal}" />                                   
                                </div>
                                <span class="help-block" id="error"></span>
                            </div>

                            <div class="form-group col-lg-3">
                                <mvc:label path="laRaza" for="laRaza">Raza</mvc:label>
                                    <div class="input-group">
                                        <div class="input-group-addon"><i class="fas fa-dog"></i></div>
                                        <mvc:select path="laRaza" name="raza" class="form-control" items="${laRaza}" />                                   
                                </div>
                                <span class="help-block" id="error"></span>
                            </div>

                        </div>

                        <div class ='row'>
                            <div class="form-group col-lg-3">
                                <mvc:label path="sexe" for="sexe">Sexe</mvc:label>
                                    <div class="input-group">                                  
                                        <div class="input-group-addon"><i class="fas fa-kiwi-bird"></i></div> 
                                        <mvc:select path="sexe" class="form-control" name="sexe" multiple="false">
                                            <mvc:option value="0" class="form-control" label="Mascle"></mvc:option>
                                            <mvc:option value="1" class="form-control" label="Femella"></mvc:option>
                                        </mvc:select>
                                </div>
                                <span class="help-block" id="error"></span>
                            </div>
                            <div class="form-group col-lg-3">
                                <mvc:label path="tamany" for="tamany">Tamany</mvc:label>
                                    <div class="input-group">                                  
                                        <div class="input-group-addon"><i class="fas fa-hippo"></i></div>                                                                     
                                        <!--  <mvc:input path="tamany" name="tamany" type="text" class="form-control" placeholder="tamany" />-->
                                    <mvc:select path="tamany" class="form-control" name="tamany" multiple="false">
                                        <mvc:option value="molt petit" class="form-control" label="Molt petit"></mvc:option>
                                        <mvc:option value="petit" class="form-control" label="Petit"></mvc:option>
                                        <mvc:option value="mitja" class="form-control" label="Mitjà"></mvc:option>
                                        <mvc:option value="gran" class="form-control" label="Gran"></mvc:option>
                                        <mvc:option value="molt gran" class="form-control" label="Molt gran"></mvc:option>

                                    </mvc:select>
                                </div>
                                <span class="help-block" id="error"></span>
                            </div>

                            <div class="form-group col-lg-3">
                                <mvc:label path="veterinari" for="veterinar">Veterinari</mvc:label>
                                    <div class="input-group">                                  
                                        <div class="input-group-addon"><i class="fas fa-user-md"></i></div>                                                                     
                                        <mvc:select path="veterinari" name="veterinari" class="form-control" items="${vet}" /> 

                                </div>
                                <span class="help-block" id="error"></span>
                            </div>

                        </div> 

                        <div class="row">

                            <div class="form-check col-lg-3">
                                <mvc:label path="isVacunat" for="isVacunat"><i class="fas fa-syringe"></i>&nbsp;Està vacunat?</mvc:label>
                                    <br/>                                       
                                <mvc:radiobutton path="isVacunat" name="isVacunat" class="form-check-input" value="0" />
                                <mvc:label path="isVacunat" class="form-check-label">No</mvc:label>
                                <mvc:radiobutton path="isVacunat" name="isVacunat" class="form-check-input" value="1" />
                                <mvc:label path="isVacunat" class="form-check-label">Sí</mvc:label>
                                    <span class="help-block" id="error"></span>
                            </div>

                            <div class="form-check col-lg-3">
                                <mvc:label path="isEsterlitzat" for="isEsterlitzat"><i class="fas fa-briefcase-medical"></i>&nbsp;Està esterilitzat?</mvc:label>
                                    <br/>                                       
                                <mvc:radiobutton path="isEsterlitzat" name="isEsterlitzat" class="form-check-input" value="0" />
                                <mvc:label path="isEsterlitzat" class="form-check-label">No</mvc:label>
                                <mvc:radiobutton path="isEsterlitzat" name="isEsterlitzat" class="form-check-input" value="1" />
                                <mvc:label path="isEsterlitzat" class="form-check-label">Sí</mvc:label>
                                    <span class="help-block" id="error"></span>
                            </div>

                            <div class="form-check col-lg-2">
                                <mvc:label path="hasChip" for="hasChip"><i class="fas fa-briefcase-medical"></i>&nbsp;Té chip?</mvc:label>
                                    <br/>                                       
                                <mvc:radiobutton path="hasChip" name="hasChip"  class="form-check-input _chipRadio" value="0" />
                                <mvc:label path="hasChip" class="form-check-label">No</mvc:label>
                                <mvc:radiobutton path="hasChip" name="hasChip"  class="form-check-input _chipRadio" value="1" />
                                <mvc:label path="hasChip" class="form-check-label">Sí</mvc:label>
                                    <span class="help-block" id="error"></span>
                            </div>

                            <div class="form-check col-lg-4">
                                <mvc:label path="numChip" for="numChip">Nº chip</mvc:label>
                                    <div class="input-group">                                  
                                        <div class="input-group-addon"><i class="fas fa-microchip"></i></div>                                                                     
                                        <mvc:input path="numChip" name="numChip" type="text" id="numChip" class="form-control" placeholder="Nombre chip" disabled="true" />                                   
                                </div>
                                <span class="help-block" id="error"></span>

                            </div>
                        </div>

                        <div class="row">
                            <div class="form-group col-lg-12">
                                <label  for="comentari"><i class="far fa-keyboard"></i>&nbsp;Comentari:</label> 
                                <textarea name="comentari" class="form-control" id="comentari" rows="10"></textarea>
                            </div>
                        </div>

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
                                <c:when test="${error=='error_create'}">
                                    <br><span class="alert alert-danger">Error a l'crear l'animal.</span>
                                </c:when>
                            </c:choose>
                        </div>

                    </div>

                </mvc:form>

            </div>
        </div>

        <script>

            jQuery(document).ready(function () {           


            });
            
            //Control del chip
            $("._chipRadio").click(function (e) {                
                if (this.value == 1 ) {
                    $("#numChip").removeAttr("disabled");
                }
                if (this.value == 0 ) {
                    $("#numChip").attr("disabled", "true");
                }
            });





        </script>

    </body>
</html>
