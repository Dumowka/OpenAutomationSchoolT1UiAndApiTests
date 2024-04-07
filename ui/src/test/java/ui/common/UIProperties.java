package ui.common;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:config.properties"
})
public interface UIProperties extends Config {

    @Key("BASE_URI")
    String baseUri();
}
