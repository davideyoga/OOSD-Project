package gamingplatform.dao.interfaces;


import gamingplatform.dao.data.*;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.Group;
import gamingplatform.model.User;

import java.util.List;

public interface UserDao extends DaoData{	
	

	public void insertUser( User user) throws DaoException;
	
	public void deleteUserByKey( int idUser ) throws DaoException;

	public void updateUser( User user ) throws DaoException;

	public User getUser();

	public List<User> getUsers() throws DaoException;

	public User getUser( int idUser) throws DaoException;
	
	public void destroy() throws DaoException;

	public User getUserByUsernamePassword(String username, String password) throws DaoException;
	
}