package sg.com.ctc.picoservice.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="dbo.rbm_bookroom_trans")
public class BookRoomTrans {
	
	@Column(name="rbm_bookroom_id")
	private String id;
	
	@Id
	@Column(name = "rbm_event_id", nullable = false)
	private int eventId;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	@Column(name = "rbm_event_start_date", nullable = true)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime eventStartDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	@Column(name = "rbm_event_end_date", nullable = true)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime eventEndDate;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	@Column(name = "rbm_event_start_time", nullable = true)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime eventStartTime;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	@Column(name = "rbm_event_end_time", nullable = true)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime eventEndTime;
	
	@Size(min=1, max=2)
	@Column(name = "rbm_bookroom_status", nullable = false)
	private String status;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	@Column(name = "rbm_bookroom_dcreated", nullable = true)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime eventDateCreated;
	
	@Size(min=1, max=20)
	@Column(name = "rbm_bookroom_user", nullable = false)
	private String user;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public LocalDateTime getEventStartDate() {
		return eventStartDate;
	}

	public void setEventStartDate(LocalDateTime eventStartDate) {
		this.eventStartDate = eventStartDate;
	}

	public LocalDateTime getEventEndDate() {
		return eventEndDate;
	}

	public void setEventEndDate(LocalDateTime eventEndDate) {
		this.eventEndDate = eventEndDate;
	}

	public LocalDateTime getEventStartTime() {
		return eventStartTime;
	}

	public void setEventStartTime(LocalDateTime eventStartTime) {
		this.eventStartTime = eventStartTime;
	}

	public LocalDateTime getEventEndTime() {
		return eventEndTime;
	}

	public void setEventEndTime(LocalDateTime eventEndTime) {
		this.eventEndTime = eventEndTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getEventDateCreated() {
		return eventDateCreated;
	}

	public void setEventDateCreated(LocalDateTime eventDateCreated) {
		this.eventDateCreated = eventDateCreated;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventDateCreated == null) ? 0 : eventDateCreated.hashCode());
		result = prime * result + ((eventEndDate == null) ? 0 : eventEndDate.hashCode());
		result = prime * result + ((eventEndTime == null) ? 0 : eventEndTime.hashCode());
		result = prime * result + eventId;
		result = prime * result + ((eventStartDate == null) ? 0 : eventStartDate.hashCode());
		result = prime * result + ((eventStartTime == null) ? 0 : eventStartTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookRoomTrans other = (BookRoomTrans) obj;
		if (eventDateCreated == null) {
			if (other.eventDateCreated != null)
				return false;
		} else if (!eventDateCreated.equals(other.eventDateCreated))
			return false;
		if (eventEndDate == null) {
			if (other.eventEndDate != null)
				return false;
		} else if (!eventEndDate.equals(other.eventEndDate))
			return false;
		if (eventEndTime == null) {
			if (other.eventEndTime != null)
				return false;
		} else if (!eventEndTime.equals(other.eventEndTime))
			return false;
		if (eventId != other.eventId)
			return false;
		if (eventStartDate == null) {
			if (other.eventStartDate != null)
				return false;
		} else if (!eventStartDate.equals(other.eventStartDate))
			return false;
		if (eventStartTime == null) {
			if (other.eventStartTime != null)
				return false;
		} else if (!eventStartTime.equals(other.eventStartTime))
			return false;
		if (id != other.id)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BookRoomTrans [id=" + id + ", eventId=" + eventId + ", eventStartDate=" + eventStartDate
				+ ", eventEndDate=" + eventEndDate + ", eventStartTime=" + eventStartTime + ", eventEndTime="
				+ eventEndTime + ", status=" + status + ", eventDateCreated=" + eventDateCreated + ", user=" + user
				+ "]";
	}

	
}
