package Services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;


@JsonIgnoreProperties(ignoreUnknown =  true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Mail implements Serializable, Cloneable {

    public static final long serialVersionUID = 3347324734166375499L;
    private String sender;
    private ArrayList<String> receivers;
    private String subject;
    private Date date;
    private Integer priority;
    private String status;
    private ArrayList<String> attachments;
    private String ID;
    private String bodyText;


    
    public Mail(){
        ID = UUID.randomUUID().toString();
        date =  new Date(System.currentTimeMillis());
        status = "unread";
        bodyText = "";
        subject = "";
        priority = 0;
        attachments =new ArrayList<String>();
        receivers = new ArrayList<String>();
    }

    
    public Mail(String sender,
         String subject, Date date,
                Integer priority){
        this.sender=sender;
        this.subject=subject;
        this.date= date;
        this.priority=priority;
        this.status="unread";
        this.attachments = new ArrayList<>();
        this.ID = UUID.randomUUID().toString();
        this.bodyText = "";
    }

    public void genRandomID() {
        this.ID = UUID.randomUUID().toString();
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

        this.attachments = new ArrayList<>();
        
        this.ID = (String) obj.get("ID");
        this.bodyText = (String) obj.get("bodyText");
    }


    
    
    public boolean equals(Mail b){
        if (!this.sender.equals(b.sender))
            return false;
        if (!this.subject.equals(b.subject))
            return false;
        if (!this.date.equals(b.date))
            return false;
        if (!this.priority.equals(b.priority))
            return false;
        if (!this.status.equals(b.status))
            return false;
        if (!this.attachments.equals(b.attachments))
            return false;
        if (!this.ID.equals(b.ID))
            return false;
        if (!this.bodyText.equals(b.bodyText))
            return false;
        return true;
    }
    

    public void addAttachment(String attachment){
        this.attachments.add(attachment);
    }

    public ArrayList<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<String> attachments) {
        this.attachments = attachments;
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

    public ArrayList<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(ArrayList<String> receivers) {
        this.receivers = receivers;
    }

    public JSONObject toJSON(){
        JSONObject mailJSON = new JSONObject();

        mailJSON.put("sender", sender);
        mailJSON.put("subject", subject);

        // TODO I am not sure if I should put date or date.toString()
        System.out.println("DATE--> " + date.getTime());
        mailJSON.put("date", date.getTime());

        mailJSON.put("priority", priority);
        mailJSON.put("status", status);

        // TODO attachments
        JSONArray attachmentsJSON = new JSONArray();
        attachmentsJSON.put(attachments);
        mailJSON.put("attachments", attachmentsJSON);

        mailJSON.put("ID", ID);
        mailJSON.put("bodyText", bodyText);

        return mailJSON;
    }


    public Mail clone(){
        Mail copy = new Mail();

        copy.setSender(this.sender);
        copy.setReceivers((ArrayList<String>) this.receivers.clone());
        copy.setSubject(this.subject);
        copy.setDate((Date) date.clone());
        copy.setPriority(Integer.valueOf(this.priority));
        copy.setStatus(this.status);
        copy.setAttachments((ArrayList<String>) this.attachments.clone());
        copy.setBodyText(this.bodyText);
        return copy;
    }
}
