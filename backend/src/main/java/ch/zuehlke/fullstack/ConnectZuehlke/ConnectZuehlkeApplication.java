package ch.zuehlke.fullstack.ConnectZuehlke;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ConnectZuehlkeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConnectZuehlkeApplication.class, args);
    }

}

