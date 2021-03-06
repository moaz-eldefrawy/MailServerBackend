import Services.*;
import com.sun.source.tree.AssertTree;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageManagerTest {

    String currentFolder = System.getProperty("user.dir") + File.separator +
            "src" + File.separator +
            "test" + File.separator +
            "java";

    String dummyFolderPath = currentFolder + File.separator + "testing_folder";
    File dummyFolder = new File(dummyFolderPath);
    String dummyFilepath =  dummyFolderPath + File.separator + "testText.txt";
    File dummyFile = new File(dummyFilepath);

    @Test
    public void mailQueries() throws IOException {
        new App();
        //User write read
        String email = "shaka@adel.com", password = "password";
        User u = new User(email, password);
        StorageManager.storeUser(u);
        assertTrue(userEquals(u, StorageManager.retrieveUser(email)));

        //Mail write read
        Mail mail =  new Mail("sender@mail1.com", "Subject 1 ysta", new Date(System.currentTimeMillis()), 1);
        StorageManager.storeMail(mail);
        assertTrue(mailEquals(mail, StorageManager.getMail(mail.getID())));

        mail =  new Mail("sender@mail12.com", "Subject 2 ysta", new Date(System.currentTimeMillis()), 4);
        mail.getAttachments().add("attachment1");
        mail.getAttachments().add("attachment2");
        mail.getAttachments().add("attachment3");
        StorageManager.storeMail(mail);
        assertTrue(mailEquals(mail, StorageManager.getMail(mail.getID())));


        //
    }

    @Test
    public void mailFolderOperations() throws IOException {
        Authentication.getInstance().signUp("some2@gmail.com","some2");
        User user1 = new User("some2@gmail.com","some");
        User user2 = new User("some2@gmail.com","some");
        Mail mail1 =  new Mail("sender@mail12.com", "Subject 2 ysta", new Date(System.currentTimeMillis()), 4);
        mail1.addAttachment("attachment1");
        Mail mail2 =  new Mail("sender@mail12.com", "Subject 3 ysta", new Date(System.currentTimeMillis()), 4);
        mail1.addAttachment("attachment1");
        StorageManager.storeMail(mail1);
        StorageManager.storeMail(mail2);

        user2.getFolders().get("inbox").add( mail1.getID() );
        user2.getFolders().get("sent").add( mail2.getID() );
        StorageManager.storeUser(user1);
        StorageManager.addMailToFolder(mail1.getID(),"inbox","some2@gmail.com");
        StorageManager.addMailToFolder(mail2.getID(),"sent","some2@gmail.com");
        user1 = StorageManager.retrieveUser("some2@gmail.com");
        assertTrue(user1.equals(user2));

        user2.getFolders().get("inbox").remove(mail1.getID());
        user1 = StorageManager.retrieveUser("some2@gmail.com");
        StorageManager.removeMailFromFolder(mail1.getID(),"inbox","some2@gmail.com");
        user1 = StorageManager.retrieveUser("some2@gmail.com");
        assertTrue(user2.equals(StorageManager.retrieveUser("some2@gmail.com")));
    }

    @Test
    public void rename_add_remove_folders(){
        final String userEmail = "random1234@ok.com";

        FileManager.deleteDir(new File(App.usersFolderPath+File.separator+userEmail+".json"));
        assertTrue(Authentication.getInstance().signUp(userEmail, "enter123"));


        User user = StorageManager.retrieveUser(userEmail);
        user.getFolders().put("temp5", new ArrayList<>());
        user.getFolders().put("temp6", new ArrayList<>());
        StorageManager.addFolder( StorageManager.retrieveUser(userEmail) , "temp5" );
        StorageManager.addFolder( StorageManager.retrieveUser(userEmail) , "temp5" );
        StorageManager.addFolder( StorageManager.retrieveUser(userEmail) , "temp5" );
        StorageManager.addFolder( StorageManager.retrieveUser(userEmail) , "temp6" );
        assertTrue(user.equals(StorageManager.retrieveUser(userEmail)));

        user.getFolders().remove("temp5");
        StorageManager.removeFolder( StorageManager.retrieveUser(userEmail) , "temp5" );
        assertTrue(user.equals(StorageManager.retrieveUser(userEmail)));


        //user.getFolders().remove("temp5");
        StorageManager.removeFolder( StorageManager.retrieveUser(userEmail) , "temp5" );
        assertTrue(user.equals(StorageManager.retrieveUser(userEmail)));


        StorageManager.removeFolder( StorageManager.retrieveUser(userEmail) , "inbox" );
        assertTrue(user.equals(StorageManager.retrieveUser(userEmail)));

        String id = UUID.randomUUID().toString();
        Mail mail1 =  new Mail("sender@mail12.com", "Subject 2 ysta", new Date(System.currentTimeMillis()), 4);
        mail1.addAttachment("attachment1");
        StorageManager.storeMail(mail1);
        mail1 = StorageManager.getMail(mail1.getID());
        StorageManager.addMailToFolder(mail1.getID(),"temp6", userEmail);
        StorageManager.renameFolder( StorageManager.retrieveUser(userEmail), "temp6", "newName" );
        user.getFolders().remove("temp6");
        user.getFolders().put("newName", new ArrayList<String>());
        user.getFolders().get("newName").add(mail1.getID());
        assertTrue(user.equals(StorageManager.retrieveUser(userEmail)));

    }
    
    boolean userEquals(User a, User b){
        // Email address
        if (!a.getEmail().equals(b.getEmail()))
            return false;

        // Password
        if (!a.getPassword().equals(b.getPassword()))
            return false;

        // Contacts
        if (a.getContacts().size() != b.getContacts().size())
            return  false;
/*
        for (User aContact : a.getContacts()){
            for (User bContact : b.getContacts()){
                if (!userEquals(aContact, bContact))
                    return false;
            }
        }*/

        // Mails
        if (a.getMails().size() != b.getMails().size())
            return  false;

        for (Mail aMail : a.getMails()){
            for (Mail bMail : b.getMails()){
                if (!mailEquals(aMail, aMail))
                    return false;
            }
        }

        return true;
    }

    boolean mailEquals(Mail a, Mail b){
        if (!a.getSender().equals(b.getSender()))
            return false;
        if (!a.getSubject().equals(b.getSubject()))
            return false;
        if (!a.getDate().equals(b.getDate()))
            return false;
        if (!a.getPriority().equals(b.getPriority()))
            return false;
        if (!a.getStatus().equals(b.getStatus()))
            return false;
        if (!a.getAttachments().equals(b.getAttachments()))
            return false;
        if (!a.getID().equals(b.getID()))
            return false;
        return a.getBodyText().equals(b.getBodyText());
    }


}
