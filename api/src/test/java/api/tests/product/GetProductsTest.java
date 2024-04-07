package api.tests.product;

import api.models.response.ProductDto;
import api.tests.CommonTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static api.common.ApiClient.commonGet;
import static api.common.ApiClient.commonGetWithoutToken;
import static api.tests.product.GetProductsTest.TEST_NAME;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(TEST_NAME)
class GetProductsTest extends CommonTest {

    protected final static String TEST_NAME = "Получение списка продуктов";

    private static final String PATH = config.getConfigParameter("PRODUCTS_PATH");

    private final List<ProductDto> products = List.of(
            new ProductDto(1, "HP Pavilion Laptop", "Electronics", new BigDecimal("10.99"), new BigDecimal("10.0")),
            new ProductDto(2, "Samsung Galaxy Smartphone", "Electronics", new BigDecimal("15.99"), null),
            new ProductDto(3, "Adidas T-shirt", "Clothing", new BigDecimal("8.99"), new BigDecimal("2.5")),
            new ProductDto(4, "Levis Jeans", "Clothing", new BigDecimal("12.99"), new BigDecimal("15.0")),
            new ProductDto(5, "HP Pavilion Laptop", "Electronics", new BigDecimal("10.99"), new BigDecimal("10.0")),
            new ProductDto(6, "Samsung Galaxy Smartphone", "Electronics", new BigDecimal("15.99"), null),
            new ProductDto(7, "Adidas T-shirt", "Clothing", new BigDecimal("8.99"), new BigDecimal("2.5")),
            new ProductDto(8, "Levis Jeans", "Clothing", new BigDecimal("12.99"), new BigDecimal("15.0"))
    );


    @Test
    @DisplayName(TEST_NAME)
    void getProductsTest() {
        // По-хорошему мы должны создать сущность - две и проверить их наличие при выполнении запроса
        var response = commonGet(accessToken, SC_OK, PATH).extract().jsonPath().getList(".", ProductDto.class);
        assertThat(response).usingRecursiveFieldByFieldElementComparator().isEqualTo(products);
    }

    @Test
    @DisplayName(TEST_NAME + " без токена")
    void getProductsWithoutTokenTest() {
        var response = commonGetWithoutToken(SC_OK, PATH).extract().jsonPath().getList(".", ProductDto.class);
        assertThat(response).usingRecursiveFieldByFieldElementComparator().isEqualTo(products);
    }

    @Test
    @DisplayName(TEST_NAME + " с некорректным токеном")
    void getProductsWithIncorrectTokenTest() {
        var response = commonGet("accessToken", SC_OK, PATH).extract().jsonPath().getList(".", ProductDto.class);
        assertThat(response).usingRecursiveFieldByFieldElementComparator().isEqualTo(products);
    }
}
