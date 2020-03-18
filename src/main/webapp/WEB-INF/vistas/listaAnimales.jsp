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
        <link href="../static/jquery.bootgrid-1.3.1/css/jquery.bootgrid.css" rel="stylesheet" />
        
        <script src="<c:url value="../static/js/jquery/jquery-3.3.1.min.js"/>"></script>        
        <script src="<c:url value="../static/js/jquery/jquery-ui.js"/>"></script>

        <script src="../static/bootstrap-4.4.1-dist/js/popper.min.js"></script>
       

        <script src="../static/bootstrap-4.4.1-dist/js/bootstrap.min.js"></script>
        <script src="../static/jquery.bootgrid-1.3.1/js/moderniz.2.8.1.js"></script>
        <script src="../static/jquery.bootgrid-1.3.1/js/jquery.bootgrid.js"></script>
        <script src="../static/jquery.bootgrid-1.3.1/js/jquery.bootgrid.fa.js"></script>


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
                                <a class="nav-link" href="#">Crear animal <span class="sr-only">(current)</span></a>
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
                        <table id="grid-data" class="table table-condensed table-hover table-striped">
                            <thead>
                                <tr>
                                    <th data-column-id="idanimal">Id</th>
                                    <th data-column-id="nom">Nom</th>
                                    <th data-column-id="tipusanimal">Animal</th>
                                    <th data-column-id="raza">Raza</th>
                                <!--    <th data-column-id="email">Email</th>                                    
                                    <th data-column-id="tAnimal">Animal</th> -->
                                    <th data-column-id="commands" data-formatter="commands" data-sortable="false">Accions</th>
                                </tr>
                            </thead>
                        </table>  
                    </div>
                </div>

                <div class="row">

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
                
                var _sort = "nom";
                
                
                var grid = $("#grid-data").bootgrid({
                    rowCount: [-1, 5, 10],
                    ajax: true,
                    sort: _sort,
                    post: function ()
                    {
                        return {
                            id: "b0df282a-0d67-40e5-8558-c9e93b7befed"
                        };
                    },
                    url: "${home}getAnimalList",
                    formatters: {
                        "commands": function (column, row)
                        {
                            if ( ($("#rol").val() === "[admin]") || ($("#rol").val() === "[responsable]") ) {
                                return "<button type=\"button\" class=\"btn btn-xs btn-default command-edit\" data-row-id=\""
                                        + row.idanimal +
                                        "\"><i class=\"far fa-edit\"></i></button> "
                                        +
                                        "<button type=\"button\" class=\"btn btn-xs btn-default command-delete\" data-row-id=\""
                                        + row.idanimal +
                                        "\"><i class=\"far fa-trash-alt\"></i></button>";
                                }   
                            else if ( ($("#rol").val() === "[veterinari]") || ($("#rol").val() === "[voluntari]") ) {
                                return "<button type=\"button\" class=\"btn btn-xs btn-default command-edit\" data-row-id=\""
                                        + row.idanimal +
                                        "\"><i class=\"far fa-edit\"></i></button> ";
                            }
                            else {
                                return "";
                            }
                        }
                    },
                    labels: {
                        all : "Tots",
                        search: _sort,
                        infos: "Mostrant {{ctx.start}} a {{ctx.end}} de {{ctx.total}} entrades"
                        
                    }

                }).on("loaded.rs.jquery.bootgrid", function () {
                    /* Executes after data is loaded and rendered */
                    grid.find(".command-edit").on("click", function (e) {
                        alert("You pressed edit on row: " + $(this).data("row-id"));
                        //location.href = '${home}editUser?username=' + $(this).data("row-id");
                    }).end().find(".command-delete").on("click", function (e) {
                        alert("You pressed delete on row: " + $(this).data("row-id"));
                        //location.href = '${home}deleteUser?username=' + $(this).data("row-id");
                    }).end().find(".text").on("click", function(e) {
                        e.preventDefault();
                        _sort = this.innerHTML;
                        $(".search-field").attr("placeholder", _sort);
                       //alert("click username " + _sort.parent());
                                           
                    });                  
                   
                    
                });
                
                
                
                
                var aux = $_GET("param");

                if (aux.length > 0) {
                    if (aux === "create_ok") {
                        alert("Usuari creat amb èxit.");
                    }
                    if (aux === "update_ok") {
                        alert("Els canvis s'han guardat amb èxit.");
                    }
                }
                
            });

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
