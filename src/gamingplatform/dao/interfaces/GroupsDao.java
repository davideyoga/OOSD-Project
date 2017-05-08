package gamingplatform.dao.interfaces;

import gamingplatform.dao.data.DaoData;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.Group;
import gamingplatform.model.User;
import gamingplatform.model.Service;

import java.util.List;

public interface GroupsDao extends DaoData{

	public Group getGroup();
	
	public Group getGroup( int idGroup) throws DaoException;

	public List<Group> getGroups()throws DaoException;

	public void insertGroup( Group group ) throws DaoException;

	public void deleteGroupById( int idGroup) throws DaoException;

	public void updateGroup( Group group ) throws DaoException;

	public List<Group> getGroupsByUserId(int idUser)throws DaoException;

	public List<User>  getUsersByGroupId(int idGroup) throws DaoException;

	public List<Group> getGroupsByServiceId(int idUser)throws DaoException;

	public List<Service> getServicesByGroupId(int idGroup)throws DaoException;

	public void removeUserFromGroup(int idUser, int idGroup)throws DaoException;

	public void addUserToGroup(int idUser, int idGroup)throws DaoException;

	public void removeServiceFromGroup(int idGroup, int idService)throws DaoException;

	public void addServiceToGroup(int idGroup, int idService)throws DaoException;


	public void destroy() throws DaoException;
	
}