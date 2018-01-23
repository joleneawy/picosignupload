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
@Table(name="dbo.rbm_room_mast")
public class RoomMast {
	
	@Id
	@Column(name="rbm_room_id")
	private String id;

	@Column(name = "rbm_room_name", nullable = false)
	private String name;
	
	@Column(name = "rbm_room_type", nullable = false)
	private String type;
	
	@Column(name = "rbm_room_loc", nullable = false)
	private String location;
	
	@Column(name = "rbm_room_desc1", nullable = true)
	private String desc1;
	
	@Column(name = "rbm_room_desc2", nullable = true)
	private String desc2;
	
	@Column(name = "rbm_room_desc3", nullable = true)
	private String desc3;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	@Column(name = "rbm_room_dcreated", nullable = false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime roomCreatedDate;
	
	@Column(name = "rbm_room_user", nullable = false)
	private String user;
	
	@Size(min=1, max=2)
	@Column(name = "rbm_room_bookable", nullable = false)
	private String bookable;
	
	@Size(min=1, max=2)
	@Column(name = "room_status", nullable = false)
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDesc1() {
		return desc1;
	}

	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}

	public String getDesc2() {
		return desc2;
	}

	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}

	public String getDesc3() {
		return desc3;
	}

	public void setDesc3(String desc3) {
		this.desc3 = desc3;
	}

	public LocalDateTime getRoomCreatedDate() {
		return roomCreatedDate;
	}

	public void setRoomCreatedDate(LocalDateTime roomCreatedDate) {
		this.roomCreatedDate = roomCreatedDate;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getBookable() {
		return bookable;
	}

	public void setBookable(String bookable) {
		this.bookable = bookable;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookable == null) ? 0 : bookable.hashCode());
		result = prime * result + ((desc1 == null) ? 0 : desc1.hashCode());
		result = prime * result + ((desc2 == null) ? 0 : desc2.hashCode());
		result = prime * result + ((desc3 == null) ? 0 : desc3.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((roomCreatedDate == null) ? 0 : roomCreatedDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		RoomMast other = (RoomMast) obj;
		if (bookable == null) {
			if (other.bookable != null)
				return false;
		} else if (!bookable.equals(other.bookable))
			return false;
		if (desc1 == null) {
			if (other.desc1 != null)
				return false;
		} else if (!desc1.equals(other.desc1))
			return false;
		if (desc2 == null) {
			if (other.desc2 != null)
				return false;
		} else if (!desc2.equals(other.desc2))
			return false;
		if (desc3 == null) {
			if (other.desc3 != null)
				return false;
		} else if (!desc3.equals(other.desc3))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (roomCreatedDate == null) {
			if (other.roomCreatedDate != null)
				return false;
		} else if (!roomCreatedDate.equals(other.roomCreatedDate))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
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
		return "RoomMast [id=" + id + ", name=" + name + ", type=" + type + ", location=" + location + ", desc1="
				+ desc1 + ", desc2=" + desc2 + ", desc3=" + desc3 + ", roomCreatedDate=" + roomCreatedDate + ", user="
				+ user + ", bookable=" + bookable + ", status=" + status + "]";
	}

}
