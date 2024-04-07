package ui.pages.disappearing_elements;

import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;
import ui.pages.checkboxes.CheckboxesAssert;
import ui.pages.checkboxes.CheckboxesPage;

import static com.codeborne.selenide.Condition.visible;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DisappearingElementsAssert extends AbstractAssert<DisappearingElementsAssert, DisappearingElementsPage> {

    protected DisappearingElementsAssert(DisappearingElementsPage disappearingElementsPage, Class<?> selfType) {
        super(disappearingElementsPage, selfType);
    }

    public static DisappearingElementsAssert assertThat(DisappearingElementsPage actual) {
        return new DisappearingElementsAssert(actual, DisappearingElementsAssert.class);
    }

    public DisappearingElementsPage page() {
        return actual;
    }

    @Step("Проверка того, что все ссылки видимы")
    public DisappearingElementsAssert checkThatAllLinksIsAppear() {
        assertTrue(
                actual.homeLink.isDisplayed() &&
                        actual.aboutLink.isDisplayed() &&
                        actual.contactUsLink.isDisplayed() &&
                        actual.portfolioLink.isDisplayed() &&
                        actual.galleryLink.isDisplayed()
        );
        return this;
    }
}
