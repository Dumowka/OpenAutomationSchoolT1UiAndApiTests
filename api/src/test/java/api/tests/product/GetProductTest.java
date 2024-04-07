package api.tests.product;

import api.models.response.MessageDto;
import api.models.response.ProductDto;
import api.tests.CommonTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static api.common.ApiClient.commonGet;
import static api.common.ApiClient.commonGetWithoutToken;
import static api.tests.product.GetProductsTest.TEST_NAME;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(TEST_NAME)
class GetProductTest extends CommonTest {

    protected final static String TEST_NAME = "Получение продукта";

    private static final String PATH = config.getConfigParameter("PRODUCT_BY_ID_PATH");

    private final ProductDto product = new ProductDto(1, "HP Pavilion Laptop", "Electronics", new BigDecimal("10.99"), new BigDecimal("10.0"));

    @Test
    @DisplayName(TEST_NAME)
    void getProductTest() {
        // По-хорошему мы должны создать сущность  и проверить его наличие при выполнении запроса
        var response = commonGet(accessToken, SC_OK, PATH, product.getId()).extract().jsonPath().getList(".", ProductDto.class);
        assertThat(response.size()).isOne();
        assertThat(response).usingRecursiveFieldByFieldElementComparator().contains(product);
    }

    @Test
    @DisplayName(TEST_NAME + " с несуществующим productId")
    void getProductWithNonExistingProductIdTest() {
        var response = commonGet(accessToken, SC_NOT_FOUND, PATH, Integer.MAX_VALUE).extract().as(MessageDto.class);
        assertThat(response).usingRecursiveComparison().isEqualTo(new MessageDto("Product not found"));
    }

    @Test
    @DisplayName(TEST_NAME + " без токена")
    void getProductWithoutTokenTest() {
        var response = commonGetWithoutToken(SC_OK, PATH, product.getId()).extract().jsonPath().getList(".", ProductDto.class);
        assertThat(response.size()).isOne();
        assertThat(response).usingRecursiveFieldByFieldElementComparator().contains(product);
    }

    @Test
    @DisplayName(TEST_NAME + " с некорректным токеном")
    void getProductWithIncorrectTokenTest() {
        var response = commonGet("accessToken", SC_OK, PATH, product.getId()).extract().jsonPath().getList(".", ProductDto.class);
        assertThat(response.size()).isOne();
        assertThat(response).usingRecursiveFieldByFieldElementComparator().contains(product);
    }
}
