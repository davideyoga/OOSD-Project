package gamingplatform.dao.implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import gamingplatform.dao.data.DaoDataMySQLImpl;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.interfaces.UserDao;
import gamingplatform.model.Group;
import gamingplatform.model.User;

import static gamingplatform.controller.utils.SecurityLayer.addSlashes;
import static gamingplatform.controller.utils.SecurityLayer.stripSlashes;


public class UserDaoImpl extends DaoDataMySQLImpl implements UserDao{
	
	private PreparedStatement insertUser,
							  selectUserById,
	                          selectUsers,
							  getUserByUsernamePassword,
	 						  deleteUserById,
							  updateUserById;

	/**
	 * Costruttore per inizializzare la connessione
	 * @param datasource e' la risorsa di connessione messa a disposizione del connection pooling
	 */
	public UserDaoImpl(DataSource datasource) {
		super(datasource);
	}
	
	/**
	 * per connessione al database e precompilazione query
	 * @throws gamingplatform.dao.exception.DaoException
	 */
	@Override
	public void init() throws DaoException{
		try {
			super.init(); // connection initialization
			
			this.insertUser = connection.prepareStatement("INSERT INTO user VALUES(NULL,?,?,?,?,?,?,?)");
			this.selectUsers = connection.prepareStatement("SELECT * FROM user");
			this.selectUserById = connection.prepareStatement("SELECT * FROM user WHERE id=?");
			this.deleteUserById = connection.prepareStatement("DELETE FROM user WHERE id=?");
			this.updateUserById = connection.prepareStatement("UPDATE user SET username=?,name=?,surname=?,email=?, password=?, exp=?, avatar=?");
			this.getUserByUsernamePassword = connection.prepareStatement("SELECT * FROM user WHERE username=? AND password =?");

		} catch (SQLException e) {
			throw new DaoException("Error initializing user dao", e);
		}
	}


	@Override
	public void insertUser(User user) throws DaoException {

		try {

			this.insertUser.setString(1,addSlashes(user.getUsername()));
			this.insertUser.setString(2,addSlashes(user.getName()));
			this.insertUser.setString(3,addSlashes(user.getSurname()));
			this.insertUser.setString(4,addSlashes(user.getEmail()));
			this.insertUser.setString(5,user.getPassword());
			this.insertUser.setInt(6,user.getExp());
			this.insertUser.setString( 7,addSlashes(user.getAvatar()));

			this.insertUser.executeUpdate();


		} catch (SQLException e) {
			throw new DaoException("Error sql insertUser", e);
		}

	}

	/**
	 * per eliminare un utente dal database
	 * @throws gamingplatform.dao.exception.DaoException
	 */
	@Override
	public void deleteUserByKey(int idUser) throws DaoException {
		if( idUser > 0 ){
			try {		
				this.deleteUserById.setInt(1, idUser);
				this.deleteUserById.executeUpdate();
				
			} catch (SQLException e) {
				throw new DaoException("Error dao delete user", e);
			}
		}
	}

	/**
	 * Metodo che restituisce una lista di utenti
	 * @return lista di utenti
	 * @throws DaoException lancia eccezione in caso di errore
	 */
	public List<User> getUsers() throws DaoException{
		List<User> lista = new ArrayList<>();
		try{
			ResultSet rs = this.selectUsers.executeQuery();

			while(rs.next())
			{
				User u=new User(this);
				//TODO
				lista.add(u);
			}
		}catch (SQLException e){
			throw new DaoException("Error query getUsers", e);

		}

		return lista;
	}

	@Override
	public void updateUser(User user) throws DaoException {

		try{

			this.updateUserById.setString(1, addSlashes(user.getUsername()));
			this.updateUserById.setString(2, addSlashes(user.getName()));
			this.updateUserById.setString(3, addSlashes(user.getSurname()));
			this.updateUserById.setString(4, addSlashes(user.getEmail()));
			this.updateUserById.setString(5, user.getPassword());
			this.updateUserById.setInt(6, user.getExp());
			this.updateUserById.setString(7, addSlashes(user.getAvatar()));

			this.updateUserById.executeUpdate();

		}catch (SQLException e) {

			throw new DaoException("Error dao update user", e);
		}

	}

	@Override
	public User getUser() {
		return new User(this);
	}

	/**
	 * per estrarre un utente dal database
	 * @throws gamingplatform.dao.exception.DaoException
	 */
	@Override
	public User getUser(int idUser) throws DaoException {
		
		User user = new User(this);
		
		try {
			this.selectUserById.setInt(1, idUser); //inserisco nella query la chiave dell'utente

			ResultSet rs = this.selectUserById.executeQuery(); //eseguo la query

			while(rs.next()){
				user.setUsername(stripSlashes(rs.getString("username")));
				user.setName(stripSlashes(rs.getString("name")));
				user.setSurname(stripSlashes(rs.getString("surname")));
				user.setEmail(stripSlashes(rs.getString("email")));
				user.setPassword(rs.getString("password"));
				user.setExp(rs.getInt("exp"));
				user.setAvatar(stripSlashes(rs.getString("avatar")));
			}

		} catch (SQLException e) {
			throw new DaoException("Error dao user get user ", e);
		}
		
		return user;
	}

	/**
	 * per estrarre un utente dal database
	 * @throws gamingplatform.dao.exception.DaoException
	 */
	@Override
	public User getUserByUsernamePassword(String username, String password) throws DaoException {

		User user = new User(this);

		try {
			this.getUserByUsernamePassword.setString(1, addSlashes(username));
			this.getUserByUsernamePassword.setString(2, addSlashes(password));

			ResultSet rs = this.getUserByUsernamePassword.executeQuery(); //eseguo la query

			while(rs.next()){
				user.setId(rs.getInt("id"));
				user.setUsername(stripSlashes(rs.getString("username")));
				user.setName(stripSlashes(rs.getString("name")));
				user.setSurname(stripSlashes(rs.getString("surname")));
				user.setEmail(stripSlashes(rs.getString("email")));
				user.setPassword(rs.getString("password"));
				user.setExp(rs.getInt("exp"));
				user.setAvatar(stripSlashes(rs.getString("avatar")));
			}

		} catch (SQLException e) {
			throw new DaoException("Error dao user get user ", e);
		}

		return user;
	}

	/**
	 * chiudo la connessione e le query precompilate
	 * @throws gamingplatform.dao.exception.DaoException
	 */
	@Override
	public void destroy() throws DaoException{
		
		//chiudo le quary precompilate
		try {
			this.insertUser.close();
			this.selectUserById.close();
			this.deleteUserById.close();
			this.selectUsers.close();
			this.updateUserById.close();
			this.getUserByUsernamePassword.close();
			
		} catch (SQLException e) {
			throw new DaoException("Error destroy dao user", e);
		}
		
		//chiudo la connessione
		super.destroy();
	}


	

	
	
}