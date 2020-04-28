<%-- 
    Document   : chgPassword
    Created on : 14-Mar-2020, 06:48:38
    Author     : edlobez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="mvc" %>

<!DOCTYPE html>
<html lang="ca">
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="static/resources/imgs/favicon.ico" rel="icon" type="image/x-icon">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

        <link href="static/bootstrap-4.4.1-dist/css/bootstrap.min.css" rel="stylesheet"/>
        <script src="<c:url value="/static/js/jquery/jquery-3.3.1.min.js"/>"></script>        
        <script src="<c:url value="/static/js/jquery/jquery-ui.js"/>"></script>       

        <script src="static/bootstrap-4.4.1-dist/js/bootstrap.min.js"></script>      


        <title>RescueManagement</title>

        <style>
            body{
                padding:100px 0;
                background-color:#efefef
            }
            a, a:hover{
                color:#333
            }
        </style>

    </head>
    <body>
        <div class="container">
            <div class="signup-form-container">

                <mvc:form method="post" role="form" id="register-form" 
                          autocomplete="off" action="${pageContext.servletContext.contextPath}/user/savePassword" modelAttribute="usuario">
                    <div class="form-header">

                        <div class="form-title"><i class="fa fa-user"></i>
                            <div class="row">
                                <div class="col-lg-4">
                                    <b>Benvingut a RescueManagement</b>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-4">
                                    <b>Si us plau, crea una nova contrasenya.</b>
                                </div>
                            </div>                            
                         
                        </div>


                    </div>

                    <div class="form-body">
                        <div class="form-group">
                            <label>Contrasenya</label>
                            <div class="input-group" id="show_hide_password">
                                <input class="form-control" type="password" name="password" placeholder="Contrasenya">
                                <div class="input-group-addon">
                                    <a href=""><i class="fa fa-eye-slash" aria-hidden="true"></i></a>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Nova contrasenya</label>
                            <div class="input-group" id="show_hide_password">
                                <input class="form-control" type="password" name="cpassword" placeholder="Contrasenya">
                                <div class="input-group-addon">
                                    <a href=""><i class="fa fa-eye-slash" aria-hidden="true"></i></a>
                                </div>
                            </div>
                        </div>

                        <div class="form-footer">
                            <button type="submit" id="btn_enviar" class="btn btn-info">
                                Guardar
                            </button>
                            <input type="button" class="btn btn-info" onclick="location.href = '${pageContext.servletContext.contextPath}/logout'"                        
                                   value=' Sortir'/>                            

                            
                            <input type="hidden" name="username" value="${usuario.username}"/>
                            
                            <mvc:errors path="*" cssClass="alert alert-danger" element="div"/>
                            <br>
                            <c:choose>
                                <c:when test="${error=='password_error'}">
                                    <br><span class="alert alert-danger">Error en crear la contrasenya</span>
                                </c:when>
                            </c:choose>
                        </div>


                    </div>
                </mvc:form>






            </div>





        </div>




        <script>
            $(document).ready(function () {
                $("#show_hide_password a").on('click', function (event) {
                    event.preventDefault();
                    if ($('#show_hide_password input').attr("type") == "text") {
                        $('#show_hide_password input').attr('type', 'password');
                        $('#show_hide_password i').addClass("fa-eye-slash");
                        $('#show_hide_password i').removeClass("fa-eye");
                    } else if ($('#show_hide_password input').attr("type") == "password") {
                        $('#show_hide_password input').attr('type', 'text');
                        $('#show_hide_password i').removeClass("fa-eye-slash");
                        $('#show_hide_password i').addClass("fa-eye");
                    }
                });
            });

        </script>


    </body>
</html>
