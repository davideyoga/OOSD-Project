package gamingplatform.dao.interfaces;

import gamingplatform.dao.data.DaoData;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.Game;

import java.util.List;

/**
 * Interfaccia per la gestione di giochi nel database
 */
public interface GameDao extends DaoData{

    /**
     * Torna Game vuoto
     * @return
     */
    Game getGame();

    /**
     * Torna Game con tale id nel database
     * @param idGame
     * @return
     * @throws DaoException
     */
    public Game getGameById( int idGame) throws DaoException;

    /**
     * Torna il gioco presente nel database con tale id
     * @param nameGame
     * @return
     * @throws DaoException
     */
    Game getGameByName( String nameGame) throws DaoException;

    /**
     * Torna lista dei Game presenti nel db
     * @return
     * @throws DaoException
     */
    List<Game> getGames() throws DaoException;

    /**
     * Inserisc etale gioco nel Database
     * @param game
     * @throws DaoException
     */
    void insertGame( Game game) throws DaoException;

    /**
     * Elimina dal database il gioco con tale id
     * @param idGame
     * @throws DaoException
     */
    void deleteGameById(int idGame) throws DaoException;

    /**
     * elimina il gioco con tale nome dal db
     * @param nameGame
     * @throws DaoException
     */
    void deleteGameByName(String nameGame) throws DaoException;

    /**
     * Esegue l'update del gioco con l'id del gioco passato
     * @param game
     * @throws DaoException
     */
    void updateGame( Game game ) throws DaoException;

    /**
     * Torna la media dei voti del gioco passato dal database
     * @param game
     * @return
     * @throws DaoException
     */
    double getAverageVote (Game game) throws DaoException;

}