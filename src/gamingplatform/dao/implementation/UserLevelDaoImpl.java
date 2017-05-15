package gamingplatform.dao.implementation;

import gamingplatform.dao.data.DaoDataMySQLImpl;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.interfaces.UserLevelDao;
import gamingplatform.model.Level;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static gamingplatform.controller.utils.SecurityLayer.stripSlashes;

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

            this.selectLevelsByUserId = connection.prepareStatement("SELECT userlevel.date," +
                                                                                " level.id," +
                                                                                " level.name," +
                                                                                " level.trophy," +
                                                                                " level.icon," +
                                                                                " level.exp" +
                                                                        " FROM level LEFT JOIN userlevel ON level.id = userlevel.id_level " +
                                                                        "WHERE userlevel.id_user = ? ");

        } catch (Exception e) {
            throw new DaoException("Error initializing user level dao", e);
        }
    }
    /**
     * Metodo che restituisce un'insieme di chiave valore che rappresentano la data in cui l'utente con ID userId ha cambiato livello in cui si trova
     * @return un'insieme di chiave valore che rappresentano la data in cui l'utente con ID userId ha cambiato livello in cui si trova
     * @throws DaoException lancia eccezione in caso di errore
     */
    @Override
    public Map<Timestamp, Level> getDateLevelsByUserId(int userId) throws DaoException{

        Map<Timestamp, Level> map = new HashMap<>();

        try {
            this.selectLevelsByUserId.setInt(1, userId); //setto la query
            ResultSet rs = this.selectLevelsByUserId.executeQuery(); //eseguo la query

            while( rs.next() ){ //ciclo ogni tupla del risultato

                Level level = new Level(this); //dichiaro il livello corrispondente alla data da inserire nella mappa

                Timestamp date = rs.getTimestamp("date"); //estraggo la data dal risultato della query

                // setto le variabili del livello da restituire nella mappa
                level.setId(rs.getInt("id"));
                level.setName(rs.getInt("name"));
                level.setTrophy(stripSlashes(rs.getString("trophy")));
                level.setIcon(stripSlashes(rs.getString("icon")));
                level.setExp(rs.getInt("exp"));

                map.put(date,level);

            }

        } catch (Exception e) {
            throw new DaoException("Error get levels by user id in user level dao", e);
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
            this.selectLevelsByUserId.close();

        } catch (Exception e) {
            throw new DaoException("Error destroy dao user", e);
        }

        //chiudo la connessione
        super.destroy();
    }
}