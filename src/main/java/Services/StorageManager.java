package Services;

import java.io.File;
import java.util.UUID;

public class StorageManager {


    public static void storeUser(String email, String password)
    {
        User newUser = new User(email, password);
        String userFilePath = App.usersFolderPath + File.separator
                + email;
        FileManager.writeToJSONFile(newUser.toJSON(), userFilePath);
    }

    public static void storeUser(User u)
    {
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

/*
    a bunch of email functions
    public void store(String userPath, String folder) {
        setMailFolderPath(userPath, folder);
        addToIndexFile();
        try{
            createMailFolder();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void addToIndexFile()
    {
        Index.writeToIndexFile(this.basicInfo);
    }
    public void setMailFolderPath(String userPath,String folder)
    {
        basicInfo.mailFolderPath=userPath+ File.separator+folder+File.separator+this.basicInfo.ID;
        Index.IndexFilePath=userPath+File.separator+folder+File.separator+"index.txt";
    }

    public void createMailFolder() throws IOException
    {
        File mailFolder=new File(this.basicInfo.mailFolderPath);
        mailFolder.mkdirs();
        File textFile =new File(mailFolder.getAbsolutePath()+File.separator+"text.txt");
        textFile.createNewFile();
        FileManager.writeToFile(this.bodyText,textFile.getAbsolutePath());


        // store attachments
        if(basicInfo.attachements == null)
            return;
        for(int i=0; i<basicInfo.attachements.size(); i++) {
            String attachment = (String) basicInfo.attachements.get(i);
            attachment = Attachment.store(attachment);
            basicInfo.attachements.set(i, attachment);
        }

    }*/

}
