package gamingplatform.model;

import gamingplatform.dao.data.DaoData;

/**
 * Created by GregBug on 22/04/2017.
 */
// Classe
public class Service {

    private int id;
    private String name;
    private String description;

    // Costruttore
    public Service(DaoData d) {
        this.id = 0;
        this.name =null;
        this.description = null;
    }

    // *** Metodi Setter ***
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // *** Metodi Getter ***
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
