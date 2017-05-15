package gamingplatform.model;
import gamingplatform.dao.data.DaoData;
import java.sql.Timestamp;
/**
 * Created by GregBug on 15/05/2017.
 */

public class UserLevel {

    // Attritubetes
    private int id;
    private Timestamp date;

    public UserLevel(DaoData d){
        this.id = 0;
        this.date = null;
    }

    public int getId() {
        return id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
