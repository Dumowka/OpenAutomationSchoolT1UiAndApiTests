package api.tests.user;

import api.models.request.UserLoginRequest;
import api.models.response.MessageDto;
import api.tests.CommonTest;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static api.common.ApiClient.commonPostWithoutToken;
import static api.common.ApiClient.commonPostWithoutTokenAndCheckStatus;
import static api.tests.user.LoginTest.TEST_NAME;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(TEST_NAME)
class LoginTest extends CommonTest {

    protected final static String TEST_NAME = "Авторизация";

    private static final String PATH = config.getConfigParameter("LOGIN_PATH");

    private UserLoginRequest userLoginRequest;

    private void assertInvalidСredentials(ValidatableResponse validatableResponse) {
        var response = validatableResponse.assertThat().statusCode(SC_UNAUTHORIZED).extract().as(MessageDto.class);
        assertThat(response).usingRecursiveComparison().isEqualTo(new MessageDto("Invalid credentials"));
    }

    @BeforeEach
    void beforeEachTestData() {
        userLoginRequest = new UserLoginRequest("username", "password");
    }

    @Test
    @DisplayName(TEST_NAME)
    void postLoginTest() {
        commonPostWithoutToken(SC_OK, userLoginRequest, PATH);
    }

    @Test
    @DisplayName(TEST_NAME + " с несуществующей парой username и password")
    void postLoginWithNonExistingPairOfUsernameAndPassword() {
        userLoginRequest.setUsername(RandomStringUtils.randomAlphabetic(15)).setPassword(RandomStringUtils.randomAlphabetic(15));
        assertInvalidСredentials(commonPostWithoutTokenAndCheckStatus(userLoginRequest, PATH));
    }

    @Test
    @DisplayName(TEST_NAME + " с несуществующим username")
    void postLoginWithNonExistingUsername() {
        assertInvalidСredentials(commonPostWithoutTokenAndCheckStatus(userLoginRequest.setUsername(RandomStringUtils.randomAlphabetic(15)), PATH));
    }

    @Test
    @DisplayName(TEST_NAME + " с несуществующим password")
    void postLoginWithNonExistingPassword() {
        assertInvalidСredentials(commonPostWithoutTokenAndCheckStatus(userLoginRequest.setPassword(RandomStringUtils.randomAlphabetic(15)), PATH));
    }
}
