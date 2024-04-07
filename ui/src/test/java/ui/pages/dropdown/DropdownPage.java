package ui.pages.dropdown;

import com.codeborne.selenide.SelenideElement;
import ui.pages.AbstractPage;
import ui.pages.checkboxes.CheckboxesAssert;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.$x;

public class DropdownPage extends AbstractPage {

    final SelenideElement dropdownSelect = $x("//select[@id='dropdown']").as("Выпадающий список");
    final SelenideElement firstOption = $x("//option[@value='1']").as("Первый элемент выпадающего списка");
    final SelenideElement secondOption = $x("//option[@value='2']").as("Второй элемент выпадающего списка");

    public DropdownPage() {
        super("Dropdown List");
    }

    public DropdownAssert check() {
        return DropdownAssert.assertThat(this);
    }

    public DropdownPage clickOnDropdownSelect() {
        dropdownSelect.click();
        return this;
    }

    public DropdownPage printDropdownSelectText() {
        System.out.println(dropdownSelect.getText());
        return this;
    }

    public DropdownPage selectFirstOption() {
        firstOption.click();
        return this;
    }

    public DropdownPage selectSecondOption() {
        secondOption.click();
        return this;
    }
}
