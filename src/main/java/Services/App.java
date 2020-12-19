package Services;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Queue;

public class App implements Serializable {


    private final long serialVersionUID = 1L;

    // set initial folders
    public static final String rootPath = System.getProperty("user.dir") + File.separator + "root";
    public static final String usersFolderPath = rootPath + File.separator + "users";
    public static final String mailsFolderPath = rootPath + File.separator + "mails";
    public static final String userIndexPath = usersFolderPath + File.separator + "usersIndex.json";
    public static final String mailIndexPath = mailsFolderPath + File.separator + "mailIndex.json";
    Authentication auth = Authentication.getInstance();

    void createInitialFolders() throws IOException {

        File usersFolder = new File(usersFolderPath);
        usersFolder.mkdirs();
        File usersJson = new File(userIndexPath);
        usersJson.createNewFile();
        //SystemUsersPath = SystemUsers.getAbsolutePath();

        /// create mail
        File mailsFolder = new File(mailsFolderPath);
        mailsFolder.mkdirs();
        File mailsJson = new File(mailIndexPath);
        mailsJson.createNewFile();


    }

    public App() throws IOException {
        createInitialFolders();
        //setDefaultViewOptions();
    }


    public void sendEmails(Mail mail, Queue emails){

        while(!emails.isEmpty()){
            String email = (String)emails.peek();
            if(auth.userExists(email)) {
                sendEmail(mail, email);
            }
            else {
                // TODO: Handle
            }
        }

        return ;
    }

    public void sendEmail(Mail mail, String email){

    }


}