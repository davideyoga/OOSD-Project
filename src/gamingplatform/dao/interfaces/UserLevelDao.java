package gamingplatform.dao.interfaces;

import gamingplatform.dao.data.DaoData;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.Level;
import gamingplatform.model.UserLevel;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Interfaccia per la gestione della tabella usergame che rappresenta la relazione tra user e level
 */
public interface UserLevelDao extends DaoData {

    /**
     * Restitusce UserLevel vuoto
     * @return
     */
    public UserLevel getUserLevel();

    /**
     * Inserisce lo UserLevel nel database
     * @param userLevel
     * @throws DaoException
     */
    public void insertUserlevel( UserLevel userLevel) throws DaoException;

    /**
     * Torna dal db l'UserLevel con tale id
     * @param idUserLevel
     * @return
     * @throws DaoException
     */
    public UserLevel selectUserLevelById( int idUserLevel ) throws DaoException;

    /**
     * Esegue l'update dell'userLevel passato
     * @param userLevel
     * @throws DaoException
     */
    public void updateUserLevel( UserLevel userLevel) throws DaoException;

    /**
     * Elimina dal database l'UserLevel passato
     * @param userLevel
     * @throws DaoException
     */
    public void deleteUserLevel( UserLevel userLevel) throws DaoException;

    /**
     * Torna gli ultimi x UserLevel appartenenti all'user con tale id userId dal database
     * @param userId
     * @param n
     * @return
     * @throws DaoException
     */
    public List<List<Object>> getLastXItemsFromUserLevel(int userId, int n) throws DaoException;


}