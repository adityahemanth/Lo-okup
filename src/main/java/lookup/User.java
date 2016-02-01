package lookup;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@XmlRootElement
// JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
public class User {
  private String username;
  private String fullName;
  private String description;
  private Date dob;
  private Date dateCreated;

  //constructors (default () and (String,String,String,Date))
  public User() {
  	username = "";
  	fullName = "";
    description = "";
    dob = new Date();
  	dateCreated = new Date();
  }

  public User(String username, String fullName, String description, Date dob) {
  	this.username = username;
  	this.fullName = fullName;
  	this.description = description;
    this.dob = dob;
    this.dateCreated = new Date();
  }
  
  public String getUsername() {
  	return username;
  }

  public String getFullName() {
  	return fullName;
  }

  public String getDescription() {
    return description;
  }

  public Date getDOB() {
  	return dob;
  }

  public Date getDateCreated(){
    return dateCreated;
  }

  public void setusername(String username){
  	this.username = username;
  }

  public void setfullName(String val) {
  	this.fullName = val;
  }

  public void setDescription(String description){
    this.description = description;
  }

  public void setDOB(Date date){
  	this.dob = date;
  }

} 
