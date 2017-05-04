package gamingplatform.dao.implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import gamingplatform.dao.data.DaoDataMySQLImpl;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.interfaces.ServiceDao;
import gamingplatform.model.Service;


public class ServiceDaoImpl extends DaoDataMySQLImpl implements ServiceDao {

    //Variabili di appoggio per preparare le query
    private PreparedStatement selectServiceById,
                              selectServices,
                              insertService,
                              deleteServiceById,
                              updateService,
                              selectServicesByUserId;


    //Costruttore
    public ServiceDaoImpl(DataSource datasource){
        super (datasource);
    }

    /**
     * Metodo incui viene inizializzata la connessione e vengono preparate le query
     * di insert, selct,delete e update
     * @throws DaoException eccezione che viene lanciata in caso di fallimento di
     * inizializzazione query
     */
    public void init() throws DaoException{
        try{
            super.init();//inizializzazione connessione

            //query che mi restituisce il gioco con id dato
            selectServiceById=connection.prepareStatement("SELECT * FROM service WHERE id=?");

            //query che mi restituisce lista di giochi
            selectServices=connection.prepareStatement("SELECT * FROM service");

            //query di inserimento di una nuova tupla nella tabella game
            insertService=connection.prepareStatement("INSERT INTO service " +
                    "                                              VALUES name=?," +
                    "                                                     description=?");

            //query di eliminazione di un gioco con id dato
            deleteServiceById=connection.prepareStatement("DELETE FROM service WHERE id=?");

            //query di update di un dato gioco
            updateService=connection.prepareStatement("UPDATE service" +
                    "                                       SET name=?" + //L'ID NON LO PUOI MODIFICARE
                    "                                           description=?" +
                    "                                       WHERE id=?");

            //query che mi restituisce i servizi a cui un utente ha autorizzazione ad accedere
            selectServicesByUserId=connection.prepareStatement("SELECT service.id, service.name, service.description" +
                    "                                                FROM user " +
                    "                                                LEFT JOIN usergroups ON usergroups.id_user=user.id" +
                    "                                                LEFT JOIN groups ON groups.id=usergroups.id_groups" +
                    "                                                LEFT JOIN groupsservice ON groupsservice.id_groups=groups.id" +
                    "                                                LEFT JOIN service ON groupsservice.id_service=service.id" +
                    "                                                WHERE USER.id=? ORDER BY service.name");

        }catch (SQLException e){
            throw new DaoException("Error initializing group dao", e);
        }
    }



    /**
     * Metodo che seleziona un servizio dato l'id
     * @param idService è l'id del servizio che si vuole visualizzare
     * @return il gioco desiderato
     */
    public Service getServiceById(int idService) throws DaoException{
       Service s=new Service(this);
        try{
            this.selectServiceById.setInt(1,idService);
            ResultSet rs=this.selectServiceById.executeQuery();
            while(rs.next()){
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setDescription(rs.getString("description"));
            }
        }catch (SQLException e){
            throw new DaoException("Error game getServiceById", e);

        }
        return s;
    }



    /**
     * Mestodo che restituisce una lista di servizi
     * @return lista di servizi
     * @throws DaoException lancia eccezione in caso di errore
     */
    public List<Service> getServices() throws DaoException{
        List<Service> lista=new ArrayList<>();
        try{
            ResultSet rs=this.selectServices.executeQuery();

            while(rs.next())
            {
                Service s=new Service(this);
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setDescription(rs.getString("description"));
                lista.add(s);
            }
        }catch (SQLException e){
            throw new DaoException("Error query getServices", e);

        }

        return lista;
    }


    /**
     * Metodo di inserimento di un nuovo elemento nella tabella service del DataBase
     * @param name è il nome del servizio
     * @param description la descrizione del servizio
     * @throws DaoException
     */
    public void insertService(String name, String description) throws DaoException{
        try{
            this.insertService.setString(1,name);
            this.insertService.setString(2,description);
            this.insertService.executeQuery();

        } catch (SQLException e){
            throw new DaoException("Error query insertService", e);

        }
    }



    /**
     * Metodo che permette la cancellazione di un servizio dalla tabella service dato l'id
     * @param idService è l'id del servizio da cancellare
     * @throws DaoException lancia eccezione in caso di errore
     */
    public void deleteServiceById(int idService) throws DaoException{
        try{
            this.deleteServiceById.setInt(1,idService);
            this.deleteServiceById.executeQuery();
        }catch (SQLException e){
            throw new DaoException("Error query deleteServiceById", e);

        }
    }


    /**
     * Metodo che permette la modifica di un servizio in service
     * @param id è l'identificativo del servizio
     * @param name è il nome del servizio
     * @param description è la descrizione del servizio
     * @throws DaoException lancia eccezione in caso di errore
     */
    public void updateService(int id,String name, String description) throws DaoException{
        try{
            this.updateService.setString(1,name);
            this.updateService.setString(2,description);
        }catch (SQLException e){
            throw new DaoException("Error query updateService", e);
        }
    }



    public List<Service> getServicesByUserId(int idUser) throws DaoException{

        List<Service> lista=new ArrayList<>();
        try{
            this.selectServicesByUserId.setInt(1,idUser);
            ResultSet rs=this.selectServicesByUserId.executeQuery();

            while (rs.next()){
                Service s=new Service(this);
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setDescription(rs.getString("description"));
                 lista.add(s);
            }

        }catch(Exception e){
            throw new DaoException("Error query getServiceByUserId", e);

        }
        return lista;
    }



    public void destroy() throws DaoException{

        //chiudo le quary precompilate
        try {
            this.selectServiceById.close();
            this.selectServices.close();
            this.insertService.close();
            this.deleteServiceById.close();
            this.updateService.close();

        } catch (SQLException e) {
            throw new DaoException("Error destroy ServiceDao", e);
        }

        //chiudo la connessione
        super.destroy();
    }
}
