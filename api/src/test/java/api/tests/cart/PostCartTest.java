package api.tests.cart;

import api.models.request.AddToCartRequest;
import api.models.response.MessageDto;
import api.tests.CommonTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Method;
import java.util.stream.Stream;

import static api.common.ApiClient.*;
import static api.common.Asserts.assertMissingAuthorization;
import static api.common.Asserts.assertNoteEnoughSegments;
import static api.common.Utils.deepCopy;
import static api.common.Utils.removeField;
import static api.tests.cart.PostCartTest.TEST_NAME;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(TEST_NAME)
class PostCartTest extends CommonTest {

    protected final static String TEST_NAME = "Добавление товара в корзину";

    private static final String PATH = config.getConfigParameter("CART_PATH");

    private final AddToCartRequest addToCartRequest = new AddToCartRequest(1, 2);


    @Test
    @DisplayName(TEST_NAME)
    void postCartTest() {
        var response = commonPost(accessToken, SC_CREATED, addToCartRequest, PATH).extract().as(MessageDto.class);
        assertThat(response).usingRecursiveComparison().isEqualTo(new MessageDto("Product added to cart successfully"));
    }

    // По-хорошему тут должен быть 404 и в тексте ошибки указываться недостающее поле
    @ParameterizedTest(name = "{displayName}: {0}")
    @ValueSource(strings = {"productId", "quantity"})
    @DisplayName(TEST_NAME + " без поля")
    void postCartWithoutField(String field) {
        var response = commonPost(accessToken, SC_NOT_FOUND, removeField(addToCartRequest, field), PATH).extract().as(MessageDto.class);
        assertThat(response).usingRecursiveComparison().isEqualTo(new MessageDto("Product not found"));
    }

    // По-хорошему тут должен быть 404 и в тексте ошибки указываться недостающее поле
    @Test
    @DisplayName(TEST_NAME + " с пустым телом запроса")
    void postCartWithEmptyBody() {
        var response = commonPost(accessToken, SC_NOT_FOUND, "{}", PATH).extract().as(MessageDto.class);
        assertThat(response).usingRecursiveComparison().isEqualTo(new MessageDto("Product not found"));
    }

    private Stream<Arguments> incorrectNumberFieldValue() {
        return Stream.of(
                Arguments.of("productId", "setProductId", -1),
                Arguments.of("productId", "setProductId", null),
                Arguments.of("quantity", "setQuantity", -1),
                Arguments.of("quantity", "setQuantity", null)
        );
    }

    // По-хорошему тут должен быть 404 и в тексте ошибки указываться "Wrong parameter value"
    @SneakyThrows
    @ParameterizedTest(name = "{displayName} {0}")
    @MethodSource("incorrectNumberFieldValue")
    @DisplayName(TEST_NAME + " с некорректным значением поля")
    void postCartWithIncorrectNumberFieldValue(String name, String methodName, Integer wrongValue) {
        AddToCartRequest request = deepCopy(addToCartRequest, AddToCartRequest.class);
        Method method = request.getClass().getMethod(methodName, Integer.class);
        method.invoke(request, wrongValue);

        var response = commonPost(accessToken, SC_NOT_FOUND, request, PATH).extract().as(MessageDto.class);
        assertThat(response).usingRecursiveComparison().isEqualTo(new MessageDto("Wrong parameter value"));
    }

    @Test
    @DisplayName(TEST_NAME + " без токена")
    void postCartWithoutTokenTest() {
        assertMissingAuthorization(commonPostWithoutTokenAndCheckStatus(addToCartRequest, PATH));
    }

    @Test
    @DisplayName(TEST_NAME + " с некорректным токеном")
    void postCartWithIncorrectTokenTest() {
        assertNoteEnoughSegments(commonPostWithoutCheckStatus("accessToken", addToCartRequest, PATH));
    }
}
