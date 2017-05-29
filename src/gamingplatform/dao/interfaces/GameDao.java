package gamingplatform.dao.interfaces;

import gamingplatform.dao.data.DaoData;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.Game;

import java.util.List;

public interface GameDao extends DaoData{

    public Game getGame();

    public Game getGameById( int idGame) throws DaoException;

    public Game getGameByName( String nameGame) throws DaoException;

    public List<Game> getGames() throws DaoException;

    public void insertGame( Game game) throws DaoException;

    public void deleteGameById(int idGame) throws DaoException;

    public void deleteGameByName(String nameGame) throws DaoException;

    public void updateGame( Game game ) throws DaoException;

    public double getAverageVote (Game game) throws DaoException;

}