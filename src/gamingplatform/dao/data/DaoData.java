package gamingplatform.dao.data;

import gamingplatform.dao.exception.DaoException;

/**
 * 
 * @author Davide Micarelli
 *
 */
public interface DaoData extends AutoCloseable{
	
    void init() throws DaoException;

    void destroy() throws DaoException;
    
}