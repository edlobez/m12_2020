/*
 * Copyright (C) 2020 eduardo.lopez
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

import dawm12.grup2.es.PasswordEncoderGenerator;
import dawm12.grup2.es.domain.Roles;
import dawm12.grup2.es.domain.TipusAnimal;
import dawm12.grup2.es.domain.Usuarios;
import dawm12.grup2.es.service.Service;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
 * @author eduardo.lopez
 */
@Controller
@RestController
@RequestMapping("/user")
public class AllUserController {

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

    /*
    Validación del resultado formulario user.jsp
    TO-DO ELIMINAR TANTOS BLOQUES DE CÓDIGO REPETIDO
     */
    @RequestMapping(value = "/saveUser")
    public ModelAndView guardarUser(
            @Valid @ModelAttribute("usuario") Usuarios usr,
            BindingResult validacion,
            @RequestParam("accion") String accion,
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

        if (accion.equals("update")) {
            //System.out.println("\n\nVamos hacer update de " + usr + " y " + _usr_copy);
            // Modificar el password de un usuario

            String password_old = request.getParameter("password_update_old");
            String password_new = request.getParameter("password_new");
            String cpassword_new = request.getParameter("cpassword_new");

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
                } else if (!new BCryptPasswordEncoder().matches(password_old, _u.getPassword())) {
                    error = true;
                    modelo.addAttribute("error", "password_error");
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
                }
            } else {
                // Si llega hasta aquí intentamos el update
                // Cargamos el password
                usr.setPassword(password_new);
            }

            if (updateUsuario(usr, _usr_copy, modelo) == null) {
                modelo.addAttribute("usuario", usr);
                //modelo.addAttribute("error", "error_update");
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

        return new ModelAndView("redirect:/home" + param);
    }

    /*
    Guardar password del primer login
     */
    @RequestMapping(value = "/savePassword")
    public ModelAndView savePassword(ModelMap modelo,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("cpassword") String cpassword) {

        ModelAndView mw = new ModelAndView();
        Usuarios usr = (Usuarios) usuarioService.getone("username=" + username);

        if (!password.equals(cpassword) || password.length() == 0 || password.length() < 4) {
            //    System.out.println("\n\n--ERROR AL CREAR CONTRASEÑAS NO COINCIDEN");
            mw.addObject("usuario", usr);
            mw.addObject("error", "password_error");
            mw.setViewName("redirect:/home");
            return mw;

        }

        //System.out.println("Guardando password: " + password + " para usuario " + usr.toString());
        usuarioService.update(usr, "changePass=0");
        usuarioService.update(usr, "password=" + PasswordEncoderGenerator.passwordGenerator(password));

        return new ModelAndView("redirect:/home");

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

        System.out.println("\n\nEmail anterior: " + usr_old.getEmail());
        System.out.println("Email nuevo: " + usr.getEmail());
        if (!usr_old.getEmail().equals(usr.getEmail())) {
            System.out.println("Se va modificar el email");
            if (usuarioService.getone("email=" + usr.getEmail()) != null) {
                modelo.addAttribute("error", "email_repetido");
                System.out.println("Error al modificar el usuario, email repetido");
                return null;
            }
        }

        String password;
        //boolean changePass = true;
        if (usr.getPassword() == null || usr.getPassword().length() == 0) {
            //System.out.println("Se mantiene le password");
            //usr.setPassword(usr_old.getPassword());
            password = "";
        } else {
            //System.out.println("Se modifica el password");
            password = PasswordEncoderGenerator.passwordGenerator(usr.getPassword());

        }

        // String passCodificada = PasswordEncoderGenerator.passwordGenerator(usr.getPassword());        
        System.out.println("\n\nModificando: " + usr);
        usr_resultado = (Usuarios) usuarioService.update(usr_old,
                "username=" + usr.getUsername() + ","
                + "password=" + password + ","
                //+ "enabled=" + ((usr.isEnabled()) ? 1 : 0) + ","
                //+ "changePass=" + 1 + ","
                + "nombre=" + usr.getNombre() + ","
                + "apellido1=" + usr.getApellido1() + ","
                + "apellido2=" + usr.getApellido2() + ","
                + "email=" + usr.getEmail() + ","
                + "rol=" + usr.getRol() + ","
                + "tipusAnimal=" + usr.getTipusAnimal());

        //System.out.println("\n\n\n\nUsuario creado: " + usr.toString() + " con rol:" + role);
        return usr_resultado;

    }

}
