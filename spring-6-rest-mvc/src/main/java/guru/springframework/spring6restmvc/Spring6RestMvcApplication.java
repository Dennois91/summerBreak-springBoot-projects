package guru.springframework.spring6restmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Spring6RestMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring6RestMvcApplication.class, args);
    }

}
/*Assignment - Handle HTTP Post for Create new Customer

Create Controller method to handle post

Update Request Mapping

Save to in-memory hash map

Return 201 status with location of created customer object

 */