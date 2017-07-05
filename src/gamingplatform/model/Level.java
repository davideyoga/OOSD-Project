package gamingplatform.model;

import gamingplatform.dao.data.DaoData;

/**
 * Classe che rappresenta l'entita' Livello
 */
public class Level{

    //Attributes
    private int id;
    private int name; //Il generico livello sarà chiamato esattamente con il numero corrispondente a quel livello
    private String trophy;
    private String icon;
    private int exp;

    /**
     * Costruttore con parametri nulli
     * @param d
     */
    public Level(DaoData d) {
        this.id = 0;
        this.name = 0;
        this.trophy = null;
        this.icon = null;
        this.exp = 0;
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
     * Torna nome corrente
     * @return
     */
    public int getName() {
        return name;
    }

    /**
     * Setta nome
     * @param name
     */
    public void setName(int name) {
        this.name = name;
    }

    /**
     * Torna trofeo corrente
     * @return
     */
    public String getTrophy() {
        return trophy;
    }

    /**
     * Setta trofeo
     * @param trophy
     */
    public void setTrophy(String trophy) {
        this.trophy = trophy;
    }

    /**
     * Torna icona corrente
     * @return
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Setta icona
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * Trona esperienza corrente
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
}