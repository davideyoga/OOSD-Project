package gamingplatform.dao.data;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import gamingplatform.dao.exception.DaoException;

/**
 * 
 * @author Davide Micarelli
 * This classes is a base Connection with connection pooling 
 *
 */
public class DaoDataMySQLImpl implements DaoData{
	
	private DataSource datasource;
	protected Connection connection;

	public DaoDataMySQLImpl( DataSource datasource ){
        super();
        this.datasource = datasource;
        this.connection = null;
	}
	
	@Override
	public void init() throws DaoException {
        
		try {
        	//database connection 
            connection = datasource.getConnection();
        } catch (SQLException e) {
            throw new DaoException("Error: db connection failed", e);
        }
		
	}
	

	@Override
	public void destroy() throws DaoException {
		try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            throw new DaoException("Error: shutdown failed connection", e);
        }
		
	}
	
	@Override
	public void close() throws Exception {
		destroy();
		
	}

	
}