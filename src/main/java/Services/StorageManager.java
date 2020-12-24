package Services;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;



/*

    includes database queries
 */
public class StorageManager {

    public static void storeUser(String email, String password)
    {
        User newUser = new User(email, password);
        String userFilePath = App.usersFolderPath + File.separator
                + email;
        FileManager.writeToJSONFile(newUser, userFilePath);
    }

    public static void storeUser(User u) {
        String userFilePath = App.usersFolderPath + File.separator
                + u.getEmail();
        try {
            FileManager.writeToJSONFile(u, userFilePath);
        }catch(Exception e) {

        }
    }

    public static User retrieveUser(String email)
    {
        String userFilePath = App.usersFolderPath + File.separator
                + email;
        User user = (User)FileManager.getJSONObj(userFilePath, 0);
        return user;
    }

    public static void storeMail(Mail mail){
        String mailPath = App.mailsFolderPath + File.separator +
                mail.getID();

        File mailFolder = new File(mailPath);
        mailFolder.mkdirs();

        FileManager.writeToJSONFile(mail.toJSON(), mailPath + File.separator + "mail");
    }

    public static Mail getMail(UUID ID){
        return getMail(ID.toString());
    }

    public static Mail getMail(String ID){
        String mailFolderPath = App.mailsFolderPath + File.separator +
                ID;
        String mailFilePath = mailFolderPath + File.separator
                + "mail";
        Mail mail = (Mail)FileManager.getJSONObj(mailFilePath, 1);
        return mail;
    }

    public static boolean addMailToFolder(String mailID, String folderName, String userEmail){
        if(!mailExists(mailID))
            return false;
        User user = retrieveUser(userEmail);
        ArrayList<String> folder = user.getFolders().get(folderName);
        if(folder == null)
            return false;
        folder.add(mailID);
        StorageManager.storeUser(user);
        return  true;
    }

    public static boolean removeMailFromFolder(String mailID, String folderName, String email){
        if(!mailExists(mailID))
            return false;
        User user = retrieveUser(email);
        if( user.getFolders().get(folderName) == null)
            return false;
        user.getFolders().get(folderName).remove(mailID);
        StorageManager.storeUser(user);
        return true;

    }

    // NOTE: will remove even if it one the destination folder doesn't exist
    public static boolean MoveMailToFolder(String ID, String folderOrigin, String folderDest, String email){
        if(!mailExists(ID))
            return false;
        return removeMailFromFolder(ID, folderOrigin, email) &&
         addMailToFolder(ID, folderDest, email);
    }

    public static ArrayList<Mail> getUserMails(String email, String folderName){
        return getUserMails( StorageManager.retrieveUser(email), folderName );
    }

    public static ArrayList<Mail> getUserMails(User user, String folderName) {
        ArrayList<String> folder = user.getFolders().get(folderName);
        ArrayList<Mail> mails = new ArrayList<Mail>();
        for (int i = 0; i < folder.size(); i++) {
            mails.add(StorageManager.getMail( (String)(folder.get(i))));
        }
        return mails;
    }


    public static boolean mailExists(String mailId){
        File file = new File(App.mailsFolderPath + File.separator + mailId);
        return file.exists();
    }

}
