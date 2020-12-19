package Services;

import org.json.simple.JSONObject;

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
    public ArrayList attachments;
    public String ID;
    public String bodyText;


    public Mail(String sender,
         String subject, Date date, Integer priority){
        this.sender=sender;
        this.subject=subject;
        this.date= date;
        this.priority=priority;
        this.status="unread";
        this.attachments = new ArrayList();
        this.ID = UUID.randomUUID().toString();
        this.bodyText = "";
    }


    public Mail (JSONObject obj){
        if (obj == null)
            throw new RuntimeException("Mail Constructor Parameter null");

        this.sender = (String) obj.get("sender");
        this.subject = (String) obj.get("subject");

        //TODO figure out how to parse the date
        this.date = new Date((long)obj.get("date"));
        this.priority = (int)(long)obj.get("priority");
        this.status = (String) obj.get("status");

        //TODO figure out how to parse attachments
        this.attachments = (ArrayList) obj.get("attachments");
        this.ID = (String) obj.get("ID");
        this.bodyText = (String) obj.get("bodyText");
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


    public JSONObject toJSON(){
        JSONObject mailJSON = new JSONObject();

        mailJSON.put("sender", sender);
        mailJSON.put("subject", subject);

        // TODO I am not sure if I should put date or date.toString()
        mailJSON.put("date", date.getTime());

        mailJSON.put("priority", priority);
        mailJSON.put("status", status);

        // TODO attachments JSON?
        mailJSON.put("attachments", attachments);

        mailJSON.put("ID", ID);
        mailJSON.put("bodyText", bodyText);

        return mailJSON;
    }
}
