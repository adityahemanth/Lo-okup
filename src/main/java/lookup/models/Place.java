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
public class Place {

	private String title;
	private GeoPt location;
	private String description;

	public Place() {
		// empty constructor
	}

	// getters and setters
	public String getTitle(){
		return title;
	}

	public GeoPt getLocation() {
		return location;
	}

	public String getDescription() {
		return description;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public void setLocation(GeoPt location) {
		this.location = location;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}