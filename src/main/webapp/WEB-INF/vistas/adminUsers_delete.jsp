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

        
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

        <link href="static/bootstrap-4.4.1-dist/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="static/jquery.bootgrid-1.3.1/css/jquery.bootgrid.css" rel="stylesheet" />

        <script src="<c:url value="static/js/jquery/jquery-3.3.1.min.js"/>"></script>        
        <script src="<c:url value="static/js/jquery/jquery-ui.js"/>"></script>

        <script src="static/bootstrap-4.4.1-dist/js/popper.min.js"></script>
     

        <script src="static/bootstrap-4.4.1-dist/js/bootstrap.min.js"></script>
        <script src="static/jquery.bootgrid-1.3.1/js/moderniz.2.8.1.js"></script>
        <script src="static/jquery.bootgrid-1.3.1/js/jquery.bootgrid.js"></script>
        <script src="static/jquery.bootgrid-1.3.1/js/jquery.bootgrid.fa.js"></script>


    </head>
    <body>
       


        <!-- http://www.jquery-bootgrid.com/ -->
        <div class="container"> 
            <seccion>
                <div class="row">
                    <div class="col-md-12">
                        <table id="grid-data" class="table table-condensed table-hover table-striped">
                            <thead>
                                <tr>
                                    <th data-column-id="username">Username</th>
                                    <th data-column-id="nombre">Nom</th>
                                    <th data-column-id="apellido1">Cognom</th>
                                    <th data-column-id="email">e-mail</th>
                                    <th data-column-id="commands" data-formatter="commands" data-sortable="false">Commands</th>
                                </tr>
                            </thead>
                        </table>  
                    </div>
                </div>

                <div class="row">

                </div>
            </seccion>
            <footer>                
            <!--    <div>                    
                    <input type="button" class="btn btn-info" onclick="location.href = '${pageContext.servletContext.contextPath}'"                        
                           value=' Volver'/>
                </div>-->

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
                    url: "${pageContext.servletContext.contextPath}/admin/userList",
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

            });

        </script>



    </body>
</html>
