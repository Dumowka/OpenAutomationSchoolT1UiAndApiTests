package ui.pages.checkboxes;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ui.pages.AbstractPage;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$$x;

public class CheckboxesPage extends AbstractPage {

    final ElementsCollection checkboxesInput = $$x("//input[@type='checkbox']").as("Чекбоксы");

    public CheckboxesPage() {
        super("Checkboxes");
    }

    public CheckboxesAssert check() {
        return CheckboxesAssert.assertThat(this);
    }

    public CheckboxesPage clickOnFirstCheckbox() {
        checkboxesInput.get(0).click();
        return this;
    }

    public CheckboxesPage clickOnSecondCheckbox() {
        checkboxesInput.get(1).click();
        return this;
    }

    public boolean getFirstCheckboxCheckedValue() {
        return checkboxesInput.get(0).getAttribute("checked") != null;
    }

    public boolean getSecondCheckboxCheckedValue() {
        return checkboxesInput.get(1).getAttribute("checked") != null;
    }
}
