import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Mail implements Serializable {

    public static final long serialVersionUID = 3347324734166375499L;
    public String sender;
    public String subject;
    public Date date;
    public Integer priority;
    public String status;
    public ArrayList attachements;
    public String ID;
    public String bodyText;


    Mail(String sender,
         String subject, Date date, Integer priority){
        this.sender=sender;
        this.subject=subject;
        this.date= date;
        this.priority=priority;
        this.status="unread";
        this.ID = UUID.randomUUID().toString();
    }

    // TODO:
    public void addAttachment(){

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }
}
