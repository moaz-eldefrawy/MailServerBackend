package Controllers;


import Services.Authentication;
import Services.StorageManager;
import Services.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "*")
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
    public User signIn(String email, String password, HttpServletResponse response) {
        User user = new User(email,password);
        String authString = "email="+email + ";" + "Max-Age=99999999999999";
        String temp = "Bearer " + "email="+email;
        response.addHeader( "Set-Cookie", authString);
        response.addHeader( "Authorization", temp);
        Authentication auth = Authentication.getInstance();
        return auth.signIn(email,password);
    }

    @GetMapping(value="/inbox")
    public String ok2(@CookieValue(value="email")String email){

        return "ok";
    }
}
