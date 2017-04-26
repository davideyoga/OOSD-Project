package gamingplatform.model;

public class Level{

    //Attributes
    private int id;
    private int name; //Il generico livello sarà chiamato esattamente con il numero corrispondente a quel livello
    private String trophy;
    private String icon;
    private int exp;

    public Level(int id, int name, String trophy, String icon, int exp) {
        this.id = id;
        this.name = name;
        this.trophy = trophy;
        this.icon = icon;
        this.exp = exp;
    }


    //Getter and Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public String getTrophy() {
        return trophy;
    }

    public void setTrophy(String trophy) {
        this.trophy = trophy;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}