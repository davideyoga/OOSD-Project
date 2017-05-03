package gamingplatform.dao.interfaces;

import gamingplatform.dao.data.DaoData;
import gamingplatform.dao.exception.DaoException;
import gamingplatform.model.Group;

import java.util.ArrayList;
import java.util.List;

public interface GroupsDao extends DaoData{
	
	public Group getGroup( int keyGroup) throws DaoException;
	
	public void insertGroup(Group group) throws DaoException;
	
	public List<Group> getGroupsByUserId(int keyUser )throws DaoException;
	
	public void deleteGroupById() throws DaoException;
	
	public void destroy() throws DaoException;
	
}