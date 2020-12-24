package Controllers;

import Services.App;
import Services.Authentication;
import Services.Mail;
import Services.StorageManager;
import Services.User;

import org.json.JSONObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class MainController {

    private Authentication auth = Authentication.getInstance();
    private ObjectMapper m = new ObjectMapper();



    @PostMapping(path = "/signup")
    public String signUp(@RequestBody String body, HttpServletResponse response) {
        JSONObject json = new JSONObject(body);
        String email = json.getString("email");
        String password = json.getString("password");

        if(auth.signUp(email, password)) {
            Cookie cookie = new Cookie("email", email);
            cookie.setMaxAge(999999999);
            response.addCookie(cookie);
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
            return "error";
        } else {
            Cookie cookie = new Cookie("email", email);
            cookie.setMaxAge(999999999);
            response.addCookie(cookie);
            System.out.println(cookie.toString());
            return "{\"user\": \""+email+"\"}";
        }
        
    }



    @GetMapping(value = "/getMail")
    public Mail getMail(@CookieValue(value = "email") String email,
    @RequestParam(name = "emailId") String emailId, @RequestParam(name = "folderName") String folderName) {
        return StorageManager.getUserMailById(email, emailId, folderName);
    }

    @PostMapping(value = "/saveDraft")
    public ResponseEntity<String> saveDraft(@CookieValue(value = "email") String userEmail, 
    @RequestParam(value = "files", required = false) MultipartFile[] files,
    @RequestParam(value = "mail") String jsonMail,
    @RequestParam(value = "compose") Boolean isCompose,
    @RequestParam(value = "receivers")String[] receivers) {
        
        try{
            Mail newDraft = m.readValue(jsonMail, Mail.class);
            Mail oldDraft = StorageManager.getUserMailById(userEmail, newDraft.getID(), "drafts");
            if(oldDraft != null){
                newDraft.setID(oldDraft.getID());
            }
            else
                newDraft.genRandomID();
            
            String mailFolder = App.mailsFolderPath + File.separator + newDraft.getID();

            File oldDraftFolder = new File(mailFolder);
            File[] filesInFolder = oldDraftFolder.listFiles();

            if(filesInFolder != null) {
                ArrayList<String> fileNames = newDraft.getAttachments();
                for(File f: filesInFolder)
                    if(fileNames == null || fileNames.size() == 0 || !fileNames.contains(f.getName()))
                        f.delete();
            }
            if(files != null)
                for(MultipartFile mpfile: files)
                    newDraft.addAttachment(mpfile.getOriginalFilename());

            StorageManager.storeMail(newDraft);
            if(oldDraft == null)
                StorageManager.addMailToFolder(newDraft.getID(), "drafts", newDraft.getSender());

            if(files != null)
            {
                for (MultipartFile mpfile : files){
                    System.out.println(mpfile.getOriginalFilename());
                    File file = new File(mailFolder + File.separator + mpfile.getOriginalFilename());
                    file.createNewFile();
                    mpfile.transferTo(file);
                }
            
            }
            System.out.println(isCompose);
            if(isCompose) {
                StorageManager.addMailToFolder(newDraft.getID(), "sent", newDraft.getSender());
                StorageManager.removeMailFromFolder(newDraft.getID(), "drafts", newDraft.getSender());
                System.out.println(newDraft.getSender());
                for (String rec : receivers) {
                    if (auth.userExists(rec)) {
                        StorageManager.addMailToFolder(newDraft.getID(), "inbox", rec);
                        System.out.println(rec);
                    }
                }    
    
            }

            return new ResponseEntity<String>(newDraft.getID(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.toString(), HttpStatus.OK);
        }
    }


    @GetMapping(value  = "/test")
    public User testF(){
        User u = StorageManager.retrieveUser("eren@attack.titan");
        return u;
    }


    @PostMapping(value = "/compose")
    public ResponseEntity<String> compose(@CookieValue(value = "email") String email, 
    @RequestParam(value = "files", required = false) MultipartFile[] files, @RequestParam(value = "mail") String jsonMail,
    @RequestParam(value = "receivers") String[] receivers) {
        
        try{
            Mail mail = m.readValue(jsonMail, Mail.class);

            if (!mail.getSender().equals(email)) {
                return new ResponseEntity<String>("unauthorised access", HttpStatus.BAD_REQUEST);
            }

            String mailFolder = App.mailsFolderPath + File.separator + mail.getID();
            if(files != null)
                for(MultipartFile mpfile: files)
                    mail.addAttachment(mpfile.getOriginalFilename());
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
    
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadAttachment(@CookieValue(value = "email")String email,
        @RequestParam(name = "emailId") String emailId, @RequestParam(name = "fileName") String fileName){
        // TODO: Auth
        System.out.println("download: " + email);
        try
        {
            String attachmentDir = App.mailsFolderPath + File.separator + emailId;
            File attachmentFile = new File(attachmentDir, fileName);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(attachmentFile));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", attachmentFile.getName()));
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            ResponseEntity<Resource> responseEntity = ResponseEntity.ok().headers(headers).contentLength(attachmentFile.length())
            .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);

            return responseEntity;
        }catch(Exception e){
            return null;
        }
    }




}
