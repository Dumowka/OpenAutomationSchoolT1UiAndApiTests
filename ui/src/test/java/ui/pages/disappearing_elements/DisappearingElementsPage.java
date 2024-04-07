package ui.pages.disappearing_elements;

import com.codeborne.selenide.SelenideElement;
import ui.pages.AbstractPage;
import ui.pages.dropdown.DropdownAssert;

import static com.codeborne.selenide.Selenide.$x;

public class DisappearingElementsPage extends AbstractPage {

    final SelenideElement homeLink = $x("//a[@href='/']").as("Home (кнопка)");
    final SelenideElement aboutLink = $x("//a[@href='/about/']").as("About (кнопка)");
    final SelenideElement contactUsLink = $x("//a[@href='/contact-us/']").as("Contact Us (кнопка)");
    final SelenideElement portfolioLink = $x("//a[@href='/portfolio/']").as("Portfolio (кнопка)");
    final SelenideElement galleryLink = $x("//a[@href='/gallery/']").as("Gallery (кнопка)");

    public DisappearingElementsPage() {
        super("Disappearing Elements");
    }

    public DisappearingElementsAssert check() {
        return DisappearingElementsAssert.assertThat(this);
    }
}
