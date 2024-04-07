package api.tests.user;

import api.models.request.UserRegistrationRequest;
import api.models.response.MessageDto;
import api.tests.CommonTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static api.common.ApiClient.commonPostWithoutToken;
import static api.tests.user.RegisterTest.TEST_NAME;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(TEST_NAME)
class RegisterTest extends CommonTest {

    protected final static String TEST_NAME = "Регистрация пользователя";

    private static final String PATH = config.getConfigParameter("REGISTER_PATH");

    private UserRegistrationRequest userRegistrationRequest;

    @BeforeEach
    void beforeEachTestData() {
        userRegistrationRequest = new UserRegistrationRequest(RandomStringUtils.randomAlphabetic(15), RandomStringUtils.randomAlphabetic(15));
    }

    @Test
    @DisplayName(TEST_NAME)
    void postRegisterTest() {
        var response = commonPostWithoutToken(SC_CREATED, userRegistrationRequest, PATH).extract().as(MessageDto.class);
        assertThat(response).usingRecursiveComparison().isEqualTo(new MessageDto("User registered successfully"));
    }

    @Test
    @DisplayName(TEST_NAME + " с уже существующей парой username и password")
    void postRegisterWithExistingUsernameAndPasswordTest() {
        commonPostWithoutToken(SC_CREATED, userRegistrationRequest, PATH).extract().as(MessageDto.class);
        var response = commonPostWithoutToken(SC_BAD_REQUEST, userRegistrationRequest, PATH).extract().as(MessageDto.class);

        assertThat(response).usingRecursiveComparison().isEqualTo(new MessageDto("User already exists"));
    }

    @Test
    @DisplayName(TEST_NAME + " с уже существующим username")
    void postRegisterWithExistingUsernameTest() {
        commonPostWithoutToken(SC_CREATED, userRegistrationRequest, PATH).extract().as(MessageDto.class);
        var response = commonPostWithoutToken(
                SC_BAD_REQUEST,
                userRegistrationRequest.setPassword(RandomStringUtils.randomAlphabetic(15)),
                PATH
        ).extract().as(MessageDto.class);

        assertThat(response).usingRecursiveComparison().isEqualTo(new MessageDto("User already exists"));
    }

    @Test
    @DisplayName(TEST_NAME + " с уже существующим password")
    void postRegisterWithExistingPasswordTest() {
        commonPostWithoutToken(SC_CREATED, userRegistrationRequest, PATH).extract().as(MessageDto.class);
        var response = commonPostWithoutToken(
                SC_CREATED,
                userRegistrationRequest.setUsername(RandomStringUtils.randomAlphabetic(15)),
                PATH
        ).extract().as(MessageDto.class);

        assertThat(response).usingRecursiveComparison().isEqualTo(new MessageDto("User registered successfully"));
    }
}
