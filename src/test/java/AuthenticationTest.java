
import Services.App;
import Services.Authentication;
import Services.FileManager;
import Services.User;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationTest {
        
    @Test
    void signUpAndSignIn() throws IOException {
        App app =  new App();
        Authentication auth = Authentication.getInstance();
        FileManager.deleteDir(new File(App.usersFolderPath +
                File.separator + "random@gmail.com.json"));
        // TODO: add json to (gmail.com)
        boolean ok = auth.signUp("random@gmail.com",
                "ok123");
        assertEquals(ok,true);
        ok = ok = auth.signUp("random@gmail.com",
                "ok123");
        assertEquals(ok,false);
        User user = auth.signIn("random@gmail.com",
                "Password");
        assertEquals(user, null);
        user = auth.signIn("random@gmail.com",
                "ok123");
        assertNotEquals(user, null);
    }

    @Test
    void signIn() {

    }
}
