package gamingplatform.dao.interfaces;

import gamingplatform.dao.data.DaoData;
import gamingplatform.model.Level;

import java.util.Calendar;
import java.util.Map;

public interface UserLevelDao extends DaoData {

    public Map<Calendar,Level> getLevelsByUserId( int userId);

}