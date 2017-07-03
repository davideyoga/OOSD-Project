package gamingplatform.dao.interfaces;


import gamingplatform.dao.data.*;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.Group;
import gamingplatform.model.User;
import gamingplatform.model.Level;

import java.util.List;

/**
 * Interfaccia per la gestione degli User nel db
 */
public interface UserDao extends DaoData{

	/**
	 * Inserisce nel db l'user passato
	 * @param user
	 * @throws DaoException
	 */
	public void insertUser( User user) throws DaoException;

	/**
	 * Elimina dal db l'User con l'id passato
	 * @param idUser
	 * @throws DaoException
	 */
	public void deleteUserByKey( int idUser ) throws DaoException;

	/**
	 * Esegue l'update dell'utente passato
	 * @param user
	 * @throws DaoException
	 */
	public void updateUser( User user ) throws DaoException;

	/**
	 * Torna User vuoto
	 * @return
	 */
	public User getUser();

	/**
	 * Torna dal db la lista degli utenti
	 * @return
	 * @throws DaoException
	 */
	public List<User> getUsers() throws DaoException;

	/**
	 * Torna dal db l'user con id passato
	 * @param idUser
	 * @return
	 * @throws DaoException
	 */
	public User getUser( int idUser) throws DaoException;

	/**
	 * Torna dal database l'User con emai e password passati
	 * @param username
	 * @param password
	 * @return
	 * @throws DaoException
	 */
	public User getUserByUsernamePassword(String username, String password) throws DaoException;

	/**
	 * Torna il livello a cui appartiene un utente
	 * @param user_id
	 * @return
	 * @throws DaoException
	 */
	public Level getLevelByUserId(int user_id) throws DaoException;
}