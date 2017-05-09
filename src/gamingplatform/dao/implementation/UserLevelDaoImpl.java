package gamingplatform.dao.implementation;

import gamingplatform.dao.data.DaoDataMySQLImpl;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.interfaces.UserDao;
import gamingplatform.dao.interfaces.UserLevelDao;
import gamingplatform.model.Level;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Map;

public class UserLevelDaoImpl extends DaoDataMySQLImpl implements UserLevelDao {

    PreparedStatement selectLevelsByUserId;

    /**
     * Costruttore per inizializzare la connessione
     * @param datasource e' la risorsa di connessione messa a disposizione del connection pooling
     */
    public UserLevelDaoImpl(DataSource datasource) {
        super(datasource);
    }

    /**
     * per connessione al database e precompilazione query
     * @throws gamingplatform.dao.exception.DaoException
     */
    @Override
    public void init() throws DaoException{
        try {
            super.init(); // connection initialization

            this.selectLevelsByUserId = connection.prepareStatement("");

        } catch (SQLException e) {
            throw new DaoException("Error initializing user dao", e);
        }
    }

    @Override
    public Map<Calendar, Level> getLevelsByUserId(int userId) {
        return null;
    }

    /**
     * chiudo la connessione e le query precompilate
     * @throws gamingplatform.dao.exception.DaoException
     */
    @Override
    public void destroy() throws DaoException {

        /*
        //chiudo le quary precompilate
        try {
;

        } catch (SQLException e) {
            throw new DaoException("Error destroy dao user", e);
        }
        */

        //chiudo la connessione
        super.destroy();
    }
}