package Controllers;

import Services.Authentication;
import Services.Mail;
import Services.StorageManager;
import Services.User;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.util.ArrayList;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class MainController {

    private Authentication auth = Authentication.getInstance();
    @PostMapping(path = "/signup")
    public String signUp(@RequestBody String body) {
        /*
        JSONObject json = new JSONObject();
        //User user = new User(email, password);
        Boolean userCreated = auth.signUp(body, password);
        if(userCreated) {
            return "kolo mia mia";
        }*/
        
        JSONObject json = new JSONObject(body);
        String email = (String)json.get("email");
        String password = (String)json.get("password");

        JSONObject resp = new JSONObject();
        resp.put("token", "gdklfgjdflgjdflkgldfh");
        resp.put("user", "ahmed bahgat");
        auth.signUp(email, password);
        return resp.toString();
    }

    @PostMapping(path = "/signin")
    public String signIn(@RequestBody String body, HttpServletResponse response) {
        JSONObject json = new JSONObject(body);
        String email = (String)json.get("email");
        String password = (String)json.get("password");
        System.out.println("email: "+email);
        System.out.println("password: "+password);
        User user = auth.signIn(email, password);

        if (user == null || !user.password.equals(password)) {
            response.setStatus(401);
        } else {
            String authString = "email=" + email + ";" + "Max-Age=99999999999999";
            response.addHeader("Set-Cookie", authString);
        }
        return "{\"user\": \""+email+"\"}";
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
