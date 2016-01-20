@Entity
public class TaskData {
  @Id public Long id;

  public String value;
  @Index public Date date;

  /**
   * Simple constructor just sets the date
   **/
  public TaskData() {
    date = new Date();
  }

  /**
   * Simple Constructor
   **/
  public TaskData(String value) {
    this();
    this.value = value;
  }

}