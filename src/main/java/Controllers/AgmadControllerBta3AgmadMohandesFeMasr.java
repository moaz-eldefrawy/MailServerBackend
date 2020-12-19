package Controllers;

import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
public class AgmadControllerBta3AgmadMohandesFeMasr {
    @PostMapping(
            path = "/",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String handlePostRequest(@RequestBody MultiValueMap<String,String> paramMap) {
        return paramMap.get("name") + " " + paramMap.get("shape");
    }

    @GetMapping("/")
    public String haha(){
        return "hello world";
    }
}
