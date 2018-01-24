package sg.com.ctc.picoservice.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.joda.time.LocalDateTime;

import sg.com.ctc.picoservice.model.BookRoomTrans;
import sg.com.ctc.picoservice.model.EventTrans;
import sg.com.ctc.picoservice.model.RoomMast;
import sg.com.ctc.picoservice.service.EventTransService;
import sg.com.ctc.picoservice.service.MeetingRoomService;
import sg.com.ctc.picoservice.service.RoomMastService;
import sg.com.ctc.picoservice.util.Util;

@RestController
@RequestMapping("/rest")
public class PicoRestController {

	@Autowired
	MeetingRoomService service;

	@Autowired
	RoomMastService roomMastService;

	@Autowired
	EventTransService eventTransService;

	@Autowired
	private Environment env;

	@RequestMapping(value = {
			"/generateimage/{meetingRoomId}" }, method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseStatus(value = HttpStatus.OK)
	public String generateImage(@PathVariable String meetingRoomId) throws NotFoundException {
		int timeout = 300;
		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000)
				.setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();

		List<BookRoomTrans> meetingRooms = service.findMeetingRoomBookingByDate(new Date(), meetingRoomId);
		RoomMast room = roomMastService.findById(meetingRoomId);

		String filename = env.getProperty("imageFilePath") + env.getProperty("imageFileName") + ".png";
		String text = "";
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
				
				text = "Meeting Room: " + roomName + "( " + roomPax + " )" + "\n" + " booked by "
						+ currentRoom.getUser() + "\n" + " on " + currentRoom.getEventStartDate().getDayOfMonth()
						+ " / " + currentRoom.getEventStartDate().getMonthOfYear() + " / "
						+ currentRoom.getEventStartDate().getYear() + " from "
						+ Util.rebuildTime(currentRoom.getEventStartTime().getHourOfDay()) + " : "
						+ Util.rebuildTime(currentRoom.getEventStartTime().getMinuteOfHour()) + " : "
						+ Util.rebuildTime(currentRoom.getEventStartTime().getSecondOfMinute()) + " until "
						+ Util.rebuildTime(currentRoom.getEventEndTime().getHourOfDay()) + " : "
						+ Util.rebuildTime(currentRoom.getEventEndTime().getMinuteOfHour()) + " : "
						+ Util.rebuildTime(currentRoom.getEventEndTime().getSecondOfMinute()) + "\n" + " for "
						+ meetingAgenda + "\n" + "Upcoming Booking: \n";
				
				if (nextRoom != null) {
					
					EventTrans trans2 = eventTransService.findById(nextRoom.getEventId());					
					if (trans2 != null) {
						nextMeetingAgenda = trans2.getName();
					}

					text = text
							+ nextRoom.getEventStartDate().getDayOfMonth() + " / "
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
					text = text + "AVAILABLE" + "\n";
				}
			}
		} else {
			text = "Meeting Room: " + roomName + "( " + roomPax + " )" + "\n" + "\n" + Util.buildSpace(18) + "AVAILABLE"
					+ "\n\n"
					+ "Upcoming Booking: \n"
					+ "AVAILABLE \n";
		}

		System.out.println("Meeting Room Text: " + text);

		CloseableHttpClient httpClientForGet = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

		try {
			System.out.println("Encode text: " + URLEncoder.encode(text, "UTF-8"));
			String requestURLForGet = env.getProperty("img4meRestUrl") + "?bcolor="
					+ env.getProperty("img4meBackgroudColor") + "&fcolor=" + env.getProperty("img4meFontColor")
					+ "&font=" + env.getProperty("img4meFont") + "&size=" + env.getProperty("img4meFontSize") + "&type="
					+ env.getProperty("img4meImgType") + "&text=" + URLEncoder.encode(text, "UTF-8") + "";
			HttpGet httpGet = new HttpGet(requestURLForGet);
			httpGet.addHeader("X-Mashape-Key", env.getProperty("img4meRestMashKey"));
			httpGet.addHeader("Accept", "text/plain");

			HttpResponse response = httpClientForGet.execute(httpGet);

			HttpEntity entity = response.getEntity();

			// Read the contents of an entity and return it as a String.
			String content = EntityUtils.toString(entity);

			StatusLine line = response.getStatusLine();
			if (line.getStatusCode() == 200) {
				Util.saveImage(content, filename);
				Util.resize(filename, filename, Integer.parseInt(env.getProperty("imgResizeWidth")),
						Integer.parseInt(env.getProperty("imgResizeHeight")));
				// Util.resize(filename, filename,
				// Double.parseDouble(env.getProperty("imgResizePercentage")));
			}
			System.out.println("Response pic: " + line);
		} catch (Exception e) {
			throw new NotFoundException("Failed upload: " + e.getMessage());
		}

		return "Sucessful";
	}

	@RequestMapping(value = { "/generate/{id}" }, method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseStatus(value = HttpStatus.OK)
	public String generate(@PathVariable String id) throws NotFoundException {

		int timeout = 300;
		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000)
				.setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
		String photoId = UUID.randomUUID().toString();
		String filename = env.getProperty("imageFilePath") + env.getProperty("imageFileName") + ".png";

		try {

			String boundary = "---------------" + photoId;

			CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
			String requestURL = env.getProperty("picoRestUrl") + id
					+ "?display=0&process=true&singleupdate=false&nodither=false&whitebg="
					+ env.getProperty("picoWhiteBackground");
			HttpPut httpPut = new HttpPut(requestURL);
			httpPut.addHeader("Authorization", env.getProperty("picoAuthorizationKey"));
			httpPut.addHeader("Content-Type", "multipart/form-data; boundary=" + boundary);
			httpPut.addHeader("Accept", "application/json");

			// new file and and entity
			File file = new File(filename);

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			builder.setBoundary(boundary);
			builder.addBinaryBody("file", file, ContentType.create("image/png"), file.getName());

			HttpEntity entity2 = builder.build();
			httpPut.setEntity(entity2);
			HttpResponse response2 = httpClient.execute(httpPut);
			StatusLine line2 = response2.getStatusLine();

			System.out.println("Status Code: " + line2.getStatusCode());

			if (file.delete()) {
				System.out.println(file.getName() + " is deleted!");
			} else {
				System.out.println("Delete operation is failed.");
			}

			// return code indicates upload failed so throw exception
			if (line2.getStatusCode() != 201) {
				throw new NotFoundException("Failed upload");
			}

		} catch (Exception e) {
			throw new NotFoundException("Failed upload: " + e.getMessage());
		}

		return "Sucessful";
	}

	@SuppressWarnings("serial")
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public class NotFoundException extends Exception {

		public NotFoundException(String message) {
			System.out.println("Error message: " + message);
		}
	}

}
