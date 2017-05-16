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

    private PreparedStatement selectLastXItems;

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

            this.selectLastXItems = connection.prepareStatement("SELECT game.exp, game.name, usergame.date " +
                    "FROM usergame LEFT JOIN game ON game.id=usergame.id_game " +
                    "WHERE usergame.id_user = ? ORDER BY date DESC LIMIT ?");

        } catch (Exception e) {
            throw new DaoException("Error initializing user game dao", e);
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
            this.selectLastXItems.close();

        } catch (Exception e) {
            throw new DaoException("Error destroy dao user", e);
        }

        //chiudo la connessione
        super.destroy();
    }
}