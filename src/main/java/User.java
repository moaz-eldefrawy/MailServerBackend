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
        for (User u : contacts)
            contactsJSON.add(u.toJSON());
        userJSON.put("contacts", contactsJSON);

        JSONArray mailsJSON = new JSONArray();
        for (Mail m : mails)
            contactsJSON.add(m.toJSON());
        userJSON.put("mails", mailsJSON);

        return userJSON;
    }
}
