<%-- 
    Document   : admin
    Created on : 20-feb-2020, 11:04:42
    Author     : z003says
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>  
        
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" 
              rel="stylesheet" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous"> 
        <link href="https://cdnjs.cloudflare.com/ajax/libs/jquery-bootgrid/1.3.1/jquery.bootgrid.css" rel="stylesheet">

        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" 
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-bootgrid/1.3.1/jquery.bootgrid.min.js"></script> 
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-bootgrid/1.3.1/jquery.bootgrid.fa.js"></script> 
        
       
        
        <title>Página del administrador</title>
    </head>
    <body>
        <h1>Página del administrador</h1>
        <h2>Lista de usuarios</h2>
        
        
        <!-- http://www.jquery-bootgrid.com/ -->
        <div>            
            <table id="grid-data" class="table table-condensed table-hover table-striped">
                <thead>
                    <tr>
                        <th data-column-id="username">Username</th>
                        <th data-column-id="nombre">Nombre</th>
                        <th data-column-id="apellido1">Apellido</th>
                        <th data-column-id="email">email</th>
                        <th data-column-id="commands" data-formatter="commands" data-sortable="false">Commands</th>
                    </tr>
                </thead>
            </table>   
        </div>
        
        
        <br>
        <a class="logout-link" href="${pageContext.servletContext.contextPath}/logout">-->Logout</a>
        <br><br>
        <input type="submit" value="Nuevo usuario" onclick="location.href='${pageContext.servletContext.contextPath}/admin/newUser'"/>
        
        
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
                                "\"><span class=\"fa fa-pencil\">E</span></button> "
                                +
                                "<button type=\"button\" class=\"btn btn-xs btn-default command-delete\" data-row-id=\""
                                + row.username +
                                "\"><span class=\"fa fa-trash-o\">B</span></button>";
                    }
                },
                
            }).on("loaded.rs.jquery.bootgrid", function ()
            {
                /* Executes after data is loaded and rendered */
                grid.find(".command-edit").on("click", function (e)
                {
                    //alert("You pressed edit on row: " + $(this).data("row-id"));
                    location.href='${home}editUser?username=' + $(this).data("row-id");
                    
                }).end().find(".command-delete").on("click", function (e)
                {
                    //alert("You pressed delete on row: " + $(this).data("row-id"));
                    location.href='${home}deleteUser?username=' + $(this).data("row-id");
                });
            });

        });

    </script>
        
        
        
    </body>
</html>
