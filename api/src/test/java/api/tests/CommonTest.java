package api.tests;

import api.common.ConfigProperties;
import api.common.UIProperties;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import static api.common.ApiClient.getAccessToken;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommonTest {

    protected static final UIProperties properties = ConfigProperties.getInstance();

    protected static String accessToken;

    @BeforeAll
    static void globalBeforeAllTest() {
        RestAssured.replaceFiltersWith(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.baseURI = properties.baseUri();
        accessToken = getAccessToken().getAccessToken();
    }
}
