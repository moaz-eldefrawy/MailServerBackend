import Services.*;
import org.junit.Test;

import java.io.File;
import java.util.Date;

import static org.junit.Assert.*;

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
    public void test(){

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
        mail.attachments.add("attachment1");
        mail.attachments.add("attachment2");
        mail.attachments.add("attachment3");
        StorageManager.storeMail(mail);
        assertTrue(mailEquals(mail, StorageManager.getMail(mail.getID())));


        //
    }

    boolean userEquals(User a, User b){
        // Email address
        if (!a.email.equals(b.email))
            return false;

        // Password
        if (!a.password.equals(b.password))
            return false;

        // Contacts
        if (a.contacts.size() != b.contacts.size())
            return  false;

        for (User aContact : a.contacts){
            for (User bContact : b.contacts){
                if (!userEquals(aContact, bContact))
                    return false;
            }
        }

        // Mails
        if (a.mails.size() != b.mails.size())
            return  false;

        for (Mail aMail : a.mails){
            for (Mail bMail : b.mails){
                if (!mailEquals(aMail, aMail))
                    return false;
            }
        }

        return true;
    }

    boolean mailEquals(Mail a, Mail b){
        if (!a.sender.equals(b.sender))
            return false;
        if (!a.subject.equals(b.subject))
            return false;
        if (!a.date.equals(b.date))
            return false;
        if (!a.priority.equals(b.priority))
            return false;
        if (!a.status.equals(b.status))
            return false;
        if (!a.attachments.equals(b.attachments))
            return false;
        if (!a.ID.equals(b.ID))
            return false;
        if (!a.bodyText.equals(b.bodyText))
            return false;

        return true;
    }

}
