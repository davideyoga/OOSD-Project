package com.company;

/**
 * Created by GregBug on 22/04/2017.
 */
// Classe
public class service {

    int id;
    String name;
    String description;

    // Costruttore
    public service(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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
