package guru.springframework.spring6di.services.operatingEnv;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("UAT")
@Service("operatingEnv")
public class OperatingEnvServiceUat implements OperatingEnvService {
    @Override
    public String sayOperatingEnv() {
        return "Hello Everyone From OperatingEnvServiceUat!!!";

    }
}
