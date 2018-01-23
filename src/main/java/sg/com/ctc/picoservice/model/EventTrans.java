package sg.com.ctc.picoservice.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class EventTrans {
	
	@Id
	@Column(name="rbm_event_id")
	private int id;
	
	@Column(name = "rbm_event_name", nullable = false)
	private String name;
	
	@Column(name = "rbm_event_desc", nullable = false)
	private String desc;
	
	@Column(name = "rbm_event_type", nullable = false)
	private String type;
	
	@Size(min=1, max=2)
	@Column(name = "rbm_event_category", nullable = false)
	private String category;
	
	@Column(name = "rbm_event_organiser", nullable = false)
	private String organiser;
	
	@Column(name = "rbm_event_objective", nullable = true)
	private String objective;
	
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
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	@Column(name = "rbm_event_dcreated", nullable = true)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime eventDateCreated;
	
	@Column(name = "rbm_event_user", nullable = false)
	private String user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getOrganiser() {
		return organiser;
	}

	public void setOrganiser(String organiser) {
		this.organiser = organiser;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
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
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + ((eventDateCreated == null) ? 0 : eventDateCreated.hashCode());
		result = prime * result + ((eventEndDate == null) ? 0 : eventEndDate.hashCode());
		result = prime * result + ((eventEndTime == null) ? 0 : eventEndTime.hashCode());
		result = prime * result + ((eventStartDate == null) ? 0 : eventStartDate.hashCode());
		result = prime * result + ((eventStartTime == null) ? 0 : eventStartTime.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((objective == null) ? 0 : objective.hashCode());
		result = prime * result + ((organiser == null) ? 0 : organiser.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		EventTrans other = (EventTrans) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (objective == null) {
			if (other.objective != null)
				return false;
		} else if (!objective.equals(other.objective))
			return false;
		if (organiser == null) {
			if (other.organiser != null)
				return false;
		} else if (!organiser.equals(other.organiser))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
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
		return "EventTrans [id=" + id + ", name=" + name + ", desc=" + desc + ", type=" + type + ", category="
				+ category + ", organiser=" + organiser + ", objective=" + objective + ", eventStartDate="
				+ eventStartDate + ", eventEndDate=" + eventEndDate + ", eventStartTime=" + eventStartTime
				+ ", eventEndTime=" + eventEndTime + ", eventDateCreated=" + eventDateCreated + ", user=" + user + "]";
	}

}
