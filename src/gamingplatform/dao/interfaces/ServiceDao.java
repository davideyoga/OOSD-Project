package gamingplatform.dao.interfaces;

import gamingplatform.dao.data.DaoData;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.Service;

import java.util.List;

public interface ServiceDao extends DaoData{

    public Service getService();

    public Service getServiceById( int idService) throws DaoException;

    public List<Service> getServices() throws DaoException;

    public List<Service> getServicesByUserId (int idUser) throws DaoException;

    public void insertService( Service service) throws DaoException;

    public void deleteServiceById(int idService) throws DaoException;

    public void updateSerivce( Service service) throws DaoException;

    public void destroy() throws DaoException;
}