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

public interface UserGameDao extends DaoData {

    public UserGame getUserGame();


    public List<List<Object>> getLastXItemsFromUserGame(int userId, int n) throws DaoException;

}