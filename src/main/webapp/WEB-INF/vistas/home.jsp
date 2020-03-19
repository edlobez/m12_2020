<%-- 
    Document   : home_
    Created on : 12-Mar-2020, 15:28:11
    Author     : edlobez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>RescueManagement</title>

        <!-- Bootstrap core CSS -->
        <link href="static/startbootstrap-simple-sidebar/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="static/startbootstrap-simple-sidebar/css/simple-sidebar.css" rel="stylesheet">

        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

    </head>

    <body>

        <div class="d-flex" id="wrapper">

            <!-- Sidebar -->
            <div class="bg-light border-right" id="sidebar-wrapper">
                <a href="${pageContext.servletContext.contextPath}">
                    <div class="sidebar-heading"><img src="static/resources/imgs/kitty_ico.png" width="30" height="30" alt="">
                        RescueManagement                       
                    </div>
                </a>
                <div class="list-group list-group-flush">
                    <sec:authorize access="hasAuthority('admin')">  
                        <ul class="list-unstyled components">
                            <li class="active">
                                <a href="#usuarisSubmenu" data-toggle="collapse" aria-expanded="false" 
                                   class="list-group-item list-group-item-action bg-light dropdown-toggle">
                                    <i class="fas fa-users"></i>
                                    <strong>Usuaris</strong>
                                </a>
                                <ul class="collapse list-unstyled" id="usuarisSubmenu">
                                    <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <a class="logout-link" href="${pageContext.servletContext.contextPath}/admin/users" id="usuarisList">
                                            <i class="fas fa-list"></i>
                                            &nbsp;Llista
                                        </a>
                                    </li>
                                </ul>
                                <ul class="collapse list-unstyled" id="usuarisSubmenu">
                                    <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <a class="logout-link" href="${pageContext.servletContext.contextPath}/admin/newUser" id="usuarisCrear">
                                            <i class="far fa-plus-square"></i>
                                            &nbsp;Crear
                                        </a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </sec:authorize>



                    <ul class="list-unstyled components">
                        <li class="active">
                            <a href="#mascotasSubmenu" data-toggle="collapse" aria-expanded="false" 
                               class="list-group-item list-group-item-action bg-light dropdown-toggle">
                                <i class="fas fa-users"></i>
                                <strong>Animals</strong>
                            </a>
                            <ul class="collapse list-unstyled" id="mascotasSubmenu">
                                <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <a class="logout-link" href="${pageContext.servletContext.contextPath}/animal/animalList" id="animalesList">
                                        <i class="fas fa-list"></i>
                                        &nbsp;Llista
                                    </a>
                                </li>
                                <sec:authorize access="hasAnyAuthority('responsable','admin')">  
                                    <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <a class="logout-link" href="${pageContext.servletContext.contextPath}/responsable/newAnimal" id="animalesCrear">
                                            <i class="fas fa-list"></i>
                                            &nbsp;Crear
                                        </a>
                                    </li>
                                </sec:authorize>
                            </ul>                                
                        </li>
                    </ul>



                    <ul class="list-unstyled components">
                        <li class="active">
                            <a href="#perfilSubmenu" data-toggle="collapse" aria-expanded="false" 
                               class="list-group-item list-group-item-action bg-light dropdown-toggle">
                                <i class="fas fa-users"></i>
                                <strong>Perfil</strong>
                            </a>
                            <ul class="collapse list-unstyled" id="perfilSubmenu">
                                <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <a class="logout-link" href="#" id="miPerfil">
                                        <i class="fas fa-list"></i>
                                        &nbsp;El meu perfil
                                    </a>
                                </li>
                            </ul>                                
                        </li>
                    </ul>



                </div>
            </div>
            <!-- /#sidebar-wrapper -->

            <!-- Page Content -->
            <div id="page-content-wrapper">

                <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
                    <button class="btn btn-primary" id="menu-toggle">Menu</button>

                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>

                    <div class="collapse navbar-collapse" id="navbarSupportedContent">                        
                        <ul class="navbar-nav ml-auto mt-2 mt-lg-0">
                            <li class="nav-item">
                                <span>
                                    <span id="username"><sec:authentication property="principal.username" /></span>&nbsp;&nbsp;&nbsp;
                                    <span><sec:authentication property="principal.authorities"/></span>&nbsp;&nbsp;&nbsp;
                                </span>
                                <a href="${pageContext.servletContext.contextPath}/logout"><i class="fas fa-sign-out-alt">Sortir</i></a> 
                            </li>
                        </ul>
                    </div>
                </nav>

                <div class="container-fluid" id="contenido">
                    <h1 class="mt-4">Benvingut/da!</h1>
                    <p>Benvingut/da a la pàgina principal de l'aplicació Rescue Management.</p>
                    <p>Des del menú de l'esquerra pots accedir als llistats i manteniment del personal i els animals que tens a càrrec, així com actualitzar el teu perfil.</p>
                </div>
            </div>
            <!-- /#page-content-wrapper -->

        </div>
        <!-- /#wrapper -->

        <!-- Bootstrap core JavaScript -->
        <script src="static/startbootstrap-simple-sidebar/vendor/jquery/jquery.min.js"></script>        
        <script src="static/startbootstrap-simple-sidebar/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>



        <!-- Menu Toggle Script -->
        <script>
            jQuery(document).ready(function ($) {

                var aux = $_GET("param");

                if (aux.length > 0) {
                    if (aux === "create_ok") {
                        alert("Usuari creat amb èxit.")
                    }
                    if (aux === "update_ok") {
                        alert("Els canvis s'han guardat amb èxit.");

                    }
                }


            });


            $("#menu-toggle").click(function (e) {
                e.preventDefault();
                $("#wrapper").toggleClass("toggled");
            });

            $("#miPerfil").click(function (e) {
                e.preventDefault();
                location.href = "${pageContext.servletContext.contextPath}/user/editUser?username=" + $("#username").text();
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