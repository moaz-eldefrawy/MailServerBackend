package Controllers;

import Services.App;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



@RestController
public class AgmadControllerBta3AgmadMohandesFeMasr {
    /*@PostMapping(
            path = "/",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String handlePostRequest(@RequestBody MultiValueMap<String,String> paramMap) {
        return paramMap.get("name") + " " + paramMap.get("shape");
    }*/

    @GetMapping("/downloadd")
    public ResponseEntity<Resource> download(@RequestParam String mailID, @RequestParam String attachmentName) throws Exception{

        File file = new File(App.mailsFolderPath +  File.separator + mailID + File.separator + attachmentName);
        Path path = Paths.get(file.getAbsolutePath());

        String contentType = Files.probeContentType(path);

        ByteArrayResource resource = null;
        resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @PostMapping("/")
    public void handleFileUpload(@RequestParam("attachments") MultipartFile[] attachments, @RequestParam String mailID) throws IOException {
        String mailFolder = App.mailsFolderPath + File.separator + mailID;
        for (MultipartFile mpfile : attachments){
            System.out.println(mpfile.getOriginalFilename());
            File file = new File(mailFolder + File.separator + mpfile.getOriginalFilename());
            file.createNewFile();
            mpfile.transferTo(file);
        }
    }

}