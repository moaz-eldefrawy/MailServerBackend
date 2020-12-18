import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    public String email;
    public String password;
    public ArrayList<User> contacts;
    public ArrayList<Mail> mails;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User (JSONObject obj){
        if (obj == null)
            throw new RuntimeException("User Constructor Parameter null");

        this.email = (String) obj.get("email");
        this.password = (String) obj.get("password");

        JSONArray contactsJSON = (JSONArray) obj.get("contacts");
        for (Object contact : contactsJSON){
            this.contacts.add(new User((JSONObject) contact));
        }

        JSONArray mailsJSON = (JSONArray) obj.get("mails");
        for (Object mail : mailsJSON){
            this.mails.add(new Mail((JSONObject) mail));
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JSONObject toJSON(){
        JSONObject userJSON = new JSONObject();

        userJSON.put("email", email);
        userJSON.put("password", password);


        JSONArray contactsJSON = new JSONArray();

        if (contacts != null) {
            for (User u : contacts)
                contactsJSON.add(u.toJSON());
        }
        userJSON.put("contacts", contactsJSON);

        JSONArray mailsJSON = new JSONArray();

        if (mails != null) {
            for (Mail m : mails)
                contactsJSON.add(m.toJSON());
        }
        userJSON.put("mails", mailsJSON);

        return userJSON;
    }
}
