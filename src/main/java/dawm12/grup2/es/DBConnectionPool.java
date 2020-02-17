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
package dawm12.grup2.es;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author DAW_M12_grup2
 */
public class DBConnectionPool {
    
    private static DBConnectionPool pdatasource;
    private final ComboPooledDataSource cpds;
    private String connectionFile;


private DBConnectionPool(String connectionFile) throws IOException, SQLException, PropertyVetoException {
        
        this.connectionFile = connectionFile;
        // Configuramos la conexion a base de datos
        // Creamos la fuente de datos
        cpds = new ComboPooledDataSource();
        
        Properties props = new Properties();
        InputStream resourceAsStream = null;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL urlResource = classLoader.getResource(connectionFile);
            if (urlResource != null) {
                resourceAsStream = urlResource.openStream();
                props.load(resourceAsStream);
                // Que driver de base de datos usaremos
                cpds.setDriverClass(props.getProperty("DB_DRIVER_CLASS"));
                // La url de la base de datos a la que nos conectaremos
                cpds.setJdbcUrl(props.getProperty("DB_URL"));
                // Usuario de esa base de datos
                cpds.setUser(props.getProperty("DB_USERNAME"));
                // Contraseña de la base de datos
                cpds.setPassword(props.getProperty("DB_PASSWORD"));

                // Configuramos el pool
                // Numero de conexiones con las que iniciara el pool
                cpds.setInitialPoolSize(Integer.parseInt(props.getProperty("INITIAL_POOL_SIZE")));
                // Minimo de conexiones que tendra el pool
                cpds.setMinPoolSize(Integer.parseInt(props.getProperty("MIN_POOL_SIZE")));
                // Numero de conexiones a crear cada incremento
                cpds.setAcquireIncrement(Integer.parseInt(props.getProperty("INCREMENTOS_POOL")));
                // Maximo numero de conexiones
                cpds.setMaxPoolSize(Integer.parseInt(props.getProperty("MAX_POOL_SIZE")));
                // Maximo de consultas
                cpds.setMaxStatements(Integer.parseInt(props.getProperty("MAX_CONSULTAS")));
                // Maximo numero de reintentos para conectar a base de datos
                cpds.setAcquireRetryAttempts(Integer.parseInt(props.getProperty("NUM_REINTENTOS")));
                // Que se genere una excepcion si no se puede conectar
                cpds.setBreakAfterAcquireFailure(Boolean.parseBoolean(props.getProperty("BREAK_AFTER_FAILURE")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (resourceAsStream != null) {
                resourceAsStream.close();
            }
        }
    }

    /**
     * Nos regresa la instancia actual del pool, en caso que no halla una
     * instancia crea una nueva y la regresa
     *
     * @return
     * @throws IOException
     * @throws SQLException
     * @throws PropertyVetoException
     */
    public static DBConnectionPool getInstance(String connectionFile) throws IOException, SQLException, PropertyVetoException {

        if (pdatasource == null) {
            pdatasource = new DBConnectionPool(connectionFile);
            return pdatasource;
        } else {
            return pdatasource;
        }
    }

    /**
     * Este metodo nos regresa una conexion a base de datos, esta la podemos
     * usar como una conexion usual
     *
     * @return Conexion a base de datos
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {       
        return this.cpds.getConnection();
    }

  /*  public DataSource getDataSource() {
        return (DataSource) this.cpds;
    }*/

    
}