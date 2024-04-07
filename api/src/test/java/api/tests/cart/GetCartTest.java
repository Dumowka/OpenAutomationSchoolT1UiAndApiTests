package api.tests.cart;

import api.models.response.ShoppingCartResponse;
import api.tests.CommonTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static api.common.ApiClient.*;
import static api.common.Asserts.assertMissingAuthorization;
import static api.common.Asserts.assertNoteEnoughSegments;
import static api.tests.cart.GetCartTest.TEST_NAME;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(TEST_NAME)
class GetCartTest extends CommonTest {

    protected static final String TEST_NAME = "Получение корзины с товарами";

    private static final String PATH = config.getConfigParameter("CART_PATH");

    @Test
    @DisplayName(TEST_NAME)
    void getCartTest() {
        var shoppingCart = new ShoppingCartResponse(
                List.of(
                        new ShoppingCartResponse.ProductInCartDto(
                                1,
                                "HP Pavilion Laptop",
                                "Electronics",
                                new BigDecimal("10.99"),
                                new BigDecimal("10.0"),
                                24
                        )
                ),
                new BigDecimal("274.75"),
                new BigDecimal("27.48")
        );
        var response = commonGet(accessToken, SC_OK, PATH).extract().as(ShoppingCartResponse.class);
        assertThat(response).usingRecursiveComparison().ignoringFields("total_discount", "total_price", "cart.quantity").isEqualTo(shoppingCart);
    }

    @Test
    @DisplayName(TEST_NAME + " без токена")
    void getCartWithoutTokenTest() {
        assertMissingAuthorization(commonGetWithoutTokenAndCheckStatus(PATH));
    }

    @Test
    @DisplayName(TEST_NAME + " с некорректным токеном")
    void getCartWithIncorrectTokenTest() {
        assertNoteEnoughSegments(commonGetWithoutCheckStatus("accessToken", PATH));
    }
}
