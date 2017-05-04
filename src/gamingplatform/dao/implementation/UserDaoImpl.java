package gamingplatform.dao.implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import gamingplatform.dao.data.DaoDataMySQLImpl;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.interfaces.UserDao;
import gamingplatform.model.Group;
import gamingplatform.model.User;

/**
 * Interazione con il database con argomento User 
 * @author Davide Micarelli
 * 
 */

public class UserDaoImpl extends DaoDataMySQLImpl implements UserDao{
	
	private PreparedStatement	insertUser,
								selectUserById,
								getUserByUsernamePassword,
								deleteUserById,
								updateUserById;

	
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
			
			this.insertUser = connection.prepareStatement("INSERT INTO user VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			this.selectUserById = connection.prepareStatement("SELECT * FROM user WHERE id=?");
			this.deleteUserById = connection.prepareStatement("DELETE FROM user WHERE id=?");
			this.updateUserById = connection.prepareStatement("UPDATE user SET username=?,name=?,surname=?,email=?, password=?, exp=?, avatar=?");
			this.getUserByUsernamePassword = connection.prepareStatement("SELECT * FROM user WHERE username=? AND password =?");

		} catch (SQLException e) {
			throw new DaoException("Error initializing user dao", e);
		}
	}
	
	/**
	 * per per inserire un utente nel database
	 * @throws gamingplatform.dao.exception.DaoException
	 */
	@Override
	public void insertUser(User user) throws DaoException {
		
		if( user.getId() > 0 ){
			//se l'utente passato gia' possiede un id
			//vuol dire che gia' e' stato inserito nel database
			//quindi effetto un'update
			updateUser( user );
		
		} else {
			//se l'istanza passata non ha una chiave > di 0 lo inserisco nel database
			try {
				
				this.insertUser.setString(1, user.getUsername());
				this.insertUser.setString(2, user.getName());
				this.insertUser.setString(3, user.getSurname());
				this.insertUser.setString(4, user.getEmail());
				this.insertUser.setString(5, user.getPassword());
				this.insertUser.setInt(6, user.getExp() );   
				this.insertUser.setString( 7, user.getAvatar() );
				
				this.insertUser.executeQuery();
				
				
			} catch (SQLException e) {
				throw new DaoException("Error sql user insert", e);
			}
			
			
		}
		
	}
	
	/**
	 * per eliminare un utente dal database
	 * @throws gamingplatform.dao.exception.DaoException
	 */
	@Override
	public void deleteUserByKey(int keyUser) throws DaoException {
		if( keyUser > 0 ){
			try {		
				this.deleteUserById.setInt(1, keyUser);
				this.deleteUserById.executeQuery();
				
			} catch (SQLException e) {
				throw new DaoException("Error dao delete user", e);
			}
		}
	}
	
	/**
	 * per modificare un utente nel database
	 * @throws gamingplatform.dao.exception.DaoException
	 */
	@Override
	public void updateUser(User user) throws DaoException {
		
		try{
			
			this.updateUserById.setString(1, user.getUsername());
			this.updateUserById.setString(2, user.getName());
			this.updateUserById.setString(3, user.getSurname());
			this.updateUserById.setString(4, user.getEmail());
			this.updateUserById.setString(5, user.getPassword());
			this.updateUserById.setInt(6, user.getExp());
			this.updateUserById.setString(7, user.getAvatar());
			
			this.updateUserById.executeUpdate();
			
		}catch (SQLException e) {
			
			throw new DaoException("Error dao update user", e);
		}
		
	}
	
	/**
	 * per estrarre un utente dal database
	 * @throws gamingplatform.dao.exception.DaoException
	 */
	@Override
	public User getUser(int keyUser) throws DaoException {
		
		User user = new User(this);
		
		try {
			this.selectUserById.setInt(1, keyUser); //inserisco nella query la chiave dell'utente

			ResultSet rs = this.selectUserById.executeQuery(); //eseguo la query

			while(rs.next()){
				user.setUsername( rs.getString("username") );
				user.setName(rs.getString("name"));
				user.setSurname(rs.getString("surname"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setExp(rs.getInt("exp"));
				user.setAvatar(rs.getString("avatar"));
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
			this.getUserByUsernamePassword.setString(1, username);
			this.getUserByUsernamePassword.setString(2, password);

			ResultSet rs = this.getUserByUsernamePassword.executeQuery(); //eseguo la query

			while(rs.next()){
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username") );
				user.setName(rs.getString("name"));
				user.setSurname(rs.getString("surname"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setExp(rs.getInt("exp"));
				user.setAvatar(rs.getString("avatar"));
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
			this.updateUserById.close();
			
		} catch (SQLException e) {
			throw new DaoException("Error destroy dao user", e);
		}
		
		//chiudo la connessione
		super.destroy();
	}


	

	
	
}