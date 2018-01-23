package sg.com.ctc.picoservice.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sg.com.ctc.picoservice.model.BookRoomTrans;
import sg.com.ctc.picoservice.service.MeetingRoomService;

@Controller
@RequestMapping("/")
public class AppController {

	@Autowired
	MeetingRoomService service;
	
	@Autowired
	MessageSource messageSource;


	/*
	 * This method will list all existing employees.
	 */
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String listBooking(ModelMap model) {

		List<BookRoomTrans> meetingRooms = service.findAllMeetingRoomsBooking();
		model.addAttribute("meetingRooms", meetingRooms);
		return "allBookings";
	}

	/*
	 * This method will provide the medium to add a new employee.
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public String newMeetingRoomBooking(ModelMap model) {
		BookRoomTrans meetingRoom = new BookRoomTrans();
		model.addAttribute("meetingRoom", meetingRoom);
		model.addAttribute("edit", false);
		return "registration";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * saving employee in database. It also validates the user input
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String saveMeetingRoomBooking(@Valid BookRoomTrans meetingRoom, BindingResult result,
			ModelMap model) {

		if (result.hasErrors()) {
			return "registration";
		}

		if(!service.isEventIdUnique(meetingRoom.getId(), meetingRoom.getEventId())){
			FieldError codeError =new FieldError("meetingRoom","eventId",messageSource.getMessage("non.unique.code", new Integer[]{meetingRoom.getEventId()}, Locale.getDefault()));
		    result.addError(codeError);
		    model.addAttribute("meetingRoom", meetingRoom);
		    model.addAttribute("edit", false);
			return "registration";
		}
		
		service.saveMeetingRoomBooking(meetingRoom);

		model.addAttribute("success", "MeetingRoom " + meetingRoom.getEventId() + " booked successfully");
		return "success";
	}


	/*
	 * This method will provide the medium to update an existing meetingRoom.
	 */
	@RequestMapping(value = { "/edit-{eventId}-meetingRoom" }, method = RequestMethod.GET)
	public String editMeetingRoomBooking(@PathVariable int eventId, ModelMap model) {
		BookRoomTrans meetingRoom = service.findMeetingRoomBookingByEventId(eventId);
		model.addAttribute("meetingRoom", meetingRoom);
		model.addAttribute("edit", true);
		return "registration";
	}
	
	/*
	 * This method will be called on form submission, handling POST request for
	 * updating meetingRoom in database. It also validates the user input
	 */
	@RequestMapping(value = { "/edit-{eventId}-meetingRoom" }, method = RequestMethod.POST)
	public String updateMeetingRoomBooking(@Valid BookRoomTrans meetingRoom, BindingResult result,
			ModelMap model, @PathVariable int eventId) {
		
		if (result.hasErrors()) {
			return "registration";
		}

		service.updateMeetingRoomBooking(meetingRoom);
		model.addAttribute("success", "MeetingRoom " + meetingRoom.getEventId()	+ " updated successfully");
		return "success";
	}

	
	/*
	 * This method will delete an meetingRoom by it's Code value.
	 */
	@RequestMapping(value = { "/delete-{eventId}-meetingRoom" }, method = RequestMethod.GET)
	public String deleteMeetingRoomBooking(@PathVariable int eventId) {
		service.deleteMeetingRoomBookingByEventId(eventId);
		return "redirect:/list";
	}

}
