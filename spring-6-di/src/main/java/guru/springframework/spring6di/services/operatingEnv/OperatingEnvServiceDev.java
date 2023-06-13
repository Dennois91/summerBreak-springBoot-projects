package guru.springframework.spring6di.services.operatingEnv;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"DEV", "default"})
@Service("operatingEnv")
public class OperatingEnvServiceDev implements OperatingEnvService {
    @Override
    public String sayOperatingEnv() {
        return "Hello Everyone From OperatingEnvServiceDev!!!";
    }
}
