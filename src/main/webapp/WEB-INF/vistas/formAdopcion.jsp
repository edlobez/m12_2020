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
<html lang="ca">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link href="../static/resources/imgs/favicon.ico" rel="icon" type="image/x-icon">

        <link href="../static/bootstrap-4.4.1-dist/css/bootstrap.min.css" rel="stylesheet"/>   
        <link href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css" rel="stylesheet"/> 

        <script src="https://kit.fontawesome.com/42bb3417c7.js" crossorigin="anonymous"></script>
        <script src="../static/js/jquery/jquery-3.3.1.min.js"></script>        
        <script src="../static/js/jquery/jquery-ui.js"></script>
        <script src="../static/bootstrap-4.4.1-dist/js/bootstrap.min.js"></script>
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.js"></script>

        <title>Formulari d'adopció</title>

    </head>
    <body>
        <a class="skip-link" href="#maincontent" style="display:none">Skip to main</a>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <a href="${pageContext.servletContext.contextPath}">
                <span style="color:darkslategrey; strong" class="navbar-brand">
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
                        <a href="${pageContext.servletContext.contextPath}/logout"><i style="color:black; text-decoration-line: underline;" class="fas fa-sign-out-alt">Sortir</i></a> </span>
                </form>
            </div>
        </nav>
               <div id='main'> 
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
            
            <div class="row" id="lista_adoptantes">               
                <div class="col-md-12"><br>
                    <h2>Llistat d'apotants registrats</h2>
                    <table id="tabla_adoptantes" class="display" style="width:100%"><thead>
                            <tr>
                                <th>Nom</th>
                                <th>Primer cognom</th>
                                <th>Segon Cognom</th>                                    
                                <th>Email</th>  
                                <th>Telèfon</th>  
                                <th>Adreça</th>  
                            </tr>
                        </thead>
                        <tbody></tbody>
                        <tfoot>
                            <tr>
                                <th>Nom</th>
                                <th>Primer cognom</th>
                                <th>Segon Cognom</th>                                    
                                <th>Email</th>  
                                <th>Telèfon</th>  
                                <th>Adreça</th>  
                            </tr>
                        </tfoot>
                    </table>
                    <div>
                        <br>
                    <input type="button" id="nuevo_adoptante" class="btn btn-info" onclick="nuevoAdoptante()"                        
                           value='Nou Adoptant'/>
                </div>
                </div>                  
            </div>
                        
            <div class="row" id="formulario_adopcion">            
            <mvc:form role="form" class="col-md-12" id="adopcio-form" autocomplete="off" action="saveAdopcio" modelAttribute="adoptante">  
                <div class="form-header"><br>
                    <h3 class="form-title"><i class="fas fa-dove"></i>
                        Formulari d'adopció
                    </h3>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-4">
                        <label for="nom">Nom</label>
                        <div class="input-group">   
                            <div class="input-group-prepend"><span class="input-group-text"><i class="fas fa-user"></i></span></div>
                            <mvc:input path="nom" type="text" class="form-control" id="nom" placeholder="Nom"/>
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                        <label for="cognom1">Primer cognom</label>
                        <div class="input-group">   
                            <div class="input-group-prepend"><span class="input-group-text"><i class="fas fa-user"></i></span></div>
                            <mvc:input path="cognom1" type="text" class="form-control" id="cognom1" placeholder="Primer cognom"/>
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                        <label for="cognom2">Segon cognom</label>
                        <div class="input-group">   
                            <div class="input-group-prepend"><span class="input-group-text"><i class="fas fa-user"></i></span></div>
                            <mvc:input path="cognom2" type="text" class="form-control" id="cognom2" placeholder="Segon cognom"/>
                        </div>
                    </div>
                </div>
                <div class="form-row">
                   <div class="form-group col-md-6">
                        <label for="email">Email</label>
                        <div class="input-group">   
                            <div class="input-group-prepend"><span class="input-group-text"><i class="fas fa-envelope"></i></span></div>
                            <mvc:input path="email" type="email" class="form-control" id="email" placeholder="Email"/>
                        </div>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="telefon">Telèfon</label>
                        <div class="input-group">   
                            <div class="input-group-prepend"><span class="input-group-text"><i class="fas fa-phone"></i></span></div>
                            <input path="telefon" type="numeric" class="form-control" id="telefon" placeholder="Telèfon"/>
                            
                        </div>
                    </div> 
                </div>
                <div class="form-group">
                    <label for="direccio">Direcció</label>
                    <div class="input-group">   
                        <div class="input-group-prepend"><span class="input-group-text"><i class="fas fa-address-card"></i></span></div>
                        <mvc:input path="direccio" type="text" class="form-control" id="direccio" placeholder="Direcció"/>
                    </div>
                </div>          
                
                    <div class="form-footer"> <br>                   
                    <button type="submit" class="btn btn-info">Enviar</button>
                    <button type="button" class="btn btn-info" onclick="location.href = '${pageContext.servletContext.contextPath}/animal/animalListDisponible'">Tornar</button>

                </div>
                 <mvc:errors path="*" cssClass="alert alert-danger" element="div"/>   
                 
            </mvc:form>  
            <br><br>
            </div>
        

        </div>
               </div>
        <script>
            
            jQuery(document).ready(function ($) {
                
                var table = $('#tabla_adoptantes').DataTable({
                    "processing" : true,
                    "serverSide" : true,                    
                    "ajax" : { 
                        url: '${home}listaAdoptantes'
                        ,type: 'POST'
                    },
                    "language" : {
                        "url" : "../static/resources/lan/Catalan.json"
                    },
                    "columns" : [                        
                        {"data": "nom"},
                        {"data": "cognom1"},
                        {"data": "cognom2"},
                        {"data": "email"},
                        {"data": "telefon"},
                        {"data": "direccio"}
                    ],
                    "columnDefs": [
                        { "searchable": false, "targets": 0 },
                        { "searchable": false, "targets": 1 },
                        { "searchable": false, "targets": 2 },
                        { "searchable": false, "targets": 3 },
                        { "searchable": false, "targets": 4 },
                        { "searchable": false, "targets": 5 }
                        
                    ]
                }); 
                
                $('#tabla_adoptantes tbody').on('click', 'tr', function () {
                    var data = table.row(this).data();
                    var opcion = confirm("Confirma que ${nombre_animal} va ser adoptado por " + Object.values(data)[5] +"?");
                    if (opcion==true) location.href = '${home}readoptar?idpersona=' + Object.values(data)[4];                     
                });
            
                $('#formulario_adopcion').css('display', 'none');
                
            });
            
           
            
            function nuevoAdoptante () {
                $('#lista_adoptantes').css('display', 'none');
                $('#formulario_adopcion').show('slow');
            } 
        
        </script>            

                        
    </body>
</html>
