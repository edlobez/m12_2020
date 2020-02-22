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

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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

    @RequestMapping(value = "/userList")
    public String getSearchResultViaAjax_(
            @RequestBody String search, 
            @RequestParam("current") String actual,
            @RequestParam("rowCount") String numFilas,
            @RequestParam("searchPhrase") String cadenaBusqueda) {
        
        System.out.println("Cadena compleata:" + search);
        System.out.println("Un parámetro (current): " + actual);
        System.out.println("num filas a mostrar: " + numFilas);
        System.out.println("Buscar: " + cadenaBusqueda);
        
        
        String result = "{\"current\":1, "
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
                + "]}";

        System.out.println(result);
        return result;
    }

}
