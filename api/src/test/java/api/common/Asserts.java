package api.common;

import api.models.response.MessageDto;
import io.restassured.response.ValidatableResponse;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.apache.http.HttpStatus.SC_UNPROCESSABLE_ENTITY;
import static org.assertj.core.api.Assertions.assertThat;

public class Asserts {

    public static void assertMissingAuthorization(ValidatableResponse validatableResponse) {
        var response = validatableResponse.assertThat().statusCode(SC_UNAUTHORIZED).extract().as(MessageDto.class);
        assertThat(response).usingRecursiveComparison().isEqualTo(new MessageDto("Missing Authorization Header"));
    }

    public static void assertNoteEnoughSegments(ValidatableResponse validatableResponse) {
        var response = validatableResponse.assertThat().statusCode(SC_UNPROCESSABLE_ENTITY).extract().as(MessageDto.class);
        assertThat(response).usingRecursiveComparison().isEqualTo(new MessageDto("Not enough segments"));
    }
}
