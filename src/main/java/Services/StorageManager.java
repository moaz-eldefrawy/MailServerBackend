package Services;

import java.io.File;
import java.util.*;


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

        FileManager.writeToJSONFile(mail, mailPath + File.separator + "mail");
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
        System.out.println(user.getFolders().get(folderName).get(0));
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
        User user = StorageManager.retrieveUser(email);
        System.out.println("Fetched user: " + user.getEmail());
        return getUserMails( user, folderName );
    }

    public static Mail getUserMailById(String userEmail, String id, String folderName){
        if(id == null)
            return null;
        User user =  StorageManager.retrieveUser(userEmail);
        ArrayList<String> folder = user.getFolders().get(folderName);

        for (int i = 0; i < folder.size(); i++) {
            if(id.equals(folder.get(i)))
                return StorageManager.getMail(id);
        }
        return null;
    }

    public static ArrayList<Mail> getUserMails(User user, String folderName) {
        ArrayList<String> folder = user.getFolders().get(folderName);
        ArrayList<Mail> mails = new ArrayList<Mail>();
        for (int i = 0; i < folder.size(); i++) {
            System.out.println(folder.get(i));
            mails.add(StorageManager.getMail( (String)(folder.get(i))));
        }
        return mails;
    }

    /**
     *
     * @param mails
     * @param sortType  "priority", "default", "subject", "sender", "body"
     *
     *      sorts the emails in place
     */
    public static void sortMails (ArrayList<Mail> mails, String sortType){
        if (sortType.equals("priority")){
            Sort.priority(mails);
            return;
        }
        Sort.iterativeQuickSort(mails, sortType);
    }

    final static Integer mailsPerPage=10;
    public static ArrayList<Mail> getPage (ArrayList<Mail> mails, int pageNumber){
        pageNumber--;
        return new ArrayList<Mail>(mails.subList(pageNumber * mailsPerPage, pageNumber * (mailsPerPage + 1)));
    }

    public static boolean removeFolder(User user, String folderName){
        HashMap<String, ArrayList<String>> folders = user.getFolders();

        //folder name is from the main folders or doesn't exist in the user's folders
        if (App.mainFolders.contains(folderName.toLowerCase()) || !folders.containsKey(folderName.toLowerCase()))
            return false;

        folders.remove(folderName);
        user.setFolders(folders);
        StorageManager.storeUser(user);
        return true;
    }

    public static boolean addFolder(User user, String folderName){
        HashMap<String, ArrayList<String>> folders = user.getFolders();
        //folder name is from the main folders or already exists in the user's folders
        if (App.mainFolders.contains(folderName.toLowerCase()) || folders.containsKey(folderName.toLowerCase()))
            return false;

        folders.put(folderName, new ArrayList<String>());
        user.setFolders(folders);
        StorageManager.storeUser(user);
        return true;
    }

    public static boolean renameFolder(User user, String oldFolderName, String newFolderName){
        HashMap<String, ArrayList<String>> folders = user.getFolders();

        //new or old folder name is from the main folders
        if (App.mainFolders.contains(oldFolderName.toLowerCase()) || App.mainFolders.contains(newFolderName.toLowerCase()))
            return false;

        //old folder name doesn't exist or new folder name exists already in the user's folders
        if (!folders.containsKey(oldFolderName.toLowerCase()) || folders.containsKey(newFolderName.toLowerCase()))
            return false;

        //remove old folder
        ArrayList<String> mails = folders.get(oldFolderName);
        folders.remove(oldFolderName);

        //add new folder
        folders.put(newFolderName, mails);


        user.setFolders(folders);
        StorageManager.storeUser(user);
        return true;
    }

    public static boolean changePassword(User user, String oldPassword, String newPassword){
        if(!user.getPassword().equals(oldPassword))
            return false;

        user.setPassword(newPassword);
        StorageManager.storeUser(user);
        return true;
    }

    public static boolean mailExists(String mailId){
        File file = new File(App.mailsFolderPath + File.separator + mailId);
        return file.exists();
    }



}
