package gamingplatform.dao.interfaces;

import gamingplatform.dao.data.DaoData;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.Game;
import gamingplatform.model.Level;
import gamingplatform.model.UserGame;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Interfaccia per la gestione della tabella nel db che rappresenta la relazione tra User e Game
 */
public interface UserGameDao extends DaoData {

    /**
     * Torna User vuoto
     * @return
     */
    public UserGame getUserGame();

    /**
     * Torna dal db l'User con tale id
     * @param idUserGame
     * @return
     * @throws DaoException
     */
    public UserGame selectUserGameById( int idUserGame)throws DaoException;

    /**
     * Inserisce l'user passato nel db
     * @param userGame
     * @throws DaoException
     */
    public void insertUserGame(UserGame userGame)throws DaoException;

    /**
     * Esegue nel db l'update dell'utente
     * @param userGame
     * @throws DaoException
     */
    public void updateUserGame(UserGame userGame)throws DaoException;

    /**
     * Cancella dal db l'utente passato
     * @param userGame
     * @throws DaoException
     */
    public void deleteUserGame(UserGame userGame)throws DaoException;

    /**
     * Restituisce gli ultimi x elementi dalla tabella usergame
     * @param userId
     * @param n
     * @return
     * @throws DaoException
     */
    public List<List<Object>> getLastXItemsFromUserGame(int userId, int n) throws DaoException;

}