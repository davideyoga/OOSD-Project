package gamingplatform.dao.interfaces;

import gamingplatform.dao.data.DaoData;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.Service;

import java.util.List;

/**
 * Interfaccia per la gestione dei Servizi nel database
 */
public interface ServiceDao extends DaoData{

    /**
     * Torna servizio vuoto
     * @return
     */
    public Service getService();

    /**
     * Torna Service nel db con id passato
     * @param idService
     * @return
     * @throws DaoException
     */
    public Service getServiceById( int idService) throws DaoException;

    /**
     * Torna la lista dei Service presenti nel databae
     * @return
     * @throws DaoException
     */
    public List<Service> getServices() throws DaoException;

    /**
     * Torna lista dei Service nel database con tale id utente
     * @param idUser
     * @return
     * @throws DaoException
     */
    public List<Service> getServicesByUserId (int idUser) throws DaoException;

    /**
     * Inserisce nel database il servizio passato
     * @param service
     * @throws DaoException
     */
    public void insertService( Service service) throws DaoException;

    /**
     * Cancella dal database il servizio con id passato
     * @param idService
     * @throws DaoException
     */
    public void deleteServiceById(int idService) throws DaoException;

    /**
     * esegue nel db l'update del servizio con id del servizio passato
     * @param service
     * @throws DaoException
     */
    public void updateSerivce( Service service) throws DaoException;

}