package model.dao.interfaces;

import model.dao.data.DaoData;
import model.entity.implement.Group;
import model.entity.implement.User;

public interface UserGroupsDao extends DaoData{
	
	void addUserToGroup( Group group, User user );
	
	void removeUserToGroup( Group group, User user );
}