package gamingplatform.model;

/**
 * Created by GregBug on 22/04/2017.
 */
// Classe
public class Group {

    private int id;
    private String name;
    private String description;

    // Costruttore
    public Group(int id, String name, String description) {
        this.id = id;
        this.name = name;
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

