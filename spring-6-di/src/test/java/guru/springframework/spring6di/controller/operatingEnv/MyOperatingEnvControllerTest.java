package guru.springframework.spring6di.controller.operatingEnv;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"DEV", "default"})
//@ActiveProfiles({"PROD","default"})
//@ActiveProfiles({"QA","default"})
//@ActiveProfiles({"UAT","default"})
@SpringBootTest
class MyOperatingEnvControllerTest {

    @Autowired
    MyOperatingEnvController myOperatingEnvController;

    @Test
    void sayOperatingEnv() {
        System.out.println(myOperatingEnvController.sayOperatingEnv());
    }
}