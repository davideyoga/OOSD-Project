package gamingplatform.dao.implementation;

import gamingplatform.dao.data.DaoDataMySQLImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import gamingplatform.dao.data.DaoDataMySQLImpl;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.interfaces.GroupsDao;
import gamingplatform.model.Group;
import gamingplatform.model.Level;

/**
 * Created by GregBug on 03/05/2017.
 */

public class LevelDaoImpl extends DaoDataMySQLImpl implements LevelDao {

    private PreparedStatement selectLevelById, insertLevel, deleleLevel, selectLevelByUserId;
    private Object getLevelGroupByUserId, getLevelById;

    // Costruttore
    public LevelDaoImpl(DataSource datasource) {
        super(datasource);
    }

    @Override
    public void init() throws DaoException{
        try {
            super.init(); //connection initialization

            this.getLevelById = connection.prepareStatement("SELECT * FROM level WHERE id=?");
            this.insertLevel = connection.prepareStatement("INSERT INTO level (name,trophy,icon,exp) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            this.getLevelGroupByUserId = connection.prepareStatement("SELECT level.id, level.name, level.icon, level.exp FROM user LEFT JOIN userlevel ON userlevel.id LEFT JOIN user ON userlevel.id = user.id WHERE user.id = ?")

        }   catch (SQLException e) {
            throw new DaoException("Error initializing level dao", e);
        }
    }

    @Override
    public Level getLevelById ( int keyLevel) throws DaoException{

        try {
            this.getLevelById.setInt(1, keyLevel);
        } catch (SQLException e) {

        }

        return null;
    }

    @Override
    public void insertLevel(Level level) {
        // TODO Auto-generated method stub
    }

    /**
     * estraggo i gruppi a cui appartiene l'utente con id keyUser
     * @throws DaoException
     */
    @Override
    public List<Level> getLevelByUserId(int keyUser) throws DaoException {

        List<Level> lista = new ArrayList<Level>();

        try{

            this.getLevelGroupByUserId.setInt(1, keyUser);

            ResultSet rs = this.selectLevelByUserId.executeQuery();

            level.setId(rs.getInt("id"));
            level.setName("name");
            level.setDescription("trophy");
            level.setexp("experience")

        } catch (SQLException e) {
            throw new DaoException("Error get group User", e);
        }

        return lista;

    }

    @Override
    public void deleteLevelById() {
        // Todo Auto-generated method stub
    }

    @Override
    public void destroy() {
    }




















}

