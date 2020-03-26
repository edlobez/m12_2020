<%-- 
    Document   : veterinari
    Created on : 02-Mar-2020, 19:40:45
    Author     : edlobez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

        <link href="../static/bootstrap-4.4.1-dist/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css" rel="stylesheet"/>       

        <script src="../static/js/jquery/jquery-3.3.1.min.js"></script>        
        <script src="../static/js/jquery/jquery-ui.js"></script>
        <script src="../static/bootstrap-4.4.1-dist/js/bootstrap.min.js"></script>
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.js"></script>
        
        <title>Llistat d'animals</title>
    </head>
    <body>

        <header>  
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
                        <sec:authorize access="hasAnyAuthority('responsable','admin')">   
                            <li class="nav-item active">                            
                                <a class="nav-link" href="${pageContext.servletContext.contextPath}/responsable/newAnimal">Crear animal <span class="sr-only">(current)</span></a>
                            </li>
                        </sec:authorize>
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
            <input type="hidden" id="rol" value="<sec:authentication property="principal.authorities" />">

        </header>


        <!-- http://www.jquery-bootgrid.com/ -->
        <div class="container"> 
            <seccion>
                  <div class="row">
                    <div class="col-md-12">
                        <table id="tabla_animales" class="display" style="width:100%"><thead>
                                <tr>
                                    <th>Nom</th>
                                    <th>Tipus</th>
                                    <th>Raza</th>
                                    <th>Estat de salut</th>
                                </tr>
                            </thead>
                            <tbody></tbody>
                            <tfoot>
                                <tr>
                                    <th>Nom</th>
                                    <th>Tipus</th>
                                    <th>Raza</th>
                                    <th>Estat de salut</th>
                                </tr>
                            </tfoot>
                        </table>
                        
                    </div>
                </div>                
            </seccion>
            <footer>                
                <div>                    
                    <input type="button" class="btn btn-info" onclick="location.href = '${pageContext.servletContext.contextPath}'"                        
                           value=' Tornar'/>
                </div>
            </footer>
        </div>
                           
        <script>
            jQuery(document).ready(function ($) {

                var table = $('#tabla_animales').DataTable({
                    "processing" : true,
                    "serverSide" : true,                    
                    "ajax" : { 
                        url: "${home}getAnimalList"
                        ,type: 'POST'
                    },
                    "language" : {
                        "url" : "../static/resources/lan/Catalan.json"
                    },
                    "columns" : [                        
                        {"data": "nom"},
                        {"data": "tAnimal"},
                        {"data": "laRaza"},
                        {"data": "isAlta"}
                    ],
                    "columnDefs": [
                        { "searchable": false, "targets": 0 },
                        { "searchable": false, "targets": 1 },
                        { "searchable": false, "targets": 2 },
                        { "searchable": false, "targets": 3 }
                        
                    ]
                }); 
                
                $('#tabla_animales tbody').on('click', 'tr', function () {
                    //alert($(this).text());
                     var data = table.row(this).data();
                     //console.log(Object.values(data)[0]);                     
                     location.href = '${home}editAnimal?idanimal=' + Object.values(data)[0];
                     //location.href = '${home}editAnimal?idanimal=' + $(this).data("row-id");
                     //alert( 'You clicked on '+data[5] +'\'s row' );
                    } );
                
                $('#tabla_animales').on('draw.dt', function () {
                    //alert("redibujando tabla");
                    $("td").each(  function () {
                        if ( $(this).text() === "true") {
                            $(this).text("Alta médica");
                            $(this).css("background-color", "#A4F09E");
                        }
                        if ( $(this).text() === 'false') {
                            $(this).text("Baixa médica");
                            $(this).css("background-color", "#F88078");
                        }
                    });
                });
                
            });
            
                var aux = $_GET("param");
                if (aux.length > 0) {
                    if (aux === "create_ok") {
                        alert("Animal creat amb èxit.");
                    }
                    if (aux === "update_ok") {
                        alert("Els canvis s'han guardat amb èxit.");
                    }
                    if (aux === "delete_ok") {
                        alert("Animal esborrat amb èxit.");
                    }
                    if (aux === "delete_Nok") {
                        alert("Error a l'esborrar l'animal.");
                    }
                }  

                
                
                
            function $_GET(param)
            {
                /* Obtener la url completa */
                url = document.URL;
                /* Buscar a partir del signo de interrogación ? */
                url = String(url.match(/\?+.+/));
                /* limpiar la cadena quitándole el signo ? */
                url = url.replace("?", "");
                /* Crear un array con parametro=valor */
                url = url.split("&");
                /* 
                 Recorrer el array url
                 obtener el valor y dividirlo en dos partes a través del signo = 
                 0 = parametro
                 1 = valor
                 Si el parámetro existe devolver su valor
                 */
                x = 0;
                while (x < url.length)
                {
                    p = url[x].split("=");
                    if (p[0] == param)
                    {
                        return decodeURIComponent(p[1]);
                    }
                    x++;
                }
            }
        </script>



    </body>
</html>
