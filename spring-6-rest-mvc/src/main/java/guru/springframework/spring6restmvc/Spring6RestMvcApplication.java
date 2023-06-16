package guru.springframework.spring6restmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Spring6RestMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring6RestMvcApplication.class, args);
    }

}
/*Populate database with test data

Challenge - Use same method as shown previously in course

Okay to emulate map initialization - DO NOT SET ID and Version

Initialize data for both Beer and Customer entities

Alter repository tests to verify data initialization

 */