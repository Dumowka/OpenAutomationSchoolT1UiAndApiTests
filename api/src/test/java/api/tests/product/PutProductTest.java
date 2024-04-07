package api.tests.product;

import api.models.request.ProductRequestDto;
import api.models.response.MessageDto;
import api.models.response.ProductDto;
import api.tests.CommonTest;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Stream;

import static api.common.ApiClient.*;
import static api.common.Asserts.assertMissingAuthorization;
import static api.common.Asserts.assertNoteEnoughSegments;
import static api.common.Utils.removeField;
import static api.tests.product.PutProductTest.TEST_NAME;
import static org.apache.http.HttpStatus.*;
import static org.assertj.core.api.Assertions.assertThat;

// Все сценарии для данного метода возвращают 405
@DisplayName(TEST_NAME)
class PutProductTest extends CommonTest {

    protected final static String TEST_NAME = "Изменение продукта";

    private static final String PATH = config.getConfigParameter("PRODUCT_BY_ID_PATH");

    private ProductDto product;

    @BeforeAll
    void beforeAllTestData() {
        // Тут должен создаться Product, из которого мы бы достали productId и который мы бы изменяли
        product = new ProductDto(
                1,
                "name " + UUID.randomUUID(),
                "category " + UUID.randomUUID(),
                new BigDecimal(RandomStringUtils.randomNumeric(3)),
                new BigDecimal(RandomStringUtils.randomNumeric(3))
        );
    }

    @Test
    @DisplayName(TEST_NAME)
    void putProductTest() {
        // По-хорошему еще нужно проверить, что продукт изменился
        commonPut(accessToken, SC_OK, product.mapToProductRequestDto(), PATH, product.getId());
    }

    private Stream<Arguments> requestBodyFields() {
        return Stream.of(
                Arguments.of("поля name", removeField(product.mapToProductRequestDto(), "category", "price", "discount")),
                Arguments.of("поля category", removeField(product.mapToProductRequestDto(), "name", "price", "discount")),
                Arguments.of("поля price", removeField(product.mapToProductRequestDto(), "name", "category", "discount")),
                Arguments.of("поля discount", removeField(product.mapToProductRequestDto(), "name", "category", "price"))
        );
    }

    @ParameterizedTest(name = "{displayName} {0}")
    @MethodSource("requestBodyFields")
    @DisplayName(TEST_NAME + " на новое значение")
    void putProductChangeOneFieldValueTest(String testName, String body) {
        // По-хорошему еще нужно проверить, что продукт изменился
        commonPut(accessToken, SC_OK, body, PATH, product.getId());
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
    void putProductWithIncorrectStringFieldValue(String name, String methodName, String wrongValue) {
        ProductRequestDto productRequest = product.mapToProductRequestDto();
        Method method = productRequest.getClass().getMethod(methodName, String.class);
        method.invoke(productRequest, wrongValue);

        var response = commonPut(accessToken, SC_BAD_REQUEST, productRequest, PATH, product.getId()).extract().as(MessageDto.class);
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
    void putProductWithIncorrectNumberFieldValue(String name, String methodName, BigDecimal wrongValue) {
        ProductRequestDto productRequest = product.mapToProductRequestDto();
        Method method = productRequest.getClass().getMethod(methodName, BigDecimal.class);

        method.invoke(productRequest, wrongValue);
        var response = commonPut(accessToken, SC_BAD_REQUEST, productRequest, PATH, product.getId()).extract().as(MessageDto.class);
        assertThat(response).usingRecursiveComparison().isEqualTo(new MessageDto("Wrong parameter value"));
    }


    @Test
    @DisplayName(TEST_NAME + " по несуществующему productId")
    void putNonExistingProductTest() {
        commonPut(accessToken, SC_NOT_FOUND, product.mapToProductRequestDto(), PATH, Integer.MAX_VALUE);
    }

    @Test
    @DisplayName(TEST_NAME + " без токена")
    void putWithoutTokenTest() {
        assertMissingAuthorization(commonPutWithoutTokenAndCheckStatus(product.mapToProductRequestDto(), PATH, product.getId()));
    }

    @Test
    @DisplayName(TEST_NAME + " с некорректным токеном")
    void putProductWithIncorrectTokenTest() {
        assertNoteEnoughSegments(commonPutWithoutCheckStatus("accessToken", product.mapToProductRequestDto(), PATH, product.getId()));
    }
}
