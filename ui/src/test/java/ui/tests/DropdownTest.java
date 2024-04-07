package ui.tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.pages.dropdown.DropdownPage;

@DisplayName("Страница Dropdown")
class DropdownTest extends AbstractTest {

    @Test
    @DisplayName("Тест")
    @Description("""
            Перейти на страницу Dropdown.
            Выбрать первую опцию,
            вывести в консоль текущий текст элемента dropdown,
            выбрать вторую опцию,
            вывести в консоль текущий текст элемента dropdown.
            """)
    void dropdownTest() {
        DropdownPage dropdownPage = mainPage.clickOnDropdownLink();
        dropdownPage
                .clickOnDropdownSelect()
                .selectFirstOption()
                .check()
                .checkFirstOptionSelectedStatus(true)
                .page()
                .printDropdownSelectText()
                .selectSecondOption()
                .check()
                .checkFirstOptionSelectedStatus(false)
                .checkSecondOptionSelectedStatus(true)
                .page()
                .printDropdownSelectText();
    }
}
