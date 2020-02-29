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
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dawm12.grup2.es.domain.Roles;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    @Qualifier("usuarioService")
    private Service usuarioService;
    
    @Autowired
    @Qualifier("rolesService")
    private Service rolesService;

    @RequestMapping("/home")
    public ModelAndView adminHome(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ModelAndView modelview = new ModelAndView();
        modelview.setViewName("admin");

       /* Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("admin"))) {
            System.out.println("\nES EL ADMIN!!!");
        }*/

        return modelview;
    }
    
    @RequestMapping(value = "/editUser") 
    public ModelAndView editUser (@RequestParam("username") String username) {
        
               
        Usuarios usr = (Usuarios)usuarioService.getone("username="+username);
        System.out.println("Editando: "+ usr.toString());
        
        usuarioService.update(usr, "nombre=eduardo");
        
        return new ModelAndView("admin");
    }
    
    @RequestMapping(value = "/deleteUser") 
    public String deleteUser (@RequestParam("username") String username) {
        
        //System.out.println ("Borrar usuario: " + username);
        
        return "Borrar usuario " + username;
    }
    
    @RequestMapping(value = "/newUser") 
    public ModelAndView newUser (ModelMap modelo) {
        
      /*  System.out.println ("Nuevo usuario");
        
        //public Usuarios(String username,String password, boolean enabled, String nombre, String apellido1, String apellido2, String email) {

        Usuarios usr = new Usuarios ("cinco", "5555", true, "Cinco", "apellido5", null, "mail5@mail.com");
        Roles rl = new Roles("cinco", "user");
        usuarioService.create(usr);
        rolesService.create(rl);*/
      
        Usuarios usr = new Usuarios ();
        Roles rl = new Roles ();
        modelo.addAttribute("usuario", usr);
        modelo.addAttribute("rol", rl);
        ModelAndView mv = new ModelAndView ("newUser");
        
        
        return mv;
    }

    @RequestMapping(value = "/saveUser") 
    public ModelAndView guardarUser ( Model modelo, 
            @ModelAttribute("usuario") Usuarios usr,
            @RequestParam("role") String role) {
        
        //System.out.println("\n\n\nGuardado: " + usr.toString());
      /*  System.out.println ("Nuevo usuario");
        
        //public Usuarios(String username,String password, boolean enabled, String nombre, String apellido1, String apellido2, String email) {

        Usuarios usr = new Usuarios ("cinco", "5555", true, "Cinco", "apellido5", null, "mail5@mail.com");
        Roles rl = new Roles("cinco", "user");
        usuarioService.create(usr);
        rolesService.create(rl);*/
      
        if (usuarioService.getOR("username="+usr.getUsername()+",email="+usr.getEmail()).size() > 0) {
            System.out.println("Error al crear el usuario, username o email repetido");
            return new ModelAndView("newUser");            
        }
        else {
            //TO - DO CONTROL DE ERRORES!!!!
            Roles rl = new Roles();
            //rl.setUsername(usr.getUsername());
            //rl.setRole(role);
            // usuarioService.create(usr);           
           // rolesService.create(rl);
            System.out.println("\n\n\n\nUsuario creado: " + usr.toString()+ " con rol:" + role);
        }
        
        return new ModelAndView("admin");
    }
    
    @RequestMapping(value = "/userList")
    public String getSearchResultViaAjax(
            @RequestBody String search, 
            @RequestParam("current") String actual,
            @RequestParam("rowCount") String numFilas,
            @RequestParam("searchPhrase") String cadenaBusqueda) throws JSONException {
        
        //System.out.println("Cadena completa:" + search);
        //System.out.println("Un parámetro (current): " + actual);
        //System.out.println("num filas a mostrar: " + numFilas);
        //System.out.println("Buscar: " + cadenaBusqueda);
        
        //System.out.println("Todos los usuarios: " + usuarioService.getAll());
        //System.out.println("Con argumentos:" + usuarioService.getAND("nombre=%m%,apellido1=%1%"));
        //System.out.println("Sin argumentos:" + usuarioService.get("nombre=%tec%"));
        
        //System.out.println(usuarioService.get("BETWEEN 4 AND 5", "apellido1"));
        
        ejemplos();
        
        List <Usuarios> lista = new ArrayList <Usuarios> ();
        if (cadenaBusqueda.length() == 0 ){
            String aux = "";
            if (Integer.parseInt(numFilas) != -1)
                aux = "LIMIT " + numFilas;
             //lista = usuarioService.getAll(("ORDER BY username ASC " + aux).trim());
             lista = usuarioService.get(aux, "enabled=1");
        }
        else 
        {
            lista = usuarioService.getAND("ORDER BY apellido1 ASC","username=%" + cadenaBusqueda + "%,enabled=1");
        }
        
        ObjectMapper JSON_MAPPER = new ObjectMapper();
        JSONObject member = null;
        JSONArray array = new JSONArray();
        for (Usuarios user:lista) {
            try {
                member = new JSONObject(JSON_MAPPER.writeValueAsString(user));
                array.put(member);
            } catch (JsonProcessingException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //System.out.println("Json array: " + array.toString());
        
        JSONObject json = new JSONObject();
             try {
                 json.put("rows", array);
                 json.put("total", lista.size());
                 json.put("rowCount", numFilas);
                 json.put("current", actual);                 
             } catch (JSONException ex) {
                 Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
             }
       
        
      /*  String result = "{\"current\":1, "
                + "\"rowCount\":10,"
                + "\"total\":14,"
                + "\"rows\": ["
                + "{\"id\":1,\"name\": \"edu\",\"correo\": \"mkyong@yahoo.com\"},"
                + "{\"id\":2,\"name\": \"mkyong\",\"correo\": \"mkyong@yahoo.com\"},"
                + "{\"id\":3,\"name\": \"mkyong\",\"correo\": \"mkyong@yahoo.com\"},"
                + "{\"id\":4,\"name\": \"mkyong\",\"correo\": \"mkyong@yahoo.com\"},"
                + "{\"id\":5,\"name\": \"mkyong\",\"correo\": \"mkyong@yahoo.com\"},"
                + "{\"id\":6,\"name\": \"mkyong\",\"correo\": \"mkyong@yahoo.com\"},"
                + "{\"id\":7,\"name\": \"mkyong\",\"correo\": \"mkyong@yahoo.com\"},"
                + "{\"id\":8,\"name\": \"mkyong\",\"correo\": \"mkyong@yahoo.com\"},"
                + "{\"id\":9,\"name\": \"mkyong\",\"correo\": \"mkyong@yahoo.com\"},"
                + "{\"id\":10,\"name\": \"mkyong\",\"correo\": \"mkyong@yahoo.com\"},"
                + "{\"id\":11,\"name\": \"mkyong\",\"correo\": \"mkyong@yahoo.com\"},"
                + "{\"id\":12,\"name\": \"mkyong\",\"correo\": \"mkyong@yahoo.com\"},"
                + "{\"id\":13,\"name\": \"mkyong\",\"correo\": \"mkyong@yahoo.com\"},"
                + "{\"id\":14,\"name\": \"mkyong\",\"correo\": \"mkyong@yahoo.com\"}"
                + "]}";*/

        //System.out.println(json.toString());
        return json.toString();
    }
    
    private void ejemplos () {
        
        // SELECT * FROM USUARIOS
        System.out.println("\nTODOS LOS USUARIOS: " +
                usuarioService.getAll());
        // SELECT * FROM USUARIOS ORDER BY USERNAME
        System.out.println("\nTodos los usuarios ordenados por username: " + 
               usuarioService.getAll("ORDER BY username"));
        // SELECT * FROM USUARIOS WHERE USERNAME = edlobez
        System.out.println("\nUsuarios de username edlobez: " + 
                usuarioService.get("username=edlobez"));
        // SELECT * FROM USUARIOS WHERE USERNAME = %C% AND NOMBRE =%E% ORDER BY APELLIDO1
        System.out.println("Usuarios que contengan un 'C' es username y una 'E' en el nombre, ordenados por el apellido: " +
                usuarioService.getAND("ORDER BY apellido1", "username=%C%,nombre=%c%"));
        // UPDATE USUARIOS SET APELLIDO=1000 NOMBRE=ADMIN WHERE USERNAME = EDLOBE
        Usuarios usr = (Usuarios)usuarioService.getone("username=edlobez");        
        System.out.println("Modificando usuario "+ usr.toString()+ ", cambiando nombre y apellido: " + 
             usuarioService.update( usr, "nombre=admin,apellido1=1000"));         
        
    }

}