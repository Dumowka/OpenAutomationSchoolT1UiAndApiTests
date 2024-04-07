package api.common;

import org.aeonbits.owner.ConfigFactory;

public class ConfigProperties {

    private static volatile UIProperties instance;

    public static UIProperties getInstance() {
        if (instance == null) {
            synchronized (UIProperties.class) {
                if (instance == null) {
                    instance = ConfigFactory.create(UIProperties.class);
                }
            }
        }
        return instance;
    }
}
