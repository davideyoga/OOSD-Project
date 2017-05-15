package gamingplatform.model;
import gamingplatform.dao.data.DaoData;
import java.sql.Timestamp;
/**
 * Created by GregBug on 15/05/2017.
 */
public class UserGame {

    // Attributes
    private int id;
    private int id_user;
    private int id_game;
    private Timestamp date;

    public UserGame(DaoData d) {
        this.id=0;
        this.id_user = 0;
        this.id_game = 0;
        this.date = null;
    }

    //Getter and Setter

    public int getId() { return id; }

    public int getUserId() { return id_user; }

    public int getGameId() { return id_game; }

    public void setId(int id){ this.id=id; }

    public void setUserId(int id){ this.id_user=id;}

    public void setGameId(int id){ this.id_game=id;}

    public Timestamp getDate() { return date; }

    public void setDate (Timestamp data) {this.date = data; }
}
