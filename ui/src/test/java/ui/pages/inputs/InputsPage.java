package ui.pages.inputs;

import com.codeborne.selenide.SelenideElement;
import ui.pages.AbstractPage;
import ui.pages.dropdown.DropdownAssert;

import static com.codeborne.selenide.Selenide.$x;

public class InputsPage extends AbstractPage {

    final SelenideElement numberInput = $x("//input[@type='number']").as("Поле ввода");

    public InputsPage() {
        super("Inputs");
    }

    public InputsAssert check() {
        return InputsAssert.assertThat(this);
    }

    public InputsPage setValueInNumberInput(String value) {
        numberInput.sendKeys(value);
        return this;
    }

    public InputsPage clearNumberInput() {
        numberInput.clear();
        return this;
    }
}
