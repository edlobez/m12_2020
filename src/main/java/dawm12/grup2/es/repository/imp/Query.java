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

import dawm12.grup2.es.myExceptions.MyException;

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
    private String campo[]; //= new String [campos.length];
    private String valor[];// = new String [campos.length];

    public String[] getValor() {
        return this.valor;
    }

    /**
     *
     * @param nomTabla
     * @param key
     * @param _campos
     * @return
     */
    public String createQueryUpdate(String nomTabla, String key, String _campos) throws MyException {

        String campos[] = null;

        //System.out.println ("En queryUpdate antes de split: " + _campos);
        if (_campos != null) {
            campos = _campos.split(",");
            //System.out.println("En queryUpdate después de split: ");
            //for (String s : campos)
            //System.out.println(s);
        } else {
            campos = new String[0];
            throw new MyException(MyException.ERROR_UPDATE_1);
        }
        
        int aux_long = campos.length;
        campo = new String[aux_long];
        valor = new String[aux_long];
        key = normalizar(key);
        key = key.replace("=", "='");
        key = key + "'";
        //nomTabla = nomTabla.toUpperCase();

        String qry;
        //edlobez.es.Debug.printDebug("Nombre tabal: " + nomTabla + "\nCampos: ");
        // Hacemo comprobaciones por si algun valor está en blanco
        int index = 0;
        for (String s : campos) {
            //s = s.toUpperCase();
            //System.out.println(s.split("=").length);
            if (s.split("=").length == 2) {
                campo[index] = s.split("=")[0];
                valor[index] = s.split("=")[1];

                // System.out.println("Campo: " + campo[index]);
                //  System.out.println("Valor: " + valor[index]);
                index++;
            }
        }

         if (index < aux_long) {
            String _campo[] = new String[index];
            String _valor[] = new String[index];
            for (int i = 0; i < index; i++) {
                _campo[i] = campo[i];
                _valor[i] = valor[i];
            }
            campo = new String[index];
            valor = new String[index];
            campo = _campo;
            valor = _valor;

        }

        qry = "UPDATE " + nomTabla + " SET ";
        int aux = campo.length;
        for (int i = 0; i < aux; i++) {
            qry = qry + campo[i] + " = ? ";
            if (i < (aux - 1)) {
                qry = qry + ", ";
            }
        }
        // }
        qry = qry + "WHERE " + key;
        qry = qry.trim();
        //edlobez.es.Debug.printDebug(qry);
        //System.out.println("Query update: " +qry);
        return qry;

    }

    public String createQueryInsert(String nomTabla, String _campos) {

        String campos[] = null;

        //System.out.println ("En queryInsert antes de split: " + _campos);
        if (_campos != null) {
            campos = _campos.split(",");
            //System.out.println("En queryInsert después de split: ");
            //for (String s : campos)
            //System.out.println(s);
        } else {
            campos = new String[0];
        }

        int aux_long = campos.length;
        campo = new String[aux_long];
        valor = new String[aux_long];
        //nomTabla = nomTabla.toUpperCase();

        //   edlobez.es.Debug.printDebug("Creando query select");
        // Hacemo comprobaciones por si algun valor está en blanco
        int index = 0;
        for (String s : campos) {
            //s = s.toUpperCase();
            //System.out.println(s.split("=").length);
            if (s.split("=").length == 2) {
                campo[index] = s.split("=")[0];
                valor[index] = s.split("=")[1];

                // System.out.println("Campo: " + campo[index]);
                //  System.out.println("Valor: " + valor[index]);
                index++;
            }
        }

        /*for (int i = 0; i < index; i++) {
            System.out.println("Campo: " + campo[i]);
            System.out.println("Valor: " + valor[i]);
        }*/
        if (index < aux_long) {
            String _campo[] = new String[index];
            String _valor[] = new String[index];
            for (int i = 0; i < index; i++) {
                _campo[i] = campo[i];
                _valor[i] = valor[i];
            }
            campo = new String[index];
            valor = new String[index];
            campo = _campo;
            valor = _valor;

        }

        String qry = "";
        /*   if (campo.length == 0 )
           edlobez.es.Debug.printDebug("Error lanzar exception");
        else */
        if (index != 0) {
            qry = "INSERT INTO " + nomTabla + " (";
        }

        int aux = index;
        for (int i = 0; i < aux; i++) {
            qry = qry + campo[i];
            if (i < (aux - 1)) {
                qry = qry + ", ";
            }
        }

        qry = qry + ") VALUES (";

        aux = index;
        for (int i = 0; i < aux; i++) {
            if (i < (aux - 1)) {
                qry = qry + "?, ";
            }
        }
        qry = qry + "?)";
        qry = qry.trim();

        //System.out.println("Query insert:" + qry);
        //edlobez.es.Debug.printDebug(qry);
        return qry;
    }

    public String createQuerySelect(String nomTabla, String tipo_busqueda, String args, String campos) {
        if (args == null || args.length() == 0) {
            return createQuerySelect(nomTabla, tipo_busqueda, campos);
        } else if (args.toLowerCase().indexOf("between") > -1) {
            //System.out.println("hay un between");
            return createQuerySelectBetween(nomTabla, args, campos);
        } else {//throw new UnsupportedOperationException("Not supported yet.");
            //System.out.println ("CON argumentos: " + createQuerySelect (nomTabla, tipo_busqueda, campos) + " " + args);
            return createQuerySelect(nomTabla, tipo_busqueda, campos) + " " + args;
        }
        //return null;
    }

    public String createQuerySelectBetween(String nomTabla, String args, String _campo) {

        //SELECT column_name FROM TABLA WHERE column_name BETWEEN x AND y
        String qry;
        nomTabla = normalizar(nomTabla);
        String campos[] = null;
        String s_aux;
        int i_aux;

        s_aux = args.trim().substring("between".length(), args.length()).trim();

        //System.out.println("sin el between " + s_aux);
        if (s_aux != null) {
            campos = s_aux.toLowerCase().split("and");
            //System.out.println("En querySelect después de split: ");
            //for (String s : campos)
            //System.out.println(s);
        } else {
            campos = new String[0];
        }

        valor = new String[campos.length];
        valor[0] = campos[0].trim();
        valor[1] = campos[1].trim();

        qry = "SELECT * FROM " + nomTabla + " WHERE " + _campo + " between ? and ?";
        //System.out.println(qry);
        return qry;

    }

    public String createQuerySelect(String nomTabla, String tipo_busqueda, String _campos) {

        String operador_busqueda = EQUAL;
        String condicion_busqueda = tipo_busqueda;
        String campos[] = null;

        //System.out.println("En querySelect antes de split: " + _campos);
        if (_campos != null) {
            campos = _campos.split(",");
            //System.out.println("En querySelect después de split: ");
            //for (String s : campos) {
              //  System.out.println(s);
           // }
        } else {
            campos = new String[0];
        }

        //nombre=xx,apellido=2
        int aux_long = campos.length;
        campo = new String[aux_long];
        valor = new String[aux_long];

        nomTabla = normalizar(nomTabla);

        //  edlobez.es.Debug.printDebug("Nombre tabla: " + nomTabla + "\nCampos: ");
        int index = 0;
        for (String s : campos) {
            s = normalizar(s);
            if (s.split("=").length == 2) {
                campo[index] = s.split("=")[0];
                valor[index] = s.split("=")[1];
                if (valor[index].contains("%")) {
                    operador_busqueda = LIKE;
                }
                index++;
            }
        }
        /*  for ( int i = 0; i < index; i++) {
            edlobez.es.Debug.printDebug("Campo: " + campo[i]);
            edlobez.es.Debug.printDebug("Valor: " + valor[i]);
        }*/
        
        if (index < aux_long) {
            String _campo[] = new String[index];
            String _valor[] = new String[index];
            for (int i = 0; i < index; i++) {
                _campo[i] = campo[i];
                _valor[i] = valor[i];
            }
            campo = new String[index];
            valor = new String[index];
            campo = _campo;
            valor = _valor;
            
        }
        
        String qry;
        if (campo.length == 0) {
            qry = "SELECT * FROM " + nomTabla;
        } else {
            qry = "SELECT * FROM " + nomTabla + " WHERE ";
        }
        int aux = campo.length;
        for (int i = 0; i < aux; i++) {
            qry = qry + campo[i] + " " + operador_busqueda + " ? ";
            if (i < (aux - 1)) {
                qry = qry + " " + condicion_busqueda + " ";
            }
        }

        qry = qry.trim();
        // System.out.println("Qry select: " + qry);
        return qry;
    }

   
 /*
     Sustituye acentos y cualquier vocal acentuada por un símbolo comodín
     */
    private String normalizar(String inText) {
        String originalchars = "áéíóúäëïöüâêîôûàèìòùÁÉÍÓÚÄËÏÖÜÂÊÎÔÛÀÈÌÒÙ%";
        String output = "";
        char ch;
        int pos;

        for (int i = 0; i < inText.length(); i++) {
            pos = originalchars.indexOf(inText.charAt(i));
            if (pos != -1) {
                output = output + "%";
            } else {
                output = output + inText.charAt(i);
            }
        }
        //output= output.toUpperCase();             
        return output;

    }

}
