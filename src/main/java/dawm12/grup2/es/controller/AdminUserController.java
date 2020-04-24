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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author edlobez
 */
@Controller
@RestController
//@RequestMapping("/admin")
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

    @RequestMapping(value = {"/admin/users", "/responsable/users"})
    public ModelAndView listaUsuarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ModelAndView modelview = new ModelAndView("listaUsuarios");
        return modelview;
    }

    @RequestMapping("/admin/pets")
    public ModelAndView adminPets(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ModelAndView modelview = new ModelAndView("listaAnimales");
        return modelview;
    }

 
    @RequestMapping(value = {"/admin/editUser"})
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

    @RequestMapping(value = "/admin/deleteUser")
    public ModelAndView deleteUser(
            @ModelAttribute("usuario") Usuarios usr) {
        //System.out.println ("Borrar usuario: " + usr.toString());
        
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

    @RequestMapping(value = "/admin/newUser")
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
    @RequestMapping(value = "/admin/saveUser")
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

                //System.out.println("Se va modificar el password un usuario: " + password_new);
                //System.out.println("Password nuevo: " + cpassword_new);
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

    @RequestMapping(value = {"/admin/userList", "/responsable/userList"})
    public String getSearchResultViaAjax(           
            HttpServletRequest request
         ) throws JSONException {
        
        String campos_tabla [] = {"username", "nombre", "apellido1", "email", "tipusAnimal"};
        
      /*  System.out.println("\nRecibiendo petición");
        Enumeration <String> par = request.getParameterNames();
        while (par.hasMoreElements()) {
            String aux = par.nextElement();
            System.out.println(aux +": " + request.getParameter(aux));
        }*/
        
        // Cada petición debemos sumar 1 a este parámetro
        int draw = Integer.parseInt(request.getParameter("draw")) + 1;
        //System.out.println("Draw: " + draw);
        
        // Número de registros a mostrar
        int num_registros = Integer.parseInt(request.getParameter("length"));
        //System.out.println("Num regisros: " + num_registros);
        
        // Primer registro a mostrar, depende de la página donde estamos
        int inicio = Integer.parseInt(request.getParameter("start"));
        //System.out.println("Inicio: " + inicio);      
        
        
        // Miramos si hay algún filtro
        String cadenaBusqueda = request.getParameter("search[value]");
        //System.out.println("Filtrar por: " + cadenaBusqueda);
        
        // La columna por la que buscar
        int buscar_por = Integer.parseInt( request.getParameter("order[0][column]") );
        String busqueda_por = campos_tabla[buscar_por];
        //System.out.println("Buscar por: " + campos_tabla[buscar_por]);
        
        //Ordernar ascendenteo o descendente
        String order_dir = request.getParameter("order[0][dir]");
        //System.out.println("Order dir: " + order_dir);
        
        // El listado dependera de si lo solicita un admin, que se muestran
        // ... todos o bien un responsable, que sólo mostrará usuarios de su tipo
        // Filtrar lista por rol
        String lista_rol = "";
        if ( rolActual().equals("responsable")) {            
            Usuarios u = (Usuarios) usuarioService.getone("username="+usuarioActual());
            lista_rol = ",tipusAnimal=" + u.getTipusAnimal();            
        }
        
        // Si vamos a buscar por tipo de animal, debemos cambiar la cadena
        // ... de búsqueda
        if ( busqueda_por.equals("tipusAnimal")) {
            if ( cadenaBusqueda.toLowerCase().startsWith("g") ) 
                cadenaBusqueda = "1";
            else if ( cadenaBusqueda.toLowerCase().startsWith("c") )
                cadenaBusqueda = "2";
            else if ( cadenaBusqueda.toLowerCase().startsWith("t") ) 
                cadenaBusqueda = "3";
        }
        
        // Cadena complementario a la busqueda      
        String aux = "ORDER BY " + campos_tabla[buscar_por] + " " + order_dir + " ";
        List <Usuarios> _usuarios = new ArrayList <>();
        _usuarios = usuarioService.getAND (aux, "enabled=1" + lista_rol);
        // Obtenemos todos los usuarios sin ningun filtro        
        int total_registros = _usuarios.size();
        if (cadenaBusqueda.length() > 0 ) {            
            _usuarios = usuarioService.getAND(aux, busqueda_por + "=%" + cadenaBusqueda + "%,enabled=1"+ lista_rol);
        } 
        
        int reg_final = inicio + num_registros;
        if ( reg_final > _usuarios.size() ) reg_final = _usuarios.size();            
        List <Usuarios> usuarios = _usuarios.subList(inicio, reg_final );
        
        if ( usuarios!= null && usuarios.size() > 0 ) {
            for (int i = 0; i < usuarios.size(); i++) {
                TipusAnimal t = (TipusAnimal) tipusAnimalService.getone("idtipus=" + usuarios.get(i).getTipusAnimal());
                usuarios.get(i).settAnimal(  t.getDescripcio()  );
                Roles r = (Roles) rolesService.getone ("idrol=" + usuarios.get(i).getRol());
                usuarios.get(i).setSrol(r.getRol());
                
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
            json.put("recordsTotal", total_registros);
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
        
        //System.out.println(json.toString());
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
    
    private String rolActual () {
        
        String rol = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("admin"))) {
            rol = "admin";
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("responsable"))){
           rol = "responsable";
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("veterinari"))){
            rol = "veterinari";
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("voluntari"))){
            rol = "voluntari";
        }
        return rol;
    }
    
    private String usuarioActual () {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
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
