<%-- 
    Document   : admin
    Created on : 20-feb-2020, 11:04:42
    Author     : z003says
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


        <script src="<c:url value="../static/js/jquery/jquery331.min.js"/>"></script>        
        <script src="<c:url value="../static/js/jquery/jquery-ui.js"/>"></script>
        <script src="<c:url value="../static/js/bootstrap-4.4.1/popper.min.js"/>"></script>  
        <link href="<c:url value="../static/css/bootstrap-4.4.1/bootstrap.min.css"/>" rel="stylesheet"/>
        <link href="<c:url value="../static/css/bootgrid-1.3.1/jquery.bootgrid.min.css"/>" rel="stylesheet">
        <script src="<c:url value="../static/js/bootstrap-4.4.1/bootstrap.min.js"/>"></script>
        <script src="<c:url value="../static/js/jquery/jquery.bootgrid.min.js"/>"></script>
        <script src="<c:url value="../static/js/jquery/jquery.bootgrid.fa.min.js"/>"></script>

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
        <input type="submit" value="Nuevo usuario" onclick="location.href = '${pageContext.servletContext.contextPath}/admin/newUser'"/>


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
                        location.href = '${home}editUser?username=' + $(this).data("row-id");

                    }).end().find(".command-delete").on("click", function (e)
                    {
                        //alert("You pressed delete on row: " + $(this).data("row-id"));
                        location.href = '${home}deleteUser?username=' + $(this).data("row-id");
                    });
                });

            });

        </script>



    </body>
</html>
