package gamingplatform.model;
import gamingplatform.dao.data.DaoData;
import java.sql.Timestamp;
/**
 * Created by GregBug on 15/05/2017.
 *
 * Classe che rappresenta l'entita' UserLevel (Relazione tra utente e livello)
 */
public class UserLevel {

    // Attritubetes
    private int id;
    private int id_user;
    private int id_level;
    private Timestamp date;

    /**
     * Costruttore con valori nulli
     * @param d
     */
    public UserLevel(DaoData d){
        this.id = 0;
        this.id_user=0;
        this.id_level=0;
        this.date = null;
    }

    /**
     * Torna id corrente
     * @return
     */
    public int getId() { return id; }

    /**
     * Torna id dell'user corrente
     * @return
     */
    public int getUserId() { return id_user; }

    /**
     * Torna id del livello corrente
     * @return
     */
    public int getLevelId() { return id_level; }

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
     * Setta id del livello
     * @param id
     */
    public void setLevelId(int id){ this.id_level=id;}

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
