package ui.pages.dropdown;

import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;
import ui.pages.checkboxes.CheckboxesAssert;
import ui.pages.checkboxes.CheckboxesPage;

import static com.codeborne.selenide.Condition.attribute;

public class DropdownAssert extends AbstractAssert<DropdownAssert, DropdownPage> {

    protected DropdownAssert(DropdownPage dropdownPage, Class<?> selfType) {
        super(dropdownPage, selfType);
    }

    public static DropdownAssert assertThat(DropdownPage actual) {
        return new DropdownAssert(actual, DropdownAssert.class);
    }

    public DropdownPage page() {
        return actual;
    }

    @Step("Проверка того, что первый элемент выпадающего списка имеет ожидаемый selected")
    public DropdownAssert checkFirstOptionSelectedStatus(boolean shouldBeSelected) {
        actual.firstOption.shouldBe(attribute("selected", shouldBeSelected? "true" : ""));
        return this;
    }

    @Step("Проверка того, что второй элемент выпадающего списка имеет ожидаемый selected")
    public DropdownAssert checkSecondOptionSelectedStatus(boolean shouldBeSelected) {
        actual.secondOption.shouldBe(attribute("selected", shouldBeSelected? "true" : ""));
        return this;
    }
}
