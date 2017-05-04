package gamingplatform.dao.interfaces;

import gamingplatform.dao.data.DaoData;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.Service;

import java.util.List;

public interface ServiceDao extends DaoData{

    public Service getServiceById( int idService) throws DaoException;

    public List<Service> getServices() throws DaoException;

    public void insertService(String name, String description) throws DaoException;

    public void deleteServiceById(int idService) throws DaoException;

    public void updateService(int id,String name, String description) throws DaoException;



    public void destroy() throws DaoException;
}