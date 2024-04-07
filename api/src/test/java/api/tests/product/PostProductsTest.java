package api.tests.product;

import api.models.request.ProductRequestDto;
import api.models.response.MessageDto;
import api.tests.CommonTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.stream.Stream;

import static api.common.ApiClient.*;
import static api.common.Asserts.assertMissingAuthorization;
import static api.common.Asserts.assertNoteEnoughSegments;
import static api.common.Utils.removeField;
import static api.tests.product.PostProductsTest.TEST_NAME;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.assertj.core.api.Assertions.assertThat;

// Все сценарии для данного метода возвращают 405
@DisplayName(TEST_NAME)
class PostProductsTest extends CommonTest {

    protected final static String TEST_NAME = "Добавление продукта";

    private static final String PATH = config.getConfigParameter("PRODUCTS_PATH");

    private ProductRequestDto productRequest;

    @BeforeEach
    void beforeEachTestData() {
        productRequest = new ProductRequestDto(
                "name",
                "category",
                new BigDecimal("1.0"),
                new BigDecimal("1.0")
        );
    }

    @Test
    @DisplayName(TEST_NAME)
    void postProductTest() {
        // По-хорошему еще нужно проверить, что продукт появился в списке всех продуктов
        commonPost(accessToken, SC_CREATED, productRequest, PATH);
    }

    // По-хорошему тут должен быть 404 и в тексте ошибки указываться недостающее поле
    @ParameterizedTest(name = "{displayName}: {0}")
    @ValueSource(strings = {"name", "category", "price", "discount"})
    @DisplayName(TEST_NAME + " без поля")
    void postProductWithoutField(String field) {
        var response = commonPost(accessToken, SC_BAD_REQUEST, removeField(productRequest, field), PATH).extract().as(MessageDto.class);
        assertThat(response).usingRecursiveComparison().isEqualTo(new MessageDto("Required field: " + field));
    }

    // По-хорошему тут должен быть 404 и в тексте ошибки указываться недостающее поле
    @Test
    @DisplayName(TEST_NAME + " с пустым телом запроса")
    void postProductWithEmptyBody() {
        var response = commonPost(accessToken, SC_BAD_REQUEST, "{}", PATH).extract().as(MessageDto.class);
        assertThat(response).usingRecursiveComparison().isEqualTo(new MessageDto("Empty body "));
    }

    private Stream<Arguments> incorrectStringFieldValue() {
        return Stream.of(
                Arguments.of("name = \"\"", "setName", ""),
                Arguments.of("name = null", "setName", null),
                Arguments.of("category = \"\"", "setCategory", ""),
                Arguments.of("category = null", "setCategory", null)
        );
    }

    @SneakyThrows
    @ParameterizedTest(name = "{displayName} {0}")
    @MethodSource("incorrectStringFieldValue")
    @DisplayName(TEST_NAME + " с некорректным значением поля")
    void postProductWithIncorrectStringFieldValue(String name, String methodName, String wrongValue) {
        Method method = productRequest.getClass().getMethod(methodName, String.class);
        method.invoke(productRequest, wrongValue);

        var response = commonPost(accessToken, SC_BAD_REQUEST, productRequest, PATH).extract().as(MessageDto.class);
        assertThat(response).usingRecursiveComparison().isEqualTo(new MessageDto("Wrong parameter value"));
    }

    private Stream<Arguments> incorrectNumberFieldValue() {
        return Stream.of(
                Arguments.of("price", "setPrice", new BigDecimal("-1.0")),
                Arguments.of("price", "setPrice", null),
                Arguments.of("discount", "setDiscount", new BigDecimal("-1.0")),
                Arguments.of("discount", "setDiscount", null)
        );
    }

    @SneakyThrows
    @ParameterizedTest(name = "{displayName} {0}")
    @MethodSource("incorrectNumberFieldValue")
    @DisplayName(TEST_NAME + " с некорректным значением поля")
    void postProductWithIncorrectNumberFieldValue(String name, String methodName, BigDecimal wrongValue) {
        Method method = productRequest.getClass().getMethod(methodName, BigDecimal.class);
        method.invoke(productRequest, wrongValue);

        var response = commonPost(accessToken, SC_BAD_REQUEST, productRequest, PATH).extract().as(MessageDto.class);
        assertThat(response).usingRecursiveComparison().isEqualTo(new MessageDto("Wrong parameter value"));
    }

    // По-хорошему тут должен быть 401 и в текст ошибки "Missing Authorization Header"
    @Test
    @DisplayName(TEST_NAME + " без токена")
    void postProductWithoutTokenTest() {
        assertMissingAuthorization(commonPostWithoutTokenAndCheckStatus(productRequest, PATH));
    }

    // По-хорошему тут должен быть 401 и в текст ошибки "Not enough segments"
    @Test
    @DisplayName(TEST_NAME + " с некорректным токеном")
    void postProductWithIncorrectTokenTest() {
        assertNoteEnoughSegments(commonPostWithoutCheckStatus("accessToken", productRequest, PATH));
    }
}
