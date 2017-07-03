package gamingplatform.dao.implementation;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import gamingplatform.dao.data.DaoDataMySQLImpl;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.interfaces.UserDao;
import gamingplatform.model.User;
import gamingplatform.model.Level;

import static gamingplatform.controller.utils.SecurityLayer.addSlashes;
import static gamingplatform.controller.utils.SecurityLayer.stripSlashes;

/**
 * Classe per la gestione dell'untente nel database
 */
public class UserDaoImpl extends DaoDataMySQLImpl implements UserDao{
	
	private PreparedStatement insertUser,
							  selectUserById,
	                          selectUsers,
							  getUserByUsernamePassword,
	 						  deleteUserById,
							  updateUserById,
	                          getLevelByUserId; // Ottenere il livello attuale di un utente

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

			//query insert
			this.insertUser = connection.prepareStatement("INSERT INTO user" +
					"										    VALUES(NULL,?,?,?,?,?,?,?)");



			//query select all users
			this.selectUsers = connection.prepareStatement("SELECT * " +
					"											 FROM user");



			//query select one user from id
			this.selectUserById = connection.prepareStatement("SELECT * " +
					"												FROM user " +
					"												WHERE id=?");



			//query delete a user
			this.deleteUserById = connection.prepareStatement("DELETE FROM user " +
					"												WHERE id=?");



			//query update user
			this.updateUserById = connection.prepareStatement("UPDATE user " +
					"												SET username=?," +
					"													name=?," +
					"													surname=?," +
					"													email=?," +
					"												 	password=?," +
					"													exp=?, " +
					"													avatar=?" +
					"												WHERE id=?");



			//query select user from username e password
			this.getUserByUsernamePassword = connection.prepareStatement("SELECT * " +
					"														   FROM user " +
					"														   WHERE username=? AND password =?");



			//Query che mi restituisce l'attuale livello in cui si trova l'user
			this.getLevelByUserId = connection.prepareStatement("SELECT level.id, level.name, level.icon, level.trophy, level.exp " +
					"                                                 FROM level " +
					"                                                 LEFT JOIN userlevel ON userlevel.id_level=level.id " +
					"                                                 WHERE userlevel.id_user = ? ORDER BY date DESC LIMIT 1");

		} catch (Exception e) {
			throw new DaoException("Error initializing user dao", e);
		}
	}

	/**
	 * Inserisce nle database l'user passato
	 * @param user
	 * @throws DaoException
	 */
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


		} catch (Exception e) {
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
				
			} catch (Exception e) {
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
				u.setId(rs.getInt("id"));
				u.setUsername(stripSlashes(rs.getString("username")));
				u.setName(stripSlashes(rs.getString("name")));
				u.setSurname(stripSlashes(rs.getString("surname")));
				u.setEmail(stripSlashes(rs.getString("email")));
				u.setPassword(rs.getString("password"));
				u.setExp(rs.getInt("exp"));
				u.setAvatar(stripSlashes(rs.getString("avatar")));
				lista.add(u);
			}
		}catch (Exception e){
			throw new DaoException("Error query getUsers", e);

		}

		return lista;
	}

	/**
	 * Esegue l'update del' user con l'id dell'user passato
	 * @param user
	 * @throws DaoException
	 */
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
			this.updateUserById.setInt(8, user.getId());

			this.updateUserById.executeUpdate();

		}catch (Exception e) {

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
				user.setId(idUser);
				user.setUsername(stripSlashes(rs.getString("username")));
				user.setName(stripSlashes(rs.getString("name")));
				user.setSurname(stripSlashes(rs.getString("surname")));
				user.setEmail(stripSlashes(rs.getString("email")));
				user.setPassword(rs.getString("password"));
				user.setExp(rs.getInt("exp"));
				user.setAvatar(stripSlashes(rs.getString("avatar")));
			}

		} catch (Exception e) {
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

		} catch (Exception e) {
			throw new DaoException("Error dao user get user ", e);
		}

		return user;
	}

	/**
	 * Metodo che restituisce l'ultimo livello aggiornato di un dato utente
	 * @param idUser
	 * @return
	 * @throws DaoException
	 */
	public Level getLevelByUserId(int idUser) throws DaoException {

		Level level=new Level(this);

		try{

			this.getLevelByUserId.setInt(1, idUser);
			ResultSet rs = this.getLevelByUserId.executeQuery();

			while(rs.next()) {
				level.setId(rs.getInt("id"));
				level.setName(rs.getInt("name"));
				level.setTrophy(stripSlashes(rs.getString("trophy")));
				level.setIcon(stripSlashes(rs.getString("icon")));
				level.setExp(rs.getInt("exp"));
			}



		} catch (Exception e) {
			throw new DaoException("Error query getLevelByUserId", e);
		}

		return level;
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
			this.getLevelByUserId.close();
			
		} catch (Exception e) {
			throw new DaoException("Error destroy dao user", e);
		}
		
		//chiudo la connessione
		super.destroy();
	}


	

	
	
}