package gamingplatform.model;
import gamingplatform.dao.data.DaoData;
import java.sql.Timestamp;
/**
 * Created by GregBug on 15/05/2017.
 */
public class UserGame {

    // Attributes
    private int id;
    private Timestamp date;

    public UserGame(DaoData d) {
        this.id = 0;
        this.date = null;
    }

    //Getter and Setter

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Timestamp getDate() { return date; }

    public void setDate (Timestamp data) {this.date = data; }
}
