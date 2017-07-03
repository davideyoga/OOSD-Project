package gamingplatform.dao.interfaces;

import gamingplatform.dao.data.DaoData;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.Group;
import gamingplatform.model.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * Intefaccia per la gestione dei livelli del database
 * Created by GregBug on 03/05/2017.
 */
public interface LevelDao extends DaoData {

    /**
     * Torna Livello vuoto
     * @return
     */
    public Level getLevel();

    /**
     * Torna livello con tale id dal database
     * @param keyLevel
     * @return
     * @throws DaoException
     */
    public Level getLevelById(int keyLevel) throws DaoException;

    /**
     * inserisce Level passato nel database
     * @param level
     * @throws DaoException
     */
    public void insertLevel(Level level) throws DaoException;

    /**
     * Elimina tale Level dal database
     * @param idLevel
     * @throws DaoException
     */
    public void deleteLevel(int idLevel) throws DaoException;

    /**
     * Eseue l'update del Level con l'id del livello passato nel database
     * @param level
     * @throws DaoException
     */
    public void updateLevel(Level level) throws DaoException;

    /**
     * Restituisce dal database il livello successivo a quello passato
     * @param currentLevel
     * @return
     * @throws DaoException
     */
    public Level getNextLevel(Level currentLevel) throws DaoException;

    /**
     * Restituisce dal database tutti i livelli
     * @return
     * @throws DaoException
     */
    public List<Level> getLevels () throws DaoException;

    /**
     * Ritorna la lista dei livelli dal database ordinati, il tipo di ordine e' deciso dall'implementazione
     * @return
     * @throws DaoException
     */
    public List<Level> getLevelsOrdered () throws DaoException;


}

