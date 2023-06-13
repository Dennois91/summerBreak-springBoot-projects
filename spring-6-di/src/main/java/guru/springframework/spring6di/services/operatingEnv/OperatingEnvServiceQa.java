package guru.springframework.spring6di.services.operatingEnv;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("QA")
@Service("operatingEnv")
public class OperatingEnvServiceQa implements OperatingEnvService {

    @Override
    public String sayOperatingEnv() {
        return "Hello Everyone From OperatingEnvServiceQa!!!";
    }
}
