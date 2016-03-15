package lookup.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.google.appengine.api.datastore.*;

import java.util.Date;

// this serves as a data wrapper to be passed
// around in methods.


@XmlRootElement
public class Photo {

	private String title;
	private String owner;
	private String place;
	private String url;
	private String blobkey;
	private int likes;
	private boolean pub;
	private String description;
	private GeoPt location;

	public Photo() {
		// empty constructor
	}

	// getters and setters
	public String getTitle(){
		return title;
	}

	public String getOwner() {
		return owner;
	}

	public String getPlace() {
		return place;
	}

	public boolean isPublic(){
		return pub;
	}

	public String getURL(){
		return url;
	}

	public GeoPt getLocation(){
		return location;
	}

	public String getDescription() {
		return description;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public void setURL(String url){
		this.url = url;
	}

	public void setPublic(boolean bool){
		pub = bool;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLocation(GeoPt loc){
		location = loc;
	}
	
}