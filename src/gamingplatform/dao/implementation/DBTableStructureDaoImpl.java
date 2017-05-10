package gamingplatform.dao.implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.sql.DataSource;
import gamingplatform.dao.data.DaoDataMySQLImpl;
import gamingplatform.dao.interfaces.DBTableStructureDao;
import gamingplatform.model.DBTableStructure;
import gamingplatform.dao.exception.DaoException;
import static gamingplatform.controller.utils.SecurityLayer.stripSlashes;
import static gamingplatform.controller.utils.SecurityLayer.addSlashes;



public class DBTableStructureDaoImpl extends DaoDataMySQLImpl implements DBTableStructureDao {

    //Variabili di appoggio per preparazione query
    private PreparedStatement getTableStructure;


    public DBTableStructureDaoImpl (DataSource dataSource){ super (dataSource);}


    public void init(String table) throws DaoException {
        try{
            super.init();

            //query che mi restituisce la struttura di una tabella
            getTableStructure=connection.prepareStatement("SHOW COLUMNS FROM "+table);

        }catch (SQLException | DaoException e){
            throw new DaoException("Error initializing DBTableStructure",e);
        }
    }


    /**
     * Metodo che restituisce un DBTableStructure vuoto
     */
    @Override
    public DBTableStructure getDBTableStructure() {
        return new DBTableStructure(this);
    }

    public DBTableStructure getTableStructure () throws DaoException{

        DBTableStructure ts=new DBTableStructure(this);

        try{
            ResultSet rs = this.getTableStructure.executeQuery();
            Integer order = new Integer(1);

            HashMap<Integer,String> f=new HashMap<>();
            HashMap<Integer,String> t=new HashMap<>();
            HashMap<Integer,String> n=new HashMap<>();
            HashMap<Integer,String> k=new HashMap<>();
            HashMap<Integer,String> d=new HashMap<>();
            HashMap<Integer,String> e=new HashMap<>();

            while(rs.next()) {
                f.put(order, rs.getString("Field"));
                t.put(order, rs.getString("Type"));
                n.put(order, rs.getString("Null"));
                k.put(order, rs.getString("Key"));
                d.put(order, rs.getString("Default"));
                e.put(order, rs.getString("Extra"));
                order+=1;
            }

            ts.setFields(f);
            ts.setTypes(t);
            ts.setNulls(n);
            ts.setKeys(k);
            ts.setDefaults(d);
            ts.setExtras(e);

        }catch (Exception exep){
            throw new DaoException("Error query getTableStructure",exep);
        }
        return ts;
    }



    public void destroy() throws DaoException{

        //chiudo le quary precompilate
        try {
            this.getTableStructure.close();

        } catch (SQLException e) {
            throw new DaoException("Error destroy GameDao", e);
        }

        //chiudo la connessione
        super.destroy();
    }



}
