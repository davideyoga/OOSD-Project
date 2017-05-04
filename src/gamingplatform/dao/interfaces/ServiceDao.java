package gamingplatform.dao.interfaces;

import gamingplatform.dao.data.DaoData;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.Service;

import java.util.List;

public interface ServiceDao extends DaoData{

    public Service getServiceById( int keyService) throws DaoException;

    public List<Service> getServices() throws DaoException;

    public void insertService(Service s) throws DaoException;

    public void deleteServiceById(int keyService) throws DaoException;

    public void updateService(int id,String name, String description) throws DaoException;



    public void destroy() throws DaoException;
}