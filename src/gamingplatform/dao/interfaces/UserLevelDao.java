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

public interface UserLevelDao extends DaoData {

    public List<List<Object>> getLastXItemsFromUserLevel(int userId, int n) throws DaoException;



}