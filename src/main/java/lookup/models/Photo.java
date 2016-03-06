package lookup.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Date;

// this serves as a data wrapper to be passed
// around in methods.


@XmlRootElement
public class Photo {

	private String title;
	private String owner;
	private String ownerID;
	private String place;
	private String description;

	public Photo() {
		// empty constructor
	}

	// getters and setters
	public String getTitle(){
		return title;
	}

	public String getOwnerID(){
		return ownerID;
	}

	public String getOwner() {
		return owner;
	}

	public String getPlace() {
		return place;
	}

	public String getDescription() {
		return description;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}