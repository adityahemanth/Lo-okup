package lookup;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@XmlRootElement
// JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
public class Post {
  private String postkey;
  private String title;
  private String content;
  private Key parent
  private Date dateCreated;

  //constructors (default () and (String,String,String,Date))
  public Post() {
  	postkey = "";
    title = "";
    content = "";
    dateCreated = new Date();
  }

  public Post(String postkey, String title, String content, Date dateCreated) {
  	this.postkey = postkey;
  	this.title = title;
  	this.content = content;
    this.dateCreated = new Date();
  }
  
  public String getPostkey() {
  	return postkey;
  }

  public String getTitle() {
  	return title;
  }

  public String getContent() {
    return content;
  }

  public Date getDateCreated() {
  	return dateCreated;
  }

  public void setusername(String postkey){
  	this.postkey = postkey;
  }

  public void setTitle(String title) {
  	this.title = title;
  }

  public void setContent (String content){
    this.content = content;
  }

  public void setDateCreated(Date date){
  	this.dateCreated = date;
  }

} 
