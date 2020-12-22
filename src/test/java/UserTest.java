import Services.StorageManager;
import Services.User;
import org.junit.jupiter.api.Test;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {


    @Test
    void toJSONandEquals_Test() {
        User user1 = new User("some","some");
        User user2 = new User("some","some");
        user1.folders.get("inbox").add( "1" );
        user2.folders.get("inbox").add( "1" );
        user1.folders.get("inbox").add( "123123" );
        assertFalse(user1.equals(user2));
        user2.folders.get("inbox").add( "123123" );
        assertTrue(user1.equals(user2));

        StorageManager.storeUser(user1);
        User user3 = StorageManager.retrieveUser("some");
        assertTrue(user3.equals(user1));
    }
}