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

import dawm12.grup2.es.DBConnectionPool;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import dawm12.grup2.es.repository.MyRepository;

/**
 *
 * @author DAW_M12_grup2
 */
public abstract class MyRepositoryImp <T> implements MyRepository <T> {
    
    private static DBConnectionPool dBConnectionPool;
    private static Connection connection;

    public MyRepositoryImp ()  {
        try {
            dBConnectionPool = DBConnectionPool.getInstance("dbPool.properties");
        } catch (IOException ex) {
            Logger.getLogger(MyRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MyRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(MyRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
      
    @Override
    public List<T> get(String nomTabla, String tipo_busqueda, String args, String campos) {
        List <T> result = null;
        Query q = new Query();
        
        //System.out.println ("En get: " + nomTabla + " " + tipo_busqueda + " " + args + " " + campos);
        String qry = q.createQuerySelect(nomTabla, tipo_busqueda, args, campos);
        //System.out.println("\nQry: " + qry);
        PreparedStatement preparedStatement;
        try {
            preparedStatement = getPreparedStatement(qry);
            for ( int i = 0; i < q.getValor().length; i++) { 
                System.out.println("Valor: " + q.getValor()[i]);
                preparedStatement.setString(i+1, q.getValor()[i]);
            }
            result = executeQuery(preparedStatement);
        } catch (SQLException ex) {
            Logger.getLogger(MyRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        if ( getConnection() != null ) closeConnection();
        
        return result;
    }
    
    @Override
    public T getone (String nomTabla, String campos) {
        T result = null;
        Query q = new Query();
        String qry = q.createQuerySelect(nomTabla, MyRepository.AND , campos);
        //System.out.println("\n\nGet one buscando: " + nomTabla + " : " + campos);
        try {
            PreparedStatement preparedStatement = getPreparedStatement(qry);
            for ( int i = 0; i < q.getValor().length; i++) {
                //System.out.println(q.getValor()[i]);
                preparedStatement.setString(i+1, q.getValor()[i]);
            }
            result = findUniqueResult(preparedStatement);
        } catch (Exception ex) {
            Logger.getLogger(MyRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error buscando en getByX");
        }
        
        if ( getConnection() != null ) closeConnection();        
        
        return result;
    }
    
    @Override
    public T create(String nomTabla, String campos) throws Exception {
        T result = null;
        Query q = new Query();
        String qry = q.createQueryInsert(nomTabla, campos);
        //edlobez.es.Debug.printDebug("Query create: " + qry);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(qry);
            for ( int i = 0; i < q.getValor().length; i++) {
                //edlobez.es.Debug.printDebug(q.getValor()[i]);
                preparedStatement.setString(i+1, q.getValor()[i]);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return createOrUpdate(nomTabla, preparedStatement, campos);
    }

    @Override
    public T update(String nomTabla, String condicion, String campos) throws Exception{
        T result = null;
        Query q = new Query();        
        String qry = q.createQueryUpdate (nomTabla,condicion, campos);
       // edlobez.es.Debug.printDebug(qry);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(qry);
            for ( int i = 0; i < q.getValor().length; i++) {
                //System.out.println("Upadate a valor: " + q.getValor()[i]);
                preparedStatement.setString(i+1, q.getValor()[i]);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return createOrUpdate(nomTabla, preparedStatement, condicion);
    }

    @Override
    public void delete(T t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private T createOrUpdate (String nomTabla, PreparedStatement preparedStatement, String campos ) throws Exception {
        int result = executeUpdateQuery (preparedStatement);
        if (result == 0 ) {
            throw new Exception ("Error creando elemento");
        }
        //System.out.println("\nCreate - update correct!!!!!!!!!!");
        return getone(nomTabla, campos);
    }
   
    private PreparedStatement getPreparedStatement(String query) throws SQLException {
        if (getConnection() == null) {
            try {               
                setConnection(this.dBConnectionPool.getConnection());
            } catch (SQLException e) {
                System.out.println("Error en la conexion!!!!!");
                e.printStackTrace();
            }
        }  
       // edlobez.es.Debug.printDebug(query);
        return getConnection().prepareStatement(query);
    }
    

    private T findUniqueResult(PreparedStatement preparedStatement) throws Exception {

        //System.out.println("Buscando único elemento");
        List<T> type = executeQuery(preparedStatement);
        if (type.isEmpty()) {
            //System.out.println("La lista está vacia");
            return null;
        }
        if (type.size() > 1) {   
            //System.out.println("Hay vario elementos en la lista");
            throw new Exception("Only one result expected");
        }
         return type.get(0);
    }
    
    private List<T> executeQuery(PreparedStatement preparedStatement) {

        List<T> types = new ArrayList<>();
        //edlobez.es.Debug.printDebug("Ejecutando: " + preparedStatement.toString());
        try (
            ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                T type = buildDomainFromResultSet(rs);
                types.add(type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }        
        return types;
    }
    
    private int executeUpdateQuery(PreparedStatement preparedStatement) {
        int result = 0;
        if (getConnection() == null) {
            try {
                setConnection(this.dBConnectionPool.getConnection());
            } catch (SQLException  e) {
                e.printStackTrace();
            }
        }
        try {
            result = preparedStatement.executeUpdate();
            //System.out.println("Ejecutando:" + result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    private Connection getConnection() {
        return connection;
    }

    private void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    private void closeConnection ()  {
        try {
            //System.out.println("\n\nCerrando conexión!!!!");
            this.connection.close();
            this.connection = null;
        } catch (SQLException ex) {
            Logger.getLogger(MyRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    abstract protected T buildDomainFromResultSet(ResultSet rs) throws SQLException;
    
}
