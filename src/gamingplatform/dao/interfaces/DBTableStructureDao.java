package gamingplatform.dao.interfaces;


import gamingplatform.dao.data.DaoData;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.DBTableStructure;


public interface DBTableStructureDao extends DaoData {

    public void init(String table) throws DaoException;

    public DBTableStructure getDBTableStructure();

    public DBTableStructure getTableStructure() throws DaoException;
}
