package gamingplatform.model;

/**
 * Created by GregBug on 22/04/2017.
 */
// Classe
public class Review {

    private int id_user;
    private int id_game;
    private String title;
    private String body;
    private int vote;

    // Costruttore
    public Review(int id_user, int id_game, String title, String body, int vote) {
        this.id_user = id_user;
        this.id_game = id_game;
        this.title = title;
        this.body = body;
        this.vote = vote;
    }

    // *** Metodi Getter ***
    public int getId_user() {
        return id_user;
    }

    public int getId_game() {
        return id_game;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public int getVote() {
        return vote;
    }

    // *** Metodi Setter ***
    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setId_game(int id_game) {
        this.id_game = id_game;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }
}
