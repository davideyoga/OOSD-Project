package gamingplatform.dao.interfaces;

import gamingplatform.dao.data.DaoData;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.Level;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

public interface UserLevelDao extends DaoData {

    public Map<Timestamp,Level> getDateLevelsByUserId(int userId) throws DaoException;

}