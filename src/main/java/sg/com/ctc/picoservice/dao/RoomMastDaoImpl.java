package sg.com.ctc.picoservice.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import sg.com.ctc.picoservice.model.RoomMast;

@Repository("roomMastDao")
public class RoomMastDaoImpl extends AbstractDao<Integer, RoomMast> implements RoomMastDao{

	public RoomMast findById(String id) {
    	Criteria criteria = createEntityCriteria();
    	criteria.add(Restrictions.eq("id", id));
        return (RoomMast) criteria.uniqueResult();
    }
}
