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

        <script src="https://kit.fontawesome.com/42bb3417c7.js" crossorigin="anonymous"></script>
        
         <link href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css" rel="stylesheet"/>    
        <link href="static/resources/imgs/favicon.ico" rel="icon" type="image/x-icon">
    </head>

    <body>        
        <div class="d-flex" id="wrapper">
                
            <!-- Sidebar -->
            <div class="bg-light border-right" id="sidebar-wrapper">
                <a href="${pageContext.servletContext.contextPath}">
                    <div class="sidebar-heading"><img src="static/resources/imgs/kitty_ico.png" width="30" height="30" alt=""><!--<img src="static/resources/imgs/kitty_ico.png" width="30" height="30" alt="">-->
                        RescueManagement                       
                    </div>
                </a>
                <div class="list-group list-group-flush">
                    <sec:authorize access="hasAnyAuthority('responsable','admin')">  
                        <ul class="list-unstyled components">
                            <li class="active">
                                <a href="#usuarisSubmenu" data-toggle="collapse" aria-expanded="false" 
                                   class="list-group-item list-group-item-action bg-light dropdown-toggle">
                                    <i class="fas fa-users"></i>
                                    <strong>Usuaris</strong>
                                </a>
                                <ul class="collapse list-unstyled" id="usuarisSubmenu">
                                    <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <a class="logout-link" href="${pageContext.servletContext.contextPath}/responsable/users" id="usuarisList">
                                            <i class="fas fa-list"></i>
                                            &nbsp;Llista
                                        </a>
                                    </li>
                                </ul>
                                <sec:authorize access="hasAuthority('admin')">                                 
                                    <ul class="collapse list-unstyled" id="usuarisSubmenu">
                                        <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <a class="logout-link" href="${pageContext.servletContext.contextPath}/admin/newUser" id="usuarisCrear">
                                                <i class="far fa-plus-square"></i>
                                                &nbsp;Crear
                                            </a>
                                        </li>
                                    </ul>
                                </sec:authorize>
                            </li>
                        </ul>
                    </sec:authorize>



                    <ul class="list-unstyled components">
                        <li class="active">
                            <a href="#mascotasSubmenu" data-toggle="collapse" aria-expanded="false" 
                               class="list-group-item list-group-item-action bg-light dropdown-toggle">
                                <i class="fas fa-horse"></i>
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
                                            <i class="far fa-plus-square"></i>
                                            &nbsp;Crear
                                        </a>
                                    </li>
                                </sec:authorize>                          
                            </ul>                                
                        </li>
                    </ul>
                    
                    <ul class="list-unstyled components">
                        <li class="active">
                            <a href="#adopcionSubmenu" data-toggle="collapse" aria-expanded="false" 
                               class="list-group-item list-group-item-action bg-light dropdown-toggle">
                                <i class="fas fa-hands"></i>
                                <strong>Adopcions</strong>
                            </a>
                            <ul class="collapse list-unstyled" id="adopcionSubmenu">
                                <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <a class="logout-link" href="${pageContext.servletContext.contextPath}/animal/animalListAdoptats" id="adopcionesList">
                                        <i class="fas fa-list"></i>
                                        &nbsp;Llistat d'adopcions
                                    </a>
                                </li>
                                <sec:authorize access="hasAnyAuthority('responsable','admin')">  
                                    <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <a class="logout-link" href="${pageContext.servletContext.contextPath}/animal/animalListDisponible" id="adoptarCrear">
                                            <i class="fas fa-dove"></i>
                                            &nbsp;Adoptar animal
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
                                <i class="fas fa-user"></i>
                                <strong>Perfil</strong>
                            </a>
                            <ul class="collapse list-unstyled" id="perfilSubmenu">
                                <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <a class="logout-link" href="#" id="miPerfil">
                                        <i class="fas fa-user-edit"></i>
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
                      <div class="container"> 
            <seccion>
                  <div class="row">
                    <div class="col-md-12">
                        <h2 class="mt-4">Comentaris més recents</h2>
                        <table id="tabla_comentarios" class="display" style="width:100%"><thead>
                                <tr>
                                  <!--  <th>ID comentari</th>-->
                                    <th>Descripció</th>
                                    <th>Nom Animal</th>
                                    <th>Data comentari</th>
                                    <th>Autor</th>
                                </tr>
                            </thead>
                            <tbody></tbody>
                            <tfoot>
                                <tr>
                                  <!--  <th>ID comentari</th>-->
                                    <th>Descripció</th>
                                    <th>Nom Animal</th>
                                    <th>Data comentari</th>
                                    <th>Autor</th>
                                </tr>
                            </tfoot>
                        </table>
                        
                    </div>
                </div>                
            </seccion>
        </div>
                </div>
            </div>            
        </div>

        <!-- Bootstrap core JavaScript -->
        <script src="static/startbootstrap-simple-sidebar/vendor/jquery/jquery.min.js"></script>        
        <script src="static/startbootstrap-simple-sidebar/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.js"></script>


        <!-- Menu Toggle Script -->
        <script>
            jQuery(document).ready(function ($) {
                
                
                var table = $('#tabla_comentarios').DataTable({
                    "processing" : true,
                    "serverSide" : true,                    
                    "ajax" : { 
                        url: "${home}user/getCommentList"
                        ,type: 'POST'
                    },
                    "language" : {
                        "url" : "static/resources/lan/Catalan.json"
                    },
                    "columns" : [                          
                        {"data": "descripcio"},
                        {"data": "nomAnimal"},
                        {"data": "createdDateString"},
                        {"data": "createdUser"}
                    ],
                    "columnDefs": [
                        { "searchable": false, "targets": 0 },
                        { "searchable": false, "targets": 1 },
                        { "searchable": false, "targets": 2 },
                        { "searchable": false, "targets": 3 }
                    ]
                });   
                
                $('#tabla_comentarios tbody').on('click', 'tr', function () {
                    //alert($(this).text());
                     var data = table.row(this).data();
                     //console.log(Object.values(data)[0]);                     
                     location.href = '${home}animal/animales/consultar?idanimal=' + Object.values(data)[0];
                     //location.href = '${home}editAnimal?idanimal=' + $(this).data("row-id");
                     //alert( 'You clicked on '+data[5] +'\'s row' );
                    } );
                

                var aux = $_GET("param");

                if (aux.length > 0) {
                    if (aux === "create_ok") {
                        alert("Usuari creat amb èxit.")
                    }
                    if (aux === "update_ok") {
                        alert("Els canvis s'han guardat amb èxit.");
                    }
                    if (aux === "adopcio_ok") {
                        alert("Els canvis s'han guardat amb èxit.");
                    }
                    if (aux === "adopcio_Nok") {
                        alert("Error al realizar la adopción!!!");
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