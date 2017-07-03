package gamingplatform.dao.interfaces;


import gamingplatform.dao.data.DaoData;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.DBTableStructure;


public interface DBTableStructureDao extends DaoData {

    /**
     * Inizializzazione query precompilate e connessione
     * @param table
     * @throws DaoException
     */
    void init(String table) throws DaoException;

    /**
     * Restituisce un DBTableStructure vuoto
     * @return
     */
    DBTableStructure getDBTableStructure();

    /**
     * Restituisce la struttura delle tabelle del db
     * @return
     * @throws DaoException
     */
    DBTableStructure getTableStructure() throws DaoException;
}
