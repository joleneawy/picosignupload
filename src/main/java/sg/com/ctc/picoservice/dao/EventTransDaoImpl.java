package sg.com.ctc.picoservice.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import sg.com.ctc.picoservice.model.EventTrans;

@Repository("eventTransDao")
public class EventTransDaoImpl extends AbstractDao<Integer, EventTrans> implements EventTransDao{

	public EventTrans findById(int id) {
    	Criteria criteria = createEntityCriteria();
    	criteria.add(Restrictions.eq("id", id));
        return (EventTrans) criteria.uniqueResult();
    }
}
