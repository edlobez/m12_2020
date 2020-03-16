<%-- 
    Document   : admin
    Created on : 20-feb-2020, 11:04:42
    Author     : z003says
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!--    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        -->
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

        <link href="../static/bootstrap-4.4.1-dist/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="../static/jquery.bootgrid-1.3.1/css/jquery.bootgrid.css" rel="stylesheet" />

        <!--    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        -->
        <script src="<c:url value="../static/js/jquery/jquery-3.3.1.min.js"/>"></script>        
        <script src="<c:url value="../static/js/jquery/jquery-ui.js"/>"></script>

        <script src="../static/bootstrap-4.4.1-dist/js/popper.min.js"></script>
        <!--
           <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        -->
        <!--   <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        -->

        <script src="../static/bootstrap-4.4.1-dist/js/bootstrap.min.js"></script>
        <script src="../static/jquery.bootgrid-1.3.1/js/moderniz.2.8.1.js"></script>
        <script src="../static/jquery.bootgrid-1.3.1/js/jquery.bootgrid.js"></script>
        <script src="../static/jquery.bootgrid-1.3.1/js/jquery.bootgrid.fa.js"></script>


        <title>Llistat d'usuaris</title>
    </head>
    <body>

        <header>
            <!--  <nav class="navbar navbar-light bg-light">
                  <span class="navbar-brand">
                      <img src="../static/resources/imgs/kitty_ico.png" width="30" height="30" alt="">
                      RescueManagement
                  </span>
                  <form class="form-inline">                    
                      <input type="button" class="btn btn-info" value="Crear usuario">                    
                      <span><sec:authentication property="principal.username" />&nbsp;&nbsp;&nbsp;</span>
                      <span onclick="alert('clikc!')" alt="logout"><i class="fas fa-sign-out-alt">Salir</i></span>                    
                  </form>
              </nav>-->

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
                        <li class="nav-item active">
                            <a class="nav-link" href="${pageContext.servletContext.contextPath}/admin/newUser">Crear usuari <span class="sr-only">(current)</span></a>
                        </li>
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


        </header>


        <!-- http://www.jquery-bootgrid.com/ -->
        <div class="container"> 
            <seccion>
                <div class="row">
                    <div class="col-md-12">
                        <table id="grid-data" class="table table-condensed table-hover table-striped">
                            <thead>
                                <tr>
                                    <th data-column-id="username">ID usuari</th>
                                    <th data-column-id="nombre">Nom</th>
                                    <th data-column-id="apellido1">Cognom</th>
                                    <th data-column-id="email">Email</th>
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
                
                
                
                var grid = $("#grid-data").bootgrid({
                    rowCount: [-1, 1, 2],
                    ajax: true,
                    post: function ()
                    {
                        return {
                            id: "b0df282a-0d67-40e5-8558-c9e93b7befed"
                        };
                    },
                    url: "${home}userList",
                    formatters: {
                        "commands": function (column, row)
                        {
                            return "<button type=\"button\" class=\"btn btn-xs btn-default command-edit\" data-row-id=\""
                                    + row.username +
                                    "\"><i class=\"far fa-edit\"></i></button> "
                                    +
                                    "<button type=\"button\" class=\"btn btn-xs btn-default command-delete\" data-row-id=\""
                                    + row.username +
                                    "\"><i class=\"far fa-trash-alt\"></i></button>";
                        }
                    },

                }).on("loaded.rs.jquery.bootgrid", function ()
                {
                    /* Executes after data is loaded and rendered */
                    grid.find(".command-edit").on("click", function (e)
                    {
                        //alert("You pressed edit on row: " + $(this).data("row-id"));
                        location.href = '${home}editUser?username=' + $(this).data("row-id");

                    }).end().find(".command-delete").on("click", function (e)
                    {
                        //alert("You pressed delete on row: " + $(this).data("row-id"));
                        location.href = '${home}deleteUser?username=' + $(this).data("row-id");
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
