package Services;

import java.io.Serializable;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private String email;
    private String password;
    private HashMap<String, String> contacts;
    private HashMap<String, ArrayList<String> > folders;
    private ArrayList<Mail> mails;

    public User() {
        this.contacts = new HashMap<String, String>();
        this.folders = new HashMap<String, ArrayList<String>>();
        this.mails = new ArrayList<Mail>();
        this.folders.put("inbox", new ArrayList<String>());
        this.folders.put("trash", new ArrayList<String>());
        this.folders.put("drafts", new ArrayList<String>());
        this.folders.put("sent", new ArrayList<String>());
    }

    public User(String email, String password) {
        this();
        this.email = email;
        this.password = password;
    }

    public User (JSONObject obj){
        if (obj == null)
            throw new RuntimeException("User Constructor Parameter null");

        this.email = (String) obj.get("email");
        this.password = (String) obj.get("password");

        JSONArray contactsJSON = (JSONArray) obj.get("contacts");
        JSONArray mailsJSON = (JSONArray) obj.get("mails");
        this.mails = new ArrayList<>();
        for (Object mail : mailsJSON){
            this.mails.add(new Mail((JSONObject) mail));
        }
        /*  TODO: THIS PART IS BUGGY
        JSONObject jo = (JSONObject) obj.get("folders");
        this.folders = new HashMap<String, ArrayList<String> >();
        for (Object key: jo.keySet()) {
            this.folders.put(key.toString() , (ArrayList<String>) jo.get((String)key));
        }
        System.out.println("\n\n\n\n\n\n\n\n\n\nHERE ------------\n\n\n\n\n\n");
        */
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

    public HashMap<String, String> getContacts() {
        return contacts;
    }
    public void setContacts(HashMap<String, String> contacts) {
        this.contacts = contacts;
    }

    public HashMap<String, ArrayList<String>> getFolders() {
        return folders;
    }
    public void setFolders(HashMap<String, ArrayList<String>> folders) {
        this.folders = folders;
    }

    public ArrayList<Mail> getMails() {
        return mails;
    }

    public void setMails(ArrayList<Mail> mails) {
        this.mails = mails;
    }

    public JSONObject toJSON(){
        JSONObject userJSON = new JSONObject();

        userJSON.put("email", email);
        userJSON.put("password", password);


        JSONArray contactsJSON = new JSONArray();

       /* if (contacts != null) {
            for (User u : contacts)
                contactsJSON.put(u.toJSON());
        }*/
        userJSON.put("contacts", contactsJSON);

        JSONArray mailsJSON = new JSONArray();

        if (mails != null) {
            for (Mail m : mails)
                mailsJSON.put(m.toJSON());
        }
        userJSON.put("mails", mailsJSON);


        JSONObject folderJson = new JSONObject();
        for (Map.Entry<String, ArrayList<String>> entry : this.folders.entrySet()) {
            String folderName =  entry.getKey();
            ArrayList<String> values = entry.getValue();
            folderJson.put(folderName, values);
        }

        userJSON.put("folders",folderJson);

        return userJSON;
    }


    public boolean equals(User b){
        // Email address
        if (!this.email.equals(b.email))
            return false;

        // Password
        if (!this.password.equals(b.password))
            return false;

        // Contacts
        if (this.contacts.size() != b.contacts.size())
            return  false;


        // TODO: compare a hasmap not a user class
        /*for (User aContact : this.contacts){
            for (User bContact : b.contacts){
                if (!aContact.equals(bContact))
                    return false;
            }
        }*/

        // Mails
        if (this.mails.size() != b.mails.size())
            return  false;

        for (Mail aMail : this.mails){
            for (Mail bMail : b.mails){
                if (!aMail.equals(aMail))
                    return false;
            }
        }

        //TODO: check this.folders
        try {
            for (Map.Entry<String, ArrayList<String>> entry : this.folders.entrySet()) {
                String folderName = entry.getKey();
                ArrayList<String> myIDs = entry.getValue();
                ArrayList<String> hisIDs = b.folders.get(folderName);
                Collections.sort(myIDs);
                Collections.sort(hisIDs);
                if(myIDs.size() != hisIDs.size()) return false;
                for(int i=0; i<myIDs.size(); i++){
                    if(!myIDs.get(i).equals(hisIDs.get(i)))
                        return false;
                }
            }
        } catch (Exception e){
            return false;
        }

        return true;
    }
}
