package gamingplatform.dao.implementation;

import gamingplatform.dao.data.DaoDataMySQLImpl;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.interfaces.UserDao;
import gamingplatform.dao.interfaces.UserGameDao;
import gamingplatform.model.Game;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static gamingplatform.controller.utils.SecurityLayer.stripSlashes;

public class UserGameDaoImpl extends DaoDataMySQLImpl implements UserGameDao {

    private PreparedStatement selectGamesByUserId;

    /**
     * Costruttore per inizializzare la connessione
     * @param datasource e' la risorsa di connessione messa a disposizione del connection pooling
     */
    public UserGameDaoImpl(DataSource datasource) {
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

            this.selectGamesByUserId = connection.prepareStatement("SELECT usergame.date, " +
                                                                               "game.id, " +
                                                                               "game.name, " +
                                                                               "game.exp, " +
                                                                               "game.description, " +
                                                                               "game.image "+
                                                                               "FROM game " +
                                                                               "LEFT JOIN usergame ON game.id = usergame.id_game " +
                                                                               "WHERE usergame.id_user = ?");

        } catch (SQLException e) {
            throw new DaoException("Error initializing user game dao", e);
        }
    }
    /**
     * Metodo che restituisce un'insieme di chiave valore che rappresentano la data in cui l'utente con ID userId ha cambiato livello in cui si trova
     * @return un'insieme di chiave valore che rappresentano la data in cui l'utente con ID userId ha cambiato livello in cui si trova
     * @throws DaoException lancia eccezione in caso di errore
     */
    @Override
    public Map<Timestamp, Game> getDateGamesByUserId(int userId) throws DaoException{

        Map<Timestamp, Game> map = new HashMap<>();

        try {
            this.selectGamesByUserId.setInt(1, userId); //setto la query
            ResultSet rs = this.selectGamesByUserId.executeQuery(); //eseguo la query

            while( rs.next() ){ //ciclo ogni tupla del risultato

                Game game = new Game(this); //dichiaro il livello corrispondente alla data da inserire nella mappa

                Timestamp date = rs.getTimestamp("date"); //estraggo la data dal risultato della query

                // setto le variabili del livello da restituire nella mappa
                game.setId(rs.getInt("id"));
                game.setName(stripSlashes(rs.getString("name")));
                game.setExp(rs.getInt("exp"));
                game.setImage(stripSlashes(rs.getString("image")));
                game.setDescription(stripSlashes(rs.getString("description")));

                map.put(date,game);

            }

        } catch (SQLException e) {
            throw new DaoException("Error get games by user id in user games dao", e);
        }
        return map;
    }

    /**
     * chiudo la connessione e le query precompilate
     * @throws gamingplatform.dao.exception.DaoException
     */
    @Override
    public void destroy() throws DaoException {

        //chiudo le quary precompilate
        try {
            this.selectGamesByUserId.close();

        } catch (SQLException e) {
            throw new DaoException("Error destroy dao user", e);
        }

        //chiudo la connessione
        super.destroy();
    }
}