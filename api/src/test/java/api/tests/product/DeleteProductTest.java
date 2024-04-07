package api.tests.product;

import api.models.response.MessageDto;
import api.tests.CommonTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static api.common.ApiClient.*;
import static api.common.Asserts.assertMissingAuthorization;
import static api.common.Asserts.assertNoteEnoughSegments;
import static api.tests.product.DeleteProductTest.TEST_NAME;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

// Все сценарии для данного метода возвращают 405
@DisplayName(TEST_NAME)
class DeleteProductTest extends CommonTest {

    protected final static String TEST_NAME = "Удаление продукта";

    private static final String PATH = config.getConfigParameter("PRODUCT_BY_ID_PATH");

    private int productId;

    @BeforeAll
    void beforeAllTestData() {
        // Тут должен создаться Product, из которого мы бы достали productId и который мы бы удаляли
        productId = 1;
    }

    @Test
    @DisplayName(TEST_NAME)
    void deleteProductTest() {
        // По-хорошему еще нужно проверить, что продукт пропал из списка всех продуктов
        commonDelete(accessToken, SC_OK, PATH, productId);
    }

    @Test
    @DisplayName(TEST_NAME + " с несуществующим productId")
    void deleteProductWithNonExistingProductIdTest() {
        var response = commonDelete(accessToken, SC_NOT_FOUND, PATH, Integer.MAX_VALUE).extract().as(MessageDto.class);
        assertThat(response).usingRecursiveComparison().isEqualTo(new MessageDto("Product not found"));
    }

    @Test
    @DisplayName(TEST_NAME + " без токена")
    void deleteProductWithoutTokenTest() {
        assertMissingAuthorization(commonDeleteWithoutTokenAndCheckStatus(PATH, productId));
    }

    @Test
    @DisplayName(TEST_NAME + " с некорректным токеном")
    void deleteProductWithIncorrectTokenTest() {
        assertNoteEnoughSegments(commonDeleteWithoutCheckStatus("accessToken", PATH, productId));
    }
}
