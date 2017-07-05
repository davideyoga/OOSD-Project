package gamingplatform.model;

import gamingplatform.dao.data.DaoData;
import gamingplatform.dao.data.DaoDataMySQLImpl;

/**
 * Classe che rappresenta l'entita' Gruppo
 */
public class Group {

    //Attributi
    private int id;
    private String name;
    private String description;

    /**
     * Costruttore con parametri nulli
     * @param d
     */
    public Group( DaoData d){
        this.id = 0;
        this.name = null;
        this.description = null;
    }

    /**
     * Torna id corrente
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Torna nome corrente
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Torna descrizione corrente
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setta id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setta il nome
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setta la descrizione
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}

