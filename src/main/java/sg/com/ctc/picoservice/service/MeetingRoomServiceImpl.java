package sg.com.ctc.picoservice.service;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.com.ctc.picoservice.dao.MeetingRoomDao;
import sg.com.ctc.picoservice.model.BookRoomTrans;

import java.util.Date;
import java.util.List;

/**
 * Created by jolene.aw on 1/12/2018.
 */

@Service("meetingRoomService")
@Transactional
public class MeetingRoomServiceImpl implements MeetingRoomService{

    @Autowired
    private MeetingRoomDao dao;

    public List<BookRoomTrans> findById(String id) {
        return dao.findById(id);
    }

    public void saveMeetingRoomBooking(BookRoomTrans meetingRoom) {
    	meetingRoom.setStatus("A");
    	meetingRoom.setId("SGFE");
    	meetingRoom.setEventDateCreated(new LocalDateTime());
        dao.saveMeetingRoomBooking(meetingRoom);
    }

    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends.
     */
    public void updateMeetingRoomBooking(BookRoomTrans meetingRoom) {
    	List<BookRoomTrans> entity = dao.findById(meetingRoom.getId());
        if(entity!=null){
        	for(BookRoomTrans br : entity){
        		br.setEventDateCreated(new LocalDateTime());
        		br.setEventEndDate(meetingRoom.getEventEndDate());
        		br.setEventEndTime(meetingRoom.getEventEndTime());
        		br.setEventStartDate(meetingRoom.getEventStartDate());
        		br.setEventStartTime(meetingRoom.getEventStartTime());
        		br.setStatus("A");
        		br.setUser(meetingRoom.getUser());
        	}
        }
    }

    public void deleteMeetingRoomBookingByEventId(int eventId) {
        dao.deleteMeetingRoomBookingByEventId(eventId);
    }

    public List<BookRoomTrans> findAllMeetingRoomsBooking() {
        return dao.findAllMeetingRoomsBooking();
    }

    public BookRoomTrans findMeetingRoomBookingByEventId(int eventId) {
        return dao.findMeetingRoomBookingByEventId(eventId);
    }
    
    public List<BookRoomTrans> findMeetingRoomBookingByDate(Date date, String id) {
        return dao.findMeetingRoomBookingByDate(date, id);
    }

    public boolean isEventIdUnique(String id, int eventId) {
    	BookRoomTrans meetingRoom = findMeetingRoomBookingByEventId(eventId);
        return ( meetingRoom == null || ((id != null) && (meetingRoom.getId() == id)));
    }
}
