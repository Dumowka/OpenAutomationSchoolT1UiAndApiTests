package ui.pages;

import com.codeborne.selenide.SelenideElement;
import ui.pages.checkboxes.CheckboxesPage;
import ui.pages.disappearing_elements.DisappearingElementsPage;
import ui.pages.dropdown.DropdownPage;
import ui.pages.inputs.InputsPage;

import static com.codeborne.selenide.Selenide.$x;

public class MainPage extends AbstractPage {
    private final SelenideElement checkboxesLink = $x("//a[@href='/checkboxes']").as("Ссылка на страницу 'Checkboxes'");
    private final SelenideElement dropdownLink = $x("//a[@href='/dropdown']").as("Ссылка на страницу 'Dropdown'");
    private final SelenideElement disappearingElementsLink = $x("//a[@href='/disappearing_elements']").as("Ссылка на страницу 'Disappearing Elements'");
    private final SelenideElement inputsLink = $x("//a[@href='/inputs']").as("Ссылка на страницу 'Inputs'");
    private final SelenideElement hoversLink = $x("//a[@href='/hovers']").as("Ссылка на страницу 'Hovers'");
    private final SelenideElement notificationMessagesLink = $x("//a[@href='/notification_message']").as("Ссылка на страницу 'Notification Message'");
    private final SelenideElement addRemoveElementsLink = $x("//a[@href='/add_remove_elements/']").as("Ссылка на страницу 'Add/Remove Elements'");
    private final SelenideElement statusCodesLink = $x("//a[@href='/status_codes']").as("Ссылка на страницу 'Status Codes'");
    private final SelenideElement dragAndDropLink = $x("//a[@href='/drag_and_drop']").as("Ссылка на страницу 'Drag And Drop'");
    private final SelenideElement contextMenuLink = $x("//a[@href='/context_menu']").as("Ссылка на страницу 'Context Menu'");
    private final SelenideElement infiniteScrollLink = $x("//a[@href='/infinite_scroll']").as("Ссылка на страницу 'Infinite Scroll'");
    private final SelenideElement keyPressesLink = $x("//a[@href='/key_presses']").as("Ссылка на страницу 'Key Presses'");

    public MainPage() {
        super("Welcome to the-internet");
    }

    public CheckboxesPage clickOnCheckboxesLink() {
        checkboxesLink.click();
        return new CheckboxesPage();
    }

    public DropdownPage clickOnDropdownLink() {
        dropdownLink.click();
        return new DropdownPage();
    }

    public DisappearingElementsPage clickOnDisappearingElementsLink() {
        disappearingElementsLink.click();
        return new DisappearingElementsPage();
    }

    public InputsPage clickOnInputsLink() {
        inputsLink.click();
        return new InputsPage();
    }
}
