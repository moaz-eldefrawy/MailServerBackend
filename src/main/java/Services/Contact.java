package Services;

import java.util.ArrayList;

public class Contact{
    private String name;
    private ArrayList<String> emails;

    public Contact(String name) {
        this.name = name;
        this.emails = new ArrayList<>();
    }

    public Contact() {}

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

    // For testing
    public boolean equals(Contact c){
        return this.name.equals(c.name) && this.emails.equals(c.getEmails());
    }

}
