package api.common;

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

    @Key("REGISTER_PATH")
    String registerPath();

    @Key("LOGIN_PATH")
    String loginPath();

    @Key("PRODUCTS_PATH")
    String productsPath();

    @Key("PRODUCT_BY_ID_PATH")
    String productByIdPath();

    @Key("CART_PATH")
    String cartPath();

    @Key("CART_BY_ID_PATH")
    String cartByIdPath();
}
