package Services;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class StorageManager {


    public static void storeUser(String email, String password)
    {
        User newUser = new User(email, password);
        String userFilePath = App.usersFolderPath + File.separator
                + email;
        FileManager.writeToJSONFile(newUser.toJSON(), userFilePath);
    }

    public static void storeUser(User u) {
        String userFilePath = App.usersFolderPath + File.separator
                + u.email;
        FileManager.writeToJSONFile(u.toJSON(), userFilePath);
    }

    public static User retrieveUser(String email)
    {
        String userFilePath = App.usersFolderPath + File.separator
                + email;
        User user = new User(FileManager.getJSONObj(userFilePath));
        return user;
    }

    public static void storeMail(Mail mail){
        String mailPath = App.mailsFolderPath + File.separator +
                mail.ID;

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
        Mail mail = new Mail(FileManager.getJSONObj(mailFilePath));
        return mail;
    }

    public static void addMailToFolder(String ID, String folderName, User user){
        ArrayList folder = user.folders.get(folderName);
        folder.add(ID);
        StorageManager.storeUser(user);
    }
    public static void addMailToFolder(String mailID, String folder, String userEmail){
        User user = retrieveUser(userEmail);
        addMailToFolder(mailID,folder, user);
    }

    public static void removeMailFromFolder(String mailID, String folderName, User user){
        ArrayList folder = user.folders.get(folderName);
        folder.remove(mailID);
        StorageManager.storeUser(user);
    }
    public static void removeMailFromFolder(String ID, String folder, String email){
        User user = retrieveUser(email);
        addMailToFolder(ID,folder, user);
    }

    public static ArrayList<Mail> getUserMails(String email, String folderName){
        return getUserMails( StorageManager.retrieveUser(email), folderName );
    }

    public static ArrayList<Mail> getUserMails(User user, String folderName){
        ArrayList folder = user.folders.get(folderName);
        ArrayList <Mail> mails = new ArrayList<Mail>();
        for(int i=0; i<folder.size(); i++){
            mails.add( StorageManager.getMail( (UUID)folder.get(i) ) );
        }
        return mails;
    }


}
