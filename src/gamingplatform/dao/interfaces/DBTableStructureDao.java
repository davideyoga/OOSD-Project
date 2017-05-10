package gamingplatform.dao.interfaces;


import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.DBTableStructure;


public interface DBTableStructureDao {
    public DBTableStructure getTableStructure(String table) throws DaoException;
}
