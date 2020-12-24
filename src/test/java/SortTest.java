import Controllers.MainController;
import Controllers.UserController;
import Services.*;
import com.sun.source.tree.AssertTree;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortTest {

    @Test
    public void test() throws IOException {
        new App();
        //Create user
        String email = "shaka@adel.com", password = "password";
        User u = new User(email, password);
        StorageManager.storeUser(u);


        //Add a bunch of mails
        for (int c = 0;c < 30;c++) {
            Mail mail = new Mail(String.format("sender%d@mail.com", c), //sender
                    String.format("Subject no %d", c),                  //subject
                    new Date(System.currentTimeMillis()),               //date
                    (int) Math.round(Math.random() * 4) + 1);    //random priority
            // (priority <= 0 will throw "Invalid Key", see PriorityQueue.insert()

            StorageManager.storeMail(mail);
            StorageManager.addMailToFolder(mail.getID(), "inbox", email);
        }



        //
    }


}
