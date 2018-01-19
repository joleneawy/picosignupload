package sg.com.ctc.picoservice.dao;

import sg.com.ctc.picoservice.model.BookRoomTrans;

import java.util.Date;
import java.util.List;

/**
 * Created by jolene.aw on 1/12/2018.
 */
public interface MeetingRoomDao {

	List<BookRoomTrans> findById(String id);

    void saveMeetingRoomBooking(BookRoomTrans meetingRoom);

    void deleteMeetingRoomBookingByEventId(int eventId);

    List<BookRoomTrans> findAllMeetingRoomsBooking();

    BookRoomTrans findMeetingRoomBookingByEventId(int eventId);
    
    List<BookRoomTrans> findMeetingRoomBookingByDate(Date date, String id);
}
