package api.common;

import api.ConfigProperties;
import api.models.AccessToken;
import api.models.request.UserLoginRequest;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpHeaders;

import static io.restassured.RestAssured.given;

public class ApiClient {

    private static final ConfigProperties config = ConfigProperties.getInstance();

    public static ValidatableResponse commonGet(String accessToken, int statusCode, String path, Object... pathParams) {
        return given()
                .auth()
                .oauth2(accessToken)
                .header(HttpHeaders.ACCEPT, "application/json")
                .when().get(path, pathParams).then()
                .assertThat().statusCode(statusCode);
    }

    public static ValidatableResponse commonGetWithoutToken(int statusCode, String path, Object... pathParams) {
        return given()
                .header(HttpHeaders.ACCEPT, "application/json")
                .when().get(path, pathParams).then()
                .assertThat().statusCode(statusCode);
    }

    public static ValidatableResponse commonGetWithoutCheckStatus(String accessToken, String path, Object... pathParams) {
        return given()
                .auth()
                .oauth2(accessToken)
                .header(HttpHeaders.ACCEPT, "application/json")
                .when().get(path, pathParams).then();
    }

    public static ValidatableResponse commonGetWithoutTokenAndCheckStatus(String path, Object... pathParams) {
        return given()
                .header(HttpHeaders.ACCEPT, "application/json")
                .when().get(path, pathParams).then();
    }

    public static ValidatableResponse commonPost(String accessToken, int statusCode, Object body, String path) {
        return given()
                .auth()
                .oauth2(accessToken)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(body)
                .when().post(path).then()
                .assertThat().statusCode(statusCode);
    }

    public static ValidatableResponse commonPostWithoutToken(int statusCode, Object body, String path) {
        return given()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(body)
                .when().post(path).then()
                .assertThat().statusCode(statusCode);
    }

    public static ValidatableResponse commonPostWithoutCheckStatus(String accessToken, Object body, String path) {
        return given()
                .auth()
                .oauth2(accessToken)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(body)
                .when().post(path).then();
    }

    public static ValidatableResponse commonPostWithoutTokenAndCheckStatus(Object body, String path) {
        return given()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(body)
                .when().post(path).then();
    }

    public static ValidatableResponse commonPut(String accessToken, int statusCode, Object body, String path, Object... pathParams) {
        return given()
                .auth()
                .oauth2(accessToken)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(body)
                .when().put(path, pathParams).then()
                .assertThat().statusCode(statusCode);
    }

    public static ValidatableResponse commonPutWithoutCheckStatus(String accessToken, Object body, String path, Object... pathParams) {
        return given()
                .auth()
                .oauth2(accessToken)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(body)
                .when().put(path, pathParams).then();
    }

    public static ValidatableResponse commonPutWithoutTokenAndCheckStatus(Object body, String path, Object... pathParams) {
        return given()
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(body)
                .when().put(path, pathParams).then();
    }

    public static ValidatableResponse commonDelete(String accessToken, int statusCode, String path, Object... pathParams) {
        return given()
                .auth()
                .oauth2(accessToken)
                .when().delete(path, pathParams).then()
                .assertThat().statusCode(statusCode);
    }

    public static ValidatableResponse commonDeleteWithoutCheckStatus(String accessToken, String path, Object... pathParams) {
        return given()
                .auth()
                .oauth2(accessToken)
                .when().delete(path, pathParams).then();
    }

    public static ValidatableResponse commonDeleteWithoutTokenAndCheckStatus(String path, Object... pathParams) {
        return given().when().delete(path, pathParams).then();
    }

    public static AccessToken getAccessToken() {
        return commonPostWithoutToken(
                200,
                new UserLoginRequest("username", "password"),
                config.getConfigParameter("LOGIN_PATH")
        ).extract().as(AccessToken.class);
    }
}
