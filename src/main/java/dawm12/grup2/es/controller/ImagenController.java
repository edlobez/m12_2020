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

import dawm12.grup2.es.service.Service;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author edlobez
 *
 * http://jsfiddle.net/Soldier/YqQ9b/1/
*/
@Controller
@RequestMapping("/imagenes")
public class ImagenController {
    
    @Autowired
    private JdbcTemplate jdbc;
    
    @RequestMapping("/listImagen")
    public ModelAndView listImagen ( ModelMap modelo) {
       String sql = "SELECT * FROM imagen";
        List<Map<String, Object>> imageList = jdbc.queryForList(sql);

        return new ModelAndView("pruebaImagenCargador", "lista", imageList);
    }
    
    @RequestMapping("/imagen")
    public ModelAndView imagen (@RequestParam ("nombre") String nombre ) {
        return new ModelAndView ("pruebaImagenVisor", "nombre", nombre);
    }
    
    @RequestMapping(path="/form", method = RequestMethod.POST)
    public String handleFormUpload (
            @RequestParam ("file") MultipartFile file,
            @RequestParam ("id") int id
    ) throws IOException {
        
        System.out.println("Insertando imagen para animal id " + id);
        String sql = "INSERT INTO imagen (idAnimal, nombre, tipo, tamano, pixel) VALUES(?, ?, ?, ?, ?)";
        
        if ( !file.isEmpty() ) {
            String nombre = file.getOriginalFilename();
            String tipo = file.getContentType();
            Long tamano = file.getSize();
            byte[] pixel = file.getBytes(); 
            jdbc.update(sql, id, nombre, tipo, tamano, pixel);
        }        
        return "redirect:/home";
    }
    
  /*  @RequestMapping(value = "/uploaded")
    public void getUploadedPicture (
        @RequestParam("nombre") String nombre, HttpServletResponse response, ModelMap modelo) throws IOException, SQLException {
        
        String sql = "SELECT pixel, tipo FROM imagen WHERE nombre = '" + nombre + "'";
        List<Map<String, Object>> result = jdbc.queryForList(sql);

        if (!result.isEmpty()) {
            byte[] bytes = (byte[]) result.get(0).get("PIXEL");
            String mime = (String) result.get(0).get("TIPO");

            response.setHeader("Content-Type", mime);
            response.getOutputStream().write(bytes);
        }
        
    }*/
    
    @RequestMapping(value = "/uploaded")
    public void getUploadedPicture (
        HttpServletResponse response, 
        int id) throws IOException, SQLException {
        
        String sql = "SELECT pixel, tipo FROM imagen WHERE idAnimal = '" + id + "'";
        List<Map<String, Object>> result = jdbc.queryForList(sql);

        if (!result.isEmpty()) {
            byte[] bytes = (byte[]) result.get(0).get("PIXEL");
            String mime = (String) result.get(0).get("TIPO");

            response.setHeader("Content-Type", mime);
            response.getOutputStream().write(bytes);
        }
        else {
            System.out.println("No hay imagen");   
            response.getOutputStream().write(0);
            
        }
        
    }
    
    
    
}
