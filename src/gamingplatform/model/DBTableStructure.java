package gamingplatform.model;


import gamingplatform.dao.data.DaoData;
import java.util.HashMap;

/**
 *
 */
public class DBTableStructure {

    //Attributi
    private HashMap<Integer, String> fields;
    private HashMap<Integer, String> types;
    private HashMap<Integer, String> nulls;
    private HashMap<Integer, String> keys;
    private HashMap<Integer, String> defaults;
    private HashMap<Integer, String> extras;


    //Costruttore
    public DBTableStructure(DaoData d){
        fields=new HashMap<>();
        types= new HashMap<>();
        nulls=new HashMap<>();
        keys=new HashMap<>();
        defaults=new HashMap<>();
        extras=new HashMap<>();
    }



    //Getter and setter
    public HashMap<Integer, String> getFields() {
        return fields;
    }

    public void setFields(HashMap<Integer, String> fields) {
        this.fields = fields;
    }

    public HashMap<Integer, String> getTypes() {
        return types;
    }

    public void setTypes(HashMap<Integer, String> types) {
        this.types = types;
    }

    public HashMap<Integer, String> getNulls() {
        return nulls;
    }

    public void setNulls(HashMap<Integer, String> nulls) {
        this.nulls = nulls;
    }

    public HashMap<Integer, String> getKeys() {
        return keys;
    }

    public void setKeys(HashMap<Integer, String> keys) {
        this.keys = keys;
    }

    public HashMap<Integer, String> getDefaults() {
        return defaults;
    }

    public void setDefaults(HashMap<Integer, String> defaults) {
        this.defaults = defaults;
    }

    public HashMap<Integer, String> getExtras() {
        return extras;
    }

    public void setExtras(HashMap<Integer, String> extras) {
        this.extras = extras;
    }

    public int getArity (){
        return fields.size();
    }
}
