package sg.com.ctc.picoservice.controller;

import java.io.File;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

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
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import sg.com.ctc.picoservice.service.EventTransService;
import sg.com.ctc.picoservice.service.MeetingRoomService;
import sg.com.ctc.picoservice.service.RoomMastService;

@SuppressWarnings("deprecation")
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

		String photoId = UUID.randomUUID().toString();
		String filename = env.getProperty("imageFilePath") + meetingRoomId + "/" + env.getProperty("imageFileName")
				+ ".png";

		try {

			HttpClientBuilder b = HttpClientBuilder.create();

			// setup a Trust Strategy that allows all certificates.
			//
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					return true;
				}
			}).build();
			b.setSslcontext(sslContext);

			// don't check Hostnames, either.
			// -- use SSLConnectionSocketFactory.getDefaultHostnameVerifier(),
			// if you don't want to weaken
			HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;

			// here's the special part:
			// -- need to create an SSL Socket Factory, to use our weakened
			// "trust strategy";
			// -- and create a Registry, to register it.
			//
			SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.getSocketFactory())
					.register("https", sslSocketFactory).build();

			// now, we create connection-manager using our Registry.
			// -- allows multi-threaded use
			PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			b.setConnectionManager(connMgr);

			HttpClient httpclient = b.build();

			String boundary = "---------------" + photoId;

			// CloseableHttpClient httpClient =
			// HttpClientBuilder.create().setDefaultRequestConfig(config).build();
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
			HttpResponse response2 = httpclient.execute(httpPut);
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
