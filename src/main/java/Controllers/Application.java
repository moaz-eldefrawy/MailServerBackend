package Controllers;

import Services.App;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {
        App app = new App();
        SpringApplication.run(Application.class, args);
    }


}