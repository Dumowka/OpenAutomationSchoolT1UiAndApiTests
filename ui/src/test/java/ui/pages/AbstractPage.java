package ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public abstract class AbstractPage {

    private final SelenideElement title;

    public AbstractPage(String title) {
        this.title = $x("//*[self::h3 or self::h1 and text()='%s']".formatted(title)).as("Заголовок страницы '%s'".formatted(title));
        waitForPageLoading();
    }

    protected void waitForPageLoading() {
        title.shouldBe(visible);
    }
}
