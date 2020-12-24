package Controllers;

import Services.Mail;
import Services.StorageManager;
import Services.User;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * controls requests pertaining to the profile of a single user (name,contacts,folders)
 * */
@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class UserController {
    //TODO: remove default value

    @GetMapping(value = "/folders/{folderName}")
    public ArrayList<Mail> listMails(@CookieValue(value = "email") String email,
                                     @PathVariable String folderName) {
        System.out.println(email);
        return StorageManager.getUserMails(email, folderName);
        //return "ok";
    }

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


    @DeleteMapping("/removeFolder")
    public static boolean removeFolder(@RequestBody String body, @CookieValue(value = "email") String email){
        JSONObject json = new JSONObject(body);

        String folder = json.getString("folder");
        User user = StorageManager.retrieveUser(email);

        return StorageManager.removeFolder(user, folder);
    }

    @PostMapping("/addFolder")
    public static boolean addFolder(@RequestBody String body, @CookieValue(value = "email") String email){
        JSONObject json = new JSONObject(body);

        String folder = json.getString("folder");
        User user = StorageManager.retrieveUser(email);

        return StorageManager.addFolder(user, folder);
    }

    @PostMapping("/renameFolder")
    public static boolean renameFolder(@RequestBody String body, @CookieValue(value = "email") String email){
        JSONObject json = new JSONObject(body);

        String oldFolder = json.getString("oldFolder");
        String newFolder = json.getString("newFolder");
        User user = StorageManager.retrieveUser(email);

        return StorageManager.renameFolder(user, oldFolder, newFolder);
    }
}
