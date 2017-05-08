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
import gamingplatform.dao.interfaces.LevelDao;
import gamingplatform.model.Level;

import static gamingplatform.controller.utils.SecurityLayer.addSlashes;


public class LevelDaoImpl extends DaoDataMySQLImpl implements LevelDao {

    private PreparedStatement selectLevelById,
                              selectLevels,
                              insertLevel,
                              deleteLevel,
                              updateLevel;

    // Costruttore
    public LevelDaoImpl(DataSource datasource) {
        super(datasource);
    }

    @Override
    public void init() throws DaoException{
        try {
            super.init(); //connection initialization

            this.selectLevelById = connection.prepareStatement("SELECT * " +
                    "                                                FROM level " +
                    "                                                WHERE id=?");


            this.insertLevel = connection.prepareStatement("INSERT INTO level (name,trophy,icon,exp) " +
                    "                                            VALUES(NULL,?,?,?,?)");

            this.deleteLevel=connection.prepareStatement("DELETE FROM level" +
                    "                                           WHERE id=?");


            this.updateLevel=connection.prepareStatement("UPDATE levels" +
                    "                                          SET name=?," +
                    "                                              trophy=?," +
                    "                                              icon=?," +
                    "                                              exp=?" +
                    "                                          WHERE id=?");

            this.selectLevels=connection.prepareStatement("SELECT * FROM level");


        }   catch (SQLException e) {
            throw new DaoException("Error initializing level dao", e);
        }
    }

    @Override
    public Level getLevelById ( int keyLevel) throws DaoException{
        Level l=new Level(this);
        try {
            this.selectLevelById.setInt(1, keyLevel);
            ResultSet rs= this.selectLevelById.executeQuery();
            l.setId(rs.getInt("id"));
            l.setName(rs.getInt("name"));
            l.setTrophy(rs.getString("trophy"));
            l.setIcon(rs.getString("icon"));
            l.setExp(rs.getInt("exp"));
        } catch (SQLException e) {
            throw new DaoException("Error query getLevel", e);
        }

        return l;
    }




    public void insertLevel(Level level) throws DaoException {
        try{
            this.insertLevel.setInt(1,level.getName());
            this.insertLevel.setString(2, addSlashes(level.getTrophy()));
            this.insertLevel.setString(3, addSlashes(level.getIcon()));
            this.insertLevel.setInt(4,level.getExp());
            this.insertLevel.executeUpdate();
        }catch (Exception e){
            throw new DaoException("Error query insertLevel", e);
        }
    }



    public void deleteLevel(int idLevel) throws DaoException {
        try{
            this.deleteLevel.setInt(1,idLevel);
            this.deleteLevel.executeUpdate();
        }catch (Exception e){
            throw new DaoException("Error query deleteLevel", e);

        }
    }



    public void updateLevel(Level level) throws DaoException {
        try{
            this.updateLevel.setInt(1,level.getName());
            this.updateLevel.setString(2, addSlashes(level.getTrophy()));
            this.updateLevel.setString(3, addSlashes(level.getIcon()));
            this.updateLevel.setInt(4,level.getExp());
            this.updateLevel.setInt(5,level.getId());
            this.updateLevel.executeUpdate();
        }catch (Exception e){
            throw new DaoException("Error query updateLevel", e);
        }
    }





    public List<Level> getLevels() throws DaoException{
        List<Level> lista=new ArrayList<>();
        try{
            ResultSet rs=this.selectLevels.executeQuery();

            while (rs.next()) {
                Level level=new Level(this);
                level.setId(rs.getInt("id"));
                level.setName(rs.getInt("name"));
                level.setTrophy(rs.getString("trophy"));
                level.setIcon(rs.getString("icon"));
                level.setExp(rs.getInt("exp"));

                lista.add(level);
            }
        } catch (SQLException e) {
            throw new DaoException("Error query getLevels", e);
        }

        return lista;
    }

    @Override
    public void destroy() throws DaoException {
        //chiudo le quary precompilate
        try {
            this.selectLevelById.close();
            this.insertLevel.close();
            this.deleteLevel.close();
            this.updateLevel.close();
            this.selectLevels.close();
        } catch (SQLException e) {
            throw new DaoException("Error destroy LevelDao", e);
        }

        //chiudo la connessione
        super.destroy();
    }




















}

