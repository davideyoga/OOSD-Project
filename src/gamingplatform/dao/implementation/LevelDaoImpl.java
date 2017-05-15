package gamingplatform.dao.implementation;

import gamingplatform.dao.data.DaoDataMySQLImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.interfaces.LevelDao;
import gamingplatform.model.Level;

import static gamingplatform.controller.utils.SecurityLayer.addSlashes;
import static gamingplatform.controller.utils.SecurityLayer.stripSlashes;


public class LevelDaoImpl extends DaoDataMySQLImpl implements LevelDao {

    private PreparedStatement selectLevelById,
                              selectLevels,
                              insertLevel,
                              deleteLevel,
                              selectNextLevel,
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
                    "                                                WHERE id=? ");


            this.insertLevel = connection.prepareStatement("INSERT INTO level (id,name,trophy,icon,exp) " +
                    "                                            VALUES(NULL,?,?,?,?)");

            this.deleteLevel=connection.prepareStatement("DELETE FROM level" +
                    "                                           WHERE id=?");


            this.updateLevel=connection.prepareStatement("UPDATE level " +
                    "                                          SET name=?," +
                    "                                              trophy=?, " +
                    "                                              icon=?, " +
                    "                                              exp=? " +
                    "                                          WHERE id=?");

            this.selectLevels=connection.prepareStatement("SELECT * FROM level");

            this.selectNextLevel = connection.prepareStatement("SELECT level.id, level.name, level.trophy, level.icon, level.exp FROM level WHERE level.exp > ? ORDER BY exp ASC LIMIT 1");


        }   catch (Exception e) {
            throw new DaoException("Error initializing level dao", e);
        }
    }



    @Override
    public Level getNextLevel ( Level level) throws DaoException{
        Level l=new Level(this);
        try {
            this.selectNextLevel.setInt(1, level.getExp());
            ResultSet rs= this.selectNextLevel.executeQuery();

            while(rs.next()) {
                l.setId(rs.getInt("id"));
                l.setName(rs.getInt("name"));
                l.setTrophy(stripSlashes(rs.getString("trophy")));
                l.setIcon(stripSlashes(rs.getString("icon")));
                l.setExp(rs.getInt("exp"));
            }

        } catch (Exception e) {
            throw new DaoException("Error query getNextLevel", e);
        }

        return l;
    }

    public Level getLevel() {return new Level(this);}

    @Override
    public Level getLevelById ( int keyLevel) throws DaoException{
        Level l=new Level(this);
        try {
            this.selectLevelById.setInt(1, keyLevel);
            ResultSet rs= this.selectLevelById.executeQuery();

            while(rs.next()) {
                l.setId(rs.getInt("id"));
                l.setName(rs.getInt("name"));
                l.setTrophy(stripSlashes(rs.getString("trophy")));
                l.setIcon(stripSlashes(rs.getString("icon")));
                l.setExp(rs.getInt("exp"));
            }
        } catch (Exception e) {
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
                level.setTrophy(stripSlashes(rs.getString("trophy")));
                level.setIcon(stripSlashes(rs.getString("icon")));
                level.setExp(rs.getInt("exp"));

                lista.add(level);
            }
        } catch (Exception e) {
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
            this.selectNextLevel.close();
        } catch (Exception e) {
            throw new DaoException("Error destroy LevelDao", e);
        }

        //chiudo la connessione
        super.destroy();
    }




















}

