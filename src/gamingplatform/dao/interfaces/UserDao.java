package gamingplatform.dao.interfaces;


import gamingplatform.dao.data.*;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.Group;
import gamingplatform.model.User;

public interface UserDao extends DaoData{	
	
	void insertUser( User user) throws DaoException;
	
	void deleteUserByKey( int keyUser ) throws DaoException;
	
	void updateUser( User user) throws DaoException;
	
	User getUser( int keyUser) throws DaoException;
	
	void destroy() throws DaoException;

	User getUserByUsernamePassword(String username, String password) throws DaoException;
	
}