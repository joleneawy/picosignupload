package sg.com.ctc.picoservice.controller;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.validation.Valid;

import org.joda.time.LocalDateTime;
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
import sg.com.ctc.picoservice.model.EventTrans;
import sg.com.ctc.picoservice.model.RoomMast;
import sg.com.ctc.picoservice.service.EventTransService;
import sg.com.ctc.picoservice.service.MeetingRoomService;
import sg.com.ctc.picoservice.service.RoomMastService;
import sg.com.ctc.picoservice.util.Util;

@Controller
@RequestMapping("/")
public class AppController {

	@Autowired
	MeetingRoomService service;

	@Autowired
	MessageSource messageSource;

	@Autowired
	RoomMastService roomMastService;

	@Autowired
	EventTransService eventTransService;

	@RequestMapping(value = { "/getTemplate/{meetingRoomId}" }, method = RequestMethod.GET)
	public String getTemplate(ModelMap model, @PathVariable String meetingRoomId) {

		List<BookRoomTrans> meetingRooms = service.findMeetingRoomBookingByDate(new Date(), meetingRoomId);
		RoomMast room = roomMastService.findById(meetingRoomId);

		String meetingRoom = "";
		String result1 = "";
		String result2 = "";
		String pax = "";
		
		String roomName = "";
		String roomPax = "";
		String meetingAgenda = "No Title";
		String nextMeetingAgenda = "No Title";
		if (room != null) {
			roomName = room.getName();
			roomPax = room.getDesc1().replaceAll("<li>Capacity :", "");
		}
		if (meetingRooms.size() > 0) {
			BookRoomTrans currentRoom = null;
			BookRoomTrans nextRoom = null;
			int i = 0;
			int n = 0;
			for (BookRoomTrans r : meetingRooms) {

				LocalDateTime date = LocalDateTime.now();
				if (currentRoom == null) {
					if ((date.equals(r.getEventStartTime()) || date.isAfter(r.getEventStartTime()))
							&& (date.equals(r.getEventEndTime()) || date.isBefore(r.getEventEndTime()))) {
						currentRoom = r;
						n = i;
					}
				} else {
					if (i == n + 1) {
						nextRoom = r;
					}
				}
				i++;
			}

			if (currentRoom == null) {
				nextRoom = meetingRooms.get(0);
			}

			if (currentRoom != null) {
				EventTrans trans = eventTransService.findById(currentRoom.getEventId());
				if (trans != null) {
					meetingAgenda = trans.getName();
				}
				meetingRoom = roomName;
				pax = "( " + roomPax + " )";
				result1 = " booked by "
						+ currentRoom.getUser() + "\n" + " on " + currentRoom.getEventStartDate().getDayOfMonth()
						+ " / " + currentRoom.getEventStartDate().getMonthOfYear() + " / "
						+ currentRoom.getEventStartDate().getYear() + " from "
						+ Util.rebuildTime(currentRoom.getEventStartTime().getHourOfDay()) + " : "
						+ Util.rebuildTime(currentRoom.getEventStartTime().getMinuteOfHour()) + " : "
						+ Util.rebuildTime(currentRoom.getEventStartTime().getSecondOfMinute()) + " until "
						+ Util.rebuildTime(currentRoom.getEventEndTime().getHourOfDay()) + " : "
						+ Util.rebuildTime(currentRoom.getEventEndTime().getMinuteOfHour()) + " : "
						+ Util.rebuildTime(currentRoom.getEventEndTime().getSecondOfMinute()) + "\n" + " for "
						+ meetingAgenda;

				if (nextRoom != null) {
					
					EventTrans trans2 = eventTransService.findById(nextRoom.getEventId());					
					if (trans2 != null) {
						nextMeetingAgenda = trans2.getName();
					}

					result2 = nextRoom.getEventStartDate().getDayOfMonth() + " / "
							+ nextRoom.getEventStartDate().getMonthOfYear() + " / "
							+ nextRoom.getEventStartDate().getYear() + " from "
							+ Util.rebuildTime(nextRoom.getEventStartTime().getHourOfDay()) + " : "
							+ Util.rebuildTime(nextRoom.getEventStartTime().getMinuteOfHour()) + " : "
							+ Util.rebuildTime(nextRoom.getEventStartTime().getSecondOfMinute()) + " until "
							+ Util.rebuildTime(nextRoom.getEventEndTime().getHourOfDay()) + " : "
							+ Util.rebuildTime(nextRoom.getEventEndTime().getMinuteOfHour()) + " : "
							+ Util.rebuildTime(nextRoom.getEventEndTime().getSecondOfMinute()) + "\n" + " for "
							+ nextMeetingAgenda + "\n";
				}else{
					result2 = "AVAILABLE" + "\n";
				}
			}
		} else {
			meetingRoom = roomName;
			pax = "( " + roomPax + " )";
			result1 = "AVAILABLE";
			result2 = "AVAILABLE";
		}

		model.addAttribute("meetingRoom", meetingRoom);
		model.addAttribute("pax", pax);
		model.addAttribute("result1", result1);
		model.addAttribute("result2", result2);
		
		return "template";
	}

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
	public String saveMeetingRoomBooking(@Valid BookRoomTrans meetingRoom, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			return "registration";
		}

		if (!service.isEventIdUnique(meetingRoom.getId(), meetingRoom.getEventId())) {
			FieldError codeError = new FieldError("meetingRoom", "eventId", messageSource.getMessage("non.unique.code",
					new Integer[] { meetingRoom.getEventId() }, Locale.getDefault()));
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
	public String updateMeetingRoomBooking(@Valid BookRoomTrans meetingRoom, BindingResult result, ModelMap model,
			@PathVariable int eventId) {

		if (result.hasErrors()) {
			return "registration";
		}

		service.updateMeetingRoomBooking(meetingRoom);
		model.addAttribute("success", "MeetingRoom " + meetingRoom.getEventId() + " updated successfully");
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
