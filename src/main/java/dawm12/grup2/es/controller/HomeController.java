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

import dawm12.grup2.es.service.Service;
import java.io.IOException;
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
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author DAW_M12_grup2
 */

@Controller
public class HomeController {    
    
    @RequestMapping(value = {"/", "/home"})    
    public ModelAndView homeRequest (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
        
        ModelAndView modelview = new ModelAndView();
        String mv = "home";
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("admin"))) {
            mv = "redirect:/admin/home";
            System.out.println("El admin");
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("responsable"))){
            mv = "redirect:/responsable/home";
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("veterinari"))){
            mv = "redirect:/veterinari/home";
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("voluntari"))){
            mv = "redirect:/voluntari/home";
        }
        
        modelview.setViewName(mv);
        //modelview.setViewName("presentacion");        
        
        return modelview;
        
    }  
    
     
}
