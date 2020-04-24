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
        <link href="../static/resources/imgs/favicon.ico" rel="icon" type="image/x-icon">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

        <link href="../static/bootstrap-4.4.1-dist/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css" rel="stylesheet"/>       

         <script src="../static/js/jquery/jquery-3.3.1.min.js"></script>        
        <script src="../static/js/jquery/jquery-ui.js"></script>
          <script src="../static/bootstrap-4.4.1-dist/js/bootstrap.min.js"></script>
          <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.js"></script>

      
        


        <title>Llistat d'usuaris</title>
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
                        <li class="nav-item active">
                            <sec:authorize access="hasAuthority('admin')">
                            <a class="nav-link" href="${pageContext.servletContext.contextPath}/admin/newUser">Crear usuari <span class="sr-only">(current)</span></a>
                            </sec:authorize>
                        </li>                       
                    </ul>
                    
                    <form class="form-inline my-2 my-lg-0">
                        <span><sec:authentication property="principal.username" />&nbsp;&nbsp;&nbsp;</span>
                        <span>
                            <a href="${pageContext.servletContext.contextPath}/logout"><i class="fas fa-sign-out-alt">Sortir</i></a> </span>
                    </form>
                </div>
            </nav>


        </header>


        <!-- https://datatables.net// -->
        <div class="container"> 
            <seccion>
                <div class="row">
                    <div class="col-md-12">
                        <table id="tabla_usuarios" class="display" style="width:100%">
        <thead>
            <tr>
                <th>Username</th>
                <th>Rol</th>
                <th>Nom</th>
                <th>Cognom</th>
                <th>Email</th>
                <th>Animal</th>
            </tr>
        </thead>
        <tbody></tbody>
        <tfoot>
            <tr>
                <th>Username</th>
                <th>Rol</th>
                <th>Nom</th>
                <th>Cognom</th>
                <th>Email</th>
                <th>Animal</th>
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
            
            //https://datatables.net/examples/index
            //https://datatables.net/manual/server-side
           
            $(document).ready(function () {
                
                var aux = 0;
                
                var table = $('#tabla_usuarios').DataTable({
                    "processing" : true,
                    "serverSide" : true,                    
                    "ajax" : { 
                        url: "${home}userList"
                        ,type: 'POST'
                    },
                    "language" : {
                        "url" : "../static/resources/lan/Catalan.json"
                    },
                    "columns" : [
                        {"data": "username"},
                        {"data": "srol"},
                        {"data": "nombre"},
                        {"data": "apellido1"},
                        {"data": "email"},
                        {"data": "tAnimal"}
                    ],
                    "columnDefs": [
                        { "searchable": false, "targets": 0 },
                        { "searchable": false, "targets": 1 },
                        { "searchable": false, "targets": 2 },
                        { "searchable": false, "targets": 3 },
                        { "searchable": false, "targets": 4 } 
                    ]
                }); 
                
                $('#tabla_usuarios tbody').on('click', 'tr', function () {
                    //alert($(this).text());
                     var data = table.row(this).data();
                     //console.log(Object.values(data)[10]);                     
                     location.href = '${pageContext.servletContext.contextPath}/admin/editUser?username=' + Object.values(data)[10];
                     //alert( 'You clicked on '+data[5] +'\'s row' );
                    } );                     
                    $("th").click(function () {
                        //alert ($(this).text());                        
                    }); 
                
                
                var aux = $_GET("param");
                if (aux.length > 0) {
                    if (aux === "create_ok") {
                        alert("Usuari creat amb èxit.");
                    }
                    if (aux === "update_ok") {
                        alert("Els canvis s'han guardat amb èxit.");
                    }
                    if (aux === "delete_ok") {
                        alert("Usuari esborrat amb èxit.");
                    }
                    if (aux === "delete_Nok") {
                        alert("Error a l'esborrar l'usuari.");
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
