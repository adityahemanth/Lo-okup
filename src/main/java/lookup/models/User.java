package lookup.models;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@XmlRootElement
// JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
public class User {
  private String username;
  private String picBlob;
  private String email;
  private String fullname;
  private String description;
  private Date dob;
  private Date dateCreated;

  //constructors (default () and (String,String,String,Date))
  public User() {
  }

  public User(String username, String email, String fullname, String description, Date dob) {
  	this.username = username;
  	this.fullname = fullname;
  	this.description = description;
    this.email = email;
    this.dob = dob;
    this.dateCreated = new Date();
  }
  
  public String getUsername() {
  	return username;
  }

  public String getFullname() {
  	return fullname;
  }

  public String getEmail() {
    return email;
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

  public String getPicBlob(){
    return picBlob;
  }

  public void setusername(String username){
  	this.username = username;
  }

  public void setFullname(String val) {
  	this.fullname = val;
  }

  public void setDescription(String description){
    this.description = description;
  }

  public void setEmail(String email){
    this.email = email;
  }

  public void setDOB(Date date){
  	this.dob = date;
  }

  public void setPicBlob(String picBlob) {
    this.picBlob = picBlob;
  }

} 
