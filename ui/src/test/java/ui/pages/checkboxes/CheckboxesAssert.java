package ui.pages.checkboxes;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.not;

public class CheckboxesAssert extends AbstractAssert<CheckboxesAssert, CheckboxesPage> {

    protected CheckboxesAssert(CheckboxesPage checkboxesPage, Class<?> selfType) {
        super(checkboxesPage, selfType);
    }

    public static CheckboxesAssert assertThat(CheckboxesPage actual) {
        return new CheckboxesAssert(actual, CheckboxesAssert.class);
    }

    public CheckboxesPage page() {
        return actual;
    }

    @Step("Проверка того, что у первого чекбокса поменялся selected")
    public CheckboxesAssert checkThatFirstCheckboxCheckedHasChanged(boolean isChecked) {
        return checkThatCheckboxCheckedHasChanged(actual.checkboxesInput.get(0), isChecked);
    }

    @Step("Проверка того, что у второго чекбокса поменялся selected")
    public CheckboxesAssert checkThatSecondCheckboxCheckedHasChanged(boolean isChecked) {
        return checkThatCheckboxCheckedHasChanged(actual.checkboxesInput.get(1), isChecked);
    }

    private CheckboxesAssert checkThatCheckboxCheckedHasChanged(SelenideElement checkbox, boolean isChecked) {
        checkbox.shouldHave(not(attribute("checked", isChecked ? "true" : "")));
        return this;
    }
}
