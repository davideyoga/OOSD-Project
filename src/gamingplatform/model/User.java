package gamingplatform.model;

import gamingplatform.dao.data.DaoData;

/**
 * Classe che rappresenta l'entita' Utente
 */
public class User{

    //Attributes
    private int id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;
    private int exp;
    private String avatar;

    /**
     * Costruttore con valori nulli
     * @param d
     */
    public User(DaoData d){
        this.id = 0;
        this.username = null;
        this.name = null;
        this.surname =null;
        this.email = null;
        this.password = null;
        this.exp = 0;
        this.avatar = null;
    }

    /**
     * Torna id corrente
     * @return
     */
    public int getId() {
            return id;
        }

    /**
     * Setta id
     * @param id
     */
    public void setId(int id) {
            this.id = id;
        }

    /**
     * Torna Username corrente
     * @return
     */
    public String getUsername() {
            return username;
        }

    /**
     * Setta Username
     * @param username
     */
    public void setUsername(String username) {
            this.username = username;
        }

    /**
     * Torna nome corrente
     * @return
     */
    public String getName() {
            return name;
        }

    /**
     * Setta nome
     * @param name
     */
    public void setName(String name) {
            this.name = name;
        }

    /**
     * Torna surname corrente
     * @return
     */
    public String getSurname() {
            return surname;
        }

    /**
     * Setta surname
     * @param surname
     */
    public void setSurname(String surname) {
            this.surname = surname;
        }

    /**
     * Tornam email corrente
     * @return
     */
    public String getEmail() {
            return email;
        }

    /**
     * Setta email
     * @param email
     */
    public void setEmail(String email) {
            this.email = email;
        }

    /**
     * Torna password corrente
     * @return
     */
    public String getPassword() {
            return password;
        }

    /**
     * Setta password
     * @param password
     */
    public void setPassword(String password) {
            this.password = password;
        }

    /**
     * Torna esperienza corrente
     * @return
     */
    public int getExp() {
            return exp;
        }

    /**
     * Setta esperienza
     * @param exp
     */
    public void setExp(int exp) {
            this.exp = exp;
        }

    /**
     * Torna avatar corrente
     * @return
     */
    public String getAvatar() {
            return avatar;
        }

    /**
     * Setta avatar
     * @param avatar
     */
    public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
}