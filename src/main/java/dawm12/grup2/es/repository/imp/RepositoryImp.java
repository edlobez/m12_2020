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
import dawm12.grup2.es.repository.Repository;
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

/**
 *
 * @author DAW_M12_grup2
 */
public abstract class RepositoryImp <T> implements Repository <T> {
    
    private static DBConnectionPool dBConnectionPool;
    private static Connection connection;

    public RepositoryImp ()  {
        try {
            dBConnectionPool = DBConnectionPool.getInstance("dbPool.properties");
        } catch (IOException ex) {
            Logger.getLogger(RepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(RepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public List<T> get(String nomTabla, String tipo_busqueda, String... campos) {
        List <T> result = null;
        Query q = new Query();
        
        String qry = q.createQuerySelect(nomTabla, tipo_busqueda, campos);
        PreparedStatement preparedStatement;
        try {
            preparedStatement = getPreparedStatement(qry);
            for ( int i = 0; i < q.getValor().length; i++) {
                preparedStatement.setString(i+1, q.getValor()[i]);
            }
            result = executeQuery(preparedStatement);
        } catch (SQLException ex) {
            Logger.getLogger(RepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
           /* 
        if (edlobez.es.Debug.isDebugMode()) {
            edlobez.es.Debug.printDebug("Elementos de la lista " + result.size());
            for (T t: result)
                edlobez.es.Debug.printDebug(t.toString());
        }*/
        return result;
    }
    
    @Override
    public T getone (String nomTabla, String... campos) {
        T result = null;
        Query q = new Query();
        String qry = q.createQuerySelect(nomTabla, Repository.AND , campos);
        
        try {
            PreparedStatement preparedStatement = getPreparedStatement(qry);
            for ( int i = 0; i < q.getValor().length; i++) {
                //edlobez.es.Debug.printDebug(q.getValor()[i]);
                preparedStatement.setString(i+1, q.getValor()[i]);
            }
            result = findUniqueResult(preparedStatement);
        } catch (Exception ex) {
            Logger.getLogger(RepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error buscando en getByX");
        }    
        return result;
    }
    
    @Override
    public T create(String nomTabla, String... campos) throws Exception {
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
            Logger.getLogger(RepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return createOrUpdate(nomTabla, preparedStatement, campos);
    }

    @Override
    public T update(String nomTabla, String condicion, String... campos) throws Exception{
        T result = null;
        Query q = new Query();        
        String qry = q.createQueryUpdate (nomTabla,condicion, campos);
       // edlobez.es.Debug.printDebug(qry);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(qry);
            for ( int i = 0; i < q.getValor().length; i++) {
                //edlobez.es.Debug.printDebug(q.getValor()[i]);
                preparedStatement.setString(i+1, q.getValor()[i]);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return createOrUpdate(nomTabla, preparedStatement, campos);
    }

    @Override
    public void delete(T t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private T createOrUpdate (String nomTabla, PreparedStatement preparedStatement, String... campos ) throws Exception {
        int result = executeUpdateQuery (preparedStatement);
        if (result == 0 ) {
            throw new Exception ("Error creando elemento");
        }
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

        List<T> type = executeQuery(preparedStatement);
        if (type.isEmpty()) {
            return null;
        }
        if (type.size() > 1) {            
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
            this.connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(RepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    abstract protected T buildDomainFromResultSet(ResultSet rs) throws SQLException;
    
}
