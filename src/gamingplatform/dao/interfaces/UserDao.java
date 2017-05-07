package gamingplatform.dao.interfaces;


import gamingplatform.dao.data.*;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.Group;
import gamingplatform.model.User;

public interface UserDao extends DaoData{	
	
	public void insertUser( String username,String name, String surname,String email, String password,int exp, String avatar) throws DaoException;

	public void insertUser( User user) throws DaoException;
	
	public void deleteUserByKey( int idUser ) throws DaoException;
	
	public void updateUser(String username,String name, String surname,String email, String password,int exp, String avatar) throws DaoException;

	public void updateUser( User user ) throws DaoException;
	
	public User getUser( int idUser) throws DaoException;
	
	public void destroy() throws DaoException;

	public User getUserByUsernamePassword(String username, String password) throws DaoException;
	
}