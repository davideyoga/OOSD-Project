package gamingplatform.dao.implementation;

import gamingplatform.dao.data.DaoDataMySQLImpl;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.interfaces.UserDao;
import gamingplatform.dao.interfaces.UserGameDao;
import gamingplatform.model.Game;
import gamingplatform.model.UserGame;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import static gamingplatform.controller.utils.SecurityLayer.stripSlashes;

public class UserGameDaoImpl extends DaoDataMySQLImpl implements UserGameDao {

    private PreparedStatement selectUserGameById;
    private PreparedStatement selectLastXItems;
    private PreparedStatement insertGameUser;
    private PreparedStatement updateGameUser;
    private PreparedStatement deleteGameUser;

    /**
     * Costruttore per inizializzare la connessione
     *
     * @param datasource e' la risorsa di connessione messa a disposizione del connection pooling
     */
    public UserGameDaoImpl(DataSource datasource) {
        super(datasource);
    }

    /**
     * per connessione al database e precompilazione query
     *
     * @throws gamingplatform.dao.exception.DaoException
     */
    @Override
    public void init() throws DaoException {
        try {
            super.init(); // connection initialization

            this.selectUserGameById = connection.prepareStatement("SELECT * " +
                    "												FROM usergame " +
                    "												WHERE id=?");

            this.selectLastXItems = connection.prepareStatement("SELECT game.exp, game.name, game.image, usergame.date " +
                    "FROM usergame LEFT JOIN game ON game.id=usergame.id_game " +
                    "WHERE usergame.id_user = ? ORDER BY date DESC LIMIT ?");

            this.insertGameUser = connection.prepareStatement("INSERT INTO usergame" +
                    "										    VALUES(NULL,?,?,?)");

            this.updateGameUser = connection.prepareStatement("UPDATE usergame " +
                    "												SET id_user=?," +
                    "													id_game=?," +
                    "													date=?," +
                    "													WHERE id=?");

            this.deleteGameUser = connection.prepareStatement("DELETE FROM usergame " +
                    "												WHERE id=?");

        } catch (Exception e) {
            throw new DaoException("Error initializing user game dao", e);
        }
    }

    /**
     * restituisce un UserGame vuoto
     *
     * @return UserGame vuoto
     *
     * @throws gamingplatform.dao.exception.DaoException
     */
    @Override
    public UserGame getUserGame() {
        return new UserGame(this);
    }

    /**
     * restituisce un UserGame secondo l'id passato
     * @param idUserGame id dello UserGame desiderato
     * @return userGame con id = idUserGame
     * @throws gamingplatform.dao.exception.DaoException
     */
    @Override
    public UserGame selectUserGameById(int idUserGame) throws DaoException {

        UserGame userGame = this.getUserGame(); // diciaro lo UserGame da restituire

        try {
            selectUserGameById.setInt(1, idUserGame);

            ResultSet rs = selectUserGameById.executeQuery();

            userGame.setId(idUserGame);
            userGame.setGameId( rs.getInt("id_game"));
            userGame.setUserId( rs.getInt("id_user"));
            userGame.setDate( rs.getTimestamp("date"));

        } catch (SQLException e) {
            throw new DaoException("Error dao user game", e);
        }

        return userGame;
    }

    /**
     * inserisce uno UserGame nel db
     *
     * @param userGame da inserire nel db
     * @throws DaoException
     */
    @Override
    public void insertUserGame( UserGame userGame ) throws DaoException {

        if( userGame.getId() >= 1){ // se lo UserGame possiede gia un id vado a fare un update
            this.updateUserGame(userGame);
        }else{

            try {
                this.insertGameUser.setInt(1, userGame.getUserId());
                this.insertGameUser.setInt(2, userGame.getGameId());
                this.insertGameUser.setTimestamp(3, userGame.getDate());

                this.insertGameUser.executeUpdate();

            } catch (SQLException e) {
                throw new DaoException("Error get user game dao", e);
            }
        }

    }

    /**
     * metodo per l'update di un UserGame nel db
     * @param userGame su cui fare l'update
     * @throws DaoException
     */
    @Override
    public void updateUserGame(UserGame userGame) throws DaoException {


        try {

            this.updateGameUser.setInt(1, userGame.getUserId());
            this.updateGameUser.setInt(2, userGame.getGameId());
            this.updateGameUser.setTimestamp(3, userGame.getDate());
            this.updateGameUser.setInt(4, userGame.getId());

            this.updateGameUser.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Error update user game dao", e);
        }

    }

    /**
     * metodo per cancellare uno UserGame dal db
     * @param userGame da cancellare dal db
     * @throws DaoException
     */
    @Override
    public void deleteUserGame(UserGame userGame) throws DaoException {

        try {

            this.deleteGameUser.setInt(1, userGame.getId());

            this.deleteGameUser.executeUpdate();


        } catch (SQLException e) {
            throw new DaoException("Error delete user game dao", e);
        }
    }

    /**
     * Metodo che restituisce un'insieme di chiave valore che rappresentano la data in cui l'utente con ID userId ha cambiato livello in cui si trova
     *
     * @return un'insieme di chiave valore che rappresentano la data in cui l'utente con ID userId ha cambiato livello in cui si trova
     * @throws DaoException lancia eccezione in caso di errore
     */
    @Override
    public List<List<Object>> getLastXItemsFromUserGame(int userId, int n) throws DaoException {

        List<List<Object>> list = new ArrayList<>();

        try {
            this.selectLastXItems.setInt(1, userId); //setto la query
            this.selectLastXItems.setInt(2, n);
            ResultSet rs = this.selectLastXItems.executeQuery(); //eseguo la query



            while (rs.next()) { //ciclo ogni tupla del risultato

                List<Object> innerList = new ArrayList<>();

                innerList.add(rs.getTimestamp("date")); //estraggo la data dal risultato della query
                innerList.add(stripSlashes(rs.getString("name")));
                innerList.add(stripSlashes(rs.getString("image")));
                innerList.add((rs.getInt("exp")));


                list.add(innerList);

            }

        } catch (Exception e) {
            throw new DaoException("Error get games by user id in user games dao", e);
        }
        return list;
    }

    /**
     * chiudo la connessione e le query precompilate
     *
     * @throws gamingplatform.dao.exception.DaoException
     */
    @Override
    public void destroy() throws DaoException {

        //chiudo le quary precompilate
        try {

            this.selectUserGameById.close();
            this.selectLastXItems.close();
            this.insertGameUser.close();
            this.updateGameUser.close();
            this.deleteGameUser.close();


        } catch (Exception e) {
            throw new DaoException("Error destroy dao user", e);
        }

        //chiudo la connessione
        super.destroy();
    }
}