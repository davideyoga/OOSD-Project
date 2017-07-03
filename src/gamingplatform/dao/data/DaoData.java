package gamingplatform.dao.data;

import gamingplatform.dao.exception.DaoException;

/**
 * Interfaccia per definire la base dell' inizializzazione e chiusura della connessione
 * @author Davide Micarelli
 *
 */
public interface DaoData extends AutoCloseable{

    /**
     * Inizilizzazzione della connessione e delle query precompilate
     * @throws DaoException
     */
    void init() throws DaoException;

    /**
     * Chusura connessione e query precompialte
     * @throws DaoException
     */
    void destroy() throws DaoException;
    
}