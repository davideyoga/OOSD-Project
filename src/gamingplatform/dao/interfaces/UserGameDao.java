package gamingplatform.dao.interfaces;

import gamingplatform.dao.data.DaoData;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.Game;
import gamingplatform.model.Level;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

public interface UserGameDao extends DaoData {

    public Map<Date,Game> getDateGamesByUserId(int userId) throws DaoException, SQLException;

}