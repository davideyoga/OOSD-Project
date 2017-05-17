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

    public UserLevel getUserLevel();

    public void insertUserlevel( UserLevel userLevel) throws DaoException;

    public UserLevel selectUserLevelById( int idUserLevel ) throws DaoException;

    public void updateUserLevel( UserLevel userLevel) throws DaoException;

    public void deleteUserLevel( UserLevel userLevel) throws DaoException;

    public List<List<Object>> getLastXItemsFromUserLevel(int userId, int n) throws DaoException;


}