package guru.springframework.spring6di.controller.operatingEnv;

import guru.springframework.spring6di.services.operatingEnv.OperatingEnvService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class MyOperatingEnvController {

    private final OperatingEnvService operatingEnvService;

    public MyOperatingEnvController(@Qualifier("operatingEnv") OperatingEnvService operatingEnvService) {
        this.operatingEnvService = operatingEnvService;
    }

    public String sayOperatingEnv(){
        return operatingEnvService.sayOperatingEnv();
    }
}
//Create implementations to return dev, qa, uat, or prod