package guru.springframework.reactivemongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication
public class Spring6ReactiveMongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring6ReactiveMongoApplication.class, args);
    }

}
