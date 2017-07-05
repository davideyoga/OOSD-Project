package gamingplatform.model;

import gamingplatform.dao.data.DaoData;

/**
 * Classe che rappresenta l'entita' Recensione
 */
public class Review {

    private int id_user;
    private int id_game;
    private String title;
    private String body;
    private int vote;

    /**
     * Costruttore con valori nulli
     * @param d
     */
    public Review(DaoData d) {
        this.id_user = 0;
        this.id_game = 0;
        this.title = null;
        this.body = null;
        this.vote = 0;
    }

    /**
     * Torna id dell'utente corrente
     * @return
     */
    public int getIdUser() {
        return id_user;
    }

    /**
     * Torna id del gioco corrente
     * @return
     */
    public int getIdGame() {
        return id_game;
    }

    /**
     * Torna titolo corrente
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Torna Il corpo della recensione corrente
     * @return
     */
    public String getBody() {
        return body;
    }

    /**
     * Torna voto corrente
     * @return
     */
    public int getVote() {
        return vote;
    }

    /**
     * Setta id dell'utente corrente
     * @param id_user
     */
    public void setIdUser(int id_user) {
        this.id_user = id_user;
    }

    /**
     * Setta id del gioco corrente
     * @param id_game
     */
    public void setIdGame(int id_game) {
        this.id_game = id_game;
    }

    /**
     * Setta titolo
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Setta il corpo della recensione
     * @param body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Setta il voto
     * @param vote
     */
    public void setVote(int vote) {
        this.vote = vote;
    }
}
