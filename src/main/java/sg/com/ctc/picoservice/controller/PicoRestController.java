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
			"/generate/{id}/{meetingRoomId}" }, method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseStatus(value = HttpStatus.OK)
	public String generate(@PathVariable String id, @PathVariable String meetingRoomId) throws NotFoundException {

		int timeout = 300;
		RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000)
				.setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
		String photoId = UUID.randomUUID().toString();
		List<BookRoomTrans> meetingRooms = service.findMeetingRoomBookingByDate(new Date(), meetingRoomId);
		RoomMast room = roomMastService.findById(meetingRoomId);
		
		String filename = env.getProperty("imageFilePath") + photoId + ".png";
		String text = "Meeting Room is available at the moment";
		String roomName = "";
		String meetingAgenda = "";
		if(room!=null){
			roomName = room.getName();
		}
		System.out.println("Meeting Room Size: "+meetingRooms.size());
		if (meetingRooms.size() > 0) {
			
			EventTrans trans = eventTransService.findById(meetingRooms.get(0).getEventId());
			if(trans!=null){
				meetingAgenda = trans.getName();
			}
			
			text = "Meeting Room " + roomName + " booked by " + meetingRooms.get(0).getUser() + "on "
					+ meetingRooms.get(0).getEventStartDate().getDayOfMonth() + "/"
					+ meetingRooms.get(0).getEventStartDate().getMonthOfYear() + "/"+
					+ meetingRooms.get(0).getEventStartDate().getYear()+" from "
					+ meetingRooms.get(0).getEventStartTime().getHourOfDay() + ":"
					+ meetingRooms.get(0).getEventStartTime().getMinuteOfHour() + ":"
					+ meetingRooms.get(0).getEventStartTime().getSecondOfMinute() + " until "
					+ meetingRooms.get(0).getEventEndTime().getHourOfDay() + ":"
					+ meetingRooms.get(0).getEventEndTime().getMinuteOfHour() + ":"
					+ meetingRooms.get(0).getEventEndTime().getSecondOfMinute() + " for " + meetingAgenda;
		}else{
			text = "Meeting Room "+roomName+" is available at the moment";
		}

		CloseableHttpClient httpClientForGet = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

		try {
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
			throw new NotFoundException("Failed upload");
		}
		
		return "Sucessful";
	}
	
	@SuppressWarnings("serial")
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public class NotFoundException extends Exception {
		public NotFoundException(String message) {
	    }
	}

}
