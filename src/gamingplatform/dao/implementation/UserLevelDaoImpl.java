package gamingplatform.dao.implementation;

import gamingplatform.dao.data.DaoDataMySQLImpl;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.interfaces.UserLevelDao;
import gamingplatform.model.Level;
import gamingplatform.model.UserLevel;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gamingplatform.controller.utils.SecurityLayer.stripSlashes;

public class UserLevelDaoImpl extends DaoDataMySQLImpl implements UserLevelDao {

    private PreparedStatement selectLastXitems;

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

        } catch (Exception e) {
            throw new DaoException("Error destroy dao user", e);
        }

        //chiudo la connessione
        super.destroy();
    }
}