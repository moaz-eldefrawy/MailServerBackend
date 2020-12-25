import Services.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {


    @Test
    void toJSONandEquals_Test() throws IOException {
        new App();
        Authentication auth = Authentication.getInstance();
        FileManager.deleteDir(new File(App.usersFolderPath + File.separator + "someZc@ok.com.json"));
        assertTrue(auth.signUp("someZc@ok.com","some"));
        User user1 = new User("someZc@ok.com","some");
        User user2 = new User("someZc@ok.com","some");
        user1.getFolders().get("inbox").add( "1" );
        user2.getFolders().get("inbox").add( "1" );
        user1.getFolders().get("inbox").add( "123123" );
        assertFalse(user1.equals(user2));
        user2.getFolders().get("inbox").add( "123123" );
        assertTrue(user1.equals(user2));

        StorageManager.storeUser(user1);
        User user3 = StorageManager.retrieveUser("someZc@ok.com");
        assertTrue(user3.equals(user1));
    }

    @Test
    void Contact() throws IOException {
        new App();
        Authentication auth = Authentication.getInstance();
        FileManager.deleteDir(new File(App.usersFolderPath + File.separator + "someZc@ok.com.json"));
        assertTrue(auth.signUp("someZc@ok.com","some"));
        User user1 = new User("someZc@ok.com","some");
        User user2 = new User("someZc@ok.com","some");
        user1.getFolders().get("inbox").add( "1" );
        user2.getFolders().get("inbox").add( "1" );
        user1.getFolders().get("inbox").add( "123123" );
        assertFalse(user1.equals(user2));
        user2.getFolders().get("inbox").add( "123123" );
        assertTrue(user1.equals(user2));

        StorageManager.storeUser(user1);
        User user3 = StorageManager.retrieveUser("someZc@ok.com");
        assertTrue(user3.equals(user1));

















    }
}