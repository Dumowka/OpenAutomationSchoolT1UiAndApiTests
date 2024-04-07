package api.tests.cart;

import api.models.response.MessageDto;
import api.tests.CommonTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static api.common.ApiClient.*;
import static api.common.Asserts.assertMissingAuthorization;
import static api.common.Asserts.assertNoteEnoughSegments;
import static api.tests.cart.GetCartTest.TEST_NAME;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(TEST_NAME)
class DeleteCartTest extends CommonTest {

    protected static final String TEST_NAME = "Удаление корзины с товарами";

    private static final String PATH = config.getConfigParameter("CART_BY_ID_PATH");

    private final int cartId = 1;

    @Test
    @DisplayName(TEST_NAME)
    void deleteCartTest() {
        var response = commonDelete(accessToken, SC_OK, PATH, cartId).extract().as(MessageDto.class);
        assertThat(response).usingRecursiveComparison().isEqualTo(new MessageDto("Product removed from cart"));
    }

    @Test
    @DisplayName(TEST_NAME + " с несуществующим cartId")
    void deleteWithNonExistingCartIdTest() {
        var response = commonDelete(accessToken, SC_NOT_FOUND, PATH, Integer.MAX_VALUE).extract().as(MessageDto.class);
        assertThat(response).usingRecursiveComparison().isEqualTo(new MessageDto("Product not found in cart"));
    }

    @Test
    @DisplayName(TEST_NAME + " без токена")
    void deleteWithoutTokenTest() {
        assertMissingAuthorization(commonDeleteWithoutTokenAndCheckStatus(PATH, cartId));
    }

    @Test
    @DisplayName(TEST_NAME + " с некорректным токеном")
    void deleteWithIncorrectTokenTest() {
        assertNoteEnoughSegments(commonDeleteWithoutCheckStatus("accessToken", PATH, cartId));
    }
}
