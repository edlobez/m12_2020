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
package dawm12.grup2.es.repository.imp;

/**
 *
 * @author edlobez
 */
public class Query {
    
      // private static String OR = "OR";
   // private static String AND = "AND";
    private static String LIKE = "LIKE";
    private static String EQUAL = "=";
    
    private String query;
    private String campo []; //= new String [campos.length];
    private String valor [];// = new String [campos.length];
    
    public String [] getValor ()  {
        return this.valor;
    }
    
    public String createQueryUpdate (String nomTabla, String key, String... campos) {
        
        campo= new String [campos.length];
        valor= new String [campos.length];
        key = normalizar(key);
        nomTabla = nomTabla.toUpperCase();
        
        String qry;
        //edlobez.es.Debug.printDebug("Nombre tabal: " + nomTabla + "\nCampos: ");
        int index = 0;
        for (String s: campos ) {
            //s = s.toUpperCase();
            campo[index] = s.split("=")[0];
            valor[index] = s.split("=")[1];
            index++;
        }
      
        qry = "UPDATE " + nomTabla + " SET ";
            int aux = campo.length;
            for ( int i = 0; i < aux; i++ ) {
                qry = qry + campo[i] + " = ? ";
                if ( i < (aux -1 )) {
                    qry = qry + ", ";
                }
            }
       // }
       qry = qry + "WHERE " + key;
       qry = qry.trim();
       //edlobez.es.Debug.printDebug(qry);
       return qry;
        
    }
    
    public String createQueryInsert (String nomTabla, String... campos) {
        
        campo= new String [campos.length];
        valor= new String [campos.length];
        nomTabla = nomTabla.toUpperCase();
        
     //   edlobez.es.Debug.printDebug("Creando query select");
        int index = 0;
        for (String s: campos ) {  
            //s = s.toUpperCase();
            campo[index] = s.split("=")[0];
            valor[index] = s.split("=")[1];
            index++;
        }
     
        String qry="";
     /*   if (campo.length == 0 )
           edlobez.es.Debug.printDebug("Error lanzar exception");
        else */
          if (campo.length != 0)
              qry = "INSERT INTO " + nomTabla + " (";
   
        int aux = campo.length;
        for (int i = 0; i < aux; i++) {
            qry = qry + campo[i];
            if (i < (aux - 1)) {
                qry = qry + ", ";
            }
        }
       
       qry = qry + ") VALUES (";
        
        aux = campo.length;
        for (int i = 0; i < aux; i++) {
            if (i < (aux - 1)) {
                qry = qry + "?, ";
            }
        }
       qry = qry + "?)";
       qry = qry.trim();
       //edlobez.es.Debug.printDebug(qry);
       return qry;
    }
            
    public String createQuerySelect (String nomTabla, String tipo_busqueda, String args, String campos) {
        if (args == null || args.length() == 0 )
            return createQuerySelect (nomTabla, tipo_busqueda, campos);
        else {//throw new UnsupportedOperationException("Not supported yet.");
            System.out.println ("CON argumentos: " + createQuerySelect (nomTabla, tipo_busqueda, campos) + " " + args);
            return createQuerySelect (nomTabla, tipo_busqueda, campos) + " " + args;
        }
        //return null;
    }
    
    public String createQuerySelect (String nomTabla, String tipo_busqueda, String _campos) {
        
        String operador_busqueda = EQUAL;
        String condicion_busqueda = tipo_busqueda;
        String campos [] = null;
        
        System.out.println ("En query antes de split: " + _campos);
        if (_campos!= null) {
            campos = _campos.split(",");
            System.out.println("En query después de split: ");
            for (String s : campos)
            System.out.println(s);
        }
        else campos = new String [0];
        
        
        
        //nombre=xx,apellido=2
        campo= new String [campos.length];
        valor= new String [campos.length];
        
        nomTabla = normalizar(nomTabla);
        
      //  edlobez.es.Debug.printDebug("Nombre tabla: " + nomTabla + "\nCampos: ");
        int index = 0;
        for (String s: campos ) {  
            s = normalizar(s);
            campo[index] = s.split("=")[0];
            valor[index] = s.split("=")[1];
            if ( valor[index].contains("%") )
                operador_busqueda = LIKE;
            index++;
        }
      /*  for ( int i = 0; i < index; i++) {
            edlobez.es.Debug.printDebug("Campo: " + campo[i]);
            edlobez.es.Debug.printDebug("Valor: " + valor[i]);
        }*/
      String qry;
      if (campo.length == 0 )
         qry = "SELECT * FROM " + nomTabla;
      else 
        qry = "SELECT * FROM " + nomTabla + " WHERE ";  
      int aux = campo.length;
      for (int i = 0; i < aux; i++) {
        qry = qry + campo[i] + " " + operador_busqueda + " ? ";
        if (i < (aux - 1)) {
            qry = qry + " " + condicion_busqueda + " ";
        }
      }
      
      
       qry = qry.trim();
      // edlobez.es.Debug.printDebug(qry);
       return qry;
    }
    /*
    public String createQuerySelect (String nomTabla, String tipo_busqueda, String... campos) {
        
        String operador_busqueda = EQUAL;
        String condicion_busqueda = tipo_busqueda;
        
        //nombre=xx,apellido=2
        campo= new String [campos.length];
        valor= new String [campos.length];
        
        nomTabla = normalizar(nomTabla);
        
      //  edlobez.es.Debug.printDebug("Nombre tabla: " + nomTabla + "\nCampos: ");
        int index = 0;
        for (String s: campos ) {  
            s = normalizar(s);
            campo[index] = s.split("=")[0];
            valor[index] = s.split("=")[1];
            if ( valor[index].contains("%") )
                operador_busqueda = LIKE;
            index++;
        }
      /*  for ( int i = 0; i < index; i++) {
            edlobez.es.Debug.printDebug("Campo: " + campo[i]);
            edlobez.es.Debug.printDebug("Valor: " + valor[i]);
        }*//*
      String qry;
      if (campo.length == 0 )
         qry = "SELECT * FROM " + nomTabla;
      else 
        qry = "SELECT * FROM " + nomTabla + " WHERE ";  
        int aux = campo.length;
        for ( int i = 0; i < aux; i++ ) {
            qry = qry + campo[i] + " "+ operador_busqueda +" ? ";
            if ( i < (aux -1 )) {
                qry = qry + " " + condicion_busqueda + " ";
            }
        }
      
       qry = qry.trim();
      // edlobez.es.Debug.printDebug(qry);
       return qry;
    }*/
    
    /*
     Sustituye acentos y cualquier vocal acentuada por un símbolo comodín
    */
    private String normalizar (String inText ) {
        String originalchars = "áéíóúäëïöüâêîôûàèìòùÁÉÍÓÚÄËÏÖÜÂÊÎÔÛÀÈÌÒÙ%";
        String output = "";
        char ch;
        int pos;

        for (int i = 0; i < inText.length(); i++) {
                pos = originalchars.indexOf(inText.charAt(i));
                if (pos != -1)
                        output = output + "%";
                else {
                        output = output + inText.charAt(i);
                }
        }
        //output= output.toUpperCase();             
        return output;
        
        
    }
    
}
