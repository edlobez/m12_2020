/*
 * Copyright (C) 2020 edlobez
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package dawm12.grup2.es.controller;

import dawm12.grup2.es.domain.Usuarios;
import dawm12.grup2.es.service.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dawm12.grup2.es.PasswordEncoderGenerator;
import dawm12.grup2.es.domain.Roles;
import dawm12.grup2.es.domain.TipusAnimal;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author edlobez
 */
@Controller
@RestController
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    @Qualifier("usuarioService")
    private Service usuarioService;

    @Autowired
    @Qualifier("rolesService")
    private Service rolesService;

    @Autowired
    @Qualifier("tipusAnimalService")
    private Service tipusAnimalService;

    @Autowired
    @Qualifier("animalService")
    private Service animalService;

    private Usuarios _usr_copy;

    @RequestMapping("/users")
    public ModelAndView adminUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ModelAndView modelview = new ModelAndView("adminUsers");
        return modelview;
    }

    @RequestMapping("/pets")
    public ModelAndView adminPets(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ModelAndView modelview = new ModelAndView("listaAnimales");
        return modelview;
    }

    /*  @RequestMapping("/logout")
    public void logout () {
        System.out.println("\n\n\nAdios");
    }*/
    @RequestMapping(value = "/editUser")
    public ModelAndView editUser(@RequestParam("username") String username, Model modelo) {
        Usuarios usr = (Usuarios) usuarioService.getone("username=" + username);
        //Usuarios usr_copy = new Usuarios (usr);
        modelo.addAttribute("usuario", usr);
        this._usr_copy = new Usuarios(usr); // guardamos una copia que no se modificará
        modelo.addAttribute("accion", "update");

        ModelAndView mv = new ModelAndView("user");
        List<Roles> roles = rolesService.getAll();
        mv.addObject("listaRoles", roles);
        List<TipusAnimal> tipusAnimal = tipusAnimalService.getAll();
        mv.addObject("listaTipusAnimal", tipusAnimal);
        return mv;
    }

    @RequestMapping(value = "/deleteUser")
    public ModelAndView deleteUser(
            @ModelAttribute("usuario") Usuarios usr) {
        System.out.println ("Borrar usuario: " + usr.toString());
        
        // Para borrar el usuario lo deshabílitamos
        Usuarios _usr = (Usuarios) usuarioService.update(usr, "enabled=0");
        
        String param;
        if ( _usr == null) {
            param = "?param=delete_Nok";
        }
        else {
            param = "?param=delete_ok";
        }
        
        
        return new ModelAndView("redirect:/admin/users" + param);
    }

    @RequestMapping(value = "/newUser")
    public ModelAndView newUser(ModelMap modelo) {
        Usuarios usr = new Usuarios();
        modelo.addAttribute("usuario", usr);
        modelo.addAttribute("accion", "create");

        ModelAndView mv = new ModelAndView("user");
        List<Roles> roles = rolesService.getAll();
        mv.addObject("listaRoles", roles);
        List<TipusAnimal> tipusAnimal = tipusAnimalService.getAll();
        mv.addObject("listaTipusAnimal", tipusAnimal);
        return mv;
    }

    /*
    Validación del resultado formulario user.jsp
    TO-DO ELIMINAR TANTOS BLOQUES DE CÓDIGO REPETIDO
     */
    @RequestMapping(value = "/saveUser")
    public ModelAndView guardarUser(
            @Valid @ModelAttribute("usuario") Usuarios usr,
            BindingResult validacion,
            @RequestParam("accion") String accion,
            @RequestParam("password") String password,
            @RequestParam("cpassword") String cpassword,
            ModelMap modelo,
            HttpServletRequest request) {

        Usuarios _u = (Usuarios) usuarioService.getone("username=" + usr.getUsername());

        if (validacion.hasErrors()) {
            modelo.addAttribute("usuario", usr);
            modelo.addAttribute("accion", accion);
            ModelAndView mv = new ModelAndView("user");
            List<Roles> roles = rolesService.getAll();
            mv.addObject("listaRoles", roles);
            List<TipusAnimal> tipusAnimal = tipusAnimalService.getAll();
            mv.addObject("listaTipusAnimal", tipusAnimal);

            return mv;
        }

        //System.out.println("Password:" + password);
        //System.out.println("Cpasswore: " + cpassword);
        if (accion.equals("create")) {
            boolean error = false;
            if (password.length() == 0 || password.length() < 4) {
                modelo.addAttribute("error", "password_error_long");
                error = true;
            } else if (!password.equals(cpassword)) {
                modelo.addAttribute("error", "password_error");
                error = true;
            }

            if (error) {
                //    System.out.println("\n\n--ERROR AL CREAR CONTRASEÑAS NO COINCIDEN");
                modelo.addAttribute("error", "password_error");
                modelo.addAttribute("usuario", usr);
                modelo.addAttribute("accion", accion);
                ModelAndView mv = new ModelAndView("user");
                List<Roles> roles = rolesService.getAll();
                mv.addObject("listaRoles", roles);
                List<TipusAnimal> tipusAnimal = tipusAnimalService.getAll();
                mv.addObject("listaTipusAnimal", tipusAnimal);
                return mv;
            } else {
                if (createUsuario(usr, modelo) == null) {
                    //modelo.addAttribute("error", "error_create");
                    modelo.addAttribute("usuario", usr);
                    modelo.addAttribute("accion", accion);
                    ModelAndView mv = new ModelAndView("user");
                    List<Roles> roles = rolesService.getAll();
                    mv.addObject("listaRoles", roles);
                    List<TipusAnimal> tipusAnimal = tipusAnimalService.getAll();
                    mv.addObject("listaTipusAnimal", tipusAnimal);
                    return mv;
                }
            }
        }

        if (accion.equals("update")) {

            //System.out.println("\n\nVamos hacer update de " + usr + " y " + _usr_copy);

            String password_old = request.getParameter("password_update_old");
            String password_new = request.getParameter("password_new");
            String cpassword_new = request.getParameter("cpassword_new");

            System.out.println(" " + password_old + " " + password_new + " " + cpassword_new);

            // Modificar el password de un usuario
            if ((password_old != null && password_new != null && cpassword_new != null)
                    && (password_old.length() > 0 || password_new.length() > 0 || cpassword_new.length() > 0)) {

                System.out.println("Se va modificar el password un usuario: " + password_new);
                System.out.println("Password nuevo: " + cpassword_new);
                boolean error = false;
                if (password_new.length() == 0 || password_new.length() < 4) {
                    modelo.addAttribute("error", "password_error_long");
                    error = true;
                } else if (!password_new.equals(cpassword_new)) {
                    modelo.addAttribute("error", "password_error");
                    error = true;
                }
                // Al admin no se verifica password antiguo
                /*else if ( ! new BCryptPasswordEncoder().matches(password_old, _u.getPassword())  ) {
                   error = true;
                   modelo.addAttribute("error", "password_error");
                }*/
                if (error) {
                    //    System.out.println("\n\n--ERROR AL CREAR CONTRASEÑAS NO COINCIDEN");
                    modelo.addAttribute("error", "password_error");
                    modelo.addAttribute("usuario", usr);
                    modelo.addAttribute("accion", accion);
                    ModelAndView mv = new ModelAndView("user");
                    List<Roles> roles = rolesService.getAll();
                    mv.addObject("listaRoles", roles);
                    List<TipusAnimal> tipusAnimal = tipusAnimalService.getAll();
                    mv.addObject("listaTipusAnimal", tipusAnimal);
                    return mv;
                }
            } else {
                // Si llega hasta aquí intentamos el update
                // Cargamos el password
                usr.setPassword(password_new);
            }

            if (updateUsuario(usr, _usr_copy, modelo) == null) {
                //modelo.addAttribute("error", "error_update");
                modelo.addAttribute("usuario", usr);
                modelo.addAttribute("accion", accion);
                ModelAndView mv = new ModelAndView("user");
                List<Roles> roles = rolesService.getAll();
                mv.addObject("listaRoles", roles);
                List<TipusAnimal> tipusAnimal = tipusAnimalService.getAll();
                mv.addObject("listaTipusAnimal", tipusAnimal);
                return mv;
            }
        }
        
        String param = "";
        if ( accion.equals("create") )
            param = "?param=create_ok";
        if ( accion.equals("update") ) 
            param = "?param=update_ok";

        return new ModelAndView("redirect:/admin/users" + param);
        
    }

    @RequestMapping(value = "/userList")
    public String getSearchResultViaAjax(
            @RequestBody String search,
            @RequestParam("current") String actual,
            @RequestParam("rowCount") String numFilas,
            HttpServletRequest request,
            @RequestParam("searchPhrase") String cadenaBusqueda) throws JSONException {

               
        String busqueda_por = "username";
        Enumeration <String> par = request.getParameterNames();
        while (par.hasMoreElements()) {
            String aux = par.nextElement();
            if (aux.indexOf("sort") != -1) {
                busqueda_por = aux.substring(5, aux.length()-1);                
                if ( busqueda_por.equals("tAnimal") ) {
                    busqueda_por = "tipusAnimal";
                }
            }
        }
        
        //System.out.println("\n\n"+busqueda_por);
        
        if ( busqueda_por.equals("tipusAnimal")) {
            if ( cadenaBusqueda.toLowerCase().startsWith("g") ) 
                cadenaBusqueda = "1";
            else if ( cadenaBusqueda.toLowerCase().startsWith("c") )
                cadenaBusqueda = "2";
            else if ( cadenaBusqueda.toLowerCase().startsWith("t") ) 
                cadenaBusqueda = "3";
        }
        
        
        List<Usuarios> lista = new ArrayList<>();
        if (cadenaBusqueda.length() == 0) {
            String aux = "";
            if (Integer.parseInt(numFilas) != -1) {
                aux = "LIMIT " + numFilas;
            }
            //lista = usuarioService.getAll(("ORDER BY username ASC " + aux).trim());
            lista = usuarioService.get(aux, "enabled=1");
        } else {
            lista = usuarioService.getAND("ORDER BY apellido1 ASC", busqueda_por + "=%" + cadenaBusqueda + "%,enabled=1");
        }

        if ( lista!= null && lista.size() > 0 ) {
            for (int i = 0; i < lista.size(); i++) {
                TipusAnimal t = (TipusAnimal) tipusAnimalService.getone("idtipus=" + lista.get(i).getTipusAnimal());
                lista.get(i).settAnimal(  t.getDescripcio()  );
            }
        }
        
        ObjectMapper JSON_MAPPER = new ObjectMapper();
        JSONObject member = null;
        JSONArray array = new JSONArray();
        for (Usuarios user : lista) {
            try {
                member = new JSONObject(JSON_MAPPER.writeValueAsString(user));
                array.put(member);
            } catch (JsonProcessingException ex) {
                Logger.getLogger(AdminUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //System.out.println("Json array: " + array.toString());
        JSONObject json = new JSONObject();
        try {
            json.put("total", lista.size());
            json.put("rows", array);            
            json.put("rowCount", numFilas);
            json.put("current", actual);
        } catch (JSONException ex) {
            Logger.getLogger(AdminUserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*  String result = "{\"current\":1, "
                + "\"rowCount\":10,"
                + "\"total\":14,"
                + "\"rows\": ["
                + "{\"id\":1,\"name\": \"edu\",\"correo\": \"edu@yahoo.com\"},"
                + "{\"id\":2,\"name\": \"edu\",\"correo\": \"edu@yahoo.com\"},"
                + "{\"id\":3,\"name\": \"edu\",\"correo\": \"edu@yahoo.com\"},"
                + "{\"id\":4,\"name\": \"edu\",\"correo\": \"edu@yahoo.com\"},"
                + "{\"id\":5,\"name\": \"edu\",\"correo\": \"edu@yahoo.com\"},"
                + "{\"id\":6,\"name\": \"edu\",\"correo\": \"edu@yahoo.com\"},"
                + "{\"id\":7,\"name\": \"edu\",\"correo\": \"edu@yahoo.com\"},"
                + "{\"id\":8,\"name\": \"edu\",\"correo\": \"edu@yahoo.com\"},"
                + "{\"id\":9,\"name\": \"edu\",\"correo\": \"edu@yahoo.com\"},"
                + "{\"id\":10,\"name\": \"edu\",\"correo\": \"edu@yahoo.com\"},"
                + "{\"id\":11,\"name\": \"edu\",\"correo\": \"edu@yahoo.com\"},"
                + "{\"id\":12,\"name\": \"edu\",\"correo\": \"edu@yahoo.com\"},"
                + "{\"id\":13,\"name\": \"edu\",\"correo\": \"edu@yahoo.com\"},"
                + "{\"id\":14,\"name\": \"edu\",\"correo\": \"edu@yahoo.com\"}"
                + "]}";*/
        //System.out.println(json.toString());
        return json.toString();
    }

    @RequestMapping(value = "/userList_v2")
    public String getSearchResultViaAjaxV2(           
            HttpServletRequest request
         ) throws JSONException {
        
        System.out.println("\nRecibiendo petición");
        Enumeration <String> par = request.getParameterNames();
        while (par.hasMoreElements()) {
            String aux = par.nextElement();
            System.out.println(aux +": " + request.getParameter(aux));
        }
        
        int draw = Integer.parseInt(request.getParameter("draw")) + 1;
        //System.out.println("Draw: " + draw);
        
        int num_registros = Integer.parseInt(request.getParameter("length"));
        System.out.println("Num regisros: " + num_registros);
        
        int inicio = Integer.parseInt(request.getParameter("start"));
        System.out.println("Inicio: " + inicio);
        
        // Obtenemos todos los usuarios sin ningun filtro
        List <Usuarios> _usuarios = usuarioService.get("enabled=1");
        int total_registros = _usuarios.size();
        
        // Miramos si hay algún filtro
        String filtro = request.getParameter("search[value]");
        System.out.println("Filtrar por: " + filtro);
        
        int reg_final = inicio + num_registros;
        if ( reg_final > _usuarios.size() ) reg_final = _usuarios.size();            
        List <Usuarios> usuarios = _usuarios.subList(inicio, reg_final );
        
        if ( usuarios!= null && usuarios.size() > 0 ) {
            for (int i = 0; i < usuarios.size(); i++) {
                TipusAnimal t = (TipusAnimal) tipusAnimalService.getone("idtipus=" + usuarios.get(i).getTipusAnimal());
                usuarios.get(i).settAnimal(  t.getDescripcio()  );
            }
        }
        
        ObjectMapper JSON_MAPPER = new ObjectMapper();
        JSONObject member = null;
        JSONArray array = new JSONArray();
        for (Usuarios user : usuarios) {
            try {
                member = new JSONObject(JSON_MAPPER.writeValueAsString(user));
                array.put(member);
            } catch (JsonProcessingException ex) {
                Logger.getLogger(AdminUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //System.out.println("Json array: " + array.toString());
        JSONObject json = new JSONObject();
        try {            
            json.put("recordsFiltered", _usuarios.size());            
            json.put("recordsTotal", _usuarios.size());
            json.put("draw", draw);
            json.put("data", array);
        } catch (JSONException ex) {
            Logger.getLogger(AdminUserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*  String resultado = "{"
                + "\"draw\": " + draw +","
                + "\"recordsTotal\":1,"
                + "\"recordsFiltered\":1,"
                + "\"data\": ["
                + "{"
                + "\"username\": \"edlobez\","
                + "\"nombre\": \"edu\","
                + "\"apellido1\": \"lopez\","
                + "\"email\": \"edo@do.com\","
                + "\"tAnimal\": \"gato\" "
                + "}"
                + "]"
                + "}";*/
        
        System.out.println(json.toString());
        return json.toString();
    }
    
    
    
    private Usuarios createUsuario(Usuarios usr, ModelMap modelo) {

        Usuarios usr_resultado;

        // Comprobamos que no exista ni el nombre de usuario ni el mail
        // Esta comprobación se podría quitar ya que mysql retornará null en el 
        // ... create del item al estar repetido cualquiera de los campos.
        if (usuarioService.getone("username=" + usr.getUsername()) != null) {
            modelo.addAttribute("error", "username_repetido");
            return null;
        }
        if (usuarioService.getone("email=" + usr.getEmail()) != null) {
            modelo.addAttribute("error", "email_repetido");
            return null;
        }
        if (usr.getRol() == 0) {
            //System.out.println("Error en rol");
            modelo.addAttribute("error", "error_rol");
            return null;
        }
        if (usr.getRol() == 2 || usr.getRol() == 3) {
            if (usr.getTipusAnimal() == 0) {
                //System.out.println("Error en tipo animal");
                modelo.addAttribute("error", "error_tAnimal");
                return null;
            }
        }
        if (usr.getRol() == 1 || usr.getRol() == 4) {
            usr.setTipusAnimal(3);
        }
        //  else {
        //System.out.println("Creando usuario");
        usr.setEnabled(true);
        usr_resultado = (Usuarios) usuarioService.create(usr);
        if (usr_resultado == null) {
            return null;
        }
        //System.out.println("\n\n\n\nUsuario creado: " + usr.toString());
        //   }

        return usr_resultado;

    }

    // TO - DO REACER DESPUES DEL CAMBIO DE LA TABLA ROLES
    private Usuarios updateUsuario(Usuarios usr, Usuarios usr_old, ModelMap modelo) {

        Usuarios usr_resultado = null;
        Roles rl_resultado;
        boolean username_modificado = false;
        // Si se ha modificado el username o email
        // Comprobamos que no exista ni el nombre de usuario ni el mail

        //System.out.println("Modificando: " + usr_old.toString());
        //System.out.println("Valor nuevo: " + usr.toString());
        //Campo rol o tipo animal si son 0 copiamos el antiguo
        if (usr.getRol() == 0) {
            usr.setRol(usr_old.getRol());
        }
        if (usr.getTipusAnimal() == 0) {
            usr.setTipusAnimal(usr_old.getTipusAnimal());
        }
        if (usr.getRol() == 2 || usr.getRol() == 3) {
            if (usr.getTipusAnimal() == 0) {
                //System.out.println("Error en tipo animal");
                modelo.addAttribute("error", "error_tAnimal");
                return null;
            }
        }
        if (usr.getRol() == 1 || usr.getRol() == 4) {
            usr.setTipusAnimal(3);
        }

        if (!usr_old.getEmail().equals(usr.getEmail())) {
            if (usuarioService.getone("email=" + usr.getEmail()) != null) {
                //System.out.println("Error al modificar el usuario, email repetido");
                modelo.addAttribute("error", "email_repetido");
                return null;
            }
        }

        String password;
        boolean changePass = true;
        if (usr.getPassword() == null || usr.getPassword().length() == 0) {
            //System.out.println("Se mantiene le password");
            //usr.setPassword(usr_old.getPassword());
            password = "";
        } else {
            //System.out.println("Se modifica el password");
            password = PasswordEncoderGenerator.passwordGenerator(usr.getPassword());

        }

        // String passCodificada = PasswordEncoderGenerator.passwordGenerator(usr.getPassword());
        System.out.println("Modificando: " + usr_old);
        System.out.println("Nuevo: " + usr);
        usr_resultado = (Usuarios) usuarioService.update(usr_old,
                "username=" + usr.getUsername() + ","
                + "password=" + password + ","
                //+ "enabled="  + 1 + ","
                + "changePass=" + ((changePass) ? 1 : 0) + ","
                + "nombre=" + usr.getNombre() + ","
                + "apellido1=" + usr.getApellido1() + ","
                + "apellido2=" + usr.getApellido2() + ","
                + "email=" + usr.getEmail() + ","
                + "rol=" + usr.getRol() + ","
                + "tipusAnimal=" + usr.getTipusAnimal());

        //System.out.println("\n\n\n\nUsuario creado: " + usr.toString() + " con rol:" + role);
        return usr_resultado;

    }

    private void ejemplos() {

        // SELECT * FROM USUARIOS
        System.out.println("\nTODOS LOS USUARIOS: "
                + usuarioService.getAll());
        // SELECT * FROM USUARIOS ORDER BY USERNAME
        System.out.println("\nTodos los usuarios ordenados por username: "
                + usuarioService.getAll("ORDER BY username"));
        // SELECT * FROM USUARIOS WHERE USERNAME = edlobez
        System.out.println("\nUsuarios de username edlobez: "
                + usuarioService.get("username=edlobez"));
        // SELECT * FROM USUARIOS WHERE USERNAME = %C% AND NOMBRE =%E% ORDER BY APELLIDO1
        System.out.println("Usuarios que contengan un 'C' es username y una 'E' en el nombre, ordenados por el apellido: "
                + usuarioService.getAND("ORDER BY apellido1", "username=%C%,nombre=%c%"));
        // UPDATE USUARIOS SET APELLIDO=1000 NOMBRE=ADMIN WHERE USERNAME = EDLOBE
        Usuarios usr = (Usuarios) usuarioService.getone("username=edlobez");
        System.out.println("Modificando usuario " + usr.toString() + ", cambiando nombre y apellido: "
                + usuarioService.update(usr, "nombre=admin,apellido1=1000"));

        // SELECT * FROM ANIMAL
        System.out.println("Todos los animales: " + animalService.getAll());
    }

}
