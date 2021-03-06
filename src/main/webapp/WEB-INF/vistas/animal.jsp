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
<html lang="ca">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="${pageContext.servletContext.contextPath}/static/resources/imgs/favicon.ico" rel="icon" type="image/x-icon">

        <link href="${pageContext.servletContext.contextPath}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>  


        <link href="${pageContext.servletContext.contextPath}/static/bootstrap-3.3.7-dist/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.servletContext.contextPath}/static/css/fileUploader.css" rel="stylesheet" type="text/css"/>


        <script src="${pageContext.servletContext.contextPath}/static/js/jquery/jquery-3.3.1.min.js"></script>
        <script src="${pageContext.servletContext.contextPath}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
        <script src="https://kit.fontawesome.com/42bb3417c7.js" crossorigin="anonymous"></script>

        <title>Formulari Animal</title>
    </head>
    <body>  
        <a class="skip-link" href="#maincontent" style="display:none">Skip to main</a>
        <header>            

            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <a href="${pageContext.servletContext.contextPath}">
                            <span class="navbar-brand">                        
                                <img src="${pageContext.servletContext.contextPath}/static/resources/imgs/kitty_ico.png" width="30" height="30" alt="logo" onerror="alert('error');">                            
                            </span>
                            <span style="color:darkslategrey; strong" class="navbar-brand">RescueManagement</span>
                        </a>
                    </div>
                    <form class="nav navbar-nav navbar-right">
                        <span><sec:authentication property="principal.username" />&nbsp;&nbsp;&nbsp;</span>
                        <a href="${pageContext.servletContext.contextPath}/logout"><i style="color:black; text-decoration-line: underline;" class="fas fa-sign-out-alt">Sortir</i></a> 
                    </form>
                </div>
            </nav>


        </header>
        <main id="main">
        <div class="container">

            <div id="img_container" class="row">
                <div id="myCarousel" class="carousel slide" data-ride="carousel">
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
                            <c:set var="clase" scope="page" value="item"/>
                            <c:if test="${aux}">
                                <c:set var="clase" scope="page" value="item active"/>
                                <c:set var="aux" scope="page" value="${false}"/>
                            </c:if>
                            <div class="${clase}">
                                <img id="imagen" src='data:${imagen.tipo};base64,${imagen.base64}' class="d-block w-100" style="max-height: 150px;display: block;margin-left: auto;margin-right: auto;" onerror="ocultarImagen();"/>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <div class="signup-form-container">    
                <!-- url del action -->
                <c:if test="${accion=='update'}">
                    <c:set var="url" scope="page" value="${pageContext.servletContext.contextPath}/animal/saveAnimal"/>
                </c:if>
                <c:if test="${accion=='create'}">
                    <c:set var="url" scope="page" value="${pageContext.servletContext.contextPath}/responsable/saveAnimal"/>
                </c:if>  
                <!-- form start -->
                <mvc:form method="post" role="form" id="register-form" autocomplete="off" 
                          action="${url}" modelAttribute="animal" methodParam="post">

                    <div class="form-header">
                        <h3 class="form-title"><i class="fas fa-paw"></i>
                            <c:if test="${accion=='update'}">
                                <c:out value="Editar animal"/>
                            </c:if>
                            <c:if test="${accion=='create'}">
                                <c:out value="Crear animal"/>
                            </c:if>    
                            <c:if test="${accion=='consulta'}">
                                <c:out value="Consultar animal"/>
                            </c:if> 
                            <c:if test="${accion=='consulta_adoptado'}">
                                <c:out value="Consultar animal adoptat"/>
                            </c:if> 
                        </h3>

                    </div>

                    <mvc:input path="idAnimal" name="idAnimal" type="hidden" class="form-control" /> 
                    <div class="form-body">
                        <div id="editables">
                            <div class="row">

                                <div class="form-group col-lg-3">
                                    <mvc:label path="nom" for="nom">Nom</mvc:label>
                                        <div class="input-group">                                  
                                            <div class="input-group-addon"><i class="fas fa-cat"></i></div>                                                                     
                                            <mvc:input path="nom" name="nom" id="nom" type="text" class="form-control" placeholder="Nom" />                                   
                                    </div>
                                    <span class="help-block" id="error"></span>
                                </div>

                                <div class="form-group col-lg-3">
                                    <mvc:label path="dataNaix" for="dataNaix">Data de naixement</mvc:label>
                                        <div class="input-group">
                                            <div class="input-group-addon"><i class="far fa-calendar-alt"></i></div>
                                            <mvc:input path="dataNaix" name="dataNaix" id="dataNaix" type="date" class="form-control" />    

                                    </div>
                                    <span class="help-block" id="error"></span>
                                </div>

                                <div class="form-group col-lg-3">
                                    <mvc:label path="tAnimal" for="tAnimal">Tipus d'animal</mvc:label>
                                        <div class="input-group">
                                            <div class="input-group-addon"><i class="fas fa-paw"></i></div>
                                            <mvc:select path="tAnimal" name="tipusAnimal" id="tipusAnimal" class="form-control" items="${tAnimal}" />                                   
                                    </div>
                                    <span class="help-block" id="error"></span>
                                </div>

                                <div class="form-group col-lg-3">
                                    <mvc:label path="laRaza" for="laRaza">Raça</mvc:label>
                                        <div class="input-group">
                                            <div class="input-group-addon"><i class="fas fa-dog"></i></div>
                                            <mvc:select path="laRaza" name="raza" id="raza" class="form-control" items="${laRaza}" />                                   
                                    </div>
                                    <span class="help-block" id="error"></span>
                                </div>

                            </div>

                            <div class ='row'>
                                <div class="form-group col-lg-3">
                                    <mvc:label path="sexe" for="sexe">Sexe</mvc:label>
                                        <div class="input-group">                                  
                                            <div class="input-group-addon"><i class="fas fa-kiwi-bird"></i></div> 
                                            <mvc:select path="sexe" id="sexe" class="form-control" name="sexe" multiple="false">
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

                                        <mvc:select path="tamany" id="tamany" class="form-control" name="tamany" multiple="false">
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
                                            <mvc:select path="veterinari" id="veterinari" name="veterinari" class="form-control" items="${vet}" /> 

                                    </div>
                                    <span class="help-block" id="error"></span>
                                </div>

                                <c:if test="${accion=='update' || accion=='consulta'}"> 
                                    <div class="form-group col-lg-2">
                                        <mvc:label path="isAlta" for="isAlta">Estat de salut</mvc:label>
                                            <div class="input-group"> 
                                            <c:if test="${animal.isAlta}">
                                                <c:set var="btn_estado_class" scope="page" value="btn btn-success"/>
                                                <c:set var="btn_estado_value" scope="page" value="ALTA"/>
                                                <c:set var="_btn_estado_value" scope="page" value="true"/>
                                            </c:if>
                                            <c:if test="${animal.isAlta==false}">
                                                <c:set var="btn_estado_class" scope="page" value="btn btn-danger"/>
                                                <c:set var="btn_estado_value" scope="page" value="BAIXA"/>
                                                <c:set var="_btn_estado_value" scope="page" value="false"/>
                                            </c:if>
                                            <input type="button" id="estado_medico" class="${btn_estado_class}" onclick="estadoMedico()" value="${btn_estado_value}"/>
                                            <mvc:input type="hidden" id="_estado_medico" path="isAlta" value="${_btn_estado_value}"/>    
                                        </div>
                                        <span class="help-block" id="error"></span>
                                    </div>
                                </c:if>

                            </div> 

                            <div class="row">

                                <div class="form-check col-lg-2">
                                    <mvc:label path="isAdoptat" for="isAdoptat"><i class="fas fa-heart"></i>&nbsp;Està adoptat?</mvc:label>
                                        <br/>                                       
                                    <mvc:radiobutton path="isAdoptat" name="isAdoptat" class="form-check-input _adoptatRadio" value="0" id="noadoptat" />
                                    <mvc:label path="isAdoptat" class="form-check-label">No</mvc:label>
                                    <mvc:radiobutton path="isAdoptat" name="isAdoptat" class="form-check-input _adoptatRadio" value="1" />
                                    <mvc:label path="isAdoptat" class="form-check-label">Si</mvc:label>
                                        <span class="help-block" id="error"></span>
                                    </div>

                                    <div class="form-check col-lg-2">
                                    <mvc:label path="isVacunat" for="isVacunat"><i class="fas fa-syringe"></i>&nbsp;Està vacunat?</mvc:label>
                                        <br/>                                       
                                    <mvc:radiobutton path="isVacunat" name="isVacunat" class="form-check-input _vacunatRadio" value="0" />
                                    <mvc:label path="isVacunat" class="form-check-label">No</mvc:label>
                                    <mvc:radiobutton path="isVacunat" name="isVacunat" class="form-check-input _vacunatRadio" value="1" />
                                    <mvc:label path="isVacunat" class="form-check-label">Si</mvc:label>
                                        <span class="help-block" id="error"></span>
                                    </div>

                                    <div class="form-check col-lg-2">
                                    <mvc:label path="isEsterlitzat" for="isEsterlitzat"><i class="fas fa-notes-medical"></i>&nbsp;Està esterilitzat?</mvc:label>
                                        <br/>                                       
                                    <mvc:radiobutton path="isEsterlitzat" name="isEsterlitzat" class="form-check-input _esterilitzatRadio" value="0" />
                                    <mvc:label path="isEsterlitzat" class="form-check-label">No</mvc:label>
                                    <mvc:radiobutton path="isEsterlitzat" name="isEsterlitzat" class="form-check-input _esterilitzatRadio" value="1" />
                                    <mvc:label path="isEsterlitzat" class="form-check-label">Si</mvc:label>
                                        <span class="help-block" id="error"></span>
                                    </div>

                                    <div class="form-check col-lg-2">
                                    <mvc:label path="hasChip" for="hasChip"><i class="fas fa-briefcase-medical"></i>&nbsp;Té xip?</mvc:label>
                                        <br/>                                       
                                    <mvc:radiobutton path="hasChip" name="hasChip"  class="form-check-input _chipRadio" value="0" />
                                    <mvc:label path="hasChip" class="form-check-label">No</mvc:label>
                                    <mvc:radiobutton path="hasChip" name="hasChip"  class="form-check-input _chipRadio" value="1" />
                                    <mvc:label path="hasChip" class="form-check-label">Si</mvc:label>
                                        <span class="help-block" id="error"></span>
                                    </div>

                                    <div class="form-check col-lg-4">
                                    <mvc:label path="numChip" for="numChip">Nº xip</mvc:label>
                                        <div class="input-group">                                  
                                            <div class="input-group-addon"><i class="fas fa-microchip"></i></div>                                                                     
                                            <mvc:input path="numChip" name="numChip" type="text" id="numChip" class="form-control" placeholder="Nombre chip" disabled="true" />                                   
                                    </div>
                                    <span class="help-block" id="error"></span>

                                </div>
                            </div>
                        </div> <!--fin Editables-->

                        <c:if test="${accion=='update' || accion=='consulta'}">
                            <br>
                            <!-- Boton -->


                            <div class="row">
                                <div class="form-group col-lg-12">
                                    <c:if test="${not empty comentarios}">
                                        <button data-toggle="modal" href="#mi_modal" class="btn btn-default" type="button">
                                            Veure comentaris
                                        </button>
                                    </c:if>

                                    <c:if test="${accion=='update'}">
                                        <button type="button" id="nuevoComentario" class="btn btn-default">
                                            Nou comentari
                                        </button>
                                    </c:if>

                                </div>
                            </div>
                            <!-- si se necesita cambiar tamaño de modal agregar modal-lg a la linea 
                            <div class="modal-dialog"> por <div class="modal-dialog modal-lg">-->

                            <!-- Modal-->
                            <div class="modal fade" id="mi_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">
                                                <span aria-hidden="true">&times;</span><span class="sr-only">Tanca</span>
                                            </button>
                                            <h4 class="modal-title" id="myModalLabel">Llistat de comentaris</h4>
                                        </div>
                                        <div class="modal-body">
                                            <div class="row" style="padding:15px">
                                                <c:if test="${not empty comentarios}">
                                                    <c:forEach var="comentario" begin="0" items="${comentarios}">
                                                        <a class="list-group-item">
                                                            <h4 class="list-group-item-heading">${comentario.createdDate} - ${comentario.createdUser}</h4>
                                                            <p class="list-group-item-text">${comentario.descripcio}</p>
                                                        </a>        
                                                    </c:forEach>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">Tanca</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Fin modal -->



                        </c:if>
                        <div class="row" id="newComentari" style="display:none;">
                            <div class="form-group col-lg-12">
                                <label  for="comentari"><i class="far fa-keyboard"></i>&nbsp;Comentari:</label> 
                                <textarea name="comentari" class="form-control" id="comentari" rows="10"></textarea>
                            </div>
                        </div>  

                        <br/> <br/>
                        <div class="form-footer">

                            <c:if test="${accion=='update' || accion=='create'}">

                                <button type="submit" id="btn_enviar" class="btn btn-info">
                                    <span class="glyphicon glyphicon-log-in"></span> Enviar
                                </button>                            
                               <!-- <input type="button" id="btn_volver" class="btn btn-info" onclick="location.href = '${pageContext.servletContext.contextPath}/animal/animalList'"                        
                                       value=' Tornar'/>-->
                            </c:if>

                            <c:if test="${accion=='update'}">
                                <button type="button" data-toggle="modal" id="btn_imagen" href="#modal_imagen" class="btn btn-info">
                                    <i class="fas fa-camera-retro"></i> Afegeix imatge
                                </button>
                            </c:if>
                            <c:if test="${accion=='consulta_adoptado'}">
                                <div class="form-header">
                                    <h3 class="form-title"><i class="fas fa-users"></i>
                                           <c:out value="Familia adoptant"/>
                                    </h3>

                                </div>
                            <div class="row">                              
                                <div class="form-group col-md-4">
                                    <label>Nom:&nbsp;</label><label class="form-control"> <c:out value="${persona.nom}"/></label>                                   
                                </div>
                                <div class="form-group col-md-4">
                                    <label>Primer cognom:&nbsp;</label><label class="form-control"> <c:out value="${persona.cognom1}"/> </label>                                  
                                </div>
                                <div class="form-group col-md-4">
                                    <label>Segon cognom:&nbsp;</label><label class="form-control"><c:out value="${persona.cognom2}"/> </label>                                 
                                </div>                                
                                <br><br>
                            </div>
                                <div class="row">                                
                                <div class="form-group col-md-4">
                                    <label>Email:&nbsp;</label><label class="form-control"><c:out value="${persona.email}"/> </label>                               
                                </div>
                                <div class="form-group col-md-4">
                                    <label>Telèfon:&nbsp;</label><label class="form-control"><c:out value="${persona.telefon}"/> </label>                                
                                </div>
                                <div class="form-group col-md-4">
                                    <label>Direcció:&nbsp;</label><label class="form-control"><c:out value="${persona.direccio}"/> </label>                               
                                </div>                                
                                <br><br>
                            </div>
                            </c:if>
 
                            <button type="button" class="btn btn-info" onclick="location.href = '${pageContext.servletContext.contextPath}/animal/animalList'">Tornar</button>

                            <sec:authorize access="hasAuthority('admin')">  
                                <c:if test="${accion=='update'}">
                                    <input type="button" class="btn btn-danger" onclick="borrarAnimal()" value="Borrar"/>
                                </c:if>
                            </sec:authorize>

                            <input type="hidden" id="_accion" name="accion" value="${accion}"/>


                            <mvc:errors path="*" cssClass="alert alert-danger" element="div"/>
                            <br>
                            <c:choose>
                                <c:when test="${error=='fecha_error'}">
                                    <span class="alert alert-danger">Si us plau, introdueix una data de naixement correcta.</span>
                                </c:when>
                                <c:when test="${error=='create_error'}">
                                    <span class="alert alert-danger">Error a crear l'animal.</span>
                                </c:when>
                            </c:choose>
                        </div>

                    </div>

                </mvc:form>



                <!-- Modal-->
                <div class="modal fade" id="modal_imagen" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">
                                    <span aria-hidden="true">&times;</span><span class="sr-only">Tanca</span>
                                </button>
                                <h4 class="modal-title" id="myModalLabel">Selecciona imatge</h4>
                            </div>
                            <div class="modal-body">
                                <div class="row" style="padding:15px">
                                    <a href="#" id="file-select2" class="btn btn-default">Escull arxiu</a>
                                    <div id="preview" class="thumbnail" style="display:none;">
                                        <a href="#" id="file-select" class="btn btn-default">Canvia la imatge</a>
                                        <img src=""/>
                                    </div>
                                    <span class="alert alert-info" id="file-info">Encara no s'han carregat arxius</span>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <form method="post" id="file-submit" action="${pageContext.servletContext.contextPath}/imagenes/form?id=${id}" enctype="multipart/form-data">
                                    <input id="file" name="file" type="file"/> 
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Tanca</button>
                                    <input type="submit" class="btn btn-primary" id="file-save" value="Guardar"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Fin modal -->




            </div>
        </div>

        <script>

            jQuery(document).ready(function () {
                $("._adoptatRadio").attr("disabled", "true");
                
                /*Si es la pantalla de creación muestra el textarea de comentario*/
                var accion = "${accion}";
                if (accion == "create") {
                    $("#newComentari").show("slow");
                   /* $("._adoptatRadio").attr("disabled", "true");*/
                }

                if (accion == "update" && $("#estado_medico").val() == "BAIXA") {
                    /*$("._adoptatRadio").attr("disabled", "true");*/
                    $("#noadoptat").prop("checked", true);
                }

                /*Habilita/Deshabilita el campo Num chip al cargar segun si tiene o no chip*/
                if ($("._chipRadio:checked").val() == 1) {
                    $("#numChip").removeAttr("disabled");
                } else {
                    $("#numChip").attr("disabled", "true");
                }


                /* Controles según el rol */
                if ("${rol}" == "voluntari") {
                    deshabilita_todos();
                    habilitaSubirImagen();
                } else if ("${rol}" == "admin") {
                    //alert("El admin!!!");
                } else if ("${rol}" == "responsable") {
                    //alert("El responsable!!!");
                    deshabilita_veterinario();
                } else if ("${rol}" == "veterinari") {
                    deshabilita_todos();
                    habilita_veterinario();
                    habilitaSubirImagen();
                }

                // El caso más resctrictivo, una consulta todo deshabilitado
                if (accion == "consulta" || accion == "consulta_adoptado") {
                    deshabilita_todos();
                }
                /*Al iniciar la previsualización de imagen nueva no se verá*/

            });

            $('#preview').hover(function () {
                $(this).find('a').fadeIn();
            }, function () {
                $(this).find('a').fadeOut();
            });

            $('#file-select').on('click', function (e) {
                e.preventDefault();
                $('#file').click();
            });

            $('#file-select2').on('click', function (e) {
                e.preventDefault();
                $('#file').click();
            });

            $('input[type=file]').change(function () {
                var file = (this.files[0].name).toString();
                var reader = new FileReader();

                $('#file-info').text('');
                $('#file-info').text(file);
                $('#preview').show("slow");
                $('#file-select2').css("display", "none");

                reader.onload = function (e) {
                    $('#preview img').attr('src', e.target.result);
                }

                reader.readAsDataURL(this.files[0]);
            });



            var habilitado = true;

            //Control del chip
            $("._chipRadio").click(function (e) {
                if (this.value == 1) {
                    $("#numChip").removeAttr("disabled");
                }
                if (this.value == 0) {
                    $("#numChip").attr("disabled", "true");
                }
            });


            $("#nuevoComentario").click(function () {
                $("#newComentari").show("slow");
            });

            $("#btn_enviar").click(function () {
                if (!habilitado)
                    habilita_todos();
            });

            function borrarAnimal() {
                var opcion = confirm("Esteu segur que vol esborrar l'animal " + "${animal.nom}" + "?");
                if (opcion === true) {
                    location.href = '${pageContext.servletContext.contextPath}/responsable/deleteAnimal?idanimal=' + "${animal.idAnimal}";
                }
            }

            function deshabilita_todos() {
                $("input").attr("disabled", "true");
                $("select").attr("disabled", "true");
                $("radiobutton").attr("disabled", "true");
                habilitado = false;
            }
            function habilitaSubirImagen() {
                $('#file').removeAttr("disabled");
                $('#file-save').removeAttr("disabled");

            }

            function habilita_todos() {

                $("input").removeAttr("disabled");
                $("select").removeAttr("disabled");
                $("radiobutton").removeAttr("disabled");
            }

            function habilita_veterinario() {
                $("#estado_medico").removeAttr("disabled");
                $("._vacunatRadio").removeAttr("disabled");
                $("._esterilitzatRadio").removeAttr("disabled");
                $("._chipRadio").removeAttr("disabled");
            }
            function deshabilita_veterinario() {
                $("#estado_medico").attr("disabled", true);
            }

            function estadoMedico() {
                if ($("#estado_medico").val() == "BAIXA") {
                    $("#estado_medico").val("ALTA");
                    $("#_estado_medico").val("true");
                    $("#estado_medico").removeClass("btn-danger");
                    $("#estado_medico").addClass("btn-success");
                    /*$("._adoptatRadio").removeAttr("disabled");*/

                } else {
                    $("#_estado_medico").val("false");
                    $("#estado_medico").val("BAIXA");
                    $("#estado_medico").removeClass("btn-success");
                    $("#estado_medico").addClass("btn-danger");
                    /*$("._adoptatRadio").attr("disabled", "true");*/
                    $("#noadoptat").prop("checked", true);
                }

            }

            function ocultarImagen() {
                $("#img_container").css("display", "none");
            }

            </script>
        </main>
    </body>
</html>
