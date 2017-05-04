package gamingplatform.dao.interfaces;

import gamingplatform.dao.data.DaoData;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.Game;

import java.util.List;

public interface GameDao extends DaoData{

    public Game getGameById( int keyGame) throws DaoException;

    public Game getGameByName( String nameGame) throws DaoException;

    public List<Game> getGames() throws DaoException;

    public void insertGame(Game game) throws DaoException;

    public void deleteGameById(int keygame) throws DaoException;

    public void deleteGameByName(String nameGame) throws DaoException;

    public void updateGame(int id,String name, int exp, String image, String description) throws DaoException;



    public void destroy() throws DaoException;
}