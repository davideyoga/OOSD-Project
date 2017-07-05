package gamingplatform.model;

import gamingplatform.dao.data.DaoData;

/**
 * Created by GregBug on 22/04/2017.
 * Classe che rappresenta l'entita' servizio
 */
public class Service {

    private int id;
    private String name;
    private String description;

    /**
     * Costruttore con valori nulli
     * @param d
     */
    public Service(DaoData d) {
        this.id = 0;
        this.name =null;
        this.description = null;
    }

    /**
     * Setta id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setta nome
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setta descrizione
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
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
     * Torna descrizione
     * @return
     */
    public String getDescription() {
        return description;
    }
}
