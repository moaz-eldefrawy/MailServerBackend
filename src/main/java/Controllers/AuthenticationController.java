package Controllers;


import Services.Authentication;
import Services.StorageManager;
import Services.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @PostMapping(
            path = "/signup", consumes = "application/x-www-form-urlencoded")
    public boolean signUp(String email, String password) {
        User user = new User(email,password);
        Authentication auth = Authentication.getInstance();
        return auth.signUp(email,password);
    }

    @PostMapping(
            path = "/signin", consumes = "application/x-www-form-urlencoded")
    public User signIn(String email, String password) {
        User user = new User(email,password);
        Authentication auth = Authentication.getInstance();
        return auth.signIn(email,password);
    }

}
