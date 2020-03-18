/*
 * Copyright (C) 2020 DAW_M12_grup2
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

import dawm12.grup2.es.domain.Accesos;
import dawm12.grup2.es.domain.Usuarios;
import dawm12.grup2.es.service.Service;
import java.io.IOException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author DAW_M12_grup2
 */

@Controller
public class HomeController {   
    
    @Autowired @Qualifier("accesosService")
    private Service accesosService;
    
    @Autowired @Qualifier("usuarioService")
    private Service usuarioService;
    
    @RequestMapping(value = {"/", "/home"})    
    public ModelAndView homeRequest (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
        
        ModelAndView modelview = null;
        String mv = "home";
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    /*    if (auth.getAuthorities().contains(new SimpleGrantedAuthority("admin"))) {
            mv = "redirect:/admin/home";                       
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("responsable"))){
            mv = "redirect:/responsable/home";
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("veterinari"))){
            mv = "redirect:/veterinari/home";
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("voluntari"))){
            mv = "redirect:/voluntari/home";
        }
        */
        //modelview =         
        guardarAcceso(auth.getName()); 
        modelview = comprobarPassword(auth.getName(), request.getParameter("error"));
        //modelview.setViewName(mv);
        //modelview.setViewName("presentacion"); 
        return modelview;
        
    }  
    
    private void guardarAcceso (String username) {
        accesosService.create(new Accesos(username, horaActual()));
        //return new ModelAndView("home") ;
        
    }
    
    private Timestamp horaActual () {        
        java.util.Date d = new java.util.Date();        
        Timestamp date2 = new Timestamp(d.getTime());        
        return date2;        
    }
    
    /*
    Si el usuario se logea por primera vez después de que el admin le creara
    la cuenta, deberá cambiar la contraseña
    */
    private ModelAndView comprobarPassword(String username, @RequestParam ("error") String error) {
        
        ModelAndView mw = new ModelAndView();
        
        Usuarios usr = (Usuarios)usuarioService.getone("username="+username);
        if ( usr.isChangePass() ) {
            System.out.println("El usuario debe cambiar el password");  
            mw.addObject("usuario", usr);
            if ( error != null )
                mw.addObject("error", "password_error");
            mw.setViewName("chgPassword");
            return mw;
            
        }
        mw = new ModelAndView ("home");
        return mw;
    }
     
}
