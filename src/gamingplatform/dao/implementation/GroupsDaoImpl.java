package gamingplatform.dao.implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import gamingplatform.dao.data.DaoDataMySQLImpl;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.dao.interfaces.GroupsDao;
import gamingplatform.model.Group;
import gamingplatform.model.Service;
import gamingplatform.model.User;

import static gamingplatform.controller.utils.SecurityLayer.addSlashes;
import static gamingplatform.controller.utils.SecurityLayer.stripSlashes;


public class GroupsDaoImpl extends DaoDataMySQLImpl implements GroupsDao {
	
	private PreparedStatement selectGroupById,
							  selectGroups,
							  insertGroup,
							  deleteGroupById,
							  updateGroup,
							  selectGroupsByUserId,
							  selectUsersByGroupId,
							  selectGroupsByServiceId,
							  selectServicesByGroupId,
							  removeUserFromGroup,
							  addUserToGroup,
							  removeServiceFromGroup,
							  addServiceToGroup,
						      selectUsersNotInThisGroup,
							  selectServicesNotInThisGroup;


	/**
	 * Costruttore per inizializzare la connessione
	 * @param datasource e' la risorsa di connessione messa a disposizione del connection pooling
	 */
	public GroupsDaoImpl(DataSource datasource) {

		super(datasource);
	}

	/**
	 * Metodo in cui viene inizializzata la connessione e vengono preparate le query
	 * di insert, select,delete e update
	 * @throws DaoException eccezione che viene lanciata in caso di fallimento di
	 * inizializzazione query
	 */
	@Override
	public void init() throws DaoException{
		try {
			super.init(); // connection initialization

			//Query che retituisce un gruppo dato l'id
			this.selectGroupById = connection.prepareStatement("SELECT * " +
					"												 FROM groups " +
					"												 WHERE id=?");


			//Query che retituisce la lista di tuuti i gruppi
			this.selectGroups = connection.prepareStatement("SELECT * " +
					"											  FROM groups ");

			//Query che inserisce un nuovo gruppo nel DB
			this.insertGroup = connection.prepareStatement("INSERT INTO groups"	 +
					"											 VALUES (NULL,?,?)");
			//Query di cancellazione di un gruppo
			this.deleteGroupById= connection.prepareStatement("DELETE FROM groups WHERE id=?");


			//Query di  modifica di un gruppo
			this.updateGroup=connection.prepareStatement("UPDATE groups" +
					"										   SET name=?," +
					"										       description=? " +
					"										   WHERE id=?");

			//Query che restituisce i gruppi a cui appartiene un dato user
			this.selectGroupsByUserId = connection.prepareStatement("SELECT groups.id, " +
																		 	    "groups.name, " +
																		        "groups.description" +
																		 " FROM groups " +
																		 " LEFT JOIN usergroups ON groups.id = usergroups.id_groups" +
																		 " WHERE usergroups.id_user=?");

			//Query che restituisce gli user appartenenti a un dato gruppo
			this.selectUsersByGroupId=connection.prepareStatement("SELECT user.id," +
					"														   user.username," +
					"														   user.name," +
					"														   user.surname," +
					"														   user.email," +
					"														   user.password," +
					"														   user.exp," +
					"														   user.avatar" +
					"													FROM user " +
					"													LEFT JOIN usergroups ON user.id = usergroups.id_user" +
					"													WHERE usergroups.id_groups=?");
			//Query che restituisce i gruppi che hanno autorizzazioni su un dato servizio
			this.selectGroupsByServiceId=connection.prepareStatement("SELECT groups.id," +
					"														      groups.name," +
					"														      groups.description" +
					"													  FROM groups " +
					"													  LEFT JOIN groupsservice ON groups.id = groupsservice.id_groups" +
					"													  WHERE groupsservice.id_service=?");

			//Query che restituisce i servizi a cui può accedere un dato gruppo
			this.selectServicesByGroupId=connection.prepareStatement("SELECT service.id," +
					"														      service.name," +
					"														      service.description" +
					"													  FROM service " +
					"													  LEFT JOIN groupsservice ON service.id = groupsservice.id_service" +
					"													  WHERE groupsservice.id_groups=?");

			//Query che consente di eliminare un utente da un gruppo
			this.removeUserFromGroup=connection.prepareStatement("DELETE FROM usergroups" +
					"												   WHERE id_groups=? AND id_user=?");

			//Query che consente di aggiungere un utente a un gruppo
			this.addUserToGroup=connection.prepareStatement("INSERT INTO usergroups" +
					"												 VALUES (?,?)");


			//Query che consente di rimuovere un servizio da un gruppo
			this.removeServiceFromGroup=connection.prepareStatement("DELETE FROM groupsservice" +
					"													  WHERE id_groups=? AND id_service=?");

			//Query che consente di aggiungere un servizio al gruppo
			this.addServiceToGroup=connection.prepareStatement("INSERT INTO groupsservice" +
					"												 VALUES (?,?)");

			//Query che mi restituisce gli user non appartenenti al gruppo dato
			this.selectUsersNotInThisGroup=connection.prepareStatement("SELECT user.id AS id, user.username" +
					"														FROM user " +
					"														WHERE id NOT IN (" +
					"																		SELECT user.id" +
					"																		FROM user " +
					"																		LEFT JOIN usergroups ON usergroups.id_user=user.id" +
					"																		WHERE usergroups.id_groups=?)");


			//Query che mi restituisce i services non appartenenti al gruppo dato
			this.selectUsersNotInThisGroup=connection.prepareStatement("SELECT service.id AS id, service.name" +
					"														FROM service " +
					"														WHERE id NOT IN (" +
					"																		SELECT service.id" +
					"																		FROM service " +
					"																		LEFT JOIN groupsservice ON groupsservice.id_service=service.id" +
					"																		WHERE groupsservice.id_groups=?)");

		} catch (Exception e) {
			throw new DaoException("Error initializing group dao", e);
		}
	}

	/**
	 * Metodo che restituisce un gruppo vuoto
	 */
	@Override
	public Group getGroup() {
		return new Group(this);
	}

	/**
	 * Metodo che restituisce un gruppo dato il suo id
	 * @param idGroup è l'identificatore del gruppo
	 * @return un oggetto di tipo Group
	 * @throws DaoException lancia eccezione in caso di errore
	 */
	public Group getGroup( int idGroup) throws DaoException {

		Group g=new Group(this);
		try {
			this.selectGroupById.setInt(1, idGroup);
			ResultSet rs=this.selectGroupById.executeQuery();
			while(rs.next()){
				g.setId(rs.getInt("id"));
				g.setName(stripSlashes(rs.getString("name")));
				g.setDescription(stripSlashes(rs.getString("description")));
			}

		} catch (Exception e) {
			throw new DaoException("Error query getGroup", e);

		}
		
		return g;
	}




	public List<Group> getGroups() throws DaoException{
		List<Group> lista= new ArrayList<>();

		try{
			ResultSet rs=this.selectGroups.executeQuery();

			while (rs.next()){
				Group g=new Group(this);

				g.setId(rs.getInt("id"));
				g.setName(stripSlashes(rs.getString("name")));
				g.setDescription(stripSlashes(rs.getString("description")));

				lista.add(g);
			}
		} catch (Exception e){
			throw new DaoException("Error query getGroups", e);
		}
		return lista;
	}




	/**
	 * Metodo che permette l'inserimento di un gruppo nel DB
	 * @param group e' il gruppo che viene inserito nel database
	 * @throws DaoException lancia eccezione in caso di errore
	 */
	@Override
	public void insertGroup(Group group) throws DaoException {
		try{
			this.insertGroup.setString(1, addSlashes(group.getName()));
			this.insertGroup.setString(2,addSlashes(group.getDescription()));
			this.insertGroup.executeUpdate();
		}catch (Exception e){
			throw new DaoException("Error query insertGroup", e);
		}
	}


	/**
	 * Metodo che consente l'eliminazione di un gruppo dal DB
	 * @param idGroup identificatore del gruppo da eliminare
	 * @throws DaoException lancia eccezione in caso di errore
	 */
	public void deleteGroupById(int idGroup) throws DaoException{
		try{
			this.deleteGroupById.setInt(1,idGroup);
			this.deleteGroupById.executeUpdate();
		}catch (Exception e){
			throw new DaoException("Error query deleteGroupById", e);
		}
	}

	/**
	 * Metodo che consente la modifica di un gruppo esistente nel DB
	 * @param group e' il gruppo di cui viene fatto l'update
	 * @throws DaoException lancia eccezione in caso di errore
	 */
	@Override
	public void updateGroup(Group group) throws DaoException {
		try{
			this.updateGroup.setString(1, addSlashes(group.getName()));
			this.updateGroup.setString(2, addSlashes(group.getDescription()));
			this.updateGroup.setInt(3, group.getId());

			this.updateGroup.executeUpdate();

		}catch (Exception e){
			throw new DaoException("Error query updateGroup", e);
		}
	}


	/**
	 * Metodo che ritorna la lista di gruppi a cui appartiene un utente
	 * @param idUser id dell'user su cui viene effettuato il controllo
	 * @return lista di gruppi
	 * @throws DaoException lancia eccezione in caso di errore
	 */
	public List<Group> getGroupsByUserId(int idUser) throws DaoException {
		
		List<Group> list = new ArrayList<Group>();
		
		try{
			
			this.selectGroupsByUserId.setInt( 1, idUser);
			
			ResultSet rs = this.selectGroupsByUserId.executeQuery();
			
			//rs ritorna un insieme di tuple rappresentanti i gruppi acuoi appartiene l'utente 
			//scorro rs ed aggiungo alla lista il gruppo
			while( rs.next() ){
				
				Group group = new Group(this);
				 
				group.setId(rs.getInt("id"));
				group.setName(stripSlashes(rs.getString("name")));
				group.setDescription(stripSlashes(rs.getString("description")));
				
				list.add(group);
			}
			
		}catch (Exception e) {
			throw new DaoException("Error query getGroupByUserId", e);
		}
		
		return list;
	}




	/**
	 * Metodo che ritorna la lista di utenti appartenenti a un gruppo dato
	 * @param idGroup id del gruppo su cui viene effettuato il controllo
	 * @return lista di User
	 * @throws DaoException lancia eccezione in caso di errore
	 */
	public List<User> getUsersByGroupId(int idGroup) throws DaoException {

		List<User> list = new ArrayList<>();

		try{

			this.selectUsersByGroupId.setInt( 1, idGroup);

			ResultSet rs = this.selectUsersByGroupId.executeQuery();

			//rs ritorna un insieme di tuple rappresentanti i gruppi acuoi appartiene l'utente
			//scorro rs ed aggiungo alla lista il gruppo
			while( rs.next() ){

				User u=new User(this);
				u.setId(rs.getInt("id"));
				u.setUsername(stripSlashes(rs.getString("username")));
				u.setName(stripSlashes(rs.getString("name")));
				u.setSurname(stripSlashes(rs.getString("surname")));
				u.setEmail(stripSlashes(rs.getString("email")));
				u.setPassword(rs.getString("password"));
				u.setExp(rs.getInt("exp"));
				u.setAvatar(stripSlashes(rs.getString("avatar")));

				list.add(u);
			}

		}catch (Exception e) {
			throw new DaoException("Error query getUsersByGroupId", e);
		}

		return list;
	}



	/**
	 * Metodo che ritorna la lista di gruppi a cui appartiene un dato servizio
	 * @param idService id del servizio su cui viene effettuato il controllo
	 * @return lista di gruppi
	 * @throws DaoException lancia eccezione in caso di errore
	 */
	public List<Group> getGroupsByServiceId(int idService) throws DaoException {

		List<Group> list = new ArrayList<Group>();

		try{

			this.selectGroupsByUserId.setInt( 1, idService);

			ResultSet rs = this.selectGroupsByUserId.executeQuery();

			//rs ritorna un insieme di tuple rappresentanti i gruppi acuoi appartiene l'utente
			//scorro rs ed aggiungo alla lista il gruppo
			while( rs.next() ){

				Group group = new Group(this);

				group.setId(rs.getInt("id"));
				group.setName(stripSlashes(rs.getString("name")));
				group.setDescription(stripSlashes(rs.getString("description")));

				list.add(group);
			}

		}catch (Exception e) {
			throw new DaoException("Error query getGroupByServiceId", e);
		}

		return list;
	}



	/**
	 * Metodo che ritorna la lista di servizi a cui può accedervi un dato gruppo
	 * @param idGroup id del gruppo su cui viene effettuato il controllo
	 * @return lista di servizi
	 * @throws DaoException lancia eccezione in caso di errore
	 */
	public List<Service> getServicesByGroupId(int idGroup) throws DaoException {

		List<Service> list = new ArrayList<>();

		try{

			this.selectServicesByGroupId.setInt( 1, idGroup);

			ResultSet rs = this.selectServicesByGroupId.executeQuery();

			//rs ritorna un insieme di tuple rappresentanti i gruppi acuoi appartiene l'utente
			//scorro rs ed aggiungo alla lista il gruppo
			while( rs.next() ){

				Service s=new Service(this);

				s.setId(rs.getInt("id"));
				s.setName(stripSlashes(rs.getString("name")));
				s.setDescription(stripSlashes(rs.getString("description")));

				list.add(s);
			}

		}catch (Exception e) {
			throw new DaoException("Error query getServiceByGroupId", e);
		}

		return list;
	}


	/**
	 * Metodo che permette di rimuovere un user da un gruppo
	 * @param idUser identificativo dell'user da rimuvere
	 * @param idGroup identificativo del gruppo in cui va rimosso l'user
	 * @throws DaoException lancia eccezione in caso di errore
	 */
	public void removeUserFromGroup(int idUser,int idGroup) throws DaoException{
		try{
			this.removeUserFromGroup.setInt(1,idUser);
			this.removeUserFromGroup.setInt(2,idGroup);
			this.removeUserFromGroup.executeUpdate();
		}catch (Exception e){
			throw new DaoException("Error query removeUserFromGroup", e);
		}
	}


	/**
	 * Metodo che aggiunge un user a un gruppo
	 * @param idUser identificativo dell'user da aggiungere al gruppo
	 * @param idGroup identificativo del gruppo in cui aggiungere l'user
	 * @throws DaoException lancia eccezione in caso di errore
	 */
	public void addUserToGroup(int idUser,int idGroup) throws DaoException{
		try{
			this.addUserToGroup.setInt(1,idUser);
			this.addUserToGroup.setInt(2,idGroup);
			this.addUserToGroup.executeUpdate();
		}catch (Exception e){
			throw new DaoException("Error query addUserToGroup", e);
		}
	}


	/**
	 *  Metodo che rimuove un servizio da un gruppo
	 * @param idGroup identificativo del gruppo in cui va rimosso l'user
	 * @param idService identificativo del servizio da rimuvere
	 * @throws DaoException lancia eccezione in caso di errore
	 */
	public void removeServiceFromGroup(int idGroup,int idService) throws DaoException{
		try{
			this.removeServiceFromGroup.setInt(1,idGroup);
			this.removeServiceFromGroup.setInt(2,idService);
			this.removeServiceFromGroup.executeUpdate();
		}catch (Exception e){
			throw new DaoException("Error query removeServiceFromGroup", e);
		}
	}


	/**
	 *  Metodo che aggiunge un servizio a un gruppo
	 * @param idGroup identificativo delgruppo a cui aggiungere il servizio
	 * @param idService identificativo del servizio da aggiungere al gruppo
	 * @throws DaoException lancia eccezione in caso di errore
	 */
	public void addServiceToGroup(int idGroup,int idService) throws DaoException{
		try{
			this.addServiceToGroup.setInt(1,idGroup);
			this.addServiceToGroup.setInt(2,idService);
			this.addServiceToGroup.executeUpdate();
		}catch (Exception e){
			throw new DaoException("Error query addServiceToGroup", e);
		}
	}


	public List<User> getUsersNotInThisGroup(int idGroup) throws DaoException {
		List<User> list = new ArrayList<>();

		try {

			this.selectUsersNotInThisGroup.setInt(1, idGroup);

			ResultSet rs = this.selectUsersNotInThisGroup.executeQuery();

			//scorro rs ed aggiungo alla lista
			while (rs.next()) {

				User u = new User(this);
				u.setId(rs.getInt("id"));
				u.setUsername(stripSlashes(rs.getString("username")));
				u.setName(stripSlashes(rs.getString("name")));
				u.setSurname(stripSlashes(rs.getString("surname")));
				u.setEmail(stripSlashes(rs.getString("email")));
				u.setPassword(rs.getString("password"));
				u.setExp(rs.getInt("exp"));
				u.setAvatar(stripSlashes(rs.getString("avatar")));

				list.add(u);
			}

		} catch (Exception e) {
			throw new DaoException("Error query  getUsersNotInThisGroup", e);
		}

		return list;
	}



	public List<Service> getServicesNotInThisGroup(int idGroup) throws DaoException {
		List<Service> list = new ArrayList<>();

		try {

			this.selectServicesNotInThisGroup.setInt(1, idGroup);

			ResultSet rs = this.selectServicesNotInThisGroup.executeQuery();

			//scorro rs ed aggiungo alla lista
			while (rs.next()) {

				Service s=new Service(this);

				s.setId(rs.getInt("id"));
				s.setName(stripSlashes(rs.getString("name")));
				s.setDescription(stripSlashes(rs.getString("description")));

				list.add(s);
			}

		} catch (Exception e) {
			throw new DaoException("Error query  getServicesNotInThisGroup", e);
		}

		return list;
	}



	/**
	 * Metodo che chiude la connessione e le query preparate
	 * @throws DaoException lancia eccezione in caso di errore
	 */
	public void destroy() throws DaoException{
		//chiudo le quary precompilate
		try {
			this.selectGroupById.close();
			this.insertGroup.close();
			this.deleteGroupById.close();
			this.updateGroup.close();
			this.selectGroups.close();
			this.selectGroupsByUserId.close();
			this.selectUsersByGroupId.close();
			this.selectGroupsByServiceId.close();
			this.selectServicesByGroupId.close();
			this.removeUserFromGroup.close();
			this.addUserToGroup.close();
			this.removeServiceFromGroup.close();
			this.addServiceToGroup.close();
			this.selectUsersNotInThisGroup.close();
			this.selectServicesNotInThisGroup.close();



		} catch (Exception e) {
			throw new DaoException("Error destroy GroupDao", e);
		}

		//chiudo la connessione
		super.destroy();
	}
	
}