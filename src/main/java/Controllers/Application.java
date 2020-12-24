package Controllers;

import Services.App;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {
        App app = new App();
        SpringApplication.run(Application.class, args);
    }


}