package ui.tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import ui.pages.disappearing_elements.DisappearingElementsPage;

import static com.codeborne.selenide.Selenide.refresh;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Страница Disappearing Elements")
class DisappearingElementsTest extends AbstractTest {

    private DisappearingElementsPage disappearingElementsPage;

    @Test
    @Order(1)
    @DisplayName("Переход на страницу Disappearing Elements")
    void goToDisappearingElementsPageTest() {
        disappearingElementsPage = mainPage.clickOnDisappearingElementsLink();
    }

    @RepeatedTest(value = 10, failureThreshold = 9)
    @Order(2)
    @DisplayName("Тест")
    @Description("""
            Перейти на страницу Disappearing Elements.
            Добиться отображения 5 элементов, максимум за 10 попыток, если нет, провалить тест с ошибкой.
            """)
    void disappearingElementsTest() {
        refresh();
        disappearingElementsPage.check().checkThatAllLinksIsAppear();
    }
}
