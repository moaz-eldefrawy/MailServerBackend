package Controllers;

import Services.Authentication;
import Services.Mail;
import Services.StorageManager;
import Services.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*")
public class MainController {


    @PostMapping(
            path = "/signup", consumes = "application/x-www-form-urlencoded")
    public boolean signUp(String email, String password) {
        User user = new User(email, password);
        Authentication auth = Authentication.getInstance();
        return auth.signUp(email, password);
    }

    @PostMapping(
            path = "/signin", consumes = "application/x-www-form-urlencoded")
    public User signIn(String email, String password, HttpServletResponse response) {
        Authentication auth = Authentication.getInstance();
        User user = auth.signIn(email, password);
        if (user == null) {
            response.setStatus(401);
        } else {
            String authString = "email=" + email + ";" + "Max-Age=99999999999999";
            String temp = "Bearer " + "email=" + email;
            response.addHeader("Set-Cookie", authString);
        }
        return user;
    }

    //TODO: remove default value

    @GetMapping(value = "/inbox")
    public ArrayList<Mail> listMails(@CookieValue(value = "email", defaultValue = "shaka@adel.com") String email,
                                     @RequestParam(value = "folderName", defaultValue = "inbox") String folderName) {
        System.out.println(email);
        return StorageManager.getUserMails(email, folderName);
        //return "ok";
    }

    @PostMapping(value = "/compose", consumes = "application/x-www-form-urlencoded")
    public boolean compose(@CookieValue(value = "email", defaultValue = "shaka@adel.com") String email, Mail mail, String receivers) {
        System.out.println(receivers);
        System.out.println(mail.subject);
        if (email == null)
            email = "shaka@adel.com";

        System.out.println(email);
        // store email
        StorageManager.storeMail(mail);
        if (!mail.sender.equals(email)) {
            // TODO: handle in-valid sender
            return false;
        }
        // add it to sent folder

        StorageManager.addMailToFolder(mail.ID, "sent", email);

        String[] receiver = receivers.split(",", 0);

        Authentication auth = Authentication.getInstance();
        for (String rec : receiver) {
            if (auth.userExists(rec)) {
                StorageManager.addMailToFolder(mail.ID, "inbox", rec);
                System.out.println(rec);
            } else {
                // TODO:
            }
        }
        return true;
    }

    @PostMapping(
            path = "/test5", consumes = "application/x-www-form-urlencoded")
    public User createPerson(Car person) {
        return StorageManager.retrieveUser("some2");
    }

    @GetMapping(value = "/signout")
    public void signout(@CookieValue(value = "email") String email, HttpServletResponse response,
                        HttpServletRequest request) {
        Cookie cookie = new Cookie("email", "");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
        return;
    }

    @PutMapping("/copy")
    public static boolean addMailToFolder(String id, String folder, @CookieValue(value = "email") String email) {
        return StorageManager.addMailToFolder(id, folder, email);
    }

    @PutMapping("/move")
    public static boolean MoveMailToFolder(String id, String folderOrigin, String folderDest, @CookieValue(value = "email") String email) {
        return StorageManager.MoveMailToFolder(id, folderOrigin, folderDest, email);
    }

    @DeleteMapping("/remove")
    public static boolean removeMailFromFolder(String id, String folder, @CookieValue(value = "email") String email) {
        return StorageManager.removeMailFromFolder(id, folder, email);
    }

}
