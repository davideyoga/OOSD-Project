package gamingplatform.dao.data;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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

	/**
	 * Inizializzazzione della connessione
	 * @throws DaoException
	 */
	@Override
	public void init() throws DaoException {
        
		try {

			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/gamingplatform");
			connection = ds.getConnection();
        } catch (SQLException | NamingException e) {
            throw new DaoException("Error: db connection failed", e);
        }
		
	}

	/**
	 * Chiusura della connessione
	 * @throws DaoException
	 */
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

	/**
	 * Sovrascrizione del metodo close, si rimanda al metodo destroy
	 * @throws Exception
	 */
	@Override
	public void close() throws Exception {
		destroy();
	}

	
}