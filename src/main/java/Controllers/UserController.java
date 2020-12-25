package Controllers;

import Filters.AbstractFilter;
import Filters.FilterFactory;
import Services.Mail;
import Services.StorageManager;
import Services.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * controls requests pertaining to the profile of a single user (name,contacts,folders)
 * */
@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class UserController {

    @GetMapping(value = "/folders/{folderName}")
    public ArrayList<Mail> listMails(@CookieValue(value = "email") String email,
                                     @PathVariable String folderName,
                                     @RequestParam(name = "sortType", defaultValue = "default") String sortType,
                                     @RequestParam(name = "page", defaultValue = "1") Integer page,
                                     @RequestParam(name = "filterType") String filterType,
                                     @RequestParam(name = "filterValue") String filterValue) {


        ArrayList<Mail> mails = StorageManager.getUserMails(email, folderName);
        //return mails;
        //sorts in place
        StorageManager.sortMails(mails, sortType);

        //filter
        AbstractFilter filter = FilterFactory.getFilter(filterType);
        if(filter != null){
            mails = filter.meetCriteria(mails, filterValue);
        }
        return StorageManager.getPage(mails, page);
        //return "ok";
    }

    @PutMapping("/updateContacts")
    public void updateContacts(@CookieValue(value = "email") String email,
                                  @RequestParam(value = "contacts") String contactsString) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        HashMap<String, String> map = objMapper.readValue(contactsString, HashMap.class);
        User user = StorageManager.retrieveUser(email);
        user.setContacts(map);
        StorageManager.storeUser(user);
        return ;
    }

    @GetMapping("/getContacts")
    public HashMap<String,String> updateContacts(@CookieValue(value = "email") String email) throws JsonProcessingException {
        User user = StorageManager.retrieveUser(email);
        return user.getContacts();
    }


    @PutMapping("/copy")
    public static boolean addMailToFolder(@RequestBody String body, @CookieValue(value = "email") String email) {
        JSONObject json = new JSONObject(body);
        String id = json.getString("id");
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


    @GetMapping("/getFolders")
    public static String[] getFolders(@CookieValue(value="email") String email){
        User user = StorageManager.retrieveUser(email);
        return user.getFolders().keySet().stream().toArray(String[]::new);
    }

    @DeleteMapping("/removeFolder")
    public static boolean removeFolder(@RequestBody String body, @CookieValue(value = "email") String email){
        JSONObject json = new JSONObject(body);

        String folder = json.getString("folder");
        User user = StorageManager.retrieveUser(email);

        return StorageManager.removeFolder(user, folder.toLowerCase());
    }

    @PostMapping("/addFolder")
    public static boolean addFolder(@RequestBody String body, @CookieValue(value = "email") String email){
        JSONObject json = new JSONObject(body);

        String folder = json.getString("folder");
        User user = StorageManager.retrieveUser(email);

        return StorageManager.addFolder(user, folder.toLowerCase());
    }

    @PostMapping("/renameFolder")
    public static boolean renameFolder(@RequestBody String body, @CookieValue(value = "email") String email){
        JSONObject json = new JSONObject(body);

        String oldFolder = json.getString("oldFolder");
        String newFolder = json.getString("newFolder");
        User user = StorageManager.retrieveUser(email);

        return StorageManager.renameFolder(user, oldFolder.toLowerCase(), newFolder.toLowerCase());
    }

    @PostMapping("/changePassword")
    public static boolean changePassword(@RequestBody String body, @CookieValue(value = "email") String email){
        JSONObject json = new JSONObject(body);

        String oldPassword = json.getString("oldPassword");
        String newPassword = json.getString("newPassword");
        User user = StorageManager.retrieveUser(email);

        return StorageManager.changePassword(user, oldPassword, newPassword);
    }
}
