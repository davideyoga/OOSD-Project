package gamingplatform.model;

import gamingplatform.dao.data.DaoData;
import gamingplatform.dao.data.DaoDataMySQLImpl;

// Classe
public class Group {

    //Attributi
    private int id;
    private String name;
    private String description;

    // Costruttore
    public Group( DaoData d){
        this.id = 0;
        this.name = null;
        this.description = null;
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
}

