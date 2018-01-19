package sg.com.ctc.picoservice.service;

import sg.com.ctc.picoservice.model.BookRoomTrans;

import java.util.Date;
import java.util.List;

/**
 * Created by jolene.aw on 1/12/2018.
 */
public interface MeetingRoomService {

	List<BookRoomTrans> findById(String id);

    void saveMeetingRoomBooking(BookRoomTrans meetingRoom);

    void updateMeetingRoomBooking(BookRoomTrans meetingRoom);

    void deleteMeetingRoomBookingByEventId(int eventId);

    List<BookRoomTrans> findAllMeetingRoomsBooking();

    BookRoomTrans findMeetingRoomBookingByEventId(int eventId);

    boolean isEventIdUnique(String id, int eventId);
    
    List<BookRoomTrans> findMeetingRoomBookingByDate(Date date, String id);
}
