package gamingplatform.dao.implementation;

import gamingplatform.dao.data.DaoDataMySQLImpl;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.interfaces.UserLevelDao;
import gamingplatform.model.UserLevel;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static gamingplatform.controller.utils.SecurityLayer.stripSlashes;

public class UserLevelDaoImpl extends DaoDataMySQLImpl implements UserLevelDao {

    private PreparedStatement selectLastXitems;
    private PreparedStatement selectUserLevelById;
    private PreparedStatement updateLevelUser;
    private PreparedStatement insertLevelUser;
    private PreparedStatement deleteLeveluser;

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

            this.selectLastXitems = connection.prepareStatement(" SELECT level.name, level.exp, level.icon, level.trophy, userlevel.date" +
                                                                        " FROM userlevel LEFT JOIN level ON userlevel.id_level = level.id"+
                                                                        " WHERE userlevel.id_user = ? ORDER BY date DESC LIMIT ?");

            this.selectUserLevelById = connection.prepareStatement("SELECT * " +
                    "												FROM userlevel " +
                    "												WHERE id=?");

            this.insertLevelUser = connection.prepareStatement("INSERT INTO userlevel" +
                    "										    VALUES(NULL,?,?,?)");

            this.updateLevelUser = connection.prepareStatement("UPDATE userlevel " +
                    "												SET id_user=?," +
                    "													id_game=?," +
                    "													date=?," +
                    "													WHERE id=?");

            this.deleteLeveluser = connection.prepareStatement("DELETE FROM usergame " +
                    "												WHERE id=?");

        } catch (Exception e) {
            throw new DaoException("Error initializing user level dao", e);
        }
    }

    /**
     * restituisce un UserLevel vuoto
     *
     * @return UserLevel vuoto
     */
    @Override
    public UserLevel getUserLevel() {
        return new UserLevel(this);
    }

    /**
     * inserisce uno UserLevel nel db
     *
     * @param userLevel da inserire nel db
     * @throws DaoException
     */
    @Override
    public void insertUserlevel(UserLevel userLevel) throws DaoException {

        if( userLevel.getId() >= 1){ // se lo UserGame possiede gia un id vado a fare un update
            this.updateUserLevel(userLevel);
        }else{

            try {
                this.insertLevelUser.setInt(1, userLevel.getUserId());
                this.insertLevelUser.setInt(2, userLevel.getLevelId());
                this.insertLevelUser.setTimestamp(3, userLevel.getDate());

                this.insertLevelUser.executeUpdate();

            } catch (SQLException e) {
                throw new DaoException("Error get user level dao", e);
            }
        }
    }

    /**
     * restituisce un UserLevel secondo l'id passato
     * @param idUserLevel id dello UserGame desiderato
     * @return userLevel con id = idUserLevel
     * @throws gamingplatform.dao.exception.DaoException
     */
    @Override
    public UserLevel selectUserLevelById(int idUserLevel) throws DaoException {
        UserLevel userLevel = this.getUserLevel(); // diciaro lo UserLevel da restituire

        try {
            selectUserLevelById.setInt(1, idUserLevel);

            ResultSet rs = selectUserLevelById.executeQuery();

            userLevel.setId(idUserLevel);
            userLevel.setLevelId( rs.getInt("id_level"));
            userLevel.setUserId( rs.getInt("id_user"));
            userLevel.setDate( rs.getTimestamp("date"));

        } catch (SQLException e) {
            throw new DaoException("Error dao user level", e);
        }

        return userLevel;
    }

    /**
     * metodo per l'update di un UserLevel nel db
     * @param userLevel
     * @throws DaoException
     */
    @Override
    public void updateUserLevel(UserLevel userLevel) throws DaoException {

        try {

            this.updateLevelUser.setInt(1, userLevel.getUserId());
            this.updateLevelUser.setInt(2, userLevel.getLevelId());
            this.updateLevelUser.setTimestamp(3, userLevel.getDate());
            this.updateLevelUser.setInt(4, userLevel.getId());

            this.updateLevelUser.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Error update user level dao", e);
        }

    }

    /**
     * cancella lo UserLevel dal db
     * @param userLevel da cancellare
     * @throws DaoException
     */
    @Override
    public void deleteUserLevel(UserLevel userLevel) throws DaoException {

        try {

            this.deleteLeveluser.setInt(1, userLevel.getId());

            this.deleteLeveluser.executeUpdate();


        } catch (SQLException e) {
            throw new DaoException("Error delete user level dao", e);
        }
    }

    /**
     * Metodo che restituisce un'insieme di chiave valore che rappresentano la data in cui l'utente con ID userId ha cambiato livello in cui si trova
     * @return un'insieme di chiave valore che rappresentano la data in cui l'utente con ID userId ha cambiato livello in cui si trova
     * @throws DaoException lancia eccezione in caso di errore
     */
    @Override
    public List<List<Object>> getLastXItemsFromUserLevel(int userId, int n) throws DaoException{

        List <List<Object>> list = new ArrayList<>();

        try {
            this.selectLastXitems.setInt(1, userId); //setto la query
            this.selectLastXitems.setInt(2, n);

            ResultSet rs = this.selectLastXitems.executeQuery(); //eseguo la query


            while( rs.next() ){ //ciclo ogni tupla del risultato
                List<Object> innerList = new ArrayList<>();

                innerList.add(rs.getTimestamp("date")); //estraggo la data dal risultato della query
                innerList.add(rs.getInt("name"));
                innerList.add(stripSlashes(rs.getString("icon")));
                innerList.add(stripSlashes(rs.getString("trophy")));
                innerList.add((rs.getInt("exp")));


                list.add(innerList);
            }

        } catch (Exception e) {
            throw new DaoException("Error get levels by user id in user level dao", e);
        }

        return list;
    }




    /**
     * chiudo la connessione e le query precompilate
     * @throws gamingplatform.dao.exception.DaoException
     */
    @Override
    public void destroy() throws DaoException {

        //chiudo le quary precompilate
        try {
            this.selectLastXitems.close();
            this.selectUserLevelById.close();
            this.updateLevelUser.close();
            this.insertLevelUser.close();
            this.deleteLeveluser.close();

        } catch (Exception e) {
            throw new DaoException("Error destroy dao user", e);
        }

        //chiudo la connessione
        super.destroy();
    }
}