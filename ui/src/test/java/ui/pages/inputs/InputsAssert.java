package ui.pages.inputs;

import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.value;

public class InputsAssert extends AbstractAssert<InputsAssert, InputsPage> {

    protected InputsAssert(InputsPage inputsPage, Class<?> selfType) {
        super(inputsPage, selfType);
    }

    public static InputsAssert assertThat(InputsPage actual) {
        return new InputsAssert(actual, InputsAssert.class);
    }

    public InputsPage page() {
        return actual;
    }

    @Step("Проверка значения у поля ввода")
    public InputsAssert checkNumberInputValue(String expectedValue) {
        actual.numberInput.shouldHave(value(expectedValue));
        return this;
    }
}
