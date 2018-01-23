package sg.com.ctc.picoservice.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Repository;

import sg.com.ctc.picoservice.model.BookRoomTrans;

import java.util.Date;
import java.util.List;

/**
 * Created by jolene.aw on 1/12/2018.
 */
@Repository("meetingRoomDao")
public class MeetingRoomDaoImpl extends AbstractDao<Integer, BookRoomTrans> implements MeetingRoomDao{

    @SuppressWarnings("unchecked")
	public List<BookRoomTrans> findById(String id) {
    	Criteria criteria = createEntityCriteria();
    	criteria.add(Restrictions.eq("id", id));
        return (List<BookRoomTrans>) criteria.list();
    }

    public void saveMeetingRoomBooking(BookRoomTrans meetingRoom) {
        persist(meetingRoom);
    }

    public void deleteMeetingRoomBookingByEventId(int eventId) {
        Query query = getSession().createSQLQuery("delete from dbo.rbm_bookroom_trans where rbm_event_id = :eventId");
        query.setInteger("eventId", eventId);
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public List<BookRoomTrans> findAllMeetingRoomsBooking() {
        Criteria criteria = createEntityCriteria();
        return (List<BookRoomTrans>) criteria.list();
    }

    public BookRoomTrans findMeetingRoomBookingByEventId(int eventId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("eventId", eventId));
        return (BookRoomTrans) criteria.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    public List<BookRoomTrans> findMeetingRoomBookingByDate(Date date, String id){
    	Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.ge("eventEndTime", LocalDateTime.fromDateFields(date)));
        criteria.add(Restrictions.eq("id", id));
        criteria.addOrder(Order.asc("eventStartTime"));
        return (List<BookRoomTrans>) criteria.list();
    }
}
