package gamingplatform.model;

import gamingplatform.dao.data.DaoData;

// Classe
public class Review {

    private int id_user;
    private int id_game;
    private String title;
    private String body;
    private int vote;

    // Costruttore
    public Review(DaoData d) {
        this.id_user = 0;
        this.id_game = 0;
        this.title = null;
        this.body = null;
        this.vote = 0;
    }

    // *** Metodi Getter ***
    public int getIdUser() {
        return id_user;
    }

    public int getIdGame() {
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
    public void setIdUser(int id_user) {
        this.id_user = id_user;
    }

    public void setIdGame(int id_game) {
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
