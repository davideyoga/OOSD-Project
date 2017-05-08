package gamingplatform.dao.interfaces;

import gamingplatform.dao.data.DaoData;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.Group;
import gamingplatform.model.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GregBug on 03/05/2017.
 */
public interface LevelDao extends DaoData {

    public Level getLevelById(int keyLevel) throws DaoException;

    public void  setLevel(Level level) throws DaoException;

    public List<Level> getLevelByUserId(int keyUser) throws DaoException;

    public void deleteLevelById() throws DaoException;

    public void destroy() throws DaoException;

}

