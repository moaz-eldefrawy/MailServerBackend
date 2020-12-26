package Services;

import java.util.ArrayList;
import java.util.UUID;

public class Contact{
    private String id;
    private String name;
    private ArrayList<String> emails;

    public Contact(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.emails = new ArrayList<>();
    }

    public Contact() {
        this.id = UUID.randomUUID().toString();
    }

    public ArrayList<String> getEmails() {
        return this.emails;
    }

    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void generateId() {
        this.id = UUID.randomUUID().toString();
    }

    // For testing
    public boolean equals(Contact c){
        return this.name.equals(c.name) && this.emails.equals(c.getEmails());
    }

}
