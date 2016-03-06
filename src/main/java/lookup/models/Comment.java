package lookup.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Date;

// this serves as a data wrapper to be passed
// around in methods.


@XmlRootElement
public class Comment {

	private String placeID;
	private String owner;
	private String content;

	public Comment() {
		// empty constructor
	}

	// getters and setters
	public String getPlaceID(){
		return placeID;
	}

	public String getOwner() {
		return owner;
	}

	public String getContent() {
		return content;
	}

	public void setPlaceID(String placeID){
		this.placeID = placeID;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setContent(String content) {
		this.content = content;
	}

}