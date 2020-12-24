package Controllers;

import Services.App;
import Services.Authentication;
import Services.Mail;
import Services.StorageManager;
import Services.User;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*")
public class MainController {

    private Authentication auth = Authentication.getInstance();
    private ObjectMapper m = new ObjectMapper();



    @PostMapping(path = "/signup")
    public String signUp(@RequestBody String body, HttpServletResponse response) {
        JSONObject json = new JSONObject(body);
        String email = json.getString("email");
        String password = json.getString("password");

        if(auth.signUp(email, password)) {
            String authString = "email=" + email + ";" + "Max-Age=99999999999999";
            response.addHeader("Set-Cookie", authString);
            return "{\"user\": \""+email+"\"}";
        }
        else
            return "kolo sharafanta7";
    }

    @PostMapping(path = "/signin")
    public String signIn(@RequestBody String body, HttpServletResponse response) {
        JSONObject json = new JSONObject(body);
        String email = (String)json.get("email");
        String password = (String)json.get("password");
        System.out.println("email: "+email);
        System.out.println("password: "+password);
        User user = auth.signIn(email, password);

        if (user == null || !user.getPassword().equals(password)) {
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

    @GetMapping(value  = "/test")
    public User testF(){
        User u = StorageManager.retrieveUser("eren@attack.titan");
        return u;
    }


    @PostMapping(value = "/compose")
    public ResponseEntity<String> compose(/*@CookieValue(value = "email", defaultValue = "shaka@adel.com") String email,*/ 
    @RequestParam(value = "files", required = false) MultipartFile[] files, @RequestParam(value = "mail") String jsonMail,
    @RequestParam(value = "receivers") String[] receivers) {
        /*
        // TODO: handle in-valid sender
        if (!mail.sender.equals(email)) {
            return false;
        }*/

        try{
            Mail mail = m.readValue(jsonMail, Mail.class);
            String mailFolder = App.mailsFolderPath + File.separator + mail.getID();
            if(files != null)
                for(MultipartFile mpfile: files)
                    mail.addAttachment(mailFolder + File.separator + mpfile.getOriginalFilename());
            StorageManager.storeMail(mail);
            StorageManager.addMailToFolder(mail.getID(), "sent", mail.getSender());

            for (String rec : receivers) {
                if (auth.userExists(rec)) {
                    StorageManager.addMailToFolder(mail.getID(), "inbox", rec);
                    System.out.println(rec);
                }
            }    
            if(files != null)
            {
                for (MultipartFile mpfile : files){
                    System.out.println(mpfile.getOriginalFilename());
                    File file = new File(mailFolder + File.separator + mpfile.getOriginalFilename());
                    file.createNewFile();
                    mpfile.transferTo(file);
                }
            
            }

            return new ResponseEntity<String>("email sent successfully", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.toString(), HttpStatus.OK);
        }
    }

    @PostMapping(
            path = "/test5", consumes = "application/x-www-form-urlencoded")
    public User createPerson(Car person) {
        return StorageManager.retrieveUser("some2");
    }

    //SignOut is performed in the frontend only
    /*
    @GetMapping(value = "/signout")
    public void signout(@CookieValue(value = "email") String email, HttpServletResponse response,
                        HttpServletRequest request) {
        Cookie cookie = new Cookie("email", "");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
        return;
    }
    */

    @PutMapping("/copy")
    public static boolean addMailToFolder(@RequestBody String body, @CookieValue(value = "email") String email) {
        JSONObject json = new JSONObject(body);
        String id = (String) json.getString("id");
        String folder = json.getString("folder");
        return StorageManager.addMailToFolder(id, folder, email);
    }

    @PutMapping("/move")
    public static boolean MoveMailToFolder(@RequestBody String body, @CookieValue(value = "email") String email) {
        JSONObject json = new JSONObject(body);
        String id = (String) json.getString("id");
        String folderOrigin = json.getString("folderOrigin");
        String folderDest = json.getString("folderDest");
        return StorageManager.MoveMailToFolder(id, folderOrigin, folderDest, email);
    }

    @DeleteMapping("/remove")
    public static boolean removeMailFromFolder(@RequestBody String body, @CookieValue(value = "email") String email) {
        JSONObject json = new JSONObject(body);
        String id = (String) json.getString("id");
        String folder = json.getString("folder");
        return StorageManager.removeMailFromFolder(id, folder, email);
    }

}
