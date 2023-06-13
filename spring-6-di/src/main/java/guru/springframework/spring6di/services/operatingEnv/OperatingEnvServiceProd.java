package guru.springframework.spring6di.services.operatingEnv;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("PROD")
@Service("operatingEnv")
public class OperatingEnvServiceProd implements OperatingEnvService {
    @Override
    public String sayOperatingEnv() {
        return "Hello Everyone From OperatingEnvServiceProd!!!";

    }
}
