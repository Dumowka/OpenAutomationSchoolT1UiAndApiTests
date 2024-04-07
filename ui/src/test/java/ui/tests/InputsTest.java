package ui.tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import ui.pages.inputs.InputsPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Страница Inputs")
class InputsTest extends AbstractTest {

    private InputsPage inputsPage;

    @Test
    @Order(1)
    @DisplayName("Переход на страницу Inputs")
    void goToInputsPageTest() {
        inputsPage = mainPage.clickOnInputsLink();
    }

    @TestFactory
    @Order(2)
    @DisplayName("Тест")
    @Description("""
            Перейти на страницу Inputs. 
            Ввести любое случайное число от 1 до 10 000. 
            Вывести в консоль значение элемента Input.
            """)
    List<DynamicTest> inputsTest() {
        Map<String, String> testDataAndExpectedResults = new HashMap<>() {{
            put("'123.54'", "123.54");
            put("'1'", "1");
            put("' 1.4'", "1.4");
            put("'4.6 '", "4.6");
            put("'2$.8 '", "2.8");
            put("'a8 '", "8");
            put("'asd'", "");
            put("'Имя'", "");
            put("'$@'", "");
            put("'   '", "");
        }};

        return testDataAndExpectedResults.keySet().stream().map(key -> DynamicTest.dynamicTest(
                "Ввод значения - " + key,
                () -> inputsPage.clearNumberInput().setValueInNumberInput(key).check().checkNumberInputValue(testDataAndExpectedResults.get(key))
        )).collect(Collectors.toList());
    }
}
