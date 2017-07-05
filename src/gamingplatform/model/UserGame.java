package gamingplatform.model;
import gamingplatform.dao.data.DaoData;
import java.sql.Timestamp;
/**
 * Created by GregBug on 15/05/2017.
 * Classe che rappresenta l'entita' UserGame (Relazione tra utente e gioco)
 */
public class UserGame {

    // Attributes
    private int id;
    private int id_user;
    private int id_game;
    private Timestamp date;

    /**
     * Costruttore con valori nulli
     * @param d
     */
    public UserGame(DaoData d) {
        this.id=0;
        this.id_user = 0;
        this.id_game = 0;
        this.date = null;
    }

    /**
     * Torna id corrente
     * @return
     */
    public int getId() { return id; }

    /**
     * Torna id dell'utente
     * @return
     */
    public int getUserId() { return id_user; }

    /**
     * Torna id del gioco
     * @return
     */
    public int getGameId() { return id_game; }

    /**
     * Setta id
     * @param id
     */
    public void setId(int id){ this.id=id; }

    /**
     * Setta id dell'utente
     * @param id
     */
    public void setUserId(int id){ this.id_user=id;}

    /**
     * Setta id del gioco
     * @param id
     */
    public void setGameId(int id){ this.id_game=id;}

    /**
     * Torna data corrente
     * @return
     */
    public Timestamp getDate() { return date; }

    /**
     * Setta data
     * @param data
     */
    public void setDate (Timestamp data) {this.date = data; }
}
