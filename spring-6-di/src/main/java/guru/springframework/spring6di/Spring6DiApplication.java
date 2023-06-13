package guru.springframework.spring6di;

import guru.springframework.spring6di.controller.MyController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Spring6DiApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Spring6DiApplication.class, args);

        MyController controller = ctx.getBean(MyController.class);

        System.out.println("In Main Method");

        System.out.println(controller.sayHello());
    }

}
/*
Purpose to simulate using profiles in multiple operating environments -dev, qa, uat, prod

Development, Quality Assurance, User Acceptance Testing, Production

Create faux Controller to return a string of datasource

Create Service to get datasource string

Create implementations to return dev, qa, uat, or prod

Setup profiles for each

Configure so dev environment is the default
 */